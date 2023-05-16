package com.wisedrive.dealerapp1.pojos.pojos;

import android.graphics.Bitmap;

public class pojo_static_image_data {
    String car_image_position_name;
    String car_image_position_path;

    public pojo_static_image_data(String car_image_position_name, String car_image_position_path) {
        this.car_image_position_name = car_image_position_name;
        this.car_image_position_path = car_image_position_path;
    }

    public String getCar_image_position_name() {
        return car_image_position_name;
    }

    public void setCar_image_position_name(String car_image_position_name) {
        this.car_image_position_name = car_image_position_name;
    }

    public String getCar_image_position_path() {
        return car_image_position_path;
    }

    public void setCar_image_position_path(String car_image_position_path) {
        this.car_image_position_path = car_image_position_path;
    }
}