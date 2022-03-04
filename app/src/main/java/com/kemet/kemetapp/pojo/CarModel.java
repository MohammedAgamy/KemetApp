package com.kemet.kemetapp.pojo;

public class CarModel {


    private String Image;
    private String Model;
    private String Name;

    public CarModel(String image, String model, String name) {
         this.Image = image;
       this. Model = model;
      this.  Name = name;
    }

    public CarModel() {
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
       this. Image = image;
    }
    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
       this. Model = model;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
       this. Name = name;
    }
}
