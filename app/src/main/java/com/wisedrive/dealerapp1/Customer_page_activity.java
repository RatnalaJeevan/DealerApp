package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;

public class Customer_page_activity extends AppCompatActivity {
   public ImageView iv_search, iv_filter,back;
   Context context;
    public static Customer_page_activity instance;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);
        iv_search=findViewById(R.id.iv_search);
        iv_filter=findViewById(R.id.iv_filter);
        back=findViewById(R.id.back);
        instance = this;

        // Create an instance of the CustomerFragment and add it to the activity
        CustomerFragment customerFragment = new CustomerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, customerFragment)
                .commit();

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragment=new CustomerFragment();
//                fragment.search();

                CustomerFragment.getInstance().rl_search.setVisibility(View.VISIBLE);
            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Customer_page_activity.this,FilterPage.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup );
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Customer_page_activity.this, ProfileFragment.class);
                startActivity(intent);
            }
        });
    }

    public static Customer_page_activity getInstance() {
        return instance;
    }



}