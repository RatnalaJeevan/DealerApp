package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckEligibility extends AppCompatActivity {
    TextView heading1,label1;
    GifImageView iv_anim;
    RelativeLayout rl_check_status,rl_open_addcar,rl_checkstatus,rl_add_car,rl_buy_warranty,rl_req_insp,
            rl_check_gain;
    Activity activity;
    ImageView back;
    private DealerApis apiInterface;
    EditText entered_vehno;
    public  String  vehid="",category_id="",setimage ,entered_price="",ins_stat="",ins_pro="",ins_type="",ins_pol="",ext_frnt="";
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_eligibility);
        activity=CheckEligibility.this;
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_checkstatus=findViewById(R.id.rl_checkstatus);
        entered_vehno=findViewById(R.id.entered_vehno);
        label1=findViewById(R.id.label1);
        iv_anim=findViewById(R.id.iv_anim);
        back=findViewById(R.id.back);
        heading1=findViewById(R.id.heading1);
        rl_open_addcar=findViewById(R.id.rl_open_addcar);
        rl_buy_warranty=findViewById(R.id.rl_buy_warranty);
        rl_req_insp=findViewById(R.id.rl_req_insp);
        rl_check_gain=findViewById(R.id.rl_check_gain);
        rl_check_status=findViewById(R.id.rl_check_status);
        rl_add_car=findViewById(R.id.rl_add_car);

        //5 sec
        rl_check_status.setOnClickListener(view -> {

            //if vehno is not mi
            if(entered_vehno.getText().toString().equals("")){
                Toast.makeText(activity,
                        "Please Enter a Vehicle Number",
                        Toast.LENGTH_SHORT).show();
            }else if(entered_vehno.getText().toString().length()<6){
                Toast.makeText(activity,
                        "Please Enter Valid Vehicle Number",
                        Toast.LENGTH_SHORT).show();
            }else{
                check_eligibility();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="add";
                SPHelper.carbrandid="";
                SPHelper.carmodelid="";
                Intent intent=new Intent(CheckEligibility.this,AddNewCar.class);
                startActivity(intent);
            }
        });
        rl_req_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.vehno=entered_vehno.getText().toString();
                SPHelper.goneto="act";
                Intent intent=new Intent(CheckEligibility.this,RequestVehInspection.class);
                startActivity(intent);
            }
        });
        rl_buy_warranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="no_pack";
                Intent intent=new Intent(CheckEligibility.this,MainActivity.class);
                startActivity(intent);
            }
        });
        rl_check_gain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_vehno.setText("");
                rl_open_addcar.setVisibility(View.GONE);
                rl_checkstatus.setVisibility(View.VISIBLE);
            }
        });

    }

    public void check_eligibility()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.check_warranty_eligibility(SPHelper.getSPData(activity,SPHelper.dealerid,""),entered_vehno.getText().toString());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {

                        if (response_code.equals("200"))
                        {

                            if(appResponse.getResponse().getVehicleWEInfo().getVehicle_id()==null){

                            }else{
                                vehid=appResponse.getResponse().getVehicleWEInfo().getVehicle_id();
                                category_id=appResponse.getResponse().getVehicleWEInfo().getUci_category_id();
                                String vehmake=appResponse.getResponse().getVehicleWEInfo().getVehicle_make();
                                String vehmodel=appResponse.getResponse().getVehicleWEInfo().getVehicle_model();
                                String fueltype=appResponse.getResponse().getVehicleWEInfo().getFuel_type();
                                String year=appResponse.getResponse().getVehicleWEInfo().getManufacturing_year();
                                String transtype=appResponse.getResponse().getVehicleWEInfo().getTransmission_type();
                                String brandlogo=appResponse.getResponse().getVehicleWEInfo().getBrand_icon();

                                String cust_id=appResponse.getResponse().getVehicleWEInfo().getCustomer_id();

                                String customername="";
                                customername=appResponse.getResponse().getVehicleWEInfo().getCustomer_name();
                                String customerno=appResponse.getResponse().getVehicleWEInfo().getCustomer_phone_no();
                                String location=appResponse.getResponse().getVehicleWEInfo().getLocation();
                                String pincode=appResponse.getResponse().getVehicleWEInfo().getPincode();
                                String city=appResponse.getResponse().getVehicleWEInfo().getCity();
                                String state=appResponse.getResponse().getVehicleWEInfo().getState();

                                String rcfront=appResponse.getResponse().getVehicleWEInfo().getRc_front();
                                String rcback=appResponse.getResponse().getVehicleWEInfo().getRc_rear();
                                String aadharfront=appResponse.getResponse().getVehicleWEInfo().getCustomer_aadhaar_front();
                                String aadharback=appResponse.getResponse().getVehicleWEInfo().getCustomer_aadhaar_rear();
                                String insurancecopy=appResponse.getResponse().getVehicleWEInfo().getInsurance_copy();
                                String actualprice=appResponse.getResponse().getVehicleWEInfo().getActual_price();
                                String ext_front=appResponse.getResponse().getVehicleWEInfo().getVehicle_exterior_front_image();
                                String cool_days=appResponse.getResponse().getVehicleWEInfo().getCooling_period_days();
                                String cool_kms=appResponse.getResponse().getVehicleWEInfo().getCooling_period_kms();
                                String adress=appResponse.getResponse().getVehicleWEInfo().getCustomer_full_address();
                                String sare=appResponse.getResponse().getVehicleWEInfo().getSales_receipt();
                                String de_no=appResponse.getResponse().getVehicleWEInfo().getDelivery_note();
                                String pur_from=appResponse.getResponse().getVehicleWEInfo().getPurchased_from();
                                if(cust_id==null){
                                    SPHelper.customer_id="";
                                }
                                else{
                                    SPHelper.customer_id=cust_id;
                                }
                                SPHelper.category_id=category_id;
                                SPHelper.model_name=vehmodel;
                                SPHelper.fueltype=fueltype;
                                SPHelper.manufacture_year=year;
                                SPHelper.vehno=entered_vehno.getText().toString();
                                SPHelper.brandlogo=brandlogo;
                                SPHelper.vehid=vehid;
                                SPHelper.customer_name=customername;
                                SPHelper.customer_no=customerno;
                                SPHelper.cust_location=location;
                                SPHelper.cust_pincode=pincode;
                                SPHelper.cust_state=state;
                                SPHelper.cust_city=city;
                                SPHelper.rc_back=rcback;
                                SPHelper.aadhar_front=aadharfront;
                                SPHelper.aadhar_back=aadharback;
                                SPHelper.insu_copy=insurancecopy;
                                SPHelper.rc_front=rcfront;
                                SPHelper.ext_front=ext_front;
                                SPHelper.purchase_price=actualprice;
                                SPHelper.de_note=de_no;
                                SPHelper.sa_re=sare;
                                SPHelper.cust_adress=adress;
                                SPHelper.pur_from=pur_from;
                                if(cool_days==null){
                                    SPHelper.cool_no_ofdays="";
                                }
                                else{
                                    SPHelper.cool_no_ofdays=cool_days;
                                }
                                if(cool_kms==null){
                                    SPHelper.cool_kms="";
                                }
                                else{
                                    SPHelper.cool_kms=cool_kms;
                                }

                            }

//                            //dismiss();

                            Intent intent=new Intent(CheckEligibility.this,SelectPackages.class);
                            startActivity(intent);
                            activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
                            finish();
                            //set customer details
                        }
                        else if (response_code.equals("300"))
                        {

                            SPHelper.category_id="";
                            SPHelper.model_name="";
                            SPHelper.fueltype="";
                            SPHelper.manufacture_year="";
                            SPHelper.vehno="";
                            SPHelper.brandlogo="";
                            SPHelper.vehid="";
                            SPHelper.customer_name="";
                            SPHelper.customer_no="";
                            SPHelper.cust_location="";
                            SPHelper.cust_pincode="";
                            SPHelper.cust_state="";
                            SPHelper.cust_city="";
                            SPHelper.rc_back="";
                            SPHelper.aadhar_front="";
                            SPHelper.aadhar_back="";
                            SPHelper.insu_copy="";
                            SPHelper.rc_front="";
                            SPHelper.ext_front="";
                            SPHelper.cool_no_ofdays="";
                            SPHelper.cool_kms="";

                            String isveh_exists=appResponse.getResponse().getVehexist();
                            String is_insp_exist=appResponse.getResponse().getInspecexist();
                            String is_pack_exist=appResponse.getResponse().getPackexist();
                            String msg=appResponse.getResponse().getMessage();

                            label1.setText(msg);
                            if(isveh_exists!=null&&isveh_exists.equalsIgnoreCase("n")){
                                rl_open_addcar.setVisibility(View.VISIBLE);
                                rl_checkstatus.setVisibility(View.GONE);
                                rl_add_car.setVisibility(View.VISIBLE);
                                rl_req_insp.setVisibility(View.GONE);
                                rl_buy_warranty.setVisibility(View.GONE);
                                rl_check_gain.setVisibility(View.GONE);
                            }else if(is_insp_exist!=null&&is_insp_exist.equalsIgnoreCase("n")){
                                rl_open_addcar.setVisibility(View.VISIBLE);
                                rl_checkstatus.setVisibility(View.GONE);
                                rl_req_insp.setVisibility(View.VISIBLE);
                                rl_add_car.setVisibility(View.GONE);
                                rl_buy_warranty.setVisibility(View.GONE);
                                rl_check_gain.setVisibility(View.GONE);

                            }else if(is_pack_exist!=null&&is_pack_exist.equalsIgnoreCase("n")){
                                rl_open_addcar.setVisibility(View.VISIBLE);
                                rl_checkstatus.setVisibility(View.GONE);
                                rl_buy_warranty.setVisibility(View.VISIBLE);
                                rl_req_insp.setVisibility(View.GONE);
                                rl_buy_warranty.setVisibility(View.GONE);
                                rl_check_gain.setVisibility(View.GONE);
                            }else{
                                rl_open_addcar.setVisibility(View.VISIBLE);
                                rl_checkstatus.setVisibility(View.GONE);
                                rl_check_gain.setVisibility(View.VISIBLE);
                                rl_buy_warranty.setVisibility(View.GONE);
                                rl_req_insp.setVisibility(View.GONE);
                                rl_buy_warranty.setVisibility(View.GONE);
                            }

                        }
                    } else{
                       // idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    //idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }


    //     new Handle().postDelayed(new Runnable(
//            @Override
//            public void run() {
//        ImageView.setVisibility(View.GONE); //Or the GIF View
//    }
//        ), 5000);
}