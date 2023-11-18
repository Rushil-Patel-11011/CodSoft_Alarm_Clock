package com.rushil.codsoft_alarm_clock;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.List;
public class AlarmAdapter extends ArrayAdapter<Alarm>{

public AlarmAdapter(Context context, List<Alarm> alarms) {
        super(context, 0, alarms);
        }

@Override
public View getView(final int position, View convertView, ViewGroup parent) {
        Alarm alarm = getItem(position);
        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
        }

        TextView timeTextView = convertView.findViewById(R.id.timeTextView);
        Switch switchh = convertView.findViewById(R.id.switchh);

        timeTextView.setText(alarm.getHour() + " : " + alarm.getMinute());

        switchh.setChecked(alarm.isEnabled());
        switchh.setOnCheckedChangeListener((buttonView, isChecked) -> {
        Alarm alarm1 = getItem(position);
        alarm1.setEnabled(isChecked);
        });

        return convertView;
        }
}
