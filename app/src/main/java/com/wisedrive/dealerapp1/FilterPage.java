package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterBrandLogos;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterInspStatus;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarBrands;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterPage extends AppCompatActivity {

    RecyclerView rv_brand_logos,rv_insp_status;
    //ArrayList<PojoSample> brandlogos;
    ArrayList<PojoAllCarBrands> brandlogos;
    ArrayList<PojoSample> insp_status;
    AdapterBrandLogos adapterBrandLogos;
    AdapterInspStatus adapterInspStatus;
    RangeSlider price_range,kms_range;
    ImageView iv_diesel,iv_petrol,iv_manual,iv_auto,iv_w_pack,iv_w_o_pack;
    DealerApis apiInterface;
    TextView max_price,max_kms,reset,min_price,min_kms,label_insp_status;
    RelativeLayout rl_apply,rl_back,rl_sold;
    private DecimalFormat IndianCurrencyFormat;
    String fuel_id="",trans_id="",kms_from="",kms_to="",price_from="",price_to="",is_with_pack="n",is_w_o_pack="n";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);
        SPHelper.camefrom="filter";
        IndianCurrencyFormat = new DecimalFormat("##,##,###");

        apiInterface = ApiClient.getClient().create(DealerApis.class);
        iv_w_pack=findViewById(R.id.iv_w_pack);
        iv_w_o_pack=findViewById(R.id.iv_w_o_pack);
        rl_sold=findViewById(R.id.rl_sold);
        label_insp_status=findViewById(R.id.label_insp_status);
        reset=findViewById(R.id.reset);
        rl_apply=findViewById(R.id.rl_apply);
        rl_back=findViewById(R.id.rl_back);
        rv_brand_logos=findViewById(R.id.rv_brand_logos);
        rv_insp_status=findViewById(R.id.rv_insp_status);
        price_range = findViewById(R.id.price_range);
        kms_range=findViewById(R.id.kms_range);
        iv_manual=findViewById(R.id.iv_manual);
        iv_diesel=findViewById(R.id.iv_diesel);
        iv_petrol=findViewById(R.id.iv_petrol);
        iv_auto=findViewById(R.id.iv_auto);
        max_price=findViewById(R.id.max_price);
        max_kms=findViewById(R.id.max_kms);
        min_price=findViewById(R.id.min_price);
        min_kms=findViewById(R.id.min_kms);

