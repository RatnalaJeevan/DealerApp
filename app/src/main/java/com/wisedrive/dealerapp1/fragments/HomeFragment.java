package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterExpVehList;
import com.wisedrive.dealerapp1.AddNewCar;
import com.wisedrive.dealerapp1.CheckEligibility;
import com.wisedrive.dealerapp1.RequestVehInspection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.LinePagerIndicatorDecoration;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoExpVehList;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private DealerApis apiInterface;
    ProgressBar progress_bar;
    ViewPager view_pager_2;
    RecyclerView rv_expiring_list;
    AdapterExpVehList adapterExpVehList;
    ArrayList<PojoExpVehList> pojoExpVehListArrayList;
    TabLayout indicator1;
    RelativeLayout rl_packages,rl_approved,rl_addcar,rl_activatewarranty,rl_req_for_inspection,rl_sold_cars,
            rl_insp_req,rl_re_insp,rl_repair_req,rl_exp_veh,rl_app_cool_period,rl_sold_w_o;
    Activity activity;
    String total_cars,sold_carscount,not_soldcars_count;
    public TextView tv_count_sold,tv_count_insp_req,tv_count_app_w_c,tv_count_app_w_o_c,count_pending,
            tv_count_re_insp,tv_count_rep_req,tv_count_exp_insp,tv_count_sold_w_o;

    private String insp_veh_count,app_w_c,repair_count,app_w_o_c,re_insp_count,
            sold_cars_with_warr_count,expired_insp_count,s_w_o_warra_c,all_car_counnt;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        activity=getActivity();
        SPHelper.pojoAllCarBrands=new ArrayList<>();
        SPHelper.fuel_id="";
        SPHelper.trans_id="";
        SPHelper.price_from= "";
        SPHelper.price_to= "";
        SPHelper.kms_from="";
        SPHelper.kms_to="";
        SPHelper.selected_insp_status="";
        //SPHelper.selected_brandid="";
        SPHelper.selected_insp_statuses=new ArrayList<>();
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        tv_count_sold_w_o=rootView.findViewById(R.id.tv_count_sold_w_o);
        count_pending=rootView.findViewById(R.id.count_pending);
        progress_bar=rootView.findViewById(R.id.progress_bar);
        rv_expiring_list=rootView.findViewById(R.id.rv_expiring_list);
        view_pager_2=rootView.findViewById(R.id.view_pager_2);
        indicator1=rootView.findViewById(R.id.indicator1);
        rl_packages=rootView.findViewById(R.id.rl_packages);
        rl_approved=rootView.findViewById(R.id.rl_approved);
        rl_addcar=rootView.findViewById(R.id.rl_addcar);
        rl_sold_cars=rootView.findViewById(R.id.rl_sold_cars);
        rl_activatewarranty=rootView.findViewById(R.id.rl_activatewarranty);
        rl_req_for_inspection=rootView.findViewById(R.id.rl_req_for_inspection);
        tv_count_sold=rootView.findViewById(R.id.tv_count_sold);
        tv_count_insp_req=rootView.findViewById(R.id.tv_count_insp_req);
        tv_count_app_w_c=rootView.findViewById(R.id.tv_count_app_w_c);
        tv_count_app_w_o_c=rootView.findViewById(R.id.tv_count_app_w_o_c);
        tv_count_re_insp=rootView.findViewById(R.id.tv_count_re_insp);
        tv_count_rep_req=rootView.findViewById(R.id.tv_count_rep_req);
        tv_count_exp_insp=rootView.findViewById(R.id.tv_count_exp_insp);
        rl_insp_req=rootView.findViewById(R.id.rl_insp_req);
        rl_re_insp=rootView.findViewById(R.id.rl_re_insp);
        rl_repair_req=rootView.findViewById(R.id.rl_repair_req);
        rl_exp_veh=rootView.findViewById(R.id.rl_exp_veh);
        rl_app_cool_period=rootView.findViewById(R.id.rl_app_cool_period);
        rl_sold_w_o=rootView.findViewById(R.id.rl_sold_w_o);

        // final int radius = getResources().getDimensionPixelSize(R.dimen.radius);
        // final int dotsHeight = getResources().getDimensionPixelSize();

