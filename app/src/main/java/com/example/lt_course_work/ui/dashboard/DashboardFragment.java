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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.lt_course_work.MainActivity;
import com.example.lt_course_work.R;
import com.example.lt_course_work.adapters.TripListViewAdapter;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.database.AppDatabase;
import com.example.lt_course_work.databinding.FragmentCreateTripBinding;
import com.example.lt_course_work.models.Trip;
import com.example.lt_course_work.validators.TripValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {

    private FragmentCreateTripBinding binding;
    View root;
    EditText editTextTripName, editTextTripDestination, editTextStartDate, editTextEndDate;
    Button saveButton;
    Switch switchIsRequireAssessment;
    TripListViewAdapter tripListViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentCreateTripBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        tripListViewAdapter = ((MainActivity)getActivity()).getTripAdapter();


        AppDatabase db =  ((MainActivity)getActivity()).getDbContext();
        TripDao tripDao = db.tripDao();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        editTextStartDate = binding.editTextDateStartDate;
        editTextEndDate = binding.editTextDateEndDate;
        editTextTripName = binding.editTextTripName;
        editTextTripDestination = binding.editTextTripDestination;
        switchIsRequireAssessment = binding.isRequireRiskAssessment;
        saveButton = binding.buttonCreateTrip;


        editTextStartDate.setOnClickListener(view -> onEditTextDateClick(view, editTextStartDate)
        );

        editTextEndDate.setOnClickListener(view -> onEditTextDateClick(view, editTextEndDate)
        );

        validateRequireEditText(editTextTripName);

        validateRequireEditText(editTextTripDestination);

        validateRequireEditText(editTextStartDate);

        validateRequireEditText(editTextEndDate);


        saveButton.setOnClickListener(view -> {
            Date startDate = null, endDate = null;
            String tripName = editTextTripName.getText().toString();
            String tripDestination = editTextTripDestination.getText().toString();
            Boolean isRequireRiskAssessment = switchIsRequireAssessment.isChecked();
            Boolean isValidInput = true;

            isValidInput = TripValidator.ValidateCreateTrip(binding);
            // TODO: Extract method
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            try {
                startDate = dateFormat.parse(editTextStartDate.getText().toString());
            } catch (ParseException e) {
                isValidInput = false;
                editTextStartDate.setError("Invalid date format");
            }
            try {
                endDate = dateFormat.parse(editTextEndDate.getText().toString());
            } catch (ParseException e) {
                isValidInput = false;
                editTextEndDate.setError("Invalid date format");
            }

            if(isValidInput)
            {
                // TODO: Database access

                Trip trip = new Trip(tripName, tripDestination, startDate, endDate, isRequireRiskAssessment, null, null, null);
                long tripId = tripDao.insert(trip);

                if(tripId != 0)
                {
                    tripListViewAdapter.tripArrayList.add(0, trip);
                    tripListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(root.getContext(), "Created trip", Toast.LENGTH_LONG);

                    NavController navController = Navigation.findNavController((MainActivity)(getActivity()), R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.navigation_home);
                }
                else
                {
                    Log.d(TAG, "onCreateView: ");
                    saveButton.setError("Failed to create trip");
                }

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
}
