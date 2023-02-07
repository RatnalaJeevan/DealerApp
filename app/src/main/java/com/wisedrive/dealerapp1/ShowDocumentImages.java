package com.wisedrive.dealerapp1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;

public class ShowDocumentImages extends AppCompatActivity {

    RelativeLayout rl_back;
    ZoomageView doc_image;
    TextView doc_name;
    ImageView go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_document_images);
        rl_back=findViewById(R.id.rl_back);
        doc_name=findViewById(R.id.doc_name);
        doc_image=findViewById(R.id.doc_image);
        go_back=findViewById(R.id.go_back);
        doc_name.setText(SPHelper.doc_name);
        Glide.with(ShowDocumentImages.this).load(SPHelper.doc_image).placeholder(R.drawable.icon_noimage).into(doc_image);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}