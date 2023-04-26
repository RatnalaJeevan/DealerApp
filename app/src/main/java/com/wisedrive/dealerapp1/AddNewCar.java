package com.wisedrive.dealerapp1;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.wisedrive.dealerapp1.adapters.adapters.AdapterBrandLogos;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterCarImageList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterEditedVehImgList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterModel;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterVehicleImages;
import com.wisedrive.dealerapp1.adapters.adapters.UploadImageAdapter;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.BitmapUtility;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.RequestPermissionHandler;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarBrands;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarModels;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewCar extends AppCompatActivity {
    YearPickerDialog pd1 ;
    private DatePickerDialog.OnDateSetListener listener1;
    private DecimalFormat IndianCurrencyFormat;
    public TextView choose_year;
    String upload ="";
    String itis="";
    String final_year="";
    public ArrayList<String> finalids =new ArrayList<>();
    ProgressBar idPBLoading;
    public  boolean isServiceRunning = false;
    public  String KEY = "",onclick="";
    public  String SECRET = "";
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    public ArrayList<String> finalImages;
    public String insurance_status="";
    private Runnable runnableImage;
    private Handler handlerImage = new Handler();
    private TextView capture,heading1;
    private TextView deleteTxt;
    private TextView cancelTxt,takefromgallery;
    String is_from="",filename;
    private Uri imageUri;
    public int selectedObject=0,noofowners;
    private RequestPermissionHandler mRequestPermissionHandler;
    DatePickerDialog date_picker;
    RecyclerView rv_brand_logos,rv_car_imagelist,rv_veh_imagelist,rv_model_logos;
    public ArrayList<PojoVehicleImageList> imageLists;
    ArrayList<PojoCarImageList> carImageLists;
    AdapterCarImageList adapterCarImagesList;
    AdapterEditedVehImgList adapterVehicleImages;
    AdapterBrandLogos adapterBrandLogos;
    ArrayList<PojoAllCarBrands> pojoAllCarBrands;
    ArrayList<PojoAllCarModels> carModels;
    ArrayList<PojoSample> brandlogos;
    ArrayList<PojoSample> carmodel;
    AdapterModel adapterModel;
    RelativeLayout rl_back,rl_next,rl_add_car_details,rl_basic_details,rl_car_imgs,rl_ins_details,rl_addcar,rl_go_back,
            rl_make,rl_basic,rl_photos,rl_ins,rl_editcar;
    TextView tv_make,tv_basic,tv_photos,tv_ins;
    View v_make,v_basic,v_photos,v_ins,v_offers,v_cust,v_docs,v_purchase;
    public int cameto=1,act_no=1,req_page=1;
    public Activity activity;
    private DealerApis apiInterface;
    public ImageView iv_diesel,iv_petrol,iv_manual,iv_auto,img1,back,cancel_veh_info;
    EditText selected_year,selected_clr,selected_vehno,selected_kms,selected_owners,entered_pol_no,selected_sp;
    public RelativeLayout rl_exp_date,rl_show_popup,rl_transparent;
    Spinner entered_ins_type,entered_ins_provider;
    String fuel_type="",trans_type="";
    TextView ins_exp_date;
    public String selectedbankid="",selectedbankname="",selectedinsurancetype="",server_exp_date="";
    ArrayList<String> bankname,bankid,insurancetype;
    public  static AddNewCar instance;
    @SuppressLint("MissingInflatedId")
    @Nullable

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

        init_params();

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_show_popup.setVisibility(View.GONE);
                CallCamera();
            }
        });
        takefromgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                rl_show_popup.setVisibility(View.GONE);
                mRequestPermissionHandler.requestPermission(activity, new String[]
                        {
                                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, selectedObject, new RequestPermissionHandler.RequestPermissionListener()
                {
                    @Override
                    public void onSuccess() {
                        is_from="g";
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(pickPhoto,selectedObject);
                    }

                    @Override
                    public void onFailed() {
                        System.out.println("denied");
                    }
                });
            }
        });
        cancelTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });

        cancel_veh_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        iv_diesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuel_type="Diesel";
                iv_diesel.setImageDrawable(AppCompatResources.getDrawable(activity,R.drawable.black_tickmark));
                iv_diesel.setBackground(AppCompatResources.getDrawable(activity,R.drawable.blue_border));
                iv_petrol.setBackground(AppCompatResources.getDrawable(activity,R.drawable.map_border));
                iv_petrol.setImageDrawable(null);
            }
        });
        iv_petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuel_type="Petrol";
                iv_petrol.setImageDrawable(AppCompatResources.getDrawable(activity,R.drawable.black_tickmark));
                iv_petrol.setBackground(AppCompatResources.getDrawable(activity,R.drawable.blue_border));
                iv_diesel.setBackground(AppCompatResources.getDrawable(activity,R.drawable.map_border));
                iv_diesel.setImageDrawable(null);
            }
        });
        iv_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trans_type="Automatic";
                iv_auto.setImageDrawable(AppCompatResources.getDrawable(activity,R.drawable.black_tickmark));
                iv_auto.setBackground(AppCompatResources.getDrawable(activity,R.drawable.blue_border));
                iv_manual.setBackground(AppCompatResources.getDrawable(activity,R.drawable.map_border));
                iv_manual.setImageDrawable(null);
            }
        });
        iv_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trans_type="Manual";
                iv_manual.setImageDrawable(AppCompatResources.getDrawable(activity,R.drawable.black_tickmark));
                iv_manual.setBackground(AppCompatResources.getDrawable(activity,R.drawable.blue_border));
                iv_auto.setBackground(AppCompatResources.getDrawable(activity,R.drawable.map_border));
                iv_auto.setImageDrawable(null);
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameto--;
                show_pages();
                System.out.println("cameto"+cameto);
            }
        });

        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameto++;
                show_pages();
                System.out.println("cameto"+cameto);
            }
        });

        rl_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 cameto=1;
                 itis = "make";
                 show_pages();
            }
        });
        rl_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.camefrom.equals("edit")){
                     cameto=4;
                    show_pages();
                }else{
                        if(SPHelper.carbrandid.equals("")){
                            Common.CallToast(activity, "select brand", 1);
                        }else if(SPHelper.carmodelid.equals("")){
                            Common.CallToast(activity, "select car model", 1);
                        }else if(fuel_type.equals("")){
                            Common.CallToast(activity, "select fuel type", 1);
                        }else if(trans_type.equals("")){
                            Common.CallToast(activity, "select transmission type", 1);
                        }else if(choose_year.getText().toString().equals("")){
                            Common.CallToast(activity, "select Year", 1);
                        }else{
                            cameto=4;
                            rv_brand_logos.setVisibility(View.GONE);
                            rv_model_logos.setVisibility(View.GONE);
                            rl_add_car_details.setVisibility(View.GONE);
                            rl_car_imgs.setVisibility(View.GONE);
                            rl_basic_details.setVisibility(View.VISIBLE);
                            tv_basic.setTextColor(Color.parseColor("#FF000000"));
                            tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                            tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                            v_make.setVisibility(View.GONE);
                            v_basic.setVisibility(View.VISIBLE);
                            v_photos.setVisibility(View.GONE);
                            rl_next.setVisibility(View.VISIBLE);
                            rl_addcar.setVisibility(View.GONE);
                            rl_back.setVisibility(View.VISIBLE);
                            rl_ins_details.setVisibility(View.GONE);
                            tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                            v_photos.setVisibility(View.GONE);
                            v_ins.setVisibility(View.GONE);

                    }
                }
            }
        });
        rl_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.camefrom.equals("edit")){
                    cameto=5;
                    show_pages();
                }else{
                    if(SPHelper.carbrandid.equals("")){
                        Common.CallToast(activity, "select brand", 1);
                    }else if(SPHelper.carmodelid.equals("")){
                        Common.CallToast(activity, "select car model", 1);
                    }else if(fuel_type.equals("")){
                        Common.CallToast(activity, "select fuel type", 1);
                    }else if(trans_type.equals("")){
                        Common.CallToast(activity, "select transmission type", 1);
                    }else if(choose_year.getText().toString().equals("")){
                        Common.CallToast(activity, "select Year", 1);
                    }else if(selected_vehno.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter a Vehicle Number", 1);
                    }
                    else if(selected_vehno.getText().toString().startsWith("0")){
                        Common.CallToast(activity, "Enter a valid Vehicle Number", 1);
                    }
                    else if(selected_kms.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter  kms driven", 1);
                    }else if(selected_owners.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter no.of owners", 1);

                    }else {
                        cameto=5;
                        rv_brand_logos.setVisibility(View.GONE);
                        rv_model_logos.setVisibility(View.GONE);
                        rl_ins_details.setVisibility(View.GONE);
                        rl_car_imgs.setVisibility(View.VISIBLE);
                        rl_basic_details.setVisibility(View.GONE);
                        rl_next.setVisibility(View.VISIBLE);
                        rl_addcar.setVisibility(View.GONE);
                        tv_photos.setTextColor(Color.parseColor("#FF000000"));
                        tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                        tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                        v_ins.setVisibility(View.GONE);
                        v_basic.setVisibility(View.GONE);
                        v_photos.setVisibility(View.VISIBLE);
                        rl_back.setVisibility(View.VISIBLE);
                        rl_add_car_details.setVisibility(View.GONE);
                        tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                        tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                        v_make.setVisibility(View.GONE);
                    }
                }
            }
        });
        rl_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.camefrom.equals("edit")){
                    cameto=6;
                    show_pages();
                }else{
                    if(SPHelper.carbrandid.equals("")){
                        Common.CallToast(activity, "select brand", 1);
                    }else if(SPHelper.carmodelid.equals("")){
                        Common.CallToast(activity, "select car model", 1);
                    }else if(fuel_type.equals("")){
                        Common.CallToast(activity, "select fuel type", 1);
                    }else if(trans_type.equals("")){
                        Common.CallToast(activity, "select transmission type", 1);
                    }else if(choose_year.getText().toString().equals("")){
                        Common.CallToast(activity, "select Year", 1);
                    }else if(selected_vehno.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter a Vehicle Number", 1);
                    }
                    else if(selected_vehno.getText().toString().startsWith("0")){
                        Common.CallToast(activity, "Enter a valid Vehicle Number", 1);
                    }else if(selected_kms.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter  kms driven", 1);
                    }else if(selected_owners.getText().toString().equals("")){
                        Common.CallToast(activity, "Enter no.of owners", 1);

                    }else {
                        cameto=6;
                        rv_brand_logos.setVisibility(View.GONE);
                        rv_model_logos.setVisibility(View.GONE);
                        rl_next.setVisibility(View.INVISIBLE);
                        rl_car_imgs.setVisibility(View.GONE);
                        rl_ins_details.setVisibility(View.VISIBLE);
                        rl_addcar.setVisibility(View.VISIBLE);
                        tv_ins.setTextColor(Color.parseColor("#FF000000"));
                        tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                        v_ins.setVisibility(View.VISIBLE);
                        v_photos.setVisibility(View.GONE);
                        rl_back.setVisibility(View.VISIBLE);
                        rl_add_car_details.setVisibility(View.GONE);
                        rl_basic_details.setVisibility(View.GONE);
                        tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                        tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                        v_make.setVisibility(View.GONE);
                        v_basic.setVisibility(View.GONE);

                    }
                }

            }
        });
        rl_addcar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                System.out.println("upload"+upload);
               // SPHelper.ins_exp_date=server_exp_date;
                if(selectedinsurancetype.equals("Select Insurance type")){
                    selectedinsurancetype="";
                }
               if(selectedbankname.equals("Select BankName")){
                   selectedbankname="";
               }
               if(server_exp_date.equals("")){
                   insurance_status="";
               }else{
                   insurance_status="current";
               }
                if(SPHelper.carbrandid.equals("")){
                    Toast.makeText(activity,
                            " select Car Make",
                            Toast.LENGTH_SHORT).show();
                }
                else if(SPHelper.carmodelid.equals("")){
                    Toast.makeText(activity,
                            " select Car Model",
                            Toast.LENGTH_SHORT).show();
                }
                else  if(fuel_type.equals("")){

                    Toast.makeText(activity,
                            " select Fuel type",
                            Toast.LENGTH_SHORT).show();
                }else if(trans_type.equals("")){
                    Toast.makeText(activity,
                            " select Transmission type",
                            Toast.LENGTH_SHORT).show();
                }else if(choose_year.getText().toString().equals("")){
                    Toast.makeText(activity,
                            " Enter Manufaturinng year",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_vehno.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter Vehicle Number",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_vehno.getText().toString().startsWith("0")){
                    Common.CallToast(activity, "Enter a valid Vehicle Number", 1);
                }
                else if(selected_vehno.getText().toString().length()<6){
                    Toast.makeText(activity,
                            "Enter Valid Vehicle Number",
                            Toast.LENGTH_SHORT).show();
                }

                else if(selected_kms.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter No.of Kms driven",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_kms.getText().toString().startsWith("0")){
                    Toast.makeText(activity,
                            "Enter valid kms driven",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_owners.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter No.of Owners",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_owners.getText().toString().startsWith("0"))
                {
                    Toast.makeText(activity,
                            "No.of Owners should not be '0",
                            Toast.LENGTH_SHORT).show();
                }
                else if (upload.equals("y"))
                {
                    Upload_after_check();
                } else {
                    add_car();
                }
            }
        });

        rl_editcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("upload"+upload);
                if(SPHelper.ins_exp_date.equals("")){
                    insurance_status="";
                }else{
                    insurance_status="current";
                }
                if(selectedinsurancetype.equals("Select Insurance type")){
                    selectedinsurancetype="";
                }
                if(selectedbankname.equals("Select BankName")){
                    selectedbankname="";
                }
              if(selected_kms.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter No.of Kms driven",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_kms.getText().toString().startsWith("0")){
                    Toast.makeText(activity,
                            "Enter valid kms driven",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_owners.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter No.of Owners",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_owners.getText().toString().startsWith("0"))
                {
                    Toast.makeText(activity,
                            "No.of Owners should not be '0",
                            Toast.LENGTH_SHORT).show();
                }

                else if (upload.equals("y"))
                {
                    Upload_after_check();
                } else {
                    edit_car();
                }
                System.out.println("upload"+upload);
            }
        });


        get_Insurance_Details();
        servicecall_getbankname();
        //call veh

        entered_ins_provider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String item = parent.getItemAtPosition(position).toString();
