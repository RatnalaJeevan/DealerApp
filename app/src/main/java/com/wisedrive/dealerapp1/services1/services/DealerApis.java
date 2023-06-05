package com.wisedrive.dealerapp1.services1.services;

import com.wisedrive.dealerapp1.pojos.PojoSwapVeh;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddNewCar;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyAddOn;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyOffer;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBuyPackage;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCFSession;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCustomerVehicleInfo;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDeviceDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoEditDealer;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehInspectEligible;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Update_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listin_portal;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_mark_assold;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_post_feature;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDealerDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoRequestInspectionDetails;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DealerApis {
    @GET("/send/otp")
    Call<AppResponse> sendotp(@Query("mobile_no") String mobile_no, @Query("is_for_login") String is_for_login);

    @GET("/verify/otp")
    Call<AppResponse> verifyotp(@Query("mobile_no") String mobile_no,@Query("otp") String otp);

    @POST("/update/devicedetails")
    Call<AppResponse> updateDeviceDetails(@Body PojoDeviceDetails pojoDeviceDetails);

    @GET("/getCredentials")
    Call<AppResponse> get_credentials();

    @GET("/getAppUpdateDetails")
    Call<AppResponse> getapp_update_deatails(@Query("appId") String appId,@Query("osType") String osType);

    @GET("/getpincodedetails")
    Call<AppResponse> get_PinCodeDetails(@Query("pincode") String pincode);


    @GET("/Insurance/getProviderList")
    Call<AppResponse> get_insurancelist();

    @GET("/getpincode")
    Call<AppResponse> get_pincode(@Query("pincode") String pincode);



    @GET("/dealer/warranty/infonew")
    Call<AppResponse> getwarrantyinfo(@Query("dealer_id") String dealer_id,
                                      @Query("search") String search);

    @GET("/DealerPackages/checkEligibility")
    Call<AppResponse> check_warranty_eligibility(@Query("dealerId") String dealerId,@Query("vehicleNo") String vehicleNo);


    @POST("/Inspection/checkeligibility")
    Call<AppResponse> check_veh_inspect_eligible(@Body PojoVehInspectEligible pojoVehInspectEligible);

    @POST("/Inspection/requestinspectionnew")
    Call<AppResponse> req_inspection(@Body PojoRequestInspectionDetails pojoreqinspection);


    @GET("/DealershipVehicle/getBrandList")
    Call<AppResponse> get_brandlist();

    @GET("/DealershipVehicle/getModelList")
    Call<AppResponse> get_modellist(@Query("brandId") String brandId);

    @GET("/DealershipVehicle/getimagelist")
    Call<AppResponse> get_imagelist();


    @GET("/DealershipDashboard/dashboardcountNew")
    Call<AppResponse> get_dashboardcount(@Query("dealerId") String dealerId,@Query("brandId") String brandId,
                                         @Query("listType") String listType,@Query("search") String search,
                                         @Query("isSoldCar") String isSoldCar,@Query("statusId") String statusId,
                                         @Query("isWithCooling") String isWithCooling,@Query("isWithPack") String isWithPack);


    @GET("/VehicleList/getInspectionDetails")
    Call<AppResponse> get_inspection_details(@Query("vehicleId") String vehicleId);

    @GET("/VehicleList/getVehicleImages")
    Call<AppResponse> get_vehicleImagelist(@Query("vehicleId") String vehicleId);


    @GET("/DealershipMenu/getweblinks")
    Call<AppResponse> get_web_links();

    @GET("/DealershipMenu/gethelpsupportdata")
    Call<AppResponse> get_help_support();



    @GET("/GetExpVehicleCount")
    Call<AppResponse> getExpVehCount(@Query("dealerId") String dealerId);


    @GET("/WarrantyStatusList")
    Call<AppResponse> getWarrantyStatusList();

    @GET("/GetPricekmDetails")
    Call<AppResponse> getPriceKmsDetails(@Query("dealerId") String dealerId);

    @GET("/DealerPackages/getPackageDetails")
    Call<AppResponse> getpackage_type(@Query("dealerId") String dealerId,@Query("type") String type);

    @GET("/DealerPackages/GetAddonList")
    Call<AppResponse> getAddOnList(@Query("dealerId") String dealerId);

    @GET("/DealerPackages/GetAddonCombList")
    Call<AppResponse> getAddOnComboList(@Query("dealerId") String dealerId,@Query("addonId") String addonId);

    @GET("/DealerPackages/GetDealerPurchasedPackList")
    Call<AppResponse> getDppList(@Query("dealerId") String dealerId,@Query("pageNo") String pageNo);

    @GET("/DealerPackages/checkPackEligibility")
    Call<AppResponse> checkPackEligibility(@Query("dealerId") String dealerId,@Query("vehicleId") String vehicleId,
                                           @Query("customerId") String customerId,@Query("packId") String packId,
                                           @Query("dppId") String dppId);

    @POST("/DealerPackages/buyPackage")
    Call<AppResponse> buy_package(@Body PojoBuyPackage pojoBuyPackage);


    @POST("/create/accountnew")
    Call<AppResponse> createaccount(@Body PojoDealerDetails pojoDealerDetails);

    @POST("/editDealer")
    Call<AppResponse> editDealer(@Body PojoEditDealer pojoEditDealer);

    @GET("/send/otp/edit")
    Call<AppResponse> edit_sendotp(@Query("mobile_no") String mobile_no, @Query("is_for_login") String is_for_login,
                                   @Query("dealerId") String dealerId);

    @GET("/getallOfferList")
    Call<AppResponse> customer_offerlist(@Query("dealerId") String dealerId,@Query("dppId") String dppId,
                                         @Query("categoryId") String categoryId,@Query("vehicleId") String vehicleId);

    @GET("/getOfferPresence")
    Call<AppResponse> getFtValidity(@Query("vehicleId") String vehicleId,@Query("dealerId") String dealerId,
                                    @Query("offerId") String offerId);

    @GET("/getOfferCategoryList")
    Call<AppResponse> getOfferCatList(@Query("dealerId") String dealerId,@Query("dppId") String dppId,
                                    @Query("categoryId") String categoryId);

    @GET("/getVehicleOfferList")
    Call<AppResponse> getVehOfferList(@Query("vehicleId") String vehicleId);

    @GET("/VehicleList/getVehiclePackDetails")
    Call<AppResponse> getVehPackDetails(@Query("vehicleId") String vehicleId);

    @POST("/purchase/offer")
    Call<AppResponse> purchaseOffer(@Body PojoBuyOffer pojoBuyOffer);

    //with warranty
    @POST("/sell/Package/to/customerNew")
    Call<AppResponse> sell_veh_tocustomer(@Body PojoCustomerVehicleInfo pojoCustomerVehicleInfo);

    @GET("/DealerPackages/checkAddonEligibility")
    Call<AppResponse> CheckAddOnEligibility(@Query("dealerId") String dealerId,
                                             @Query("vehicleNo") String vehicleNo,
                                             @Query("addonId") String addonId,
                                             @Query("mainpackId") String mainpackId,
                                             @Query("subpackId") String subpackId);

    @POST("/DealerPackages/buyAddon")
    Call<AppResponse> buyAddOn(@Body PojoBuyAddOn pojoBuyAddOn);


    @POST("/cashfree/generateOrder")
    Call<AppResponse> generate_sessionID(@Body PojoCFSession pojoCFSession);


    @GET("/DealershipMenu/getPaymentHistoryNew")
    Call<AppResponse> getPaymentHistory(@Query("dealerId") String dealerId,@Query("pageNo") String pageNo);

    @GET("/WarrantyVehicleList/getPartDetails")
    Call<AppResponse> part_list(@Query("vehicleId") String vehicleId,@Query("moduleId") String moduleId);


    @POST("/WarrantyVehicleList/updateImages")
    Call<AppResponse> update_image(@Body Pojo_Update_list pojo_update_list);

    @GET("/WarrantyVehicleList/getModuleist")
    Call<AppResponse> get_module_list(@Query("vehicleId") String vehicleId);

    @GET("/WarrantyVehicleList/getCount")
    Call<AppResponse> lead_count(@Query("vehicleId") String vehicleId);

    @POST("/WarrantyVehicleList/updateVehSoldStatus")
    Call<AppResponse> mark_as_sold(@Body Pojo_mark_assold pojo_mark_assold);

    @POST("/WarrantyVehicleList/updateVehListingStatus")
    Call<AppResponse> list_in_portal(@Body Pojo_listin_portal pojo_listin_portal);

    @POST("/WarrantyVehicleList/updateVehListingFeatures")
    Call<AppResponse> post_features(@Body Pojo_post_feature pojo_post_feature);


    @GET("/getdealerStatus")
    Call<AppResponse> dealer_status(@Query("dealerId") String dealerId);

    @GET("/WarrantyVehicleList/getLeadList")
    Call<AppResponse> get_leads_list(@Query("vehicleId") String vehicleId,@Query("pageNo") String pageNo);

    @GET("/VehicleList/getSearchVehicle")
    Call<AppResponse> get_searh_results(@Query("dealerId") String dealerId,@Query("search") String search);

    @GET("/WarrantyVehicleList/getRequirementList")
    Call<AppResponse> get_requirents_list(@Query("search") String search,@Query("make") String make,
                                         @Query("model") String model,@Query("kmFrom") String kmFrom,
                                         @Query("kmTo") String kmTo,@Query("yearFrom") String yearFrom,
                                         @Query("yearTo") String yearTo,@Query("ownerFrom") String ownerFrom,
                                          @Query("ownerTo") String ownerTo,@Query("fuelType") String fuelType,
                                          @Query("transmissionType") String transmissionType,@Query("color") String color,
                                          @Query("priceFrom") String priceFrom,@Query("priceTo") String priceTo,
                                          @Query("dealerId") String dealerId,@Query("pageNo") String pageNo);

    @GET("/WarrantyVehicleList/getListedVehList")
    Call<AppResponse> get_listed_vehList(@Query("dealerId") String dealerId,@Query("pageNo") String pageNo);

    @POST("/WarrantyVehicleList/addDescription")
    Call<AppResponse> add_desrpition(@Body PojoSwapVeh pojoadd_des);

    @POST("/WarrantyVehicleList/swapVehicle")
    Call<AppResponse> swap_veh(@Body PojoSwapVeh pojoSwapVeh);


    @GET("/WarrantyVehicleList/checkDataPresent")
    Call<AppResponse> get_checked_data(@Query("vehicleId") String vehicleId);


























//    @POST("/api/v2/cftoken/order")
//    Call<AppResponse> generate_cf_token(@Header("x-client-id") String cfid,@Header("x-client-secret") String cfsecret,
//                                        @Body PojoCFToken pojoCFToken);
}
