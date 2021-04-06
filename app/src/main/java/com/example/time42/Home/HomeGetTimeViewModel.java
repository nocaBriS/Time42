package com.example.time42.Home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeGetTimeViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    public MutableLiveData<ArrayList<String>> mObj;
    public MutableLiveData<ArrayList<String>> tmp;
    public MutableLiveData<String> mid;

    public MutableLiveData<ArrayList<String>> getAllProject() {
        if (mObj == null) {
            mObj = new MutableLiveData<>();
        }
        tmp = mObj;
        mObj.setValue(null);
        return tmp;
    }



    String name;

    public HomeGetTimeViewModel(Application application, int time) {
        super(application);
        Log.i("Date", "today: " + LocalDate.now() + " 7 Tage: " + LocalDate.now().plusDays(time));
        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        name = sharedpreferences.getString("name", null);
        if (mid.getValue() != null) {

            Log.i("Date", "Value" + mid.getValue());

            DocumentReference timeRef = db.collection("User").document(name).collection("Time").document(mid.getValue());
            timeRef.get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Get Time
                            }

                        }
                    });
        }
        else{
            Log.i("Date", "Value" + mid.getValue());

        }

    }


}
