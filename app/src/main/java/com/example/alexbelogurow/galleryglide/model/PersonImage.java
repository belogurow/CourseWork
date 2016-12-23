package com.example.alexbelogurow.galleryglide.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by alexbelogurow on 27.10.16.
 */

public class PersonImage implements Serializable {
    private String personName,
            personID,
            birthDate,
            imageDate,
            spin,
            tilt,
            sl,
            w,
            c;

    private Drawable drawable;
    private Integer imageID;

    @Override
    public String toString() {
        return "PersonImage{" +
                "personName='" + personName + '\'' +
                ", personID='" + personID + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", imageDate='" + imageDate + '\'' +
                ", spin='" + spin + '\'' +
                ", tilt='" + tilt + '\'' +
                ", sl='" + sl + '\'' +
                ", w='" + w + '\'' +
                ", c='" + c + '\'' +
                '}';
    }

    public PersonImage(String personName, String personID, String birthDate, String imageDate, String spin, String tilt, String sl, String w, String c, Integer imageID) {
        this.personName = personName;
        this.personID = personID;
        this.birthDate = birthDate;
        this.imageDate = imageDate;
        this.spin = spin;
        this.tilt = tilt;
        this.sl = sl;
        this.w = w;
        this.c = c;
        this.imageID = imageID;
    }

    public PersonImage(String personName, String personID, String birthDate, String imageDate, Integer imageID) {
        this.personName = personName;
        this.personID = personID;
        this.birthDate = birthDate;
        this.imageDate = imageDate;
        this.imageID = imageID;
        this.spin = "-90";
        this.tilt = "0";
        this.sl = "0.75";
        this.w = "350";
        this.c = "40";
        this.drawable = null;
    }

    public PersonImage(String personName, String personID, String birthDate, String imageDate, Drawable drawable) {
        this.personName = personName;
        this.personID = personID;
        this.birthDate = birthDate;
        this.imageDate = imageDate;
        this.drawable = drawable;
        this.spin = "-90";
        this.tilt = "0";
        this.sl = "0.75";
        this.w = "350";
        this.c = "40";
        this.imageID = null;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageDate() {
        return imageDate;
    }

    public void setImageDate(String imageDate) {
        this.imageDate = imageDate;
    }

    public String getSpin() {
        return spin;
    }

    public void setSpin(String spin) {
        this.spin = spin;
    }

    public String getTilt() {
        return tilt;
    }

    public void setTilt(String tilt) {
        this.tilt = tilt;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }
}

