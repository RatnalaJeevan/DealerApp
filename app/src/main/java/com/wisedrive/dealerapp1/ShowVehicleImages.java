package com.wisedrive.dealerapp1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wisedrive.dealerapp1.adapters.adapters.SlideAdapter;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowVehicleImages extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ImageView go_back;
    ViewPager view_pager_2;
     TabLayout indicator1;
    private DealerApis apiInterface;
    private ProgressDialog progressDialog;
    public SlideAdapter adapter1;
    ArrayList<PojoVehicleImageList> vehicleImageLists;
    RelativeLayout rl_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vehicle_images);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progressDialog = new ProgressDialog(ShowVehicleImages.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_back=findViewById(R.id.rl_back);
        go_back=findViewById(R.id.go_back);
        indicator1=findViewById(R.id.indicator1);
        view_pager_2=findViewById(R.id.view_pager_2);
        get_veh_images_list();
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void get_veh_images_list()
    {
        if(!Connectivity.isNetworkConnected(ShowVehicleImages.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.get_vehicleImagelist(SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
//                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        progressDialog.dismiss();
                        if (response_code.equals("200")) {
                            progressDialog.dismiss();
                            //appResponse.getResponse().getVehicleImageList().
                            vehicleImageLists = new ArrayList<>();
                            vehicleImageLists = appResponse.getResponse().getVehicleImageList();
                            adapter1 = new SlideAdapter(vehicleImageLists, ShowVehicleImages.this);
                            for(int i=0;i<vehicleImageLists.size();i++){
                                if(vehicleImageLists.get(i).getVehicle_images().equals("")){

                                }else{
                                    //view_pager_2.setAdapter(adapter1);
                                    view_pager_2.setCurrentItem(0);
                                    view_pager_2.setAdapter(adapter1);
                                    indicator1.setupWithViewPager(view_pager_2, true);
                                }
                            }
                            ShowVehicleImages.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });

                        }

                    } else if (response_code.equals("300")) {
                        progressDialog.dismiss();
                        Toast.makeText(ShowVehicleImages.this, "internal server error", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ShowVehicleImages.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    public void onBackPressed()
    {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}