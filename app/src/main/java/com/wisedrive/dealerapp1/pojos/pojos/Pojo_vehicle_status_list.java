package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_vehicle_status_list {
    String tv_veh_no,tv_veh_status,tv_button_text;

    public Pojo_vehicle_status_list(String tv_veh_no, String tv_veh_status, String tv_button_text) {
        this.tv_veh_no = tv_veh_no;
        this.tv_veh_status = tv_veh_status;
        this.tv_button_text = tv_button_text;
    }

    public String getTv_veh_no() {
        return tv_veh_no;
    }

    public void setTv_veh_no(String tv_veh_no) {
        this.tv_veh_no = tv_veh_no;
    }

    public String getTv_veh_status() {
        return tv_veh_status;
    }

    public void setTv_veh_status(String tv_veh_status) {
        this.tv_veh_status = tv_veh_status;
    }

    public String getTv_button_text() {
        return tv_button_text;
    }

    public void setTv_button_text(String tv_button_text) {
        this.tv_button_text = tv_button_text;
    }
}
