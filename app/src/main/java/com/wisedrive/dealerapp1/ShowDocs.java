package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;

public class ShowDocs extends BottomSheetDialogFragment {

        ZoomageView doc_image;
        Activity activity;
        @SuppressLint("MissingInflatedId")
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.activity_show_docs,
                        container, false);
                activity=getActivity();
                doc_image=v.findViewById(R.id.doc_image);
                return v;
        }
}