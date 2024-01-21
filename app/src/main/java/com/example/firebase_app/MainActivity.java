package com.example.firebase_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String namef;
    private int agef;
    private String speciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference userRef= database.collection("doctors").document("l8CYpNeDKeQbkEBTaPJw");

        userRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Data is available in the document
                                namef = document.getString("name");
                               //agef = document.getLong("age").intValue(); // Assuming "age" is stored as a number
                                speciality = document.getString("speciality");



                                Log.d(TAG, "Name: " + namef);
                                //Log.d(TAG, "Age: " + agef);
                                Log.d(TAG, "Specialization: " + speciality);
                                //Log.i(TAG,"UID:"+uid);
                                FirebaseDatabase database_add = FirebaseDatabase.getInstance();
                                DatabaseReference doctorsRef = database_add.getReference("doctors");

// Create a unique ID for the doctor
                                if (namef != null){
                                String doctorId = speciality;

// Create a data map for the doctor
                                Map<String, Object> doctorData = new HashMap<>();
                                //doctorData.put("specialization", speciality);

// Create a map for appointment slots
                                Map<String, Object> appointmentSlots = new HashMap<>();
                                appointmentSlots.put("10 00", true);
                                appointmentSlots.put("10 30", false);
                                appointmentSlots.put("11 00", true);
                                appointmentSlots.put("11 30", true);
                                appointmentSlots.put("12 00", true);
                                appointmentSlots.put("12 30", true);
                                appointmentSlots.put("13 00", true);
                                appointmentSlots.put("13 30", true);
                                appointmentSlots.put("14 00", true);
                                appointmentSlots.put("14 30", true);

                                doctorData.put("appointment_slots", appointmentSlots);

// Set the data in the Realtime Database
                                doctorsRef.child(doctorId).child(namef).setValue(doctorData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "Data successfully written to Realtime Database!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing data to Realtime Database", e);
                                            }
                                        });
                                }

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "Error getting document", task.getException());
                        }
                    }
                });



    }
}