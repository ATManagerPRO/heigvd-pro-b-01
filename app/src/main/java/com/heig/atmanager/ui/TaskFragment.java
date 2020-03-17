package com.heig.atmanager.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.heig.atmanager.R;
import com.heig.atmanager.Task;
import com.heig.atmanager.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class TaskFragment extends Fragment implements View.OnClickListener {

    private String title;
    private String description;
    private DatePickerDialog picker;
    private final Calendar calendar = Calendar.getInstance();
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHour;
    private int mMinute;
    ArrayList<String> directory = new ArrayList<>(Arrays.asList("bla"));

    private String selectedDirectory;


    private EditText titleInput;
    private EditText descriptionInput;
    private EditText tagsInput;

    private TextView dueDate;
    private TextView dueTime;




    public TaskFragment() {
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
        View view = inflater.inflate(R.layout.fragment_task, container, false);


        titleInput = view.findViewById(R.id.frag_task_title);
        descriptionInput = view.findViewById(R.id.frag_task_description);
        tagsInput = view.findViewById(R.id.frag_task_tags);

        dueDate = view.findViewById(R.id.frag_task_due_date);
        dueTime = view.findViewById(R.id.frag_task_due_time);

        final ImageButton imageValidationButton = view.findViewById(R.id.frag_validation_button);


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

        //directory.addAll(User.getDirectories());

        final Spinner tagSpinner = view.findViewById(R.id.frag_directory_choice_tag_spinner);

        ArrayAdapter tagAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, directory);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        selectedDirectory = tagSpinner.getSelectedItem().toString();

        imageValidationButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        title = titleInput.getText().toString();
        description = descriptionInput.getText().toString();

        Date selectedDate = new GregorianCalendar(mYear, mMonth,mDay, mHour,mMinute).getTime();

        new Task(title, description, selectedDate, selectedDirectory);
    }
}
