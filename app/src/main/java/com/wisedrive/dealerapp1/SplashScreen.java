package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Dealer_status;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {
    private DealerApis apiInterface;
    String app_version;
    private static final int SPLASH_DISPLAY_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        SPHelper.sharedPreferenceInitialization(SplashScreen.this);
        update_app();
        //        new Handler().postDelayed(new Runnable() {
//            public void run()
//            {
//                    if(SPHelper.getSPData(SplashScreen.this, SPHelper.dealerid, "").equals(""))
//                    {
//                        Intent intent=new Intent(SplashScreen.this,LoginPage.class);
//                        startActivity(intent);
//                   /* intent.setClass(SplashScreen.this,
//                            LoginPage.class);
//                    SplashScreen.this.startActivity(intent);*/
//                        SplashScreen.this.finish();
//                    }else {
//                        Intent intent1=new Intent(SplashScreen.this,
//                                HomePage.class);
//                        startActivity(intent1);
//                        SplashScreen.this.finish();
//                    }
//            }
//        }, SPLASH_DISPLAY_TIME);
    }

    public void update_app(){
        if(!Connectivity.isNetworkConnected(SplashScreen.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Please Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.getapp_update_deatails("3","1");
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
                            SPHelper.app_url=appResponse.getResponse().getAppUpdateDetails().getApp_url();
                            SPHelper.can_skip=appResponse.getResponse().getAppUpdateDetails().getCan_skip();
                            app_version=appResponse.getResponse().getAppUpdateDetails().getAppversion();
                            appResponse.getResponse().getAppUpdateDetails().getIs_current();
                            System.out.println("appversion"+getString(R.string.app_version_name));
                            System.out.println("app_version"+app_version);

                            show_update();
                        }
                        else if (response_code.equals("300")) {
                            Toast.makeText(SplashScreen.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else{

                        Toast.makeText(SplashScreen.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void show_update(){
        new Handler().postDelayed(new Runnable() {
            public void run()
            {
                SPHelper.camefrom="";
                if(!app_version.equals(getString(R.string.app_version_name))){
                    Intent intent=new Intent(SplashScreen.this, NotificationPage.class);
                    startActivity(intent);
                }else{
                    if(SPHelper.getSPData(SplashScreen.this, SPHelper.dealerid, "").equals(""))
                    {
                        Intent intent=new Intent(SplashScreen.this,LoginPage.class);
                        startActivity(intent);
                        SplashScreen.this.finish();

                    }else {
                        /*Intent intent1=new Intent(SplashScreen.this,
                                MainActivity.class);
                        startActivity(intent1);
                        SplashScreen.this.finish();*/
                        get_dealer_statust_update();


                    }
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }
    public void get_dealer_statust_update(){
        Call<AppResponse>call=apiInterface.dealer_status(SPHelper.getSPData(SplashScreen.this,SPHelper.dealerid,""));
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Pojo_Dealer_status dealerStatus = appResponse.getResponse().getDealerStatus();
                        if (dealerStatus != null) {
                            if (dealerStatus.getIs_active().equals("Y")) {


                                Intent intent1=new Intent(SplashScreen.this,
                                        MainActivity.class);
                                startActivity(intent1);
                                SplashScreen.this.finish();

                            } else if (dealerStatus.getIs_active().equals("N")){
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.dealership_name, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.d_email, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.d_adress, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.d_location, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.d_city, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.d_state, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.city_id,"");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.state_id, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.pincode, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.dealerid, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.dealerno, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.dealername, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.dealerlogo, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.imagestaken, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.helplineno, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.awssecret, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.awskey, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.comet_authkey, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.comet_region, "");
                                SPHelper.saveSPdata(SplashScreen.this, SPHelper.comet_appid, "");
                                Intent i = new Intent(SplashScreen.this, LoginPage.class);
                                startActivity(i);
                                finish();

                            }


                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });

    }


}