package com.example.time42.Project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.time42.Object.Project;
import com.example.time42.R;
import com.example.time42.Time.TimeFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectFragment extends Fragment {

    private ProjectViewModel projectViewModel;

    private List<Project> items;
    private ListView mListView;
    private ProgressBar progBar;

    private boolean tmp = true;

    View root;
    ConstraintLayout shape;
    FloatingActionButton addFab;
    FloatingActionButton saveFab;

    SharedPreferences sharedPreferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    //2nd View
    EditText nameText;
    EditText descText;
    EditText startDate;
    EditText endDate;

    Date stDate;
    Date enDate;

    MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_project, container, false);

        mListView = root.findViewById(R.id.list);
        progBar = root.findViewById(R.id.progressBar);
        addFab = root.findViewById(R.id.addFAB);

        sharedPreferences = getActivity().getSharedPreferences("logPref", Context.MODE_PRIVATE);


        //2nd View
        shape = root.findViewById(R.id.circle);
        shape.setVisibility(View.INVISIBLE);
        shape.setEnabled(false);
        saveFab = root.findViewById(R.id.saveFAB);
        nameText = root.findViewById(R.id.NameEdit);
        descText = root.findViewById(R.id.descEdit);
        startDate = root.findViewById(R.id.editTextStartDate);
        endDate = root.findViewById(R.id.editTextEndDate);

        saveFab.setOnClickListener(this::saveProject);
        addFab.setOnClickListener(this::revealShow);

        progBar.setVisibility(View.VISIBLE);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getProject().observe(getViewLifecycleOwner(), list -> {
            items = list;
            bindAdapterToListView(mListView);
            progBar.setVisibility(View.GONE);
        });

        //[Date Picker] builder
        builder.setTitleText("SELECT A DATE");
        builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar);
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        //[Date Picker] Listener
        startDate.setOnClickListener(v ->
                materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER")
        );
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            stDate = new Date(selection.first);
            enDate = new Date(selection.second);

            @SuppressLint("SimpleDateFormat")
            String stdateString = new SimpleDateFormat("dd/MM/yyyy").format(stDate);
            @SuppressLint("SimpleDateFormat")
            String endateString = new SimpleDateFormat("dd/MM/yyyy").format(enDate);

            startDate.setText(stdateString);
            endDate.setText(endateString);

        });

        //mListView.setOnItemClickListener((parent, view, position, id) -> test(view, position));
        mListView.setOnItemClickListener((parent, view, position, id) -> {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", items.get(position).getId());

                    TimeFragment myFragment = new TimeFragment();
                    myFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, myFragment, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null) // name can be null
                            .commit();

                }
        );


        return root;
    }


    private void test(View v, int pos) {

        Log.i("ProjectFragment", items.get(pos).getId() + ": id");
        Navigation.createNavigateOnClickListener(R.id.nav_time, null);

    }

    //Wird grad nicht verwendet
    private void expand(View v) {
        ImageButton btn = v.findViewById(R.id.expandButton);
        ConstraintLayout expandableView = v.findViewById(R.id.expandView);
        CardView cardview = v.findViewById(R.id.cardview);

        if (expandableView.getVisibility() == View.GONE) {
            TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
            expandableView.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.ic_expand_less);
        } else {
            TransitionManager.beginDelayedTransition(cardview, new AutoTransition());
            expandableView.setVisibility(View.GONE);
            btn.setImageResource(R.drawable.ic_expand_more);
        }
    }

    private void bindAdapterToListView(ListView lv) {
        lv.setAdapter(new ProjectAdapter(this.getContext(),
                R.layout.listitem_project,
                items));
    }

    private void saveProject(View view) {

        Map<String, Object> city = new HashMap<>();
        city.put("Name", nameText.getText().toString());
        city.put("Beschreibung", descText.getText().toString());
        city.put("StartDate", stDate);
        city.put("EndDate", enDate);
        city.put("Owner", sharedPreferences.getString("name", "Name:"));

        List<String> list = new ArrayList<String>();
        list.add(sharedPreferences.getString("name", "Name:"));
        city.put("User", list);

        String id = db.collection("Project").document().getId();

        db.collection("Project").document(id)
                .set(city)
                .addOnSuccessListener(aVoid -> {

                            getId();
                            DocumentReference UserRef = db.collection("User").document(sharedPreferences.getString("name", "Name:"));

                            // Atomically add a new region to the "regions" array field.

                            UserRef.update("ProjectIDs", FieldValue.arrayUnion(id));

                            Animator animator = ViewAnimationUtils.createCircularReveal(
                                    shape,
                                    shape.getWidth() - 130,
                                    shape.getHeight() - 130,
                                    (float) Math.hypot(shape.getWidth(), shape.getHeight()),
                                    0);

                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    shape.setVisibility(View.INVISIBLE);
                                }
                            });

                            animator.setInterpolator(new AccelerateDecelerateInterpolator());
                            animator.setDuration(400);
                            animator.start();
                            shape.setEnabled(false);
                        }
                )
                .addOnFailureListener(e -> Log.i("ProjectFragment", "Error writing document", e));

    }

    //FAB Animation
    private void revealShow(View view) {

        if (tmp) {

            Animator animator = ViewAnimationUtils.createCircularReveal(
                    shape,
                    shape.getWidth() - 130,
                    shape.getHeight() - 130,
                    0,
                    (float) Math.hypot(shape.getWidth(), shape.getHeight()));

            shape.setVisibility(View.VISIBLE);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            if (shape.getVisibility() == View.VISIBLE) {
                animator.setDuration(400);
                animator.start();
                shape.setEnabled(true);
                addFab.setVisibility(View.INVISIBLE);
            }
        } /*else {

            Animator animator = ViewAnimationUtils.createCircularReveal(
                    shape,
                    shape.getWidth() - 130,
                    shape.getHeight() - 130,
                    (float) Math.hypot(shape.getWidth(), shape.getHeight()),
                    0);

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    shape.setVisibility(View.INVISIBLE);
                }
            });

            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(400);
            animator.start();
            shape.setEnabled(false);

        } */

        tmp = !tmp;

    }

}