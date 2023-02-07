package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

//import com.payu.base.models.ErrorResponse;
//import com.payu.base.models.PayUPaymentParams;
//import com.payu.checkoutpro.PayUCheckoutPro;
//import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
//import com.payu.ui.model.listeners.PayUCheckoutProListener;
//import com.payu.ui.model.listeners.PayUHashGenerationListener;

//
import com.wisedrive.dealerapp1.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class PayuCheckOutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payu_check_out_page);
      // get_SHA_512_SecurePassword("")

//            PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
//            builder.setAmount("1")
//                    .setIsProduction(true)
//                    .setProductInfo("Test")
//                    .setKey("eHSpiw")
//                    .setPhone("9000000000")
//                    .setTransactionId(String.valueOf(System.currentTimeMillis()))
//                    .setFirstName("Rat")
//                    .setEmail("jeevan.ratnala@wisedrive.in");
//            //.setUserCredential("");
//            //.setAdditionalParams(<HashMap<String,Object>>); //Optional, can contain any additional PG params
//            PayUPaymentParams payUPaymentParams = builder.build();
//
//            PayUCheckoutPro.open(this, payUPaymentParams,
//                    new PayUCheckoutProListener()
//                    {
//
//
//                        @Override
//                        public void onPaymentSuccess(Object response) {
//                            //Cast response object to HashMap
//                            HashMap<String,Object> result = (HashMap<String, Object>) response;
//                            //when transaction complted
//                            String payuResponse = (String)result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
//
//                            //response from merchat via furl or surl
//                            String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
//                        }
//
//                        @Override
//                        public void onPaymentFailure(Object response) {
//                            //Cast response object to HashMap
//                            HashMap<String,Object> result = (HashMap<String, Object>) response;
//                            String payuResponse = (String)result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
//                            String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
//                        }
//
//                        @Override
//                        public void onPaymentCancel(boolean isTxnInitiated) {
//
//                            //when it is backpressed
//                            //when payment is cancelled,we get response call back
//                        }
//
//                        @Override
//                        public void onError(ErrorResponse errorResponse) {
//
//                            //when any error is present in sdk
//                            String errorMessage = errorResponse.getErrorMessage();
//                        }
//
//                        @Override
//                        public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
//                            //For setting webview properties, if any. Check Customized Integration section for more details on this
//                        }
//
//                        @Override
//                        public void generateHash(HashMap<String, String> valueMap,
//                                                 PayUHashGenerationListener hashGenerationListener) {
//
//                            //when ever there is a transaction b/w client and server hashes are required
//                            //sdk will parse u hash string
//
//                            String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
//                            String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);
//                            if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData))
//                            {
//                                //Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
//
////                                String hash
////                                if (!TextUtils.isEmpty(hash)) {
////                                    HashMap<String, String> hashMap = new HashMap<>();
////                                    hashMap.put(hashName, hash);
////                                    hashGenerationListener.onHashGenerated(hashMap);
////                                }
//                            }
//                        }
//                    }
//            );

    }

}