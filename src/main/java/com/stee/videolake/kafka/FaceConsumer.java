package com.stee.videolake.kafka;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
@Slf4j
public class FaceConsumer {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private CqlSession cqlSession;
	private static final int FACE_BUFFER_SIZE = 500;
	private List<Face> faceList;

	public FaceConsumer() {
		CassandraUtils utils = new CassandraUtils();
		try {
			cqlSession = utils.getSession();
		} catch (Exception e) {
			log.error(e.toString());
		}
		faceList = new ArrayList<Face>();
	}

	@KafkaListener(topics = "faces", groupId = "face_cg")
	public void receive(String consumerMessage) {
		// log.info("Received message from kafka queue: {}", consumerMessage);
		if (consumerMessage != null) {
			Face jsonFace = parseFace(consumerMessage);
			// log.info(jsonFace.toString());

			if (faceList.size() < FACE_BUFFER_SIZE) {
				faceList.add(jsonFace);
			} else {
				faceList.add(jsonFace);
				List<Face> faceListCopy = new ArrayList<Face>(faceList);
				log.info("Face writer : Start to process " + faceList.size() + " records... " + new Date());
				FaceWriter facewriter = new FaceWriter(faceListCopy, cqlSession);
				facewriter.start();
				faceList.clear();
			}
		}

	}

	/*
	 * Parse received face messages into Face object
	 * 
	 */
	private Face parseFace(String faceMessage) {
		ObjectMapper objectMapper = new ObjectMapper();
		Face face = null;
		try {
			JsonNode jsonNode = objectMapper.readTree(faceMessage);
			face = new Face();
			face.setTimestampVaDetected(new Date());

			JsonNode captureLibResult = jsonNode.get("captureLibResult");
			JsonNode resultNode = captureLibResult.get(0);
			face.setFaceTrackId(resultNode.get("traceUuid").asText());

			face.setImageUrlScene(resultNode.get("image").asText());
			face.setCameraId(resultNode.get("targetAttrs").get("cameraIndexCode").asText());
			face.setCameraName(resultNode.get("targetAttrs").get("deviceName").asText());

			/*
			 * Parse face data
			 */
			JsonNode faceNode = resultNode.get("faces").get(0);
			if (faceNode != null) {
				String gender = faceNode.get("gender").get("value").asText();
				face.setGender(gender != null ? gender : "");
				face.setGenderConfidence((float) faceNode.get("gender").get("confidence").asDouble());

				face.setRectX((long) faceNode.get("faceRect").get("x").asDouble());
				face.setRectY((long) faceNode.get("faceRect").get("y").asDouble());
				face.setHeight((long) faceNode.get("faceRect").get("height").asDouble());
				face.setWidth((long) faceNode.get("faceRect").get("width").asDouble());

				face.setFaceAgeMin(faceNode.get("age").get("value").asInt() - faceNode.get("age").get("range").asInt());
				face.setFaceAgeMax(faceNode.get("age").get("value").asInt() + faceNode.get("age").get("range").asInt());
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