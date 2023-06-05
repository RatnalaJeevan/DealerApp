package com.wisedrive.dealerapp1.pojos.pojos;

import android.net.Uri;

public class Pojo_part_list {
    String sample_image,image,module_id,part_id,part_name,is_feature_present;
    String is_selected="n";

    private Uri taken_img = null;
    private String filename = "";

    public Uri getTaken_img() {
        return taken_img;
    }

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public void setTaken_img(Uri taken_img) {
        this.taken_img = taken_img;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIs_feature_present() {
        return is_feature_present;
    }

    public void setIs_feature_present(String is_feature_present) {
        this.is_feature_present = is_feature_present;
    }

    public String getSample_image() {
        return sample_image;
    }

    public void setSample_image(String sample_image) {
        this.sample_image = sample_image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }


    private boolean isSelected=false; // Updated isSelected property


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Add the getIsSelected method
    public String getIsSelected() {
        return isSelected ? "Y" : "N";
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected.equalsIgnoreCase("Y");
    }
}
