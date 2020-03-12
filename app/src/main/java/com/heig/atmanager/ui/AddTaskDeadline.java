package com.heig.atmanager.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.heig.atmanager.R;

import java.util.Calendar;


public class AddTaskDeadline extends Fragment {

    private DatePickerDialog picker;
    final Calendar calendar = Calendar.getInstance();
    int mDay;
    int mMonth;
    int mYear;
    int mHour;
    int mMinute;

    public AddTaskDeadline() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_add_task_deadline, container, false);

        final EditText dueDate = result.findViewById(R.id.frag_add_task_due_date);
        final EditText dueTime = result.findViewById(R.id.frag_Add_task_due_time);

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, mYear, mMonth, mDay);
                picker.show();
            }
        });

        dueTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        return result;
    }


}
