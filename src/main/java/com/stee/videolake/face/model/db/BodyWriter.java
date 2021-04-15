package com.stee.videolake.face.model.db;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;

public class BodyWriter extends Thread {
	private final static Logger logger = LoggerFactory.getLogger("BodyWriter");
	private int noRec;
	private BodyRepository bodyRepo;
	private String preparedStatement;
	
	public BodyWriter(int noRec, CqlSession session) {
		bodyRepo = new BodyRepository(session,"videolake2","body");
		preparedStatement = bodyRepo.prepareInsertStatement();
		this.noRec = noRec;
	}
    public void run(){
    	logger.info("BodyWriter: Start to process " + noRec + " records... " + new Date ());
        for (int i=0; i<noRec; i++) {
        	bodyRepo.insert(preparedStatement, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
        			"Camera1", "JE Level 9", 103, 3, ZonedDateTime.parse("2021-01-11T19:12:44.402+08:00"),
        			ZonedDateTime.parse("2020-11-07T16:50:52.433395762Z"), 49, 334, 75, 38,
        			"99544B857E744E44A3D3A668FDC7BDD0", 0, 21, 34, 4, "M", 45, "Yellow", 44,
        			"http://10.192.72.186:6120/pic?DE0900D6027064016130*hcs57302fa4e47344f882021/iot20/16397;161036306306349458?pic*161773*41439*5*DE0900D6027064016130-2*1610363433",
        			"http://10.192.72.186:6120/pic?DE0900D6027064016130*hcs57302fa4e47344f882021/iot20/16397;161036306306349458?pic*161773*41439*5*DE0900D6027064016130-2*1610363433",
        			"RlI1MDAwMDABAAAAAAAAACTV4+6UIya9Yz1PCariCfNPMtMZBQbPyaAB7H+ef8xAv5X//4CON9cqZOQqNwEY5QoFIH/2//UA9tLdSCYqcyMW6jfwRyE5+gQqOrYawT7rAx/dGMP1qynoaNjmx38Dwf+52WCAAgv7FhsUSinNyoTPEa8NAmsWIw38PfRi3vXsE1DD7w5/D+RN0L4rQF1vDtg2gB8tC5gGweIJ56PTEASpXtrYPTFRUnfDR/sqHsq0FMc+5fF/5JR/6CsH+DgY2WCxDaQoj2LFQX8LQbkqpKUW4SiZQe4SWRE3nqgTzv4RrSfDzAAwDWXK8ZvirV1O+wt/7ZwE5AfdKNxeJCdeSzw\\u003d");
        }
        logger.info("BodyWriter: Process " + noRec + " records completed. " + new Date());
     }
}
