package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jsibbold.zoomage.ZoomageView;
import com.wisedrive.dealerapp1.R;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterOfferModuleList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterOfferPurchasedList;
import com.wisedrive.dealerapp1.adapters.adapters.AdapterVehPackList;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehPackList;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SoldVehDetails extends BottomSheetDialogFragment
{
    public int act_no=1;
    public RelativeLayout rl_offer_details,rl_pur_details,rl_veh_docs,rl_cust_details,rl_cust,rl_docs,rl_purchase,rl_offers;
    TextView tv_cust,tv_docs,tv_purchase,tv_offers,tv_veh_details,veh_no,no_cool_days,
            cust_name,cust_no,cust_state,cust_city,cust_pincode,cust_adress,cust_location,act_date,
            activation_code,warrantytype,car_solddate;
    View v_offers,v_cust,v_docs,v_purchase;
    ImageView car_logo,iv_aadhar_front,iv_aadhar_back,iv_rc_front,iv_rc_back,iv_insurance_1,iv_pan_card
            ,iv_de_note,iv_sa_re;
    ZoomageView   doc_image;
    Activity activity;
    RecyclerView rv_offer_list;
    ArrayList<PojoOfferModuleList> offerModuleListArrayList;
    AdapterOfferPurchasedList adapterOfferPurchasedList;
    private DealerApis apiInterface;
    String adhr_frnt="";
    String adhr_back="";
    String rc_front="";
    String rc_back="";
    String insrnce_copy="";
    String de_note="";
    String sa_re="";
    RecyclerView rv_veh_packlist;
    AdapterVehPackList adapterVehPackList;
    ArrayList<PojoVehPackList> pojoVehPackLists;
    RelativeLayout rl_show_popup,rl_transparent;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sold_veh_details,
                container, false);
        activity=getActivity();
        init_params(v);

        get_insp_details();

        getOfferList();
        getVehPackDetails();
        rl_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=1;
                show_act_pages();
            }
        });
        rl_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=2;
                show_act_pages();
            }
        });
        rl_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=3;
                show_act_pages();
            }
        });
        rl_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                act_no=4;
                show_act_pages();
            }
        });

        iv_aadhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adhr_frnt==null||adhr_frnt.equals("")||adhr_frnt.equals("null"))
                {

                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(adhr_frnt).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });
        iv_aadhar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adhr_back==null||adhr_back.equals("")||adhr_back.equals("null"))
                {

                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(adhr_back).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });

        iv_rc_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rc_front==null||rc_front.equals("")||rc_front.equals("null"))
                {

                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(rc_front).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });
        iv_rc_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rc_back==null||rc_back.equals("")||rc_back.equals("null"))
                {

                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(rc_back).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });
        iv_insurance_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(insrnce_copy==null||insrnce_copy.equals("")||insrnce_copy.equals("null"))
                {

                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(insrnce_copy).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });

        iv_de_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(de_note==null||de_note.equals("")||de_note.equals("null"))
                {
                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(de_note).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });
        iv_sa_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sa_re==null||sa_re.equals("")||sa_re.equals("null"))
                {
                }else{
                    rl_show_popup.setVisibility(View.VISIBLE);
                    Glide.with(activity).load(sa_re).placeholder(R.drawable.icon_noimage).into(doc_image);
                }
            }
        });

        rl_transparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_show_popup.setVisibility(View.GONE);
            }
        });
        Glide.with(activity).load(SPHelper.brandlogo).placeholder(R.drawable.icon_noimage).into(car_logo);
        tv_veh_details.setText(SPHelper.model_name+"-"+SPHelper.fueltype+"-"+SPHelper.manufacture_year);
        veh_no.setText(SPHelper.vehno.toUpperCase());

        if((SPHelper.cool_kms==null||SPHelper.cool_kms.equals("null")||SPHelper.cool_kms.equals(""))&&
                (SPHelper.cool_no_ofdays==null||SPHelper.cool_no_ofdays.equals("null")||SPHelper.cool_no_ofdays.equals(""))){
            no_cool_days.setVisibility(View.GONE);
         }
        else if((SPHelper.cool_kms==null||SPHelper.cool_kms.equals("")||SPHelper.cool_kms.equals("null"))&&
                (!SPHelper.cool_no_ofdays.equals(""))){
            SPHelper.cool_kms="";
            no_cool_days.setVisibility(View.VISIBLE);
            no_cool_days.setText(SPHelper.cool_no_ofdays+"\t days "+SPHelper.cool_kms+"\t kms Cooling Period");
        }else if((SPHelper.cool_no_ofdays==null||SPHelper.cool_no_ofdays.equals("")||SPHelper.cool_no_ofdays.equals("null"))&&
                (!SPHelper.cool_kms.equals(""))
                ){
            no_cool_days.setVisibility(View.VISIBLE);
            SPHelper.cool_no_ofdays="";
            no_cool_days.setText(SPHelper.cool_no_ofdays+"\t days "+SPHelper.cool_kms+"\t kms Cooling Period");
        }
       else{
            no_cool_days.setVisibility(View.VISIBLE);
            no_cool_days.setText(SPHelper.cool_no_ofdays+" "+"days\t"+"&\t"+SPHelper.cool_kms+"\t kms Cooling Period");
        }

        return v;
    }

    private void init_params(View v) {
        rv_veh_packlist=v.findViewById(R.id.rv_veh_packlist);
        rl_show_popup=v.findViewById(R.id.rl_show_popup);
        rl_transparent=v.findViewById(R.id.rl_transparent);
        doc_image=v.findViewById(R.id.doc_image);
        rv_offer_list=v.findViewById(R.id.rv_offer_list);
        iv_de_note=v.findViewById(R.id.iv_de_note);
        iv_sa_re=v.findViewById(R.id.iv_sa_re);
        iv_pan_card=v.findViewById(R.id.iv_pan_card);
        rv_offer_list=v.findViewById(R.id.rv_offer_list);
        act_date=v.findViewById(R.id.act_date);
        activation_code=v.findViewById(R.id.activation_code);
        warrantytype=v.findViewById(R.id.warrantytype);
        car_solddate=v.findViewById(R.id.car_solddate);
        iv_aadhar_front=v.findViewById(R.id.iv_aadhar_front);
        iv_aadhar_back=v.findViewById(R.id.iv_aadhar_back);
        iv_rc_front=v.findViewById(R.id.iv_rc_front);
        iv_rc_back=v.findViewById(R.id.iv_rc_back);
        iv_insurance_1=v.findViewById(R.id.iv_insurance_1);
        cust_state=v.findViewById(R.id.cust_state);
        cust_city=v.findViewById(R.id.cust_city);
        cust_pincode=v.findViewById(R.id.cust_pincode);
        cust_adress=v.findViewById(R.id.cust_adress);
        cust_location=v.findViewById(R.id.cust_location);
        cust_no=v.findViewById(R.id.cust_no);
        cust_name=v.findViewById(R.id.cust_name);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        no_cool_days=v.findViewById(R.id.no_cool_days);
        veh_no=v.findViewById(R.id.veh_no);
        tv_veh_details=v.findViewById(R.id.tv_veh_details);
        car_logo=v.findViewById(R.id.car_logo);
        rl_offer_details=v.findViewById(R.id.rl_offer_details);
        rl_pur_details=v.findViewById(R.id.rl_pur_details);
        rl_veh_docs=v.findViewById(R.id.rl_veh_docs);
        rl_cust_details=v.findViewById(R.id.rl_cust_details);
        tv_cust=v.findViewById(R.id.tv_cust);
        tv_docs=v.findViewById(R.id.tv_docs);
        tv_purchase=v.findViewById(R.id.tv_purchase);
        tv_offers=v.findViewById(R.id.tv_offers);
        v_cust=v.findViewById(R.id.v_cust);
        v_docs=v.findViewById(R.id.v_docs);
        v_purchase=v.findViewById(R.id.v_purchase);
        v_offers=v.findViewById(R.id.v_offers);
        rl_cust=v.findViewById(R.id.rl_cust);
        rl_docs=v.findViewById(R.id.rl_docs);
        rl_purchase=v.findViewById(R.id.rl_purchase);
        rl_offers=v.findViewById(R.id.rl_offers);
    }

    public  void show_act_pages()
    {
        if(act_no==1){
            rl_offer_details.setVisibility(View.GONE);
            rl_pur_details.setVisibility(View.GONE);
            rl_veh_docs.setVisibility(View.GONE);
            rl_cust_details.setVisibility(View.VISIBLE);
            tv_cust.setTextColor(Color.parseColor("#FF000000"));
            tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
            tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
            tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
            v_cust.setVisibility(View.VISIBLE);
            v_offers.setVisibility(View.GONE);
            v_docs.setVisibility(View.GONE);
            v_purchase.setVisibility(View.GONE);

        }else if(act_no==2){
            rl_offer_details.setVisibility(View.GONE);
            rl_pur_details.setVisibility(View.GONE);
            rl_veh_docs.setVisibility(View.VISIBLE);
            rl_cust_details.setVisibility(View.GONE);
            tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
            tv_docs.setTextColor(Color.parseColor("#FF000000"));
            tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
            tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
            v_cust.setVisibility(View.GONE);
            v_offers.setVisibility(View.GONE);
            v_docs.setVisibility(View.VISIBLE);
            v_purchase.setVisibility(View.GONE);
        }else if(act_no==3){
            rl_offer_details.setVisibility(View.GONE);
            rl_pur_details.setVisibility(View.VISIBLE);
            rl_veh_docs.setVisibility(View.GONE);
            rl_cust_details.setVisibility(View.GONE);
            tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
            tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
            tv_purchase.setTextColor(Color.parseColor("#FF000000"));
            tv_offers.setTextColor(Color.parseColor("#D3D3D3"));
            v_cust.setVisibility(View.GONE);
            v_offers.setVisibility(View.GONE);
            v_docs.setVisibility(View.GONE);
            v_purchase.setVisibility(View.VISIBLE);
        }else if(act_no==4){
            rl_offer_details.setVisibility(View.VISIBLE);
            rl_pur_details.setVisibility(View.GONE);
            rl_veh_docs.setVisibility(View.GONE);
            rl_cust_details.setVisibility(View.GONE);
            tv_cust.setTextColor(Color.parseColor("#D3D3D3"));
            tv_docs.setTextColor(Color.parseColor("#D3D3D3"));
            tv_purchase.setTextColor(Color.parseColor("#D3D3D3"));
            tv_offers.setTextColor(Color.parseColor("#FF000000"));
            v_cust.setVisibility(View.GONE);
            v_offers.setVisibility(View.VISIBLE);
            v_docs.setVisibility(View.GONE);
            v_purchase.setVisibility(View.GONE);
        }


    }

    public void get_insp_details()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            //progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.get_inspection_details(SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
