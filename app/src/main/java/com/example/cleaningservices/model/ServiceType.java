package com.example.cleaningservices.model;

public class ServiceType {

    private String imageResource;
    private String serviceName;
    private String id;
    private Boolean isChecked = false;

    public ServiceType(String id, String serviceName, String imageResource) {
        this.imageResource = imageResource;
        this.serviceName = serviceName;
        this.id = id;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getService() {
        return serviceName;
    }

    public void setService(String service) {
        this.serviceName = service;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
