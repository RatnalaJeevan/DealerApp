package com.wisedrive.dealerapp1;

import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wisedrive.dealerapp1.adapters.adapters.Adapter_static_images;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.BitmapUtility;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.Connectivity;
import com.wisedrive.dealerapp1.commonclasses1.commonclasses.SPHelper;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_Update_list;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_imagearray;
import com.wisedrive.dealerapp1.pojos.pojos.Pojo_part_list;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowListedImages extends BottomSheetDialogFragment
{
    public static ShowListedImages instance;
    Dialog dialog_upload;
    Uri cam_uri;
    String it_is = "", filename;
    public int selectedObject = 0;
    TextView heading,cancel,takefromgallery,Recapture;
    ArrayList<String> final_imgs = new ArrayList<>();
    ArrayList<String> final_ids = new ArrayList<>();
    private DealerApis apiInterface;
    Activity activity;
    ArrayList<Pojo_part_list> pojo_part_listArrayList = new ArrayList<>();
    Adapter_static_images adapter_static_images;
    RecyclerView rv_car_imagelist;
    AppCompatButton add_feature_button;
    ArrayList<Pojo_imagearray> pojo_imagearrayArrayList;
    ProgressBar progress_bar;
    private BasicAWSCredentials credentials;
    private AmazonS3Client s3Client;
    ImageView image_popup_cross;
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_show_listed_images,
                container, false);
        activity=getActivity();
        instance=this;
        AWSMobileClient.getInstance().initialize(activity).execute();
        SPHelper.sharedPreferenceInitialization(activity);
        credentials = new BasicAWSCredentials(SPHelper.getSPData(activity, SPHelper.awskey, ""),
                SPHelper.getSPData(activity, SPHelper.awssecret, ""));
        s3Client = new AmazonS3Client(credentials);
        apiInterface = ApiClient.getClient().create(DealerApis.class);
        progress_bar=v.findViewById(R.id.progress_bar);
        rv_car_imagelist=v.findViewById(R.id.rv_car_imagelist);
        add_feature_button=v.findViewById(R.id.add_feature_button);
        image_popup_cross=v.findViewById(R.id.image_popup_cross);
        getimage_part_list();

        add_feature_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_updated_imgs();
            }
        });

        image_popup_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }

    public void getimage_part_list()
    {
        System.out.println("vehid" + SPHelper.vehid);
        Call<AppResponse> call = apiInterface.part_list(SPHelper.vehid, "1");
        call.enqueue(new Callback<AppResponse>() {
            @Override
            public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                AppResponse appResponse = response.body();
                assert appResponse != null;
                String response_code = appResponse.getResponseType();
                if (response.body() != null) {
                    if (response_code.equals("200")) {
                        pojo_part_listArrayList = new ArrayList<>();
                        pojo_part_listArrayList = appResponse.getResponse().getPartDetails();
                        adapter_static_images = new Adapter_static_images(activity, pojo_part_listArrayList);
                        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
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

    public void get_updated_imgs() {
        pojo_imagearrayArrayList = new ArrayList<>();
        for (int i = 0; i < final_ids.size(); i++) {
            Pojo_imagearray imageobj = new Pojo_imagearray();
            imageobj.setImage(final_imgs.get(i));
            imageobj.setImage_type_id(final_ids.get(i));
            pojo_imagearrayArrayList.add(imageobj);
        }

        if (pojo_imagearrayArrayList.isEmpty()) {
            Toast.makeText(activity,
                    "Please upload atleast one image",
                    Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("imgarray" + pojo_imagearrayArrayList);
            update_images();
        }
    }

    public void update_images() {
        {
            if (!Connectivity.isNetworkConnected(activity)) {
                Toast.makeText(activity,
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progress_bar.setVisibility(View.VISIBLE);


                Pojo_Update_list pojo_update_list = new Pojo_Update_list(SPHelper.vehid, pojo_imagearrayArrayList);
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

                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                               // get_insp_veh_list();
                                MainActivity.getInstance().get_data_present();
                                final_imgs = new ArrayList<>();
                                final_ids = new ArrayList<>();
                                pojo_imagearrayArrayList = new ArrayList<>();
                                dismiss();
                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                                Toast.makeText(activity, appResponse.getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(activity, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(activity,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }


    private boolean shouldShowCameraPermissionRationale()
    {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA);
    }
    public void showPhotoDialog()
    {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.image_upload_options_pop);
        cancel = dialog.findViewById(R.id.cancel);
        Recapture = dialog.findViewById(R.id.Recapture);
        takefromgallery = dialog.findViewById(R.id.takefromgallery);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        takefromgallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                it_is="g";
                // open_gallery();

                if (shouldShowCameraPermissionRationale())
                {
                    // Show a dialog or message explaining why the camera permission is needed
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                dialog.cancel();
            }
        });


        Recapture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // Toast.makeText(activity,  "call camera", Toast.LENGTH_SHORT).show();
                it_is="c";
                if (shouldShowCameraPermissionRationale())
                {
                    // Show a dialog or message explaining why the camera permission is needed
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                dialog.cancel();


            }
        });

        dialog.show();
    }

    private void requestCameraPermission() {
        String[] permissions = {Manifest.permission.CAMERA};

        // Request the permissions
        requestPermissions(permissions, selectedObject);
    }


    public void open_Camera()
    {
        it_is="c";
        // Toast.makeText(activity,  "opoen camera", Toast.LENGTH_SHORT).show();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
        String fineName = dateFormat.format(new Date());
        filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
        cam_uri = FileProvider.getUriForFile(activity,
                BuildConfig.APPLICATION_ID + ".provider", new File(filename));
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);
        startActivityForResult(takePictureIntent, selectedObject);
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
                    open_Camera();
                }
                else {
                    // open_gallery();

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    String[] mimeTypes = {"image/*", "application/pdf"};
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, selectedObject);
                }

            } else {

                // Camera permission denied, handle accordingly
                Toast.makeText(activity, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && it_is.equals("g"))
        {
            Uri uri = data.getData();
            // Handle the selected PDF file here
            String type = activity.getContentResolver().getType(uri);

            if (type != null && type.startsWith("image/")) {
                // Handle the selected image file here


                SimpleDateFormat dateFormat = new SimpleDateFormat("-dd_MMM_yyyy_HH_mm_ss_SSSSSS'.jpg'");
                String fineName = dateFormat.format(new Date());
                filename = BitmapUtility.PictUtil.getSavePath1().getPath() + "/" + "Wisedrive" + fineName;
                String OriginalFileName = null;
                try {
                    OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs4(activity, uri, filename, new Pair<Integer, Integer>(800, 800), "/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                pojo_part_listArrayList.get(adapter_static_images.adapter_position).setTaken_img(uri);
                pojo_part_listArrayList.get(adapter_static_images.adapter_position).setFilename(OriginalFileName);
                if (final_ids.size() > 0) {
                    for (int i = 0; i < final_ids.size(); i++) {
                        if (pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id().equalsIgnoreCase(final_ids.get(i))) {
                            final_imgs.remove(i);
                            final_ids.remove(pojo_part_listArrayList.get(i).getPart_id());
                            break;
                        } else {
                        }
                    }
                }
                final_ids.add(pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id());
                //  iv_ins_copy.setImageURI(uri);
                upload_to_s3(uri);
            }

        }
        else if (resultCode == RESULT_OK && it_is.equals("c")) {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(2040, 1080), "/");
                    cam_uri = FileProvider.getUriForFile(activity,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    //setimageuri

                    if (!Connectivity.isNetworkConnected(activity)) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
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
                    if (final_ids.size() > 0) {
                        for (int i = 0; i < final_ids.size(); i++) {
                            if (pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id().equalsIgnoreCase(final_ids.get(i))) {
                                final_imgs.remove(i);
                                final_ids.remove(pojo_part_listArrayList.get(i).getPart_id());
                                break;
                            } else {
                            }
                        }
                    }
                    final_ids.add(pojo_part_listArrayList.get(adapter_static_images.adapter_position).getPart_id());

                    upload_to_s3(cam_uri);
                }
            });
        }
    }


    private void validate() {
    }

    public void upload_to_s3(Uri imageUri)
    {
        try {
            dialog_upload = ProgressDialog.show(
                    activity, "", "Uploading...");
            final TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(activity)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(s3Client)
                            .build();
            final String key = SPHelper.doc_name + "/" + imageUri.getLastPathSegment();
            final TransferObserver uploadObserver =
                    transferUtility.upload(key, new File(filename));
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Toast.makeText(activity,  SPHelper.doc_name+"\tuploaded!", Toast.LENGTH_SHORT).show();
                        String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                        System.out.print("doc_url" + finalurl);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog_upload.dismiss();
                                // progressDialog.cancel();
                            }
                        });
                        adapter_static_images.notifyDataSetChanged();

                        // doc_url=finalurl;
                        final_imgs.add(finalurl);
                        //update_images();

                    } else if (TransferState.FAILED == state) {

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog_upload.dismiss();
                                // progressDialog.cancel();
                            }
                        });
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal)
                {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;
                   // progress_bar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onError(int id, Exception ex) {
                    ex.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           // progress_bar.setVisibility(View.GONE);
                            // progressDialog.cancel();
                        }
                    });
                }

            });
        } catch (Exception je) {

            je.printStackTrace();
        }
    }


    public static ShowListedImages getInstance() {
        return instance;
    }
}