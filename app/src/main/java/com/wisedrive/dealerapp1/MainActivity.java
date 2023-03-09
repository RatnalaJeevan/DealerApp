package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterBrandLogos;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterModel;
import com.wisedrive.dealerapp1.adapters.adapters.ViewPagerAdapter;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.CustomViewPager;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.GifImageView;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.fragments.HomeFragment;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
   private static MainActivity  instance;
    private DealerApis apiInterface;
    TextView tv_profile,tv_home,tv_pack,tv_customer;
    LinearLayout layout_1,layout_2,layout_3,layout_4;
    ImageView iv_home,iv_pack,iv_customer,iv_profile,iv_filter;
    RelativeLayout rl_pack_selection;
    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_pack_selection=findViewById(R.id.rl_pack_selection);
        layout_2=findViewById(R.id.layout_2);
        layout_3=findViewById(R.id.layout_3);
        layout_4=findViewById(R.id.layout_4);
        layout_1=findViewById(R.id.layout_1);
        tv_customer=findViewById(R.id.tv_customer);
        tv_pack=findViewById(R.id.tv_pack);
        tv_home=findViewById(R.id.tv_home);
        tv_profile=findViewById(R.id.tv_profile);
        iv_home=findViewById(R.id.iv_home);
        iv_pack=findViewById(R.id.iv_pack);
        iv_customer=findViewById(R.id.iv_customer);
        iv_profile=findViewById(R.id.iv_profile);
        iv_filter=findViewById(R.id.iv_filter);

        if(SPHelper.camefrom.equals("filter")){
            tv_home.setVisibility(View.GONE);
            tv_pack.setVisibility(View.GONE);
            tv_customer.setVisibility(View.VISIBLE);
            tv_profile.setVisibility(View.GONE);
            iv_filter.setVisibility(View.VISIBLE);
            iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
            iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
            iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.customers_black));
            iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
            replaceFragment(new CustomerFragment());
            iv_filter.setVisibility(View.VISIBLE);
        }
        else if(SPHelper.camefrom.equals("no_pack")){
            tv_home.setVisibility(View.GONE);
            tv_pack.setVisibility(View.VISIBLE);
            tv_customer.setVisibility(View.GONE);
            tv_profile.setVisibility(View.GONE);
            iv_filter.setVisibility(View.GONE);
            iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
            iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.packages_sel_black));
            iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
            iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
            replaceFragment(new PackageFragment());
            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");

        }else if(SPHelper.comingfrom.equals("added")){

            iv_filter.setVisibility(View.GONE);
            replaceFragment(new HomeFragment());
            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");
        }
        else if(SPHelper.comingfrom.equals("activated")){

            iv_filter.setVisibility(View.GONE);
            replaceFragment(new HomeFragment());
            SoldVehDetails bottomSheetDialogFragment = new SoldVehDetails();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");
            CongratulationsPage bottomSheetDialogFragment1 = new CongratulationsPage();
            bottomSheetDialogFragment1.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");

        }
        else{
            iv_filter.setVisibility(View.GONE);
            replaceFragment(new HomeFragment());

        }
        layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_home.setVisibility(View.VISIBLE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.GONE);
                tv_profile.setVisibility(View.GONE);
                iv_filter.setVisibility(View.GONE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.home_selected_black));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                replaceFragment(new HomeFragment());
                SPHelper.camefrom="";
                SPHelper.comingfrom="";
            }
        });
        layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.VISIBLE);
                tv_customer.setVisibility(View.GONE);
                tv_profile.setVisibility(View.GONE);
                iv_filter.setVisibility(View.GONE);
                rl_pack_selection.setVisibility(View.VISIBLE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.packages_sel_black));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                SPHelper.camefrom="";
                SPHelper.comingfrom="";
                replaceFragment(new PackageFragment());
            }
        });
        layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.VISIBLE);
                tv_profile.setVisibility(View.GONE);
                iv_filter.setVisibility(View.VISIBLE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.customers_black));
                iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                SPHelper.camefrom="";
                SPHelper.comingfrom="";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                SPHelper.fuel_id="";
                SPHelper.trans_id="";
                SPHelper.price_from= "";
                SPHelper.price_to= "";
                SPHelper.kms_from="";
                SPHelper.kms_to="";
                SPHelper.selected_insp_status="";
                SPHelper.selected_brandid="";
                SPHelper.selected_insp_statuses=new ArrayList<>();
                replaceFragment(new CustomerFragment());
            }
        });
        layout_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.GONE);
                iv_filter.setVisibility(View.GONE);
                tv_profile.setVisibility(View.VISIBLE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                SPHelper.camefrom="";
                SPHelper.comingfrom="";
                Intent intent=new Intent(MainActivity.this, ProfileFragment.class);
                startActivity(intent);
            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,FilterPage.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup );
            }
        });

    }


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }


}