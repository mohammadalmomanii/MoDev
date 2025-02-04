package com.mohammadalmomani.modev;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mohammadalmomani.modev.databinding.ActivityMainBinding;
import com.mohammadalmomani.modevlib.support.AppHelper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppHelper.delay(() -> binding.refreshView.show(true), 4000);
        AppHelper.delay(() -> binding.refreshView.show(false), 8000);

        binding.asas.setOnClickListener(v -> {
            Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
        });


    }

}