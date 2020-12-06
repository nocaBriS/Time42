package com.example.time42.Profile;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.time42.Object.Project;
import com.example.time42.Object.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference mStorageRef;

    SharedPreferences sharedpreferences;

    public MutableLiveData<User> mUser;
    public MutableLiveData<User> getUser() {
        if (mUser == null) {
            mUser = new MutableLiveData<User>();
        }
        return mUser;
    }

    public MutableLiveData<byte[]> mImage;
    public MutableLiveData<byte[]> getImagePath() {
        if (mImage == null) {
            mImage = new MutableLiveData<byte[]>();
        }
        return mImage;
    }

    public ProfileViewModel(Application application) {
        super(application);

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("name", "test");

        DocumentReference userRef = db.collection("User").document(name);
        userRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if(document != null) {
                            User tmp = new User(document.getData().get("First").toString(), document.getData().get("Last").toString(), document.getData().get("Email").toString(), document.getData().get("Password").toString());
                            mUser.setValue(tmp);
                        }
                    } else {
                        Log.d("test", "Error getting documents: ", task.getException());
                    }
                });

        downloadImage();

    }

    public void downloadImage()
    {
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://time42-5d136.appspot.com");


        mStorageRef.child("20190824_174157.jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            // Use the bytes to display the image
            Log.i("bytes", "Fehler: " + bytes);
            mImage.setValue(bytes);

        }).addOnFailureListener(exception -> {
            Log.i("bytes", "Fehler: " + exception);
        });
    }

}