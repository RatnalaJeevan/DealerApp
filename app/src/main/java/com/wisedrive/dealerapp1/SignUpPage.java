package com.wisedrive.dealerapp1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.google.android.datatransport.backend.cct.BuildConfig;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.BitmapUtility;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Common;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.GeoCodeLocation;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.RequestPermissionHandler;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.PojoDealerDetails;
import com.wisedrive.dealerapp1.pojos.pojos.PojoEditDealer;
import com.wisedrive.dealerapp1.responseclasses.responseclasses.AppResponse;
import com.wisedrive.dealerapp1.services1.services.ApiClient;
import com.wisedrive.dealerapp1.services1.services.DealerApis;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPage extends AppCompatActivity
{
    public int selectedObject=0;
    RelativeLayout rl_go_back;
    String  d_city, d_state, d_pincode,isDealerCity="",it_is="" ;
    private DealerApis apiInterface;
    private ProgressDialog progressDialog;
    ImageView go_back_home,cancel_btn;
    TextView tv_sendotp,tv_signup,tv_otp,tv_resend,timer;
    String otps;
    Integer textlength1,textlength2,textlength3;
    EditText otp1,otp2,otp3,otp4;
    EditText selected_dealership_name,selected_dealername,selected_phoneno,selected_adres,selected_pincode,entered_location,
            selected_mail;
    TextView selected_city,selected_state,label_create;
    LinearLayout otp_ll;
    public String selectedcityid="",selectedstateid="",selectedcityname="";
    ArrayList<String> cityname,cityid;
    RelativeLayout rl_dealerlogo;
    ImageView iv_dealerlogo;
    String filename;
    private Uri imageUri;
    public  String dealerlogourl ="";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private RequestPermissionHandler mRequestPermissionHandler;
    String emailpattern = "^(?=.{1,64}@)[A-Za-z0-9_-]*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    String mobile_no_pattern="^[6-9][0-9]{9}$";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        cityname = new ArrayList<>();
        cityid = new ArrayList<>();
        getWindow().setStatusBarColor(getColor(R.color.white));
        AWSMobileClient.getInstance().initialize(SignUpPage.this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(SignUpPage.this,SPHelper.awskey,""), SPHelper.getSPData(SignUpPage.this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();
        SPHelper.sharedPreferenceInitialization(SignUpPage.this);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progressDialog = new ProgressDialog(SignUpPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_go_back=findViewById(R.id.rl_go_back);
        timer=findViewById(R.id.timer);
        label_create=findViewById(R.id.label_create);
        selected_mail=findViewById(R.id.selected_mail);
        entered_location=findViewById(R.id.entered_location);
        cancel_btn=findViewById(R.id.cancel_btn);
        selected_adres= findViewById(R.id.selected_adres);
        selected_pincode= findViewById(R.id.selected_pincode);
        iv_dealerlogo=findViewById(R.id.iv_dealerlogo);
        rl_dealerlogo=findViewById(R.id.rl_dealerlogo); 
        otp_ll=findViewById(R.id.otp_ll);
        selected_dealership_name=findViewById(R.id.selected_dealership_name);
        selected_city=findViewById(R.id.selected_city);
        selected_state=findViewById(R.id.selected_state);
        selected_dealername=findViewById(R.id.selected_dealername);
        selected_phoneno=findViewById(R.id.selected_phoneno);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        tv_otp=findViewById(R.id.tv_otp);
        tv_resend=findViewById(R.id.tv_resend);
        tv_signup=findViewById(R.id.tv_signup);
        tv_sendotp=findViewById(R.id.tv_sendotp);
        go_back_home=findViewById(R.id.go_back_home);
        rl_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.camefrom.equals("edit_d")){
                    finish();
                }else{
                    Intent intent=new Intent(SignUpPage.this,LoginPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        if(SPHelper.camefrom.equals("edit_d"))
        {

            tv_signup.setText("SUBMIT");
            selected_dealership_name.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.dealership_name,""));
            selected_pincode.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.pincode,""));
            selected_dealername.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.dealername,""));
            selected_phoneno.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.dealerno,""));
            selected_mail.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.d_email,""));
            selected_adres.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.d_adress,""));
            entered_location.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.d_location,""));
            selected_city.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.d_city,""));
            selected_state.setText(SPHelper.getSPData(SignUpPage.this,SPHelper.d_state,""));
            String logo=SPHelper.getSPData(SignUpPage.this,SPHelper.dealerlogo,"");
            if (logo != null && !logo.isEmpty() && !logo.equals("null")) {
                Glide.with(SignUpPage.this).load(logo).placeholder(R.drawable.icon_noimage).into(iv_dealerlogo);
                dealerlogourl=logo;
            }
            selected_pincode.setEnabled(false);
            selected_pincode.setFocusable(false);
            label_create.setText("Edit Your\nAccount");
        }else{
            tv_signup.setText("SIGNUP");
            label_create.setText("Create\nAccount");
        }

        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                tv_sendotp.setVisibility(View.GONE);
                if(selected_phoneno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_phoneno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!selected_phoneno.getText().toString().matches(mobile_no_pattern)){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    settimer();
                    if(SPHelper.camefrom.equals("edit_d")){

                        send_edit_otp();
                    }else{
                        send_otp();
                    }

                }

            }
        });
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_ll.setVisibility(View.VISIBLE);
                tv_otp.setVisibility(View.VISIBLE);
                tv_resend.setVisibility(View.VISIBLE);
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_phoneno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_phoneno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    otp1.setText("");
                    otp2.setText("");
                    otp3.setText("");
                    otp4.setText("");
                    otp1.requestFocus();
                    settimer();
                    if(SPHelper.camefrom.equals("edit_d")){

                        send_edit_otp();
                    }else{
                        send_otp();
                    }
                }

            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                //Toast.makeText(SignUpPage.this, selectedcityname, Toast.LENGTH_SHORT).show();
                if(selected_dealership_name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Enter your Dealership name",
                            Toast.LENGTH_SHORT).show();
                }
                else if(selected_dealername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Enter Dealer name",
                            Toast.LENGTH_SHORT).show();
                }
               else if(selected_phoneno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Enter Your PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_phoneno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }
               else if(!selected_mail.getText().toString().equals("")&&!selected_mail.getText().toString().matches(emailpattern)){
                    Toast.makeText(getApplicationContext(),
                            "Enter valid email",
                            Toast.LENGTH_SHORT).show();
                }
               else if(selected_adres.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Enter your address",
                            Toast.LENGTH_SHORT).show();
                }
                else if(entered_location.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Enter your location",
                            Toast.LENGTH_SHORT).show();
                }
               else if(selected_pincode.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),
                            "Enter valid pincode",
                            Toast.LENGTH_SHORT).show();
                }
               else if(isDealerCity.equalsIgnoreCase("n")){
                    Toast.makeText(SignUpPage.this,"this pincode is not serving enter different pincode",Toast.LENGTH_SHORT).show();
                }
               else{
                    //if it comes fom edit
                    SPHelper.lon="";SPHelper.lat="";
                    String address = selected_adres.getText().toString()+entered_location.getText().toString()+selected_city.getText().toString()
                            +selected_state.getText().toString()+selected_pincode.getText().toString();
                    GeoCodeLocation locationAddress = new GeoCodeLocation();
                    locationAddress.getAddressFromLocation(address, getApplicationContext(), new
                            GeoCoderHandler());

                }
            }
        });
        selected_phoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_phoneno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_phoneno.getText().toString().length()<10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                    otp_ll.setVisibility(View.GONE);
                    tv_otp.setVisibility(View.GONE);
                    tv_resend.setVisibility(View.INVISIBLE);
                }else{
                    hideKeybaord();
                }

            }
        });
        otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength2 = otp2.getText().length();

                if (textlength2 >= 1) {
                    otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }
        });
        otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength4 = otp3.getText().length();

                if (textlength4 >= 1) {
                    hideKeybaord();
                    tv_signup.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        //set city
        //add dealerlogo
        rl_dealerlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(view);
            }
        });
       // selected_pincode.addTextChangedListener(textWatcher);
        selected_pincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        selected_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_pincode.getText().toString().trim().length() >0||selected_pincode.getText().toString().trim().length() <6){
                    cancel_btn.setVisibility(View.VISIBLE);
                    selected_city.setText("");
                    selected_state.setText("");
                }
                if (selected_pincode.getText().toString().trim().length() == 6) {
                    hideKeybaord();
                    cancel_btn.setVisibility(View.GONE);
                    selected_city.setFocusable(false);
                    selected_state.setFocusable(false);
                    get_pincode();
                }
            }
        });
    }


    public void opendialog(View view)
    {
        final Dialog dialog4 = new Dialog(SignUpPage.this);
        dialog4.setCancelable(true);
        View view4 = SignUpPage.this.getLayoutInflater().inflate(R.layout.dialog_camera_options, null);
        dialog4.setContentView(view4);
        RelativeLayout rl_camera4 = view4.findViewById(R.id.rl_camera);
        RelativeLayout rl_gallery4 = view4.findViewById(R.id.rl_gallery);

        rl_camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                it_is="c";
                if (shouldShowCameraPermissionRationale())
                {
                    // Show a dialog or message explaining why the camera permission is needed
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                    builder.setTitle("Camera Permission Required")
                            .setMessage("This app needs access to your camera to capture photos.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Request the camera permission
                                    requestCameraPermission();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                } else {
                    // Request the camera permission directly
                    requestCameraPermission();
                }
                dialog4.dismiss();
            }
        });
        rl_gallery4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                it_is="g";
                if (shouldShowCameraPermissionRationale())
                {
                    // Show a dialog or message explaining why the camera permission is needed
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                    builder.setTitle("Camera Permission Required")
                            .setMessage("This app needs access to your camera to capture photos.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Request the camera permission
                                    requestCameraPermission();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                } else {
                    // Request the camera permission directly
                    requestCameraPermission();
                }
                dialog4.dismiss();
            }
        });
        dialog4.show();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                selectedObject);
    }

    private boolean shouldShowCameraPermissionRationale() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == selectedObject)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed with camera functionality
               // Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                if(it_is.equals("c")){
                    openCamera();
                }
                else {
                    // open_gallery();

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto,selectedObject);

                }

            } else {

                // Camera permission denied, handle accordingly
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(SignUpPage.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                if (fromWhere == 1) {
                    CallCamera();
                } else if (fromWhere == 10) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 200);
                } 
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CallCamera() {
        mRequestPermissionHandler.requestPermission(SignUpPage.this, new String[]{
                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {

            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
//        {
            // Create the File where the photo should go
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            imageUri = FileProvider.getUriForFile(SignUpPage.this,
                    com.wisedrive.dealerapp1.BuildConfig.APPLICATION_ID + ".provider",
                    new File(filename));
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, 100);
       // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (it_is.equals("c") && resultCode == RESULT_OK )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                   // imageUri = FileProvider.getUriForFile(SignUpPage.this,
                     //       BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                   // filename = OriginalFileName;
                    iv_dealerlogo.setImageDrawable(null);
                    iv_dealerlogo.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(SignUpPage.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignUpPage.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    dealerlogourl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (it_is.equals("g") && data!=null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(SignUpPage.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(SignUpPage.this, imageUri, filename, new Pair<Integer, Integer>(800, 800), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    iv_dealerlogo.setImageDrawable(null);
                    iv_dealerlogo.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(SignUpPage.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SignUpPage.this);
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
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    dealerlogourl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
    }

    private void validate()
    {
    }

    public void get_pincode() {
        cancel_btn.setVisibility(View.GONE);
        if(!Connectivity.isNetworkConnected(SignUpPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            //selected_pincode.removeTextChangedListener(textWatcher);
            Call<AppResponse> call = apiInterface.get_PinCodeDetails(selected_pincode.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();

                            if(appResponse.getResponse()==null){
                                Toast.makeText(SignUpPage.this,"this pincode is not valid",Toast.LENGTH_SHORT).show();

                            }else{
                                d_city = appResponse.getResponse().getGetpincodedetails().getCity();
                                d_state = appResponse.getResponse().getGetpincodedetails().getState();
                                isDealerCity=appResponse.getResponse().getGetpincodedetails().getIs_dealer_city();
                                selectedcityid=appResponse.getResponse().getGetpincodedetails().getCity_id();
                                selectedstateid=appResponse.getResponse().getGetpincodedetails().getState_id();
                                if(isDealerCity==null){
                                    Common.CallToast(SignUpPage.this,"enter valid pincode",1);
                                }
                                else if(isDealerCity.equalsIgnoreCase("y")){
                                    selected_city.setText(d_city);
                                    selected_state.setText(d_state);
                                }else{
                                    Toast.makeText(SignUpPage.this,"this pincode is not serving enter different pincode",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                           // selected_pincode.removeTextChangedListener(textWatcher);
                            Toast.makeText(SignUpPage.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    //selected_pincode.removeTextChangedListener(textWatcher);
                }
            });
        }
    }

    public void send_otp()
    {

        if(!Connectivity.isNetworkConnected(SignUpPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            tv_sendotp.setVisibility(View.GONE);
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.sendotp(selected_phoneno.getText().toString().trim(),"N");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200")) {
                            progressDialog.dismiss();
                            otp_ll.setVisibility(View.VISIBLE);
                            tv_otp.setVisibility(View.VISIBLE);
                            tv_resend.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (response_code.equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            selected_phoneno.setText("");
                        }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(SignUpPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void send_edit_otp()
    {

        if(!Connectivity.isNetworkConnected(SignUpPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            tv_sendotp.setVisibility(View.GONE);
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.edit_sendotp(selected_phoneno.getText().toString().trim(),"E",
                    SPHelper.getSPData(SignUpPage.this, SPHelper.dealerid, ""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200")) {
                            progressDialog.dismiss();
                            otp_ll.setVisibility(View.VISIBLE);
                            tv_otp.setVisibility(View.VISIBLE);
                            tv_resend.setVisibility(View.VISIBLE);
                            Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (response_code.equals("300")) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            selected_phoneno.setText("");
                        }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(SignUpPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
    public  void create_account(){
        {
            if(!Connectivity.isNetworkConnected(SignUpPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
                progressDialog.show();
                PojoDealerDetails post=new PojoDealerDetails(selected_dealership_name.getText().toString().trim(),selected_dealername.getText().toString().trim(),
                        selected_phoneno.getText().toString().trim(),dealerlogourl,selected_adres.getText().toString().trim(),
                        selectedcityid,selected_pincode.getText().toString(),otps,entered_location.getText().toString().trim(),
                        selected_mail.getText().toString(),SPHelper.lat,SPHelper.lon);
                Call<AppResponse> call =  apiInterface.createaccount(post);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                progressDialog.dismiss();

                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerid, appResponse.getResponse().getDealerInfo().getDealer_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealership_name, appResponse.getResponse().getDealerInfo().getDealership_name());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealername, appResponse.getResponse().getDealerInfo().getDealer_name());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerno, appResponse.getResponse().getDealerInfo().getPhone_no());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerlogo, appResponse.getResponse().getDealerInfo().getDealer_logo());

                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_email, appResponse.getResponse().getDealerInfo().getDealer_email());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_adress, appResponse.getResponse().getDealerInfo().getFull_address());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_location, appResponse.getResponse().getDealerInfo().getLocation());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_city, appResponse.getResponse().getDealerInfo().getCity());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_state, appResponse.getResponse().getDealerInfo().getState());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.city_id, appResponse.getResponse().getDealerInfo().getCity_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.state_id, appResponse.getResponse().getDealerInfo().getState_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.pincode, appResponse.getResponse().getDealerInfo().getPincode());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.helplineno, appResponse.getResponse().getDealerInfo().getCustomer_support_phone_no());
                                //System.out.println("awssecret"+appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.awssecret, appResponse.getResponse().getCredentials().getAws_secret());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.awskey, appResponse.getResponse().getCredentials().getAws_key());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.comet_authkey, appResponse.getResponse().getCredentials().getComet_auth_key());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.comet_region, appResponse.getResponse().getCredentials().getComet_region());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.comet_appid, appResponse.getResponse().getCredentials().getComet_app_id());
                                SPHelper.fragment_is="home";
                                SPHelper.comingfrom="";
                                SPHelper.camefrom="";
                                Intent intent=new Intent(SignUpPage.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(SignUpPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    public  void edit_account(){
        {
            if(!Connectivity.isNetworkConnected(SignUpPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
                progressDialog.show();

                PojoEditDealer pojoEditDealer=new PojoEditDealer(SPHelper.getSPData(SignUpPage.this,SPHelper.dealerid,""),
                        selected_dealership_name.getText().toString().trim(),selected_dealername.getText().toString().trim(),selected_mail.getText().toString(),
                        entered_location.getText().toString().trim(),"",selected_adres.getText().toString().trim(),"",SPHelper.getSPData(SignUpPage.this,SPHelper.city_id,""),
                        SPHelper.getSPData(SignUpPage.this,SPHelper.state_id,""),SPHelper.lat,SPHelper.lon,"",selected_phoneno.getText().toString().trim(),otps,
                        dealerlogourl);
                System.out.println(pojoEditDealer);
                Call<AppResponse> call =  apiInterface.editDealer(pojoEditDealer);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {

                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpPage.this, "Dealer edited successfully", Toast.LENGTH_SHORT).show();
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerid, appResponse.getResponse().getDealerInfo().getDealer_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealership_name, appResponse.getResponse().getDealerInfo().getDealership_name());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealername, appResponse.getResponse().getDealerInfo().getDealer_name());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerno, appResponse.getResponse().getDealerInfo().getPhone_no());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.dealerlogo, appResponse.getResponse().getDealerInfo().getDealer_logo());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_email, appResponse.getResponse().getDealerInfo().getDealer_email());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_adress, appResponse.getResponse().getDealerInfo().getFull_address());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_location, appResponse.getResponse().getDealerInfo().getLocation());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_city, appResponse.getResponse().getDealerInfo().getCity());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.d_state, appResponse.getResponse().getDealerInfo().getState());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.city_id, appResponse.getResponse().getDealerInfo().getCity_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.state_id, appResponse.getResponse().getDealerInfo().getState_id());
                                SPHelper.saveSPdata(SignUpPage.this, SPHelper.pincode, appResponse.getResponse().getDealerInfo().getPincode());
                               SPHelper.camefrom="";
                                SPHelper.comingfrom="";
                                SPHelper.fragment_is="profile";
                                Intent intent=new Intent(SignUpPage.this,MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpPage.this, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(SignUpPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    private void hideKeybaord(){
        InputMethodManager inputManager = (InputMethodManager) SignUpPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(SignUpPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    public void oncancelSelect(View view) {
        hideKeybaord();
        selected_pincode.setText("");
        cancel_btn.setVisibility(View.GONE);
        selected_city.setText("");selected_state.setText("");
    }

    public  void settimer()
    {
        new CountDownTimer(30000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timer.setVisibility(View.VISIBLE);
                timer.setText(""+ millisUntilFinished / 1000);
            }
            public void onFinish() {
                tv_resend.setVisibility(View.VISIBLE);
                timer.setText("");
            }
        }.start();
    }

    private class GeoCoderHandler extends Handler
    {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what)
            {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            if(SPHelper.camefrom.equals("edit_d"))
            {
                edit_account();
            }else{
                create_account();
            }
            System.out.println("adress"+locationAddress);
        }
    }

}