package com.wisedrive.dealerapp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterAllCarPage;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_feature_list;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_features;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_static_images;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.BitmapUtility;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.RequestPermissionHandler;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseExtension;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.ResponseListener;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.fragments.ProfileFragment;
import com.wisedrive.dealerapp1.pojos.pojos.Feature;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Update_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_imagearray;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listin_portal;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_mark_assold;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_post_feature;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_static_image_data;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;
import com.wisedrive.dealerapp1.services1.services.ServiceURL;
import com.wisedrive.dealerapp1.services1.services.WebService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCarsPage extends AppCompatActivity {
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    ArrayList<String> final_imgs=new ArrayList<>();
    ArrayList<String> final_ids=new ArrayList<>();
    ProgressDialog dialog1;
    Uri cam_uri;
    String it_is="",filename,doc_url="";
    public int selectedObject=0;
    private RequestPermissionHandler mRequestPermissionHandler;
    public  String KEY = "",onclick="";
    public  String SECRET = "";
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    ProgressBar progress_bar;
    public  int pageno=0;
    ArrayList<PojoAllCarsList> customer_cars_list=new ArrayList<>();
    ArrayList<PojoAllCarsList> allCarsPages = new ArrayList();
    AdapterAllCarPage adapterAllCarPage;
    RecyclerView rv_all_cars;
    ImageView iv_filter,go_back,iv_search;
    RelativeLayout rl_parent,rl_header;
    private DealerApis apiInterface;
    TextView heading;
    public RelativeLayout rl_cust_details,rl_cust,rl_purchase,rl_offers;
    private static AllCarsPage instance;
    RecyclerView rv_veh_imgs;
    public RelativeLayout rl_transparent1,rl_show_veh_images;
    public Dialog dialog,dialog_cp;
    public TextView tv_cp_days,tv_cp_kms,cp_comments;
    public RelativeLayout rl_transperant,rl_transperant_pop_up,rl_feature_pop_up,
          rl_transperant_add_image,rl_transperant_add_image_pop_up,rl_add_image,
          rl_car_imgs,rl_portal_transperant,rl_portal_menu,rl_list_in_portal,rl_mark_sold,
          rl_showlistingprice, rl_showmarksold,rl_listing_price,rl_price_transperant,rl_search;
    public TextView comments,tv_no_cars;
    ImageView img1,imv_cross,check1,check2,image_popup_cross;
    TextView heading_fratures;
    EditText ed_name,ed_number,ed_listingprice;
    AppCompatButton mark_assold,price_submit,features_submit;
    public static final int GALLERY_REQ_CODE = 1000;
    public static final int CAMERA_REQ_CODE = 100;
    EditText search_veh;
    ArrayList<Pojo_part_list> pojo_part_listArrayList=new ArrayList<>();
    Adapter_static_images adapter_static_images;
    RecyclerView rv_car_imagelist;

    ArrayList<Pojo_Module_list>pojo_module_listArrayList;
    public Adapter_features adapter_features;
    RecyclerView rv_features;
    ArrayList<Pojo_imagearray> pojo_imagearrayArrayList;
    AppCompatButton add_feature_button;
    ArrayList<Feature> featureArr;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        instance=this;
        customer_cars_list=new ArrayList<>();
        setContentView(R.layout.activity_all_cars_page);

        AWSMobileClient.getInstance().initialize(this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(this,SPHelper.awskey,""),
                SPHelper.getSPData(this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();
        iv_search=findViewById(R.id.iv_search);
        rl_search=findViewById(R.id.rl_search);
        search_veh=findViewById(R.id.search_veh);
        heading_fratures=findViewById(R.id.heading_fratures);
        imv_cross=findViewById(R.id.imv_cross);
        add_feature_button=findViewById(R.id.add_feature_button);
        apiInterface = ApiClient.getClient().create(DealerApis.class);

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

        dialog_cp= new Dialog(AllCarsPage.this);
        dialog_cp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_cp.setContentView(R.layout.popup_cp);
        dialog_cp.setCancelable(true);
        tv_cp_days=dialog_cp.findViewById(R.id.tv_cp_days) ;
        tv_cp_kms=dialog_cp.findViewById(R.id.tv_cp_kms) ;
        cp_comments=dialog_cp.findViewById(R.id.cp_comments) ;
        if(SPHelper.goneto.equals("edited")){

            CongratulationsPage bottomSheetDialogFragment = new CongratulationsPage();
            bottomSheetDialogFragment.show(AllCarsPage.this.getSupportFragmentManager(), "CongratsPage");
        }
        rl_transparent1.setOnClickListener(new View.OnClickListener()
        {
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
            public void onClick(View view)
            {
                if(adapterAllCarPage.is_img_present.equalsIgnoreCase("y")&&
                        adapterAllCarPage.is_feat_present.equalsIgnoreCase("y")){
                    if (check1.getVisibility() == View.VISIBLE) {
                        // i1 is currently visible, so hide it and l1
                        check1.setVisibility(View.GONE);
                        rl_showlistingprice.setVisibility(View.GONE);
                    } else {
                        // i1 is currently hidden, so show it and l1
                        check1.setVisibility(View.VISIBLE);
                        rl_showlistingprice.setVisibility(View.VISIBLE);
                    }
                }else if(adapterAllCarPage.is_img_present.equalsIgnoreCase("n"))
                {
                    Toast.makeText(AllCarsPage.this,
                            " Please add vehicle images",
                            Toast.LENGTH_SHORT).show();
                }
                else if(adapterAllCarPage.is_feat_present.equalsIgnoreCase("n"))
                {
                    Toast.makeText(AllCarsPage.this,
                            " Please add vehicle features",
                            Toast.LENGTH_SHORT).show();
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


                if(!ed_number.getText().toString().equals("")&&!ed_number.getText().toString().matches(mobile_no_pattern)){
                    Toast.makeText(AllCarsPage.this,
                            " Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }

                    post_marked_as_sold();
            }
        });
        price_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ed_listingprice.getText().toString().isEmpty()){
                    Toast.makeText(AllCarsPage.this, "Enter price", Toast.LENGTH_SHORT).show();
                }else if(ed_listingprice.getText().toString().startsWith("0")){
                    Toast.makeText(AllCarsPage.this, "Enter valid price", Toast.LENGTH_SHORT).show();
                }
                else {
                    post_list_in_portal();
                }
            }
        });
        features_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    get_updated_features();
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

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_search.setVisibility(View.VISIBLE);
                search();
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




        add_feature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_updated_imgs();
            }
        });

        search();

    }

    public void search(){

        search_veh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(search_veh.getText().toString().length()>2)
                {
                    if(SPHelper.comingfrom.equals("all"))
                    {
                        get_all_vehiclelist();
                    }else{
                        get_insp_veh_list();
                    }
                }
                else if(search_veh.getText().toString().length()==0){
                    hideKeybaord();
                }
                else {
                    if(SPHelper.comingfrom.equals("all"))
                    {
                        get_all_vehiclelist();
                    }else{
                        get_insp_veh_list();
                    }
                }

            }
        });

    }
    public static AllCarsPage getInstance() {
        return instance;
    }


    public void get_insp_veh_list() {
        progress_bar.setVisibility(View.VISIBLE);
        pageno = 1;
        JSONObject params = new JSONObject();
        try {

            params.put("dealerId",SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search",search_veh.getText().toString());
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
            params.put("search",search_veh.getText().toString());
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
        progress_bar.setVisibility(View.VISIBLE);
        pageno = 1;
        JSONObject params = new JSONObject();
        try {
            params.put("dealerId", SPHelper.getSPData(AllCarsPage.this,SPHelper.dealerid,""));
            params.put("brandId",SPHelper.selected_brandid);
            params.put("listType","");
            params.put("search",search_veh.getText().toString());
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            params.put("isWithPack",SPHelper.is_with_pack);

            System.out.print(params);
            System.out.print("url"+ServiceURL.allcarurl);
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
            params.put("search",search_veh.getText().toString());
            params.put("pageNo",String.valueOf(pageno));
            params.put("isSold",SPHelper.is_sold);
            params.put("priceFrom",SPHelper.price_from);
            params.put("priceTo",SPHelper.price_to);
            params.put("kmFrom",SPHelper.kms_from);
            params.put("kmTo",SPHelper.kms_to);
            params.put("fuelId",SPHelper.fuel_id);
            params.put("transmissionId",SPHelper.trans_id);
            params.put("warrantyStatusId",SPHelper.selected_insp_status);
            params.put("isWithPack",SPHelper.is_with_pack);
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
                        Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        rl_portal_transperant.setVisibility(View.GONE);
                        rl_portal_menu.setVisibility(View.GONE);
                        get_insp_veh_list();

                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

            }
        });
    }
    public void post_marked_as_sold()
    {
        Pojo_mark_assold pojo_mark_assold=new Pojo_mark_assold(SPHelper.vehid,ed_name.getText().toString().trim(),
                ed_number.getText().toString().trim());
        Call<AppResponse> call =  apiInterface.mark_as_sold(pojo_mark_assold);
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        rl_portal_transperant.setVisibility(View.GONE);
                        rl_portal_menu.setVisibility(View.GONE);
                        get_insp_veh_list();

                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {

            }
        });
    }

    public  void get_updated_features()
    {
        featureArr = new ArrayList<>();
        for (int i = 0; i < pojo_module_listArrayList.size(); i++)
        {
//            if (pojo_module_listArrayList.get(i).isVisible())
//            {
               // String moduleId = pojo_module_listArrayList.get(i).getModule_id();
                for (int j = 0; j < adapter_features.pojo_part_listArrayList.size(); j++)
                {

                    if (adapter_features.pojo_part_listArrayList.get(j).isSelected())
                    {
                        Feature obj = new Feature();
                        obj.setModuleId(adapter_features.pojo_part_listArrayList.get(j).getModule_id());
                        obj.setPart_id(adapter_features.pojo_part_listArrayList.get(j).getPart_id());
                        obj.setIsPresent("Y");
                        featureArr.add(obj);
                    }else {
                        Feature obj = new Feature();
                        obj.setModuleId(adapter_features.pojo_part_listArrayList.get(j).getModule_id());
                        obj.setPart_id(adapter_features.pojo_part_listArrayList.get(j).getPart_id());
                        obj.setIsPresent("N");
                        featureArr.add(obj);
                    }
            //    }
            }
        }

        if(featureArr.isEmpty()){
            Toast.makeText(AllCarsPage.this, "Please select atleast one feature", Toast.LENGTH_SHORT).show();
        }else {
            post_add_features();
        }


    }
    public void post_add_features()
    {

        Pojo_post_feature pojo_post_feature = new Pojo_post_feature(SPHelper.vehid, featureArr);
        Call<AppResponse> call = apiInterface.post_features(pojo_post_feature);

        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null)
                {
                    if (response_code.equals("200"))
                    {
                        Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        rl_transperant.setVisibility(View.GONE);
                        rl_feature_pop_up.setVisibility(View.VISIBLE);
                        get_module_list();
                        featureArr=new ArrayList<>();
                        get_insp_veh_list();
                    }else {
                        Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();

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


    public void showPhotoDialog()
    {
        final Dialog dialog = new Dialog(AllCarsPage.this);
        dialog.setContentView(R.layout.image_upload_options_pop);
        TextView cancel = dialog.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView textView1 = dialog.findViewById(R.id.takefromgallery);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_gallery();
                dialog.cancel();
            }
        });

        TextView textView = dialog.findViewById(R.id.Recapture);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencamera();
                dialog.cancel();
            }
        });


        dialog.show();
    }

    public void opencamera(){
        mRequestPermissionHandler.requestPermission(AllCarsPage.this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess()
            {
                System.out.println("Succeed");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // pickCamera();
                    it_is = "c";
                    CallCamera();

            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });

    }

    public void open_gallery(){

        mRequestPermissionHandler.requestPermission(AllCarsPage.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, selectedObject, new RequestPermissionHandler.RequestPermissionListener()
        {
            @Override
            public void onSuccess() {
                it_is = "g";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimeTypes = {"image/*", "application/pdf"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,selectedObject);
            }

            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }

    public void CallCamera() {

        mRequestPermissionHandler.requestPermission(AllCarsPage.this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                open_Camera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    public void open_Camera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            cam_uri = FileProvider.getUriForFile(AllCarsPage.this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
            startActivityForResult(takePictureIntent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == selectedObject && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();
            // Handle the selected PDF file here
            String type = getContentResolver().getType(uri);

            if (type != null && type.startsWith("image/"))
            {
                // Handle the selected image file here


                SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
                String fineName = dateFormat.format(new Date());
                filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
                String OriginalFileName = null;
                try {
                    OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(AllCarsPage.this, uri, filename, new Pair<Integer, Integer>(800, 800), "/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                pojo_part_listArrayList.get(adapter_static_images.adapter_position).setTaken_img(uri);
                pojo_part_listArrayList.get(adapter_static_images.adapter_position).setFilename(OriginalFileName);
                if(final_ids.size()>0)
                {
                    for(int i=0;i<final_ids.size();i++)
                    {
                        if (pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id().equalsIgnoreCase(final_ids.get(i))) {
                            final_imgs.remove(i);
                            final_ids.remove(pojo_part_listArrayList.get(i).getPart_id());
                            break;
                        }else {
                        }
                    }
                }
                final_ids.add(pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id());
                //  iv_ins_copy.setImageURI(uri);
                upload_to_s3(uri);
            }

        }
        else if(resultCode==RESULT_OK&&it_is.equals("c"))
        {

            AllCarsPage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    cam_uri = FileProvider.getUriForFile(AllCarsPage.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    //setimageuri

                    if (!Connectivity.isNetworkConnected(AllCarsPage.this)) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AllCarsPage.this);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }

                    pojo_part_listArrayList.get(adapter_static_images.adapter_position).setTaken_img(cam_uri);
                    pojo_part_listArrayList.get(adapter_static_images.adapter_position).setFilename(OriginalFileName);
                    if(final_ids.size()>0)
                    {
                        for(int i=0;i<final_ids.size();i++)
                        {
                            if (pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id().equalsIgnoreCase(final_ids.get(i))) {
                                final_imgs.remove(i);
                                final_ids.remove(pojo_part_listArrayList.get(i).getPart_id());
                                break;
                            }else {
                            }
                        }
                    }
                    final_ids.add(pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id());

                    upload_to_s3(cam_uri);
                }
            });
        }
    }


    private void validate(){}
    public  void upload_to_s3(Uri imageUri){
        try {
            // idPBLoading.setVisibility(View.VISIBLE);
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(AllCarsPage.this)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key =  SPHelper.doc_name+"/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Toast.makeText(AllCarsPage.this,  SPHelper.doc_name+"\tuploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print("doc_url"+finalurl);
                        AllCarsPage.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress_bar.setVisibility(View.GONE);
                                // progressDialog.cancel();
                            }
                        });
                        adapter_static_images.notifyDataSetChanged();

                       // doc_url=finalurl;
                        final_imgs.add(finalurl);
                        //update_images();

                    } else if (TransferState.FAILED == state) {

                        AllCarsPage.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress_bar.setVisibility(View.GONE);
                                // progressDialog.cancel();
                            }
                        });
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    AllCarsPage.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress_bar.setVisibility(View.GONE);
                            // progressDialog.cancel();
                        }
                    });
                }

            });
        } catch (Exception je) {

            je.printStackTrace();
        }
    }

    public void get_updated_imgs(){
        pojo_imagearrayArrayList=new ArrayList<>();
        for (int i = 0; i <final_ids.size(); i++) {
            Pojo_imagearray imageobj=new Pojo_imagearray();
            imageobj.setImage(final_imgs.get(i));
            imageobj.setImage_type_id(final_ids.get(i));
            pojo_imagearrayArrayList.add(imageobj);
        }

        if(pojo_imagearrayArrayList.isEmpty()){
            Toast.makeText(AllCarsPage.this,
                    "Please upload atleast one image",
                    Toast.LENGTH_SHORT).show();
        }else {
            System.out.println("imgarray"+pojo_imagearrayArrayList);
           update_images();
        }
    }

    public void update_images() {
        {
            if (!Connectivity.isNetworkConnected(AllCarsPage.this)) {
                Toast.makeText(AllCarsPage.this,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progress_bar.setVisibility(View.VISIBLE);


                Pojo_Update_list pojo_update_list=new Pojo_Update_list(SPHelper.vehid,pojo_imagearrayArrayList);
                Call<AppResponse> call = apiInterface.update_image(pojo_update_list);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200")) {
                                progress_bar.setVisibility(View.GONE);
                                rl_transperant_add_image.setVisibility(View.INVISIBLE);
                                rl_add_image.setVisibility(View.INVISIBLE);
                                Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                get_insp_veh_list();
                                 final_imgs=new ArrayList<>();
                                 final_ids=new ArrayList<>();
                                 pojo_imagearrayArrayList=new ArrayList<>();
                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                                Toast.makeText(AllCarsPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(AllCarsPage.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(AllCarsPage.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    private void hideKeybaord() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

}
