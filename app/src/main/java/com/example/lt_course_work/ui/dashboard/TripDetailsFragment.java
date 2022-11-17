package com.example.lt_course_work.ui.dashboard;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.lt_course_work.MainActivity;
import com.example.lt_course_work.R;
import com.example.lt_course_work.adapters.TripListViewAdapter;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.database.AppDatabase;
import com.example.lt_course_work.databinding.FragmentDetailsTripBinding;
import com.example.lt_course_work.models.Trip;
import com.example.lt_course_work.validators.TripValidator;
import ultis.DateTimeHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class TripDetailsFragment extends Fragment {

    private FragmentDetailsTripBinding binding;
    View root;
    EditText editTextTripName, editTextTripDestination, editTextStartDate, editTextEndDate, editTextAmount, editTextType, editTextExpenseTime;
    Button saveButton, deleteButton;
    Switch switchIsRequireAssessment, switchIsEnableEdit;
    TripListViewAdapter tripListViewAdapter;

    Trip trip;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailsTripBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        tripListViewAdapter = ((MainActivity)getActivity()).getTripAdapter();

        AppDatabase db =  ((MainActivity)getActivity()).getDbContext();

        TripDao tripDao = db.tripDao();

        editTextStartDate = binding.editTextDateStartDate;
        editTextEndDate = binding.editTextDateEndDate;
        editTextTripName = binding.editTextTripName;
        editTextTripDestination = binding.editTextTripDestination;
        editTextAmount = binding.editTextExpenseAmount;
        editTextType = binding.editTextExpenseType;
        editTextExpenseTime = binding.editTextExpenseTime;
        switchIsRequireAssessment = binding.isRequireRiskAssessment;
        switchIsEnableEdit = binding.switchEnableEdit;
        saveButton = binding.buttonSaveTrip;
        deleteButton = binding.buttonDeleteTrip;



        long tripId = getArguments().getLong("tripId");
        trip = tripDao.findById(tripId);


        setEditTextValues();
        handleSwitchEnableEdit();


        editTextStartDate.setOnClickListener(view -> onEditTextDateClick(view, editTextStartDate)
        );

        editTextEndDate.setOnClickListener(view -> onEditTextDateClick(view, editTextEndDate)
        );

        editTextExpenseTime.setOnClickListener(view -> onEditTextDateClick(view, editTextExpenseTime)
        );

        validateRequireEditText(editTextTripName);

        validateRequireEditText(editTextTripDestination);

        validateRequireEditText(editTextStartDate);

        validateRequireEditText(editTextEndDate);

        switchIsEnableEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {

                handleSwitchEnableEdit();
                if(switchIsEnableEdit.isChecked() == false)
                {
                    setEditTextValues();
                };
            }
        });


        saveButton.setOnClickListener(view -> {
            Date startDate = null, endDate = null, expenseDate;
            String tripName = editTextTripName.getText().toString();
            String tripDestination = editTextTripDestination.getText().toString();
            Boolean isRequireRiskAssessment = switchIsRequireAssessment.isChecked();
            Boolean isValidInput = true;
            String tripType = editTextType.getText().toString();
            Double expenseAmount =!editTextAmount.getText().toString().isEmpty() ? Double.parseDouble(editTextAmount.getText().toString()) : null;
            startDate = DateTimeHelper.StringToDateConverter(editTextStartDate.getText().toString());
            endDate = DateTimeHelper.StringToDateConverter(editTextEndDate.getText().toString());
            expenseDate = DateTimeHelper.StringToDateConverter(editTextExpenseTime.getText().toString());



            isValidInput = TripValidator.ValidateUpdateTrip(binding);


            if(isValidInput)
            {
                Trip editedTrip = new Trip(tripName, tripDestination, startDate, endDate, isRequireRiskAssessment, tripType, expenseAmount, expenseDate);
                editedTrip.setId(trip.getId());
                int result = tripDao.updateUser(editedTrip);
                if(result != 0)
                {
                    tripListViewAdapter.tripArrayList.add(0, editedTrip);
                    tripListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(root.getContext(), "Updated trip", Toast.LENGTH_LONG);

                    NavController navController = Navigation.findNavController((MainActivity)(getActivity()), R.id.nav_host_fragment_activity_main);
                    navController.popBackStack();
                    navController.navigate(R.id.navigation_home);
                }
                else
                {
                    Log.d(TAG, "onCreateView: ");
                    saveButton.setError("Failed to create trip");
                }

            }


        });

        deleteButton.setOnClickListener(view -> {
            try
            {
                tripDao.deleteById(tripId);
                NavController navController = Navigation.findNavController((MainActivity)(getActivity()), R.id.nav_host_fragment_activity_main);
                navController.popBackStack();
                navController.navigate(R.id.navigation_home);
            }
            catch (Exception e)
            {
                Log.d(TAG, e.getMessage().toString());
            }

        });
        return root;


    }

    private void validateRequireEditText(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean isValid = isValidRequireEditText(editText.getText().toString());
                if(!isValid)
                {
                    editText.setError("The name is required");
                }
                else editText.setError(null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean isValidRequireEditText(String editTextInput)
    {
        return editTextInput.isEmpty() ? false : true;
    }

    public void onEditTextDateClick(View view, EditText editText) {
        // Get Current Date
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(),
                (view1, year, monthOfYear, dayOfMonth) -> editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void handleSwitchEnableEdit()
    {
        if(switchIsEnableEdit.isChecked())
        {
            switchEditTextStatus(editTextStartDate, true);
            editTextStartDate.setFocusableInTouchMode(false);
            switchEditTextStatus(editTextEndDate, true);
            editTextEndDate.setFocusableInTouchMode(false);
            editTextExpenseTime.setFocusableInTouchMode(false);


            switchEditTextStatus(editTextTripName, true);
            switchEditTextStatus(editTextTripDestination, true);
            switchIsRequireAssessment.setClickable(true);
            switchEditTextStatus(editTextType, true);
            switchEditTextStatus(editTextAmount, true);

            if(trip.getExpenseType().isEmpty()) {
                switchEditTextStatus(editTextExpenseTime, false);
            }
            else
            {
                editTextExpenseTime.setText(DateTimeHelper.DateToStringConverter(new Date()));

            }

            saveButton.setEnabled(true);

        }
        else
        {
            switchEditTextStatus(editTextStartDate, false);
            switchEditTextStatus(editTextEndDate, false);
            switchEditTextStatus(editTextTripName, false);
            switchEditTextStatus(editTextTripDestination, false);
            switchIsRequireAssessment.setClickable(false);
            switchEditTextStatus(editTextType, false);
            switchEditTextStatus(editTextAmount, false);
            switchEditTextStatus(editTextExpenseTime, false);

            saveButton.setEnabled(false);
        }
    }

    private void switchEditTextStatus(EditText editText, boolean isEnable)
    {
        editText.setFocusableInTouchMode(isEnable);
        editText.setFocusable(isEnable);
        editText.setClickable(isEnable);
        editText.setCursorVisible(isEnable);
    }

    private void setEditTextValues()
    {
        editTextTripName.setText(trip.getName());
        editTextTripDestination.setText(trip.getDestination());
        editTextStartDate.setText(DateTimeHelper.DateToStringConverter(trip.getStartDate()));
        editTextEndDate.setText(DateTimeHelper.DateToStringConverter(trip.getEndDate()));
        switchIsRequireAssessment.setChecked(trip.isRequiredRiskAssessment());
        editTextType.setText(trip.getExpenseType());
        if (trip.getExpenseAmount() == null) {
            editTextAmount.setText(null);
        } else {
            editTextAmount.setText(String.valueOf(trip.getExpenseAmount()));
        }
        editTextExpenseTime.setText(DateTimeHelper.DateToStringConverter(trip.getExpenseTime()));

    }
}
