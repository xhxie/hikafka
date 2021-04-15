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
		faceRepo = new FaceRepository(session, "videolake2", "face");
		preparedStatement = faceRepo.prepareInsertStatement();
		this.noRec = noRec;
	}

	public FaceWriter(List<Face> faceList, CqlSession session) {
		faceRepo = new FaceRepository(session, "videolake2", "face");
		preparedStatement = faceRepo.prepareInsertStatement();
		this.faceList = faceList;
	}

	public void run() {
		//logger.info("Face writer : Start to process " + faceList.size() + " records... " + new Date());
		for (Face face : faceList) {		
			faceRepo.insert(preparedStatement, face.getFaceId(), UUID.randomUUID(), UUID.randomUUID(),
					UUID.randomUUID(), "Camera1", "JE Level 9", (float) 103.011, (float) 1.04356,
					ZonedDateTime.parse("2021-01-11T19:12:44.402+08:00"),
					ZonedDateTime.parse("2020-11-07T16:50:52.433395762Z"), face.getHeight(), face.getWidth(),
					face.getRectX(), face.getRectY(), face.getFaceTrackId(), face.getFaceConfidence(),
					face.getFaceAgeMin(), face.getFaceAgeMax(), face.getFaceAgeConfidence(), face.getGender(),
					face.getGenderConfidence(), face.getSkinColor(), face.getSkinColorConfidence(),
					face.getImageUrlScene(), face.getImageUrlScene(), face.getModelData());
		}

		/*
		 * for (int i=0; i<noRec; i++) { faceRepo.insert(preparedStatement,
		 * UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
		 * "Camera1", "JE Level 9", 103, 3,
		 * ZonedDateTime.parse("2021-01-11T19:12:44.402+08:00"),
		 * ZonedDateTime.parse("2020-11-07T16:50:52.433395762Z"), 49, 334, 75, 38,
		 * "99544B857E744E44A3D3A668FDC7BDD0", 0, 21, 34, 4, "M", 45, "Yellow", 44,
		 * "http://10.192.72.186:6120/pic?DE0900D6027064016130*hcs57302fa4e47344f882021/iot20/16397;161036306306349458?pic*161773*41439*5*DE0900D6027064016130-2*1610363433",
		 * "http://10.192.72.186:6120/pic?DE0900D6027064016130*hcs57302fa4e47344f882021/iot20/16397;161036306306349458?pic*161773*41439*5*DE0900D6027064016130-2*1610363433",
		 * "RlI1MDAwMDABAAAAAAAAACTV4+6UIya9Yz1PCariCfNPMtMZBQbPyaAB7H+ef8xAv5X//4CON9cqZOQqNwEY5QoFIH/2//UA9tLdSCYqcyMW6jfwRyE5+gQqOrYawT7rAx/dGMP1qynoaNjmx38Dwf+52WCAAgv7FhsUSinNyoTPEa8NAmsWIw38PfRi3vXsE1DD7w5/D+RN0L4rQF1vDtg2gB8tC5gGweIJ56PTEASpXtrYPTFRUnfDR/sqHsq0FMc+5fF/5JR/6CsH+DgY2WCxDaQoj2LFQX8LQbkqpKUW4SiZQe4SWRE3nqgTzv4RrSfDzAAwDWXK8ZvirV1O+wt/7ZwE5AfdKNxeJCdeSzw\\u003d"
		 * ); }
		 */
		logger.info("FaceWriter: Process " + faceList.size() + " records completed. " + new Date());
	}
}
