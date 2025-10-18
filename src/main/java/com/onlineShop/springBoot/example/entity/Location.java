package com.onlineShop.springBoot.example.entity;

import java.time.LocalTime;

public class Location {
    private Long id;
    private String title;
    private String type;
    private LocalTime open_time;

    public Location(Long id, String title, String type, LocalTime open_time) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.open_time = open_time;
    }

    public Location() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalTime getOpen_time() {
        return open_time;
    }

    public void setOpen_time(LocalTime open_time) {
        this.open_time = open_time;
    }
}
