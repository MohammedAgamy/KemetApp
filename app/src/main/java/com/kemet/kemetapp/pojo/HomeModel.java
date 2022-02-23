package com.kemet.kemetapp.pojo;

import java.net.URL;

public class HomeModel {

     private String name ;
     private String image ;

    public HomeModel(String name, String image)
    {
        this.name = name;
        this.image = image;
    }

    public HomeModel() {

    }


    public String getName()

    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
}
