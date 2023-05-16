package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.core.HIChartView;
import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.CheckEligibility;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
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
    private String insp_veh_count,app_w_c,repair_count,app_w_o_c,re_insp_count,
            sold_cars_with_warr_count,expired_insp_count,s_w_o_warra_c,all_car_counnt;
    public TextView tv_allcar_count,tv_count_approved_count,tv_count_exp_insp,tv_count_rep_req;
    RelativeLayout activate_button;
    Activity activity;
    TextView text_pending_count;



    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_2, container, false);
        activity=getActivity();
        SPHelper.pojoAllCarBrands=new ArrayList<>();
        SPHelper.fuel_id="";
        SPHelper.trans_id="";
        SPHelper.price_from= "";
        SPHelper.price_to= "";
        SPHelper.kms_from="";
        SPHelper.kms_to="";
        SPHelper.selected_insp_status="";
        SPHelper.selected_insp_statuses=new ArrayList<>();
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

     /*   String backendData = "30/40";
        text_pending_count.setText(backendData);
        String[] numbers = backendData.split("/");
        String firstNumber = numbers[0];
        text_pending_count.setText(firstNumber + "/" + numbers[1]);*/



        rl_approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="N";
                SPHelper.with_cool="N";
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

        BarChart barChart = rootView.findViewById(R.id.bar_chart);

// Set chart padding to 0dp
        barChart.setPadding(0, 0, 0, 0);

// Customize chart settings
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        barChart.getLegend().setEnabled(false);

// Hide axis lines, grid lines, and labels
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);

// Create data set and add data
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 5));
        entries.add(new BarEntry(1, 7));
        entries.add(new BarEntry(2, 6));
        entries.add(new BarEntry(3, 6));
        entries.add(new BarEntry(4, 6));
        entries.add(new BarEntry(5, 6));
        entries.add(new BarEntry(6, 7));
        entries.add(new BarEntry(7, 6));
        entries.add(new BarEntry(8, 7));
        entries.add(new BarEntry(9, 8));

// Customize chart settings
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBorders(false);
        barChart.getLegend().setEnabled(false);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPadding(0, 0, 0, 0);

// Create data set and add data
        BarDataSet dataSet = new BarDataSet(entries, "Data Set 1");
        dataSet.setFormLineWidth(0.01f);
        int barColor = ContextCompat.getColor(getContext(), R.color.blue);
        dataSet.setColor(barColor);
        dataSet.setDrawValues(true); // Set drawValues property to true
        dataSet.setValueTextColor(Color.BLACK); // Set the text color of the values
        dataSet.setBarBorderColor(Color.WHITE);
        dataSet.setBarBorderWidth(1f);

// Set data and fit bars
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barData.setBarWidth(0.1f);
        barChart.setData(barData);

// Hide axis lines, grid lines, and labels
        barChart.getXAxis().setDrawAxisLine(true);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setEnabled(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setLabelCount(entries.size());
        barChart.getXAxis().setAxisMinimum(-0.5f);
        barChart.getXAxis().setAxisMaximum(entries.size() - 0.5f);
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.getXAxis().setAvoidFirstLastClipping(true);
        barChart.getXAxis().setTextColor(Color.BLACK);

        barChart.getAxisLeft().setEnabled(true);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMaximum(10f);

        barChart.getAxisRight().setEnabled(false);

// Disable double-tap to zoom
        barChart.setDoubleTapToZoomEnabled(false);

// Set chart orientation and animate
        barChart.setRotation(0f);
        barChart.animateY(1000);
        barChart.invalidate();

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
                        if (response_code.equals("200")) {
                            String no_of_warranties_left = appResponse.getResponse().getDealerWarrantyInfo().getNo_of_warranties_left();
                            // Assuming you have the following variables:
                            int totalWarranties = 30; // Total number of warranties

                            //set text of text_pending_count
                            int warrantiesLeft = Integer.parseInt(no_of_warranties_left != null ? no_of_warranties_left : "0");
                            String text = warrantiesLeft + "/" + totalWarranties;
                            text_pending_count.setText(text);
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
                                insp_veh_count=appResponse.getResponse().getReqInspection().getCount();
                                app_w_c = appResponse.getResponse().getApprovedwithCooling().getCount();
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
                        progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }



}





