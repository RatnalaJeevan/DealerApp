package com.wisedrive.dealerapp1.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_new_reqlist;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;

import java.util.ArrayList;


public class New_Req_Fragment extends Fragment {
    ArrayList<Pojo_New_req> pojo_new_reqArrayList;
    Adapter_new_reqlist adapter_new_reqlist;
    RecyclerView rv_new_req;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new__req_, container, false);
        rv_new_req = rootView.findViewById(R.id.rv_new_req);

        pojo_new_reqArrayList = new ArrayList<>();
        pojo_new_reqArrayList.add(new Pojo_New_req("22 May,2023","3 Leads","3 Series Sedan",
                "Rs 10000-Rs 20000","10000-20000","2015-2020","1-2","Diesel","Automatic",
                "Green +2","2 Matching  Cars",R.drawable.bmw_icon));
        pojo_new_reqArrayList.add(new Pojo_New_req("22 May,2023","3 Leads","3 Series Sedan",
                "Rs 10000-Rs 20000","10000-20000","2015-2020","1-2","Diesel","Automatic",
                "Green +2","0 Matching Car, Add Car",R.drawable.bmw_icon));
        pojo_new_reqArrayList.add(new Pojo_New_req("22 May,2023","3 Leads","3 Series Sedan",
                "Rs 10000-Rs 20000","10000-20000","2015-2020","1-2","Diesel","Automatic",
                "Green +2","2 Matching  Cars",R.drawable.bmw_icon));

        // Use 'requireContext()' method to obtain the context
        adapter_new_reqlist = new Adapter_new_reqlist(requireContext(), pojo_new_reqArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rv_new_req.setLayoutManager(linearLayoutManager);
        rv_new_req.setAdapter(adapter_new_reqlist);



        return rootView;
    }
}