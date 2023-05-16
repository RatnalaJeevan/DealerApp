package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_features {
    String text_feature;
    public boolean isVisible;

    public boolean isVisible(){
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Pojo_features(String text_feature) {
        this.text_feature = text_feature;
    }

    public String getText_feature() {
        return text_feature;
    }

    public void setText_feature(String text_feature) {
        this.text_feature = text_feature;
    }
}
