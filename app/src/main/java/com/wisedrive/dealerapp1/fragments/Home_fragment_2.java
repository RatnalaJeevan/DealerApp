package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarDataSet;



import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.CheckEligibility;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.CustomTypefaceSpan;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home_fragment_2 extends Fragment {
    private DealerApis apiInterface;
    ProgressBar progress_bar;
    RelativeLayout rl_all_cars,rl_approved,rl_exp_veh,rl_repair_req;
    String total_cars,sold_carscount,not_soldcars_count;
    private String live_cars="0",from_car_c="0",repair_count,app_w_o_c,re_insp_count,
            sold_cars_with_warr_count,expired_insp_count,s_w_o_warra_c,all_car_counnt;
    public TextView tv_allcar_count,tv_count_approved_count,tv_count_exp_insp,tv_count_rep_req,label_for_cars;
    RelativeLayout activate_button;
    Activity activity;
    TextView text_pending_count,tv_live_cars,text_new_leads,tv_new_leads;
    RelativeLayout rl_live_cars;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_2, container, false);
        activity=getActivity();
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
        SPHelper.pojoAllCarBrands=new ArrayList<>();
        SPHelper.fuel_id="";
        SPHelper.trans_id="";
        SPHelper.price_from= "";
        SPHelper.price_to= "";
        SPHelper.kms_from="";
        SPHelper.kms_to="";
        SPHelper.selected_insp_status="";
        SPHelper.selected_insp_statuses=new ArrayList<>();
        tv_new_leads=rootView.findViewById(R.id.tv_new_leads);
        label_for_cars=rootView.findViewById(R.id.label_for_cars);
        text_new_leads=rootView.findViewById(R.id.text_new_leads);
        tv_live_cars=rootView.findViewById(R.id.tv_live_cars);
        progress_bar=rootView.findViewById(R.id.progress_bar);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progress_bar=rootView.findViewById(R.id.progress_bar);
        rl_all_cars=rootView.findViewById(R.id.rl_all_cars);
        rl_approved=rootView.findViewById(R.id.rl_approved);
        rl_exp_veh=rootView.findViewById(R.id.rl_exp_veh);
        rl_repair_req=rootView.findViewById(R.id.rl_repair_req);
        tv_allcar_count=rootView.findViewById(R.id.tv_allcar_count);
        tv_count_approved_count=rootView.findViewById(R.id.tv_count_approved_count);
        tv_count_exp_insp=rootView.findViewById(R.id.tv_count_exp_insp);
        tv_count_rep_req=rootView.findViewById(R.id.tv_count_rep_req);
        activate_button=rootView.findViewById(R.id.activate_button);
        text_pending_count=rootView.findViewById(R.id.text_pending_count);
        rl_live_cars=rootView.findViewById(R.id.rl_live_cars);



        String text = getResources().getString(R.string.text_to_style);
        Typeface boldTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bold.ttf");
        Typeface mediumTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/medium.ttf");

        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new CustomTypefaceSpan( boldTypeface), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new CustomTypefaceSpan( mediumTypeface), 7, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text_new_leads.setText(spannableString);

        rl_live_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.is_sold="N";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="1";
                SPHelper.title="Listed Vehicle List";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";

                if(from_car_c.equals("0")&&live_cars.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="app";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }


            }
        });

        rl_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="N";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="1";
                SPHelper.title="Approved Vehicle List";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";

                if(app_w_o_c==null||app_w_o_c.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="app";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }



            }
        });
        rl_all_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPHelper.title="All Cars";
                SPHelper.comingfrom="all";
                SPHelper.is_sold="";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";
                SPHelper.is_with_pack="";
                if(all_car_counnt==null||all_car_counnt.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), AllCarsPage.class);
                    startActivity(intent);
                }

            }
        });
        rl_repair_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="4";
                SPHelper.title="Repair Request";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";
                if(repair_count==null||repair_count.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="rep_req";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }



            }
        });

        rl_exp_veh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="5";
                SPHelper.title="Expired Inspection List";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";
                if(expired_insp_count==null||expired_insp_count.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="exp";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }

            }
        });

        activate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, CheckEligibility.class);
                startActivity(intent);
                activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);

            }
        });

        get_dashboard_count();
        get_dealer_warranty_info();
        return rootView;


    }

    public void get_dealer_warranty_info() {
        if (!Connectivity.isNetworkConnected(getContext())) {
            Toast.makeText(getContext(), "Please Check Your Internet", Toast.LENGTH_SHORT).show();
        } else {
            Call<AppResponse> call = apiInterface.getwarrantyinfo(SPHelper.getSPData(getContext(), SPHelper.dealerid, ""), "");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200"))
                        {
                            String no_of_warranties_left = appResponse.getResponse().getDealerWarrantyInfo().getNo_of_warranties_left();
                             live_cars=appResponse.getResponse().getLiveCarCount().getTotal_live_cars();
                            String msg=appResponse.getResponse().getLiveCarCount().getMsg();
                            String total=appResponse.getResponse().getDealerWarrantyInfo().getNo_of_warranties_left_from();
                            String new_lead_c=appResponse.getResponse().getLiveCarCount().getNew_lead_count();
                             from_car_c=appResponse.getResponse().getLiveCarCount().getFrom_car_count();
                            if(new_lead_c==null){
                                tv_new_leads.setText("0 Total Leads");
                            }else {
                                tv_new_leads.setText(new_lead_c+" Total Leads");
                                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                anim.setDuration(200); //You can manage the blinking time with this parameter
                                anim.setStartOffset(20);
                                anim.setRepeatMode(Animation.REVERSE);
                                anim.setRepeatCount(Animation.INFINITE);
                                tv_new_leads.startAnimation(anim);
                            }
                            if(from_car_c==null){
                                label_for_cars.setText("0 cars");
                            }else {
                                label_for_cars.setText("for "+from_car_c+" cars");
                            }


                            int warrantiesLeft = Integer.parseInt(no_of_warranties_left != null ? no_of_warranties_left : "0");
                            if(total==null||total.equals("null")||total.equals("0")){
                                total="0";
                            }
                            String text = warrantiesLeft + "/" + total;
                            text_pending_count.setText(text);
                            tv_live_cars.setText(live_cars+" Cars");
                            text_new_leads.setText(msg);
                        } else if (response_code.equals("300")) {
                            // handle response code 300
                        }
                    } else {
                        Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public  void get_dashboard_count() {
        {
            if (!Connectivity.isNetworkConnected(getContext())) {
                Toast.makeText(getContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
              //  progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_dashboardcount(SPHelper.getSPData(getContext(), SPHelper.dealerid, ""),
                        "","","","","","","");
                call.enqueue(new Callback<AppResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {

                                total_cars=appResponse.getResponse().getDashboardCount().getAll_cars();
                                SPHelper.cfs_count=appResponse.getResponse().getDashboardCount().getCars_for_sale();
                                SPHelper.cfp_count=appResponse.getResponse().getDashboardCount().getTotal_requested_count();
                                SPHelper.req_veh_count=appResponse.getResponse().getDashboardCount().getRequested_vehicles();
                                sold_carscount=appResponse.getResponse().getDashboardCount().getSold_cars_count();
                                not_soldcars_count=appResponse.getResponse().getDashboardCount().getNot_sold_cars_count();
                                expired_insp_count=appResponse.getResponse().getExpired().getCount();
                                app_w_o_c=appResponse.getResponse().getApprovedwithoutCooling().getCount();
                                re_insp_count=appResponse.getResponse().getReinspect().getCount();
                                repair_count=appResponse.getResponse().getRepair().getCount();
                                sold_cars_with_warr_count=appResponse.getResponse().getActivatedvehcount().getCount();
                                s_w_o_warra_c=appResponse.getResponse().getWithoutwarranty().getCount();
                                all_car_counnt=appResponse.getResponse().getAllCarCount().getCount();
                                tv_count_approved_count.setText(app_w_o_c);
                                tv_count_rep_req.setText(repair_count);
                                tv_count_exp_insp.setText(expired_insp_count);
                                tv_allcar_count.setText(all_car_counnt);
                              //  progress_bar.setVisibility(View.GONE);
                            } else if (response_code.equals("300")) {
                              //  progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                           // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }



                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
//                        progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

}





