package com.example.time42.Calendar;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.time42.Object.MyCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CalendarViewModel extends AndroidViewModel {
    ArrayList<MyCalendar> list = new ArrayList<>();

    //testCalender tmp1 = new testCalender("Geburtstag", "30.12.2002", "30.12.2002");
    SharedPreferences sharedpreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private MutableLiveData<ArrayList<MyCalendar>> mObj;

    public CalendarViewModel(Application application) {
        super(application);
        Log.i("CalendarViewModel", "test");

        //list.add(tmp1);
        mObj = new MutableLiveData<>();

        sharedpreferences = getApplication().getSharedPreferences("logPref", Context.MODE_PRIVATE);
        checkExist();
    }

    public LiveData<ArrayList<MyCalendar>> getProject() {
        if (mObj == null) {
            mObj = new MutableLiveData<>();
        }
        return mObj;
    }


    private void checkExist() {
        Log.i("CalendarViewModel", "test");

        CollectionReference colRef = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Calendar");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() == null) {
                        //Create Collection
                        db.collection("User").document(sharedpreferences.getString("name", null)).collection("Calendar")
                                .add(new HashMap<>())
                                .addOnSuccessListener(aVoid -> Log.i("CalendarViewModel", "DocumentSnapshot successfully written!"))
                                .addOnFailureListener(e -> Log.i("CalendarViewModel", "Error writing document", e));
                    } else {
                        //find all Calendar Dates
                        Log.i("CalendarViewModel", "find all Calendar Dates");
                        //Task<QuerySnapshot> task = db.collection("User").document(sharedpreferences.getString("name", null)).collection("Calendar").get();
                        //QuerySnapshot qSnapshot = colRef.get().getResult();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MyCalendar cal = new MyCalendar(document.getId(), (Timestamp) document.getData().get("startDate"), (Timestamp) document.getData().get("endDate"));
                            list.add(cal);
                            mObj.setValue(list);
                        }
                    }
                }
            }
        });
    }
}