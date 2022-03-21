package com.kemet.kemetapp.pojo;

public class OrderRoomModel {
    private String name ;
    private String nationality ;
    private String startDate ;
    private String endDate ;
    private String immagePass ;
    private String location ;

    public OrderRoomModel(String name, String nationality, String startDate, String endDate, String immagePass, String location) {
        this.name = name;
        this.nationality = nationality;
        this.startDate = startDate;
        this.endDate = endDate;
        this.immagePass = immagePass;
        this.location = location;
    }

    public OrderRoomModel(String name, String nationality, String startDate, String endDate, String immagePass) {
        this.name = name;
        this.nationality = nationality;
        this.startDate = startDate;
        this.endDate = endDate;
        this.immagePass = immagePass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImmagePass() {
        return immagePass;
    }

    public void setImmagePass(String immagePass) {
        this.immagePass = immagePass;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
