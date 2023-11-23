package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cashfree.pg.ui.hidden.checkout.subview.payment.CardView;
import com.google.android.material.tabs.TabLayout;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterBrandLogos;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterModel;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_Colours;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_listed_vehicles;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_static_images;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_vehicle_status_list;
import com.wisedrive.dealerapp1.adapters.adapters.ViewPagerAdapter;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.CustomViewPager;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.GifImageView;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.CustomerFragment;
import com.wisedrive.dealerapp1.fragments.HomeFragment;
import com.wisedrive.dealerapp1.fragments.Home_fragment_2;
import com.wisedrive.dealerapp1.fragments.New_Req_Fragment;
import com.wisedrive.dealerapp1.fragments.PackageFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.PojoSwapVeh;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_colours;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listed_vehicle_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listin_portal;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_post_feature;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_vehicle_status_list;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
   private static MainActivity  instance;
   private DealerApis apiInterface;
   public TextView tv_profile,tv_home,tv_pack,tv_customer,bundle_label,label_single;
    LinearLayout layout_1,layout_2,layout_3,layout_4;
   public ImageView iv_home,iv_pack,iv_customer,iv_profile,iv_filter,iv_search;
    RelativeLayout rl_pack_selection,rl_single,rl_bundle;
   public int count1=0,count2=0,count3=0;
    AppCompatButton add_new_car_button;
    RelativeLayout rl_transperant,rl_new_car,new_inspection,rl_newdetails_adding;
   public View card_btm_nav;
    LinearLayout l_bottom;
    AppCompatButton add_confirm_button,buy_pac;
    ImageView cross_matched_veh,imv_listedcars_cross,alert_popup_cross,imv_colorpopup_cross
            ,portal_popup_cross,imv_featurecross_button,image_popup_cross;
   public RelativeLayout rl_portal_transperant;
    EditText ed_listingprice;
    public RecyclerView rv_vehicle_status_list;
    public RelativeLayout rl_transperant_cars_list,rl_cars_list,rl_top,
            rl_transperant_listed_cars,rl_listed_cars,rl_trans_alert_pop_up,rl_alert,rl_color_popup_transperant,
            rl_transperant_pop_feature,rl_transperant_add_image;
    AppCompatButton add_car_button,price_submit;
    public TextView text_add_images;
    public TextView text_add_features;
    public TextView tv_swap_content,sel_veh_no;
    public RecyclerView rv_listed_vehicle_list;
    Adapter_listed_vehicles adapter_listed_vehicles;
    ArrayList<Pojo_listed_vehicle_list>pojo_listed_vehicle_listArrayList;
    public RecyclerView rv_colors;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        init_params();
        getWindow().setStatusBarColor(getColor(R.color.white));
        rl_transperant_cars_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant_cars_list.setEnabled(true);
            }
        });
        cross_matched_veh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_cars_list.setVisibility(View.INVISIBLE);
                rl_cars_list.setVisibility(View.INVISIBLE);
                card_btm_nav.setVisibility(View.VISIBLE);
            }
        });
        rl_transperant_listed_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_listed_cars.setEnabled(true);
            }
        });
        imv_listedcars_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_listed_cars.setVisibility(View.INVISIBLE);
                rl_listed_cars.setVisibility(View.INVISIBLE);
                rl_transperant_cars_list.setVisibility(View.INVISIBLE);
                card_btm_nav.setVisibility(View.VISIBLE);

            }
        });
        rl_trans_alert_pop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_trans_alert_pop_up.setEnabled(true);
            }
        });
        alert_popup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_trans_alert_pop_up.setVisibility(View.INVISIBLE);
                rl_alert.setVisibility(View.INVISIBLE);
                card_btm_nav.setVisibility(View.VISIBLE);

            }
        });
        rl_portal_transperant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_portal_transperant.setEnabled(true);

            }
        });
        portal_popup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_portal_transperant.setVisibility(View.INVISIBLE);
                card_btm_nav.setVisibility(View.VISIBLE);
                rl_trans_alert_pop_up.setVisibility(View.GONE);

            }
        });
        add_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_portal_transperant.setVisibility(View.VISIBLE);
                sel_veh_no.setText(SPHelper.vehno);
                // rl_trans_alert_pop_up.setVisibility(View.GONE);

            }
        });
        rl_color_popup_transperant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_color_popup_transperant.setEnabled(true);

            }
        });
        imv_colorpopup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_color_popup_transperant.setVisibility(View.INVISIBLE);
                card_btm_nav.setVisibility(View.VISIBLE);

            }
        });
        add_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPHelper.camefrom="add";
                SPHelper.vehno="";
                Intent intent=new Intent(MainActivity.this, AddNewCar.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(R.anim.slide_inup, R.anim.slide_outup);

            }
        });
        text_add_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // rl_transperant_add_image.setVisibility(View.VISIBLE);
                //getimage_part_list();

                ShowListedImages bottomSheetDialogFragment = new ShowListedImages();
                bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");

            }
        });
        text_add_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // rl_transperant_pop_feature.setVisibility(View.VISIBLE);
               // get_module_list();

                ShowFeaturesPopup bottomSheetDialogFragment = new ShowFeaturesPopup();
                bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");

            }
        });
        rl_transperant_pop_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_pop_feature.setEnabled(true);

            }
        });
        imv_featurecross_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_pop_feature.setVisibility(View.GONE);
            }
        });
        rl_transperant_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_add_image.setEnabled(true);

            }
        });
        image_popup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant_add_image.setVisibility(View.GONE);

            }
        });

        price_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(SPHelper.is_feature_present.equalsIgnoreCase("n"))
                {
                    Toast.makeText(MainActivity.this, "Please add features", Toast.LENGTH_SHORT).show();
                }
                else if(SPHelper.is_img_present.equalsIgnoreCase("n")){
                    Toast.makeText(MainActivity.this, "Please add images", Toast.LENGTH_SHORT).show();

                }
                else if(ed_listingprice.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Please add listing price", Toast.LENGTH_SHORT).show();

                }
                else {
                    if(SPHelper.is_swap.equals("y")){
                        swap_vehicle();
                    }else {
                        list_in_portal();
                    }
                }
            }
        });

        if(SPHelper.camefrom.equals("filter"))
        {
            count3=1;
            tv_home.setVisibility(View.GONE);
            tv_pack.setVisibility(View.GONE);
            tv_customer.setVisibility(View.VISIBLE);
            tv_profile.setVisibility(View.GONE);
           // iv_filter.setVisibility(View.VISIBLE);
            iv_search.setVisibility(View.VISIBLE);
            iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
            iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
            iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.customers_black));
            iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
         //   replaceFragment(new CustomerFragment());

            rl_top.setBackgroundResource(R.color.bg_location);
            replaceFragment(new New_Req_Fragment());
            add_new_car_button.setVisibility(View.GONE);
        }
        else if(SPHelper.camefrom.equals("no_pack")){
            count2=1;
            tv_home.setVisibility(View.GONE);
            tv_pack.setVisibility(View.VISIBLE);
            tv_customer.setVisibility(View.GONE);
            tv_profile.setVisibility(View.GONE);
            iv_filter.setVisibility(View.GONE);
            iv_search.setVisibility(View.GONE);
            add_new_car_button.setVisibility(View.GONE);
            iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
            iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.packages_sel_black));
            iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
            iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
            rl_top.setBackgroundResource(R.color.white);
            replaceFragment(new PackageFragment());
            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");

        }
        else if(SPHelper.comingfrom.equals("added")){
            count1=1;
            iv_filter.setVisibility(View.GONE);
            iv_search.setVisibility(View.GONE);
           // replaceFragment(new HomeFragment());
            add_new_car_button.setVisibility(View.VISIBLE);
            replaceFragment(new Home_fragment_2());
            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");
        }
        else if(SPHelper.comingfrom.equals("activated"))
        {
            count1=1;
            iv_filter.setVisibility(View.GONE);
            iv_search.setVisibility(View.GONE);
           // replaceFragment(new HomeFragment());
            add_new_car_button.setVisibility(View.VISIBLE);
            replaceFragment(new Home_fragment_2());
            SoldVehDetails bottomSheetDialogFragment = new SoldVehDetails();
            bottomSheetDialogFragment.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");
            CongratulationsPage bottomSheetDialogFragment1 = new CongratulationsPage();
            bottomSheetDialogFragment1.show(MainActivity.this.getSupportFragmentManager(), "CongratsPage");
        }
        else
        {
            if(SPHelper.fragment_is.equals("profile"))
            {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.GONE);
                iv_filter.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                add_new_car_button.setVisibility(View.GONE);
                tv_profile.setVisibility(View.VISIBLE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                Intent intent=new Intent(MainActivity.this, ProfileFragment.class);
                SPHelper.fragment_is="home";
                startActivity(intent);
            }
           else if(SPHelper.fragment_is.equals("req"))
            {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.VISIBLE);
                tv_profile.setVisibility(View.GONE);
                iv_filter.setVisibility(View.INVISIBLE);
                iv_search.setVisibility(View.VISIBLE);
                add_new_car_button.setVisibility(View.GONE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.customers_black));
                iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                rl_top.setBackgroundResource(R.color.bg_location);
                SPHelper.camefrom = "";
                SPHelper.comingfrom = "";
                SPHelper.pojoAllCarBrands = new ArrayList<>();
                SPHelper.fuel_id = "";
                SPHelper.trans_id = "";
                SPHelper.price_from = "";
                SPHelper.price_to = "";
                SPHelper.kms_from = "";
                SPHelper.kms_to = "";
                SPHelper.selected_insp_status = "";
                SPHelper.selected_brandid = "";
                SPHelper.selected_insp_statuses = new ArrayList<>();
                //  replaceFragment(new CustomerFragment());
                replaceFragment(new New_Req_Fragment());
                count3=1;
                count2=0;
                count1=0;
            }
            else {
                count1=1;
                SPHelper.fragment_is="home";
                iv_filter.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                // replaceFragment(new HomeFragment());
                add_new_car_button.setVisibility(View.VISIBLE);
                rl_top.setBackgroundResource(R.color.white);
                replaceFragment(new Home_fragment_2());
            }
        }

        layout_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(count1==0) {
                    tv_home.setVisibility(View.VISIBLE);
                    tv_pack.setVisibility(View.GONE);
                    tv_customer.setVisibility(View.GONE);
                    tv_profile.setVisibility(View.GONE);
                    iv_filter.setVisibility(View.GONE);
                    iv_search.setVisibility(View.GONE);
                    add_new_car_button.setVisibility(View.VISIBLE);
                    rl_pack_selection.setVisibility(View.GONE);
                    iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.home_selected_black));
                    iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                    iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                    iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                   // replaceFragment(new HomeFragment());
                    rl_top.setBackgroundResource(R.color.white);
                    replaceFragment(new Home_fragment_2());
                    SPHelper.camefrom = "";
                    SPHelper.comingfrom = "";
                    SPHelper.fragment_is = "home";
                    count1=1;
                    count2=0;
                    count3=0;
                }
            }
        });
        layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               if(count2==0) {
                   tv_home.setVisibility(View.GONE);
                   tv_pack.setVisibility(View.VISIBLE);
                   tv_customer.setVisibility(View.GONE);
                   tv_profile.setVisibility(View.GONE);
                   iv_filter.setVisibility(View.GONE);
                   iv_search.setVisibility(View.GONE);
                   add_new_car_button.setVisibility(View.GONE);
                   rl_pack_selection.setVisibility(View.VISIBLE);
                   iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                   iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.packages_sel_black));
                   iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                   iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                   rl_top.setBackgroundResource(R.color.white);
                   SPHelper.camefrom = "";
                   SPHelper.comingfrom = "";
                   SPHelper.fragment_is = "pack";
                   replaceFragment(new PackageFragment());
                   count2=1;
                   count1=0;
                   count3=0;
               }
            }
        });
        layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count3==0)
                {
                    tv_home.setVisibility(View.GONE);
                    tv_pack.setVisibility(View.GONE);
                    tv_customer.setVisibility(View.VISIBLE);
                    tv_profile.setVisibility(View.GONE);
                    iv_filter.setVisibility(View.INVISIBLE);
                    iv_search.setVisibility(View.VISIBLE);
                    add_new_car_button.setVisibility(View.GONE);
                    rl_pack_selection.setVisibility(View.GONE);
                    iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                    iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                    iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.customers_black));
                    iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                    rl_top.setBackgroundResource(R.color.bg_location);
                    SPHelper.fragment_is = "req";
                    SPHelper.camefrom = "";
                    SPHelper.comingfrom = "";
                    SPHelper.pojoAllCarBrands = new ArrayList<>();
                    SPHelper.fuel_id = "";
                    SPHelper.trans_id = "";
                    SPHelper.price_from = "";
                    SPHelper.price_to = "";
                    SPHelper.kms_from = "";
                    SPHelper.kms_to = "";
                    SPHelper.selected_insp_status = "";
                    SPHelper.selected_brandid = "";
                    SPHelper.selected_insp_statuses = new ArrayList<>();
                  //  replaceFragment(new CustomerFragment());
                    replaceFragment(new New_Req_Fragment());
                    count3=1;
                    count2=0;
                    count1=0;
                }
            }
        });
        layout_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tv_home.setVisibility(View.GONE);
                tv_pack.setVisibility(View.GONE);
                tv_customer.setVisibility(View.GONE);
                iv_filter.setVisibility(View.GONE);
                iv_search.setVisibility(View.GONE);
                add_new_car_button.setVisibility(View.GONE);
                tv_profile.setVisibility(View.VISIBLE);
                rl_pack_selection.setVisibility(View.GONE);
                iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_pack));
                iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                SPHelper.camefrom="";
                SPHelper.comingfrom="";
                SPHelper.fragment_is="profile";
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

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( New_Req_Fragment.getInstance().rl_search.getVisibility()==View.VISIBLE){
                    New_Req_Fragment.getInstance().rl_search.setVisibility(View.GONE);
                }else {
                    New_Req_Fragment.getInstance().rl_search.setVisibility(View.VISIBLE);
                }

            }
        });

        rl_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_single.setBackground(MainActivity.this.getDrawable(R.drawable.cardview_lightgrey_margined));
                rl_single.setBackgroundTintList(ColorStateList.valueOf(MainActivity.this.getColor(R.color.black)));
                label_single.setTextColor(ColorStateList.valueOf(MainActivity.this.getColor(R.color.white)));
                bundle_label.setTextColor(ColorStateList.valueOf(MainActivity.this.getColor(R.color.black)));
                rl_bundle.setBackground(null);
                PackageFragment.getInstance().percentage_amount_saved.setText("You saved upto "+"0%");
                PackageFragment.getInstance().price.setText("0");
                PackageFragment.getInstance().pack_type="Single";
                PackageFragment.getInstance().get_package_type();
            }
        });
        rl_bundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_bundle.setBackground(MainActivity.this.getDrawable(R.drawable.cardview_lightgrey_margined));
                rl_bundle.setBackgroundTintList(ColorStateList.valueOf(MainActivity.this.getColor(R.color.black)));
                label_single.setTextColor(ColorStateList.valueOf(MainActivity.this.getColor(R.color.black)));
                bundle_label.setTextColor(ColorStateList.valueOf(MainActivity.this.getColor(R.color.white)));
                rl_single.setBackground(null);
                PackageFragment.getInstance().percentage_amount_saved.setText("You saved upto "+"0%");
                PackageFragment.getInstance().price.setText("0");
                PackageFragment.getInstance().pack_type="Bundle";
                PackageFragment.getInstance().get_package_type();
            }
        });
        add_new_car_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_transperant.setVisibility(View.VISIBLE);
              //  card_btm_nav.setVisibility(View.GONE);
            }
        });

        rl_transperant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant.setEnabled(true);
                rl_transperant.setVisibility(View.GONE);
                card_btm_nav.setVisibility(View.VISIBLE);
            }
        });

        rl_new_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.carmodelid="";
                SPHelper.carbrandid="";
                SPHelper.camefrom="add";
                SPHelper.vehno="";
                SPHelper.pojoAllCarBrands=new ArrayList<>();
                Intent intent=new Intent(MainActivity.this, AddNewCar.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
            }
        });
        buy_pac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                rl_transperant_listed_cars.setVisibility(View.GONE);
                if(count2==0) {
                    tv_home.setVisibility(View.GONE);
                    tv_pack.setVisibility(View.VISIBLE);
                    tv_customer.setVisibility(View.GONE);
                    tv_profile.setVisibility(View.GONE);
                    iv_filter.setVisibility(View.GONE);
                    iv_search.setVisibility(View.GONE);
                    add_new_car_button.setVisibility(View.GONE);
                    rl_pack_selection.setVisibility(View.VISIBLE);
                    iv_home.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unselected_home));
                    iv_pack.setImageDrawable(MainActivity.this.getDrawable(R.drawable.packages_sel_black));
                    iv_customer.setImageDrawable(MainActivity.this.getDrawable(R.drawable.unslected_cust));
                    iv_profile.setImageDrawable(MainActivity.this.getDrawable(R.drawable.profile_icon));
                    rl_top.setBackgroundResource(R.color.white);
                    SPHelper.camefrom = "";
                    SPHelper.comingfrom = "";
                    SPHelper.fragment_is = "pack";
                    replaceFragment(new PackageFragment());
                    count2=1;
                    count1=0;
                    count3=0;
                }
            }
        });
        new_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.vehno="";
                SPHelper.goneto="";
                SPHelper.is_d_loc="n";
                Intent intent=new Intent(MainActivity.this, RequestVehInspection.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup);
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


    public void get_listed_veh_list()
    {
        Call<AppResponse> call = apiInterface.get_listed_vehList( SPHelper.getSPData(MainActivity.this,SPHelper.dealerid,""),"1",
                "");
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200"))
                    {
                        pojo_listed_vehicle_listArrayList= new ArrayList<>();
                        pojo_listed_vehicle_listArrayList = appResponse.getResponse().getListedVehList();
                        adapter_listed_vehicles= new Adapter_listed_vehicles(MainActivity.this,pojo_listed_vehicle_listArrayList);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_listed_vehicle_list.setLayoutManager(linearLayoutManager2);
                        rv_listed_vehicle_list.setAdapter(adapter_listed_vehicles);
                    }
                }
            }


            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });
    }


    public void init_params(){
        buy_pac=findViewById(R.id.buy_pac);
        rl_top=findViewById(R.id.rl_top);
        ed_listingprice=findViewById(R.id.ed_listingprice);
        price_submit=findViewById(R.id.price_submit);
        sel_veh_no=findViewById(R.id.sel_veh_no);
        tv_swap_content=findViewById(R.id.tv_swap_content);
        rl_transperant_pop_feature=findViewById(R.id. rl_transperant_pop_feature);
        rl_transperant_add_image=findViewById(R.id.rl_transperant_add_image);
        text_add_images=findViewById(R.id.text_add_images);
        text_add_features=findViewById(R.id.text_add_features);
        text_add_images.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        text_add_features.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        rl_portal_transperant=findViewById(R.id.rl_portal_transperant);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        rl_transperant_cars_list=findViewById(R.id.rl_transperant_cars_list);
        rl_cars_list=findViewById(R.id.rl_cars_list);
        rl_transperant_listed_cars=findViewById(R.id.rl_transperant_listed_cars);
        rl_listed_cars=findViewById(R.id.rl_listed_cars);
        rl_trans_alert_pop_up=findViewById(R.id.rl_trans_alert_pop_up);
        rl_alert=findViewById(R.id.rl_alert);
        rl_color_popup_transperant=findViewById(R.id.rl_color_popup_transperant);
        add_confirm_button=findViewById(R.id.add_confirm_button);
        rv_vehicle_status_list=findViewById(R.id. rv_vehicle_status_list);
        rv_listed_vehicle_list=findViewById(R.id.rv_listed_vehicle_list);
        rv_colors=findViewById(R.id.rv_colors);
        add_car_button=findViewById(R.id.add_car_button);
        iv_search=findViewById(R.id.iv_search);
        bundle_label=findViewById(R.id.bundle_label);
        label_single=findViewById(R.id.label_single);
        rl_single=findViewById(R.id.rl_single);
        rl_pack_selection=findViewById(R.id.rl_pack_selection);
        rl_bundle=findViewById(R.id.rl_bundle);
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
        add_new_car_button=findViewById(R.id.add_new_car_button);
        rl_transperant=findViewById(R.id. rl_transperant);
        rl_new_car=findViewById(R.id.rl_new_car);
        new_inspection=findViewById(R.id.new_inspection);
        rl_newdetails_adding=findViewById(R.id.rl_newdetails_adding);
        l_bottom=findViewById(R.id.l_bottom);
        card_btm_nav=findViewById(R.id.card_btm_nav);
        cross_matched_veh=findViewById(R.id.cross_matched_veh);
        imv_listedcars_cross=findViewById(R.id.imv_listedcars_cross);
        alert_popup_cross=findViewById(R.id.alert_popup_cross);
        imv_colorpopup_cross=findViewById(R.id.imv_colorpopup_cross);
        portal_popup_cross=findViewById(R.id.portal_popup_cross);
        imv_featurecross_button=findViewById(R.id.imv_featurecross_button);
        image_popup_cross=findViewById(R.id.image_popup_cross);

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

    public void swap_vehicle() {

        PojoSwapVeh pojoSwapVeh=new PojoSwapVeh(SPHelper.old_veh_id,SPHelper.vehid,ed_listingprice.getText().toString());
        Call<AppResponse> call = apiInterface.swap_veh(pojoSwapVeh);

        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200"))
                    {
                        rl_portal_transperant.setVisibility(View.GONE);
                        rl_transperant_listed_cars.setVisibility(View.GONE);
                        rl_trans_alert_pop_up.setVisibility(View.GONE);
                        New_Req_Fragment.getInstance().get_req_list();
                        Toast.makeText(MainActivity.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                // Handle the failure
            }
        });
    }

    public void list_in_portal()
    {
        Pojo_listin_portal pojo_listin_portal = new Pojo_listin_portal(SPHelper.vehid, ed_listingprice.getText().toString().trim());
        Call<AppResponse> call = apiInterface.list_in_portal(pojo_listin_portal);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        rl_portal_transperant.setVisibility(View.GONE);
                        rl_transperant_listed_cars.setVisibility(View.GONE);
                        rl_trans_alert_pop_up.setVisibility(View.GONE);
                        New_Req_Fragment.getInstance().get_req_list();
                        Toast.makeText(MainActivity.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MainActivity.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

            }
        });
    }


    public void get_data_present()
    {
        Call<AppResponse> call = apiInterface.get_checked_data(SPHelper.vehid);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200"))
                    {
                       SPHelper.is_feature_present= appResponse.getResponse().getDataPresent().getIs_feature_present();
                       SPHelper.is_img_present= appResponse.getResponse().getDataPresent().getIs_image_present();

                        if(SPHelper.is_img_present.equalsIgnoreCase("y")){

                            text_add_images.setTextColor(ContextCompat.getColorStateList(MainActivity.this, R.color.black));
                        }else {
                            text_add_images.setTextColor(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                        }

                        if(SPHelper.is_feature_present.equalsIgnoreCase("y")){

                            text_add_features.setTextColor(ContextCompat.getColorStateList(MainActivity.this, R.color.black));
                        }else {
                            text_add_features.setTextColor(ContextCompat.getColorStateList(MainActivity.this, R.color.red));
                        }


                    }
                }
            }


            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });
    }
}