package com.wisedrive.dealerapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.webkit.WebView;

import androidx.annotation.Nullable;

/*import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;*/

import java.util.HashMap;

public class DBHelperClass extends SQLiteOpenHelper
{
    public DBHelperClass(@Nullable Context context)
    {
        super(context,"Car_data.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table CarDetails(car_no TEXT,car_make TEXT,car_model TEXT,sel_price TEXT, " +
                "kms_driven TEXT,no_of_owner TEXT,color TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop Table if exists CarDetails");
    }

    public  Boolean inser_veh_details(String car_no,String car_make  ,String car_model ,String sel_price ,
                                      String kms_driven ,String no_of_owner ,
                                      String color ){

        //saving data
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("car_no",car_no);
        contentValues.put("car_make",car_make);
        contentValues.put("car_model", car_model);
        contentValues.put("sel_price",sel_price);
        contentValues.put("kms_driven",kms_driven);
        contentValues.put("no_of_owner",no_of_owner);
        contentValues.put("color",color);
        long result=db.insert("CarDetails",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return  true;
        }
    }

    public  Boolean update_veh_details(String car_no,String car_make  ,String car_model ,String sel_price ,
                                      String kms_driven ,String no_of_owner ,
                                      String color )
    {
        //saving data
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("car_no",car_no);
        contentValues.put("car_make",car_make);
        contentValues.put("car_model", car_model);
        contentValues.put("sel_price",sel_price);
        contentValues.put("kms_driven",kms_driven);
        contentValues.put("no_of_owner",no_of_owner);
        contentValues.put("color",color);

        Cursor cursor=db.rawQuery("Select * from CarDetails where car_make=?",new String[]{car_make});

        if(cursor.getCount()>0)
        {
            long result=db.update("CarDetails",contentValues,"car_make=?", new String[]{car_make});
            if(result==-1){
                return false;
            }else{
                return  true;
            }


        }else{
            return false;
        }
    }

    public  Cursor getdata()
    {
        //saving data
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("Select * from CarDetails ",null);
        return  cursor;
    }
}
