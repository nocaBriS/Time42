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

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    ArrayList<Integer> timeList = new ArrayList<>();
    ArrayList<String> projectList = new ArrayList<>();

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

}