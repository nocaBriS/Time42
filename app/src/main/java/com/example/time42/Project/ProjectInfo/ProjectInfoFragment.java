package com.example.time42.Project.ProjectInfo;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.time42.Project.ProjectFragment;
import com.example.time42.R;

public class ProjectInfoFragment extends Fragment {

    private ProjectInfoViewModel mViewModel;

    public static ProjectInfoFragment newInstance() {
        return new ProjectInfoFragment();
    }

    View root;

    TextView projectName;

    TextView hourText;
    TextView statusText;
    CircleProgressBar statusBar;
    TextView doneText;
    CircleProgressBar doneBar;

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

            projectName.setText(obj.getName());

            hourText.setText(obj.getHours() + "");
            switch (Integer.parseInt(String.valueOf(obj.getStatus()))) {
                case 1:
                    doneText.setText("Grün");
                    statusBar.setColor(res.getColor(R.color.green));
                    break;
                case 3:
                    doneText.setText("Rot");
                    statusBar.setColor(res.getColor(R.color.red));
                    break;
                default:
                    doneText.setText("Gelb");
                    statusBar.setColor(res.getColor(R.color.yellow));
                    break;
            }

        });

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_projectinfo, container, false);

        projectName = root.findViewById(R.id.ProjektName);

        hourText = root.findViewById(R.id.hourTextView);
        statusText = root.findViewById(R.id.statusText);
        statusBar = root.findViewById(R.id.statusBar);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = mContext.getResources();
        Log.i("test", "Create");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext == null)
            mContext = context.getApplicationContext();
        Log.i("test", "Attach");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        Log.i("test", "Detach");

    }
}