package com.stee.videolake.face.model.db;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.stee.videolake.face.model.Face2;

public class FaceWriterForLoadTest extends Thread {
	private int noRec;
	private FaceRepository faceRepo;
	private String preparedStatement;
	private List<Face2> faceList = new ArrayList<Face2>();


	private final static Logger logger = LoggerFactory.getLogger("FaceWriterForLoadTest");

	public FaceWriterForLoadTest(int noRec, CqlSession session) {
		faceRepo = new FaceRepository(session, "videolake2", "face");
		preparedStatement = faceRepo.prepareInsertStatement();
		this.noRec = noRec;
	}

	public FaceWriterForLoadTest(List<Face2> faceList, CqlSession session) {
		faceRepo = new FaceRepository(session, "videolake2", "face");
		preparedStatement = faceRepo.prepareInsertStatementForLoadTest();
		this.faceList = faceList;
	}

	public void run() {
		//logger.info("Face writer : Start to process " + faceList.size() + "records... " + new Date());
		for (Face2 face : faceList) {
			faceRepo.insertFace(preparedStatement, face.getVapEventId(), face.getVaEngineEventId(),
					face.getTenantIdRef(), face.getVaEngineId(), face.getCameraId(), face.getLensId(),
					face.getTenantName(), face.getVaEngineName(), face.getCameraName(), "JE Level 9", (float) 103.011,
					(float) 1.04356, ZonedDateTime.now(), ZonedDateTime.now(), /*face.getTimestampVaDetected(),*/ face.getHeight(),
					face.getWidth(), face.getRectX(), face.getRectY(), face.getFaceTrackId(), face.getFaceConfidence(),
					face.getFaceAgeMin(), face.getFaceAgeMax(), face.getFaceAgeConfidence(), face.getGender(),
					face.getGenderConfidence(), face.getSkinColor(), face.getSkinColorConfidence(),
					face.getHasScars(), face.getHasSideburns(),face.getHasMoustache(),face.getHasBeard(),
					face.getImageUrlScene(),face.getImageUrlScene(), face.getModelData(), face.getTimeInVideo());
		}

		logger.info("FaceWriter: Process " + faceList.size() + " records completed. " + new Date());
	}
}
