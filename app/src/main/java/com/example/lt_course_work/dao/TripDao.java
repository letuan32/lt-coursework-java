package com.example.lt_course_work.dao;

import androidx.room.*;
import com.example.lt_course_work.models.Trip;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TripDao {
    @Query("SELECT * FROM trips")
    List<Trip> getAll();

    @Query("SELECT * FROM trips WHERE id IN (:TripIds)")
    List<Trip> loadAllByIds(int[] TripIds);

    @Query("SELECT * FROM trips WHERE name LIKE '%' || :name || '%' ")
    List<Trip> findByName(String name);

    @Insert
    void insertAll(Trip... Trips);

    @Insert(onConflict = REPLACE)
    long insert(Trip trip);

    @Query("SELECT * FROM trips WHERE id =:id")
    Trip findById(long id);

    @Query("DELETE FROM trips")
    void deleteAll();

    @Query("DELETE FROM trips WHERE id=:id")
    void deleteById(long id);

    @Update
    public int updateUser(Trip trip);

}
