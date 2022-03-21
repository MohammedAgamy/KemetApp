package com.kemet.kemetapp.pojo;

public class UserInfoModel {
    private String name ;
    private String phone ;
    private String male;
    private String female ;
    private String photo ;


    public UserInfoModel(String name, String phone, String male, String female, String photo) {
        this.name = name;
        this.phone = phone;
        this.male = male;
        this.female = female;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
