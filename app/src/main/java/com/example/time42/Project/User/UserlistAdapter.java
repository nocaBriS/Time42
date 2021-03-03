package com.example.time42.Project.User;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.time42.Object.User;
import com.example.time42.R;

import java.util.List;

public class UserlistAdapter extends BaseAdapter {

    private List<User> list;
    private final int layoutId;
    private final LayoutInflater inflater;

    private TextView username;

    public UserlistAdapter(Context ctx, int layoutId, List<User> list)
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
        Log.i("test", "list: " + list.get(i));
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;

        ((TextView) listItem.findViewById(R.id.userName)).setText((CharSequence) list.get(i));

        return listItem;
    }


}
