package hexadots.in.piaggybankappfinal.bean;

import java.io.Serializable;

public class PiggyDetails implements Serializable {
    private String deviceId;
    private String deviceName;
    private Double goalAmount;
    private boolean goalCreated;
    private String goalCreatedDate;
    private String goalDate;
    private String goalName;
    private String goalStatus;
    private boolean piggyAttached;
    private String piggyLastConnectedDateAndTime;
    private String piggyName;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Double getGoalAmount() {
        return this.goalAmount;
    }

    public void setGoalAmount(Double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public boolean isGoalCreated() {
        return this.goalCreated;
    }

    public void setGoalCreated(boolean goalCreated) {
        this.goalCreated = goalCreated;
    }

    public String getGoalCreatedDate() {
        return this.goalCreatedDate;
    }

    public void setGoalCreatedDate(String goalCreatedDate) {
        this.goalCreatedDate = goalCreatedDate;
    }

    public String getGoalDate() {
        return this.goalDate;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }

    public String getGoalName() {
        return this.goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalStatus() {
        return this.goalStatus;
    }

    public void setGoalStatus(String goalStatus) {
        this.goalStatus = goalStatus;
    }

    public String getPiggyLastConnectedDateAndTime() {
        return this.piggyLastConnectedDateAndTime;
    }

    public void setPiggyLastConnectedDateAndTime(String piggyLastConnectedDateAndTime) {
        this.piggyLastConnectedDateAndTime = piggyLastConnectedDateAndTime;
    }

    public String getPiggyName() {
        return this.piggyName;
    }

    public void setPiggyName(String piggyName) {
        this.piggyName = piggyName;
    }

    public boolean isPiggyAttached() {
        return this.piggyAttached;
    }

    public void setPiggyAttached(boolean piggyAttached) {
        this.piggyAttached = piggyAttached;
    }
}
