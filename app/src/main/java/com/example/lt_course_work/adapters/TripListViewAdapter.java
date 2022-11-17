package com.example.lt_course_work.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.lt_course_work.MainActivity;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.database.AppDatabase;
import com.example.lt_course_work.models.Trip;
import com.example.lt_course_work.R;
import ultis.DateTimeHelper;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TripListViewAdapter extends BaseAdapter {

    public ArrayList<Trip> tripArrayList;
    AppDatabase appDatabase;

    public TripListViewAdapter(ArrayList<Trip> tripArrayList, AppDatabase appDatabase)
    {
        this.tripArrayList = tripArrayList;
        this.appDatabase = appDatabase;
    }

    @Override
    public int getCount() {
        return tripArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return tripArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tripArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewTrip;
        TripDao tripDao = appDatabase.tripDao();
        if(view == null)
        {
            viewTrip = View.inflate(viewGroup.getContext(), R.layout.trip_view, null);
        }
        else viewTrip = view;

        Trip trip = (Trip) getItem(i);
        ((TextView) viewTrip.findViewById(R.id.trip_id)).setText(String.format("ID = %d", trip.getId()));
        ((TextView) viewTrip.findViewById(R.id.trip_name)).setText(String.format("Trip Name : %s", trip.getName()));
        ((TextView) viewTrip.findViewById(R.id.trip_destination)).setText(String.format("Destination %s", trip.getDestination()));
        ((TextView) viewTrip.findViewById(R.id.start_date)).setText(String.format("Start date %s", DateTimeHelper.DateToStringConverter(trip.getStartDate())));
        ((TextView) viewTrip.findViewById(R.id.end_date)).setText(String.format("End date %s", DateTimeHelper.DateToStringConverter(trip.getStartDate())));
        ((TextView) viewTrip.findViewById(R.id.is_require_risk_assessment)).setText(String.format("Require Risk Assessment %s", trip.isRequiredRiskAssessment()));
        ImageButton button_Delete = (ImageButton) viewTrip.findViewById(R.id.button_delete);
        button_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    tripDao.deleteById(getItemId(i));
                    tripArrayList.remove(getItem(i));
                    notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    Log.d(TAG, e.getMessage().toString());
                }
            }
        });
        return viewTrip;
    }
}
