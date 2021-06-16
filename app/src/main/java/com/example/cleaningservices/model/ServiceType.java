package com.example.cleaningservices.model;

public class ServiceType {

    private int imageResource;
    private String service;
    private int colorResource;

    public ServiceType(int imageResource, String service, int colorResource) {
        this.imageResource = imageResource;
        this.service = service;
        this.colorResource = colorResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

}
