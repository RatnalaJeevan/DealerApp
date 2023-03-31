package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.AllCarsPage;
import com.wisedrive.dealerapp1.AllPayments;
import com.wisedrive.dealerapp1.HelpSupportPage;
import com.wisedrive.dealerapp1.LoginPage;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.SignUpPage;
import com.wisedrive.dealerapp1.WebPages;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends AppCompatActivity {
    TextView yes,no;
    TextView dealer_name,dealer_phoneno;
    ImageView go_back_home,edit_dealer;

    RelativeLayout rl_all_payments,rl_log_out,rl_all_cars,rl_edit_dealer,rl_back;
    private  Dialog dialog;
    ProgressBar idPBLoading;
    private DealerApis apiInterface;
    RelativeLayout rl_tnc,rl_wpol,rl_pr_pol,rl_hns;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        SPHelper.sharedPreferenceInitialization(ProfileFragment.this);
        rl_back=findViewById(R.id.rl_back);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_tnc=findViewById(R.id.rl_tnc);
        rl_wpol=findViewById(R.id.rl_wpol);
        rl_pr_pol=findViewById(R.id.rl_pr_pol);
        rl_hns=findViewById(R.id.rl_hns);
        idPBLoading=findViewById(R.id.idPBLoading);
        rl_edit_dealer=findViewById(R.id.rl_edit_dealer);
        edit_dealer=findViewById(R.id.edit_dealer);
        rl_all_cars=findViewById(R.id.rl_all_cars);
        rl_log_out=findViewById(R.id.rl_log_out);
        dealer_name=findViewById(R.id.dealer_name);
        dealer_phoneno=findViewById(R.id.dealer_phoneno);
        go_back_home=findViewById(R.id.go_back_home);
        rl_all_payments=findViewById(R.id.rl_all_payments);

        dialog = new Dialog(ProfileFragment.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.dealership_name, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.d_email, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.d_adress, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.d_location, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.d_city, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.d_state, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.city_id,"");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.state_id, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.pincode, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.dealerid, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.dealerno, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.dealername, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.dealerlogo, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.imagestaken, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.helplineno, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.awssecret, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.awskey, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.comet_authkey, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.comet_region, "");
                SPHelper.saveSPdata(ProfileFragment.this, SPHelper.comet_appid, "");
                Intent i = new Intent(ProfileFragment.this, LoginPage.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        rl_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        rl_all_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileFragment.this, AllPayments.class);
                startActivity(intent);
            }
        });

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rl_all_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="all";
                SPHelper.title="All Cars";
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";

                Intent intent=new Intent(ProfileFragment.this, AllCarsPage.class);
                startActivity(intent);
            }
        });
        dealer_name.setText(SPHelper.getSPData(ProfileFragment.this, SPHelper.dealername, ""));
        dealer_phoneno.setText(SPHelper.getSPData(ProfileFragment.this, SPHelper.dealerno, ""));

        rl_edit_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="edit_d";
                Intent intent=new Intent(ProfileFragment.this, SignUpPage.class);
                startActivity(intent);
            }
        });
        rl_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="tnc";
                Intent intent=new Intent(ProfileFragment.this, WebPages.class);
                startActivity(intent);
            }
        });
        rl_wpol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="warranty";
                if(SPHelper.claim_warranty.equals("")||SPHelper.claim_warranty==null){
                    Toast.makeText(getApplicationContext(),
                            "Coming Soon",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(ProfileFragment.this, WebPages.class);
                    startActivity(intent);
                }
            }
        });
        rl_pr_pol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="pp";
                Intent intent=new Intent(ProfileFragment.this,WebPages.class);
                startActivity(intent);
            }
        });
        rl_hns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileFragment.this, HelpSupportPage.class);
                startActivity(intent);
            }
        });

        get_web_links();
    }

    public  void get_web_links() {
        {
            if (!Connectivity.isNetworkConnected(ProfileFragment.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.get_web_links();
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
                                idPBLoading.setVisibility(View.GONE);
                                SPHelper.tnc=appResponse.getResponse().getGetweblinks().getTerms();
                                SPHelper.pp=appResponse.getResponse().getGetweblinks().getPrivacy_policy();
                                SPHelper.claim_warranty=appResponse.getResponse().getGetweblinks().getClaim_warranty();
                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(ProfileFragment.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProfileFragment.this, MainActivity.class);
        startActivity(intent);
    }
}