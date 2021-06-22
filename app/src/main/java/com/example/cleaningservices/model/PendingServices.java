package com.example.cleaningservices.model;

public class PendingServices {

    private String id;
    private Long time;
    private String money;

    public PendingServices(String id, Long time, String money) {
        this.id = id;
        this.time = time;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
