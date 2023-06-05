package com.wisedrive.dealerapp1;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterCashBackList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterOfferModuleList;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.BitmapUtility;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.RequestPermissionHandler;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyOffer;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCustomerVehicleInfo;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferarray;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activate extends BottomSheetDialogFragment {
    public String isWithOffer="N",cat_id="";
    public int general_count,cashback_count,add_on_count;
    String offer_paymemnt_id="",offer_id="";
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    String emailpattern = "^(?=.{1,64}@)[A-Za-z0-9_-]*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public int fromWhere=0;
    private ProgressDialog progressDialog;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    private  Uri imageUri;
    public  boolean isServiceRunning = true;
    public String isvalid="",is_offer_taken="";
    ProgressBar idPBLoading;
    DealerApis apiInterface;
    public  String filename, aadharfronturl="",aadharbackurl="",rcfronturl ="", rcbackurl ="",del_note_url="",sale_re_url="",KEY = "", SECRET = "", is_with_ft="",
            insuranceurl ="",setimage ,panurl="",fast_tagurl="",ins_stat="",ins_pro="",ins_type="",ins_pol="",ext_frnt="";
    RecyclerView rv_offers_list,rv_max_details;
    public RelativeLayout rl_check_status,rl_offer_details
            ,rl_pur_details,rl_veh_docs,rl_cust_details,rl_cust,rl_docs,rl_purchase,rl_offers,
            rl_back1,rl_next1,rl_activate,rl_offer_module;
    TextView tv_cust,tv_docs,tv_purchase,tv_offers,tv_veh_details,veh_no,no_cool_days;
    View v_offers,v_cust,v_docs,v_purchase;
    int act_no=1;
    ImageView car_logo,iv_ins_copy1,iv_de_note,iv_sa_re,app_stamp,aadhar_back,
            aadhar_front,rc_back,rc_front;
     Activity activity;
    EditText entered_name,entered_no,cust_location,cust_adress,cust_pincode,entered_price,entered_pufr;
    public TextView selected_city,selected_state;
    private RequestPermissionHandler mRequestPermissionHandler;
    ArrayList<PojoOfferModuleList> offerModuleListArrayList=new ArrayList<>();
    ArrayList<PojoOfferModuleList> cashbackofferlist=new ArrayList<>();
    AdapterOfferModuleList adapterOfferModuleList;
    AdapterCashBackList adapterCashBackList;
    RelativeLayout rl_cashback_offer,rl_add_on_offer,rl_max_offer_details;
    ImageView iv_add_on,iv_cash_back;
    private static Activate instance;
    ArrayList<PojoOfferarray> pojoOfferarray=new ArrayList();
    public RelativeLayout rl_show_popup,rl_transparent,rl_ok;
    EditText entered_fastag_no,entered_mail;
    ImageView pan_img,fast_tag_img;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_activate,container, false);
        activity=getActivity();

        ///act warranty
        init_params(v);

        System.out.println("SPHelper.vehid"+SPHelper.vehid);
        instance=this;

        if(SPHelper.customer_id==null||SPHelper.customer_id.equals("")){
            app_stamp.setImageResource(R.drawable.app_bla);
        }
        else {
            entered_name.setText(SPHelper.customer_name);
            entered_no.setText(SPHelper.customer_no);
            cust_location.setText(SPHelper.cust_location);
            cust_pincode.setText(SPHelper.cust_pincode);
            selected_state.setText(SPHelper.cust_state);
            selected_city.setText(SPHelper.cust_city);
            entered_name.setText(SPHelper.customer_name);
            cust_adress.setText(SPHelper.cust_adress);
            entered_pufr.setText(SPHelper.pur_from);
            entered_price.setText(SPHelper.purchase_price);
            app_stamp.setImageResource(R.drawable.sold_label);
            if(SPHelper.rc_front==null||SPHelper.rc_front.equals("null")){

            }else{
                rcfronturl=SPHelper.rc_front;
                Glide.with(activity).load(SPHelper.rc_front).placeholder(R.drawable.add_car_icon).into(rc_front);
            }
            if(SPHelper.rc_back==null||SPHelper.rc_back.equals("null")){

            }else{
                rcbackurl=SPHelper.rc_back;
                Glide.with(activity).load(SPHelper.rc_back).placeholder(R.drawable.add_car_icon).into(rc_back);
            }
            if(SPHelper.aadhar_front==null||SPHelper.aadhar_front.equals("null")){

            }else{
                aadharfronturl=SPHelper.aadhar_front;
                Glide.with(activity).load(SPHelper.aadhar_front).placeholder(R.drawable.add_car_icon).into(aadhar_front);
            }
            if(SPHelper.aadhar_back==null||SPHelper.aadhar_back.equals("null")){

            }else{
                aadharbackurl=SPHelper.aadhar_back;
                Glide.with(activity).load(SPHelper.aadhar_back).placeholder(R.drawable.add_car_icon).into(aadhar_back);
            }
            if(SPHelper.insu_copy==null||SPHelper.insu_copy.equals("null")){

            }else{
                insuranceurl=SPHelper.insu_copy;
                Glide.with(activity).load(SPHelper.insu_copy).placeholder(R.drawable.add_car_icon).into(iv_ins_copy1);
            }
            if(SPHelper.sa_re==null||SPHelper.sa_re.equals("null")){

            }else{
                sale_re_url=SPHelper.sa_re;
                Glide.with(activity).load(SPHelper.sa_re).placeholder(R.drawable.add_car_icon).into(iv_sa_re);
            }
            if(SPHelper.de_note==null||SPHelper.de_note.equals("null")){

            }else{
                del_note_url=SPHelper.de_note;
                Glide.with(activity).load(SPHelper.de_note).placeholder(R.drawable.add_car_icon).into(iv_de_note);
            }
        }
        rl_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=1;
                show_act_pages();
            }
        });
        rl_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=2;
                show_act_pages();
                //validate_customer();
            }
        });
        rl_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=3;
                show_act_pages();
            }
        });
        rl_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=4;
                show_act_pages();
            }
        });

        rl_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no--;
                show_act_pages();
                System.out.println("act_no"+act_no);
            }
        });

        rl_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no++;
                //validate_customer();
                show_act_pages();
                System.out.println("act_no"+act_no);
            }
        });

        cust_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(cust_pincode.getText().toString().length()==6){
                   // hideKeybaord();
                    get_pincode_details();
                }else if(cust_pincode.getText().toString().length()<6){
                    selected_city.setText("");
                    selected_state.setText("");
                }
            }
        });

        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        rl_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        Glide.with(activity).load(SPHelper.brandlogo).placeholder(R.drawable.icon_noimage).into(car_logo);
        tv_veh_details.setText(SPHelper.model_name+"-"+SPHelper.fueltype+"-"+SPHelper.manufacture_year);
        veh_no.setText(SPHelper.vehno.toUpperCase());
        if((SPHelper.cool_kms==null||SPHelper.cool_kms.equals("null")||SPHelper.cool_kms.equals(""))&&
                (SPHelper.cool_no_ofdays==null||SPHelper.cool_no_ofdays.equals("null")||SPHelper.cool_no_ofdays.equals(""))){
            no_cool_days.setVisibility(View.GONE);
        }
        else if((SPHelper.cool_kms==null||SPHelper.cool_kms.equals("")||SPHelper.cool_kms.equals("null"))&&
                (!SPHelper.cool_no_ofdays.equals(""))){
            SPHelper.cool_kms="";
            no_cool_days.setVisibility(View.VISIBLE);
            no_cool_days.setText(SPHelper.cool_no_ofdays+"\t days "+SPHelper.cool_kms+"\t kms Cooling Period");
        }else if((SPHelper.cool_no_ofdays==null||SPHelper.cool_no_ofdays.equals("")||SPHelper.cool_no_ofdays.equals("null"))&&
                (!SPHelper.cool_kms.equals(""))
        ){
            no_cool_days.setVisibility(View.VISIBLE);
            SPHelper.cool_no_ofdays="";
            no_cool_days.setText(SPHelper.cool_no_ofdays+"\t days "+SPHelper.cool_kms+"\t kms Cooling Period");
        }
        else{
            no_cool_days.setVisibility(View.VISIBLE);
            no_cool_days.setText(SPHelper.cool_no_ofdays+" "+"days\t"+"&\t"+SPHelper.cool_kms+"\t kms Cooling Period");
        }

        rc_front.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                setimage="rcfront";
                opendialog(view);
            }
        });
        rc_back.setOnClickListener(view -> {
            setimage="rcback";
            opendialog(view);
        });
        aadhar_front.setOnClickListener(view -> {
            setimage="aadharfront";
            opendialog(view);
        });
        aadhar_back.setOnClickListener(view -> {
            setimage="aadharback";
            opendialog(view);
        });
        iv_ins_copy1.setOnClickListener(view -> {
            setimage="insurance1st";
            opendialog(view);
        });

        iv_sa_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="sa_re";
                opendialog(view);
            }
        });
        iv_de_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="de_no";
                opendialog(view);
            }
        });

        fast_tag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="fast";
                opendialog(view);
            }
        });
        pan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setimage="pan";
                opendialog(view);
            }
        });
        rl_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //dialog.show();
                SPHelper.camefrom="activate";
                if(entered_name.getText().toString().trim().equals("")){
                    Toast.makeText(activity,
                            " Enter Customer name",
                            Toast.LENGTH_SHORT).show();
                }
                else if(entered_no.getText().toString().equals("")){
                    Toast.makeText(activity,
                            " Enter Customer PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(entered_no.getText().toString().length()<10){
                    Toast.makeText(activity,
                            " Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!entered_no.getText().toString().matches(mobile_no_pattern)){
                    Toast.makeText(activity,
                            " Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else   if(entered_mail.getText().toString().equals(""))
                {
                    Toast.makeText(activity,
                            " Enter Email",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!entered_mail.getText().toString().matches(emailpattern)){
                    Toast.makeText(activity, "Enter valid email" , Toast.LENGTH_SHORT).show();
                }
                 else   if(cust_adress.getText().toString().equals(""))
                 {
                        Toast.makeText(activity,
                                " enter address",
                                Toast.LENGTH_SHORT).show();
                  }
                else if(cust_location.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter Location",
                            Toast.LENGTH_SHORT).show();
                }

                else if(cust_pincode.getText().toString().length()<6
                        ||selected_city.getText().toString().equals("")
                        ||selected_state.getText().toString().equals("")){
                    Toast.makeText(activity,
                            " enter valid pincode",
                            Toast.LENGTH_SHORT).show();
                }
                else if(rcfronturl.equals("") ){
                    Toast.makeText(activity,
                            " Upload RC front photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if( rcbackurl.equals("")){
                    Toast.makeText(activity,
                            " Upload RC back photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if(aadharfronturl.equals("") ){
                    Toast.makeText(activity,
                            " Upload aadhar front photo",
                            Toast.LENGTH_SHORT).show();
                }
                else if(aadharbackurl.equals("")){
                    Toast.makeText(activity,
                            " Upload Aadhar back photo",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    validate_parameters();
                }

            }
        });

        rl_add_on_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_add_on.setVisibility(View.VISIBLE);
                iv_cash_back.setVisibility(View.GONE);
                cat_id="3";
                rl_max_offer_details.setVisibility(View.VISIBLE);
                getCashBackDetails();
            }
        });
        rl_cashback_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_add_on.setVisibility(View.GONE);
                rl_max_offer_details.setVisibility(View.VISIBLE);
                iv_cash_back.setVisibility(View.VISIBLE);
                cat_id="2";
                getCashBackDetails();
            }
        });

        getOfferList();
        getOfferCount();
        getOfferPresence();
        return v;
    }

    public void get_pincode_details() {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_pincode(cust_pincode.getText().toString());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                           // idPBLoading.setVisibility(View.GONE);
                            //get state and city name
                            AppResponse appResponse=response.body();

                            String cityname=appResponse.getResponse().getGetpincodedata().getCity_name();
                            String statename=appResponse.getResponse().getGetpincodedata().getState_name();
                            if(cityname==null){
                                Common.CallToast(activity,"Enter Valid pincode",1);
                                selected_city.setText("");
                                selected_state.setText("");
                            }else{
                                selected_city.setText(cityname);
                                selected_state.setText(statename);
                            }

                        }
                        else
                        {
                            Toast.makeText(activity,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    //idPBLoading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                   // idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void hideKeybaord(){

        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    private void init_params(View v)
    {
        entered_mail=v.findViewById(R.id.entered_mail);
        rl_ok=v.findViewById(R.id.rl_ok);
        rl_show_popup=v.findViewById(R.id.rl_show_popup);
        rl_transparent=v.findViewById(R.id.rl_transparent);
        entered_fastag_no=v.findViewById(R.id.entered_fastag_no);
        pan_img=v.findViewById(R.id.pan_img);
        fast_tag_img=v.findViewById(R.id.fast_tag_img);
        rl_max_offer_details=v.findViewById(R.id.rl_max_offer_details);
        rl_cashback_offer=v.findViewById(R.id.rl_cashback_offer);
        rl_add_on_offer=v.findViewById(R.id.rl_add_on_offer);
        iv_add_on=v.findViewById(R.id.iv_add_on);
        iv_cash_back=v.findViewById(R.id.iv_cash_back);
        rv_max_details=v.findViewById(R.id.rv_max_details);
        app_stamp=v.findViewById(R.id.app_stamp);
        no_cool_days=v.findViewById(R.id.no_cool_days);
        rl_offer_module=v.findViewById(R.id.rl_offer_module);
        rv_offers_list=v.findViewById(R.id.rv_offers_list);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        SPHelper.sharedPreferenceInitialization(activity);
        KEY=SPHelper.getSPData(activity,SPHelper.awskey,"");
        SECRET=SPHelper.getSPData(activity,SPHelper.awssecret,"");
        System.out.println("key"+KEY);
        System.out.println("secret"+SECRET);
        idPBLoading=v.findViewById(R.id.idPBLoading);
        mRequestPermissionHandler = new RequestPermissionHandler();
        AWSMobileClient.getInstance().initialize(activity).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(activity,SPHelper.awskey,""), SPHelper.getSPData(activity,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        entered_pufr=v.findViewById(R.id.entered_pufr);
        entered_price=v.findViewById(R.id.entered_price);
        iv_ins_copy1=v.findViewById(R.id.iv_ins_copy1);
        iv_de_note=v.findViewById(R.id.iv_de_note);
        iv_sa_re=v.findViewById(R.id.iv_sa_re);
        aadhar_back=v.findViewById(R.id.aadhar_back);
        aadhar_front=v.findViewById(R.id.aadhar_front);
        rc_back=v.findViewById(R.id.rc_back);
        rc_front=v.findViewById(R.id.rc_front);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        cust_location=v.findViewById(R.id.cust_location);
        cust_adress=v.findViewById(R.id.cust_adress);
        cust_pincode=v.findViewById(R.id.cust_pincode);
        selected_city=v.findViewById(R.id.selected_city);
        selected_state=v.findViewById(R.id.selected_state);
        entered_name=v.findViewById(R.id.entered_name);
        entered_no=v.findViewById(R.id.entered_no);
        veh_no=v.findViewById(R.id.veh_no);
        tv_veh_details=v.findViewById(R.id.tv_veh_details);
        car_logo=v.findViewById(R.id.car_logo);
        rl_check_status=v.findViewById(R.id.rl_check_status);
        rl_offer_details=v.findViewById(R.id.rl_offer_details);
        rl_pur_details=v.findViewById(R.id.rl_pur_details);
        rl_veh_docs=v.findViewById(R.id.rl_veh_docs);
        rl_cust_details=v.findViewById(R.id.rl_cust_details);
        tv_cust=v.findViewById(R.id.tv_cust);
        tv_docs=v.findViewById(R.id.tv_docs);
        tv_purchase=v.findViewById(R.id.tv_purchase);
        tv_offers=v.findViewById(R.id.tv_offers);
        v_cust=v.findViewById(R.id.v_cust);
        v_docs=v.findViewById(R.id.v_docs);
        v_purchase=v.findViewById(R.id.v_purchase);
        v_offers=v.findViewById(R.id.v_offers);
        rl_cust=v.findViewById(R.id.rl_cust);
        rl_docs=v.findViewById(R.id.rl_docs);
        rl_purchase=v.findViewById(R.id.rl_purchase);
        rl_offers=v.findViewById(R.id.rl_offers);
        rl_back1=v.findViewById(R.id.rl_back1);
        rl_next1=v.findViewById(R.id.rl_next1);
        rl_activate=v.findViewById(R.id.rl_activate);

    }

    public  void show_act_pages()
    {

        if(act_no==1)
        {
            rl_offer_module.setVisibility(View.GONE);
            rl_pur_details.setVisibility(View.GONE);
            rl_veh_docs.setVisibility(View.GONE);
            rl_cust_details.setVisibility(View.VISIBLE);
            tv_cust.setTextColor(Color.parseColor("#FF000000"));
            tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
            tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
            tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
            v_cust.setVisibility(View.VISIBLE);
            v_offers.setVisibility(View.GONE);
            v_docs.setVisibility(View.GONE);
            v_purchase.setVisibility(View.GONE);
            rl_next1.setVisibility(View.VISIBLE);
            rl_activate.setVisibility(View.INVISIBLE);
            rl_back1.setVisibility(View.GONE);

        }
        else if(act_no==2)
        {
            //validate_customer();
            if(entered_name.getText().toString().trim().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer name",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer PhoneNo",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().length()<10){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_no.getText().toString().matches(mobile_no_pattern)){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else   if(entered_mail.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " Enter Email",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_mail.getText().toString().matches(emailpattern)){
                act_no--;
                Toast.makeText(activity, "Enter valid email" , Toast.LENGTH_SHORT).show();
            }
            else   if(cust_adress.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " enter address",
                        Toast.LENGTH_SHORT).show();
            }
            else if(cust_location.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        "Enter Location",
                        Toast.LENGTH_SHORT).show();
            }

            else if(cust_pincode.getText().toString().length()<6
                    ||selected_city.getText().toString().equals("")
                    ||selected_state.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter valid pincode",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                rl_offer_module.setVisibility(View.GONE);
                rl_pur_details.setVisibility(View.GONE);
                rl_veh_docs.setVisibility(View.VISIBLE);
                rl_cust_details.setVisibility(View.GONE);
                tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
                tv_docs.setTextColor(Color.parseColor("#FF000000"));
                tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
                tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
                v_cust.setVisibility(View.GONE);
                v_offers.setVisibility(View.GONE);
                v_docs.setVisibility(View.VISIBLE);
                v_purchase.setVisibility(View.GONE);
                rl_next1.setVisibility(View.VISIBLE);
                rl_activate.setVisibility(View.INVISIBLE);
                rl_back1.setVisibility(View.VISIBLE);
            }
        }
        else if(act_no==3)
        {
            if(entered_name.getText().toString().trim().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer name",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer PhoneNo",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().length()<10){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_no.getText().toString().matches(mobile_no_pattern)){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else  if(entered_mail.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " Enter Email",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_mail.getText().toString().matches(emailpattern)){
                act_no--;
                Toast.makeText(activity, "Enter valid email" , Toast.LENGTH_SHORT).show();
            }
            else   if(cust_adress.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " enter address",
                        Toast.LENGTH_SHORT).show();
            }
            else if(cust_location.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        "Enter Location",
                        Toast.LENGTH_SHORT).show();
            }

            else if(cust_pincode.getText().toString().length()<6
                    ||selected_city.getText().toString().equals("")
                    ||selected_state.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter valid pincode",
                        Toast.LENGTH_SHORT).show();
            }
            else if(rcfronturl.equals("")||rcbackurl.equals("")){
                act_no--;
                Toast.makeText(activity,
                        " upload rc",
                        Toast.LENGTH_SHORT).show();
            }
            else if(aadharfronturl.equals("")||aadharbackurl.equals("")){
                act_no--;
                Toast.makeText(activity,
                        " upload aadhar ",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                rl_offer_module.setVisibility(View.GONE);
                rl_pur_details.setVisibility(View.VISIBLE);
                rl_veh_docs.setVisibility(View.GONE);
                rl_cust_details.setVisibility(View.GONE);
                tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
                tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
                tv_purchase.setTextColor(Color.parseColor("#FF000000"));
                tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
                v_cust.setVisibility(View.GONE);
                v_offers.setVisibility(View.GONE);
                v_docs.setVisibility(View.GONE);
                v_purchase.setVisibility(View.VISIBLE);
                rl_next1.setVisibility(View.VISIBLE);
                rl_activate.setVisibility(View.INVISIBLE);
                rl_back1.setVisibility(View.VISIBLE);
            }
        }
        else if(act_no==4)
        {
            if(entered_name.getText().toString().trim().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer name",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter Customer PhoneNo",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_no.getText().toString().length()<10){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_no.getText().toString().matches(mobile_no_pattern)){
                act_no--;
                Toast.makeText(activity,
                        " Enter Valid Phone Number",
                        Toast.LENGTH_SHORT).show();
            }
            else   if(entered_mail.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " enter mail",
                        Toast.LENGTH_SHORT).show();
            }
            else if(!entered_mail.getText().toString().matches(emailpattern)){
                act_no--;
                Toast.makeText(activity, "Enter valid email" , Toast.LENGTH_SHORT).show();
            }
            else   if(cust_adress.getText().toString().equals(""))
            {
                act_no--;
                Toast.makeText(activity,
                        " enter address",
                        Toast.LENGTH_SHORT).show();
            }
            else if(cust_location.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        "Enter Location",
                        Toast.LENGTH_SHORT).show();
            }

            else if(cust_pincode.getText().toString().length()<6
                    ||selected_city.getText().toString().equals("")
                    ||selected_state.getText().toString().equals("")){
                act_no--;
                Toast.makeText(activity,
                        " Enter valid pincode",
                        Toast.LENGTH_SHORT).show();
            }
            else if(rcfronturl.equals("")||rcbackurl.equals("")){
                act_no--;
                Toast.makeText(activity,
                        " upload rc",
                        Toast.LENGTH_SHORT).show();
            }
            else if(aadharfronturl.equals("")||aadharbackurl.equals("")){
                act_no--;
                Toast.makeText(activity,
                        " upload aadhar ",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                rl_offer_module.setVisibility(View.VISIBLE);
                rl_pur_details.setVisibility(View.GONE);
                rl_veh_docs.setVisibility(View.GONE);
                rl_cust_details.setVisibility(View.GONE);
                tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
                tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
                tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
                tv_offers.setTextColor(Color.parseColor("#FF000000"));
                v_cust.setVisibility(View.GONE);
                v_offers.setVisibility(View.VISIBLE);
                v_docs.setVisibility(View.GONE);
                v_purchase.setVisibility(View.GONE);
                rl_next1.setVisibility(View.INVISIBLE);
                rl_activate.setVisibility(View.VISIBLE);
                rl_back1.setVisibility(View.VISIBLE);
            }
        }
    }


    public  void validate_parameters(){

        boolean isselcted=false;
        if(offerModuleListArrayList.isEmpty()){
            isselcted=false;
        }
        else {
            for (int i = 0; i <offerModuleListArrayList.size(); i++)
            {
               if (offerModuleListArrayList.get(i).getOffer_id().equals("2")&&
                       offerModuleListArrayList.get(i).getIsSelected().equalsIgnoreCase("y")){
                   isselcted=true;
                   break;
              }
            }
        }

        if(isselcted==true) {
            if (entered_fastag_no.getText().toString().equals("")) {
                Common.CallToast(activity, " Enter fastag number", 1);
            }
            else if(entered_fastag_no.getText().toString().length()<12){
                Common.CallToast(activity,"Enter valid fastag number",1);
            }
            else if (fast_tagurl.equals("")) {
                Common.CallToast(activity, "Add fastag image", 1);
            }
            else if (panurl.equals("")) {
                Common.CallToast(activity, "Add pancard image", 1);
            } else {
                is_with_ft = "y";
                activate_package_toveh();
            }
        }else{
            is_with_ft = "n";
            activate_package_toveh();
        }

    }

    public void opendialog(View view){
        final Dialog dialog4 = new Dialog(activity);
        dialog4.setCancelable(true);
        View view4 = activity.getLayoutInflater().inflate(R.layout.dialog_camera_options, null);
        dialog4.setContentView(view4);
        RelativeLayout rl_camera4 = view4.findViewById(R.id.rl_camera);
        RelativeLayout rl_gallery4 = view4.findViewById(R.id.rl_gallery);

        rl_camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(setimage.equals("rcfront")){
                        fromWhere=1;
                    }else if(setimage.equals("rcback")){
                        fromWhere=2;
                    }
                    else if(setimage.equals("aadharfront")){
                        fromWhere=3;
                    }
                    else if(setimage.equals("aadharback")){
                        fromWhere=4;
                    }
                    else if(setimage.equals("insurance1st")){
                        fromWhere=5;
                    }
                    else if(setimage.equals("pan")){
                        fromWhere=6;
                    }
                    else if(setimage.equals("fast")){
                        fromWhere=7;
                    }
                    else if(setimage.equals("de_no")){
                        fromWhere=8;
                    }
                    else if(setimage.equals("sa_re")){
                        fromWhere=9;
                    }
                    getCameraPermissions(fromWhere);
                }
                dialog4.dismiss();
            }
        });
        rl_gallery4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(setimage.equals("rcfront")){
                        fromWhere=10;
                    }else if(setimage.equals("rcback")){
                        fromWhere=20;
                    }
                    else if(setimage.equals("aadharfront")){
                        fromWhere=30;
                    }
                    else if(setimage.equals("aadharback")){
                        fromWhere=40;
                    }
                    else if(setimage.equals("insurance1st")){
                        fromWhere=50;
                    }
                    else if(setimage.equals("pan")){
                        fromWhere=60;
                    }
                    else if(setimage.equals("fast")){
                        fromWhere=70;
                    }
                    else if(setimage.equals("de_no")){
                        fromWhere=80;
                    }
                    else if(setimage.equals("sa_re")){
                        fromWhere=90;
                    }
                    getCameraPermissions(fromWhere);
                }
                dialog4.dismiss();
            }
        });
        dialog4.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(activity, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");

                Intent intent;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                }else{
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                }
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");

                if (fromWhere == 1) {
                    CallCamera();
                } else if (fromWhere == 10) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 200);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);


                } else if (fromWhere == 2) {

                    CallCamera();
                } else if (fromWhere == 20) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 400);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 400);


                } else if (fromWhere == 3) {

                    CallCamera();
                } else if (fromWhere == 30) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 600);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 600);


                } else if (fromWhere == 4) {

                    CallCamera();
                } else if (fromWhere == 40) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 800);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 800);


                }else if (fromWhere == 5) {

                    CallCamera();
                } else if (fromWhere == 50) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 1000);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);


                }
                else if (fromWhere == 6) {

                    CallCamera();
                } else if (fromWhere == 60) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 1200);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1200);


                }
                else if (fromWhere == 7) {

                    CallCamera();
                } else if (fromWhere == 70) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 1400);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1400);


                }

                else if (fromWhere == 8) {

                    CallCamera();
                } else if (fromWhere == 80) {

                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1600);

