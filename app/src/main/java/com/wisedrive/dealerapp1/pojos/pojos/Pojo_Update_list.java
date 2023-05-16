package com.wisedrive.dealerapp1.pojos.pojos;

import java.util.ArrayList;

public class Pojo_Update_list {
    String vehicleId;
    ArrayList<Pojo_imagearray> pojo_imagearrayArrayList;

    public Pojo_Update_list(String vehicleId, ArrayList<Pojo_imagearray> pojo_imagearrayArrayList) {
        this.vehicleId = vehicleId;
        this.pojo_imagearrayArrayList = pojo_imagearrayArrayList;
    }
}
