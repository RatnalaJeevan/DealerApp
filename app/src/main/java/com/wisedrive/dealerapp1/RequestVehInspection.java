package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoRequestInspectionDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehInspectEligible;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestVehInspection extends AppCompatActivity {

    String mobile_no_pattern="^[6-9][0-9]{9}$";
    DatePickerDialog date_picker;
    public RelativeLayout rl_req_insp,rl_presale,rl_postsale,rl_presale_details,rl_show_popup,rl_transparent,rl_check_status,
            rl_postsale_details,rl_location_details,rl_insp_cust_details,rl_submit_insp,rl_add_car,rl_label,rl_go_back;
    TextView tv_presale,tv_postsale,selected_veh_no,postsale_insp_date;
    View v_postsale,v_presale;
    ImageView iv_cust_seleted,iv_dealer_selected,back,cancel_veh_info;
    int req_page=1;
    EditText et_no_cars,selected_vehno,cust_adress,cust_name,cust_no,cust_location;
    TextView selected_insp_date;
    Activity activity;
    private DealerApis apiInterface;
    String serverdate="",inspection_type="Presale",locationType="";
    @SuppressLint("MissingInflatedId")
    @Nullable


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modal_bottom_sheet);

        //req insp
        // rl_show_req_insp_page=v.findViewById(R.id.rl_show_req_insp_page);
        activity=RequestVehInspection.this;
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        back=findViewById(R.id.back);
        rl_go_back=findViewById(R.id.rl_go_back);
        cancel_veh_info=findViewById(R.id.cancel_veh_info);
        rl_check_status=findViewById(R.id.rl_check_status);
        rl_label=findViewById(R.id.rl_label);
        rl_req_insp=findViewById(R.id.rl_req_insp);
        cust_adress=findViewById(R.id.cust_adress);
        cust_name=findViewById(R.id.cust_name);
        cust_no=findViewById(R.id.cust_no);
        cust_location=findViewById(R.id.cust_location);
        et_no_cars=findViewById(R.id.et_no_cars);
        postsale_insp_date=findViewById(R.id.postsale_insp_date);
        selected_insp_date=findViewById(R.id.selected_insp_date);
        selected_vehno=findViewById(R.id.selected_vehno);
        rl_postsale=findViewById(R.id.rl_postsale);
        rl_presale=findViewById(R.id.rl_presale);
        tv_presale=findViewById(R.id.tv_presale);
        tv_postsale=findViewById(R.id.tv_postsale);
        v_postsale=findViewById(R.id.v_postsale);
        v_presale=findViewById(R.id.v_presale);
        rl_presale_details=findViewById(R.id.rl_presale_details);
        rl_postsale_details=findViewById(R.id.rl_postsale_details);
        rl_location_details=findViewById(R.id.rl_location_details);
        rl_insp_cust_details=findViewById(R.id.rl_insp_cust_details);
        rl_submit_insp=findViewById(R.id.rl_submit_insp);
        selected_veh_no=findViewById(R.id.selected_veh_no);
        iv_cust_seleted=findViewById(R.id.iv_cust_seleted);
        iv_dealer_selected=findViewById(R.id.iv_dealer_selected);
        rl_add_car=findViewById(R.id.rl_add_car);
        rl_show_popup=findViewById(R.id.rl_show_popup);
        rl_transparent=findViewById(R.id.rl_transparent);


        if(SPHelper.goneto.equals("repair")||SPHelper.goneto.equals("expired"))
        {
            selected_vehno.setText(SPHelper.vehno);
            selected_veh_no.setText(SPHelper.vehno);
            req_page=3;
            inspection_type=SPHelper.goneto;
            show_req_insp();
            rl_presale.setEnabled(false);
            rl_postsale.setEnabled(false);

        }else if(SPHelper.goneto.equals("act")||SPHelper.goneto.equals("bbg")){
            selected_vehno.setText(SPHelper.vehno);
            selected_veh_no.setText(SPHelper.vehno);
            req_page=3;
            inspection_type="Postsale";
            show_req_insp();
            rl_presale.setEnabled(false);
            rl_postsale.setEnabled(false);
        }else{
            req_page=1;
            selected_veh_no.setText("");
            show_req_insp();
            rl_presale.setEnabled(true);
            rl_postsale.setEnabled(true);
        }
        iv_cust_seleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationType="Customer";
                rl_insp_cust_details.setVisibility(View.VISIBLE);
                iv_cust_seleted.setImageDrawable(activity.getDrawable(R.drawable.black_tickmark));
                iv_dealer_selected.setImageDrawable(null);
            }
        });
        iv_dealer_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationType="Dealership";
                rl_insp_cust_details.setVisibility(View.GONE);
                iv_dealer_selected.setImageDrawable(activity.getDrawable(R.drawable.black_tickmark));
                iv_cust_seleted.setImageDrawable(null);
            }
        });
        rl_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="add";
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                SPHelper.vehno=selected_vehno.getText().toString();
                Intent intent=new Intent(RequestVehInspection.this,AddNewCar.class);
                startActivity(intent);
            }
        });

        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
                Intent intent=new Intent(RequestVehInspection.this,MainActivity.class);
                startActivity(intent);
            }
        });
        cancel_veh_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
                Intent intent=new Intent(RequestVehInspection.this,MainActivity.class);
                startActivity(intent);
            }
        });
        rl_presale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req_page=1;
                selected_veh_no.setText("");
                inspection_type="Presale";
                locationType="";
                et_no_cars.setText("");
                selected_insp_date.setText("");
                serverdate="";
                postsale_insp_date.setText("");
                show_req_insp();
            }
        });
        rl_postsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req_page=2;
                et_no_cars.setText("");
                selected_insp_date.setText("");
                serverdate="";
                postsale_insp_date.setText("");
                inspection_type="Postsale";
                rl_insp_cust_details.setVisibility(View.GONE);
                iv_dealer_selected.setImageDrawable(null);
                iv_cust_seleted.setImageDrawable(null);
                show_req_insp();
            }
        });

        selected_insp_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                select_date();
            }
        });
        postsale_insp_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_date();
            }
        });

        select_vehno();

        rl_req_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationType="Dealership";
                inspection_type="Presale";
                if(et_no_cars.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter no.of cars",
                            Toast.LENGTH_SHORT).show();
                }else if(et_no_cars.getText().toString().equals("0")){
                    Toast.makeText(activity,
                            "Enter a number other than 0",
                            Toast.LENGTH_SHORT).show();
                }
                else if(et_no_cars.getText().toString().startsWith("0")){
                    Toast.makeText(activity,
                            "Number should not start with 0",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_insp_date.getText().toString().equals("")){
                    Toast.makeText(activity,
                            "Enter Inspection date",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    post_req_inspection();
                }
            }
        });

        rl_submit_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationType.equals("")){
                    Toast.makeText(activity,
                            "Please Select Inspection Location ",
                            Toast.LENGTH_SHORT).show();
                }
                else if(locationType.equals("Dealership"))
                {
                    if(selected_vehno.getText().toString().equals("")){
                        Toast.makeText(activity,
                                "Please Enter Vehicle Number",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(postsale_insp_date.getText().toString().equals("")){
                        Toast.makeText(activity,
                                "Please Enter Inspection date",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        post_req_inspection();
                    }
                }
                else
                {
                    if(postsale_insp_date.getText().toString().equals("")){
                        Toast.makeText(activity,
                                "Please Enter Inspection date",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (cust_name.getText().toString().equals(""))
                    {
                        Toast.makeText(activity,
                                "Please Enter Customer name",
                                Toast.LENGTH_SHORT).show();
                    } else if (cust_no.getText().toString().equals("")) {
                        Toast.makeText(activity,
                                "Please Enter Customer PhoneNo",
                                Toast.LENGTH_SHORT).show();
                    } else if (cust_no.getText().toString().length() < 10) {
                        Toast.makeText(activity,
                                "Please Enter Valid Phone Number",
                                Toast.LENGTH_SHORT).show();
                    }else if(!cust_no.getText().toString().matches(mobile_no_pattern)){
                        Toast.makeText(activity,
                                " Enter Valid Phone Number",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(cust_location.getText().toString().equals("")){
                        Toast.makeText(activity,
                                " Enter Customer Location",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if (cust_adress.getText().toString().equals("")) {
                        Toast.makeText(activity,
                                "Please Enter Customer Address",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        post_req_inspection();
                    }
                }
            }
        });

        rl_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rl_check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord();
                if(selected_vehno.getText().toString().equals("")){
                    Common.CallToast(RequestVehInspection.this,"Enter a Vehno.",1);
                }
                else if(selected_vehno.getText().toString().trim().length()>=6)
                {
                    check_veh_inspect_eligibility();
                }else{
                    rl_label.setVisibility(View.GONE);
                    rl_add_car.setVisibility(View.GONE);
                    Common.CallToast(RequestVehInspection.this,"VehNo. should be greater than 5",1);
                }
            }
        });
    }

    private void select_vehno() {
        selected_vehno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(selected_vehno.getText().toString().trim().length()>=6)
                {

                }else{
                    rl_label.setVisibility(View.GONE);
                    rl_add_car.setVisibility(View.GONE);
                }
            }
        });

    }

    public  void select_date(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        //Theme_Material3_Dark_Dialog_Alert
        //Theme_Material3_DayNight_DialogWhenLarge
        //R.style.AppCompatDialogStyle
        date_picker = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener()
                {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        serverdate=year+"-"+(monthOfYear + 1) +"-"+dayOfMonth;
                        selected_insp_date.setText(Common.getDateFromString(serverdate));
                        postsale_insp_date.setText(Common.getDateFromString(serverdate));

                    }
                }, year, month, day);
        date_picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        date_picker.show();
    }
    public void show_req_insp(){
        if(req_page==1){
            rl_presale_details.setVisibility(View.VISIBLE);
            rl_postsale_details.setVisibility(View.GONE);
            rl_location_details.setVisibility(View.GONE);
            tv_presale.setTextColor(Color.parseColor("#FF000000"));
            tv_postsale.setTextColor(Color.parseColor("#D3D3D3"));
            v_presale.setVisibility(View.VISIBLE);
            v_postsale.setVisibility(View.GONE);
            rl_submit_insp.setVisibility(View.GONE);
            selected_veh_no.setVisibility(View.GONE);

        }else if(req_page==2){

            rl_presale_details.setVisibility(View.GONE);
            rl_postsale_details.setVisibility(View.VISIBLE);
            rl_location_details.setVisibility(View.GONE);
            tv_presale.setTextColor(Color.parseColor("#D3D3D3"));
            tv_postsale.setTextColor(Color.parseColor("#FF000000"));
            v_presale.setVisibility(View.GONE);
            v_postsale.setVisibility(View.VISIBLE);
            rl_submit_insp.setVisibility(View.GONE);
            selected_veh_no.setVisibility(View.GONE);

        }else if(req_page==3){
            rl_presale_details.setVisibility(View.GONE);
            rl_postsale_details.setVisibility(View.GONE);
            rl_location_details.setVisibility(View.VISIBLE);
            tv_presale.setTextColor(Color.parseColor("#D3D3D3"));
            tv_postsale.setTextColor(Color.parseColor("#FF000000"));
            v_presale.setVisibility(View.GONE);
            v_postsale.setVisibility(View.VISIBLE);
            rl_submit_insp.setVisibility(View.VISIBLE);
            selected_veh_no.setVisibility(View.VISIBLE);

        }
    }

    public  void check_veh_inspect_eligibility()
    {
            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
               // progress_bar.setVisibility(View.VISIBLE);
                PojoVehInspectEligible post1=
                        new PojoVehInspectEligible(SPHelper.getSPData(activity, SPHelper.dealerid, ""),selected_vehno.getText().toString());
                Call<AppResponse> call =  apiInterface.check_veh_inspect_eligible(post1);
                call.enqueue(new Callback<AppResponse>() {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                //progress_bar.setVisibility(View.GONE);
                                String errormsg=appResponse.getResponse().getInspectionEligibility().getError_msg();
                                String veh_exists=appResponse.getResponse().getInspectionEligibility().getIs_vehicle_exists();
                                String can_request_for_inspection=appResponse.getResponse().getInspectionEligibility().getCan_request_for_inspection();

                                //if veh exsits is n
                                //allow them to add  car
                                //
                                if(errormsg.equals(""))
                                {
                                    rl_label.setVisibility(View.GONE);
                                    rl_add_car.setVisibility(View.GONE);
                                    req_page=3;
                                    selected_veh_no.setText(selected_vehno.getText().toString());
                                    show_req_insp();
                                }else{
                                    if(veh_exists.equalsIgnoreCase("y")
                                            && can_request_for_inspection.equalsIgnoreCase("n")){
                                        Common.CallToast(RequestVehInspection.this,errormsg,1);
                                        rl_label.setVisibility(View.GONE);
                                        rl_add_car.setVisibility(View.GONE);
                                    }
                                    else if(appResponse.getResponse().getInspectionEligibility().getIs_vehicle_exists().equalsIgnoreCase("y")
                                    && can_request_for_inspection.equalsIgnoreCase("y")){
                                        rl_label.setVisibility(View.GONE);
                                        rl_add_car.setVisibility(View.GONE);
                                        req_page=3;
                                        selected_veh_no.setText(selected_vehno.getText().toString());
                                        show_req_insp();
                                    }else{
                                        rl_label.setVisibility(View.VISIBLE);
                                        rl_add_car.setVisibility(View.VISIBLE);
                                       // Toast.makeText(activity, appResponse.getResponse().getInspectionEligibility().getError_msg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else if (response_code.equals("300")) {
                                //progress_bar.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                           // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public  void post_req_inspection(){
        {
            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
               // idPBLoading.setVisibility(View.VISIBLE);
                PojoRequestInspectionDetails post1=
                        new PojoRequestInspectionDetails(SPHelper.getSPData(activity, SPHelper.dealerid, ""),
                                "",selected_vehno.getText().toString(),inspection_type,locationType,et_no_cars.getText().toString(),serverdate
                                ,cust_name.getText().toString(),cust_no.getText().toString(),cust_adress.getText().toString(),cust_location.getText().toString());
                Call<AppResponse> call =  apiInterface.req_inspection(post1);
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
                               // idPBLoading.setVisibility(View.GONE);
                               selected_vehno.setText("");
                               et_no_cars.setText("");
                               rl_show_popup.setVisibility(View.VISIBLE);
                            } else if (response_code.equals("300")) {
                               // idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
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
                                Toast.LENGTH_SHORT).show();
                        //idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    private void hideKeybaord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}