package com.wisedrive.dealerapp1.pojos.pojos;

import java.util.ArrayList;

public class PojoCustomerVehicleInfo {


    private String dealer_id;
    private String vehicle_id;
    private String serviceTypeId;
    private String vehicle_no;
    private String category_id;
    private String customer_name;
    private String customer_no;
    private String aadhaar_front;
    private String aadhaar_rear;
    private String address;
    private String pincode;
    private String city;
    private String state;
    private String rc_front;
    private String rc_rear;
    private String insurance_copy;
    private String selling_price;
    private String fastagNo;
    private String fastagImage;
    private String panImage;
    private String addedBy;
    private String addedFrom;
    private String iswithfasttag;
    private String location;
    private String purchasedFrom;
    private String purchase_price;
    private String lead_type;
    private String delivery_note;
    private String sales_receipt;
    private String insurance_status;
    private String insurance_provider;
    private String insurance_type;
    private String insurance_policy;
    private String veh_frontimage;
    private String dealerNo;
    private String leadId;
    private String leadcomment;
    private String isWithOffer;
    private String dppId;
    private ArrayList<PojoOfferarray> offerArr;

    public PojoCustomerVehicleInfo(String dealer_id, String vehicle_id, String serviceTypeId,
                                   String vehicle_no, String category_id, String customer_name,
                                   String customer_no, String aadhaar_front, String aadhaar_rear,
                                   String address, String pincode, String city, String state,
                                   String rc_front, String rc_rear, String insurance_copy,
                                   String selling_price, String fastagNo, String fastagImage,
                                   String panImage, String addedBy, String addedFrom, String iswithfasttag,
                                   String location, String purchasedFrom, String purchase_price,
                                   String lead_type, String delivery_note, String sales_receipt,
                                   String insurance_status, String insurance_provider, String insurance_type,
                                   String insurance_policy, String veh_frontimage, String dealerNo, String leadId, String leadcomment,
                                   String isWithOffer,String dppId,
                                   ArrayList<PojoOfferarray> offerArr) {
        this.dealer_id = dealer_id;
        this.vehicle_id = vehicle_id;
        this.serviceTypeId = serviceTypeId;
        this.vehicle_no = vehicle_no;
        this.category_id = category_id;
        this.customer_name = customer_name;
        this.customer_no = customer_no;
        this.aadhaar_front = aadhaar_front;
        this.aadhaar_rear = aadhaar_rear;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.rc_front = rc_front;
        this.rc_rear = rc_rear;
        this.insurance_copy = insurance_copy;
        this.selling_price = selling_price;
        this.fastagNo = fastagNo;
        this.fastagImage = fastagImage;
        this.panImage = panImage;
        this.addedBy = addedBy;
        this.addedFrom = addedFrom;
        this.iswithfasttag = iswithfasttag;
        this.location = location;
        this.purchasedFrom = purchasedFrom;
        this.purchase_price = purchase_price;
        this.lead_type = lead_type;
        this.delivery_note = delivery_note;
        this.sales_receipt = sales_receipt;
        this.insurance_status = insurance_status;
        this.insurance_provider = insurance_provider;
        this.insurance_type = insurance_type;
        this.insurance_policy = insurance_policy;
        this.veh_frontimage = veh_frontimage;
        this.dealerNo = dealerNo;
        this.leadId = leadId;
        this.leadcomment = leadcomment;
        this.isWithOffer = isWithOffer;
        this.dppId=dppId;
        this.offerArr=offerArr;
    }
}
