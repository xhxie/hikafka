package com.stee.videolake.face.model.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.LocalDate;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;

public class FaceRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceRepository.class);
	private CqlSession session;
	private String keyspace;
	private String table;

	public FaceRepository(CqlSession session, String keyspace, String table) {
		this.session = session;
		this.keyspace = keyspace;
		this.table = table;
	}

	/**
	 * Insert a row into face table
	 *
	 * @param id
	 * @param
	 * @param
	 */
	public void insert(String preparedStatement, UUID vaEngineId, UUID cameraId, UUID lensId, String camera_name,
			String cameraAddress, double cameraLat, double cameraLng, ZonedDateTime timestampVaDetected,
			ZonedDateTime timestampVapReceived, float height, float width, float x, float y, String faceTrackingId,
			float faceConfidence, int faceAgeMin, int faceAgeMax, float faceAgeConfidence, String face_gender,
			float faceGenderConfidence, String faceSkinColor, float faceSkinColorConfidence, String imageUrlScene,
			String imageUrlFace, String modelData) {
		PreparedStatement prepared = session.prepare(preparedStatement);
		BoundStatement bound = prepared.bind(vaEngineId, cameraId, lensId, camera_name, cameraAddress, cameraLat,
				cameraLng, timestampVaDetected, timestampVapReceived, height, width, x, y, faceTrackingId,
				faceConfidence, faceAgeMin, faceAgeMax, faceAgeConfidence, face_gender, faceGenderConfidence,
				faceSkinColor, faceSkinColorConfidence, imageUrlScene, imageUrlFace, modelData).setIdempotent(true);
		session.execute(bound);
	}

	/**
	 * Insert a row into face table for load test
	 *
	 * @param id
	 * @param
	 * @param
	 */
	public void insertFace(String preparedStatement, UUID vapEventId, String vaEngineEventId, UUID tenantIdRef,
			UUID vaEngineIdRef, String cameraId, UUID lensId, String tenantName, String vaEngineName, String camera_name,
			String cameraAddress, double cameraLat, double cameraLng, ZonedDateTime timestampVaDetected,
			ZonedDateTime timestampVapReceived, float height, float width, float x, float y, String faceTrackingId,
			float faceConfidence, int faceAgeMin, int faceAgeMax, float faceAgeConfidence, String face_gender,
			float faceGenderConfidence, String faceSkinColor, float faceSkinColorConfidence, String hasScar,
			String hasSideburn, String hasMoustache, String hasBeard, String imageUrlScene, String imageUrlFace,
			String modelData, int timeInVideo) {
		PreparedStatement prepared = session.prepare(preparedStatement);
		BoundStatement bound = prepared.bind(vapEventId, vaEngineEventId, tenantIdRef, vaEngineIdRef, cameraId, lensId,
				tenantName, vaEngineName, camera_name, cameraAddress, cameraLat, cameraLng, timestampVaDetected,
				timestampVapReceived, height, width, x, y, faceTrackingId, faceConfidence, faceAgeMin, faceAgeMax,
				faceAgeConfidence, face_gender, faceGenderConfidence, faceSkinColor, faceSkinColorConfidence, hasScar,
				hasSideburn, hasMoustache, hasBeard, imageUrlScene, imageUrlFace, modelData, timeInVideo)
				.setIdempotent(true);
		session.execute(bound);
	}

	/**
	 * Create a PrepareStatement to insert a row to face table
	 *
	 * @return PreparedStatement
	 */
	public String prepareInsertStatementForLoadTest() {
		final String insertStatement = "INSERT INTO " + keyspace + "." + "face"
				+ " (id_event_vap, id_event_va_engine, id_tenant_ref, id_va_engine_ref, id_camera_ref, id_lens_ref, tenant_name, va_engine_name, "
				+ "camera_name,camera_address,camera_lat,camera_lng, "
				+ "timestamp_vap_detected, timestamp_va_detected, detection_rect_height_percent, detection_rect_width_percent, "
				+ "detection_rect_x_percent, detection_rect_y_percent, face_tracking_id, face_confidence, face_age_min,face_age_max, face_age_confidence,"
				+ "face_gender, face_gender_confidence, face_skin_color_value, face_skin_color_confidence, "
				+ "has_scars, has_sideburns, has_moustache, has_beard,"
				+ "image_url_scene, image_url_face, model_data,time_in_video) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		return insertStatement;
	}

	/**
	 * Create a PrepareStatement to insert a row to face table
	 *
	 * @return PreparedStatement
	 */
	public String prepareInsertStatement() {
		final String insertStatement = "INSERT INTO " + keyspace + "." + table
				+ " (id_va_engine_ref, id_camera_ref, id_lens_ref, camera_name,camera_address,camera_lat,camera_lng, "
				+ "timestamp_va_detected, timestamp_vap_detected, detection_rect_height_percent, detection_rect_width_percent, "
				+ "detection_rect_x_percent, detection_rect_y_percent, face_tracking_id, face_confidence, face_age_min,face_age_max, face_age_confidence,"
				+ "face_gender, face_gender_confidence, face_skin_color_value, face_skin_color_confidence, "
				+ "image_url_scene, image_url_face, model_data) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		return insertStatement;
	}

	/**
	 * Select a row from user table
	 *
	 * @param id user_id
	 */
	public void selectFace(int id) {
		final String query = "SELECT * FROM " + keyspace + "." + table + " where face_id = 3";
		Row row = session.execute(query).one();

		LOGGER.info("Obtained row: {} | {} | {} ", row.getInt("user_id"), row.getString("user_name"),
				row.getString("user_bcity"));
	}
}
