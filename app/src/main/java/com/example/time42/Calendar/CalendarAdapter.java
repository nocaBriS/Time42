package com.example.time42.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.time42.Object.MyCalendar;
import com.example.time42.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends BaseAdapter{

    private List<MyCalendar> list;
    private final int layoutId;
    private final LayoutInflater inflater;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private TextView text2;
    private TextView text3;
    private ImageButton btn;
    private boolean isexpand = false;

    public CalendarAdapter(Context ctx, int layoutId, List<MyCalendar> list)
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
        MyCalendar calender = list.get(i);
        View listItem = (view == null) ? inflater.inflate(this.layoutId, null) : view;

        Date startDate = calender.getStartDate().toDate();
        Date endDate = calender.getEndDate().toDate();
        ((TextView) listItem.findViewById(R.id.ErgName)).setText(calender.getName());
        ((TextView) listItem.findViewById(R.id.ErgZeit)).setText(dateFormat.format(startDate) + " - " + dateFormat.format(endDate));

        return listItem;
    }

}