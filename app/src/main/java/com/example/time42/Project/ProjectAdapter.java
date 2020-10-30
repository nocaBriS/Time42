package com.example.time42.Project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.time42.Object.Project;
import com.example.time42.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProjectAdapter extends BaseAdapter {

    private List<Project> list = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;

    public ProjectAdapter(Context ctx, int layoutId, List<Project> list)
    {
        this.list = list;
        this.layoutId = layoutId;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i){
        return list.get(i);
    }

    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Project project = list.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;
        ((TextView) listItem.findViewById(R.id.ProjektName)).setText(project.getName());
        ((TextView) listItem.findViewById(R.id.VonText)).setText(project.getStart().toString());
        ((TextView) listItem.findViewById(R.id.BisText)).setText(project.getEnd().toString());
        return listItem;
    }


}
