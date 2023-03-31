package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterNewVehImgs;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterCustomerCarsPage;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.HomeFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllCarsPage extends AppCompatActivity {
    ProgressBar progress_bar;
    public  int pageno=0;
    ArrayList<PojoAllCarsList> customer_cars_list;
    AdapterAllCarPage adapterAllCarPage;
    RecyclerView rv_all_cars;
    ImageView iv_filter,go_back;
    RelativeLayout rl_parent,rl_header;
    TextView heading;
    public RelativeLayout rl_cust_details,rl_cust,rl_purchase,rl_offers;
    private static AllCarsPage instance;
    RecyclerView rv_veh_imgs;
    public RelativeLayout rl_transparent1,rl_show_veh_images;
    public Dialog dialog;
   public TextView comments,tv_no_cars;
    int count1=0,count2=0,count3=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_all_cars_page);
        progress_bar=findViewById(R.id.progress_bar);
        go_back=findViewById(R.id.go_back);
        rl_header=findViewById(R.id.rl_header);
        iv_filter=findViewById(R.id.iv_filter);
        rv_all_cars=findViewById(R.id.rv_all_cars);
        rl_parent=findViewById(R.id.rl_parent);
        tv_no_cars=findViewById(R.id.tv_no_cars);
        heading=findViewById(R.id.heading);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_show_veh_images=findViewById(R.id.rl_show_veh_images);
        rv_veh_imgs=findViewById(R.id.rv_veh_imgs);

        dialog = new Dialog(AllCarsPage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.pop_up_comments);
        dialog.setCancelable(true);
        comments=dialog.findViewById(R.id.comments) ;
        if(SPHelper.goneto.equals("edited")){

            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(AllCarsPage.this.getSupportFragmentManager(), "CongratsPage");
        }
        rl_transparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_veh_images.setVisibility(View.GONE);
            }
        });
        customer_cars_list=new ArrayList<>();

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllCarsPage.this,FilterPage.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup );
               // finish();
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="";
                if(SPHelper.fragment_is.equals("profile")){
                    Intent intent=new Intent(AllCarsPage.this,ProfileFragment.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(AllCarsPage.this,MainActivity.class);
                    startActivity(intent);
                }
               // finish();
            }
        });
        //if coming from sold
        heading.setText(SPHelper.title);
    }

    public static AllCarsPage getInstance() {
        return instance;
    }

    //insp_req_vehicle,approved veh,re-insp,repair-req,sold cars with warranty

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

            params.put("dealerId",SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
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
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get,
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
                                    tv_no_cars.setVisibility(View.GONE);
                                    for (int i = 0; i < tktobj.getJSONArray("AllInspectedVehWithStatus").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllInspectedVehWithStatus").getJSONObject(i);
                                        PojoAllCarsList leadobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(leadobj);
                                    }
                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    AllCarsPage.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                        }
                                    });
                                }
                                else {
                                    tv_no_cars.setVisibility(View.VISIBLE);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            progress_bar.setVisibility(View.GONE);
                           // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(AllCarsPage.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
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
            params.put("dealerId",SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
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
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get, null,
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
                        Toast.makeText(AllCarsPage.this, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }

    public void removeloader()
    {
        customer_cars_list.remove(customer_cars_list.size() - 1);
        adapterAllCarPage.notifyItemRemoved(customer_cars_list.size());
    }

    //all cars list,sold cars,public,private
    public void get_all_vehiclelist()
    {
       /* if(search_foryourcars.getText().toString().equals("")){
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
            params.put("dealerId", SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold","n");
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            System.out.print("params"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get, null,
                WebService.RequestType.shared.REST(ServiceURL.allcarurl, WebService.RESTType.UrlEncode, params), false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                           // idPBLoading.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                customer_cars_list.clear();
                                if (tktobj.getJSONArray("AllcarList").length() > 0)
                                {
                                    tv_no_cars.setVisibility(View.GONE);
                                    for (int i = 0; i < tktobj.getJSONArray("AllcarList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllcarList").getJSONObject(i);
                                        PojoAllCarsList leadobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(leadobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }

                                    AllCarsPage.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                        }
                                    });
                                }else{
                                    tv_no_cars.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            //idPBLoading.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(AllCarsPage.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        progress_bar.setVisibility(View.GONE);
                        //idPBLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void getpagination()
    {
        progress_bar.setVisibility(View.VISIBLE);
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold","n");
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            System.out.print("params"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get,
                null, WebService.RequestType.shared.REST(ServiceURL.allcarurl, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response)
                    {
                        removeloader();
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                            progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {

                                if (tktobj.getJSONArray("AllcarList").length() > 0)
                                {

                                    for (int i = 0; i < tktobj.getJSONArray("AllcarList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllcarList").getJSONObject(i);
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
                        }

                        else{
                            progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                       progress_bar.setVisibility(View.GONE);
                        Toast.makeText(AllCarsPage.this, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }
    @Override
    public void onResume()
    {
        super.onResume();
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(getApplicationContext(),1);
        rv_all_cars.setLayoutManager(linearLayoutManager2);
        adapterAllCarPage=new AdapterAllCarPage(AllCarsPage.this,customer_cars_list,rv_all_cars);
        rv_all_cars.setAdapter(adapterAllCarPage);
        adapterAllCarPage.setOnLoadMoreListener(() -> {
            if (pageno > 1)
            {
                customer_cars_list.add(null);
                rv_all_cars.post(new Runnable()
                {
                    public void run() {
                        adapterAllCarPage.notifyItemInserted(customer_cars_list.size() - 1);
                        if(SPHelper.comingfrom.equals("all"))
                        {
                            if(customer_cars_list.size()>0){
                                getpagination();
                            }
                        }else{
                            if(customer_cars_list.size()>0){
                                get_insp_veh_list_pagination();
                            }
                        }
                    }
                });
            }
        });

        if(SPHelper.comingfrom.equals("all"))
        {
            get_all_vehiclelist();
        }else{
            get_insp_veh_list();
        }
    }

    @Override
    public void onBackPressed()
    {

        SPHelper.camefrom="";

        if(SPHelper.fragment_is.equals("profile")){
            Intent intent=new Intent(AllCarsPage.this,ProfileFragment.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(AllCarsPage.this,MainActivity.class);
            startActivity(intent);
        }
        finish();

    }
}