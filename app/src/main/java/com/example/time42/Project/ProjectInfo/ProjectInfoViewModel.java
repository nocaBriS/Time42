package com.example.time42.Project.ProjectInfo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.time42.Object.Project;
import com.example.time42.Object.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ProjectInfoViewModel extends AndroidViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<Project> mObj;


    public MutableLiveData<Project> getProject() {
        mObj = new MutableLiveData<>();
        return mObj;
    }

    String name;

    public ProjectInfoViewModel(@NonNull Application application, String mId) {
        super(application);

        DocumentReference projectRef = db.collection("Project").document(mId);

        projectRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot document = task.getResult();
                            Project tmp = new Project((String) document.getData().get("Name"),
                                    ((Timestamp) document.getData().get("StartDate")).toDate(),
                                    ((Timestamp) document.getData().get("EndDate")).toDate(),
                                    document.getId(),
                                    (String) document.getData().get("Beschreibung"),
                                    (String) document.getData().get("Owner"),
                                    (Long) document.getData().get("Hours"),
                                    (Long) document.getData().get("Status"),
                                    (List<User>) document.getData().get("User"));

                            mObj.postValue(tmp);

                        } catch (NullPointerException e) {
                            Log.i("ProjectViewModel", e + "");
                        }
                    }
                });

        projectRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }

            String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";

            if (snapshot != null && snapshot.exists()) {
                Project tmp = new Project((String) snapshot.getData().get("Name"),
                        ((Timestamp) snapshot.getData().get("StartDate")).toDate(),
                        ((Timestamp) snapshot.getData().get("EndDate")).toDate(),
                        snapshot.getId(),
                        (String) snapshot.getData().get("Beschreibung"),
                        (String) snapshot.getData().get("Owner"),
                        (Long) snapshot.getData().get("Hours"),
                        (Long) snapshot.getData().get("Status"),
                        (List<User>) snapshot.getData().get("User"));

                mObj.postValue(tmp);
            } else {
            }
        });


    }
    // TODO: Implement the ViewModel
}