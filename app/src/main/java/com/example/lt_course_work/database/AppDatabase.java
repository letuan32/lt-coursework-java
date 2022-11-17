package com.example.lt_course_work.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.lt_course_work.dao.Converters;
import com.example.lt_course_work.dao.TripDao;
import com.example.lt_course_work.models.Trip;

@Database(entities = {Trip.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
}