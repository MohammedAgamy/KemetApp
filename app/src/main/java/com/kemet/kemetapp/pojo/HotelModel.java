package com.kemet.kemetapp.pojo;

public class HotelModel {
    private String name;
    private String image;
    private String rite;

    public HotelModel(String name, String image, String rite) {
        this.name = name;
        this.image = image;
        this.rite = rite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRite() {
        return rite;
    }

    public void setRite(String rite) {
        this.rite = rite;
    }
}
