package com.example.student.readyapp;

import java.util.Date;

public class Detection {

    private int id;
    private String detectionTime;
    private double height;
    private double distance;
    private boolean isOurs;
    private boolean isShotDown;

    public Detection(int id, String detectionTime, double height, double distance, boolean isOurs, boolean isShotDown) {
        this.id = id;
        this.detectionTime = detectionTime;
        this.height = height;
        this.distance = distance;
        this.isOurs = isOurs;
        this.isShotDown = isShotDown;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetectionTime() {
        return this.detectionTime;
    }

    public void setDetectionTime(String detectionTime) {
        this.detectionTime = detectionTime;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isOurs() {
        return this.isOurs;
    }

    public void setOurs(boolean ours) {
        this.isOurs = ours;
    }

    public boolean isShotDown() {
        return this.isShotDown;
    }

    public void setShotDown(boolean shotDown) {
        this.isShotDown = shotDown;
    }
}