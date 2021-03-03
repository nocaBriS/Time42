package com.example.time42.Project.ProjectInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.Calendar.CalendarAdapter;
import com.example.time42.Object.MyCalendar;
import com.example.time42.Object.User;
import com.example.time42.Project.ProjectFragment;
import com.example.time42.Project.User.UserlistAdapter;
import com.example.time42.R;

import java.util.List;

public class ProjectInfoFragment extends Fragment {

    public static ProjectInfoFragment newInstance() {
        return new ProjectInfoFragment();
    }

    View root;

    private List<User> items;
    private ListView mListView;

    ProjectInfoViewModel mViewModel;

    TextView projectName;

    TextView hourText;
    TextView statusText;
    TextView descTextView;
    CircleProgressBar statusBar;

    private Context mContext;
    Resources res;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("test", "ViewCreated");

        String id = null;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        mViewModel = new ViewModelProvider(this, new ProjectInfoViewModelFactory(this.getActivity().getApplication(), id)).get(ProjectInfoViewModel.class);
        mViewModel.getProject().observe(getViewLifecycleOwner(), obj -> {

            Log.i("test", obj.getName());
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
        mListView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            @Override
            public void onSwipeBottom() {
                mListView.setVisibility(View.INVISIBLE);
            }
        });

        root.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeTop() {
                mListView.setVisibility(View.VISIBLE);
            }
        });


        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void bindAdapterToListView(ListView lv) {
        lv.setAdapter(new UserlistAdapter(this.getContext(),
                R.layout.listitem_user,
                items));
    }


}