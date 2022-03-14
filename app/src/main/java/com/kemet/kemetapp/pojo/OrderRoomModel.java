package com.kemet.kemetapp.pojo;

public class OrderRoomModel {
    private String name ;
    private String nationality ;
    private String startDate ;
    private String endDate ;

    public OrderRoomModel(String name, String nationality, String startDate, String endDate) {
        this.name = name;
        this.nationality = nationality;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
