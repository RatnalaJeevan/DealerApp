package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.FilterPage;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterNewVehImgs;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerFragment extends Fragment {
    public  int pageno=0;
    ProgressBar progress_bar;
    Activity activity;
    ArrayList<PojoAllCarsList> customer_cars_list=new ArrayList<>();
    AdapterAllCarPage adapterAllCarPage;
    RecyclerView rv_all_cars;
    private static CustomerFragment instance;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        activity=getActivity();
        instance=this;
        SPHelper.is_sold="Y";
        SPHelper.with_cool="";
        SPHelper.with_pack="Y";
        SPHelper.status_id="99";
        SPHelper.comingfrom="customer";
        //SPHelper.selected_brandid="";
        rv_all_cars=rootView.findViewById(R.id.rv_all_cars);
        progress_bar=rootView.findViewById(R.id.progress_bar);
        return rootView;
    }

    public static CustomerFragment getInstance() {
        return instance;
    }

    public void get_insp_veh_list() {

        /*if(search_foryourcars.getText().toString().equals("")){
            progress_bar.setVisibility(View.GONE);
            idPBLoading.setVisibility(View.VISIBLE);
        }else{
            progress_bar.setVisibility(View.VISIBLE);
            idPBLoading.setVisibility(View.GONE);
        }*/
        progress_bar.setVisibility(View.VISIBLE);
        pageno = 1;
        JSONObject params = new JSONObject();
        try {

            params.put("dealerId",SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("withCooling",SPHelper.with_cool);
            params.put("withPack",SPHelper.with_pack);
            params.put("statusId",SPHelper.status_id);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.get,
                null, WebService.RequestType.shared.REST(ServiceURL.insp_veh_list_url, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200")) {
                             progress_bar.setVisibility(View.GONE);
                            // idPBLoading.setVisibility(View.GONE);
                            System.out.println(response);
                            JSONObject tktobj = response.getResponseObject();
                            try {

                                customer_cars_list.clear();
                                if (tktobj.getJSONArray("AllInspectedVehWithStatus").length() > 0)
                                {
                                    for (int i = 0; i < tktobj.getJSONArray("AllInspectedVehWithStatus").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllInspectedVehWithStatus").getJSONObject(i);
                                        PojoAllCarsList leadobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(leadobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                             progress_bar.setVisibility(View.GONE);
                            // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                         progress_bar.setVisibility(View.GONE);
                        // idPBLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void get_insp_veh_list_pagination()
    {
        // progress_bar.setVisibility(View.VISIBLE);
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId",SPHelper.getSPData(activity,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("withCooling",SPHelper.with_cool);
            params.put("withPack",SPHelper.with_pack);
            params.put("statusId",SPHelper.status_id);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(activity, ServiceURL.BASEURL, WebService.HttpMethod.get, null,
                WebService.RequestType.shared.REST(ServiceURL.insp_veh_list_url, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        System.out.println(response);
                        removeloader();
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                            // progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                if (tktobj.getJSONArray("AllInspectedVehWithStatus").length() > 0)
                                {
                                    for (int i = 0; i < tktobj.getJSONArray("AllInspectedVehWithStatus").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllInspectedVehWithStatus").getJSONObject(i);
                                        PojoAllCarsList carobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(carobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    rv_all_cars.post(new Runnable() {
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                            adapterAllCarPage.setLoaded();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            // progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        // progress_bar.setVisibility(View.GONE);
                        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }

    public void removeloader()
    {
        customer_cars_list.remove(customer_cars_list.size() - 1);
        adapterAllCarPage.notifyItemRemoved(customer_cars_list.size());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(activity,1);
        rv_all_cars.setLayoutManager(linearLayoutManager2);
        adapterAllCarPage=new AdapterAllCarPage(activity,customer_cars_list,rv_all_cars);
        rv_all_cars.setAdapter(adapterAllCarPage);
        adapterAllCarPage.setOnLoadMoreListener(() -> {
            if (pageno > 1)
            {
                customer_cars_list.add(null);
                rv_all_cars.post(new Runnable()
                {
                    public void run() {
                        adapterAllCarPage.notifyItemInserted(customer_cars_list.size() - 1);

                        if(customer_cars_list.size()>0){
                            get_insp_veh_list_pagination();
                        }
                    }
                });
            }
        });

        get_insp_veh_list();

    }
}