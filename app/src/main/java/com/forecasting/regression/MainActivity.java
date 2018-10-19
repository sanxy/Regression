package com.forecasting.regression;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.file_maintenance)
    Button fileMaintenance;
    @BindView(R.id.forecast_technique)
    Button forecastTechnique;
    @BindView(R.id.report_generation)
    Button reportGeneration;
    @BindView(R.id.help)
    Button help;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 11;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fileMaintenance.setOnClickListener(this);
        forecastTechnique.setOnClickListener(this);
        reportGeneration.setOnClickListener(this);
        help.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.file_maintenance:
                // Create a new intent to start an AddTaskActivity
                Intent maintenance = new Intent(MainActivity.this, ListActivity.class);
                startActivity(maintenance);
                break;

            case R.id.forecast_technique:
                Intent forecast = new Intent(this, ForecastActivity.class);
                startActivity(forecast);
                break;

            case R.id.report_generation:
                verifyStoragePermissions();
                break;

            case R.id.help:
                // Create a new intent to start an AddTaskActivity
                Intent help = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(help);
                break;
        }
    }

    private void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }else{
            openReport();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_EXTERNAL_STORAGE) {
                openReport();
            }
        }
    }

    private void openReport() {
        Intent report = new Intent(this, ReportGenerationActivity.class);
        startActivity(report);
    }
}
