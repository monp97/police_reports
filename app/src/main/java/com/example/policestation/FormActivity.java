package com.example.policestation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.Calendar;

public class FormActivity extends BaseActivity {

    String cur_date;
    int month_ind;
    int year;
    String new_date;
    TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        edit=findViewById(R.id.date_edit_text);

        cur_date=getIntent().getStringExtra("current_date");

        String[] arr=cur_date.split(",");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        myToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        edit.setText(cur_date);

        month_ind=MainActivity.months.indexOf(arr[1]);
        year=Integer.parseInt(arr[2]);

        new_date=arr[1]+","+year;


    }

    public void changePrev(View view) {

       super.onBackPressed();

    }

    public void pickDate(View view)
    {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(FormActivity.this,myDateListener,year,month_ind, cal.get(Calendar.DATE));
                dialog.show();

    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            new_date=MainActivity.months.get(arg2)+","+arg1;

            edit.setText(arg3+","+new_date);

        }
    };


    public void DisplayInMain(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",new_date);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }


}
