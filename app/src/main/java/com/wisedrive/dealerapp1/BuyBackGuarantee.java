package com.wisedrive.dealerapp1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAddOnList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterSubPackSelection;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnComboList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyAddOn;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCFSession;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCustomerVehicleInfo;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyBackGuarantee extends AppCompatActivity implements CFCheckoutResponseCallback {
    private DealerApis apiInterface;
    ProgressBar idPBLoading;
    RelativeLayout pay_now,rl_show_popup,rl_check_status,rl_paymow_popup,rl_buy_addonpack,rl_req_ins,
            rl_transparent1,rl_transparent;
    RecyclerView rv_veh_category;
    ArrayList<PojoAddOnComboList> pojoAddOnComboLists;
    AdapterSubPackSelection adapterSubPackSelection;
    public TextView content,label_buy_guarantee,price,saved_amount,title2;
    ImageView back,car_img;
    EditText entered_vehno;
    String vehid,cust_id,cat_id;
    private static BuyBackGuarantee instance;

    String orderID = "";
    String paymentSessionID = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.SANDBOX;
    String payment_status="",cforderid="";
    String  gateway_id="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_back_guarantee);
        instance=this;
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        car_img=findViewById(R.id.car_img);
        title2=findViewById(R.id.title2);
        rl_req_ins=findViewById(R.id.rl_req_ins);
        back=findViewById(R.id.back);
        rl_buy_addonpack=findViewById(R.id.rl_buy_addonpack);
        saved_amount=findViewById(R.id.saved_amount);
        price=findViewById(R.id.price);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        entered_vehno=findViewById(R.id.entered_vehno);
        idPBLoading=findViewById(R.id.idPBLoading);
        content=findViewById(R.id.content);
        label_buy_guarantee=findViewById(R.id.label_buy_guarantee);
        rv_veh_category=findViewById(R.id.rv_veh_category);
        pay_now=findViewById(R.id.pay_now);
        rl_show_popup=findViewById(R.id.rl_show_popup);
        rl_check_status=findViewById(R.id.rl_check_status);
        rl_paymow_popup=findViewById(R.id.rl_paymow_popup);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_transparent=findViewById(R.id.rl_transparent);
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.add_onprice==0){
                    Common.CallToast(BuyBackGuarantee.this,"Select a Package",1);
                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                }
            }
        });
        rl_req_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.goneto="bbg";
                SPHelper.is_d_loc="n";
                SPHelper.vehno=entered_vehno.getText().toString();
                Intent intent=new Intent(BuyBackGuarantee.this,RequestVehInspection.class);
                startActivity(intent);
            }
        });
        rl_check_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(entered_vehno.getText().toString().equals("")){
                    Common.CallToast(BuyBackGuarantee.this,"Enter a Vehicle No.",1);

                }else if(entered_vehno.getText().toString().length()<6){
                    Common.CallToast(BuyBackGuarantee.this,"Enter Valid Vehicle No.",1);

                }else{
                    check_addOnEligibility();
                }
            }
        });
        rl_transparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_paymow_popup.setVisibility(View.GONE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        rl_buy_addonpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buyAddOnPack();
                create_sessionID();
            }
        });
        content.setText(SPHelper.long_description);
        label_buy_guarantee.setText(SPHelper.addon_name);
        get_add_onCombolist();
    }

    public void get_add_onCombolist() {
        {
            if (!Connectivity.isNetworkConnected(BuyBackGuarantee.this)) {
                Toast.makeText(BuyBackGuarantee.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getAddOnComboList(SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealerid, ""),
                        SPHelper.add_onid);
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
                                //bundl elist
                                pojoAddOnComboLists=new ArrayList<>();
                                pojoAddOnComboLists=appResponse.getResponse().getAddonCombList();
                                LinearLayoutManager layoutManager=new LinearLayoutManager(BuyBackGuarantee.this,LinearLayoutManager.VERTICAL,false);
                                rv_veh_category.setLayoutManager(layoutManager);
                                adapterSubPackSelection=new AdapterSubPackSelection(pojoAddOnComboLists,BuyBackGuarantee.this);
                                rv_veh_category.setAdapter(adapterSubPackSelection);
                                //subpackagelist
                                BuyBackGuarantee.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterSubPackSelection.notifyDataSetChanged();
                                    }
                                });

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(BuyBackGuarantee.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(BuyBackGuarantee.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }

    }

    public void check_addOnEligibility() {{
            if (!Connectivity.isNetworkConnected(BuyBackGuarantee.this)) {
                Toast.makeText(BuyBackGuarantee.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.CheckAddOnEligibility(SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealerid, ""),
                        entered_vehno.getText().toString(), SPHelper.add_onid,SPHelper.add_on_Main_pack_id,SPHelper.add_on_sub_pack_id);
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
                                car_img.setImageResource(R.drawable.insp_approved);
                                //bundl elist
                                 vehid=appResponse.getResponse().getAddoneligibility().getVehicle_id();
                                 cust_id=appResponse.getResponse().getAddoneligibility().getCustomer_id();
                                 cat_id=appResponse.getResponse().getAddoneligibility().getCategory_id();
                                 rl_req_ins.setVisibility(View.GONE);
                                 rl_paymow_popup.setVisibility(View.VISIBLE);
                                 rl_show_popup.setVisibility(View.GONE);
                                 rl_buy_addonpack.setVisibility(View.VISIBLE);
                                 title2.setText("Your vehicle is eligible for buy\nback guarantee");
                            } else if (response_code.equals("300")) {
                                //if is
                                String showReqinspection=appResponse.getResponse().getShowReqinspection();
                                car_img.setImageResource(R.drawable.alert);
                                if(showReqinspection.equalsIgnoreCase("y")){
                                    rl_req_ins.setVisibility(View.VISIBLE);
                                }else{
                                    rl_req_ins.setVisibility(View.GONE);
                                }
                                rl_paymow_popup.setVisibility(View.VISIBLE);
                                rl_show_popup.setVisibility(View.GONE);
                                rl_buy_addonpack.setVisibility(View.GONE);
                                title2.setText(appResponse.getResponse().getMessage());
                               // Toast.makeText(BuyBackGuarantee.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(BuyBackGuarantee.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(BuyBackGuarantee.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }

    }
    public  void buyAddOnPack(){
        {
            if(!Connectivity.isNetworkConnected(BuyBackGuarantee.this))
            {
                Toast.makeText(BuyBackGuarantee.this,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);

                        PojoBuyAddOn pojoBuyAddOn=new PojoBuyAddOn(SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealerid, ""),
                        SPHelper.add_onid,SPHelper.add_on_Main_pack_id,SPHelper.add_on_sub_pack_id,String.valueOf(SPHelper.add_onprice),
                        payment_status,"online","",cforderid,orderID,"",
                        "","","0",SPHelper.gateway_id,vehid,cust_id,
                        cat_id);
                Call<AppResponse> call =  apiInterface.buyAddOn(pojoBuyAddOn);
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
                                idPBLoading.setVisibility(View.GONE);
                                rl_paymow_popup.setVisibility(View.GONE);
                                SPHelper.isSuccess="y";
                                SPHelper.cf_msg=appResponse.getResponse().getMessage();
                                SPHelper.camefrom="no_pack";
                                Intent intent=new Intent(BuyBackGuarantee.this,MainActivity.class);
                                startActivity(intent);

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                                SPHelper.cf_msg=appResponse.getResponse().getMessage();
                                SPHelper.isSuccess="n";
                                CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
                                bottomSheetDialogFragment.show(BuyBackGuarantee.this.getSupportFragmentManager(), "CongratsPage"); }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(BuyBackGuarantee.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(BuyBackGuarantee.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void create_sessionID(){
        {
            if(!Connectivity.isNetworkConnected(BuyBackGuarantee.this))
            {
                Toast.makeText(BuyBackGuarantee.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);
                PojoCFSession pojoCFSession=new PojoCFSession(SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.d_email, ""),SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealerno, ""),
                        SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealername, ""),String.valueOf(SPHelper.add_onprice),SPHelper.getSPData(BuyBackGuarantee.this, SPHelper.dealerid, ""));
                Call<AppResponse> call =  apiInterface.generate_sessionID(pojoCFSession);
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
                                idPBLoading.setVisibility(View.GONE);
                                paymentSessionID=appResponse.getResponse().getOrderData().getPayment_session_id();
                                orderID=appResponse.getResponse().getOrderData().getOrder_id();
                                cforderid=appResponse.getResponse().getOrderData().getCf_order_id();

                                doDropCheckoutPayment();

                            } else if (response_code.equals("300"))
                            {
                                idPBLoading.setVisibility(View.GONE);
                                Toast.makeText(BuyBackGuarantee.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(BuyBackGuarantee.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(BuyBackGuarantee.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void doDropCheckoutPayment() {
        try {
            CFSession cfSession = new CFSession.CFSessionBuilder()
                    .setEnvironment(cfEnvironment)
                    .setPaymentSessionID(paymentSessionID)
                    .setOrderId(orderID)
                    .build();

            CFTheme cfTheme = new CFTheme.CFThemeBuilder()
                    .setNavigationBarBackgroundColor("#0057CC")
                    .setNavigationBarTextColor("#ffffff")
                    .setButtonBackgroundColor("#0057CC")
                    .setButtonTextColor("#ffffff")
                    .setPrimaryTextColor("#000000")
                    .setSecondaryTextColor("#000000")
                    .build();
            CFDropCheckoutPayment cfDropCheckoutPayment = new CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder()
                    .setSession(cfSession)
                    // .setCFUIPaymentModes(cfPaymentComponent)
                    .setCFNativeCheckoutUITheme(cfTheme)
                    .build();
            CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
            gatewayService.doPayment(BuyBackGuarantee.this, cfDropCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onPaymentVerify(String orderID) {
        Log.e("onPaymentVerify", "verifyPayment triggered");
        payment_status="paid";
        buyAddOnPack();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        Log.e("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        payment_status="not paid";
        buyAddOnPack();

    }

    public static BuyBackGuarantee getInstance() {
        return instance;
    }

}