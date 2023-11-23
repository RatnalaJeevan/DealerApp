package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;

public class NotificationPage extends AppCompatActivity {

    RelativeLayout tv_AU_Update1;
    TextView tv_AU_skip,tv_no_thanks;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        SPHelper.sharedPreferenceInitialization(NotificationPage.this);
        tv_AU_skip=findViewById(R.id.tv_AU_skip);
        tv_AU_Update1= findViewById(R.id.tv_AU_Update1);
        tv_no_thanks=findViewById(R.id.tv_no_thanks);

        tv_AU_Update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(SPHelper.app_url));
                startActivity(httpIntent);
            }
        });

        tv_AU_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.getSPData(NotificationPage.this, SPHelper.dealerid, "").equals(""))
                {
                    Intent intent=new Intent(NotificationPage.this,LoginPage.class);
                    startActivity(intent);
                   /* intent.setClass(SplashScreen.this,
                            LoginPage.class);
                    SplashScreen.this.startActivity(intent);*/
                    NotificationPage.this.finish();
                }else {
                    Intent intent1=new Intent(NotificationPage.this,
                            MainActivity.class);
                    startActivity(intent1);
                    NotificationPage.this.finish();
                }
            }
        });

        if(SPHelper.can_skip.equalsIgnoreCase("y")){
            tv_AU_skip.setVisibility(View.VISIBLE);
            tv_no_thanks.setVisibility(View.GONE);
        }else {
            tv_AU_skip.setVisibility(View.GONE);
            tv_no_thanks.setVisibility(View.VISIBLE);
        }

        tv_no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}