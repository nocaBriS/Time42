package com.example.time42.Project;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.time42.Object.Project;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ProjectViewModel extends ViewModel {

    ArrayList<Project> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    public MutableLiveData<ArrayList<Project>> mObj;

    public ProjectViewModel() {
        //super(application);

        // = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        //String name = sharedpreferences.getString("name", "test");

        CollectionReference proRef = db.collection("User").document("admin").collection("Project");
        proRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Project tmp = new Project(document.getId(), ((Timestamp) document.getData().get("StartDate")).toDate(), ((Timestamp) document.getData().get("EndDate")).toDate());
                            list.add(tmp);
                            Log.d("test", tmp.toString());
                        }
                    } else {
                        Log.d("test", "Error getting documents: ", task.getException());
                    }
                });

        mObj = new MutableLiveData<ArrayList<Project>>();
        mObj.setValue(list);
    }



    public MutableLiveData<ArrayList<Project>> getProject() {
        if(mObj == null)
        {
            mObj = new MutableLiveData<ArrayList<Project>>();

        }
        return mObj;
    }

}