package com.wisedrive.dealerapp1.fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.AllPayments;
import com.wisedrive.dealerapp1.MainActivity;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllPayments;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_new_reqlist;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class New_Req_Fragment extends Fragment {
    ArrayList<Pojo_New_req> pojo_new_reqArrayList;
    Adapter_new_reqlist adapter_new_reqlist;
    RecyclerView rv_new_req;
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    private DealerApis apiInterface;
    ProgressBar idPBLoading;
    public RelativeLayout rl_search;
    private static New_Req_Fragment instance;
    LinearLayoutManager linearLayoutManager;
    EditText search_veh;
    TextView tv_no_cars;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new__req_, container, false);
        instance=this;
        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.bg_location));
        rv_new_req = rootView.findViewById(R.id.rv_new_req);
        rl_search=rootView.findViewById(R.id.rl_search);
        search_veh=rootView.findViewById(R.id.search_veh);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        idPBLoading=rootView.findViewById(R.id.idPBLoading);
        tv_no_cars=rootView.findViewById(R.id.tv_no_cars);
        get_req_list();
         linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        adapter_new_reqlist = new Adapter_new_reqlist(requireContext());
        rv_new_req.setLayoutManager(linearLayoutManager);
        rv_new_req.setAdapter(adapter_new_reqlist);
        rv_new_req.addOnScrollListener(new  PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(pojo_new_reqArrayList.size()>=30){
                   get_req_pag();
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

        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        search();
        return rootView;
    }

    public void search() {

        search_veh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                if (search_veh.getText().toString().length() > 1)
                {
                    linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    adapter_new_reqlist = new Adapter_new_reqlist(requireContext());
                    rv_new_req.setLayoutManager(linearLayoutManager);
                    rv_new_req.setAdapter(adapter_new_reqlist);
                    get_req_list();
                } else if (search_veh.getText().toString().length() == 0)
                {
                    linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
                    adapter_new_reqlist = new Adapter_new_reqlist(requireContext());
                    rv_new_req.setLayoutManager(linearLayoutManager);
                    rv_new_req.setAdapter(adapter_new_reqlist);
                    get_req_list();
                    hideKeybaord();
                }
//                else {
//                    linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
//                    adapter_new_reqlist = new Adapter_new_reqlist(requireContext());
//                    rv_new_req.setLayoutManager(linearLayoutManager);
//                    rv_new_req.setAdapter(adapter_new_reqlist);
//                    get_req_list();
//                }

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

    public void get_req_list()
    {
        currentPage=1;

        if(!Connectivity.isNetworkConnected(requireContext()))
        {
            Toast.makeText(requireContext(),
                    "Please Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.get_requirents_list(search_veh.getText().toString(),"","","","",
                    "","","","","","","","","",
                    SPHelper.getSPData(requireContext(),SPHelper.dealerid,""),String.valueOf(currentPage));
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
                            pojo_new_reqArrayList = new ArrayList<>();
                            pojo_new_reqArrayList = appResponse.getResponse().getRequirementList();
                            if(pojo_new_reqArrayList.isEmpty()){
                                tv_no_cars.setVisibility(View.VISIBLE);
                                rv_new_req.setVisibility(View.GONE);
                            }
                            else
                            {
                                tv_no_cars.setVisibility(View.GONE);
                                rv_new_req.setVisibility(View.VISIBLE);
                                adapter_new_reqlist.addAll(pojo_new_reqArrayList);
                                if (currentPage <= TOTAL_PAGES)
                                {
                                    try {
                                        adapter_new_reqlist.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    isLastPage = true;
                                }
                                adapter_new_reqlist.removeLoadingFooter();
                            }
                        } else if (response_code.equals("300")) {
                            //load_list.setVisibility(View.GONE);
                            idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //load_list.setVisibility(View.GONE);
                        idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "internal server error", Toast.LENGTH_SHORT).show();
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

    private void get_req_pag()
    {

        if(!Connectivity.isNetworkConnected(requireContext()))
        {
            Toast.makeText(requireContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            Call<AppResponse> call =  apiInterface.get_requirents_list(search_veh.getText().toString(),"","","","",
                    "","","","","","","","","",
                    SPHelper.getSPData(requireContext(),SPHelper.dealerid,""),String.valueOf(currentPage));
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
                            pojo_new_reqArrayList = new ArrayList<>();
                            pojo_new_reqArrayList = appResponse.getResponse().getRequirementList();
                            if(pojo_new_reqArrayList.isEmpty()){
                                adapter_new_reqlist.removeLoadingFooter();
                                //noresults.setVisibility(View.VISIBLE);
                                //rv_veh_list.setVisibility(View.GONE);
                            }
                            else
                            {
                                //noresults.setVisibility(View.GONE);
                                // rv_veh_list.setVisibility(View.VISIBLE);
                                adapter_new_reqlist.removeLoadingFooter();
                                isLoading = false;
                                adapter_new_reqlist.addAll(pojo_new_reqArrayList);

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
                            Toast.makeText(requireContext(), appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(requireContext(), "internal server error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

    }

    public static New_Req_Fragment getInstance() {
        return instance;
    }

    private void hideKeybaord() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

}