package com.wisedrive.dealerapp1.pojos.pojos;

public class Pojo_leads_page {
    String name,phone_number,text_date,time,text_am_pm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getText_date() {
        return text_date;
    }

    public void setText_date(String text_date) {
        this.text_date = text_date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText_am_pm() {
        return text_am_pm;
    }

    public void setText_am_pm(String text_am_pm) {
        this.text_am_pm = text_am_pm;
    }

    public Pojo_leads_page(String name, String phone_number, String text_date, String time, String text_am_pm) {
        this.name = name;
        this.phone_number = phone_number;
        this.text_date = text_date;
        this.time = time;
        this.text_am_pm = text_am_pm;
    }
}
