package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_leads_page;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_leads_page;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Leads_page extends AppCompatActivity {
    RecyclerView rv_leads_list;
    ArrayList<Pojo_leads_page>pojo_leads_pageArrayList;
    Adapter_leads_page adapter_leads_page;
    RelativeLayout rl_back;
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    private static Leads_page instance;
    private DealerApis apiInterface;
    ProgressBar idPBLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads_page);
        rv_leads_list=findViewById(R.id.rv_leads_list);
        rl_back=findViewById(R.id.rl_back);
        idPBLoading=findViewById(R.id.idPBLoading);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        instance=this;
        getWindow().setStatusBarColor(getColor(R.color.background_page));
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        System.out.println("veh_id"+SPHelper.vehid);
        get_leads();
        adapter_leads_page = new Adapter_leads_page(this);
        GridLayoutManager layoutManager1 = new GridLayoutManager(Leads_page.this, 1);
        rv_leads_list.setLayoutManager(layoutManager1);
        rv_leads_list.setAdapter(adapter_leads_page);
        rv_leads_list.addOnScrollListener(new PaginationScrollListener(layoutManager1) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(pojo_leads_pageArrayList.size()>=30){
                    get_leads_pagin();
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

    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener
    {

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
    public void get_leads() {
        currentPage=1;
        if(!Connectivity.isNetworkConnected(Leads_page.this))
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
            Call<AppResponse> call =  apiInterface.get_leads_list(SPHelper.vehid, String.valueOf(currentPage));
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
                            pojo_leads_pageArrayList = new ArrayList<>();
                            pojo_leads_pageArrayList = appResponse.getResponse().getLeadList();
                            if(pojo_leads_pageArrayList.isEmpty()){

                            }
                            else
                            {
                                adapter_leads_page.addAll(pojo_leads_pageArrayList);
                                if (currentPage <= TOTAL_PAGES)
                                {
                                    try {
                                        adapter_leads_page.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    isLastPage = true;
                                }
                                adapter_leads_page.removeLoadingFooter();
                            }
                        } else if (response_code.equals("300")) {
                            //load_list.setVisibility(View.GONE);
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(Leads_page.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //load_list.setVisibility(View.GONE);
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(Leads_page.this, "internal server error", Toast.LENGTH_SHORT).show();
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
    private void get_leads_pagin()
    {

        if(!Connectivity.isNetworkConnected(Leads_page.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            Call<AppResponse> call =  apiInterface.get_leads_list(SPHelper.vehid, String.valueOf(currentPage));
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
                            pojo_leads_pageArrayList = new ArrayList<>();
                            pojo_leads_pageArrayList = appResponse.getResponse().getLeadList();
                            if(pojo_leads_pageArrayList.isEmpty()){
                                adapter_leads_page.removeLoadingFooter();
                                //noresults.setVisibility(View.VISIBLE);
                                //rv_veh_list.setVisibility(View.GONE);
                            }
                            else
                            {
                                //noresults.setVisibility(View.GONE);
                                // rv_veh_list.setVisibility(View.VISIBLE);
                                adapter_leads_page.removeLoadingFooter();
                                isLoading = false;
                                adapter_leads_page.addAll(pojo_leads_pageArrayList);

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
                            Toast.makeText(Leads_page.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(Leads_page.this, "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }
    public static Leads_page getInstance () {
        return instance;
    }
}
