package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_Module_list {
    String module_id,module_name;
    public boolean isVisible;


    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    private boolean isSelected; // Updated isSelected property

    public Pojo_Module_list() {
        isSelected = false; // Initialize isSelected to false by default
    }

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
