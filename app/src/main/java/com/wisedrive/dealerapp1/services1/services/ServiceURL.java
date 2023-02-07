    package com.wisedrive.dealerapp1.services1.services;
    public class ServiceURL
    {
        //live
       // public final static String BASEURL = "https://dealerappapis.wisedrive.in/";

         //test
        public final static String BASEURL = "http://164.52.217.96:30025/";
        public final static String allcarurl = "VehicleList/getAllVehicleListNew";
        public final static String carsforsaleurl = "VehicleList/getPublicVehicleList";
        public final static String inspectionHistory = "DealershipMenu/getInspectionHistoryNew";
        public final static String activationHistory = "DealershipMenu/getActivationHistoryNew";
        public static String paymentHistory="DealershipMenu/getPaymentHistoryNew";
        public static String serviceHistory="DealershipMenu/getServiceHistory";
        public static String addcarurl="DealershipVehicle/addCarNew";
        public static String editcarurl="DealershipVehicle/editCarNew";
        public static String insp_veh_list_url="VehicleList/getAllVehicleWithInspectionStatus";
        public static String exp_vehList="GetExpVehicleList";

    }