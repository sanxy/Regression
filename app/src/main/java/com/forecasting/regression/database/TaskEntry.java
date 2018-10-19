package com.forecasting.regression.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameTask;
    private String addressTask;
    private String patternTask;
    private String periodTask;
    private String totNoObsTask;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public TaskEntry(String nameTask, String addressTask, String patternTask, String periodTask, String totNoObsTask, Date updatedAt) {
        this.nameTask = nameTask;
        this.addressTask = addressTask;
        this.patternTask = patternTask;
        this.periodTask = periodTask;
        this.totNoObsTask = totNoObsTask;
        this.updatedAt = updatedAt;
    }

    public TaskEntry(int id, String nameTask, String addressTask, String patternTask, String periodTask, String totNoObsTask, Date updatedAt) {
        this.id = id;
        this.nameTask = nameTask;
        this.addressTask = addressTask;
        this.patternTask = patternTask;
        this.periodTask = periodTask;
        this.totNoObsTask = totNoObsTask;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getAddressTask() {
        return addressTask;
    }

    public void setAddressTask(String addressTask) {
        this.addressTask = addressTask;
    }

    public String getPatternTask() {
        return patternTask;
    }

    public void setPatternTask(String patternTask) {
        this.patternTask = patternTask;
    }

    public String getPeriodTask() {
        return periodTask;
    }

    public void setPeriodTask(String periodTask) {
        this.periodTask = periodTask;
    }

    public String getTotNoObsTask() {
        return totNoObsTask;
    }

    public void setTotNoObsTask(String totNoObsTask) {
        this.totNoObsTask = totNoObsTask;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
