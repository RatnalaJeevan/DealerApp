package com.wisedrive.dealerapp1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class YearPickerDialog extends DialogFragment
{

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        View dialog = inflater.inflate(R.layout.year_picker_dialog, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(thisYear);
        yearPicker.setValue(year);

        builder.setView(dialog).setPositiveButton(Html.fromHtml("<font color='#7F000000'>Ok</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
               // listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                ((AddNewCar)getContext()).choose_year.setText(String.valueOf(yearPicker.getValue()));
            }
        }).setNegativeButton(Html.fromHtml("<font color='#9a9a9a'>Cancel</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                YearPickerDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
