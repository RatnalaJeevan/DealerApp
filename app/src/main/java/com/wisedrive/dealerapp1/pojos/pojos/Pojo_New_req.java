package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_New_req {
    String expiry_date,tv_no_leads,tv_make_model,
            tv_price_range,tv_kmsdriven,tv_makeyear,owner_no,tv_fuel_type,tv_trans_type,tv_color,tv_matching_count;
    int brand_logo;

    public Pojo_New_req(String expiry_date, String tv_no_leads, String tv_make_model,
                        String tv_price_range, String tv_kmsdriven, String tv_makeyear, String owner_no,
                        String tv_fuel_type, String tv_trans_type, String tv_color, String tv_matching_count,
                        int brand_logo) {
        this.expiry_date = expiry_date;
        this.tv_no_leads = tv_no_leads;
        this.tv_make_model = tv_make_model;
        this.tv_price_range = tv_price_range;
        this.tv_kmsdriven = tv_kmsdriven;
        this.tv_makeyear = tv_makeyear;
        this.owner_no = owner_no;
        this.tv_fuel_type = tv_fuel_type;
        this.tv_trans_type = tv_trans_type;
        this.tv_color = tv_color;
        this.tv_matching_count = tv_matching_count;
        this.brand_logo = brand_logo;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getTv_no_leads() {
        return tv_no_leads;
    }

    public void setTv_no_leads(String tv_no_leads) {
        this.tv_no_leads = tv_no_leads;
    }

    public String getTv_make_model() {
        return tv_make_model;
    }

    public void setTv_make_model(String tv_make_model) {
        this.tv_make_model = tv_make_model;
    }

    public String getTv_price_range() {
        return tv_price_range;
    }

    public void setTv_price_range(String tv_price_range) {
        this.tv_price_range = tv_price_range;
    }

    public String getTv_kmsdriven() {
        return tv_kmsdriven;
    }

    public void setTv_kmsdriven(String tv_kmsdriven) {
        this.tv_kmsdriven = tv_kmsdriven;
    }

    public String getTv_makeyear() {
        return tv_makeyear;
    }

    public void setTv_makeyear(String tv_makeyear) {
        this.tv_makeyear = tv_makeyear;
    }

    public String getOwner_no() {
        return owner_no;
    }

    public void setOwner_no(String owner_no) {
        this.owner_no = owner_no;
    }

    public String getTv_fuel_type() {
        return tv_fuel_type;
    }

    public void setTv_fuel_type(String tv_fuel_type) {
        this.tv_fuel_type = tv_fuel_type;
    }

    public String getTv_trans_type() {
        return tv_trans_type;
    }

    public void setTv_trans_type(String tv_trans_type) {
        this.tv_trans_type = tv_trans_type;
    }

    public String getTv_color() {
        return tv_color;
    }

    public void setTv_color(String tv_color) {
        this.tv_color = tv_color;
    }

    public String getTv_matching_count() {
        return tv_matching_count;
    }

    public void setTv_matching_count(String tv_matching_count) {
        this.tv_matching_count = tv_matching_count;
    }

    public int getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(int brand_logo) {
        this.brand_logo = brand_logo;
    }
}
