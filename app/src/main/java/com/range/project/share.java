package com.range.project;

/**
 * Created by Jayaraman on 12-03-2018.
 */

public class share {
    public double latitude;
    public double longitude;
    public String username;
    public String message;
    public String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public share(double latitude, double longitude, String username, String message, String pid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
        this.message = message;
        this.pid = pid;
    }

    public share() {
    }

    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
