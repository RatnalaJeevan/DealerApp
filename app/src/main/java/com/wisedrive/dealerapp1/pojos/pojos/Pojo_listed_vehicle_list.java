package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_listed_vehicle_list {
    String tv_veh_no,tv_veh_make_model,tv_button_text,tv_lead_count;

    public Pojo_listed_vehicle_list(String tv_veh_no, String tv_veh_make_model,
                                    String tv_button_text, String tv_lead_count) {
        this.tv_veh_no = tv_veh_no;
        this.tv_veh_make_model = tv_veh_make_model;
        this.tv_button_text = tv_button_text;
        this.tv_lead_count = tv_lead_count;
    }

    public String getTv_veh_no() {
        return tv_veh_no;
    }

    public void setTv_veh_no(String tv_veh_no) {
        this.tv_veh_no = tv_veh_no;
    }

    public String getTv_veh_make_model() {
        return tv_veh_make_model;
    }

    public void setTv_veh_make_model(String tv_veh_make_model) {
        this.tv_veh_make_model = tv_veh_make_model;
    }

    public String getTv_button_text() {
        return tv_button_text;
    }

    public void setTv_button_text(String tv_button_text) {
        this.tv_button_text = tv_button_text;
    }

    public String getTv_lead_count() {
        return tv_lead_count;
    }

    public void setTv_lead_count(String tv_lead_count) {
        this.tv_lead_count = tv_lead_count;
    }
}
