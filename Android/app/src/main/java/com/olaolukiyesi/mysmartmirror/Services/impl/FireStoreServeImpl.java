package com.olaolukiyesi.mysmartmirror.Services.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.olaolukiyesi.mysmartmirror.Models.User;

import static android.content.ContentValues.TAG;

public class  FireStoreServeImpl {
    private static FireStoreServeImpl instance;
    private static FirebaseAuth authInstance;

    FirebaseFirestore db;
    String userID;
    public static FireStoreServeImpl getInstance() {
        if(instance== null){
            instance = new FireStoreServeImpl();
        }
        return instance;
    }

    public static FirebaseAuth getAuthInstance(){
        if(authInstance == null){
            authInstance = FirebaseAuth.getInstance();
        }
        return authInstance;
    }
    private FireStoreServeImpl(){
        db = FirebaseFirestore.getInstance();
        userID = FirebaseAuth.getInstance().getUid();


    }
    public void validateUser(final User current) {
        userID = current.getUserID();

        DocumentReference userRef = db.collection("Users").document(current.getUserID());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot cur_user = task.getResult();
                    if(cur_user.exists()){
                        Log.d(TAG, "onComplete: User Already Exists in DB");
                    }
                    else{
                        Log.d(TAG, "No such User in DB ");
                        createUser(current);
                    }
                }
            }
        });

    }
    private void createUser(User current) {

        db.collection("Users").document(current.getUserID()).set(current).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "DocumentSnapshot NOT successfully written!");

            }
        });

    }

}
