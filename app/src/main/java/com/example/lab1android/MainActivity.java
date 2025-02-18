package com.example.lab1android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {
    private RadioGroup productTypeGroup;
    private RadioGroup manufacturerGroup;
    private TextView resultText;
    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація елементів інтерфейсу
        productTypeGroup = findViewById(R.id.productTypeGroup);
        manufacturerGroup = findViewById(R.id.manufacturerGroup);
        resultText = findViewById(R.id.resultText);
        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Обробник для кнопки OK
        okButton.setOnClickListener(v -> {
            if (productTypeGroup.getCheckedRadioButtonId() == -1 ||
                    manufacturerGroup.getCheckedRadioButtonId() == -1) {
                // Показуємо повідомлення якщо не всі дані вибрані
                showAlert();
                return;
            }

            // Отримуємо вибрані значення
            String productType = getSelectedProductType();
            String manufacturer = getSelectedManufacturer();

            // Відображаємо результат
            String result = String.format("Вибрано: %s від компанії %s",
                    productType, manufacturer);
            resultText.setText(result);
        });

        // Обробник для кнопки Cancel
        cancelButton.setOnClickListener(v -> {
            // Очищаємо всі вибрані значення
            productTypeGroup.clearCheck();
            manufacturerGroup.clearCheck();
            resultText.setText("");
        });
    }

    private void showAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Увага")
                .setMessage("Будь ласка, виберіть тип товару та виробника")
                .setPositiveButton("OK", null)
                .show();
    }

    private String getSelectedProductType() {
        int selectedId = productTypeGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.productType1) return "Смартфон";
        if (selectedId == R.id.productType2) return "Ноутбук";
        if (selectedId == R.id.productType3) return "Планшет";
        return "";
    }

    private String getSelectedManufacturer() {
        int selectedId = manufacturerGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.manufacturer1) return "Apple";
        if (selectedId == R.id.manufacturer2) return "Samsung";
        if (selectedId == R.id.manufacturer3) return "Lenovo";
        return "";
    }
}