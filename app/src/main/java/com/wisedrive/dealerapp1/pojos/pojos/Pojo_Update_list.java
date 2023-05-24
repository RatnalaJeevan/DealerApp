package com.wisedrive.dealerapp1.pojos.pojos;

import java.util.ArrayList;

public class Pojo_Update_list {
    String vehicleId;
    ArrayList<Pojo_imagearray> imagesArr;

    public Pojo_Update_list(String vehicleId, ArrayList<Pojo_imagearray> imagesArr) {
        this.vehicleId = vehicleId;
        this.imagesArr = imagesArr;
    }
}
