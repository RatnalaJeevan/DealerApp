package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//import com.payu.base.models.ErrorResponse;
//import com.payu.base.models.PayUPaymentParams;
//import com.payu.checkoutpro.PayUCheckoutPro;
//import com.payu.checkoutpro.models.PayUCheckoutProConfig;
//import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
//import com.payu.ui.model.listeners.PayUCheckoutProListener;
//import com.payu.ui.model.listeners.PayUHashGenerationListener;
import com.wisedrive.dealerapp1.CongratulationsPage;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAddOnList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterMainPackLists;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnLists;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyPackage;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCFSession;
import com.wisedrive.dealerapp1.pojos.pojos.PojoMainPackLists;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageFragment extends Fragment implements CFCheckoutResponseCallback {
    public ArrayList<PojoMainPackLists> pojoMainPackLists;
    AdapterMainPackLists adapterMainPackLists;
    ArrayList<PojoAddOnLists> pojoAddOnLists;
    AdapterAddOnList adapterAddOnList;
    RecyclerView rv_main_pack_lists,rv_add_on_lists;
    RelativeLayout rl_packages,rl_add_ons,rl_pay,rl_pay_now;
    public Activity activity;
    ProgressBar idPBLoading;
    private DealerApis apiInterface;
    private static PackageFragment instance;
    public TextView price,percentage_amount_saved;
    String orderID = "";
    String paymentSessionID = "";
    CFSession.Environment cfEnvironment = CFSession.Environment.SANDBOX;
    String payment_status="",cforderid="";
    public String  gateway_id="",pack_type="Bundle";
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_package, container, false);
        activity=getActivity();
        instance=this;
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            e.printStackTrace();
        }
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_pay_now=rootView.findViewById(R.id.rl_pay_now);
        price=rootView.findViewById(R.id.price);
        rv_add_on_lists=rootView.findViewById(R.id.rv_add_on_lists);
        idPBLoading=rootView.findViewById(R.id.idPBLoading);
        rv_main_pack_lists=rootView.findViewById(R.id.rv_main_pack_lists);
        rl_packages=rootView.findViewById(R.id.rl_packages);
        rl_add_ons=rootView.findViewById(R.id.rl_add_ons);
        rl_pay=rootView.findViewById(R.id.rl_pay);
        percentage_amount_saved=rootView.findViewById(R.id.percentage_amount_saved);

//        final int start_color = ContextCompat.getColor(getContext(), R.color.start_clr);
//        final int end_clr = ContextCompat.getColor(getContext(), R.color.end_clr);
//        rv_main_pack_lists.addItemDecoration(new LinePagerIndicatorDecoration( start_color, end_clr,getContext()));
//        new PagerSnapHelper().attachToRecyclerView(rv_main_pack_lists);

        rl_packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_packages.setBackground(activity.getDrawable(R.drawable.cv_shroom));
                rl_add_ons.setBackground(null);
                rv_main_pack_lists.setVisibility(View.VISIBLE);
                rv_add_on_lists.setVisibility(View.GONE);
                rl_pay.setVisibility(View.VISIBLE);
            }
        });
        rl_add_ons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_add_ons.setBackground(activity.getDrawable(R.drawable.cv_shroom));
                rl_packages.setBackground(null);
                rv_main_pack_lists.setVisibility(View.GONE);
                rv_add_on_lists.setVisibility(View.VISIBLE);
                rl_pay.setVisibility(View.GONE);
            }
        });

        rl_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(price.getText().toString().equals("0")){
                    Common.CallToast(activity,"Select a Package",1);
                }else{

                    if(gateway_id.equals("1")){
                        //payu
                    }else{
                        create_sessionID();
                    }

                    System.out.println("id"+SPHelper.product_id+SPHelper.selected_main_pack_id+
                            SPHelper.selected_sub_packid+SPHelper.finalamount+"d_id"+
                            SPHelper.getSPData(activity, SPHelper.dealerid, ""));
                }
            }
        });
        get_package_type();
        get_add_onlist();
        return  rootView;
    }

    public void get_package_type() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getpackage_type(SPHelper.getSPData(activity, SPHelper.dealerid, ""),
                        pack_type);
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

                                pojoMainPackLists=new ArrayList<>();
                                pojoMainPackLists=appResponse.getResponse().getDealerPackageList();
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                                rv_main_pack_lists.setLayoutManager(layoutManager);
                                adapterMainPackLists=new AdapterMainPackLists(pojoMainPackLists,getContext());
                                rv_main_pack_lists.setAdapter(adapterMainPackLists);
                                //subpackagelist
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterMainPackLists.notifyDataSetChanged();
                                    }
                                });

                                gateway_id=pojoMainPackLists.get(0).getActivegateway().getPayment_gateway_id();


                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }

    }

    public void get_add_onlist() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                idPBLoading.setVisibility(View.VISIBLE);
                Call<AppResponse> call = apiInterface.getAddOnList(SPHelper.getSPData(activity, SPHelper.dealerid, ""));
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
                                pojoAddOnLists=new ArrayList<>();
                                pojoAddOnLists=appResponse.getResponse().getAddonList();
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                                rv_add_on_lists.setLayoutManager(layoutManager);
                                adapterAddOnList=new AdapterAddOnList(getContext(),pojoAddOnLists);
                                rv_add_on_lists.setAdapter(adapterAddOnList);
                                //subpackagelist
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterAddOnList.notifyDataSetChanged();
                                    }
                                });
                                SPHelper.gateway_id=appResponse.getResponse().getActivegateway().getPayment_gateway_id();

                            } else if (response_code.equals("300")) {
                                idPBLoading.setVisibility(View.GONE);
                            }
                        } else {
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
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
            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);
                PojoCFSession pojoCFSession=new PojoCFSession(SPHelper.getSPData(activity, SPHelper.d_email, ""),SPHelper.getSPData(activity, SPHelper.dealerno, ""),
                        SPHelper.getSPData(activity, SPHelper.dealername, ""),String.valueOf(SPHelper.finalamount),SPHelper.getSPData(activity, SPHelper.dealerid, ""));
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
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
    public void generate_hash(){

    }