//                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        //progress_bar.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {
                           // progress_bar.setVisibility(View.GONE);
                            String est_cost=appResponse.getResponse().getInspectionDetails().getEstimated_cost();
                            SPHelper.inspectionreport=appResponse.getResponse().getInspectionDetails().getInspection_report_image();
                            //fast tag validity
                            String  status=appResponse.getResponse().getWarrantyDetails().getFastag_status();
                            String customer_id=appResponse.getResponse().getWarrantyDetails().getCustomer_id();
                            String panimg = appResponse.getResponse().getWarrantyDetails().getPancard_image();
                            String ftimg = appResponse.getResponse().getWarrantyDetails().getFastag_iamge();
                            String ft_no = appResponse.getResponse().getWarrantyDetails().getFastag_number();
                            String ft_available = appResponse.getResponse().getWarrantyDetails().getFastag_availability();
                            String ft_validity = appResponse.getResponse().getWarrantyDetails().getFastag_validity();

                            String customer_name=appResponse.getResponse().getWarrantyDetails().getName();
                            String customer_no=appResponse.getResponse().getWarrantyDetails().getNumber();
                            String location=appResponse.getResponse().getWarrantyDetails().getLocation();
                            String adress=appResponse.getResponse().getWarrantyDetails().getCustomer_full_address();
                            String pincode=appResponse.getResponse().getWarrantyDetails().getPincode();
                            String city=appResponse.getResponse().getWarrantyDetails().getCity();
                            String state=appResponse.getResponse().getWarrantyDetails().getState();
                             de_note=appResponse.getResponse().getWarrantyDetails().getDelivery_note();
                             sa_re=appResponse.getResponse().getWarrantyDetails().getSales_receipt();
                            String act_code=appResponse.getResponse().getWarrantyDetails().getPackage_activation_code();
                            String warranty_type=appResponse.getResponse().getWarrantyDetails().getWarranty_type();
                            String solddate=appResponse.getResponse().getWarrantyDetails().getSold_date();
                            String package_act_date=appResponse.getResponse().getWarrantyDetails().getPackage_activated_on();

                            //ft details
