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
import com.stee.videolake.face.model.Face2;
import com.stee.videolake.face.model.db.FaceWriter;
import com.stee.videolake.face.model.db.FaceWriterForLoadTest;
import com.stee.videolake.util.CassandraUtils;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@Service
@Slf4j
public class FaceConsumerForLoadTest {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private CqlSession cqlSession;
	private static final int FACE_BUFFER_SIZE = 500;
	private List<Face2> faceList;
	
	@Autowired
    private SimpMessageSendingOperations messagingSendingOperations;

	public FaceConsumerForLoadTest() {
		CassandraUtils utils = new CassandraUtils();
		try {
			cqlSession = utils.getSession();
		} catch (Exception e) {
			log.error(e.toString());
		}
		faceList = new ArrayList<Face2>();
	}

	@KafkaListener(topics = "faces", groupId = "face_cg")
	public void receive(String consumerMessage) {
		//System.out.println("Receive message!");
		if (consumerMessage != null) {
			Face2 jsonFace = parseFace(consumerMessage);
			//log.info(jsonFace.toString());
			faceList.add(jsonFace);
			if (faceList.size() >= FACE_BUFFER_SIZE) {
				List<Face2> faceListCopy = new ArrayList<Face2>(faceList);
				log.info("Face writer : Start to process " + faceList.size() + " records... " + new Date());
				FaceWriterForLoadTest facewriter = new FaceWriterForLoadTest(faceListCopy, cqlSession);
				facewriter.start();
				faceList.clear();
			}
		}

		//messagingSendingOperations.convertAndSend("/topic/faces", consumerMessage);
	}

	/*
	 * Parse received face messages into Face object
	 * 
	 */
	private Face2 parseFace(String faceMessage) {

		ObjectMapper objectMapper = new ObjectMapper();
		Face2 face = null;
		try {
			JsonNode jsonNode = objectMapper.readTree(faceMessage);
			face = new Face2();
			face.setTimestampVaDetected(ZonedDateTime.parse(jsonNode.get("dateTime").asText()));
			face.setVaEngineEventId(jsonNode.get("uuid").asText());

			JsonNode captureLibResult = jsonNode.get("captureLibResult");
			JsonNode resultNode = captureLibResult.get(0);
			face.setFaceTrackId(resultNode.get("traceUuid").asText());
			face.setImageUrlFace(resultNode.get("image").asText());
			face.setCameraId(resultNode.get("targetAttrs").get("cameraIndexCode").asText());
			face.setCameraName(resultNode.get("targetAttrs").get("deviceName").asText());
			face.setImageUrlScene(resultNode.get("targetAttrs").get("bkgUrl").asText());
			
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
				face.setModelData(faceNode.get("modelData").asText());
			}
			//System.out.println("face is processed!");

		} catch (JsonMappingException jme) {
			log.error(jme.toString());
		} catch (JsonProcessingException jpe) {
			log.error(jpe.toString());
		} 

		return face;
	}
}