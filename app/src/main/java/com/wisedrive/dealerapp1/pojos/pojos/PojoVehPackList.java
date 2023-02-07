package com.wisedrive.dealerapp1.pojos.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoVehPackList {

     @SerializedName("package_name")
     String package_name;
     @SerializedName("package_activated_on")
     String package_activated_on;
     @SerializedName("activation_code")
     String activation_code;
     @SerializedName("package_type")
     String package_type;

     public String getPackage_type() {
          return package_type;
     }

     public String getPackage_name() {
          return package_name;
     }

     public String getPackage_activated_on() {
          return package_activated_on;
     }

     public String getActivation_code() {
          return activation_code;
     }
}
