package hexadots.in.piaggybankappfinal.bean;

public class PiggyStatusDetails {
    private String connectedUserId;
    private boolean isPiggyConnected;
    private String linkedAccountId;
    private String piggyId;
    private String statusUpdatedOn;

    public String getConnectedUserId() {
        return this.connectedUserId;
    }

    public void setConnectedUserId(String connectedUserId) {
        this.connectedUserId = connectedUserId;
    }

    public boolean isPiggyConnected() {
        return this.isPiggyConnected;
    }

    public void setPiggyConnected(boolean piggyConnected) {
        this.isPiggyConnected = piggyConnected;
    }

    public String getLinkedAccountId() {
        return this.linkedAccountId;
    }

    public void setLinkedAccountId(String linkedAccountId) {
        this.linkedAccountId = linkedAccountId;
    }

    public String getPiggyId() {
        return this.piggyId;
    }

    public void setPiggyId(String piggyId) {
        this.piggyId = piggyId;
    }

    public String getStatusUpdatedOn() {
        return this.statusUpdatedOn;
    }

    public void setStatusUpdatedOn(String statusUpdatedOn) {
        this.statusUpdatedOn = statusUpdatedOn;
    }
}
