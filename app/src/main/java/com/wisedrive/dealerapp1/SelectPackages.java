package com.wisedrive.dealerapp1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.dealerapp1.adapters.adapters.AdapterSelectPack;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterSubPackSelection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSelectPack;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class   SelectPackages extends AppCompatActivity {
    private DealerApis apiInterface;
    RelativeLayout rl_next,rl_go_back;
    RecyclerView rv_select_packs;
    ArrayList<PojoSelectPack> pojoSelectPacks;
    AdapterSelectPack adapterSelectPack;
    ImageView back;
    ProgressBar idPBLoading;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_packages);
        rl_go_back=findViewById(R.id.rl_go_back);
        idPBLoading=findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_next=findViewById(R.id.rl_next);
        rv_select_packs=findViewById(R.id.rv_select_packs);
        back=findViewById(R.id.back);
        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.selected_pack_id.equals("")){
                    Common.CallToast(SelectPackages.this,"Select Package",1);

                }else{
                    System.out.println("id"+SPHelper.getSPData(SelectPackages.this, SPHelper.dealerid, "")+
                            SPHelper.vehid+SPHelper.customer_id+SPHelper.selected_pack_id+SPHelper.dpp_id);
                    checkPackEligibility();
                }
            }
        });

        rl_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SelectPackages.this,CheckEligibility.class);
                startActivity(intent);
                finish();
            }
        });

        getDealerPurchasePackList();
    }

    public void getDealerPurchasePackList() {
        {
            if (!Connectivity.isNetworkConnected(SelectPackages.this)) {
                Toast.makeText(SelectPackages.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getDppList(SPHelper.getSPData(SelectPackages.this, SPHelper.dealerid, ""),
                        "1");
                call.enqueue(new Callback<AppResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);

                                pojoSelectPacks=new ArrayList<>();
                                pojoSelectPacks=appResponse.getResponse().getPurchasedPackList();
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SelectPackages.this,LinearLayoutManager.VERTICAL,false);
                                adapterSelectPack=new AdapterSelectPack(pojoSelectPacks,SelectPackages.this);
                                rv_select_packs.setLayoutManager(linearLayoutManager);
                                rv_select_packs.setAdapter(adapterSelectPack);
                                SelectPackages.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterSelectPack.notifyDataSetChanged();
                                    }
                                });

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(SelectPackages.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(SelectPackages.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }

    }

    public void checkPackEligibility() {
        {
            if (!Connectivity.isNetworkConnected(SelectPackages.this)) {
                Toast.makeText(SelectPackages.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.checkPackEligibility(SPHelper.getSPData(SelectPackages.this, SPHelper.dealerid, ""),
                        SPHelper.vehid,SPHelper.customer_id,SPHelper.selected_pack_id,SPHelper.dpp_id);
                call.enqueue(new Callback<AppResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                idPBLoading.setVisibility(View.GONE);
                                Activate bottomSheet = new Activate();
                                bottomSheet.show(SelectPackages.this.getSupportFragmentManager(), "Activate");

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                Common.CallToast(SelectPackages.this,appResponse.getResponse().getMessage(),1);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(SelectPackages.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(SelectPackages.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }

    }
}