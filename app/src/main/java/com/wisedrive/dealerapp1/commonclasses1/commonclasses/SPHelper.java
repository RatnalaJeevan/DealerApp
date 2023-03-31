package com.wisedrive.dealerapp1.commonclasses1.commonclasses;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.ArraySet;

import com.google.gson.JsonArray;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarBrands;
import com.wisedrive.dealerapp1.pojos.pojos.PojoNewVehImgs;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferarray;
import com.wisedrive.dealerapp1.pojos.pojos.PojoPackageDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSelectPack;

import java.util.ArrayList;
import java.util.List;

public class SPHelper {

    public static SharedPreferences prefs;
    public static String comingfromhome="";
    public static String camefrom="";
    public static ArrayList<PojoPackageDetails> packageDetails;
    public static String main_packid="";
    public static String dealerid="dealerid";
    public static String dealername="dealername";
    public static String dealerno="dealerno";
    public static String imagestaken="imagestaken";
    public static String dealerlogo="dealerlogo";
    public static String helplineno="helplineno";
    public static String awssecret="awssecret";
    public static String awskey="awskey";
    public static String comet_authkey="comet_authkey";
    public static String comet_region="comet_region";
    public static String comet_appid="comet_appid";
    public static String content2="";
    public static String content1="";
    public static String selected_repairid = "";
    public static String carbrandid="";
    public static String carmodelid="";
    public static String manufacture_year="";
    public static String transmissiontype="";
    public static String fueltype="";
    public static String model_name="";
    public static String brandlogo="";
    public static String activationcode="";
    public static String vehid="";
    public static String selected_brandid="";
    public static ArrayList<PojoAllCarBrands> allbrandlogos;
    public static String kmsdriven="";
    public static String vehno="";
    public static String veh_color="";
    public static String no_owners="";
    public static String veh_ispublic="";
    public static String selected_ispublic="";
    public static String is_insurance_update="";
    public static String insurance_provider="";
    public static String insurance_status="";
    public static String insurancetype="";
    public static String tnc="";
    public static String pp="";
    public static String comingfrom="";
    public static String selling_price="";
    public static String inspectionreport="";
    public static String userid="";
    public static double finalamount=0;
    public static String todealerid="";
    public static String to_userid="";
    public static String payment_link="";
    public static String claim_warranty="";
    public static String service_type_id="";
    public static String main_package_id="";
    public static String sub_package_id="";
    public static String ischat_login="n";
    public static String goneto="";
    public static String ins_doc_image="";
    public static String rb_doc_image="";
    public static String rf_doc_image="";
    public static String ab_doc_image="";
    public static String af_doc_image="";
    public static String doc_image="";
    public static String doc_name="";
    public static String inspection_type="";
    public static String insp_status="";
    public static String cool_no_ofdays="";
    public static String cool_kms="";
    public static String comments="";
    public static String req_veh_count="";
    public static String cfp_count="";
    public static String cfs_count="";
    public static String is_sold="";
    public static String with_cool="";
    public static String with_pack="";
    public static String status_id="";
    public static String title="";
    public static String app_url="";
    public static String can_skip="";
    public static String veh_fnt_img="";
    public static String insu_pol="";
    public static ArrayList<String> selected_array;
    public static String category_id="";
    public static String exp_vehid="";
    public static String fuel_id="";
    public static String trans_id="";
    public static String selected_insp_status="";
    public static String price_from="";
    public static String price_to="";
    public static String kms_from="";
    public static String kms_to="";
    public static String selected_sub_packid="";
    public static String add_onid="";
    public static String long_description="";
    public static String addon_name="";
    public static String selected_pack_id="";
    public static String customer_id="";
    public static String selected_main_pack_id="";
    public static String product_id="";
    public static String dealership_name="dealership_name";
    public static String d_email="d_email";
    public static String d_adress="d_adress";
    public static String d_location="d_location";
    public static String city_id="city_id";
    public static String state_id="state_id";
    public static String pincode="pincode";
    public static String d_city="d_city";
    public static String d_state="d_state";
    public static String dpp_id="";
    public static String offer_id="";
    public static int offer_price=0;
    public static String add_on_Main_pack_id="";
    public static String add_on_sub_pack_id="";
    public static int add_onprice=0;
    public static String isSuccess="";
    public static String cf_msg="";
    public static ArrayList<PojoNewVehImgs> veh_imgs=new ArrayList<>();
    public static String customer_name="";
    public static String customer_no="";
    public static String cust_location="";
    public static String cust_pincode="";
    public static String cust_state="";
    public static String cust_city="";
    public static String rc_front="";
    public static String rc_back="";
    public static String aadhar_front="";
    public static String aadhar_back="";
    public static String insu_copy="";
    public static String ext_front="";
    public static String purchase_price="";
    public static String de_note="";
    public static String sa_re="";
    public static String cust_adress="";
    public static String pur_from="";
    public static String ins_exp_date="";
    public static String brand_name="";
    public static String selected_cat_id="";
    public static String selected_offer_id="";
    public  static  ArrayList<PojoOfferarray> pojoOfferarray=new ArrayList();
    public  static  ArrayList<PojoAllCarBrands> pojoAllCarBrands=new ArrayList();
    public static ArrayList<PojoSample> selected_insp_statuses=new ArrayList<>();
    public static String gateway_id="";
    public static String lat="";
    public static String lon="";
    public static String fragment_is="";

    private  static String spName="DealerApp1";

    public static void sharedPreferenceInitialization(Context ctx) {
        prefs = ctx.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }

    public static void saveSPdata(Context ctx, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSPData(Context ctx, String key, String defaultValue) {
        prefs = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }
}
