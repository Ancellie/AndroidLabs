package com.example.lab1android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;

public class InputFragment extends Fragment {
    private RadioGroup productTypeGroup;
    private RadioGroup manufacturerGroup;
    private Button okButton;
    private OnInputListener listener;

    public interface OnInputListener {
        void onInput(String productType, String manufacturer);
    }

    public void setOnInputListener(OnInputListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        productTypeGroup = view.findViewById(R.id.productTypeGroup);
        manufacturerGroup = view.findViewById(R.id.manufacturerGroup);
        okButton = view.findViewById(R.id.okButton);

        okButton.setOnClickListener(v -> {
            if (productTypeGroup.getCheckedRadioButtonId() == -1 ||
                    manufacturerGroup.getCheckedRadioButtonId() == -1) {
                showAlert();
                return;
            }

            String productType = getSelectedProductType();
            String manufacturer = getSelectedManufacturer();

            if (listener != null) {
                listener.onInput(productType, manufacturer);
            }
        });

        return view;
    }

    public void clearSelections() {
        if (productTypeGroup != null) productTypeGroup.clearCheck();
        if (manufacturerGroup != null) manufacturerGroup.clearCheck();
    }

    private void showAlert() {
        new AlertDialog.Builder(requireContext())
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