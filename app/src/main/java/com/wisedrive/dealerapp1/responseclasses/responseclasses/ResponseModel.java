package com.wisedrive.dealerapp1.responseclasses.responseclasses;

import com.google.gson.annotations.SerializedName;
import com.wisedrive.dealerapp1.pojos.PojoSearchResults;
import com.wisedrive.dealerapp1.pojos.pojos.PojOurServices;
import com.wisedrive.dealerapp1.pojos.pojos.PojoActVehlist;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnComboList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAddOnLists;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarsList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllPayments;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAppUpdateDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoBankList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCFSession;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCredentials;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDocumentDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoExpVehList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoInspectionEligibleStatus;
import com.wisedrive.dealerapp1.pojos.pojos.PojoMainPackLists;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferCount;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoOfferModuleList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarBrands;
import com.wisedrive.dealerapp1.pojos.pojos.PojoAllCarModels;
import com.wisedrive.dealerapp1.pojos.pojos.PojoCarImageList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDasboardCount;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDealerWarrantyInfo;
import com.wisedrive.dealerapp1.pojos.pojos.PojoHelpSupportDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoInspectionDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoPincodeDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoResponseDealerDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSample;
import com.wisedrive.dealerapp1.pojos.pojos.PojoSelectPack;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehPackList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleImageList;
import com.wisedrive.dealerapp1.pojos.pojos.PojoVehicleWEInfo;
import com.wisedrive.dealerapp1.pojos.pojos.PojoWarrantyDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoWebLinks;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Dealer_status;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Module_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_New_req;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_lead_count;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_leads_page;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_listed_vehicle_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;

import java.util.ArrayList;

public class ResponseModel {
    //response_msg
    @SerializedName("message")
    String message;
    @SerializedName("uid")
    String uid;

    @SerializedName("vehexist")
    String vehexist;
    @SerializedName("inspecexist")
    String inspecexist;
    @SerializedName("packexist")
    String packexist;

    public String getVehexist() {
        return vehexist;
    }

    public String getInspecexist() {
        return inspecexist;
    }

    public String getPackexist() {
        return packexist;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }
    @SerializedName("showReqinspection")
    String showReqinspection;

