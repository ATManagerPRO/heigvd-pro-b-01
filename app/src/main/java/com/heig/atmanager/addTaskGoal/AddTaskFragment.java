package com.heig.atmanager.addTaskGoal;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.User;
import com.heig.atmanager.UserViewModel;
import com.heig.atmanager.Utils;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.provider.CalendarContract.Events;


public class AddTaskFragment extends Fragment {

    private static final String TAG = "AddTaskFragment";

    private String title;
    private String description;
    private DatePickerDialog picker;
    private final Calendar calendar = Calendar.getInstance();
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mHour;
    private int mMinute;


    private ArrayList<Task> tasks;
    private RecyclerView tasksRecyclerView;

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
        tasksRecyclerView = (RecyclerView) getActivity().findViewById(R.id.tasks_rv);

        // Override OnBacPressed to show hidden components
        final OnBackPressedCallback callback = new OnBackPressedCallback(true) {


            @Override
            public void handleOnBackPressed() {
                getFragmentManager().popBackStack();

                getActivity().findViewById(R.id.fab_container).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.dock).setVisibility(View.VISIBLE);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

                // Bind the picker value to ours variable
                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dueDateString = Utils.formatNumber(dayOfMonth) + "." +
                                Utils.formatNumber(month + 1) + "." +
                                Utils.formatNumber(year);
                        dueDateTextView.setText(dueDateString);
                        mYear = year;
                        mMonth = month;
                        mDay
                                = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                // Show the picker
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
                        dueTimeTextView.setText(
                                Utils.formatNumber(hourOfDay) + ":" + Utils.formatNumber(minute));
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        // Tags
        //final ArrayAdapter<String> chipsAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, currentUser.getTags().getValue());
        ArrayList<String> test = new ArrayList<>();
        test.add("tag1");
        test.add("tag2");
        final ArrayAdapter<String> chipsAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, test);
        // App detect the input to suggest the tag
        final AutoCompleteTextView autoCompleteTextView = mView.findViewById(R.id.frag_add_task_autocomplete_textview);
        autoCompleteTextView.setAdapter(chipsAdapter);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    ChipGroup chipGroup = mView.findViewById(R.id.frag_add_task_chipgroup);
                    addChipToGroup(autoCompleteTextView.getText().toString().trim(), chipGroup);
                    autoCompleteTextView.setText(null);
                }
                return false;
            }
        });

        // Directory spinner
        final Spinner folderSpinner = mView.findViewById(R.id.frag_directory_choice_tag_spinner);
        ArrayAdapter<TaskList> spinnerAdapter = new AddTaskSpinnerAdapter(getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                ((MainActivity) getContext()).getUser().getTaskLists());
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        folderSpinner.setAdapter(spinnerAdapter);

        // Button
        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                selectedDirectory = folderSpinner.getSelectedItem().toString();




                if (title.isEmpty()) {
                    titleLayout.setError(getString(R.string.input_missing));
                    return;
                } else {
                    titleLayout.setError(null);
                }

                Date selectedDate;
                if (mYear == 0) {
                    selectedDate = null;
                } else {
                    selectedDate = new GregorianCalendar(mYear, mMonth, mDay, mHour, mMinute).getTime();

                    MainActivity.googleCalendarHandler.addTask(title, selectedDate);
                    }
                Task newTask = new Task(title, description, selectedDate);

                // Add the task to a selected taskList
                for (TaskList taskList : ((MainActivity) getContext()).getUser().getTaskLists()) {
                    if (taskList.toString().equals(selectedDirectory)) {
                        newTask.setTasklist(taskList);
                        ((MainActivity) getContext()).getUser().addTask(newTask);
                        //update homeview
                        tasks = (((MainActivity) getContext()).getUser().getTasksForDay(Calendar.getInstance().getTime()));
                        tasks.addAll((((MainActivity) getContext()).getUser().getTasksWithoutDate()));
                        ((TaskFeedAdapter) tasksRecyclerView.getAdapter()).setTasks(tasks);
                    }
                }


                getActivity().findViewById(R.id.fab_container).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.dock).setVisibility(View.VISIBLE);

                getFragmentManager().popBackStack();
            }
        });


        return mView;
    }



    /**
     * Add a string as chip into the given chip group
     *
     * @param tag       a name
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
