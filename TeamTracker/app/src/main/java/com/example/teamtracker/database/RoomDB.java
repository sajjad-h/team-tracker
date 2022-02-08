package com.example.teamtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.teamtracker.database.dao.ProjectDao;
import com.example.teamtracker.database.dao.TaskDao;
import com.example.teamtracker.models.Project;
import com.example.teamtracker.models.Task;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    private static RoomDB database;
    private static String DATABASE_NAME ="team_tracker_app";

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();
}
