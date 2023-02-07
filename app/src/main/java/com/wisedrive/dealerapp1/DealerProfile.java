package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;

public class DealerProfile extends Fragment {
    TextView yes,no;
    TextView dealer_name,dealer_phoneno;
    ImageView go_back_home;
    RelativeLayout rl_all_payments,rl_log_out,rl_all_cars;
    private Dialog dialog;
    Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        activity=getActivity();
        SPHelper.sharedPreferenceInitialization(activity);
        rl_all_cars=rootView.findViewById(R.id.rl_all_cars);
        rl_log_out=rootView.findViewById(R.id.rl_log_out);
        dealer_name=rootView.findViewById(R.id.dealer_name);
        dealer_phoneno=rootView.findViewById(R.id.dealer_phoneno);
        go_back_home=rootView.findViewById(R.id.go_back_home);
        rl_all_payments=rootView.findViewById(R.id.rl_all_payments);

        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(activity, SPHelper.dealerid, "");
                SPHelper.saveSPdata(activity, SPHelper.dealerno, "");
                SPHelper.saveSPdata(activity, SPHelper.dealername, "");
                SPHelper.saveSPdata(activity, SPHelper.dealerlogo, "");
                SPHelper.saveSPdata(activity, SPHelper.imagestaken, "");
                SPHelper.saveSPdata(activity, SPHelper.helplineno, "");
                SPHelper.saveSPdata(activity, SPHelper.awssecret, "");
                SPHelper.saveSPdata(activity, SPHelper.awskey, "");
                SPHelper.saveSPdata(activity, SPHelper.comet_authkey, "");
                SPHelper.saveSPdata(activity, SPHelper.comet_region, "");
                SPHelper.saveSPdata(activity, SPHelper.comet_appid, "");
                Intent i = new Intent(activity, LoginPage.class);
                startActivity(i);
               // finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        rl_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        rl_all_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AllPayments.class);
                startActivity(intent);
            }
        });

        rl_all_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.comingfrom="all";
                SPHelper.title="All Cars";
                Intent intent=new Intent(activity, AllCarsPage.class);
                startActivity(intent);
            }
        });

        go_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
        dealer_name.setText(SPHelper.getSPData(activity, SPHelper.dealername, ""));
        dealer_phoneno.setText(SPHelper.getSPData(activity, SPHelper.dealerno, ""));
        String dealerlogo=SPHelper.getSPData(activity, SPHelper.dealerlogo, "");
//        if (dealerlogo != null && !dealerlogo.isEmpty() && !dealerlogo.equals("null")) {
//            Glide.with(activity).load(dealerlogo).placeholder(R.drawable.icon_noimage).into(dealer_logo);
//        }
        return rootView;
    }
}