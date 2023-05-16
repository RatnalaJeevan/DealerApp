package com.wisedrive.dealerapp1.pojos.pojos;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pojo_post_feature {
    String vehicleId;
    ArrayList<Feature>featureArr;

    public Pojo_post_feature(String vehicleId, ArrayList<Feature> featureArr) {
        this.vehicleId = vehicleId;
        this.featureArr = featureArr;
    }
}
