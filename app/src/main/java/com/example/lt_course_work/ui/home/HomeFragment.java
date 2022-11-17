package com.example.lt_course_work.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.example.lt_course_work.MainActivity;
import com.example.lt_course_work.R;
import com.example.lt_course_work.adapters.TripListViewAdapter;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.database.AppDatabase;
import com.example.lt_course_work.databinding.FragmentHomeBinding;
import com.example.lt_course_work.models.Trip;
import com.jakewharton.rxbinding4.widget.RxTextView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    TripListViewAdapter tripListViewAdapter;
    ListView listViewTrip;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        AppDatabase db =  ((MainActivity)getActivity()).getDbContext();
        TripDao tripDao = db.tripDao();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tripListViewAdapter = ((MainActivity)getActivity()).getTripAdapter();
        listViewTrip = root.findViewById(R.id.listview_trip);
        listViewTrip.setAdapter(tripListViewAdapter);
        EditText editText_search = binding.editTextSearch;
        editText_search.setText(null);


        RxTextView.textChanges(editText_search)
                .filter(s -> s.length() > 2)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()))
                .subscribe(textChanged -> {
                    List<Trip> searchResults = tripDao.findByName(editText_search.getText().toString());
                    tripListViewAdapter.tripArrayList = (ArrayList<Trip>) searchResults;
                    tripListViewAdapter.notifyDataSetChanged();
                });

        RxTextView.textChanges(editText_search)
                .filter(s -> s.length() > 3)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()))
                .subscribe(textChanged -> {
                    List<Trip> searchResults = tripDao.findByName(editText_search.getText().toString());
                    tripListViewAdapter.tripArrayList = (ArrayList<Trip>) searchResults;
                    tripListViewAdapter.notifyDataSetChanged();
                });

        RxTextView.textChanges(editText_search)
                .filter(s -> !(s.length() > 3))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()))
                .subscribe(textChanged -> {
                    List<Trip> searchResults = tripDao.getAll();
                    tripListViewAdapter.tripArrayList = (ArrayList<Trip>) searchResults;
                    tripListViewAdapter.notifyDataSetChanged();
                });

        listViewTrip.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long tripId = tripListViewAdapter.getItemId(i);
                Bundle bundle = new Bundle();
                bundle.putLong("tripId", tripId);

                NavController navController = Navigation.findNavController((MainActivity)(getActivity()), R.id.nav_host_fragment_activity_main);
                navController.popBackStack();
                navController.navigate(R.id.navigation_tripDetails, bundle);
            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}