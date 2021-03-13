package com.example.time42.Project.ProjectInfo;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.Object.User;
import com.example.time42.Project.User.UserlistAdapter;
import com.example.time42.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectInfoFragment extends Fragment {

    public static ProjectInfoFragment newInstance() {
        return new ProjectInfoFragment();
    }


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;

    View root;

    private List<User> items;
    private ListView mListView;

    String id;
    Object obj;

    ProjectInfoViewModel mViewModel;

    TextView projectName;

    TextView hourText;
    TextView statusText;
    TextView descTextView;
    CircleProgressBar statusBar;

    FloatingActionButton Fab;

    private Context mContext;
    Resources res;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("test", "ViewCreated");

        id = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        sharedpreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);

        //Fab.setOnClickListener(v -> addUser());

        mViewModel = new ViewModelProvider(this, new ProjectInfoViewModelFactory(this.getActivity().getApplication(), id)).get(ProjectInfoViewModel.class);
        getData();

    }

    public void getData()
    {
        mViewModel.getProject().observe(this.getViewLifecycleOwner(), obj -> {

            projectName.setText(obj.getName());

            hourText.setText(obj.getHours() + "");
            switch (Integer.parseInt(String.valueOf(obj.getStatus()))) {
                case 1:
                    statusText.setText("Grün");
                    //statusBar.setColor(res.getColor(R.color.green));
                    break;
                case 3:
                    statusText.setText("Rot");
                    //statusBar.setColor(res.getColor(R.color.red));
                    break;
                default:
                    statusText.setText("Gelb");
                    //statusBar.setColor(res.getColor(R.color.yellow));
                    break;
            }

            descTextView.setText(obj.getDesc());

            items = obj.getItems();
            bindAdapterToListView(mListView);

        });
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res = getActivity().getResources();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_projectinfo, container, false);

        projectName = root.findViewById(R.id.ProjektName);

        hourText = root.findViewById(R.id.hourTextView);
        statusText = root.findViewById(R.id.StatusTextView);
        statusBar = root.findViewById(R.id.statusBar);
        descTextView = root.findViewById(R.id.descTextView);

        mListView = root.findViewById(R.id.userList);

        Fab = root.findViewById(R.id.addFab);
        Fab.setOnClickListener(v -> addUser());

        LinearLayout swipeLayout = root.findViewById(R.id.swipeLayout);
        ImageView image = root.findViewById(R.id.imageView2);
        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            @Override
            public void onSwipeBottom() {
                ObjectAnimator animation = ObjectAnimator.ofFloat(swipeLayout, "translationY", 1080f);
                animation.setDuration(500);
                animation.start();

                RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setFillAfter(true);
                image.startAnimation(rotate);

            }

            @Override
            public void onSwipeTop() {

                ObjectAnimator animation = ObjectAnimator.ofFloat(swipeLayout, "translationY", 0f);
                animation.setDuration(500);
                animation.start();

                RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(500);
                rotate.setInterpolator(new LinearInterpolator());
                rotate.setFillAfter(true);
                image.startAnimation(rotate);

            }
        });

        return root;
    }

    public void addUser() {

        final EditText userText = new EditText(this.getContext());
        userText.setHint("Username");
        new AlertDialog.Builder(this.getContext())
                .setView(userText)
                .setMessage("Member hinzufügen")

                .setPositiveButton(android.R.string.yes, (dialog, which) -> {

                    if (userText.getText().length() > 1) {

                        Map<String, Object> user = new HashMap<>();

                        //Projekt zu User hinzufügen
                        DocumentReference docRef = db.collection("User").document(userText.getText().toString());
                        docRef.get().addOnSuccessListener(DocumentSnapshot -> {
                            if (DocumentSnapshot.exists()) {

                                if (DocumentSnapshot.get("ProjectIDs") == null) {

                                    user.put("ProjectIDs", Collections.emptyList());
                                    docRef.update(user);
                                    docRef.update("ProjectIDs", FieldValue.arrayUnion(id));

                                    Toast toast = Toast.makeText(this.getContext(), "User: " + userText.getText() + " wurde hinzugefügt", Toast.LENGTH_SHORT);
                                    toast.show();
                                } else {
                                    docRef.update("ProjectIDs", FieldValue.arrayUnion(id));

                                    Toast toast = Toast.makeText(this.getContext(), "User: " + userText.getText() + " wurde hinzugefügt", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                                //User zu Projekt hinzufügen
                                DocumentReference pjrRef = db.collection("Project").document(id);
                                pjrRef.get().addOnSuccessListener(Document -> {
                                    if (Document.exists()) {

                                        if (Document.get("User") == null) {

                                            user.put("User", Collections.emptyList());
                                            pjrRef.update(user);
                                            pjrRef.update("User", FieldValue.arrayUnion(userText.getText().toString()));

                                            Toast toast = Toast.makeText(this.getContext(), "User: " + userText.getText() + " wurde hinzugefügt", Toast.LENGTH_SHORT);
                                            toast.show();

                                        } else {
                                            pjrRef.update("User", FieldValue.arrayUnion(userText.getText().toString()));

                                            Toast toast = Toast.makeText(this.getContext(), "User: " + userText.getText() + " wurde hinzugefügt", Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                    }

                                });

                            } else {
                                Toast toast = Toast.makeText(this.getContext(), "User: " + userText.getText() + " nicht gefunden!", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        });

                    }
                    getData();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    @Override
    public void onStop() {
        mViewModel.getProject().removeObservers(this);
        super.onStop();
    }

    private void bindAdapterToListView(ListView lv) {
        lv.setAdapter(new UserlistAdapter(this.getContext(),
                R.layout.listitem_user,
                items));
    }


}