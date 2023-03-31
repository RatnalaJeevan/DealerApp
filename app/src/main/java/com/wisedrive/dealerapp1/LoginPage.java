package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDeviceDetails;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity
{
    Boolean mBackSpace;
    Integer mPreviousLength;
    TextView tv_login,tv_sendotp,tv_signup,tv_otp,tv_resend,timer;
    EditText selected_mobileno,otp1,otp2,otp3,otp4;
    private DealerApis apiInterface;
    private ProgressDialog progressDialog;
     String otps;
     Integer textlength1,textlength2,textlength3;
     LinearLayout otp_ll;
     RelativeLayout rl_footer;
    private boolean isBackspaceClicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        SPHelper.sharedPreferenceInitialization(LoginPage.this);
        timer=findViewById(R.id.timer);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_footer=findViewById(R.id.rl_footer);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        otp_ll=findViewById(R.id.otp_ll);
        selected_mobileno=findViewById(R.id.selected_mobileno);
        tv_otp=findViewById(R.id.tv_otp);
        tv_signup=findViewById(R.id.tv_signup) ;
        tv_login=findViewById(R.id.tv_login);
        tv_sendotp=findViewById(R.id.tv_sendotp);
        tv_resend=findViewById(R.id.tv_resend);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Your PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    verify_otp();
                }
            }
        });
        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    settimer();
                    send_otp();
                }

            }
        });
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_ll.setVisibility(View.VISIBLE);
                tv_otp.setVisibility(View.VISIBLE);
                tv_resend.setVisibility(View.VISIBLE);
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    otp1.setText("");
                    otp2.setText("");
                    otp3.setText("");
                    otp4.setText("");
                    otp1.requestFocus();
                    settimer();
                    send_otp();
                }

            }
        });
        rl_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="login";
                Intent intent=new Intent(LoginPage.this,SignUpPage.class);
                startActivity(intent);
            }
        });
        selected_mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }else{
                    hideKeybaord();
                }
            }
        });

        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
                if (after < count) {
                    isBackspaceClicked = true;
                } else {
                    isBackspaceClicked = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.setEnabled(true);
                    otp2.requestFocus();
                }
                if (!isBackspaceClicked) {
                    // Your current code
                } else {
                    otp1.setText("");
                    otp1.requestFocus();
                    // Your "backspace" handling
                }
                mBackSpace = mPreviousLength > s.length();
