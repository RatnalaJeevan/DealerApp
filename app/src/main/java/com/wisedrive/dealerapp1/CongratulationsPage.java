package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.GifImageView;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;

public class CongratulationsPage extends BottomSheetDialogFragment {

    TextView label1;
    pl.droidsonroids.gif.GifImageView iv_anim;
    Activity activity;
    ImageView close;
    RelativeLayout rl_retry;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_warranty_activated_page, container, false);
        activity=getActivity();
        label1=v.findViewById(R.id.label1);
        iv_anim=v.findViewById(R.id.iv_anim);
        rl_retry=v.findViewById(R.id.rl_retry);
        close=v.findViewById(R.id.close);
        label1.setText(SPHelper.cf_msg);
        if(SPHelper.isSuccess.equals("y")){
            rl_retry.setVisibility(View.GONE);
            iv_anim.setImageResource(R.drawable.success_anim);
        }
        else if(SPHelper.isSuccess.equals("add")||SPHelper.isSuccess.equals("activate")){
            rl_retry.setVisibility(View.GONE);
            iv_anim.setImageResource(R.drawable.insp_approved);
        }else{
            rl_retry.setVisibility(View.VISIBLE);
            iv_anim.setImageResource(R.drawable.alert_anim);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rl_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }


}