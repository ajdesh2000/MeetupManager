package com.icos.meetupmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button jgButton;
    Button cgButton;
    Intent i;
    EditText etgc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=new Intent(this,FillDetails.class);
        jgButton=findViewById(R.id.join_group_id);
        cgButton=findViewById(R.id.create_group_id);
        etgc=findViewById(R.id.gcet);
        jgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code;
                code=etgc.getText().toString();
                i.putExtra("groupcode",etgc.getText().toString());
                startActivity(i);
            }
        });


        cgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random=new Random();
                String code="";
                int r;

                for(int t=0;t<4;t++) {
                    r = 'a'+random.nextInt(26);
                    code = (String) code.concat(Character.toString((char)r));
                }
                i.putExtra("groupcode",code);

                startActivity(i);


            }
        });


    }
}
