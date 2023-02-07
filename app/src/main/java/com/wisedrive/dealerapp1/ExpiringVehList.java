package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedrive.dealerapp1.adapters.adapters.AdapterAbtExpVeh;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.PojoAbtExpVehList;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExpiringVehList extends AppCompatActivity {

    public  int pageno=0;
    RecyclerView rv_veh_list;
    AdapterAbtExpVeh adapterAbtExpVeh;
    ArrayList<PojoAbtExpVehList> pojoAbtExpVehLists;
    RelativeLayout rl_back;
    EditText search_foryourcars;
    ProgressBar progress_bar,progress_centre;
    TextView no_results;
    ImageView cancel_btn,iv_filter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expiring_veh_list);
        rv_veh_list=findViewById(R.id.rv_veh_list);
        iv_filter=findViewById(R.id.iv_filter);
        rl_back=findViewById(R.id.rl_back);
        progress_centre=findViewById(R.id.progress_centre);
        pojoAbtExpVehLists=new ArrayList<>();
        rl_back.setOnClickListener(view -> {
            Intent intent=new Intent(ExpiringVehList.this, MainActivity.class);
            startActivity(intent);
        });
        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExpiringVehList.this,FilterPage.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup );
            }
        });
        getExpiringVehList();

    }

    public void getExpiringVehList()
    {
        pageno = 1;
        JSONObject params = new JSONObject();
//        if(search_foryourcars.getText().toString().equals("")){
//            progress_centre.setVisibility(View.VISIBLE);
//            progress_bar.setVisibility(View.GONE);
//        }else{
//            progress_centre.setVisibility(View.GONE);
//            progress_bar.setVisibility(View.VISIBLE);
//        }

        try {

            params.put("dealerId", SPHelper.getSPData(ExpiringVehList.this,SPHelper.dealerid,""));
            params.put("pageNo",String.valueOf(pageno));
            params.put("search","");
            params.put("id",SPHelper.exp_vehid);
            params.put("brandId","");
            params.put("priceFrom","");
            params.put("priceTo","");
            params.put("kmFrom","");
            params.put("kmTo","");
            params.put("fuelId","");
            params.put("transmissionId","");
            params.put("statusId","");
            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(ExpiringVehList.this, ServiceURL.BASEURL, WebService.HttpMethod.get,
                null, WebService.RequestType.shared.REST(ServiceURL.exp_vehList, WebService.RESTType.UrlEncode, params), false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                           // progress_bar.setVisibility(View.GONE);
                            progress_centre.setVisibility(View.GONE);
                            System.out.println(response);
                            JSONObject tktobj = response.getResponseObject();
                            try {

                                pojoAbtExpVehLists.clear();
                                if (tktobj.getJSONArray("ExpVehicleList").length() > 0)
                                {

                                    for (int i = 0; i < tktobj.getJSONArray("ExpVehicleList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("ExpVehicleList").getJSONObject(i);
                                        PojoAbtExpVehList leadobj = new PojoAbtExpVehList(apartment);
                                        pojoAbtExpVehLists.add(leadobj);
                                    }

                                }else{

                                }

                                if (pojoAbtExpVehLists.size() == 30 || pojoAbtExpVehLists.size() > 30) {
                                    pageno++;
                                }

                                ExpiringVehList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterAbtExpVeh.notifyDataSetChanged();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                           // progress_bar.setVisibility(View.GONE);
                            progress_centre.setVisibility(View.GONE);
                            Toast.makeText(ExpiringVehList.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                       // progress_bar.setVisibility(View.GONE);
                        progress_centre.setVisibility(View.GONE);
                    }
                });
    }

    private void getExpiringVehListpagination()
    {
//        if(search_foryourcars.getText().toString().equals("")){
//            progress_centre.setVisibility(View.VISIBLE);
//            progress_bar.setVisibility(View.GONE);
//        }else{
//            progress_centre.setVisibility(View.GONE);
//            progress_bar.setVisibility(View.VISIBLE);
//        }
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(ExpiringVehList.this,SPHelper.dealerid,""));
            params.put("pageNo",String.valueOf(pageno));
            params.put("search","");
            params.put("id",SPHelper.exp_vehid);
            params.put("brandId","");
            params.put("priceFrom","");
            params.put("priceTo","");
            params.put("kmFrom","");
            params.put("kmTo","");
            params.put("fuelId","");
            params.put("transmissionId","");
            params.put("statusId","");

            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(ExpiringVehList.this, ServiceURL.BASEURL,
                WebService.HttpMethod.get, null,
                WebService.RequestType.shared.REST(ServiceURL.exp_vehList, WebService.RESTType.UrlEncode, params), false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        System.out.println(response);
                        removeloader();
                        if (response.getResponseType().equalsIgnoreCase("200")) {
                           // progress_bar.setVisibility(View.GONE);
                            progress_centre.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                if (tktobj.getJSONArray("ExpVehicleList").length() > 0)
                                {
                                    for (int i = 0; i < tktobj.getJSONArray("ExpVehicleList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("ExpVehicleList").getJSONObject(i);
                                        PojoAbtExpVehList carobj = new PojoAbtExpVehList(apartment);
                                        pojoAbtExpVehLists.add(carobj);
                                    }

                                    if (pojoAbtExpVehLists.size() == 30 || pojoAbtExpVehLists.size() > 30) {
                                        pageno++;
                                    }
                                    rv_veh_list.post(new Runnable() {
                                        public void run() {
                                            adapterAbtExpVeh.notifyDataSetChanged();
                                            adapterAbtExpVeh.setLoaded();
                                        }
                                    });
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            progress_centre.setVisibility(View.GONE);
                           // progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                       // progress_bar.setVisibility(View.GONE);
                        progress_centre.setVisibility(View.GONE);
                        Toast.makeText(ExpiringVehList.this, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }


    public void removeloader()
    {
        pojoAbtExpVehLists.remove(pojoAbtExpVehLists.size() - 1);
        adapterAbtExpVeh.notifyItemRemoved(pojoAbtExpVehLists.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(getApplicationContext(),1);
        rv_veh_list.setLayoutManager(linearLayoutManager2);
        adapterAbtExpVeh = new AdapterAbtExpVeh(ExpiringVehList.this, pojoAbtExpVehLists, rv_veh_list);
        rv_veh_list.setAdapter(adapterAbtExpVeh);
        adapterAbtExpVeh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore()
            {
                if (pageno > 1)
                {
                    pojoAbtExpVehLists.add(null);
                    rv_veh_list.post(new Runnable()
                    {
                        public void run() {
                            adapterAbtExpVeh.notifyItemInserted(pojoAbtExpVehLists.size() - 1);
                            getExpiringVehListpagination();
                        }
                    });
                }
            }
        });
        getExpiringVehList();
    }

    private void hideKeybaord(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
    public void oncancelSelect(View view) {
        hideKeybaord(view);
        search_foryourcars.setText("");
        cancel_btn.setVisibility(View.GONE);
        getExpiringVehList();
    }
}