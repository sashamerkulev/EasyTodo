package ru.merkulyevsasha.core.domain;



import java.io.Serializable;

public class TaskModel implements Serializable {

    public static final int EMPTY_ID = -1;

    public static final int STATUS_CREATED = 0;
    public static final int STATUS_INPROGRESS = 1;
    public static final int STATUS_SUSPENDED = 2;
    public static final int STATUS_DONE = 3;

    private long id;
    private String shortName;
    private String shortDescription;
    private int status;
    private String statusName;
    private long createdAt;
    private long expiryAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(long expiryAt) {
        this.expiryAt = expiryAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
