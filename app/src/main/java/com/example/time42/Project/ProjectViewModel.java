package com.example.time42.Project;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.time42.Object.Project;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ProjectViewModel extends AndroidViewModel {

    ArrayList<Project> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    public MutableLiveData<ArrayList<Project>> mObj;

    public MutableLiveData<ArrayList<Project>> getProject() {
        if (mObj == null) {
            mObj = new MutableLiveData<>();
        }
        return mObj;
    }

    public ProjectViewModel(Application application) {
        super(application);

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        String name = sharedpreferences.getString("name", "test");
        Log.d("test", "test");

        CollectionReference proRef = db.collection("User").document(name).collection("Project");
        proRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Project tmp = new Project(document.getId(), ((Timestamp) document.getData().get("StartDate")).toDate(), ((Timestamp) document.getData().get("EndDate")).toDate());
                            list.add(tmp);
                            Log.d("test", tmp.toString());
                        }
                        mObj.setValue(list);
                    } else {
                        Log.d("test", "Error getting documents: ", task.getException());
                    }
                });

    }

}