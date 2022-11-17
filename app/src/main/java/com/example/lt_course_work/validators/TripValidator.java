package com.example.lt_course_work.validators;

import android.widget.EditText;
import android.widget.Switch;
import com.example.lt_course_work.databinding.FragmentCreateTripBinding;
import com.example.lt_course_work.databinding.FragmentDetailsTripBinding;

public class TripValidator
{
    public static boolean ValidateCreateTrip(FragmentCreateTripBinding binding)
    {
        boolean isValid = true;
        if(binding.editTextTripName.getText().toString().isEmpty())
        {
            binding.editTextTripName.setError("The trip name is required");
            isValid = false;
        }
        if(binding.editTextTripDestination.getText().toString().isEmpty())
        {
            binding.editTextTripDestination.setError("The destination  is required");
            isValid = false;
        }
        if(binding.editTextDateStartDate.getText().toString().isEmpty())
        {
            binding.editTextDateStartDate.setError("The start date is required");
            isValid = false;
        }
        if(binding.isRequireRiskAssessment.getText().toString().isEmpty())
        {
            binding.editTextTripName.setError("The risk assessment is required");
            isValid = false;
        }
        return isValid;
    }

    public static boolean ValidateUpdateTrip(FragmentDetailsTripBinding binding)
    {
        boolean isValid = true;
        if(binding.editTextTripName.getText().toString().isEmpty())
        {
            binding.editTextTripName.setError("The trip name is required");
            isValid = false;
        }
        if(binding.editTextTripDestination.getText().toString().isEmpty())
        {
            binding.editTextTripDestination.setError("The destination  is required");
            isValid = false;
        }
        if(binding.editTextDateStartDate.getText().toString().isEmpty())
        {
            binding.editTextDateStartDate.setError("The start date is required");
            isValid = false;
        }
        if(binding.isRequireRiskAssessment.getText().toString().isEmpty())
        {
            binding.editTextTripName.setError("The risk assessment is required");
            isValid = false;
        }
        return isValid;
    }
}
