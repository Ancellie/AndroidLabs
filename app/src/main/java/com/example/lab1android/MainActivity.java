package com.example.lab1android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements InputFragment.OnInputListener, ResultFragment.OnCancelListener {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onInput(String productType, String manufacturer) {
        resultFragment.setResult(productType, manufacturer);
    }

    @Override
    public void onCancel() {
        inputFragment.clearSelections();
        resultFragment.setResult("", "");
    }
}