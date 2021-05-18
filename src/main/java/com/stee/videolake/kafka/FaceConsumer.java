package com.stee.videolake.kafka;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stee.videolake.face.model.Face;
import com.stee.videolake.face.model.db.FaceWriter;
import com.stee.videolake.util.CassandraUtils;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@Service
@Slf4j
public class FaceConsumer {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private CqlSession cqlSession;
	private static final int FACE_BUFFER_SIZE = 1;
	private List<Face> faceList;
	
	@Autowired
    private SimpMessageSendingOperations messagingSendingOperations;

	public FaceConsumer() {
		CassandraUtils utils = new CassandraUtils();
		try {
			cqlSession = utils.getSession();
		} catch (Exception e) {
			log.error(e.toString());
		}
		faceList = new ArrayList<Face>();
	}

	@KafkaListener(topics = "SNAP_IMAGE_INFO_TOPIC", groupId = "face_cg")
	public void receive(String consumerMessage) {
		// log.info("Received message from kafka queue: {}", consumerMessage);
		if (consumerMessage != null) {
			Face jsonFace = parseFace(consumerMessage);
			log.info(jsonFace.toString());
			faceList.add(jsonFace);
			if (faceList.size() >= FACE_BUFFER_SIZE) {
				//faceList.add(jsonFace);
				List<Face> faceListCopy = new ArrayList<Face>(faceList);
				log.info("Face writer : Start to process " + faceList.size() + " records... " + new Date());
				FaceWriter facewriter = new FaceWriter(faceListCopy, cqlSession);
				facewriter.start();
				faceList.clear();
			}
		}

		messagingSendingOperations.convertAndSend("/topic/faces", consumerMessage);
	}

	/*
	 * Parse received face messages into Face object
	 * 
	 */
	private Face parseFace(String faceMessage) {
		//SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		ObjectMapper objectMapper = new ObjectMapper();
		Face face = null;
		try {
			JsonNode jsonNode = objectMapper.readTree(faceMessage);
			face = new Face();
			face.setTimestampVaDetected(ZonedDateTime.parse(jsonNode.get("dateTime").asText()));

			JsonNode captureLibResult = jsonNode.get("captureLibResult");
			JsonNode resultNode = captureLibResult.get(0);
			face.setFaceTrackId(resultNode.get("traceUuid").asText());
			face.setImageUrlFace(resultNode.get("image").asText());
			face.setCameraId(resultNode.get("targetAttrs").get("cameraIndexCode").asText());
			face.setRegionId(resultNode.get("targetAttrs").get("regionIndexCode").asText());
			face.setImageUrlScene(resultNode.get("targetAttrs").get("bkgUrl").asText());
			
			//face.setCameraName(resultNode.get("targetAttrs").get("regionIndexCode").asText());
			//To be decided whether to store below 3 attributes
			resultNode.get("targetAttrs").get("ruleId").asText();
			resultNode.get("targetAttrs").get("playType").asText();
			resultNode.get("targetAttrs").get("analysisType").asText();
			
			/*
			 * Parse face data
			 */
			JsonNode faceNode = resultNode.get("faces").get(0);
			if (faceNode != null) {
				face.setRectX((long) faceNode.get("faceRect").get("x").asDouble());
				face.setRectY((long) faceNode.get("faceRect").get("y").asDouble());
				face.setHeight((long) faceNode.get("faceRect").get("height").asDouble());
				face.setWidth((long) faceNode.get("faceRect").get("width").asDouble());

				face.setFaceAgeMin(faceNode.get("age").get("value").asInt() - faceNode.get("age").get("range").asInt());
				face.setFaceAgeMax(faceNode.get("age").get("value").asInt() + faceNode.get("age").get("range").asInt());
				faceNode.get("age").get("ageGroup").asText();
				
				String gender = faceNode.get("gender").get("value").asText();
				face.setGender(gender != null ? gender : "");
				face.setGenderConfidence((float) faceNode.get("gender").get("confidence").asDouble());

				faceNode.get("glass").get("value").asText();
				faceNode.get("glass").get("confidence").asDouble();
				
				faceNode.get("smile").get("value").asText();
				faceNode.get("smile").get("confidence").asDouble();
				
				faceNode.get("mask").get("value").asText();
				faceNode.get("mask").get("confidence").asDouble();
				
				// face.setFaceAgeConfidence((float)faceNode.get("age").get("confidence").asDouble());
				face.setModelData(faceNode.get("modelData").asText());

			}

		} catch (JsonMappingException jme) {
			log.error(jme.toString());
		} catch (JsonProcessingException jpe) {
			log.error(jpe.toString());
		} 

		return face;
	}
}