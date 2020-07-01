package entity;

public class JsonResult {
    private String deviceId;
    private int code;
    private float score;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "JsonResult{" +
                "deviceId='" + deviceId + '\'' +
                ", code=" + code +
                ", score=" + score +
                '}';
    }

}
