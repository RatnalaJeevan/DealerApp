package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.R;

public class WebPages extends AppCompatActivity {

    WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_pages);
        mWebview=findViewById(R.id.mWebview);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setAppCacheEnabled(false);
        mWebview.clearCache(true);

        if(SPHelper.comingfrom.equals("tnc"))
        {
            mWebview .loadUrl(SPHelper.tnc);
        }else if(SPHelper.comingfrom.equals("pp"))
        {
            mWebview .loadUrl(SPHelper.pp);
        }else if(SPHelper.comingfrom.equals("report"))
        {
                mWebview .loadUrl(SPHelper.inspectionreport);
        }
        else if(SPHelper.comingfrom.equals("warranty"))
        {
                mWebview .loadUrl(SPHelper.claim_warranty);
        }
    }
}