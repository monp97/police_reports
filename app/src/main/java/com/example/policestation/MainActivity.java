package com.example.policestation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends BaseActivity {

    String TAG = "MainActivity";
    int selected_month;
    int index;
    int selected_year;
    String activated_month;
    int activated_year;
     TextView tv;
     Calendar today = Calendar.getInstance();
    static ArrayList<String> months=new ArrayList<String>(Arrays.asList("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //change intial month and year
        tv=findViewById(R.id.month_picker);

        String[] str={months.get(today.get(Calendar.MONTH)),(today.get(Calendar.YEAR))+""};

        activated_month=str[0];
        activated_year=Integer.parseInt(str[1]);

        index=months.indexOf(activated_month);

        tv.setText(activated_month+","+activated_year);

        setNormalPicker();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        myToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //load all the transactions for the current date.
        loadTransactionsFor(activated_month+","+activated_year);

    }


    private void setNormalPicker() {

        //calendar library used

        findViewById(R.id.month_picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                       // Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);

                        selected_month=selectedMonth;
                        selected_year=selectedYear;

                        String new_picked_date=findMonthYearString(selected_month,selectedYear);

                        TextView myAwesomeTextView = (TextView)findViewById(R.id.month_picker);

                        myAwesomeTextView.setText(new_picked_date);

                        loadTransactionsFor(new_picked_date);

                        Toast.makeText(MainActivity.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));



                builder.setActivatedMonth(index)
                        .setMinYear(1990)
                        .setActivatedYear(activated_year)
                        .setMaxYear(2030)
                        .setMinMonth(Calendar.JANUARY)
                        .setTitle("Select Month and Year")
                        .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {
                              //  Log.d(TAG, "Selected month : " + selectedMonth);

                                index=selectedMonth;

                                // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {
                               // Log.d(TAG, "Selected year : " + selectedYear);
                                // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                                activated_year=selectedYear;
                            }
                        })
                        .build()
                        .show();

            }

        });

    }

    private String findMonthYearString(int selected_month, int selectedYear) {

        return months.get(selected_month)+","+selectedYear;
    }


    public void changePrev(View view) {

        int year;
        String month;
        tv=findViewById(R.id.month_picker);

        String month_year=tv.getText().toString();

       // Log.d(TAG, "prev this : " +month_year);

        String[] str=month_year.split(",");

        month=str[0];

        year=Integer.parseInt(str[1]);

        if(month.compareTo("JAN")==0)
        {
            year=year-1;
            month="DEC";
            index=11;
        }
        else
        {
           int index1=months.indexOf(month);

           month=months.get(index1-1);
           index=index1-1;

        }


        tv.setText(month+","+year);
        activated_year=year;

        loadTransactionsFor(month+","+year);
    }


    public void changeNext(View view) {

        int year;
        String month;

       tv=findViewById(R.id.month_picker);

        String month_year=tv.getText().toString();

       // Log.d(TAG, "prev this : " +month_year);

        String[] str=month_year.split(",");

        month=str[0];

        year=Integer.parseInt(str[1]);

        if(month.compareTo("DEC")==0)
        {
            year=year+1;
            month="JAN";
            index=0;

        }
        else
        {
            int index1=months.indexOf(month);
            month=months.get(index1+1);
            index=index1+1;

        }

        tv.setText(month+","+year);

        activated_year=year;

        loadTransactionsFor(month+","+year);

    }


    public void clicked(View view) {

        Intent intent=new Intent(MainActivity.this,FormActivity.class);
        intent.putExtra("current_date", today.get(Calendar.DATE)+","+months.get(index)+","+activated_year);
        startActivityForResult(intent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");

                Log.d(TAG+"helloooo", result);
                this.tv.setText(result);
                String[] str=result.split(",");

                index=months.indexOf(str[0]);
                activated_year=Integer.parseInt(str[1]);

                loadTransactionsFor(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }

    }

    private void loadTransactionsFor(String result) {

        //load all transactions for this date.

        //currently load dummy recycler view.

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        rv.setHasFixedSize(true);


        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        List<Transaction> persons;

        activated_month=months.get(index);

        String str1="6 "+activated_month+","+activated_year;
        String str2="7 "+activated_month+","+activated_year;


            persons = new ArrayList<>();
            persons.add(new Transaction(str1,1,3,4,6));
            persons.add(new Transaction(str2, 23,12,45,67));
        persons.add(new Transaction(str1,1,3,4,6));
        persons.add(new Transaction(str2, 23,12,45,67));
        persons.add(new Transaction(str1,1,3,4,6));
        persons.add(new Transaction(str2, 23,12,45,67));
        persons.add(new Transaction(str1,1,3,4,6));
        persons.add(new Transaction(str2, 23,12,45,67));
        persons.add(new Transaction(str1,1,3,4,6));
        persons.add(new Transaction(str2, 23,12,45,67));


            RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(persons);
            rv.setAdapter(recyclerViewAdapter);

    }


    public void onClickWhatsApp(View view) {

        new FancyGifDialog.Builder(this)
                .setTitle("Share on whatsapp?")
                .setMessage("The entire information of your form will be shared, press ok to continue.")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Ok")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(R.drawable.gif2)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                        openWhatsapp();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    public void openWhatsapp()
    {
        PackageManager pm=getPackageManager();

        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            String text = "SENDING CCDS";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

}
