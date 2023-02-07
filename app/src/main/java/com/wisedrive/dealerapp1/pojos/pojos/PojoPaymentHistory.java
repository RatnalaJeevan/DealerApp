package com.wisedrive.dealerapp1.pojos.pojos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PojoPaymentHistory {

    private String amount;
    private String sub_package_name;
    private String service_name;
    private  String payment_mode;
    private  String display_name;
    private  String package_type;
    private String dpp_id;
    private JSONArray ActivateVehList=new JSONArray();

    private String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPayment_date() {
        return payment_date;
    }


    public String getPayment_mode() {
        return payment_mode;
    }

    public JSONArray getActivateVehList() {
        return ActivateVehList;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getSub_package_name() {
        return sub_package_name;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getDpp_id() {
        return dpp_id;
    }

    private String package_name;
    private String payment_date;
    private String payment_status;
    private String no_of_warranties_left;
    private  String valid_to_date;
    private  String is_expired;


    public PojoPaymentHistory(JSONObject obj) throws JSONException {

        {
            try {
                amount=(obj.has("amount")?obj.getString("amount") : "");
                package_name=(obj.has("package_name")?obj.getString("package_name") : "");
                payment_date=(obj.has("payment_date")? obj.getString("payment_date") : "");
                payment_status = (obj.has("payment_status") ? obj.getString("payment_status") : "");
                no_of_warranties_left = (obj.has("no_of_warranties_left") ? obj.getString("no_of_warranties_left") : "");
                service_name = (obj.has("service_name") ? obj.getString("service_name") : "");
                sub_package_name = (obj.has("sub_package_name") ? obj.getString("sub_package_name") : "");
                valid_to_date=(obj.has("valid_to_date") ? obj.getString("valid_to_date") : "");
                is_expired=(obj.has("is_expired") ? obj.getString("is_expired") : "");
                payment_mode=(obj.has("payment_mode") ? obj.getString("payment_mode") : "");
                display_name=(obj.has("display_name") ? obj.getString("display_name") : "");
                package_type=(obj.has("package_type") ? obj.getString("package_type") : "");
                dpp_id=(obj.has("dpp_id") ? obj.getString("dpp_id") : "");

                ActivateVehList=(obj.has("ActivateVehList")?(obj.getJSONArray("ActivateVehList")):new JSONArray());

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }
}