//                            if ((ftimg == null && panimg == null && ft_no == null))
//                            {
//                                if(ft_available==null||ft_validity==null){
//                                }else
//                                {
//                                    if (ft_available.equalsIgnoreCase("n") || ft_validity.equalsIgnoreCase("n")
//                                    ) {
//                                       // req_for_fasttag.setVisibility(View.GONE);
//                                    } else if (ft_available.equalsIgnoreCase("y") && ft_validity.equalsIgnoreCase("y")) {
//                                       // req_for_fasttag.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            }
//                            else
//                            {
//
//                                Glide.with(activity).load(panimg).placeholder(R.drawable.icon_noimage).into(iv_pan_card);
//                                Glide.with(activity).load(ftimg).placeholder(R.drawable.icon_noimage).into(ft_img);
//                                fastag_no.setText(ft_no);
//                            }

                            //warranty deatils


                                act_date.setText(Common.getDateFromString(package_act_date));
                            if(act_code==null){
                                activation_code.setText("");
                            }else{
                                activation_code.setText(act_code.toUpperCase());
                            }
                            if(warranty_type==null){
                                warrantytype.setText("");
                            }else{
                                warrantytype.setText(warranty_type.toUpperCase());
                            }

                                car_solddate.setText(Common.getDateFromString(solddate));
                                //customer details

                                    cust_name.setText(customer_name);
                                    cust_no.setText(customer_no);
                                    cust_location.setText(location);
                                    cust_adress.setText(adress);
                                    cust_pincode.setText(pincode);
                                    cust_city.setText(city);
                                    cust_state.setText(state);
                                    //location adress state city pinncode ?

                            //get document details
                             adhr_frnt=appResponse.getResponse().getDocumentDetails().getAadhaar_front();
                             adhr_back=appResponse.getResponse().getDocumentDetails().getAadhaar_rear();
                             rc_front=appResponse.getResponse().getDocumentDetails().getRc_front();
                             rc_back=appResponse.getResponse().getDocumentDetails().getRc_rear();
                             insrnce_copy=appResponse.getResponse().getDocumentDetails().getInsurance_copy();

                            //need sales reeipt and delivery note
                            if(adhr_frnt==null||adhr_frnt.equals("")||adhr_frnt.equals("null"))
                            {
                                SPHelper.af_doc_image="";
                            }else{
                                SPHelper.af_doc_image=adhr_frnt;
                                Glide.with(activity).load(adhr_frnt).placeholder(R.drawable.icon_noimage).into(iv_aadhar_front);
                            }
                            if(adhr_back==null||adhr_back.equals("")||adhr_back.equals("null"))
                            {
                                SPHelper.ab_doc_image="";
                            }else{
                                SPHelper.ab_doc_image=adhr_back;
                                Glide.with(activity).load(adhr_back).placeholder(R.drawable.icon_noimage).into(iv_aadhar_back);
                            }
                            if(rc_front==null||rc_front.equals("")||rc_front.equals("null"))
                            {
                                SPHelper.rf_doc_image="";
                            }else{
                                SPHelper.rf_doc_image=rc_front;
                                Glide.with(activity).load(rc_front).placeholder(R.drawable.icon_noimage).into(iv_rc_front);
                            }
                            if(rc_back==null||rc_back.equals("")||rc_back.equals("null"))
                            {
                                SPHelper.rb_doc_image="";
                            }else{
                                SPHelper.rb_doc_image=rc_back;
                                Glide.with(activity).load(rc_back).placeholder(R.drawable.icon_noimage).into(iv_rc_back);
                            }

                            if(de_note==null||de_note.equals("")||de_note.equals("null"))
                            {
                            }else{
                                Glide.with(activity).load(de_note).placeholder(R.drawable.icon_noimage).into(iv_de_note);
                            }
                            if(sa_re==null||sa_re.equals("")||sa_re.equals("null"))
                            {
                            }else{
                                Glide.with(activity).load(sa_re).placeholder(R.drawable.icon_noimage).into(iv_sa_re);
                            }
                            if(insrnce_copy==null||insrnce_copy.equals("")||insrnce_copy.equals("null"))
                            {
                                SPHelper.ins_doc_image="";
                            }else{
                                SPHelper.ins_doc_image=insrnce_copy;
                                Glide.with(activity).load(insrnce_copy).placeholder(R.drawable.icon_noimage).into(iv_insurance_1);
                            }

                        } else if (response_code.equals("300"))
                        {
                           // progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        //progress_bar.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    //progress_bar.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getOfferList()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
           // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getVehOfferList(SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    System.out.println("responsecode"+SPHelper.getSPData(activity,SPHelper.dealerid,"")+
                            SPHelper.dpp_id
                            +SPHelper.category_id);
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                       // idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {

                           // idPBLoading.setVisibility(View.GONE);
                            offerModuleListArrayList=new ArrayList<>();
                            offerModuleListArrayList=appResponse.getResponse().getVehicleOfferList();
                            if(offerModuleListArrayList.isEmpty()){

                            }else{
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                                adapterOfferPurchasedList=new AdapterOfferPurchasedList(offerModuleListArrayList,activity);
                                rv_offer_list.setLayoutManager(linearLayoutManager);
                                rv_offer_list.setAdapter(adapterOfferPurchasedList);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterOfferPurchasedList.notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                        else if (response_code.equals("300"))
                        {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        //idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    //idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }

    public void getVehPackDetails()
    {
        if(!Connectivity.isNetworkConnected(activity))
        {
            Toast.makeText(activity,
                    "Plaese Check Your Internet",
                    Toast.LENGTH_LONG).show();
        }else
        {
            // idPBLoading.setVisibility(View.VISIBLE);
            Call<AppResponse> call =  apiInterface.getVehPackDetails(SPHelper.vehid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();

                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        // idPBLoading.setVisibility(View.GONE);
                        if (response_code.equals("200"))
                        {

                            // idPBLoading.setVisibility(View.GONE);
                            pojoVehPackLists=new ArrayList<>();
                            pojoVehPackLists=appResponse.getResponse().getVehiclePackageDetails();
                            if(pojoVehPackLists.isEmpty()){

                            }else{
                                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
                                adapterVehPackList=new AdapterVehPackList(pojoVehPackLists,activity);
                                rv_veh_packlist.setLayoutManager(linearLayoutManager);
                                rv_veh_packlist.setAdapter(adapterVehPackList);
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterVehPackList.notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                        else if (response_code.equals("300"))
                        {
                            //idPBLoading.setVisibility(View.GONE);
                            Toast.makeText(activity, appResponse.getResponse().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        //idPBLoading.setVisibility(View.GONE);
                        Toast.makeText(activity, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(activity,
                            t.getMessage(),
                            Toast.LENGTH_LONG).show();
                    //idPBLoading.setVisibility(View.GONE);
                }
            });
        }
    }
}