/*
                if (mBackSpace) {
                    otp1.setText("");
                    otp1.requestFocus();
                }*/

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
                if (after < count) {
                    isBackspaceClicked = true;
                } else {
                    isBackspaceClicked = false;
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (after < count) {
                    isBackspaceClicked = true;
                } else {
                    isBackspaceClicked = false;
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                textlength2 = otp2.getText().length();
                /*if (textlength2 >= 1) {
                    otp3.requestFocus();
                }else{
                    otp2.setText("");
                    otp1.requestFocus();
                }*/
                mBackSpace = mPreviousLength > s.length();

                if (!isBackspaceClicked) {
                    // Your current code
                    otp3.requestFocus();
                } else {
                    otp2.setText("");
                    otp1.requestFocus();
                    // Your "backspace" handling
                }
                /*if (mBackSpace) {
                    otp2.setText("");
                    otp1.requestFocus();
                    // do your stuff ...

                }*/
            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
                if (after < count) {
                    isBackspaceClicked = true;
                } else {
                    isBackspaceClicked = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }
                mBackSpace = mPreviousLength > s.length();
                if (!isBackspaceClicked) {
                    // Your current code
                } else {
                    otp3.setText("");
                    otp2.requestFocus();
                    // Your "backspace" handling
                }
                /*if (mBackSpace) {
                    otp3.setText("");
                    otp2.requestFocus();
                    // do your stuff ...

                }*/
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousLength = s.length();
                if (after < count) {
                    isBackspaceClicked = true;
                } else {
                    isBackspaceClicked = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBackSpace = mPreviousLength > s.length();

                /*if (mBackSpace) {
                    otp4.setText("");
                    otp3.requestFocus();
                    // do your stuff ...

                }*/
                if (!isBackspaceClicked) {
                    // Your current code
                } else {
                    otp4.setText("");
                    otp3.requestFocus();
                    // Your "backspace" handling
                }
                Integer textlength4 = otp4.getText().length();

                if (textlength4 >= 1) {
                    hideKeybaord();
                    tv_login.setVisibility(View.VISIBLE);
                    tv_login.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.black));
                    tv_login.setTextColor(Color.parseColor("#FFFFFFFF"));
                }else{
                    tv_login.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_login.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.greywhite));
                    tv_login.setTextColor(Color.parseColor("#0619c3"));
                }
            }
        });
        get_credentials();
    }

    public void send_otp()
    {
        if(!Connectivity.isNetworkConnected(LoginPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            tv_sendotp.setVisibility(View.INVISIBLE);
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.sendotp(selected_mobileno.getText().toString().trim(),"Y");
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
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        otp_ll.setVisibility(View.VISIBLE);
                        tv_otp.setVisibility(View.VISIBLE);
                        tv_resend.setVisibility(View.VISIBLE);
                        otp1.requestFocus();
                    } else if (response_code.equals("300")) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        selected_mobileno.setText("");
                    }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    public  void verify_otp(){
    {
            otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
            if(!Connectivity.isNetworkConnected(LoginPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                progressDialog.show();
                Call<AppResponse> call =  apiInterface.verifyotp(selected_mobileno.getText().toString().trim(),otps);
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
                                progressDialog.dismiss();
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.dealerid, appResponse.getResponse().getDealerInfo().getDealer_id());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.dealership_name, appResponse.getResponse().getDealerInfo().getDealership_name());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.dealername, appResponse.getResponse().getDealerInfo().getDealer_name());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.dealerno, appResponse.getResponse().getDealerInfo().getPhone_no());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.dealerlogo, appResponse.getResponse().getDealerInfo().getDealer_logo());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.d_email, appResponse.getResponse().getDealerInfo().getDealer_email());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.d_adress, appResponse.getResponse().getDealerInfo().getFull_address());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.d_location, appResponse.getResponse().getDealerInfo().getLocation());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.d_city, appResponse.getResponse().getDealerInfo().getCity());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.d_state, appResponse.getResponse().getDealerInfo().getState());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.city_id, appResponse.getResponse().getDealerInfo().getCity_id());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.state_id, appResponse.getResponse().getDealerInfo().getState_id());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.pincode, appResponse.getResponse().getDealerInfo().getPincode());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.helplineno, appResponse.getResponse().getDealerInfo().getCustomer_support_phone_no());
                                //System.out.println("awssecret"+appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awssecret, appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awskey, appResponse.getResponse().getCredentials().getAws_key());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_authkey, appResponse.getResponse().getCredentials().getComet_auth_key());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_region, appResponse.getResponse().getCredentials().getComet_region());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_appid, appResponse.getResponse().getCredentials().getComet_app_id());
                                updateDevice_Details();

                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                otp1.setText("");
                                otp2.setText("");
                                otp3.setText("");
                                otp4.setText("");
                                otp1.requestFocus();
                                tv_login.setVisibility(View.INVISIBLE);
                                tv_sendotp.setVisibility(View.VISIBLE);
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    }
    public  void get_credentials(){
        {

            if(!Connectivity.isNetworkConnected(LoginPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                progressDialog.show();
                Call<AppResponse> call =  apiInterface.get_credentials();
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
                                progressDialog.dismiss();

                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awssecret, appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.awskey, appResponse.getResponse().getCredentials().getAws_key());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_authkey, appResponse.getResponse().getCredentials().getComet_auth_key());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_region, appResponse.getResponse().getCredentials().getComet_region());
                                SPHelper.saveSPdata(LoginPage.this, SPHelper.comet_appid, appResponse.getResponse().getCredentials().getComet_app_id());
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    }
    public  void updateDevice_Details(){
        {
            if(!Connectivity.isNetworkConnected(LoginPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                progressDialog.show();
                PojoDeviceDetails deviceDetails=new PojoDeviceDetails(SPHelper.getSPData(LoginPage.this, SPHelper.dealerid,"")
                         ,selected_mobileno.getText().toString().trim(), "dealerapp", Common.getDeviceIMEI(LoginPage.this),
                       Build.BRAND+" "+Build.MODEL,Build.VERSION.RELEASE,"", "android",getString(R.string.app_version_name));
                Call<AppResponse> call =  apiInterface.updateDeviceDetails(deviceDetails);
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
                                progressDialog.dismiss();
                                Intent intent=new Intent(LoginPage.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    }
    private void hideKeybaord(){
        InputMethodManager inputManager = (InputMethodManager) LoginPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(LoginPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    public  void settimer()
    {
        new CountDownTimer(30000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timer.setVisibility(View.VISIBLE);
                timer.setText(""+ millisUntilFinished / 1000);
            }
            public void onFinish() {
                tv_resend.setVisibility(View.VISIBLE);
                timer.setText("");
            }
        }.start();
    }

}