    public String getShowReqinspection() {
        return showReqinspection;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    //get insurance provider
    @SerializedName("providerList")
    ArrayList<PojoBankList> providerList;

    public ArrayList<PojoBankList> getProviderList() {
        return providerList;
    }

    public void setProviderList(ArrayList<PojoBankList> providerList) {
        this.providerList = providerList;
    }


    //dealerid
    @SerializedName("dealerInfo")
    PojoResponseDealerDetails dealerInfo;
    @SerializedName("credentials")
    PojoCredentials credentials;
    public PojoResponseDealerDetails getDealerInfo() {
        return dealerInfo;
    }


    public PojoCredentials getCredentials() {
        return credentials;
    }

    //get app update details
    @SerializedName("appUpdateDetails")
    PojoAppUpdateDetails appUpdateDetails;

    public PojoAppUpdateDetails getAppUpdateDetails() {
        return appUpdateDetails;
    }


//getpincodedetails

    @SerializedName("getpincodedata")
    PojoPincodeDetails getpincodedata;
    @SerializedName("getpincodedetails")
    PojoPincodeDetails getpincodedetails;

    public PojoPincodeDetails getGetpincodedetails() {
        return getpincodedetails;
    }


    public PojoPincodeDetails getGetpincodedata() {
        return getpincodedata;
    }


    //getwarrantyinfo

    @SerializedName("dealerWarrantyInfo")
    PojoDealerWarrantyInfo dealerWarrantyInfo;

    @SerializedName("liveCarCount")
    PojoDealerWarrantyInfo liveCarCount;
    public PojoDealerWarrantyInfo getDealerWarrantyInfo() {
        return dealerWarrantyInfo;
    }

    public PojoDealerWarrantyInfo getLiveCarCount() {
        return liveCarCount;
    }

    //getvehiclewarranty eligible info
    @SerializedName("vehicledata")
    PojoVehicleWEInfo vehicledata;

    public PojoVehicleWEInfo getVehicleWEInfo() {
        return vehicledata;
    }


    //getactivationcode
    @SerializedName("vehicleObj")
    PojoVehicleWEInfo vehicleObj;

    public PojoVehicleWEInfo getVehicleObj() {
        return vehicleObj;
    }

    //getbrandlist
    @SerializedName("brandList")
    ArrayList<PojoAllCarBrands> brandList;
    //getmodellist
    @SerializedName("modelList")
    ArrayList<PojoAllCarModels> modelList;
    //getimagelist
    @SerializedName("getvehicleimages")
    ArrayList<PojoCarImageList> getvehicleimages;

    public ArrayList<PojoCarImageList> getGetvehicleimages() {
        return getvehicleimages;
    }
    public ArrayList<PojoAllCarBrands> getBrandList() {
        return brandList;
    }

    public void setBrandList(ArrayList<PojoAllCarBrands> brandList) {
        this.brandList = brandList;
    }

    public ArrayList<PojoAllCarModels> getModelList() {
        return modelList;
    }


    //allbrandlogocount && allcarlist
    @SerializedName("getbrandwisevehicleList")
    ArrayList<PojoAllCarBrands> getbrandwisevehicleList;
    @SerializedName("InspectedVehicleBrandList")
    ArrayList<PojoAllCarBrands> InspectedVehicleBrandList;

    public ArrayList<PojoAllCarBrands> getInspectedVehicleBrandList() {
        return InspectedVehicleBrandList;
    }

    public ArrayList<PojoAllCarBrands> getGetbrandwisevehicleList() {
        return getbrandwisevehicleList;
    }
    //getdashboardcount
    @SerializedName("dashboardCount")
    PojoDasboardCount dashboardCount;
    @SerializedName("inspectedVehCount")
    PojoDasboardCount inspectedVehCount;
    @SerializedName("repair")
    PojoDasboardCount repair;
    @SerializedName("activatedvehcount")
    PojoDasboardCount activatedvehcount;
    @SerializedName("approvedwithoutCooling")
    PojoDasboardCount approvedwithoutCooling;
    @SerializedName("approvedwithCooling")
    PojoDasboardCount approvedwithCooling;
    @SerializedName("reinspect")
    PojoDasboardCount reinspect;
    @SerializedName("expired")
    PojoDasboardCount expired;
    @SerializedName("withoutwarranty")
    PojoDasboardCount withoutwarranty;

    @SerializedName("allCarCount")
    PojoDasboardCount allCarCount;
    @SerializedName("reqInspection")
    PojoDasboardCount reqInspection;

    public PojoDasboardCount getReqInspection() {
        return reqInspection;
    }

    public PojoDasboardCount getAllCarCount() {
        return allCarCount;
    }

    public PojoDasboardCount getWithoutwarranty() {
        return withoutwarranty;
    }

    public PojoDasboardCount getExpired() {
        return expired;
    }

    public PojoDasboardCount getInspectedVehCount() {
        return inspectedVehCount;
    }
    public PojoDasboardCount getRepair() {
        return repair;
    }
    public PojoDasboardCount getActivatedvehcount() {
        return activatedvehcount;
    }
    public PojoDasboardCount getApprovedwithoutCooling() {
        return approvedwithoutCooling;
    }
    public PojoDasboardCount getApprovedwithCooling() {
        return approvedwithCooling;
    }
    public PojoDasboardCount getReinspect() {
        return reinspect;
    }
    public PojoDasboardCount getDashboardCount() {
        return dashboardCount;
    }

    //getinspectiondetails
    @SerializedName("InspectionDetails")
    PojoInspectionDetails InspectionDetails;
    //get warranty details
    @SerializedName("WarrantyDetails")
    PojoWarrantyDetails WarrantyDetails;
    @SerializedName("DocumentDetails")
    PojoDocumentDetails DocumentDetails;

    public PojoDocumentDetails getDocumentDetails() {
        return DocumentDetails;
    }

    public PojoWarrantyDetails getWarrantyDetails() {
        return WarrantyDetails;
    }

    public PojoInspectionDetails getInspectionDetails() {
        return InspectionDetails;
    }

    //get web links
    @SerializedName("getweblinks")
    PojoWebLinks getweblinks;

    public PojoWebLinks getGetweblinks() {
        return getweblinks;
    }


    //gethelpsupport
    @SerializedName("getsupportdata")
    PojoHelpSupportDetails getsupportdata;

    public PojoHelpSupportDetails getGetsupportdata() {
        return getsupportdata;
    }


    //getinspectioneligility status
    @SerializedName("InspectionEligibility")
    PojoInspectionEligibleStatus InspectionEligibility;

    public PojoInspectionEligibleStatus getInspectionEligibility() {
        return InspectionEligibility;
    }

    //getvehicle image list
    @SerializedName("vehicleImageList")
    ArrayList<PojoVehicleImageList> vehicleImageList;

    public ArrayList<PojoVehicleImageList> getVehicleImageList() {
        return vehicleImageList;
    }




    //get offer details
    @SerializedName("OfferList")
    ArrayList<PojoOfferDetails> OfferList;

    public ArrayList<PojoOfferDetails> getOfferList() {
        return OfferList;
    }


    //expiring vehlist
    @SerializedName("ExpVehicleCount")
    ArrayList<PojoExpVehList> ExpVehicleCount;

    public ArrayList<PojoExpVehList> getExpVehicleCount() {
        return ExpVehicleCount;
    }

    @SerializedName("WarrantyStatusList")
    ArrayList<PojoSample> WarrantyStatusList;

    public ArrayList<PojoSample> getWarrantyStatusList() {
        return WarrantyStatusList;
    }

    @SerializedName("DealerBrandList")
    ArrayList<PojoAllCarBrands> DealerBrandList;

    public ArrayList<PojoAllCarBrands> getDealerBrandList() {
        return DealerBrandList;
    }


    @SerializedName("PriceKmDetails")
    PojoAllCarBrands PriceKmDetails;

    public PojoAllCarBrands getPriceKmDetails() {
        return PriceKmDetails;
    }

    @SerializedName("DealerPackageList")
    ArrayList<PojoMainPackLists> DealerPackageList;

    public ArrayList<PojoMainPackLists> getDealerPackageList() {
        return DealerPackageList;
    }

    @SerializedName("addonList")
    ArrayList<PojoAddOnLists> addonList;

    public ArrayList<PojoAddOnLists> getAddonList() {
        return addonList;
    }

    @SerializedName("addonCombList")
    ArrayList<PojoAddOnComboList> addonCombList;

    public ArrayList<PojoAddOnComboList> getAddonCombList() {
        return addonCombList;
    }

    @SerializedName("PurchasedPackList")
    ArrayList<PojoSelectPack> PurchasedPackList;

    public ArrayList<PojoSelectPack> getPurchasedPackList() {
        return PurchasedPackList;
    }

    @SerializedName("packeligibility")
    PojoSelectPack packeligibility;

    public PojoSelectPack getPackeligibility() {
        return packeligibility;
    }

    @SerializedName("AllOfferList")
    ArrayList<PojoOfferModuleList> AllOfferList;

    public ArrayList<PojoOfferModuleList> getAllOfferList() {
        return AllOfferList;
    }

    @SerializedName("VehicleOfferList")
    ArrayList<PojoOfferModuleList> VehicleOfferList;

    public ArrayList<PojoOfferModuleList> getVehicleOfferList() {
        return VehicleOfferList;
    }

    @SerializedName("paymentHistory")
    ArrayList<PojoAllPayments> paymentHistory;

    public ArrayList<PojoAllPayments> getPaymentHistory() {
        return paymentHistory;
    }

    @SerializedName("vehiclePackageDetails")
    ArrayList<PojoVehPackList> vehiclePackageDetails;

    public ArrayList<PojoVehPackList> getVehiclePackageDetails() {
        return vehiclePackageDetails;
    }

    @SerializedName("FastagValidity")
    PojoOfferModuleList FastagValidity;

    @SerializedName("OfferPresence")
    PojoOfferModuleList OfferPresence;

    public PojoOfferModuleList getOfferPresence() {
        return OfferPresence;
    }

    public PojoOfferModuleList getFastagValidity() {
        return FastagValidity;
    }

    @SerializedName("offerPaymentObj")
    PojoOfferModuleList offerPaymentObj;

    public PojoOfferModuleList getOfferPaymentObj() {
        return offerPaymentObj;
    }

    @SerializedName("addoneligibility")
    PojoAddOnComboList addoneligibility;

    public PojoAddOnComboList getAddoneligibility() {
        return addoneligibility;
    }

    @SerializedName("CashfreeorderData")
    PojoCFSession CashfreeorderData;

    public PojoCFSession getOrderData() {
        return CashfreeorderData;
    }

    @SerializedName("cashbackCount")
    PojoOfferCount cashbackCount;
    @SerializedName("GeneralCount")
    PojoOfferCount GeneralCount;
    @SerializedName("addonCount")
    PojoOfferCount addonCount;

    public PojoOfferCount getCashbackCount() {
        return cashbackCount;
    }

    public PojoOfferCount getGeneralCount() {
        return GeneralCount;
    }

    public PojoOfferCount getAddonCount() {
        return addonCount;
    }

    @SerializedName("activatedVehicleList")
    ArrayList<PojoActVehlist> activatedVehicleList;

    @SerializedName("searchResultList")
    ArrayList<PojoSearchResults> searchResultList;

    public ArrayList<PojoSearchResults> getSearchResultList() {
        return searchResultList;
    }

    public ArrayList<PojoActVehlist> getActivatedVehicleList() {
        return activatedVehicleList;
    }

    @SerializedName("activegateway")
    PojOurServices activegateway;

    @SerializedName("PartDetails")
    private ArrayList <Pojo_part_list>PartDetails;

    public ArrayList<Pojo_part_list> getPartDetails() {
        return PartDetails;
    }

    public void setPartDetails(ArrayList<Pojo_part_list> partDetails) {
        PartDetails = partDetails;
    }

    @SerializedName("ModuleList")
    private ArrayList <Pojo_Module_list>ModuleList;

    public ArrayList<Pojo_Module_list> getModuleList() {
        return ModuleList;
    }

    public void setModuleList(ArrayList<Pojo_Module_list> moduleList) {
        ModuleList = moduleList;
    }

    @SerializedName("ViewCount")
    private  PojoAllCarsList ViewCount;

    public PojoAllCarsList getViewCount() {
        return ViewCount;
    }

    public void setViewCount(PojoAllCarsList viewCount) {
        ViewCount = viewCount;
    }

    @SerializedName("LeadCount")
    private PojoAllCarsList LeadCount;

    public PojoAllCarsList getLeadCount() {
        return LeadCount;
    }

    public void setLeadCount(PojoAllCarsList leadCount) {
        LeadCount = leadCount;
    }

    @SerializedName("dealerStatus")
    private Pojo_Dealer_status dealerStatus;

    @SerializedName("LeadList")
    ArrayList<Pojo_leads_page> LeadList;

    @SerializedName("RequirementList")
    ArrayList<Pojo_New_req> RequirementList;

    @SerializedName("ListedVehList")
    ArrayList<Pojo_listed_vehicle_list> ListedVehList;

    public ArrayList<Pojo_listed_vehicle_list> getListedVehList() {
        return ListedVehList;
    }

    public ArrayList<Pojo_New_req> getRequirementList() {
        return RequirementList;
    }

    public ArrayList<Pojo_leads_page> getLeadList() {
        return LeadList;
    }

    public Pojo_Dealer_status getDealerStatus() {
        return dealerStatus;
    }

    public void setDealerStatus(Pojo_Dealer_status dealerStatus) {
        this.dealerStatus = dealerStatus;
    }

    public PojOurServices getActivegateway() {
        return activegateway;
    }


    @SerializedName("DataPresent")
    VehicleData DataPresent;

    public VehicleData getDataPresent() {
        return DataPresent;
    }

    @SerializedName("description")
    VehicleData description;

    public VehicleData getDescription() {
        return description;
    }

    public class VehicleData{
        String is_image_present;
        String is_feature_present;
        String description;

        public String getDescription() {
            return description;
        }

        public String getIs_image_present() {
            return is_image_present;
        }

        public String getIs_feature_present() {
            return is_feature_present;
        }
    }


}
