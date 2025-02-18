package com.example.lab1android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class ResultFragment extends Fragment {
    private TextView resultText;
    private Button cancelButton;
    private OnCancelListener listener;

    public interface OnCancelListener {
        void onCancel();
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        resultText = view.findViewById(R.id.resultText);
        cancelButton = view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancel();
            }
        });

        return view;
    }

    public void setResult(String productType, String manufacturer) {
        String result = "Нічого не вибрано";
        if (!Objects.equals(productType, "") && !Objects.equals(manufacturer, "")){
            result = String.format("Вибрано: %s від компанії %s", productType, manufacturer);
        }
        resultText.setText(result);
    }
}