//                if (position == 0) {
//                    tv.setTextColor(Color.rgb(191, 189, 184));
//                    tv.setTextSize(15);
//                    tv.setTypeface(Typeface.DEFAULT);
//                } else {
//                    tv.setTextColor(Color.rgb(0, 0, 0));
//                    tv.setTextSize(15);
//                    tv.setTypeface(Typeface.DEFAULT);
//                }
                if(entered_ins_provider.getSelectedItemPosition()>0){
                    selectedbankid=bankid.get(position);
                    selectedbankname=bankname.get(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        entered_ins_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                String item = parent.getItemAtPosition(position).toString();
//                if (position == 0) {
//                    tv.setTextColor(Color.rgb(191, 189, 184));
//                    tv.setTextSize(15);
//                    tv.setTypeface(Typeface.DEFAULT);
//                } else {
//                    tv.setTextColor(Color.rgb(0, 0, 0));
//                    tv.setTextSize(15);
//                    tv.setTypeface(Typeface.DEFAULT);
//                }

                if (entered_ins_type.getSelectedItemPosition() > 0) {
                    selectedinsurancetype = insurancetype.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        rl_exp_date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                //Theme_Material3_Light_Dialog_Alert
                //Theme_Material3_DayNight_DialogWhenLarge
                date_picker = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                ins_exp_date.setText(Common.getDateFromString1(dayOfMonth + "-" + (monthOfYear + 1) + "-" +year));
                                server_exp_date=year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                SPHelper.ins_exp_date=server_exp_date;
                            }
                        }, year, month, day);
                date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                date_picker.show();
            }
        });

        rl_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(SPHelper.camefrom.equals("edit"))
        {
            cameto=4;
            heading1.setText("You are changing the \ndetails of the car");
            if(SPHelper.selling_price.equals("null")||SPHelper.selling_price.equals("")){
                selected_sp.setText("");
            }else{
                double s_price= Double.parseDouble(SPHelper.selling_price);
                int y=(int)s_price;
                selected_sp.setText(String.valueOf(y));
            }
            if(SPHelper.kmsdriven.equals("null")){
                selected_kms.setText("");
            }else{
                selected_kms.setText(SPHelper.kmsdriven);
            }
            if(SPHelper.no_owners.equals("null")||
                    SPHelper.no_owners.equals("")){
                // selected_owners.setText("");
            }else{
                selected_owners.setText(SPHelper.no_owners);
            }
            if(SPHelper.veh_color.equals("null")){
                // selected_clr.setText("");
            }else{
                selected_clr.setText(SPHelper.veh_color);
            }
            if(SPHelper.insu_pol.equals("null")){
                // entered_pol_no.setText("");
            }else{
                entered_pol_no.setText(SPHelper.insu_pol);
            }
            if(SPHelper.insurancetype.equals("null")||SPHelper.insurancetype.equals("")){
                selectedinsurancetype="Select Insurance type";

            }else{
                selectedinsurancetype=SPHelper.insurancetype;
            }
            if(SPHelper.insurance_provider.equals("null")||SPHelper.insurancetype.equals("")){
                selectedbankname="Select BankName";
            }else{
                selectedbankname=SPHelper.insurance_provider;
            }
            if(SPHelper.ins_exp_date.equals("null")){
                SPHelper.ins_exp_date="";
            }else{
                ins_exp_date.setText(Common.getDateFromString2(SPHelper.ins_exp_date));
            }
            show_pages();
            get_veh_images_list();
        }else{
            if(SPHelper.vehno.equals("")){
                selected_vehno.setFocusable(true);
            }else{
                selected_vehno.setFocusable(false);
                selected_vehno.setText(SPHelper.vehno);
            }
            SPHelper.insurance_provider="";
            SPHelper.insurancetype="";
            get_carbrands_list();
            heading1.setText("You are adding a \nnew car...");
            get_carimage_list();
        }

        choose_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // createDialogWithoutDateField().show();
                pd1.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
    }

    private void init_params()
    {
        pd1 = new YearPickerDialog();
        pd1.setListener(listener1);
        choose_year=findViewById(R.id.choose_year);
        IndianCurrencyFormat = new DecimalFormat("##,##,###");
        activity=AddNewCar.this;
        instance=this;
        bankname = new ArrayList<>();
        bankid = new ArrayList<>();
        insurancetype = new ArrayList<>();
        SPHelper.sharedPreferenceInitialization(activity);
        rl_go_back=findViewById(R.id.rl_go_back);
        cancel_veh_info=findViewById(R.id.cancel_veh_info);
        rv_model_logos=findViewById(R.id.rv_model_logos);
        heading1=findViewById(R.id.heading1);
        capture =  findViewById(R.id.Recapture);
        cancelTxt =  findViewById(R.id.cancel);
        takefromgallery=  findViewById(R.id.takefromgallery);
        back=findViewById(R.id.back);
        KEY=SPHelper.getSPData(activity,SPHelper.awskey,"");
        SECRET=SPHelper.getSPData(activity,SPHelper.awssecret,"");

        //add car images
        //upload="";
        SPHelper.saveSPdata(activity, SPHelper.imagestaken, "");
        File dir = new File(BitmapUtility.PictUtil.getSavePath().getPath());
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        }
        AWSMobileClient.getInstance().initialize(activity).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(activity,SPHelper.awskey,""),
                SPHelper.getSPData(activity,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        mRequestPermissionHandler = new RequestPermissionHandler();
        rl_editcar=findViewById(R.id.rl_editcar);
        selected_sp=findViewById(R.id.selected_sp);
        img1=findViewById(R.id.img1);
        idPBLoading=findViewById(R.id.idPBLoading);
        rv_veh_imagelist=findViewById(R.id.rv_veh_imagelist);
        rl_show_popup=findViewById(R.id.rl_show_popup);
        rl_transparent=findViewById(R.id.rl_transparent);
        rl_exp_date=findViewById(R.id.rl_exp_date);
        rv_car_imagelist=findViewById(R.id.rv_car_imagelist);
        entered_ins_type=findViewById(R.id.entered_ins_type);
        entered_ins_provider=findViewById(R.id.entered_ins_provider);
        entered_pol_no=findViewById(R.id.entered_pol_no);
        selected_clr=findViewById(R.id.selected_clr);
        ins_exp_date=findViewById(R.id.ins_exp_date);
        selected_vehno=findViewById(R.id.selected_vehno);
        selected_kms=findViewById(R.id.selected_kms);
        selected_owners=findViewById(R.id.selected_owners);
        rl_addcar=findViewById(R.id.rl_addcar);
        tv_make=findViewById(R.id.tv_make);
        tv_basic=findViewById(R.id.tv_basic);
        tv_photos=findViewById(R.id.tv_photos);
        tv_ins=findViewById(R.id.tv_ins);
        v_make=findViewById(R.id.v_make);
        v_basic=findViewById(R.id.v_basic);
        v_photos=findViewById(R.id.v_photos);
        v_ins=findViewById(R.id.v_ins);
        rl_next=findViewById(R.id.rl_next);
        rl_back=findViewById(R.id.rl_back);
        rl_ins_details=findViewById(R.id.rl_ins_details);
        rl_add_car_details=findViewById(R.id.rl_add_car_details);
        rl_basic_details=findViewById(R.id.rl_basic_details);
        rl_car_imgs=findViewById(R.id.rl_car_imgs);
        rv_brand_logos=findViewById(R.id.rv_brand_logos);
        iv_diesel=findViewById(R.id.iv_diesel);
        iv_petrol=findViewById(R.id.iv_petrol);
        iv_manual=findViewById(R.id.iv_manual);
        iv_auto=findViewById(R.id.iv_auto);
        rl_ins=findViewById(R.id.rl_ins);
        rl_make=findViewById(R.id.rl_make);
        rl_basic=findViewById(R.id.rl_basic);
        rl_photos=findViewById(R.id.rl_photos);
        selected_year=findViewById(R.id.selected_year);
    }

    public void show_pages()
    {
        //if coming from edit

        if(SPHelper.camefrom.equals("edit"))
        {
            rl_make.setVisibility(View.INVISIBLE);
            rl_basic_details.setVisibility(View.VISIBLE);
            rv_brand_logos.setVisibility(View.INVISIBLE);
            rv_model_logos.setVisibility(View.GONE);
            rl_add_car_details.setVisibility(View.INVISIBLE);
            selected_vehno.setText(SPHelper.vehno);
            selected_vehno.setFocusable(false);

            if(cameto==4){
                tv_basic.setTextColor(Color.parseColor("#FF000000"));
                tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                v_basic.setVisibility(View.VISIBLE);
                v_photos.setVisibility(View.INVISIBLE);
                rl_editcar.setVisibility(View.INVISIBLE);
                rl_car_imgs.setVisibility(View.GONE);
                rl_ins_details.setVisibility(View.INVISIBLE);
                tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                v_photos.setVisibility(View.INVISIBLE);
                v_ins.setVisibility(View.GONE);
                rl_back.setVisibility(View.INVISIBLE);
            }
            //photos
            else if (cameto == 5) {
                rl_ins_details.setVisibility(View.GONE);
                rl_car_imgs.setVisibility(View.VISIBLE);
                rl_basic_details.setVisibility(View.GONE);
                rl_back.setVisibility(View.VISIBLE);
                rl_next.setVisibility(View.VISIBLE);
                rl_editcar.setVisibility(View.GONE);
                tv_photos.setTextColor(Color.parseColor("#FF000000"));
                tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                v_ins.setVisibility(View.GONE);
                v_basic.setVisibility(View.GONE);
                v_photos.setVisibility(View.VISIBLE);
                rl_add_car_details.setVisibility(View.GONE);
                tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                v_make.setVisibility(View.GONE);
            }
            //insuran
            else if (cameto == 6) {
                rl_car_imgs.setVisibility(View.GONE);
                rl_ins_details.setVisibility(View.VISIBLE);
                rl_editcar.setVisibility(View.VISIBLE);
                tv_ins.setTextColor(Color.parseColor("#FF000000"));
                tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                v_ins.setVisibility(View.VISIBLE);
                v_photos.setVisibility(View.GONE);
                rl_add_car_details.setVisibility(View.GONE);
                rl_basic_details.setVisibility(View.GONE);
                tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                v_make.setVisibility(View.GONE);
                v_basic.setVisibility(View.GONE);
                rl_back.setVisibility(View.VISIBLE);
                rl_next.setVisibility(View.INVISIBLE);

            }
        }

        else if(SPHelper.camefrom.equals("add"))
        {
            //carmake
            if (cameto == 1 ) {
                rl_next.setVisibility(View.VISIBLE);
                rl_addcar.setVisibility(View.GONE);
                rl_back.setVisibility(View.GONE);
                rl_add_car_details.setVisibility(View.GONE);
                rl_basic_details.setVisibility(View.GONE);
                rl_car_imgs.setVisibility(View.GONE);
                rl_ins_details.setVisibility(View.GONE);
                tv_make.setTextColor(Color.parseColor("#FF000000"));
                tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                v_make.setVisibility(View.VISIBLE);
                v_basic.setVisibility(View.GONE);
                v_photos.setVisibility(View.GONE);
                v_ins.setVisibility(View.GONE);
                if(!SPHelper.carbrandid.equals("")){
                    rv_brand_logos.setVisibility(View.VISIBLE);
                    rv_model_logos.setVisibility(View.GONE);
                }else{
                    get_carbrands_list();
                }

            }
            //carmodel
            else if (cameto == 2 )
            {
                if (SPHelper.carbrandid.equals("")) {
                    cameto--;
                    Common.CallToast(activity, "select brand", 1);
                } else {
                    if(!SPHelper.carmodelid.equals("")){
                        rv_brand_logos.setVisibility(View.GONE);
                        rv_model_logos.setVisibility(View.VISIBLE);
                    }else{
                        get_carmodel_list();
                    }

                    rl_back.setVisibility(View.VISIBLE);
                    rl_add_car_details.setVisibility(View.GONE);
                    rl_next.setVisibility(View.VISIBLE);
                    rl_addcar.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_car_imgs.setVisibility(View.GONE);
                    rl_ins_details.setVisibility(View.GONE);
                    tv_make.setTextColor(Color.parseColor("#FF000000"));
                    tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                    v_make.setVisibility(View.VISIBLE);
                    v_basic.setVisibility(View.GONE);
                    v_photos.setVisibility(View.GONE);
                    v_ins.setVisibility(View.GONE);
                }

            }
            //fuel petrol
            else if (cameto == 3)
            {
                if (SPHelper.carmodelid.equals("")) {
                    cameto--;
                    Common.CallToast(activity, "select model", 1);
                } else {
                    rv_brand_logos.setVisibility(View.GONE);
                    rv_model_logos.setVisibility(View.GONE);
                    rl_add_car_details.setVisibility(View.VISIBLE);
                    rl_basic_details.setVisibility(View.GONE);
                    tv_make.setTextColor(Color.parseColor("#FF000000"));
                    v_make.setVisibility(View.VISIBLE);
                    v_basic.setVisibility(View.GONE);
                    tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                    rl_next.setVisibility(View.VISIBLE);
                    rl_addcar.setVisibility(View.GONE);
                    rl_back.setVisibility(View.VISIBLE);
                    rl_car_imgs.setVisibility(View.GONE);
                    rl_ins_details.setVisibility(View.GONE);
                    tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                    v_photos.setVisibility(View.GONE);
                    v_ins.setVisibility(View.GONE);
                }

            }
            //basic
            else if (cameto == 4 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select transmission type", 1);
                }else if(choose_year.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "select Year", 1);
                }else{
                    rv_brand_logos.setVisibility(View.GONE);
                    rv_model_logos.setVisibility(View.GONE);
                    rl_add_car_details.setVisibility(View.GONE);
                    rl_car_imgs.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.VISIBLE);
                    tv_basic.setTextColor(Color.parseColor("#FF000000"));
                    tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                    v_make.setVisibility(View.GONE);
                    v_basic.setVisibility(View.VISIBLE);
                    v_photos.setVisibility(View.GONE);
                    rl_next.setVisibility(View.VISIBLE);
                    rl_addcar.setVisibility(View.GONE);
                    rl_back.setVisibility(View.VISIBLE);
                    rl_ins_details.setVisibility(View.GONE);
                    tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                    v_photos.setVisibility(View.GONE);
                    v_ins.setVisibility(View.GONE);
                }

            }
            //photos
            else if (cameto == 5 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select transmission type", 1);
                }else if(choose_year.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter a Vehicle Number", 1);
                }
                else if(selected_vehno.getText().toString().startsWith("0")){
                    Common.CallToast(activity, "Enter a valid Vehicle Number", 1);
                }
                else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter no.of owners", 1);

                }else {
                    rv_brand_logos.setVisibility(View.GONE);
                    rv_model_logos.setVisibility(View.GONE);
                    rl_ins_details.setVisibility(View.GONE);
                    rl_car_imgs.setVisibility(View.VISIBLE);
                    rl_basic_details.setVisibility(View.GONE);
                    rl_next.setVisibility(View.VISIBLE);
                    rl_addcar.setVisibility(View.GONE);
                    tv_photos.setTextColor(Color.parseColor("#FF000000"));
                    tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_ins.setTextColor(Color.parseColor("#D3D3D3"));
                    v_ins.setVisibility(View.GONE);
                    v_basic.setVisibility(View.GONE);
                    v_photos.setVisibility(View.VISIBLE);
                    rl_back.setVisibility(View.VISIBLE);
                    rl_add_car_details.setVisibility(View.GONE);
                    tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                    v_make.setVisibility(View.GONE);
                }
            }
            //insuran
            else if (cameto == 6 ) {
                if(SPHelper.carbrandid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select brand", 1);
                }else if(SPHelper.carmodelid.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select car model", 1);
                }else if(fuel_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select fuel type", 1);
                }else if(trans_type.equals("")){
                    cameto--;
                    Common.CallToast(activity, "select transmission type", 1);
                }else if(choose_year.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "select Year", 1);
                }else if(selected_vehno.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter a Vehicle Number", 1);
                } else if(selected_vehno.getText().toString().startsWith("0")){
                    Common.CallToast(activity, "Enter a valid Vehicle Number", 1);
                }
                else if(selected_kms.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter  kms driven", 1);
                }else if(selected_owners.getText().toString().equals("")){
                    cameto--;
                    Common.CallToast(activity, "Enter no.of owners", 1);

                }else {

                    rv_brand_logos.setVisibility(View.GONE);
                    rv_model_logos.setVisibility(View.GONE);
                    rl_next.setVisibility(View.INVISIBLE);
                    rl_car_imgs.setVisibility(View.GONE);
                    rl_ins_details.setVisibility(View.VISIBLE);
                    rl_addcar.setVisibility(View.VISIBLE);
                    tv_ins.setTextColor(Color.parseColor("#FF000000"));
                    tv_photos.setTextColor(Color.parseColor("#D3D3D3"));
                    v_ins.setVisibility(View.VISIBLE);
                    v_photos.setVisibility(View.GONE);
                    rl_back.setVisibility(View.VISIBLE);
                    rl_add_car_details.setVisibility(View.GONE);
                    rl_basic_details.setVisibility(View.GONE);
                    tv_make.setTextColor(Color.parseColor("#D3D3D3"));
                    tv_basic.setTextColor(Color.parseColor("#D3D3D3"));
                    v_make.setVisibility(View.GONE);
                    v_basic.setVisibility(View.GONE);

                }
            }

        }
    }

    public  void get_carbrands_list() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
               // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_brandlist();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                               // progress_bar.setVisibility(View.GONE);
                                rv_model_logos.setVisibility(View.GONE);
                                rv_brand_logos.setVisibility(View.VISIBLE);
                                brandlogos=new ArrayList<>();
                                pojoAllCarBrands=appResponse.getResponse().getBrandList();

                                GridLayoutManager gridLayoutManager=new GridLayoutManager(activity,4);
                                rv_brand_logos.setLayoutManager(gridLayoutManager);
                                adapterBrandLogos=new AdapterBrandLogos(pojoAllCarBrands,activity);
                                rv_brand_logos.setAdapter(adapterBrandLogos);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterBrandLogos.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                               // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                           // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                       // progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_carmodel_list()
    {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
               // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_modellist(SPHelper.carbrandid);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                              //  progress_bar.setVisibility(View.GONE);
                                rv_model_logos.setVisibility(View.VISIBLE);
                                rv_brand_logos.setVisibility(View.GONE);
                                carModels=new ArrayList<>();
                                carModels=appResponse.getResponse().getModelList();
                                GridLayoutManager gridLayoutManager=new GridLayoutManager(activity,3);
                                rv_model_logos.setLayoutManager(gridLayoutManager);
                                adapterModel=new AdapterModel(carModels,activity);
                                rv_model_logos.setAdapter(adapterModel);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterModel.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                               // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                           // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_carimage_list()
    {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
               // idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_imagelist();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                //idPBLoading.setVisibility(View.GONE);
                                rv_veh_imagelist.setVisibility(View.GONE);
                                rv_car_imagelist.setVisibility(View.VISIBLE);
                                carImageLists=new ArrayList<>();
                                carImageLists=appResponse.getResponse().getGetvehicleimages();
                                adapterCarImagesList = new AdapterCarImageList(carImageLists,activity);
                                GridLayoutManager linearLayoutManager=new GridLayoutManager(activity,4);
                                rv_car_imagelist.setLayoutManager(linearLayoutManager);
                                rv_car_imagelist.setAdapter(adapterCarImagesList);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterCarImagesList.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                               // idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                       // idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    public void get_veh_images_list()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.get_vehicleImagelist(SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
//                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            //appResponse.getResponse().getVehicleImageList().
                            imageLists=new ArrayList<>();
                            imageLists=appResponse.getResponse().getVehicleImageList();
                            rv_veh_imagelist.setVisibility(View.VISIBLE);
                            rv_car_imagelist.setVisibility(View.GONE);
                            if(imageLists.isEmpty())
                            {
                                get_carimage_list();
                            }else {
                                adapterVehicleImages = new AdapterEditedVehImgList(imageLists,activity);
                                GridLayoutManager linearLayoutManager=new GridLayoutManager(activity,4);
                                rv_veh_imagelist.setLayoutManager(linearLayoutManager);
                                rv_veh_imagelist.setAdapter(adapterVehicleImages);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterVehicleImages.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else if (response_code.equals("300"))
                        {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    private void get_Insurance_Details() {
        if(SPHelper.insurancetype.equals("null")||SPHelper.insurancetype.equals("")){
            selectedinsurancetype="Select Insurance type";

        }else{
            selectedinsurancetype=SPHelper.insurancetype;
        }
        insurancetype.add(selectedinsurancetype);
        insurancetype.add("Comprehensive - Standard");
        insurancetype.add("Comprehensive - Cover");
        insurancetype.add("Third Party Liability");
        insurancetype.add("Own Damage Cover");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, insurancetype);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        entered_ins_type.setAdapter(dataAdapter3);
    }

    public void servicecall_getbankname() {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_insurancelist();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            //idPBLoading.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            bankname.clear();
                            bankid.clear();

                            if(SPHelper.insurance_provider.equals("null")||SPHelper.insurancetype.equals("")){
                                selectedbankname="Select BankName";
                            }else{
                                selectedbankname=SPHelper.insurance_provider;
                            }
                            bankname.add(selectedbankname);
                            bankid.add("0");

                            for (int i = 0; i < appResponse.getResponse().getProviderList().size(); i++)
                            {
                                bankname.add(appResponse.getResponse().getProviderList().get(i).getInsurance_provider());
                                bankid.add(appResponse.getResponse().getProviderList().get(i).getId());
                            }
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status();
                                }
                            });
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
    public void status()
    {
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, bankname);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        entered_ins_provider.setAdapter(dataAdapter3);
    }

    void CallCamera() {

        mRequestPermissionHandler.requestPermission(activity, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
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
    void openCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            is_from="c";
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + "CarService" + fineName;
            imageUri = FileProvider.getUriForFile(activity,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, selectedObject);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && is_from.equals("c"))
        {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    upload ="y";
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                    imageUri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    img1.setImageURI(imageUri);
                    if(SPHelper.camefrom.equals("add")){
                        carImageLists.get(adapterCarImagesList.adapter_position).setImage(imageUri);
                        carImageLists.get(adapterCarImagesList.adapter_position).setFilename(OriginalFileName);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterCarImagesList.notifyDataSetChanged();
                            }
                        });
                    }else{
                        imageLists.get(adapterVehicleImages.adapter_position).setImage(imageUri);
                        imageLists.get(adapterVehicleImages.adapter_position).setFilename(OriginalFileName);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterVehicleImages.notifyDataSetChanged();
                            }
                        });
                    }
                    idPBLoading.setVisibility(View.GONE);
                }
            });
        }
        else if ( resultCode == RESULT_OK &&  is_from.equals("g"))
        {
            upload="y";
            Uri imageUri = data.getData();
            SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
            String OriginalFileName = null;
            try {
                OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(activity, imageUri, filename, new Pair<Integer, Integer>(800, 800), "/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(SPHelper.camefrom.equals("add"))
            {
                carImageLists.get(adapterCarImagesList.adapter_position).setImage(imageUri);
                carImageLists.get(adapterCarImagesList.adapter_position).setFilename(String.valueOf(OriginalFileName));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterCarImagesList.notifyDataSetChanged();
                    }
                });
            }else{
                imageLists.get(adapterVehicleImages.adapter_position).setImage(imageUri);
                imageLists.get(adapterVehicleImages.adapter_position).setFilename(String.valueOf(OriginalFileName));
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterVehicleImages.notifyDataSetChanged();
                    }
                });
            }
            img1.setImageURI(imageUri);
            idPBLoading.setVisibility(View.GONE);
        }
    }
    public ProgressDialog mDialog;
    public void Upload_after_check()
    {
        int totalImagesCount = 0;

        if(SPHelper.camefrom.equals("add")) {
            for (PojoCarImageList check : carImageLists) {
                if (check.getImage() != null) {
                    totalImagesCount++;
                }
            }
        }
        else{
            for (PojoVehicleImageList check : imageLists) {
                if (check.getImage() != null) {
                    totalImagesCount++;
                }
            }
        }
        if (totalImagesCount < 1)
        {
            Common.CallToast(activity, "Upload atleast 1 image", 3);
            return;
        }
        if (mDialog == null) {
            mDialog = new ProgressDialog(activity);
            mDialog.setMessage("Please wait...");
            mDialog.setCancelable(false);
        }
        mDialog.show();
        final String CheckVerification = "N";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final JSONArray uploadImages = new JSONArray();
                if(SPHelper.camefrom.equals("add"))
                {
                    for (PojoCarImageList imageUploadDataModel : carImageLists) {
                        if (imageUploadDataModel.getImage() != null) {
                            String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(imageUploadDataModel.getFilename(), new Pair<Integer, Integer>(1500, 1500), "/");
                            uploadImages.put(OriginalFileName);
                        }
                    }
                }else{
                    for (PojoVehicleImageList imageUploadDataModel : imageLists) {
                        if (imageUploadDataModel.getImage() != null) {
                            String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(imageUploadDataModel.getFilename(), new Pair<Integer, Integer>(1500, 1500), "/");
                            uploadImages.put(OriginalFileName);
                        }
                    }
                }


                mDialog.dismiss();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SPHelper.saveSPdata(activity, SPHelper.imagestaken, uploadImages.toString());
                        checkupload();
                    }
                });
            }
        });
    }

    public void checkupload()
    {
        //Toast.makeText(activity, "check upload method started", Toast.LENGTH_SHORT).show();

        if (!Connectivity.isNetworkConnected(activity)) {
            Toast.makeText(activity, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SPHelper.getSPData(activity, SPHelper.imagestaken, "").trim().length() > 0)
        {
            try {
                JSONArray images = new JSONArray(SPHelper.getSPData(activity, SPHelper.imagestaken, ""));
                if (images.length() >1||images.length()==1) {
                    finalImages = new ArrayList<>();
                    //Toast.makeText(activity, "going to check upload", Toast.LENGTH_SHORT).show();

                    countDownStart();
                    return;
                }
            } catch (JSONException JE) {
                JE.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "Please capture all 4 images", Toast.LENGTH_SHORT).show();

        }
    }
    private void countDownStart()
    {
        // Toast.makeText(AddCarPage.this, "count down method started", Toast.LENGTH_SHORT).show();

        idPBLoading.setVisibility(View.VISIBLE);
        runnableImage = new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    if (!isServiceRunning)
                    {
                        if (!Connectivity.isNetworkConnected(activity))
                        {
                            handlerImage.removeCallbacks(this);
                            idPBLoading.setVisibility(View.GONE);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                            builder1.setMessage("Please retry to Submit your Details");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    countDownStart();
                                }
                            });
                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            return;
                        }
                        JSONArray images = new JSONArray(SPHelper.getSPData(activity, SPHelper.imagestaken, ""));
                        if ( finalImages.size() == images.length())
                        {
                            for(int i= 0;i<finalImages.size();i++)
                            {
                                if(SPHelper.camefrom.equals("add"))
                                {
                                    carImageLists.get(i).setImageurl(finalImages.get(i));
                                }
                                else{
                                    imageLists.get(i).setImageurl(finalImages.get(i));
                                }
                            }
                            if(SPHelper.camefrom.equals("edit"))
                            {
                                edit_car_withimage();
                            }else{
                                servicecallwithimage();
                            }
                        }
                        else {
                            Toast.makeText(activity, "uploading image", Toast.LENGTH_SHORT).show();
                            upload(images.getString(finalImages.size()));
                        }
                    }
                    handlerImage.postDelayed(this, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                    handlerImage.postDelayed(this, 3000);
                }
            }
        };
        handlerImage.postDelayed(runnableImage, 500);
    }
    public void upload(final String filename)
    {
        // Toast.makeText(getApplicationContext(), "Image Upload started", Toast.LENGTH_SHORT).show();

        isServiceRunning = true;
        // StorageReference storageRef = storage.getReference();
        File imageFile = new File(filename);
        Uri uri = Uri.fromFile(imageFile);
        try {
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(activity)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = "Dealer_VehicleImage/" +SPHelper.getSPData(activity, SPHelper.dealerid, "")+ uri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        //Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print(finalurl);
                        finalImages.add(finalurl);
                        isServiceRunning = false;
                    } else if (TransferState.FAILED == state) {
                        isServiceRunning = false;
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
                    isServiceRunning = false;
                }

            });

        } catch (Exception je) {

            je.printStackTrace();
            isServiceRunning = false;
        }
    }


    public void add_car()
    {
        JSONObject params = new JSONObject();
        try {

            JSONArray imagesArr;
            params.put("dealerId", SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("modelId",SPHelper.carmodelid);
            params.put("fuelType",fuel_type);
            params.put("transmissionType",trans_type);
            params.put("year", choose_year.getText().toString());
            params.put("vehicleNo",selected_vehno.getText().toString().trim());
            params.put("sellingPrice",selected_sp.getText().toString());
            params.put("km",selected_kms.getText().toString());
            params.put("noOfOwner",selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString());
            params.put("isPublic","y");
            params.put("status",insurance_status);
            params.put("bankName", selectedbankname);
            params.put("insuranceType",selectedinsurancetype);
            params.put("expDate",server_exp_date);
            params.put("policyNo",entered_pol_no.getText().toString());
            JSONArray imageraa = new JSONArray();
            params.put("imagesArr",imageraa);
            System.out.println("post_data"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.post,
                null, WebService.RequestType.shared.REST(ServiceURL.addcarurl, WebService.RESTType.JSONBody, params),
                false, 0, new ResponseListener()
                {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200")) {
                            System.out.println(response);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                idPBLoading.setVisibility(View.GONE);
                                upload ="";
                                SPHelper.carmodelid="";
                                SPHelper.carbrandid="";
                                Toast.makeText(activity, "Car added successfully", Toast.LENGTH_SHORT).show();
                                SPHelper.isSuccess="add";
                                SPHelper.comingfrom="added";
                                SPHelper.cf_msg="You have successfully added\n"+SPHelper.brand_name+"\t"+SPHelper.model_name+"\t"+fuel_type+"\t"+selected_vehno.getText().toString();
                                Intent intent=new Intent(AddNewCar.this,MainActivity.class);
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else if(response.getResponseType().equalsIgnoreCase("300")){
                            Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void servicecallwithimage()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("modelId",SPHelper.carmodelid);
            params.put("fuelType",fuel_type);
            params.put("transmissionType",trans_type);
            params.put("year", choose_year.getText().toString());
            params.put("vehicleNo",selected_vehno.getText().toString().trim());
            params.put("sellingPrice",selected_sp.getText().toString());
            params.put("km",selected_kms.getText().toString());
            params.put("noOfOwner",selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString());
            params.put("isPublic","y");
            params.put("status",insurance_status);
            params.put("bankName", selectedbankname);
            params.put("insuranceType",selectedinsurancetype);
            params.put("expDate",server_exp_date);
            params.put("policyNo",entered_pol_no.getText().toString());
            JSONArray paramsarr = new JSONArray();

            //   Toast.makeText(AddCarPage.this,"size of finl image is " + String.valueOf(finalImages.size()) ,Toast.LENGTH_SHORT).show();
            for (int i = 0; i <finalids.size(); i++)
            {
                JSONObject obbb = new JSONObject();

                obbb.put("dealerId",SPHelper.getSPData(activity,SPHelper.dealerid,""));
                obbb.put("id", finalids.get(i));
                obbb.put("imageurl", finalImages.get(i));
                obbb.put("is_profile_image","N");
                paramsarr.put(obbb);
            }
            params.put("imagesArr", paramsarr);
            System.out.println("post_data_with images"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.post, null, WebService.RequestType.shared.REST(ServiceURL.addcarurl, WebService.RESTType.JSONBody, params), false, 0, new ResponseListener() {
            @Override
            public void ResponseSuccess(ResponseExtension response) {
                if (response.getResponseType().equalsIgnoreCase("200")) {
                    System.out.println("response"+response);
                    JSONObject tktobj = response.getResponseObject();
                    try {
                        idPBLoading.setVisibility(View.GONE);
                        isServiceRunning=true;
                        SPHelper.carmodelid="";
                        SPHelper.carbrandid="";
                        SPHelper.cf_msg="You have successfully added\n"+SPHelper.brand_name+"\t"+SPHelper.model_name+"\t"+fuel_type+"\t"+selected_vehno.getText().toString();
                       // Toast.makeText(activity, "Car added successfully", Toast.LENGTH_SHORT).show();
                        SPHelper.isSuccess="add";
                        SPHelper.comingfrom="added";
                        Intent intent=new Intent(AddNewCar.this,MainActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    isServiceRunning=true;
                    idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void ResponseFailure(int responseCode, String errorMsg) {
                isServiceRunning=false;
                idPBLoading.setVisibility(View.GONE);
            }
        });
    }

    public void edit_car()
    {
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("vehicleId",SPHelper.vehid);
            params.put("sellingPrice",selected_sp.getText().toString());
            params.put("kms",selected_kms.getText().toString());
            params.put("noOfOwner", selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString());
            JSONArray imageraa = new JSONArray();
            params.put("imagesArr",imageraa);
            params.put("bankName", selectedbankname);
            params.put("insuranceType",selectedinsurancetype);
            params.put("expDate",SPHelper.ins_exp_date);
            params.put("status",insurance_status);
            params.put("policyNo",entered_pol_no.getText().toString());
            System.out.println("edit"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.post,
                null, WebService.RequestType.shared.REST(ServiceURL.editcarurl, WebService.RESTType.JSONBody, params), false, 0, new ResponseListener()
                {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                            System.out.println(response);
                            isServiceRunning=false;
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                idPBLoading.setVisibility(View.GONE);
                                upload ="";
                                SPHelper.vehno="";
                                SPHelper.selling_price="";
                                SPHelper.kmsdriven="";
                                SPHelper.no_owners="";
                                SPHelper.veh_color="";
                                SPHelper.carmodelid="";
                                SPHelper.fueltype="";
                                SPHelper.transmissiontype="";
                                SPHelper.manufacture_year="";
                                SPHelper.cf_msg="You have successfully edited\t -\t"+selected_vehno.getText().toString();
                               // SPHelper.comingfrom="added";
                                SPHelper.isSuccess="add";
                                SPHelper.saveSPdata(activity, SPHelper.imagestaken, "");
                                Toast.makeText(activity, "Car edited successfully", Toast.LENGTH_SHORT).show();
                                if(SPHelper.comingfrom.equals("customer"))
                                {
                                    SPHelper.goneto="edited";
                                    Intent intent=new Intent(AddNewCar.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    SPHelper.goneto="edited";
                                    Intent intent=new Intent(AddNewCar.this, AllCarsPage.class);
                                    startActivity(intent);
                                    finish();
                                }

                                //CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
                                //bottomSheetDialogFragment.show(AddNewCar.this.getSupportFragmentManager(), "CongratsPage");

                        } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
    }
    public void edit_car_withimage()
    {
        String f_kms=selected_kms.getText().toString().replace(",","");
        JSONObject params = new JSONObject();
        try {

            params.put("dealerId", SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("vehicleId",SPHelper.vehid);
            params.put("sellingPrice",selected_sp.getText().toString());
            params.put("kms",selected_kms.getText().toString());
            params.put("noOfOwner", selected_owners.getText().toString());
            params.put("color",selected_clr.getText().toString());
            JSONArray paramsarr = new JSONArray();

            for (int i = 0; i <finalids.size(); i++)
            {
                JSONObject obbb = new JSONObject();

                obbb.put("dealerId",SPHelper.getSPData(activity,SPHelper.dealerid,""));
                obbb.put("id", finalids.get(i));
                obbb.put("imageurl", finalImages.get(i));
                obbb.put("is_profile_image","N");
                paramsarr.put(obbb);
            }
            params.put("imagesArr", paramsarr);
            params.put("bankName", selectedbankname);
            params.put("insuranceType",selectedinsurancetype);
            params.put("expDate",SPHelper.ins_exp_date);
            params.put("status",insurance_status);
            params.put("policyNo",entered_pol_no.getText().toString());

            System.out.println("edit_with_images"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.post,
                null, WebService.RequestType.shared.REST(ServiceURL.editcarurl, WebService.RESTType.JSONBody, params), false, 0, new ResponseListener() {
            @Override
            public void ResponseSuccess(ResponseExtension response) {
                if (response.getResponseType().equalsIgnoreCase("200"))
                {
                    System.out.println(response);
                    JSONObject tktobj = response.getResponseObject();
                    //Toast.makeText(AddCarPage.this,"editcar with image called",Toast.LENGTH_SHORT).show();
                    try {
                        idPBLoading.setVisibility(View.GONE);
                        upload ="";
                        isServiceRunning=true;
                        SPHelper.vehno="";
                        SPHelper.selling_price="";
                        SPHelper.kmsdriven="";
                        SPHelper.no_owners="";
                        SPHelper.veh_color="";
                        SPHelper.carmodelid="";
                        SPHelper.fueltype="";
                        SPHelper.transmissiontype="";
                        SPHelper.manufacture_year="";
                        SPHelper.cf_msg="You have successfully edited\t -\t"+selected_vehno.getText().toString();
                        //SPHelper.comingfrom="added";
                        SPHelper.isSuccess="add";
                        SPHelper.saveSPdata(activity, SPHelper.imagestaken, "");
                        if(SPHelper.comingfrom.equals("customer")){
                            SPHelper.goneto="edited";
                            Intent intent=new Intent(AddNewCar.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            SPHelper.goneto="edited";
                            Intent intent=new Intent(AddNewCar.this, AllCarsPage.class);
                            startActivity(intent);
                            finish();
                        }
                        //CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
                        //bottomSheetDialogFragment.show(AddNewCar.this.getSupportFragmentManager(), "CongratsPage");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    isServiceRunning=true;
                    idPBLoading.setVisibility(View.GONE);
                    Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void ResponseFailure(int responseCode, String errorMsg) {
                isServiceRunning=false;
                idPBLoading.setVisibility(View.GONE);
            }
        });
    }
    public static AddNewCar getInstance() {
        return instance;
    }
}