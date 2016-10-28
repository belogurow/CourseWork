package com.example.alexbelogurow.galleryglide.model;

import java.io.Serializable;

/**
 * Created by alexbelogurow on 27.10.16.
 */

public class PersonImage implements Serializable{
    private String name;
    private String context;
    private Integer imageID;
    private boolean selected;


    public PersonImage(String name, String context, Integer imageID) {
        this.name = name;
        this.context = context;
        this.imageID = imageID;
        this.selected = false;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }
}
