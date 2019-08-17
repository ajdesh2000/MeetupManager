package com.icos.meetupmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.aigestudio.wheelpicker.widgets.WheelDayPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FillDetails extends AppCompatActivity {
    EditText name,venue;
    Button submitButton;
    WheelDatePicker wheelDatePicker;
    WheelPicker sHour;
    WheelPicker sMinute;

    WheelPicker eHour;
    WheelPicker eMinute;
    Intent submitIntent;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);


        firebaseFirestore=FirebaseFirestore.getInstance();


        submitButton=findViewById(R.id.submit_button);
        wheelDatePicker=findViewById(R.id.w_date);
        sHour=findViewById(R.id.w_start_hour);
        sMinute=findViewById(R.id.w_start_minute);

        eHour=findViewById(R.id.w_end_hour);
        eMinute=findViewById(R.id.w_end_minute);
        name=findViewById(R.id.name);
        venue=findViewById(R.id.venue);
        List<String> hours=new ArrayList<>();
        List<String> minutes=new ArrayList<>();


        for(int temp=0;temp<=9;temp++) {
            hours.add("0"+String.valueOf(temp));
            minutes.add("0"+String.valueOf(temp));
        }


        for(int temp=10;temp<=23;temp++)
            hours.add(String.valueOf(temp));

        for(int temp=10;temp<=59;temp++)
            minutes.add(String.valueOf(temp));

        sHour.setCyclic(true);
        sMinute.setCyclic(true);
        eHour.setCyclic(true);
        eMinute.setCyclic(true);

        sHour.setData(hours);
        eHour.setData(hours);

        sMinute.setData(minutes);
        eMinute.setData(minutes);

        wheelDatePicker.setVisibleItemCount(3);
        wheelDatePicker.setCurved(true);
        wheelDatePicker.setItemTextSize(50);

        sHour.setVisibleItemCount(3);
        sHour.setCurved(true);
        sHour.setItemTextSize(50);

        sMinute.setVisibleItemCount(3);
        sMinute.setCurved(true);
        sMinute.setItemTextSize(50);

        eHour.setVisibleItemCount(3);
        eHour.setCurved(true);
        eHour.setItemTextSize(50);

        eMinute.setVisibleItemCount(3);
        eMinute.setCurved(true);
        eMinute.setItemTextSize(50);
        submitIntent=new Intent(this,GroupInfo.class);
        Toast.makeText(this,getIntent().getExtras().getString("groupcode"),Toast.LENGTH_SHORT).show();
        submitIntent.putExtra("groupcode",getIntent().getExtras().getString("groupcode"));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CollectionReference members=firebaseFirestore.collection("groups").document(getIntent().getExtras().getString("groupcode")).collection("members");
                Map data=new HashMap();
                String startTime,endTime;
                startTime=sHour.getData().get(sHour.getCurrentItemPosition()).toString();
                startTime=startTime.concat(sMinute.getData().get(sMinute.getCurrentItemPosition()).toString());
                endTime=eHour.getData().get(eHour.getCurrentItemPosition()).toString();
                endTime=endTime.concat(eMinute.getData().get(eMinute.getCurrentItemPosition()).toString());
                data.put("venue",venue.getText().toString());
                 data.put("stime",startTime);
                data.put("etime",endTime);

                members.document(name.getText().toString()).set(data);
                startActivity(submitIntent);
            }
        });





    }
}
