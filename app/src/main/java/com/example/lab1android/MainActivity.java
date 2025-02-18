package com.example.lab1android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements InputFragment.OnInputListener, ResultFragment.OnCancelListener {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;
    private Database dbHelper;
    private Button openHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new Database(this);
        openHistoryButton = findViewById(R.id.openHistoryButton);

        if (savedInstanceState == null) {
            inputFragment = new InputFragment();
            resultFragment = new ResultFragment();

            inputFragment.setOnInputListener(this);
            resultFragment.setOnCancelListener(this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.inputContainer, inputFragment)
                    .add(R.id.resultContainer, resultFragment)
                    .commit();
        }

        openHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onInput(String productType, String manufacturer) {
        resultFragment.setResult(productType, manufacturer);

        long result = dbHelper.insertSelection(productType, manufacturer);

        String message = (result != -1) ?
                "Дані успішно збережено" :
                "Помилка при збереженні даних";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }


    @Override
    public void onCancel() {
        inputFragment.clearSelections();
        resultFragment.setResult("", "");
    }
}