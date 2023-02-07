package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
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
                   /* intent.setClass(SplashScreen.this,
                            LoginPage.class);
                    SplashScreen.this.startActivity(intent);*/
                        SplashScreen.this.finish();
                    }else {
                        Intent intent1=new Intent(SplashScreen.this,
                                MainActivity.class);
                        startActivity(intent1);
                        SplashScreen.this.finish();
                    }
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }

}