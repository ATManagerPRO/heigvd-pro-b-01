package com.heig.atmanager.addContent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.heig.atmanager.userData.PostRequests;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.Interval;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author   : Chau Ying Kot
 * Date     : 20.03.2020
 */

public class AddGoalFragment extends Fragment {

    public static final String FRAG_ADD_GOAL_ID = "Add_Goal_Fragment";

    // GUI Input
    private TextInputEditText quantityTextInput;
    private TextInputEditText unitTextInput;
    private TextInputEditText intervalNumberTextInput;
    private Spinner intervalSpinner;

    private DatePickerDialog picker;
    private TextView dueDateTextView;

    private Button validationButton;
    private Button cancelButton;

    private TextInputLayout unitLayout;
    private TextInputLayout quantityLayout;
    private TextInputLayout intervalNumberLayout;

    private final Calendar calendar = Calendar.getInstance();

    // Values
    private String unit;
    private int quantity;
    private int intervalNumber;
    private Interval interval;

    private int mDay;
    private int mMonth;
    private int mYear;

    public AddGoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_goal, container, false);

        // Bind input with the id
        quantityTextInput = view.findViewById(R.id.frag_add_goal_quantity);
        unitTextInput = view.findViewById(R.id.frag_add_goal_unit);
        intervalNumberTextInput = view.findViewById(R.id.frag_add_goal_interval_number);
        dueDateTextView = view.findViewById(R.id.frag_add_goal_due_date);

        intervalSpinner = view.findViewById(R.id.frag_add_goal_interval_spinner);

        validationButton = view.findViewById(R.id.frag_add_goal_validation_button);
        cancelButton = view.findViewById(R.id.frag_add_goal_cancel_button);

        unitLayout = view.findViewById(R.id.frag_add_goal_unit_layout);
        quantityLayout = view.findViewById(R.id.frag_add_goal_quantity_layout);
        intervalNumberLayout = view.findViewById(R.id.frag_add_goal_interval_number_layout);


        // Set the interval spinner
        ArrayAdapter intervalAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, Interval.values());
        intervalAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);


        // Date picker
        dueDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueDateTextView.setText(dayOfMonth + "." + (month + 1) + "." + year);
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                picker.show();
            }
        });

        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean hasInputError = false;
                unit = unitTextInput.getText().toString();

                // Errors
                if (unit.isEmpty()) {
                    unitLayout.setError(getString(R.string.input_missing));
                    hasInputError = true;
                } else {
                    unitLayout.setError(null);
                }


                if (quantityTextInput.getText().toString().isEmpty()) {
                    quantityLayout.setError(getString(R.string.input_missing));
                    hasInputError = true;
                } else {
                    quantityLayout.setError(null);
                }

                if (intervalNumberTextInput.getText().toString().isEmpty()) {
                    intervalNumberLayout.setError(getString(R.string.input_missing));
                    hasInputError = true;
                } else {
                    intervalNumberLayout.setError(null);
                }

                if(mDay == 0 ||mMonth == 0 ||mYear == 0){
                    dueDateTextView.setError(getString(R.string.input_missing));
                    hasInputError = true;
                } else {
                    dueDateTextView.setError(null);
                }

                if (hasInputError) {
                    return;
                }

                quantity = Integer.parseInt(quantityTextInput.getText().toString());
                intervalNumber = Integer.parseInt(intervalNumberTextInput.getText().toString());

                Date selectedDate = new GregorianCalendar(mYear, mMonth, mDay).getTime();


                interval = Interval.valueOf(intervalSpinner.getSelectedItem().toString());
                Goal newGoal = new Goal(-1, unit, quantity, intervalNumber, interval, selectedDate, Calendar.getInstance().getTime());
                PostRequests.postGoal(newGoal,getContext());
                MainActivity.getUser().addGoal(newGoal);


                getActivity().findViewById(R.id.fab_container).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.dock).setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();


            }
        });

        // TODO Back to the view before add goal
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();

                getActivity().findViewById(R.id.fab_container).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.dock).setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

}
