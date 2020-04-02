package com.heig.atmanager.addTaskGoal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.heig.atmanager.R;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddTaskFragment extends Fragment {

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
    ArrayList<String> userTags = new ArrayList<>(Arrays.asList("Urgent", "Normal"));

    private String selectedDirectory;

    private EditText titleEditText;
    private EditText descriptionEditText;

    private TextView dueDateTextView;
    private TextView dueTimeTextView;

    private TextInputLayout titleLayout;

    public AddTaskFragment() {
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
        final View mView = inflater.inflate(R.layout.fragment_add_task, container, false);

        // Bind all input with id
        titleEditText = mView.findViewById(R.id.frag_add_task_title);
        descriptionEditText = mView.findViewById(R.id.frag_add_task_description);

        dueDateTextView = mView.findViewById(R.id.frag_add_task_due_date);
        dueTimeTextView = mView.findViewById(R.id.frag_add_task_due_time);

        titleLayout = mView.findViewById(R.id.frag_add_task_title_layout);

        final Button validationButton = mView.findViewById(R.id.frag_validation_button);

        // Picker for date and time
        dueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dueDateString = dayOfMonth + "." + (month + 1) + "." + year;
                        dueDateTextView.setText(dueDateString);
                    }
                }, mYear, mMonth, mDay);
                picker.show();
            }
        });

        dueTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueTimeTextView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        // Tags
        //TODO get user tags
        ArrayAdapter<String> chipsAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, userTags);

        final AutoCompleteTextView autoCompleteTextView = mView.findViewById(R.id.frag_add_task_autocomplete_textview);
        autoCompleteTextView.setAdapter(chipsAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoCompleteTextView.setText(null);
                String text = (String) adapterView.getItemAtPosition(i);
                ChipGroup chipGroup = mView.findViewById(R.id.frag_add_task_chipgroup);
                addChipToGroup(text, chipGroup);
            }
        });


        // Directory spinner
        //TODO get user directory
        final Spinner tagSpinner = mView.findViewById(R.id.frag_directory_choice_tag_spinner);

        ArrayAdapter tagAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, directory);
        tagAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        tagSpinner.setAdapter(tagAdapter);
        selectedDirectory = tagSpinner.getSelectedItem().toString();

        // Button
        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                if (title.isEmpty()) {
                    titleLayout.setError(getString(R.string.input_missing));
                    return;
                } else {
                    titleLayout.setError(null);
                }

                Date selectedDate = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();

                // TODO add to the user todo
                new Task(title, description, selectedDate, selectedDirectory);
            }
        });

        return mView;
    }

    /**
     * Add a string as chip into the given chip group
     * @param tag a name
     * @param chipGroup where to add
     */
    private void addChipToGroup(String tag, final ChipGroup chipGroup) {
        final Chip chip = new Chip(this.getContext());

        chip.setText(tag);
        chip.setCloseIconEnabled(true);

        chip.setClickable(true);
        chip.setCheckable(false);
        chipGroup.addView(chip);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(chip);
            }
        });
    }

}
