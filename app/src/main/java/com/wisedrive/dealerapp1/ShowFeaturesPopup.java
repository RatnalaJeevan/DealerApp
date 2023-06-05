package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.PojoSwapVeh;
import com.wisedrive.dealerapp1.pojos.pojos.Feature;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_post_feature;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFeaturesPopup extends BottomSheetDialogFragment {

    ArrayList<Pojo_Module_list> pojo_module_listArrayList;
    public Adapter_features adapter_features;
    RecyclerView rv_features;
    Activity activity;
    private DealerApis apiInterface;
    ArrayList<Feature> featureArr;
    AppCompatButton  features_submit;
    TextView sub_desc;
    EditText sel_desr;
    ImageView imv_cross;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.activity_show_features_popup,
                container, false);
        activity=getActivity();
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        SPHelper.part_ids=new ArrayList<>();
        SPHelper.module_ids=new ArrayList<>();
        SPHelper.sel_fea=new ArrayList<>();
        imv_cross=v.findViewById(R.id.imv_cross);
        rv_features=v.findViewById(R.id.rv_features);
        sel_desr=v.findViewById(R.id.sel_desr);
        sub_desc=v.findViewById(R.id.sub_desc);
        features_submit=v.findViewById(R.id.features_submit);
        features_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                get_updated_features();
            }
        });

        sub_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sel_desr.getText().toString().equals(""))
                {
                    Toast.makeText(activity, "Enter desciption", Toast.LENGTH_SHORT).show();
                }else {
                    update_descr();
                }
            }
        });

        imv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        get_module_list();
        return v;
    }

    public void get_module_list()
    {
        Call<AppResponse> call = apiInterface.get_module_list(SPHelper.vehid);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200"))
                    {

                        String des=appResponse.getResponse().getDescription().getDescription();
                        sel_desr.setText(des);
                        pojo_module_listArrayList = new ArrayList<>();
                        pojo_module_listArrayList = appResponse.getResponse().getModuleList();

                        adapter_features = new Adapter_features(activity, pojo_module_listArrayList);
                        GridLayoutManager layoutManager2 = new GridLayoutManager(activity, 1);
                        rv_features.setLayoutManager(layoutManager2);
                        rv_features.setAdapter(adapter_features);
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });
    }

    public void get_updated_features()
    {
        featureArr = new ArrayList<>();
        for (int i = 0; i < pojo_module_listArrayList.size(); i++) {

            for (int j = 0; j < pojo_module_listArrayList.get(i).getPartDetails().size(); j++)
            {

                if (pojo_module_listArrayList.get(i).getPartDetails().get(j).getIs_selected().equals("y"))
                {
                    Feature obj = new Feature();
                    obj.setModuleId(pojo_module_listArrayList.get(i).getPartDetails().get(j).getModule_id());
                    obj.setPart_id(pojo_module_listArrayList.get(i).getPartDetails().get(j).getPart_id());
                    obj.setIsPresent("Y");
                    featureArr.add(obj);
                }

                else {
                    Feature obj = new Feature();
                    obj.setModuleId(pojo_module_listArrayList.get(i).getPartDetails().get(j).getModule_id());
                    obj.setPart_id(pojo_module_listArrayList.get(i).getPartDetails().get(j).getPart_id());
                    obj.setIsPresent("N");
                    featureArr.add(obj);
                }
            }
        }

        if (featureArr.isEmpty()) {
            Toast.makeText(activity, "Please select atleast one feature", Toast.LENGTH_SHORT).show();
        } else {
            post_add_features();
        }
    }

    public void post_add_features() {

        Pojo_post_feature pojo_post_feature = new Pojo_post_feature(SPHelper.vehid, featureArr);
        Call<AppResponse> call = apiInterface.post_features(pojo_post_feature);

        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                       // get_module_list();
                        MainActivity.getInstance().get_data_present();
                        featureArr = new ArrayList<>();
                        dismiss();

                    } else {
                        Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                // Handle the failure
            }
        });
    }

    public void update_descr() {

        PojoSwapVeh pojoupdate_des = new PojoSwapVeh(SPHelper.vehid, sel_desr.getText().toString());
        Call<AppResponse> call = apiInterface.add_desrpition(pojoupdate_des);

        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        get_module_list();
                       // featureArr = new ArrayList<>();

                    } else {
                        Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                // Handle the failure
            }
        });
    }

}