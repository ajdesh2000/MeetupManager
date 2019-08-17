package com.icos.meetupmanager;

import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupInfo extends AppCompatActivity {
    ArrayList<String> groupMembersNames;
    ListView groupMember;
    TextView groupName;
    ArrayAdapter<String> groupMembers;
    Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        calculateButton=findViewById(R.id.calculate);
        groupName=findViewById(R.id.group_name);
        groupName.setText(getIntent().getExtras().getString("groupcode"));

        groupMember=findViewById(R.id.group_members);
        groupMembersNames=new ArrayList<>();
        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
        DocumentReference dr=firebaseFirestore.collection("groups").document(getIntent().getExtras().getString("groupcode"));
        groupMembers=new ArrayAdapter(this,android.R.layout.simple_list_item_1,groupMembersNames);
        dr.collection("members").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> docRefs=queryDocumentSnapshots.getDocuments();
                for(int temp=0;temp<docRefs.size();temp++)
                    groupMembersNames.add(docRefs.get(temp).getId());
                groupMembers.notifyDataSetChanged();

            }
        });
        groupMember.setAdapter(groupMembers);


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MediaType MEDIA_TYPE = MediaType.parse("text/plain");

                RequestBody body = RequestBody.create(MEDIA_TYPE,getIntent().getExtras().getString("groupcode"));

                final Request request = new Request.Builder()
                        .url("https://us-central1-meetupmanager-3f505.cloudfunctions.net/helloWorld")
                        .post(body)
                        .addHeader("Authorization", "Your Token")
                        .addHeader("cache-control", "no-cache")
                        .build();
                final OkHttpClient client = new OkHttpClient();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        String mMessage = e.getMessage().toString();
                        Log.w("failure Response", mMessage);
                        /* call.cancel(); */
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        String mMessage = response.body().string();
                        if (response.isSuccessful()){
                            try {

                                final String serverResponse = mMessage;
                                runOnUiThread(new Runnable(){
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),serverResponse,Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }

                });


            }
        });

    }
}

