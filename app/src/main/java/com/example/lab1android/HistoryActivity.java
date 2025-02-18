package com.example.lab1android;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    private Database dbHelper;
    private ListView listView;
    private TextView emptyView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new Database(this);
        listView = findViewById(R.id.listView);
        emptyView = findViewById(R.id.emptyView);

        registerForContextMenu(listView);

        loadHistory();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Видалити");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 1) {
            showDeleteConfirmationDialog(info.id);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteConfirmationDialog(final long id) {
        new AlertDialog.Builder(this)
                .setTitle("Підтвердження видалення")
                .setMessage("Ви впевнені, що хочете видалити цей запис?")
                .setPositiveButton("Так", (dialog, which) -> {
                    deleteRecord(id);
                })
                .setNegativeButton("Ні", null)
                .show();
    }

    private void deleteRecord(long id) {
        dbHelper.deleteSelection(id);
        Toast.makeText(this, "Запис видалено", Toast.LENGTH_SHORT).show();
        loadHistory();
    }

    private void loadHistory() {
        Cursor cursor = dbHelper.getAllSelections();

        if (cursor.getCount() == 0) {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            return;
        }

        listView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        String[] fromColumns = {
                Database.COLUMN_PRODUCT_TYPE,
                Database.COLUMN_MANUFACTURER,
                Database.COLUMN_TIMESTAMP
        };

        int[] toViews = {
                R.id.textProductType,
                R.id.textManufacturer,
                R.id.textTimestamp
        };

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}