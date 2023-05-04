package com.wisedrive.dealerapp1.pojos.pojos;

public class pojo_static_image_data {
    String car_image_position_name;
    int car_image_position;

    public String getCar_image_position_name() {
        return car_image_position_name;
    }

    public void setCar_image_position_name(String car_image_position_name) {
        this.car_image_position_name = car_image_position_name;
    }

    public int getCar_image_position() {
        return car_image_position;
    }

    public void setCar_image_position(int car_image_position) {
        this.car_image_position = car_image_position;
    }

    public pojo_static_image_data(String car_image_position_name, int car_image_position) {
        this.car_image_position_name = car_image_position_name;
        this.car_image_position = car_image_position;
    }
}
