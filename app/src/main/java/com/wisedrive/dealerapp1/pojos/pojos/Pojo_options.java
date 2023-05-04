package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_options {
    public Pojo_options(String text_feature_list_2) {
        this.text_feature_list_2 = text_feature_list_2;
        isSelected = false;
    }

    public String getText_feature_list_2() {
        return text_feature_list_2;
    }

    public void setText_feature_list_2(String text_feature_list_2) {
        this.text_feature_list_2 = text_feature_list_2;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    String text_feature_list_2;
    boolean isSelected;
}