//        final int start_color = ContextCompat.getColor(getContext(), R.color.lightgrey);
//        final int end_clr = ContextCompat.getColor(getContext(), R.color.dark_grey);
//        rv_expiring_list.addItemDecoration(new LinePagerIndicatorDecoration( start_color, end_clr,getContext()));
//        new PagerSnapHelper().attachToRecyclerView(rv_expiring_list); //addContentView(rv_expiring_list);

        rl_addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                SPHelper.camefrom="add";
                SPHelper.vehno="";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                Intent intent=new Intent(activity, AddNewCar.class);
                startActivity(intent);
                activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
            }
        });
        rl_activatewarranty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, CheckEligibility.class);
                startActivity(intent);
                activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);

            }
        });
        rl_req_for_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.vehno="";
                SPHelper.goneto="";
                Intent intent=new Intent(activity, RequestVehInspection.class);
                startActivity(intent);
                activity.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
            }
        });


        rl_insp_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="7";
                SPHelper.title="Inspection Requested";
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
                if(insp_veh_count==null||insp_veh_count.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="insp_req";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);

                }
            }
        });
        rl_re_insp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="N";
                SPHelper.with_cool="";
                SPHelper.with_pack="";
                SPHelper.status_id="3";
                SPHelper.title="Reinspected Vehicle List";
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
                if(re_insp_count==null||re_insp_count.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="re_insp";
                    Intent intent=new Intent(activity, AllCarsPage.class);
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
        rl_app_cool_period.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="N";
                SPHelper.with_cool="Y";
                SPHelper.with_pack="";
                SPHelper.status_id="1";
                SPHelper.title="Approved Vehicles With Cooling Period";
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
                if(app_w_c==null||app_w_c.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    SPHelper.comingfrom="cool_per";
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }
            }
        });
        rl_sold_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.is_sold="Y";
                SPHelper.with_cool="";
                SPHelper.with_pack="Y";
                SPHelper.status_id="99";
                SPHelper.comingfrom="sold";
                SPHelper.title="Sold Cars List";
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
                if(sold_cars_with_warr_count==null||sold_cars_with_warr_count.equals("0"))
                {
                    Toast.makeText(activity,
                            "there are no cars to show",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(activity, AllCarsPage.class);
                    startActivity(intent);
                }
            }
        });
        rl_sold_w_o.setOnClickListener(new View.OnClickListener() {
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

        get_dashboard_count();
        get_dealer_warranty_info();
        getExpiringVehList();
        return  rootView;
    }

    public  void get_dealer_warranty_info() {
        {
            if (!Connectivity.isNetworkConnected(getContext())) {
                Toast.makeText(getContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getwarrantyinfo(SPHelper.getSPData(getContext(), SPHelper.dealerid, ""),"");
                call.enqueue(new Callback<AppResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                progress_bar.setVisibility(View.GONE);
                                String no_of_warranties_left=appResponse.getResponse().getDealerWarrantyInfo().getNo_of_warranties_left();
                                //set text of exp_count;
                                if(no_of_warranties_left==null){
                                    count_pending.setText("0");
                                }else{
                                    count_pending.setText(no_of_warranties_left);
                                }

                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            progress_bar.setVisibility(View.GONE);
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

    public  void get_dashboard_count() {
        {
            if (!Connectivity.isNetworkConnected(getContext())) {
                Toast.makeText(getContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progress_bar.setVisibility(View.VISIBLE);
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
                                tv_count_insp_req.setText(insp_veh_count);
                                tv_count_app_w_c.setText(app_w_c);
                                tv_count_app_w_o_c.setText(app_w_o_c);
                                tv_count_re_insp.setText(re_insp_count);
                                tv_count_rep_req.setText(repair_count);
                                tv_count_exp_insp.setText(expired_insp_count);
                                tv_count_sold.setText(sold_cars_with_warr_count);
                                tv_count_sold_w_o.setText(all_car_counnt);
                                progress_bar.setVisibility(View.GONE);
                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            progress_bar.setVisibility(View.GONE);
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

    public void getExpiringVehList(){

            if (!Connectivity.isNetworkConnected(getContext())) {
                Toast.makeText(getContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getExpVehCount(SPHelper.getSPData(getContext(), SPHelper.dealerid, ""));
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
                                pojoExpVehListArrayList=new ArrayList<>();
                                pojoExpVehListArrayList=appResponse.getResponse().getExpVehicleCount();
                                adapterExpVehList=new AdapterExpVehList(pojoExpVehListArrayList, getContext());
                               // rv_expiring_list.setNestedScrollingEnabled(false);
                                rv_expiring_list.setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
                               // rv_expiring_list.setHasFixedSize(true);
                                rv_expiring_list.setAdapter(adapterExpVehList);
                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            progress_bar.setVisibility(View.GONE);
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