//        String selected_branndid=SPHelper.selected_brandid;
//        selected_branndid=selected_branndid.replace(",","");

        get_carbrands_list();
        if(SPHelper.comingfrom.equals("all")){
            label_insp_status.setVisibility(View.VISIBLE);
            rv_insp_status.setVisibility(View.VISIBLE);
            rl_sold.setVisibility(View.VISIBLE);
            get_warrantyList();

        }else{
            label_insp_status.setVisibility(View.GONE);
            rv_insp_status.setVisibility(View.GONE);
            rl_sold.setVisibility(View.GONE);
        }

        price_range.setCustomThumbDrawable(R.drawable.circle_blue);
        kms_range.setCustomThumbDrawable(R.drawable.circle_blue);
        price_range.setTrackActiveTintList(ContextCompat.getColorStateList(FilterPage.this, R.color.black));
        kms_range.setTrackActiveTintList(ContextCompat.getColorStateList(FilterPage.this, R.color.black));
        kms_range.setTrackInactiveTintList(ContextCompat.getColorStateList(FilterPage.this, R.color.lightgrey));
        price_range.setTrackInactiveTintList(ContextCompat.getColorStateList(FilterPage.this, R.color.lightgrey));
       // kms_range.setTrackHeight(10);
       // price_range.setTrackHeight(10);

        kms_range.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {

                return value + "kms";
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                if(SPHelper.comingfrom.equals("customer")){
//
//                    Intent intent=new Intent(FilterPage.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    Intent intent=new Intent(FilterPage.this, AllCarsPage.class);
//                    startActivity(intent);
//                    finish();
//                }
                finish();
            }
        });
        price_range.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return value + "INR";
            }
        });
        if(SPHelper.fuel_id.equals("1")){
            iv_petrol.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_petrol.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_diesel.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_diesel.setImageDrawable(null);
        }else if(SPHelper.fuel_id.equals("2")){
            iv_diesel.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_diesel.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_petrol.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_petrol.setImageDrawable(null);
        }

        if(SPHelper.trans_id.equals("1")){
            iv_manual.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_manual.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_auto.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_auto.setImageDrawable(null);
        }else if(SPHelper.trans_id.equals("2")){
            iv_auto.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_auto.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_manual.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_manual.setImageDrawable(null);
        }



        iv_diesel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                //map border
                fuel_id="2";
                iv_diesel.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                iv_diesel.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                iv_petrol.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                iv_petrol.setImageDrawable(null);
            }
        });
        iv_petrol.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                fuel_id="1";
                iv_petrol.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                iv_petrol.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                iv_diesel.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                iv_diesel.setImageDrawable(null);
            }
        });

        iv_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id=2
                trans_id="2";
                iv_auto.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                iv_auto.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                iv_manual.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                iv_manual.setImageDrawable(null);
            }
        });

        iv_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               trans_id="1";
                iv_manual.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                iv_manual.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                iv_auto.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                iv_auto.setImageDrawable(null);
            }
        });

        if(SPHelper.is_with_pack.equalsIgnoreCase("y")){
            iv_w_pack.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_w_o_pack.setImageDrawable(null);
            is_with_pack="y";
        }
        else if(SPHelper.is_with_pack.equalsIgnoreCase("n")){
            iv_w_o_pack.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
            iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
            iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_w_pack.setImageDrawable(null);
            is_w_o_pack="y";
        }
        else {
            iv_w_o_pack.setImageDrawable(null);
            iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
            iv_w_pack.setImageDrawable(null);
            is_w_o_pack="";
            is_with_pack="";

        }
        iv_w_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(is_with_pack.equals("n")){
                    iv_w_pack.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                    iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                    iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                    iv_w_o_pack.setImageDrawable(null);
                    is_with_pack="y";
                    SPHelper.is_with_pack="y";
                }else {
                    is_with_pack="n";
                    SPHelper.is_with_pack="";
                    iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                    iv_w_pack.setImageDrawable(null);
                }

            }
        });
        iv_w_o_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_w_o_pack.equals("n"))
                {
                    iv_w_o_pack.setImageDrawable(FilterPage.this.getDrawable(R.drawable.black_tickmark));
                    iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.blue_border));
                    iv_w_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                    iv_w_pack.setImageDrawable(null);
                    is_w_o_pack="y";
                    SPHelper.is_with_pack="n";
                }else {
                    is_w_o_pack="n";
                    SPHelper.is_with_pack="";
                    iv_w_o_pack.setBackground(FilterPage.this.getDrawable(R.drawable.map_border));
                    iv_w_o_pack.setImageDrawable(null);
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";
                SPHelper.is_with_pack="";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.selected_insp_statuses=new ArrayList<>();
                if(SPHelper.comingfrom.equals("customer")){
                    Intent intent=new Intent(FilterPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(FilterPage.this, AllCarsPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        price_range.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                double x1=slider.getValues().get(0);
                int y1=(int)x1;
                price_from= String.valueOf(y1);
                double x2=slider.getValues().get(1);
                int y2=(int)x2;
                price_to= String.valueOf(y2);
            }
        });

        kms_range.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                double x1=slider.getValues().get(0);
                int y1=(int)x1;
                kms_from= String.valueOf(y1);
                double x2=slider.getValues().get(1);
                int y2=(int)x2;
                kms_to= String.valueOf(y2);
            }
        });
        rl_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.kms_from= kms_from;
                SPHelper.kms_to= kms_to;
                SPHelper.price_from= price_from;
                SPHelper.price_to= price_to;
                SPHelper.fuel_id=fuel_id;
                SPHelper.trans_id=trans_id;
                //select brannd ids
                HashMap<String, Object> hm = new HashMap();
                SPHelper.selected_brandid="";
                for (int i = 0; i < SPHelper.pojoAllCarBrands.size(); i++)
                {
                    if(SPHelper.pojoAllCarBrands.get(i).getIs_Selected().equalsIgnoreCase("y"))
                    {
                        SPHelper.selected_brandid = SPHelper.selected_brandid + "," + SPHelper.pojoAllCarBrands.get(i).getBrand_id();
                        SPHelper.pojoAllCarBrands.get(i).setIs_Selected("y");
                        SPHelper.pojoAllCarBrands.get(i).setBrand_id(SPHelper.pojoAllCarBrands.get(i).getBrand_id());
                     }
                    else{
                       // SPHelper.selected_brandid=SPHelper.pojoAllCarBrands.remove()
                        SPHelper.pojoAllCarBrands.get(i).setIs_Selected("n");
                        SPHelper.pojoAllCarBrands.get(i).setBrand_id(SPHelper.pojoAllCarBrands.get(i).getBrand_id());
                    }
                }
                SPHelper.selected_brandid = (SPHelper.selected_brandid).substring(0);
                hm.put("&mobile=", SPHelper.selected_brandid);
                int count=0;
                SPHelper.selected_insp_status="";
                HashMap<String, Object> hm1 = new HashMap();
                for (int i = 0; i < SPHelper.selected_insp_statuses.size(); i++)
                {

                    if(insp_status.get(i).getIsSelected().equalsIgnoreCase("y"))
                    {
                        count=count+1;
                        if (count == 1) {

                            SPHelper.selected_insp_status = SPHelper.selected_insp_statuses.get(i).getWarranty_status_id();

                        }else{
                            SPHelper.selected_insp_status = SPHelper.selected_insp_status + "," + SPHelper.selected_insp_statuses.get(i).getWarranty_status_id();
                        }
                        SPHelper.selected_insp_statuses.get(i).setIsSelected("y");
                    }else{
                        SPHelper.selected_insp_statuses.get(i).setIsSelected("n");
                    }
                }
                SPHelper.selected_insp_status = (SPHelper.selected_insp_status).substring(0);
                hm1.put("&mobile=", SPHelper.selected_insp_status);

                System.out.println("SPHelper.comingfrom"+SPHelper.comingfrom);
                System.out.println("SPHelper.selected_brandid"+SPHelper.selected_brandid);
                System.out.println("SPHelper.price_from"+SPHelper.price_from);
                System.out.println("SPHelper.price_to"+SPHelper.price_to+"kmsfrom"+SPHelper.kms_from+"\n"+
                        SPHelper.kms_to+"\n"+SPHelper.selected_insp_status);
                if(SPHelper.comingfrom.equals("customer")){

                    Intent intent=new Intent(FilterPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(FilterPage.this, AllCarsPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public  void get_carbrands_list() {
        {
            if (!Connectivity.isNetworkConnected(FilterPage.this)) {
                Toast.makeText(FilterPage.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getPriceKmsDetails(SPHelper.getSPData(FilterPage.this, SPHelper.dealerid, ""));
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
                                brandlogos=new ArrayList<>();
                                String min_odo=appResponse.getResponse().getPriceKmDetails().getMin_odometer();
                                String max_odo=appResponse.getResponse().getPriceKmDetails().getMax_odometer();
                                String min_sold=appResponse.getResponse().getPriceKmDetails().getMin_sold_price();
                                double max_sold=appResponse.getResponse().getPriceKmDetails().getMax_sold_price();
                                if(min_odo==null){

                                }else {
                                    double d2= Double.parseDouble(min_odo);
                                    int y1=(int)d2;
                                    String s3 = IndianCurrencyFormat.format(y1);
                                    min_kms.setText(s3);
                                }

                                if(max_odo==null){

                                }else {
                                    double d1= Double.parseDouble(max_odo);
                                    int y=(int)d1;
                                    String s1 = IndianCurrencyFormat.format(y);
                                    max_kms.setText(s1);
                                }

                                if(min_sold==null){

                                }else {
                                    double d3= Double.parseDouble(min_sold);
                                    int y3=(int)d3;
                                    String s4 = IndianCurrencyFormat.format(y3);
                                    min_price.setText(s4);
                                }

                                if(String.valueOf(max_sold)==null){

                                }else {
                                    BigDecimal bigDecimal = new BigDecimal(max_sold);// form to BigDecimal
                                    String str=bigDecimal.toString();
                                    int str1=Integer.parseInt(str);
                                    //String final_price=String.format("%.0f",max_sold);
                                    max_price.setText(IndianCurrencyFormat.format(str1));

                                    System.out.println("max_price"+max_price);
                                }

                                brandlogos=appResponse.getResponse().getDealerBrandList();
                                if(SPHelper.pojoAllCarBrands.isEmpty()){
                                    SPHelper.pojoAllCarBrands=brandlogos;
                                }

                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FilterPage.this,LinearLayoutManager.HORIZONTAL,false);
                                rv_brand_logos.setLayoutManager(linearLayoutManager);
                                adapterBrandLogos=new AdapterBrandLogos(SPHelper.pojoAllCarBrands,FilterPage.this);
                                rv_brand_logos.setAdapter(adapterBrandLogos);
                                FilterPage.this.runOnUiThread(new Runnable() {
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
                            Toast.makeText(FilterPage.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(FilterPage.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public  void get_warrantyList() {
        {
            if (!Connectivity.isNetworkConnected(FilterPage.this)) {
                Toast.makeText(FilterPage.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                // progress_bar.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getWarrantyStatusList();
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
                                insp_status=new ArrayList<>();
                                insp_status=appResponse.getResponse().getWarrantyStatusList();
                                if(SPHelper.selected_insp_statuses.isEmpty()){
                                    SPHelper.selected_insp_statuses=insp_status;
                                }


                                GridLayoutManager gridLayoutManager=new GridLayoutManager(FilterPage.this,2);
                                rv_insp_status.setLayoutManager(gridLayoutManager);
                                adapterInspStatus=new AdapterInspStatus(SPHelper.selected_insp_statuses,FilterPage.this);
                                rv_insp_status.setAdapter(adapterInspStatus);

                                FilterPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterInspStatus.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                // progress_bar.setVisibility(View.GONE);
                            }
                        } else {
                            // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(FilterPage.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(FilterPage.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if(SPHelper.comingfrom.equals("customer")){
//
//            Intent intent=new Intent(FilterPage.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else {
//            Intent intent=new Intent(FilterPage.this, AllCarsPage.class);
//            startActivity(intent);
//            finish();
//        }
        finish();
    }
}