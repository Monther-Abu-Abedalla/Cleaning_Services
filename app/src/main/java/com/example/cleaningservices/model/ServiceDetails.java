package com.example.cleaningservices.model;

public class ServiceDetails {

    private String imageResource;
    private String serviceId;
    private String serviceDetailsId;
    private String serviceDetails;
    private Boolean isChecked = false;

    public ServiceDetails(String serviceDetailsId, String serviceId, String serviceDetails, String imageResource) {
        this.serviceDetailsId = serviceDetailsId;
        this.serviceId = serviceId;
        this.serviceDetails = serviceDetails;
        this.imageResource = imageResource;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public String getService() {
        return serviceDetails;
    }

    public void setService(String service) {
        this.serviceDetails = service;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public String getServiceDetailsId() {
        return serviceDetailsId;
    }

    public void setServiceDetailsId(String serviceDetailsId) {
        this.serviceDetailsId = serviceDetailsId;
    }
}
