package com.example.time42.ProjectList;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.time42.Object.Project;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProjectListViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    ArrayList<Project> list = new ArrayList<>();

    public MutableLiveData<ArrayList<Project>> mObj;
    public MutableLiveData<ArrayList<Project>> getProject() {
        if (mObj == null) {
            mObj = new MutableLiveData<>();
        }
        return mObj;
    }

    String name;


    public ProjectListViewModel(Application application) {
        super(application);

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);

        name = sharedpreferences.getString("name", "test");

        DocumentReference proIDref = db.collection("User").document(name);

        proIDref.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                ArrayList projectIDs = (ArrayList) document.getData().get("ProjectIDs");

                                for (int i = 0; i < projectIDs.size(); i++) {

                                    DocumentReference proRef = db.collection("Project").document((String) projectIDs.get(i));
                                    proRef.get()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    DocumentSnapshot document1 = task1.getResult();
                                                    if (document1.exists()) {

                                                        Project tmp = new Project((String) document1.getData().get("Name"),
                                                                ((Timestamp) document1.getData().get("StartDate")).toDate(),
                                                                ((Timestamp) document1.getData().get("EndDate")).toDate(),
                                                                (String) document1.getId());
                                                        list.add(tmp);
                                                        mObj.setValue(list);
                                                    }                                               }

                                            });

                                }
                            }
                        } catch (NullPointerException e) {
                            Log.i("ProjectViewModel", e + "");
                        }
                    }
                });

    }




}