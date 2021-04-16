package com.stee.videolake.face.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

public class Face {
	private UUID faceId;
	private UUID vaEngineId;
	private String cameraId;
	private String lensId;
	private String cameraName;
	private String regionId;

	private ZonedDateTime timestampVapReceived;
	private ZonedDateTime timestampVaDetected;

	private String faceTrackId;
	private float faceConfidence;
	private int faceAgeMin;
	private int faceAgeMax;
	private float faceAgeConfidence;
	private String gender;
	private float genderConfidence;
	private String skinColor;
	private float skinColorConfidence;
	private float rectX;
	private float rectY;
	private float height;
	private float width;
	
	private String imageUrlScene;
	private String imageUrlFace;
	private String modelData;
	

	public Face() {
		faceId = UUID.randomUUID();
		vaEngineId = UUID.randomUUID();
		lensId = UUID.randomUUID().toString();
	}
	public UUID getFaceId() {
		return faceId;
	}

	public void setFaceId(UUID faceId) {
		this.faceId = faceId;
	}

	public UUID getVaEngineId() {
		return vaEngineId;
	}

	public void setVaEngineId(UUID vaEngineId) {
		this.vaEngineId = vaEngineId;
	}

	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public void setLensId(String lensId) {
		this.lensId = lensId;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public ZonedDateTime getTimestampVapReceived() {
		return timestampVapReceived;
	}

	public void setTimestampVapReceived(ZonedDateTime timestampVapReceived) {
		this.timestampVapReceived = timestampVapReceived;
	}

	public ZonedDateTime getTimestampVaDetected() {
		return timestampVaDetected;
	}

	public void setTimestampVaDetected(ZonedDateTime timestampVaDetected) {
		this.timestampVaDetected = timestampVaDetected;
	}

	public String getFaceTrackId() {
		return faceTrackId;
	}

	public void setFaceTrackId(String faceTrackId) {
		this.faceTrackId = faceTrackId;
	}

	public float getFaceConfidence() {
		return faceConfidence;
	}

	public void setFaceConfidence(float faceConfidence) {
		this.faceConfidence = faceConfidence;
	}

	public int getFaceAgeMin() {
		return faceAgeMin;
	}

	public void setFaceAgeMin(int faceAgeMin) {
		this.faceAgeMin = faceAgeMin;
	}

	public int getFaceAgeMax() {
		return faceAgeMax;
	}

	public void setFaceAgeMax(int faceAgeMax) {
		this.faceAgeMax = faceAgeMax;
	}

	public float getFaceAgeConfidence() {
		return faceAgeConfidence;
	}

	public void setFaceAgeConfidence(float faceAgeConfidence) {
		this.faceAgeConfidence = faceAgeConfidence;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public float getGenderConfidence() {
		return genderConfidence;
	}

	public void setGenderConfidence(float genderConfidence) {
		this.genderConfidence = genderConfidence;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
	}

	public float getSkinColorConfidence() {
		return skinColorConfidence;
	}

	public void setSkinColorConfidence(float skinColorConfidence) {
		this.skinColorConfidence = skinColorConfidence;
	}

	public float getRectX() {
		return rectX;
	}
	public void setRectX(float rectX) {
		this.rectX = rectX;
	}
	public float getRectY() {
		return rectY;
	}
	public void setRectY(float rectY) {
		this.rectY = rectY;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public String getImageUrlScene() {
		return imageUrlScene;
	}

	public void setImageUrlScene(String imageUrlScene) {
		this.imageUrlScene = imageUrlScene;
	}

	public String getImageUrlFace() {
		return imageUrlFace;
	}

	public void setImageUrlFace(String imageUrlFace) {
		this.imageUrlFace = imageUrlFace;
	}

	public String getModelData() {
		return modelData;
	}

	public void setModelData(String modelData) {
		this.modelData = modelData;
	}

	@Override
	public String toString() {
		return "Face [faceId=" + faceId + ", vaEngineId=" + vaEngineId + ", cameraId=" + cameraId + ", lensId=" + lensId
				+ ", cameraName=" + cameraName + ", timestampVapReceived=" + timestampVapReceived
				+ ", timestampVaDetected=" + timestampVaDetected + ", faceTrackId=" + faceTrackId + ", faceConfidence="
				+ faceConfidence + ", faceAgeMin=" + faceAgeMin + ", faceAgeMax=" + faceAgeMax + ", faceAgeConfidence="
				+ faceAgeConfidence + ", gender=" + gender + ", genderConfidence=" + genderConfidence + ", skinColor="
				+ skinColor + ", skinColorConfidence=" + skinColorConfidence + ", imageUrlScene=" + imageUrlScene
				+ ", imageUrlFace=" + imageUrlFace + ", modelData=" + modelData + "]";
	}
}
