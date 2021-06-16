package com.stee.videolake.face.model.db;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.stee.videolake.face.model.Face;

public class FaceWriter extends Thread {
	private int noRec;
	private FaceRepository faceRepo;
	private String preparedStatement;
	private List<Face> faceList = new ArrayList<Face>();

	private final static Logger logger = LoggerFactory.getLogger("FaceWriter");

	public FaceWriter(int noRec, CqlSession session) {
		faceRepo = new FaceRepository(session, "videolake2", "laapface");
		preparedStatement = faceRepo.prepareInsertStatement();
		this.noRec = noRec;
	}

	public FaceWriter(List<Face> faceList, CqlSession session) {
		faceRepo = new FaceRepository(session, "videolake2", "laapface");
		preparedStatement = faceRepo.prepareInsertStatement();
		this.faceList = faceList;
	}

	public void run() {
		// logger.info("Face writer : Start to process " + faceList.size() + "
		// records... " + new Date());
		for (Face face : faceList) {
			faceRepo.insert(preparedStatement, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Camera1",
					"JE Level 9", (float) 103.011, (float) 1.04356, face.getTimestampVaDetected(),
					ZonedDateTime.now(), face.getHeight(), face.getWidth(), face.getRectX(), face.getRectY(),
					face.getFaceTrackId(), face.getFaceConfidence(), face.getFaceAgeMin(), face.getFaceAgeMax(),
					face.getFaceAgeConfidence(), face.getGender(), face.getGenderConfidence(), face.getSkinColor(),
					face.getSkinColorConfidence(), face.getImageUrlScene(), face.getImageUrlScene(),
					face.getModelData());
		}

		logger.info("FaceWriter: Process " + faceList.size() + " records completed. " + new Date());
	}
}
