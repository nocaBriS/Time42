package com.example.time42.Home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.time42.Object.Project;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    ArrayList<String> projectList = new ArrayList<>();
    Date today = Calendar.getInstance().getTime();

    public MutableLiveData<ArrayList<String>> mObj;
    public MutableLiveData<ArrayList<String>> getAllProject() {
        if (mObj == null) {
            mObj = new MutableLiveData<>();
        }
        return mObj;
    }


    String name;


    public HomeViewModel(Application application) {
        super(application);

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        name = sharedpreferences.getString("name", null);
        CollectionReference timeRef = db.collection("User").document(name).collection("Time");
        timeRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("document", document.getId() + " => " + document.getData());
                                projectList.add(document.getId());

                            }
                            mObj.setValue(projectList);
                        } catch (NullPointerException e) {
                            Log.i("ProjectViewModel", e + "");
                        }
                    }
                });



    }

    public HomeViewModel(Application application, String id, int time)
    {
        super(application);
        Log.i("Date","today: " + LocalDate.now() + " 7 Tage: " + LocalDate.now().plusDays(time));
        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        name = sharedpreferences.getString("name", null);
        DocumentReference timeRef = db.collection("User").document(name).collection("Time").document(id);
        timeRef.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if(document.exists())
                        {

                        }

                    }
                });

    }

}