//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 1600);

                }
                else if (fromWhere == 9) {

                    CallCamera();
                } else if (fromWhere == 90) {
//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivityForResult(pickPhoto, 1800);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1800);


                }
            }

            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CallCamera() {
        mRequestPermissionHandler.requestPermission(activity, new String[]{
                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {

            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void openCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
            // Create the File where the photo should go
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            imageUri = FileProvider.getUriForFile(activity,
                    BuildConfig.APPLICATION_ID + ".provider",
                    new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            //startActivityForResult(takePictureIntent, 100);
            if(setimage.equals("rcfront")){
                startActivityForResult(takePictureIntent, 100);
            }else if(setimage.equals("rcback")){
                startActivityForResult(takePictureIntent, 300);
            }
            else if(setimage.equals("aadharfront")){
                startActivityForResult(takePictureIntent, 500);
            }
            else if(setimage.equals("aadharback")){
                startActivityForResult(takePictureIntent, 700);
            }
            else if(setimage.equals("insurance1st")){
                startActivityForResult(takePictureIntent, 900);
            }
            else if(setimage.equals("pan")){
                startActivityForResult(takePictureIntent, 1100);
            }
            else if(setimage.equals("fast")){
                startActivityForResult(takePictureIntent, 1300);
            }
            else if(setimage.equals("de_no")){
                startActivityForResult(takePictureIntent, 1500);
            }
            else if(setimage.equals("sa_re")){
                startActivityForResult(takePictureIntent, 1700);
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //rcfront
        if (requestCode == 100 && resultCode == RESULT_OK )
        {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    rc_front.setImageURI(imageUri);

                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }


                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Rc Front uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    rcfronturl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 200 && data!=null)
        {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    rc_front.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Rc Front uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    rcfronturl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //rcback
        else if (requestCode == 300 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    rc_back.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Rc Back uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    rcbackurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });

        }
        else if (requestCode == 400 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    rc_back.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Rc Back uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    rcbackurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //aadharfront
        else if (requestCode == 500 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    Glide.with(activity).load(imageUri.toString()).placeholder(R.drawable.add_car_icon).into(aadhar_front);
                    aadhar_front.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Aadhar Front uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    aadharfronturl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });

        }
        else if (requestCode == 600 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    aadhar_front.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Aadhar Front uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    aadharfronturl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //aadharback
        else if (requestCode == 700 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    aadhar_back.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Aadhar Back uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    aadharbackurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 800 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    aadhar_back.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "AadharBack/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Aadhar Back uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    aadharbackurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //insurancecopy
        else if (requestCode == 900 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv_ins_copy1.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "InsuranceCopy/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "InsuranceCopy uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    insuranceurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 1000 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv_ins_copy1.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "InsuranceCopy/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "InsuranceCopy uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    insuranceurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }

        //pan
        else if (requestCode == 1100 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    pan_img.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Pan/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "PanCard uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    panurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 1200 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    pan_img.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Pan/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "PanCard uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    panurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }

        //fastag
        else if (requestCode == 1300 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    fast_tag_img.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Fastag/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Fastag uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    fast_tagurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 1400 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    fast_tag_img.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Fastag/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Fastag uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    fast_tagurl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        //delivery note
        else if (requestCode == 1500 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv_de_note.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Delivery_Note/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Deliverynote uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    del_note_url = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 1600 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv_de_note.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Delivery_Note/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Deliverynote uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    del_note_url = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }

        //sales receipt
        else if (requestCode == 1700 && resultCode == RESULT_OK) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    iv_sa_re.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Sales_Receipt/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Sales receipt uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    sale_re_url = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 1800 && data!=null)  {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(activity, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(activity, imageUri, filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv_sa_re.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(activity)) {
                        idPBLoading.setVisibility(View.GONE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        idPBLoading.setVisibility(View.VISIBLE);
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(activity)
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Sales_Receipt/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(activity, "Sales receipt uploaded!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                    sale_re_url = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            idPBLoading.setVisibility(View.GONE);
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        idPBLoading.setVisibility(View.GONE);
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
    }

    private void validate()
    {
    }

    public void getOfferList()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.customer_offerlist(SPHelper.getSPData(activity,SPHelper.dealerid,""),
                    SPHelper.dpp_id,"1",SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    System.out.println("responsecode"+SPHelper.getSPData(activity,SPHelper.dealerid,"")+
                            SPHelper.dpp_id
                            +SPHelper.category_id);
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            offerModuleListArrayList=new ArrayList<>();
                            offerModuleListArrayList=appResponse.getResponse().getAllOfferList();
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                            adapterOfferModuleList=new AdapterOfferModuleList(offerModuleListArrayList,activity);
                            rv_offers_list.setLayoutManager(linearLayoutManager);
                            rv_offers_list.setAdapter(adapterOfferModuleList);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterOfferModuleList.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getCashBackDetails()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.customer_offerlist(SPHelper.getSPData(activity,SPHelper.dealerid,""),
                    SPHelper.dpp_id,cat_id,SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    System.out.println("responsecode"+SPHelper.getSPData(activity,SPHelper.dealerid,"")+
                            SPHelper.dpp_id
                            +SPHelper.category_id);
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                            rl_offer_module.setVisibility(View.VISIBLE);
                            idPBLoading.setVisibility(View.GONE);
                            cashbackofferlist=new ArrayList<>();
                            cashbackofferlist=appResponse.getResponse().getAllOfferList();
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                            adapterCashBackList=new AdapterCashBackList(cashbackofferlist,activity);
                            rv_max_details.setLayoutManager(linearLayoutManager);
                            rv_max_details.setAdapter(adapterCashBackList);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterOfferModuleList.notifyDataSetChanged();
                                }
                            });
                        }
                        else if (response_code.equals("300"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getOfferPresence()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getFtValidity(SPHelper.vehid,SPHelper.getSPData(activity,SPHelper.dealerid,""),
                    "2");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    System.out.println("responsecode"+appResponse.getResponseType());
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                            System.out.println("SPHelper.vehid"+SPHelper.vehid);
                             idPBLoading.setVisibility(View.GONE);
                             isvalid=appResponse.getResponse().getFastagValidity().getFastag_validity();
                             is_offer_taken=appResponse.getResponse().getOfferPresence().getIs_offer_taken();

                             //is valid is y,show fastag
                             //if is_offer_taken is y,hide cashback and addon

                        }
                        else if (response_code.equals("300"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getOfferCount()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getOfferCatList(SPHelper.getSPData(activity,SPHelper.dealerid,""),SPHelper.dpp_id,
                    "");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    System.out.println("responsecode"+appResponse.getResponseType());
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                            System.out.println("SPHelper.vehid"+SPHelper.vehid);
                            idPBLoading.setVisibility(View.GONE);
                            general_count=appResponse.getResponse().getGeneralCount().getCount();
                            cashback_count=appResponse.getResponse().getCashbackCount().getCount();
                            add_on_count=appResponse.getResponse().getAddonCount().getCount();

                            //is valid is y,show fastag
                            //if is_offer_taken is y,hide cashback and addon

                        }
                        else if (response_code.equals("300"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public  void buy_offer(){
        {
            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);
                PojoBuyOffer pojoBuyOffer=new PojoBuyOffer(SPHelper.vehid,SPHelper.getSPData(activity,SPHelper.dealerid,""),
                        "offline","","","cash","",String.valueOf(SPHelper.offer_price),SPHelper.offer_id,"paid","3234");
                Call<AppResponse> call =  apiInterface.purchaseOffer(pojoBuyOffer);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                                 offer_paymemnt_id=appResponse.getResponse().getOfferPaymentObj().getOffer_payment_id();
                                offer_id=appResponse.getResponse().getOfferPaymentObj().getOffer_id();
                               // dismiss();
                                Toast.makeText(activity, "You have purchased the offer", Toast.LENGTH_SHORT).show();
                                 isWithOffer = "Y";
                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void activate_package_toveh(){
        {

            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);
                getofferIdList();

                PojoCustomerVehicleInfo post=
                        new PojoCustomerVehicleInfo(SPHelper.getSPData(activity,SPHelper.dealerid,""),SPHelper.vehid,
                                "2",SPHelper.vehno,SPHelper.category_id,
                                entered_name.getText().toString(),entered_no.getText().toString(),aadharfronturl,
                                aadharbackurl, cust_adress.getText().toString().trim(),cust_pincode.getText().toString().trim(),
                                selected_city.getText().toString(),selected_state.getText().toString(),rcfronturl,rcbackurl,insuranceurl ,
                                entered_price.getText().toString(),entered_fastag_no.getText().toString(),fast_tagurl,panurl,
                                SPHelper.getSPData(activity,SPHelper.dealerid,""),"3",is_with_ft,cust_location.getText().toString(),
                                entered_pufr.getText().toString(),entered_price.getText().toString(),"",del_note_url,sale_re_url
                                ,ins_stat,ins_pro,ins_type,ins_pol,ext_frnt,SPHelper.getSPData(activity,SPHelper.dealerno,""),
                                "","",isWithOffer,SPHelper.dpp_id,entered_mail.getText().toString(),pojoOfferarray);
                Call<AppResponse> call =  apiInterface.sell_veh_tocustomer(post);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                               // dismiss();

//                                SoldVehDetails bottomSheetDialogFragment = new SoldVehDetails();
//                                bottomSheetDialogFragment.show(((FragmentActivity)activity).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                               /* SPHelper.activationcode=appResponse.getResponse().getVehicleObj().getActivationCode();
                                SPHelper.vehid=appResponse.getResponse().getVehicleObj().getVehicleId();
                                Intent intent=new Intent(activity, CongratulationsPage.class);
                                startActivity(intent);*/
                                SPHelper.cf_msg=appResponse.getResponse().getMessage();
                                SPHelper.isSuccess="activate";
                                SPHelper.camefrom="";
                                SPHelper.comingfrom="activated";
                                Intent intent=new Intent(activity,MainActivity.class);
                                startActivity(intent);

                               // CongratulationsPage bottomSheetDialogFragment1 = new CongratulationsPage();
                               // bottomSheetDialogFragment1.show(((FragmentActivity)activity).getSupportFragmentManager(), "CongratsPage");

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void getofferIdList()
    {
        //ArrayList<PojoOfferModuleList> pojoOfferModuleLists;
        pojoOfferarray=new ArrayList<PojoOfferarray>();

        for (int i = 0; i <cashbackofferlist.size(); i++)
        {
                if(cashbackofferlist.get(i).getIsSelected().equalsIgnoreCase("y")){
                    System.out.println("i1"+i);
                    PojoOfferarray obj1=new PojoOfferarray();
                    obj1.setOffer_id(cashbackofferlist.get(i).getOffer_id());
                    pojoOfferarray.add(obj1);
                }
        }
        for (int i = 0; i <offerModuleListArrayList.size(); i++)
        {
            if(offerModuleListArrayList.get(i).getIsSelected().equalsIgnoreCase("y")){
                System.out.println("i2"+i);
                PojoOfferarray obj2=new PojoOfferarray();
                obj2.setOffer_id(offerModuleListArrayList.get(i).getOffer_id());
                pojoOfferarray.add(obj2);
            }
        }
        if(pojoOfferarray.isEmpty()){
            isWithOffer = "N";

        }else{
            isWithOffer = "Y";

        }
        System.out.println("pojoOfferarray"+pojoOfferarray);
    }


    public static Activate getInstance() {
        return instance;
    }
}