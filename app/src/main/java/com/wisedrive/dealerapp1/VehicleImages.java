package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterNewVehImgs;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterVehicleImages;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoNewVehImgs;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleImages extends BottomSheetDialogFragment {
    RecyclerView rv_veh_imgs;
   // ArrayList<PojoVehicleImageList> pojoNewVehImgs;
    ArrayList<PojoNewVehImgs> pojoNewVehImgs;
    AdapterNewVehImgs adapterNewVehImgs;
   public static TextView text_pending_count;
    private static VehicleImages  instance;
    Activity activity;
    private DealerApis apiInterface;
    //@SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_vehicle_images,
                container, false);
        activity=getActivity();
        rv_veh_imgs=v.findViewById(R.id.rv_veh_imgs);
        text_pending_count=v.findViewById(R.id.text_pending_count);
        instance=this;

        rv_veh_imgs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Calculate the current visible item position
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                // Update the count in the TextView
                int totalCount = pojoNewVehImgs.size();
                String countText = (firstVisibleItemPosition + 1) + "/" + totalCount;
                text_pending_count.setText(countText);
            }
        });

        apiInterface = ApiClient.getClient().create(DealerApis.class);
      //  get_veh_images_list();

        pojoNewVehImgs=new ArrayList<>();
        //pojoNewVehImgs=appResponse.getResponse().getVehicleImageList();
        pojoNewVehImgs=SPHelper.veh_imgs;
        adapterNewVehImgs=new AdapterNewVehImgs(pojoNewVehImgs,activity);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false);
        rv_veh_imgs.setLayoutManager(linearLayoutManager2);
        rv_veh_imgs.setAdapter(adapterNewVehImgs);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterNewVehImgs.notifyDataSetChanged();
            }
        });
        return v;
    }
    public static VehicleImages getInstance() {
        return instance;
    }

}