//    public void payucheckout_page(){
//        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
//        builder.setAmount("1")
//                .setIsProduction(true)
//                .setProductInfo("Test")
//                .setKey("eHSpiw")
//                .setPhone("9000000000")
//                .setTransactionId(String.valueOf(System.currentTimeMillis()))
//                .setFirstName("Rat")
//                .setEmail("jeevan.ratnala@wisedrive.in");
//        //.setUserCredential("");
//        //.setAdditionalParams(<HashMap<String,Object>>); //Optional, can contain any additional PG params
//        PayUPaymentParams payUPaymentParams = builder.build();
//        PayUCheckoutPro.open(activity,payUPaymentParams,new PayUCheckoutProListener(){
//
//            @Override
//            public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
//
//            }
//
//            @Override
//            public void generateHash(@NonNull HashMap<String, String> valueMap, @NonNull PayUHashGenerationListener payUHashGenerationListener) {
//                //when ever there is a transaction b/w client and server hashes are required
//                        //sdk will parse u hash string
//
//                        String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
//                        String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);
//                        if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData))
//                        {
//                            //Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
//
////                                String hash =
////                                if (!TextUtils.isEmpty(hash)) {
////                                    HashMap<String, String> hashMap = new HashMap<>();
////                                    hashMap.put(hashName, hash);
////                                    hashGenerationListener.onHashGenerated(hashMap);
////                                }
//                        }
//            }
//
//            @Override
//            public void onError(@NonNull ErrorResponse errorResponse) {
//                String errorMessage = errorResponse.getErrorMessage();
//            }
//
//            @Override
//            public void onPaymentCancel(boolean b) {
//                //when it is backpressed
////                        //when payment is cancelled,we get response call back
//            }
//
//            @Override
//            public void onPaymentFailure(@NonNull Object response) {
//                HashMap<String,Object> result = (HashMap<String, Object>) response;
//                      String payuResponse = (String)result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
//                      String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
//            }
//
//            @Override
//            public void onPaymentSuccess(@NonNull Object response) {
//                HashMap<String,Object> result = (HashMap<String, Object>) response;
////                        //when transaction complted
//                   String payuResponse = (String)result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
////
////                        //response from merchat via furl or surl
//                      String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
//            }
//        });
//
//
//    }

    public  void post_buy_package(){
        {
            if(!Connectivity.isNetworkConnected(activity))
            {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                idPBLoading.setVisibility(View.VISIBLE);
                System.out.println("id"+SPHelper.product_id+SPHelper.selected_main_pack_id+
                        SPHelper.selected_sub_packid+SPHelper.finalamount+"d_id"+
                        SPHelper.getSPData(activity, SPHelper.dealerid, ""));

                PojoBuyPackage pojoBuyPackage=new PojoBuyPackage(SPHelper.getSPData(activity, SPHelper.dealerid, ""),
                        SPHelper.product_id,SPHelper.selected_main_pack_id,SPHelper.selected_sub_packid,String.valueOf(SPHelper.finalamount),payment_status,"online",
                        "",cforderid,orderID,"","","",
                        "0",gateway_id);
                Call<AppResponse> call =  apiInterface.buy_package(pojoBuyPackage);
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
                                 SPHelper.isSuccess="y";
                                 SPHelper.cf_msg=appResponse.getResponse().getMessage();
                                CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
                                bottomSheetDialogFragment.show(PackageFragment.this.getParentFragmentManager(), "CongratsPage");
                            } else if (response_code.equals("300"))
                            {
                                 idPBLoading.setVisibility(View.GONE);
                                 SPHelper.cf_msg=appResponse.getResponse().getMessage();
                                 SPHelper.isSuccess="n";
                                 CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
                                 bottomSheetDialogFragment.show(PackageFragment.this.getParentFragmentManager(), "CongratsPage");
                            }
                        } else{
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        idPBLoading.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public static PackageFragment getInstance() {
        return instance;
    }

    @Override
    public void onPaymentVerify(String orderID) {
        Log.e("onPaymentVerify", "verifyPayment triggered");
        payment_status="paid";
        post_buy_package();
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        Log.e("onPaymentFailure " + orderID, cfErrorResponse.getMessage());
        payment_status="not paid";
        post_buy_package();

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
            gatewayService.doPayment(activity, cfDropCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }
}