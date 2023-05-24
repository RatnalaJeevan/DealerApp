package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterCarImageList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterEditedVehImgList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterNewVehImgs;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterCustomerCarsPage;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_feature_list;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_static_images;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.HomeFragment;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.Feature;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferarray;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Update_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_features;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_lead_count;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listin_portal;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_mark_assold;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_post_feature;
import com.wisedrive.dealerapp1.pojos.pojos.pojo_static_image_data;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCarsPage extends AppCompatActivity {
    public  String KEY = "",onclick="";
    public  String SECRET = "";
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    ProgressBar progress_bar;
    public  int pageno=0;
    ArrayList<PojoAllCarsList> customer_cars_list;
    ArrayList<PojoAllCarsList> allCarsPages = new ArrayList();
    AdapterAllCarPage adapterAllCarPage;
    RecyclerView rv_all_cars;
    ImageView iv_filter,go_back;
    RelativeLayout rl_parent,rl_header;
    private DealerApis apiInterface;
    TextView heading;
    public RelativeLayout rl_cust_details,rl_cust,rl_purchase,rl_offers;
    private static AllCarsPage instance;
    RecyclerView rv_veh_imgs;
    public RelativeLayout rl_transparent1,rl_show_veh_images;
    public Dialog dialog;
  public RelativeLayout rl_transperant,rl_transperant_pop_up,rl_feature_pop_up,
          rl_transperant_add_image,rl_transperant_add_image_pop_up,rl_add_image,
          rl_car_imgs,rl_portal_transperant,rl_portal_menu,rl_list_in_portal,rl_mark_sold,
          rl_showlistingprice, rl_showmarksold,rl_listing_price,rl_price_transperant;
   public TextView comments,tv_no_cars;
   ImageView img1,imv_cross,check1,check2,image_popup_cross;
   TextView heading_fratures;
   EditText ed_name,ed_number,ed_listingprice;
   AppCompatButton mark_assold,price_submit,features_submit;
    public static final int GALLERY_REQ_CODE = 1000;
    public static final int CAMERA_REQ_CODE = 100;
    String module_id;


    int count1=0,count2=0,count3=0;

    ArrayList<Pojo_part_list>pojo_part_listArrayList=new ArrayList<>();
    Adapter_static_images adapter_static_images;
    RecyclerView rv_car_imagelist;

    ArrayList<Pojo_Module_list>pojo_module_listArrayList;
    Adapter_features adapter_features;
    RecyclerView rv_features;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance=this;
        customer_cars_list=new ArrayList<>();
        setContentView(R.layout.activity_all_cars_page);
        heading_fratures=findViewById(R.id.heading_fratures);
        imv_cross=findViewById(R.id.imv_cross);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        //  heading_fratures.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        rl_transperant=findViewById(R.id.rl_transperant);
        rl_feature_pop_up=findViewById(R.id.rl_feature_pop_up);
        rl_transperant_add_image=findViewById(R.id.rl_transperant_add_image);
        rl_transperant_add_image_pop_up=findViewById(R.id.rl_transperant_add_image_pop_up);
        rl_add_image=findViewById(R.id.rl_add_image);
        rl_car_imgs=findViewById(R.id.rl_car_imgs);
        rl_portal_transperant=findViewById(R.id.rl_portal_transperant);
        rl_portal_menu=findViewById(R.id.rl_portal_menu);
        rl_list_in_portal=findViewById(R.id.rl_list_in_portal);
        rl_mark_sold=findViewById(R.id.rl_mark_sold);
        rl_showlistingprice=findViewById(R.id.rl_showlistingprice);
        rl_showmarksold=findViewById(R.id.rl_showmarksold);
        check1=findViewById(R.id.check1);
        check2=findViewById(R.id.check2);
        rl_listing_price=findViewById(R.id.rl_listing_price);
        rl_price_transperant=findViewById(R.id.rl_price_transperant);
        rv_features=findViewById(R.id.rv_features);
        img1=findViewById(R.id.img1);
        rv_car_imagelist=findViewById(R.id.rv_car_imagelist);
        progress_bar=findViewById(R.id.progress_bar);
        go_back=findViewById(R.id.go_back);
        rl_header=findViewById(R.id.rl_header);
        iv_filter=findViewById(R.id.iv_filter);
        rv_all_cars=findViewById(R.id.rv_all_cars);
        rl_parent=findViewById(R.id.rl_parent);
        tv_no_cars=findViewById(R.id.tv_no_cars);
        heading=findViewById(R.id.heading);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_show_veh_images=findViewById(R.id.rl_show_veh_images);
        rv_veh_imgs=findViewById(R.id.rv_veh_imgs);
        image_popup_cross=findViewById(R.id.image_popup_cross);
        ed_number=findViewById(R.id.ed_number);
        ed_name=findViewById(R.id.ed_name);
        mark_assold=findViewById(R.id.mark_assold);
        ed_listingprice=findViewById(R.id.ed_listingprice);
        price_submit=findViewById(R.id.price_submit);
        features_submit=findViewById(R.id.features_submit);

        dialog = new Dialog(AllCarsPage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.pop_up_comments);
        dialog.setCancelable(true);
        comments=dialog.findViewById(R.id.comments) ;
        if(SPHelper.goneto.equals("edited")){

            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(AllCarsPage.this.getSupportFragmentManager(), "CongratsPage");
        }
        rl_transparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_veh_images.setVisibility(View.GONE);
            }
        });
        customer_cars_list=new ArrayList<>();

        rl_transperant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant.setEnabled(true);
                rl_transperant.setVisibility(View.INVISIBLE);
                rl_feature_pop_up.setVisibility(View.INVISIBLE);
            }
        });
        imv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant.setVisibility(View.INVISIBLE);
                rl_feature_pop_up.setVisibility(View.INVISIBLE);

            }
        });
        rl_transperant_add_image_pop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant_add_image_pop_up.setEnabled(true);
                rl_transperant_add_image.setVisibility(View.INVISIBLE);
                rl_add_image.setVisibility(View.INVISIBLE);
            }
        });
        image_popup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_transperant_add_image.setVisibility(View.INVISIBLE);
                rl_add_image.setVisibility(View.INVISIBLE);

            }
        });
        rl_portal_transperant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_portal_transperant.setEnabled(true);
                rl_portal_menu.setVisibility(View.INVISIBLE);
                rl_portal_transperant.setVisibility(View.INVISIBLE);
            }
        });
        rl_list_in_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (check1.getVisibility() == View.VISIBLE) {
                            // i1 is currently visible, so hide it and l1
                            check1.setVisibility(View.GONE);
                            rl_showlistingprice.setVisibility(View.GONE);
                        } else {
                            // i1 is currently hidden, so show it and l1
                            check1.setVisibility(View.VISIBLE);
                            rl_showlistingprice.setVisibility(View.VISIBLE);
                        }
                    }
                });

        rl_mark_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check2.getVisibility() == View.VISIBLE) {
                    // i1 is currently visible, so hide it and l1
                    check2.setVisibility(View.GONE);
                    rl_showmarksold.setVisibility(View.GONE);
                } else {
                    // i1 is currently hidden, so show it and l1
                    check2.setVisibility(View.VISIBLE);
                    rl_showmarksold.setVisibility(View.VISIBLE);
                }
            }
        });
        mark_assold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_marked_as_sold();
            }
        });
        price_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_list_in_portal();

            }
        });
        features_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post_add_features();
            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AllCarsPage.this,FilterPage.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_inup, R.anim.slide_outup );
               // finish();
            }
        });

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="";
                if(SPHelper.fragment_is.equals("profile")){
                    Intent intent=new Intent(AllCarsPage.this,ProfileFragment.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(AllCarsPage.this,MainActivity.class);
                    startActivity(intent);
                }
               // finish();
            }
        });
        //if coming from sold
        heading.setText(SPHelper.title);




        get_module_list();


    }

    public static AllCarsPage getInstance() {
        return instance;
    }

    //insp_req_vehicle,approved veh,re-insp,repair-req,sold cars with warranty
    /*if(search_foryourcars.getText().toString().equals("")){
            progress_bar.setVisibility(View.GONE);
            idPBLoading.setVisibility(View.VISIBLE);
        }else{
            progress_bar.setVisibility(View.VISIBLE);
            idPBLoading.setVisibility(View.GONE);
        }*/
    public void get_insp_veh_list() {
        progress_bar.setVisibility(View.VISIBLE);
        pageno = 1;
        JSONObject params = new JSONObject();
        try {

            params.put("dealerId",SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("withCooling",SPHelper.with_cool);
            params.put("withPack",SPHelper.with_pack);
            params.put("statusId",SPHelper.status_id);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);


            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get,
                null, WebService.RequestType.shared.REST(ServiceURL.insp_veh_list_url, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200")) {
                            progress_bar.setVisibility(View.GONE);
                           // idPBLoading.setVisibility(View.GONE);
                            System.out.println(response);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                customer_cars_list.clear();
                                if (tktobj.getJSONArray("AllInspectedVehWithStatus").length() > 0)
                                {
                                    tv_no_cars.setVisibility(View.GONE);
                                    for (int i = 0; i < tktobj.getJSONArray("AllInspectedVehWithStatus").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllInspectedVehWithStatus").getJSONObject(i);
                                        PojoAllCarsList leadobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(leadobj);
                                    }
                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    AllCarsPage.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                        }
                                    });
                                }
                                else {
                                    tv_no_cars.setVisibility(View.VISIBLE);
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            progress_bar.setVisibility(View.GONE);
                           // idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(AllCarsPage.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        progress_bar.setVisibility(View.GONE);
                       // idPBLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void get_insp_veh_list_pagination()
    {
       // progress_bar.setVisibility(View.VISIBLE);
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId",SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("withCooling",SPHelper.with_cool);
            params.put("withPack",SPHelper.with_pack);
            params.put("statusId",SPHelper.status_id);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            System.out.print(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get, null,
                WebService.RequestType.shared.REST(ServiceURL.insp_veh_list_url, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        System.out.println(response);
                        removeloader();
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                           // progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                if (tktobj.getJSONArray("AllInspectedVehWithStatus").length() > 0)
                                {
                                    for (int i = 0; i < tktobj.getJSONArray("AllInspectedVehWithStatus").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllInspectedVehWithStatus").getJSONObject(i);
                                        PojoAllCarsList carobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(carobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    rv_all_cars.post(new Runnable() {
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                            adapterAllCarPage.setLoaded();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                           // progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                       // progress_bar.setVisibility(View.GONE);
                        Toast.makeText(AllCarsPage.this, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }

    public void removeloader()
    {
        customer_cars_list.remove(customer_cars_list.size() - 1);
        adapterAllCarPage.notifyItemRemoved(customer_cars_list.size());
    }

    //all cars list,sold cars,public,private
    public void get_all_vehiclelist()
    {
       /* if(search_foryourcars.getText().toString().equals("")){
            progress_bar.setVisibility(View.GONE);
            idPBLoading.setVisibility(View.VISIBLE);
        }else{
            progress_bar.setVisibility(View.VISIBLE);
            idPBLoading.setVisibility(View.GONE);
        }*/
        progress_bar.setVisibility(View.VISIBLE);
        pageno = 1;
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold","n");
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            System.out.print("params"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get, null,
                WebService.RequestType.shared.REST(ServiceURL.allcarurl, WebService.RESTType.UrlEncode, params), false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response) {
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                           // idPBLoading.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {
                                customer_cars_list.clear();
                                if (tktobj.getJSONArray("AllcarList").length() > 0)
                                {
                                    tv_no_cars.setVisibility(View.GONE);
                                    for (int i = 0; i < tktobj.getJSONArray("AllcarList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllcarList").getJSONObject(i);
                                        PojoAllCarsList leadobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(leadobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }

                                    AllCarsPage.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                        }
                                    });
                                }else{
                                    tv_no_cars.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            //idPBLoading.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(AllCarsPage.this, response.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                        progress_bar.setVisibility(View.GONE);
                        //idPBLoading.setVisibility(View.GONE);
                    }
                });
    }
    private void getpagination()
    {
        progress_bar.setVisibility(View.VISIBLE);
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search","");
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold","n");
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            System.out.print("params"+params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new WebService().LoadwithUrl(AllCarsPage.this, ServiceURL.BASEURL, WebService.HttpMethod.get,
                null, WebService.RequestType.shared.REST(ServiceURL.allcarurl, WebService.RESTType.UrlEncode, params),
                false, 0, new ResponseListener() {
                    @Override
                    public void ResponseSuccess(ResponseExtension response)
                    {
                        removeloader();
                        if (response.getResponseType().equalsIgnoreCase("200"))
                        {
                            progress_bar.setVisibility(View.GONE);
                            JSONObject tktobj = response.getResponseObject();
                            try {

                                if (tktobj.getJSONArray("AllcarList").length() > 0)
                                {

                                    for (int i = 0; i < tktobj.getJSONArray("AllcarList").length(); i++) {
                                        JSONObject apartment = tktobj.getJSONArray("AllcarList").getJSONObject(i);
                                        PojoAllCarsList carobj = new PojoAllCarsList(apartment);
                                        customer_cars_list.add(carobj);
                                    }

                                    if (customer_cars_list.size() == 30 || customer_cars_list.size() > 30) {
                                        pageno++;
                                    }
                                    rv_all_cars.post(new Runnable() {
                                        public void run() {
                                            adapterAllCarPage.notifyDataSetChanged();
                                            adapterAllCarPage.setLoaded();
                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        else{
                            progress_bar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void ResponseFailure(int responseCode, String errorMsg) {
                       progress_bar.setVisibility(View.GONE);
                        Toast.makeText(AllCarsPage.this, errorMsg, Toast.LENGTH_SHORT).show();
                        removeloader();
                    }
                });

    }
   /* public void update_image(){
        Pojo_Update_list pojo_update_list=new Pojo_Update_list("","");
        Call<AppResponse> call =  apiInterface.update_image(pojo_update_list);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {

                    }

                }
            }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

                }
            });
        }  */

    public void getimage_part_list(){
        System.out.println("vehid"+SPHelper.vehid);
        Call<AppResponse> call =apiInterface.part_list(SPHelper.vehid,"1");
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                         pojo_part_listArrayList= new ArrayList<>();
                         pojo_part_listArrayList=appResponse.getResponse().getPartDetails();
                adapter_static_images = new Adapter_static_images(AllCarsPage.this,pojo_part_listArrayList);
                GridLayoutManager layoutManager = new GridLayoutManager(AllCarsPage.this, 2);
                rv_car_imagelist.setLayoutManager(layoutManager);
                rv_car_imagelist.setAdapter(adapter_static_images);

                    }
                }

            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });




    }
    public void get_module_list(){
        Call<AppResponse>call=apiInterface.get_module_list();
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body() != null) {
                        if (response_code.equals("200")) {
                            pojo_module_listArrayList= new ArrayList<>();
                            pojo_module_listArrayList=appResponse.getResponse().getModuleList();
                          //  module_id =pojo_module_listArrayList.get(0).getModule_id();
                         //   SPHelper.module_id = module_id ;
                            adapter_features = new  Adapter_features(AllCarsPage.this,pojo_module_listArrayList);
                            GridLayoutManager layoutManager2 = new GridLayoutManager(AllCarsPage.this, 1);
                            rv_features.setLayoutManager(layoutManager2);
                            rv_features.setAdapter(adapter_features);

                        }
                    }
                }



            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });
    }

    public void lead_view_count(){
        Call<AppResponse> call =apiInterface.lead_count("961");
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        customer_cars_list = new ArrayList<>();
                        PojoAllCarsList leadCount = appResponse.getResponse().getLeadCount();
                        if (leadCount != null) {
                          /*  customer_cars_list.add(leadCount);
                            customer_cars_list=appResponse.getResponse().getLeadCount();
                            adapterAllCarPage = new  AdapterAllCarPage(AllCarsPage.this, customer_cars_list);
                            GridLayoutManager layoutManager = new GridLayoutManager(AllCarsPage.this, 2);
                            rv_car_imagelist.setLayoutManager(layoutManager);
                            rv_car_imagelist.setAdapter(adapterAllCarPage); */
                        }


                    }
                }

            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {

            }
        });

    }
    public void post_list_in_portal(){
        Pojo_listin_portal pojo_listin_portal=new Pojo_listin_portal(SPHelper.vehid,ed_listingprice.getText().toString().trim());
        Call<AppResponse> call =  apiInterface.list_in_portal(pojo_listin_portal);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(AllCarsPage.this, "Added successfully.", Toast.LENGTH_SHORT).show();
                        rl_portal_transperant.setVisibility(View.GONE);
                        get_insp_veh_list();




                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

            }
        });
    }
    public void post_marked_as_sold(){
        Pojo_mark_assold pojo_mark_assold=new Pojo_mark_assold(SPHelper.vehid,ed_name.getText().toString().trim(),ed_number.getText().toString().trim());
        Call<AppResponse> call =  apiInterface.mark_as_sold(pojo_mark_assold);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(AllCarsPage.this, "Added successfully.", Toast.LENGTH_SHORT).show();
                        rl_portal_transperant.setVisibility(View.GONE);
                        get_insp_veh_list();



                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

            }
        });
    }

    public void post_add_features()
    {
        ArrayList<Feature> featureArr = new ArrayList<>();
        for (int i = 0; i < pojo_module_listArrayList.size(); i++)
        {
            if (pojo_module_listArrayList.get(i).isVisible())
            {
                Toast.makeText(AllCarsPage.this,"isvisible true",Toast.LENGTH_SHORT).show();
                String moduleId = pojo_module_listArrayList.get(i).getModule_id();
                System.out.println("pojo"+pojo_part_listArrayList);
                for (int j = 0; j < pojo_part_listArrayList.size(); j++)
                {
                    if (pojo_part_listArrayList.get(j).isSelected())
                    {
                        Toast.makeText(AllCarsPage.this,"isselected true",Toast.LENGTH_SHORT).show();

                        String partId = pojo_part_listArrayList.get(j).getPart_id();
                        Feature obj = new Feature();
                        obj.setModuleId(moduleId);
                        obj.setPartId(partId);
                        obj.setIsPresent("Y");
                        featureArr.add(obj);
                    }
                }
            }
        }
        Pojo_post_feature pojo_post_feature = new Pojo_post_feature(SPHelper.vehid, featureArr);
        Call<AppResponse> call = apiInterface.post_features(pojo_post_feature);


        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(AllCarsPage.this, "Added successfully.", Toast.LENGTH_SHORT).show();
                        rl_portal_transperant.setVisibility(View.GONE);
                        get_insp_veh_list();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResponse> call, Throwable t) {
                // Handle the failure
            }
        });
    }


    @Override
    public void onResume()
    {
        super.onResume();
        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(getApplicationContext(),1);
        rv_all_cars.setLayoutManager(linearLayoutManager2);
        adapterAllCarPage=new AdapterAllCarPage(AllCarsPage.this,customer_cars_list,rv_all_cars);
        rv_all_cars.setAdapter(adapterAllCarPage);
        adapterAllCarPage.setOnLoadMoreListener(() -> {
            if (pageno > 1)
            {
                customer_cars_list.add(null);
                rv_all_cars.post(new Runnable()
                {
                    public void run() {
                        adapterAllCarPage.notifyItemInserted(customer_cars_list.size() - 1);
                        if(SPHelper.comingfrom.equals("all"))
                        {
                            if(customer_cars_list.size()>0){
                                getpagination();
                            }
                        }else{
                            if(customer_cars_list.size()>0){
                                get_insp_veh_list_pagination();
                            }
                        }
                    }
                });
            }
        });

        if(SPHelper.comingfrom.equals("all"))
        {
            get_all_vehiclelist();
        }else{
            get_insp_veh_list();
        }
    }

    @Override
    public void onBackPressed()
    {

        SPHelper.camefrom="";

        if(SPHelper.fragment_is.equals("profile")){
            Intent intent=new Intent(AllCarsPage.this,ProfileFragment.class);
            startActivity(intent);
        }else {
            Intent intent=new Intent(AllCarsPage.this,MainActivity.class);
            startActivity(intent);
        }
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                int position = adapter_static_images.getSelectedPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Uri selectedImage = data.getData();
                    String path = selectedImage.toString(); // Get the path of the selected image
                    Pojo_part_list list = pojo_part_listArrayList.get(position);
                    list.setImage(path); // Update the path in the object
                    adapter_static_images.notifyItemChanged(position);
                }
            } else if (requestCode == CAMERA_REQ_CODE) {
                int position = adapter_static_images.getSelectedPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(AllCarsPage.this.getContentResolver(), photo, "Title", null);
                    Pojo_part_list list = pojo_part_listArrayList.get(position);
                    list.setImage(path); // Update the path in the object
                    adapter_static_images.notifyItemChanged(position);
                }
            }
        }
    }






}
