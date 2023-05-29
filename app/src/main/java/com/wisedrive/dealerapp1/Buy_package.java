package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.fragments.PackageFragment;

public class Buy_package extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_package);

        PackageFragment packageFragment = new PackageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,packageFragment)
                .commit();
    }
}