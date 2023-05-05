package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_feature_list {
    String text_feature_list;
    boolean isSelected;

    public Pojo_feature_list(String text_feature_list) {
        this.text_feature_list = text_feature_list;
        isSelected = false; // initialize isSelected to false by default
    }

    public String getText_feature_list() {
        return text_feature_list;
    }

    public void setText_feature_list(String text_feature_list) {
        this.text_feature_list = text_feature_list;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}