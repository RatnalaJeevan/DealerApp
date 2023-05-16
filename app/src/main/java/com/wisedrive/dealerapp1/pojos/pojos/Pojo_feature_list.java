package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_feature_list {
    String part_name,module_id,part_id,is_feature_present;

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
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

    public String getIs_feature_present() {
        return is_feature_present;
    }

    public void setIs_feature_present(String is_feature_present) {
        this.is_feature_present = is_feature_present;
    }


    boolean isSelected;

    public Pojo_feature_list() {
        isSelected = false; // initialize isSelected to false by default
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}