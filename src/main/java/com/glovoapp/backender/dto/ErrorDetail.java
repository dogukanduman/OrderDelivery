package com.glovoapp.backender.dto;

/**
 * Created by Dogukan Duman on 16.01.2019.
 */
public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private long timeStamp;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}