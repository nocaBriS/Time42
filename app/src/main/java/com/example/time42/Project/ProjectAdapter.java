package com.example.time42.Project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.time42.Object.Project;
import com.example.time42.R;

import java.util.List;

public class ProjectAdapter extends BaseAdapter{

    private List<Project> list;
    private final int layoutId;
    private final LayoutInflater inflater;

    private TextView text2;
    private TextView text3;
    private ImageButton btn;
    private boolean isexpand = false;

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
        ((TextView) listItem.findViewById(R.id.VonText)).setText(project.getStart());
        ((TextView) listItem.findViewById(R.id.BisText)).setText(project.getEnd());

        text2 = listItem.findViewById(R.id.textView2);
        text3 = listItem.findViewById(R.id.textView3);
        btn = listItem.findViewById(R.id.expandButton);
        listItem.setOnClickListener(v -> expand());
        listItem.findViewById(R.id.expandButton).setOnClickListener(v -> expand());

        return listItem;
    }

    private void expand()
    {

        if(!isexpand)
        {
            this.text2.setVisibility(View.VISIBLE);
            this.text3.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.ic_expand_less);
        }else{
            this.text2.setVisibility(View.GONE);
            this.text3.setVisibility(View.GONE);
            btn.setImageResource(R.drawable.ic_expand_more);
        }

        isexpand = !isexpand;

    }

}