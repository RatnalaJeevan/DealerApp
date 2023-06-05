package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllPayments;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllPayments;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllPayments extends AppCompatActivity {
    public  int pageno=0;
    ImageView go_back_home;
    RecyclerView rv_all_payments;
    ProgressBar idPBLoading;
   // public AdapterPaymentHistory adapterAllPayments;
   // public ArrayList<PojoPaymentHistory> pojoAllPayments = new ArrayList();
    private static AllPayments instance;
    private DealerApis apiInterface;
    private ProgressDialog progressDialog;
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    public AdapterAllPayments adapterAllPayments;
    public ArrayList<PojoAllPayments> pojoAllPayments;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_all_payments2);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rv_all_payments=findViewById(R.id.rv_all_payments);
        go_back_home=findViewById(R.id.go_back_home);
        idPBLoading=findViewById(R.id.idPBLoading);
        getWindow().setStatusBarColor(getColor(R.color.white));
        go_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllPayments.this, ProfileFragment.class);
                startActivity(intent);
            }
        });

        getPaymentHistory();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapterAllPayments = new AdapterAllPayments(this);
        rv_all_payments.setLayoutManager(linearLayoutManager);
        rv_all_payments.setAdapter(adapterAllPayments);
        rv_all_payments.addOnScrollListener(new  PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                System.out.println("allcarslist"+pojoAllPayments.size());
                if(pojoAllPayments.size()>=30){
                    getPaymentPagination();
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        public PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading() && !isLastPage())
            {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadMoreItems();
                }
            }
        }

        protected abstract void loadMoreItems();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();

    }


    public void getPaymentHistory() {
        currentPage=1;
        if(!Connectivity.isNetworkConnected(AllPayments.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
//            if(search_foryourcars.getText().toString().equals("")){
//                load_list.setVisibility(View.GONE);
//                idPBLoading.setVisibility(View.VISIBLE);
//            }else{
//                load_list.setVisibility(View.VISIBLE);
//                idPBLoading.setVisibility(View.GONE);
//            }
            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getPaymentHistory( SPHelper.getSPData(AllPayments.this,SPHelper.dealerid,""),String.valueOf(currentPage));
            call.enqueue(new Callback<AppResponse>()
            {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null)
                    {
                        if (response_code.equals("200"))
                        {
                           // load_list.setVisibility(View.GONE);
                            idPBLoading.setVisibility(View.GONE);
                            pojoAllPayments = new ArrayList<>();
                            pojoAllPayments = appResponse.getResponse().getPaymentHistory();
                            if(pojoAllPayments.isEmpty()){
                               // rv_veh_list.setVisibility(View.GONE);
                               // noresults.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                               // rv_veh_list.setVisibility(View.VISIBLE);
                               // noresults.setVisibility(View.GONE);
//                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllPayments.this, LinearLayoutManager.VERTICAL, false);
//                                adapterAllPayments = new AdapterAllPayments(AllPayments.this);
//                                rv_all_payments.setLayoutManager(linearLayoutManager);
//                                rv_all_payments.setAdapter(adapterAllPayments);
                                adapterAllPayments.addAll(pojoAllPayments);
                                if (currentPage <= TOTAL_PAGES)
                                {
                                    try {
                                        adapterAllPayments.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    isLastPage = true;
                                }
                                adapterAllPayments.removeLoadingFooter();
                            }
                        } else if (response_code.equals("300")) {
                            //load_list.setVisibility(View.GONE);
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(AllPayments.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //load_list.setVisibility(View.GONE);
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(AllPayments.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                   // load_list.setVisibility(View.GONE);
                    idPBLoading.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        }
    }
    private void getPaymentPagination()
    {

        if(!Connectivity.isNetworkConnected(AllPayments.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            Call<AppResponse> call =  apiInterface.getPaymentHistory( SPHelper.getSPData(AllPayments.this,SPHelper.dealerid,""),String.valueOf(currentPage));
            call.enqueue(new Callback<AppResponse>()
            {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            pojoAllPayments = new ArrayList<>();
                            pojoAllPayments = appResponse.getResponse().getPaymentHistory();
                            if(pojoAllPayments.isEmpty()){
                                adapterAllPayments.removeLoadingFooter();
                                //noresults.setVisibility(View.VISIBLE);
                                //rv_veh_list.setVisibility(View.GONE);
                            }
                            else
                            {
                                //noresults.setVisibility(View.GONE);
                               // rv_veh_list.setVisibility(View.VISIBLE);
                                adapterAllPayments.removeLoadingFooter();
                                isLoading = false;
                                adapterAllPayments.addAll(pojoAllPayments);

                                if (currentPage != TOTAL_PAGES)
                                {
//                                    try {
//                                        adapterallcars.addLoadingFooter();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                } else isLastPage = true;
                            }

                        } else if (response_code.equals("300")) {
                            Toast.makeText(AllPayments.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(AllPayments.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }


    public static AllPayments getInstance() {
        return instance;
    }
}