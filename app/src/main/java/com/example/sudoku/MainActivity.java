package com.example.sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private GridLayout grid;
    private TextView title;
    private Spinner spinner;

    private int size = 2;
    private int max = 4;
    private String textTitle = "Sudoku 2x2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.myGridLayout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ini();

        String[] type = getResources().getStringArray(R.array.type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                type
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    size = 2;
                    max = 4;
                    textTitle = "Sudoku 2x2";
                } else if (position == 1) {
                    size = 3;
                    max = 9;
                    textTitle = "Sudoku 3x3";
                }
                createButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void createButton() {
        grid.removeAllViews();
        grid.setColumnCount(size);
        grid.setRowCount(size);
        title.setText(textTitle);

        for (int box = 0; box < max; box++) {
            GridLayout subGrid = new GridLayout(this);
            subGrid.setColumnCount(size);
            subGrid.setRowCount(size);

            GridLayout.LayoutParams subGridParams = new GridLayout.LayoutParams();
            subGridParams.setMargins(2, 2, 2, 2);
            subGrid.setLayoutParams(subGridParams);

            for (int i = 0; i < max; i++) {
                EditText editText = new EditText(this);
                editText.setText(String.valueOf(i + 1));
                editText.setId(View.generateViewId());
                editText.setBackgroundResource(R.drawable.bg_btn_square);
                GridLayout.LayoutParams btnParams = new GridLayout.LayoutParams();
                btnParams.setMargins(2,2,2,2);
                btnParams.width = 110;
                btnParams.height = 110;
                editText.setLayoutParams(btnParams);

                subGrid.addView(editText);
            }
            grid.addView(subGrid);
        }
    }

    private void ini(){
        title = findViewById(R.id.tvTittle);
        spinner = findViewById(R.id.spinner);
    }
}
