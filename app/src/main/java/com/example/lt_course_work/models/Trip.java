package com.example.lt_course_work.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Date;


@Entity(tableName = "trips")
public class Trip {

    public Trip(String name, String destination, Date startDate, Date endDate, boolean isRequiredRiskAssessment, String expenseType, Double expenseAmount, Date expenseTime) {
        this.name = name;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRequiredRiskAssessment = isRequiredRiskAssessment;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseTime = expenseTime;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "destination")
    private String destination;

    @ColumnInfo(name = "startDate")
    private Date startDate;

    @ColumnInfo(name = "endDate")
    private Date endDate;

    @ColumnInfo(name = "isRequiredRiskAssessment")
    private boolean isRequiredRiskAssessment;

    @ColumnInfo(name = "expenseType")
    private String expenseType;
    @ColumnInfo(name = "expenseAmount")
    private Double expenseAmount;
    @ColumnInfo(name = "expenseTime")
    private Date expenseTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRequiredRiskAssessment() {
        return isRequiredRiskAssessment;
    }

    public void setRequiredRiskAssessment(boolean requiredRiskAssessment) {
        isRequiredRiskAssessment = requiredRiskAssessment;
    }

    public Date getExpenseTime() {
        return expenseTime;
    }

    public void setExpenseTime(Date expenseTime) {
        this.expenseTime = expenseTime;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }


}
