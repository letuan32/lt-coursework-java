package com.example.lt_course_work;

import android.os.Bundle;
import android.widget.ListView;
import androidx.room.Room;
import com.example.lt_course_work.adapters.TripListViewAdapter;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.database.AppDatabase;
import com.example.lt_course_work.databinding.ActivityMainBinding;
import com.example.lt_course_work.models.Trip;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ultis.DateTimeHelper;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Trip> listTrip = new ArrayList<>();
    TripListViewAdapter tripListViewAdapter;
    ListView listViewTrip;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "trips-coursework").allowMainThreadQueries().build();
        // Clear all data
        TripDao tripDao = db.tripDao();
        tripDao.deleteAll();

        tripDao.insertAll(
                new Trip("Hoi An", "Destination 1", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), false,"Type 1", null, null),
                new Trip("Da Nang", "Destination 2", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), false,"Type 2", null, null),
                new Trip("Quang Binh", "Destination 3", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), false,"Type 3", 1000.0, DateTimeHelper.StringToDateConverter("2022-12-20")),
                new Trip("Singapore", "Destination 4", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), false,"Type 4", null, null),
                new Trip("US", "Destination 5", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), false,"Type 5", 599.0, null),
                new Trip("Greenwich", "Destination 6", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), true, "Type 6", null, null),
                new Trip("Name 7", "Destination 7", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), true, "Type 7", 989.0, null),
                new Trip("Name 8", "Destination 8", DateTimeHelper.StringToDateConverter("2022-12-16"), DateTimeHelper.StringToDateConverter("2022-12-17"), true, "Type 8", null, null)
        );

        // Create list view adapter instance
        listTrip.addAll(tripDao.getAll());
        tripListViewAdapter = new TripListViewAdapter(listTrip, getDbContext());


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public TripListViewAdapter getTripAdapter()
    {
        return tripListViewAdapter;
    }

    public AppDatabase getDbContext()
    {
        return db;
    }





}
