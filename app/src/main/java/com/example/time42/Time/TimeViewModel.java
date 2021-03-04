package com.example.time42.Time;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TimeViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    public MutableLiveData<Date> mDate;
    public String id;

    public MutableLiveData<Date> getmDate() {
        if (mDate == null) {
            mDate = new MutableLiveData<>();
        }
        return mDate;
    }

    public TimeViewModel(Application application, String id) {
        super(application);
        this.id = id;

        if (mDate == null) {
            mDate = new MutableLiveData<>();
        }
        mDate.postValue(Calendar.getInstance().getTime());

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        checkExist();
    }

    private void checkExist() {

        DocumentReference docRef = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Time").document(id);
        docRef.get().addOnSuccessListener(DocumentSnapshot -> {

            if (!DocumentSnapshot.exists()) {
                //Create Collection
                db.collection("User").document(sharedpreferences.getString("name", null)).collection("Time").document(id)
                        .set(
                                new HashMap<>()
                        )
                        .addOnSuccessListener(aVoid -> Log.i("CalendarViewModel", "DocumentSnapshot successfully written!"))
                        .addOnFailureListener(e -> Log.i("CalendarViewModel", "Error writing document", e));

            }

        });

    }


}