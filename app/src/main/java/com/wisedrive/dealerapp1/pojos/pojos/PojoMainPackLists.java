package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoMainPackLists {

    @SerializedName("display_name")
    String display_name;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("package_logo")
    String package_logo;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("is_starter_pack")
    String is_starter_pack;

    @SerializedName("start_color")
    String start_color;
    @SerializedName("end_color")
    String end_color;
    @SerializedName("PackCombList")
    ArrayList<PojoSubPackList> PackCombList;

    @SerializedName("DescriptionList")
    ArrayList<PojOurServices> DescriptionList;

    @SerializedName("activegateway")
    PojOurServices activegateway;


    public PojOurServices getActivegateway() {
        return activegateway;
    }

    public ArrayList<PojOurServices> getDescriptionList() {
        return DescriptionList;
    }

    public String getIs_starter_pack() {
        return is_starter_pack;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getStart_color() {
        return start_color;
    }

    public String getEnd_color() {
        return end_color;
    }

    public String getPackage_logo() {
        return package_logo;
    }

    public ArrayList<PojoSubPackList> getPackCombList() {
        return PackCombList;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_id() {
        return package_id;
    }
}
