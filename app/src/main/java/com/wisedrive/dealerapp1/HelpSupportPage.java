package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpSupportPage extends AppCompatActivity {
    private DealerApis apiInterface;
    private ProgressDialog progressDialog;
    private String email_id,phone_no;
    TextView label_call;
    RelativeLayout rl_call_care,rl_email_support;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support_page);
        back=findViewById(R.id.back);
        rl_call_care=findViewById(R.id.rl_call_care);
        rl_email_support=findViewById(R.id.rl_email_support);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progressDialog = new ProgressDialog(HelpSupportPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        label_call=findViewById(R.id.label_call);
        get_help_support();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rl_call_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });
        rl_email_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + email_id));
                startActivity(intent);
            }
        });
    }

    public  void get_help_support() {
        {
            if (!Connectivity.isNetworkConnected(HelpSupportPage.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_help_support();
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
                                progressDialog.dismiss();
                               phone_no=appResponse.getResponse().getGetsupportdata().getPhone_no();
                                email_id=appResponse.getResponse().getGetsupportdata().getEmail_id();
                                label_call.setText("Call us at"+" "+phone_no);
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(HelpSupportPage.this, "internal server error", Toast.LENGTH_SHORT).show();
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
}