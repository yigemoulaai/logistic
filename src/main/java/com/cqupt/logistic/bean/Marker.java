package com.cqupt.logistic.bean;

import javax.persistence.*;
/**
*  货仓标记点
* */
@Entity(name="marker")
public class Marker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Column(length = 50)
    private  String site;
    @Column(length = 50)
    private  String longitude;
    @Column(length = 50)
    private  String  Latitude;
    @Column(length = 50)
    private  String comment;

    public Marker(int id, String site, String longitude, String latitude, String comment) {
        this.id = id;
        this.site = site;
        this.longitude = longitude;
        Latitude = latitude;
        this.comment = comment;
    }

    public Marker() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
