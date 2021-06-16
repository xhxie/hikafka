package com.stee.videolake.face.model;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;

public class Face2 {
	private UUID vapEventId;
	private String vaEngineEventId;
	private UUID tenantIdRef;
	private UUID vaEngineId;
	private String cameraId;
	private UUID lensId;
	private String tenantName;
	private String vaEngineName;
	private String cameraName;
	private String cameraAddress;	
	private Double cameraLat;
	private Double cameraLng;
	private ZonedDateTime timestampVapReceived;
	private ZonedDateTime timestampVaDetected;
	private float rectX;
	private float rectY;
	private float height;
	private float width;
	
	private String faceTrackId;
	private float faceConfidence;
	private int faceAgeMin;
	private int faceAgeMax;
	private float faceAgeConfidence;
	private String gender;
	private float genderConfidence;
	private String skinColor;
	private float skinColorConfidence;
	private String hasScars;
	private String hasSideburns;
	private String hasMoustache;
	private String hasBeard;
	
	private String imageUrlScene;
	private String imageUrlFace;
	private String modelData;
	private int timeInVideo;


	private static UUID[] tenantIds = new UUID[] { UUID.fromString("a3527c8d-a4b9-4c9e-b8d6-4a9b31225529"),
			UUID.fromString("e5942736-e47d-4720-a4bb-a32dc92458bd"),
			UUID.fromString("d03c8221-c506-4d3e-9ea0-c48f3e2657af") };
	private static String tenantNames[] = new String[] { "SPF", "ICA", "SCDF" };
	private static Random rand = new Random();
	private static short noOfTenant = 3;
	
	public Face2() {
		vapEventId = UUID.randomUUID();
		vaEngineId = UUID.fromString("d04dfee9-2a0a-41c6-9893-8f471bdaac68");
		vaEngineName = "STEE-FACE-VA";
		
		int idx = rand.nextInt(noOfTenant);
		tenantIdRef = tenantIds[idx];
		tenantName = tenantNames[idx];
		lensId = UUID.randomUUID();
		timeInVideo = 0;
		
		hasScars = rand.nextInt(2) == 0 ? "N" : "Y";
		hasSideburns = rand.nextInt(2) == 0 ? "N" : "Y";
		hasMoustache = rand.nextInt(2) == 0 ? "N" : "Y";
		hasBeard = rand.nextInt(2) == 0 ? "N" : "Y";
	}
	public UUID getVapEventId() {
		return vapEventId;
	}

	public void setVapEventId(UUID vapEventId) {
		this.vapEventId = vapEventId;
	}

	public String getVaEngineEventId() {
		return vaEngineEventId;
	}

	public void setVaEngineEventId(String vaEngineEventId) {
		this.vaEngineEventId = vaEngineEventId;
	}

	public UUID getTenantIdRef() {
		return tenantIdRef;
	}

	public void setTenantIdRef(UUID tenantIdRef) {
		this.tenantIdRef = tenantIdRef;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public UUID getLensId() {
		return lensId;
	}

	public void setLensId(UUID lensId) {
		this.lensId = lensId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getVaEngineName() {
		return vaEngineName;
	}

	public void setVaEngineName(String vaEngineName) {
		this.vaEngineName = vaEngineName;
	}

	public String getCameraAddress() {
		return cameraAddress;
	}

	public void setCameraAddress(String cameraAddress) {
		this.cameraAddress = cameraAddress;
	}

	public Double getCameraLat() {
		return cameraLat;
	}

	public void setCameraLat(Double cameraLat) {
		this.cameraLat = cameraLat;
	}

	public Double getCameraLng() {
		return cameraLng;
	}

	public void setCameraLng(Double cameraLng) {
		this.cameraLng = cameraLng;
	}

	public String getHasScars() {
		return hasScars;
	}

	public void setHasScars(String hasScars) {
		this.hasScars = hasScars;
	}

	public String getHasSideburns() {
		return hasSideburns;
	}

	public void setHasSideburns(String hasSideburns) {
		this.hasSideburns = hasSideburns;
	}

	public String getHasMoustache() {
		return hasMoustache;
	}

	public void setHasMoustache(String hasMoustache) {
		this.hasMoustache = hasMoustache;
	}

	public String getHasBeard() {
		return hasBeard;
	}

	public void setHasBeard(String hasBeard) {
		this.hasBeard = hasBeard;
	}

	public int getTimeInVideo() {
		return timeInVideo;
	}

	public void setTimeInVideo(int timeInVideo) {
		this.timeInVideo = timeInVideo;
	}


	public UUID getVaEngineId() {
		return vaEngineId;
	}

	public void setVaEngineId(UUID vaEngineId) {
		this.vaEngineId = vaEngineId;
	}

	public String getRegionId() {
		return cameraAddress;
	}
	public void setRegionId(String regionId) {
		this.cameraAddress = regionId;
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
		return "Face [faceId=" + vapEventId + ", vaEngineId=" + vaEngineId + ", cameraId=" + cameraId + ", lensId=" + lensId
				+ ", cameraName=" + cameraName + ", timestampVapReceived=" + timestampVapReceived
				+ ", timestampVaDetected=" + timestampVaDetected + ", faceTrackId=" + faceTrackId + ", faceConfidence="
				+ faceConfidence + ", faceAgeMin=" + faceAgeMin + ", faceAgeMax=" + faceAgeMax + ", faceAgeConfidence="
				+ faceAgeConfidence + ", gender=" + gender + ", genderConfidence=" + genderConfidence + ", skinColor="
				+ skinColor + ", skinColorConfidence=" + skinColorConfidence + ", imageUrlScene=" + imageUrlScene
				+ ", imageUrlFace=" + imageUrlFace + ", modelData=" + modelData + "]";
	}
}
