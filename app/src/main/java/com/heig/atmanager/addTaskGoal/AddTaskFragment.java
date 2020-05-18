package com.heig.atmanager.addTaskGoal;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.NotificationUtils;
import com.heig.atmanager.PostRequests;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.tasks.TaskFeedAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddTaskFragment extends Fragment {

    public static final String FRAG_ADD_TASK_ID = "Add_Task_Fragment";

    private static final String TAG = "AddTaskFragment";

    private static final int MINUTE = 0;
    private static final int HOUR   = 1;
    private static final int DAY    = 2;
    private static final int MONTH  = 3;
    private static final int YEAR   = 4;

    private String title;
    private String description;
    private DatePickerDialog dueDatePicker;
    private DatePickerDialog notificationPicker;
    private final Calendar calendar = Calendar.getInstance();
    private int mDayDueDate;
    private int mMonthDueDate;
    private int mYearDueDate;
    private int mHourDueDate;
    private int mMinuteDueDate;

    private int mDayNotif;
    private int mMonthNotif;
    private int mYearNotif;
    private int mHourNotif;
    private int mMinuteNotif;

    private ArrayList<Task> tasks;
    private RecyclerView tasksRecyclerView;

    private String selectedDirectory;

    private EditText titleEditText;
    private EditText descriptionEditText;

    private TextView dueDateTextView;
    private TextView dueTimeTextView;

    private TextView notificationTextView;


    private TextInputLayout titleLayout;

    private ArrayList<String> tags;

    private NotificationUtils mNotificationUtils;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tasksRecyclerView = (RecyclerView) getActivity().findViewById(R.id.tasks_rv);

        mNotificationUtils = new NotificationUtils(getContext());

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

        notificationTextView = mView.findViewById(R.id.frag_add_task_notification);

        titleLayout = mView.findViewById(R.id.frag_add_task_title_layout);

        final Button validationButton = mView.findViewById(R.id.frag_validation_button);


        // Picker for date and time
        dueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDayDueDate = calendar.get(Calendar.DAY_OF_MONTH);
                mMonthDueDate = calendar.get(Calendar.MONTH);
                mYearDueDate = calendar.get(Calendar.YEAR);

                // Bind the picker value to ours variable
                dueDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dueDateString = Utils.formatNumber(dayOfMonth) + "." +
                                Utils.formatNumber(month + 1) + "." +
                                Utils.formatNumber(year);
                        dueDateTextView.setText(dueDateString);
                        mYearDueDate = year;
                        mMonthDueDate = month;
                        mDayDueDate = dayOfMonth;
                    }
                }, mYearDueDate, mMonthDueDate, mDayDueDate);
                // Show the picker
                dueDatePicker.show();
            }
        });

        dueTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHourDueDate = calendar.get(Calendar.HOUR_OF_DAY);
                mMinuteDueDate = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dueTimeTextView.setText(
                                Utils.formatNumber(hourOfDay) + ":" + Utils.formatNumber(minute));
                    }
                }, mHourDueDate, mMinuteDueDate, true);
                timePickerDialog.show();
            }
        });

        // Picker for notification
        notificationTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mDayNotif = calendar.get(Calendar.DAY_OF_MONTH);
                mMonthNotif = calendar.get(Calendar.MONTH);
                mYearNotif = calendar.get(Calendar.YEAR);
                mHourNotif = calendar.get(Calendar.HOUR_OF_DAY);
                mMinuteNotif = calendar.get(Calendar.MINUTE);


                DatePickerDialog notificationPicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        final String notifTime = Utils.formatNumber(dayOfMonth) + "." +
                                Utils.formatNumber(month + 1) + "." +
                                Utils.formatNumber(year);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mHourNotif = hourOfDay;
                                mMinuteNotif = minute;
                                notificationTextView.setText(notifTime + " "  +Utils.formatNumber(hourOfDay) + ":" + Utils.formatNumber(minute));

                            }
                        }, mHourNotif, mMinuteNotif, true);
                        timePickerDialog.show();


                        mYearNotif = year;
                        mMonthNotif = month;
                        mDayNotif = dayOfMonth;

                    }
                }, mYearNotif, mMonthNotif, mDayNotif);

                // Show the picker
                notificationPicker.show();


            }
        });

        for (String s : MainActivity.getUser().getTags())
            Log.d(TAG, "onCreateView: Tag : " + s);

        // date setup here

        if(MainActivity.getUser().getTags() != null) {
            for (String s : MainActivity.getUser().getTags())
                Log.d(TAG, "onCreateView: Tag : " + s);
        }
        // Tags
        tags = new ArrayList<>();
        // Enable the user to choose between his/her tags
        final ArrayAdapter<String> chipsAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, MainActivity.getUser().getTags());
        // App detect the input to suggest the tag
        final AutoCompleteTextView autoCompleteTextView = mView.findViewById(R.id.frag_add_task_autocomplete_textview);
        autoCompleteTextView.setAdapter(chipsAdapter);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.d(TAG, "onEditorAction: onCreateView adding tag");
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
                ((MainActivity) getContext()).getUser().getAllTaskLists());
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

                Task newTask = new Task(title, description);
                if (mYearDueDate != 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mYearDueDate, mMonthDueDate, mDayDueDate, mHourDueDate, mMinuteDueDate);
                    newTask.setDueDate(calendar.getTime());
                }

                if (mYearNotif != 0) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mYearNotif, mMonthNotif, mDayNotif, mHourNotif, mMinuteNotif);
                    Notification.Builder nb = mNotificationUtils.getChannelNotification(getString(R.string.app_name), title);
                    mNotificationUtils.scheduleNotification(nb.build(), calendar.getTimeInMillis());
                    newTask.setReminderDate(calendar.getTime());
                }

                // Add the tags
                for (String tag : tags) {
                    newTask.addTag(tag);
                }

                // Add the task to a selected taskList
                for (TaskList taskList : MainActivity.getUser().getTaskLists()) {
                    if (taskList.toString().equals(selectedDirectory)) {
                        // Assigning the tasklist and adding the task in the tasklist
                        // (which is already in the user)
                        newTask.setTasklist(taskList);
                        PostRequests.postTask(newTask,getContext());
                        ((MainActivity) getContext()).getUser().addTask(newTask);
                        //update homeview
                        tasks = MainActivity.getUser().getTasksForDay(Calendar.getInstance().getTime());
                        tasks.addAll(MainActivity.getUser().getTasksWithoutDate());
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
        tags.add(tag);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(chip);
                tags.remove(chip.getText().toString());
            }
        });
    }

}
