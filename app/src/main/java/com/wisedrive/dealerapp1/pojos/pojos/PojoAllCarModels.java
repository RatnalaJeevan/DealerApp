package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAllCarModels {


    @SerializedName("car_model")
    private String car_model;
    @SerializedName("model_id")
    private String model_id;
    private String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getCar_model() {
        return car_model;
    }


    public String getModel_id() {
        return model_id;
    }

}
