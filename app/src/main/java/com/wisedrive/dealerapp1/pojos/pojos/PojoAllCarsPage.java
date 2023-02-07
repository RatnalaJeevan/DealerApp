package com.wisedrive.dealerapp1.pojos.pojos;

public class PojoAllCarsPage {

    String insp_status;
    String insp_on_date;
    String id;

    public PojoAllCarsPage(String insp_status,String insp_on_date,String id) {
        this.insp_status = insp_status;
        this.insp_on_date=insp_on_date;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInsp_status() {
        return insp_status;
    }

    public String getInsp_on_date() {
        return insp_on_date;
    }

    public void setInsp_on_date(String insp_on_date) {
        this.insp_on_date = insp_on_date;
    }

    public void setInsp_status(String insp_status) {
        this.insp_status = insp_status;
    }
}
