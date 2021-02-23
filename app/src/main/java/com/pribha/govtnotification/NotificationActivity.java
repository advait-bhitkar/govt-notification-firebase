package com.pribha.govtnotification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private List<Notification> notificationList = new ArrayList<>();
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setUpRecyclerView();
        //setDatabase();
        initFireStore();
    }


    private void setUpRecyclerView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
         adapter = new NotificationAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notification notification) {

                Intent intent = new Intent(NotificationActivity.this, NotificationDetailsActivity.class);
                intent.putExtra("title", notification.getTitle());
                intent.putExtra("description", notification.getDescription());
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), String.valueOf(notification.getId()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initFireStore(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                notificationList.add(new Notification(document.get("title").toString(), document.get("description").toString()));
                                Log.d("sdfgh", document.getId() + " => " + document.getData());
                            }

                            adapter.setnotifications(notificationList);

                        } else {
                            Log.w("sdgfhm", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void setDatabase(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        for (int num=1; num<=25; num++) {

            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("title", "Govt. Notification" + num);
            user.put("description", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n");

            // Add a new document with a generated ID
            db.collection("notification")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("dsvfd", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("sdfgdfbd", "Error adding document", e);
                        }
                    });


        }
    }
}