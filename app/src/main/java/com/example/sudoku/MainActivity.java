package com.example.sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private GridLayout grid;

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

        createButton();
    }

    private void createButton() {
        for (int box = 0; box < 9; box++) {
            GridLayout subGrid = new GridLayout(this);
            subGrid.setColumnCount(3);
            subGrid.setRowCount(3);

            GridLayout.LayoutParams subGridParams = new GridLayout.LayoutParams();
            subGridParams.setMargins(8, 8, 8, 8);
            subGrid.setLayoutParams(subGridParams);

            for (int i = 0; i < 9; i++) {
                EditText editText = new EditText(this);
                editText.setText(String.valueOf(i + 1));
                editText.setId(View.generateViewId());
                editText.setBackgroundResource(R.drawable.bg_btn_square);
                GridLayout.LayoutParams btnParams = new GridLayout.LayoutParams();
                btnParams.width = 110;
                btnParams.height = 110;
                editText.setLayoutParams(btnParams);

                subGrid.addView(editText);
            }
            grid.addView(subGrid);
        }
    }
}
