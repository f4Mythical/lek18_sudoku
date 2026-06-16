package com.example.sudoku;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GridLayout grid;
    private TextView title;
    private Spinner spinner;

    private int size = 2;
    private int max = 4;
    private String textTitle = "Sudoku 2x2";

    private int boardIndex = 0;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.myGridLayout);
        ini();

        String[] type = getResources().getStringArray(R.array.type);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boardIndex = random.nextInt(3);
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void createButton() {
        grid.removeAllViews();
        grid.setColumnCount(size);
        grid.setRowCount(size);
        title.setText(textTitle);

        int[][] rozwiazanie;
        if (size == 2) {
            rozwiazanie = SudokuAnswer.ANSWERS_2X2[boardIndex];
        } else {
            rozwiazanie = SudokuAnswer.ANSWERS_3X3[boardIndex];
        }

        int cellCounter = 0;

        for (int box = 0; box < max; box++) {
            GridLayout subGrid = new GridLayout(this);
            subGrid.setColumnCount(size);
            subGrid.setRowCount(size);

            GridLayout.LayoutParams subGridParams = new GridLayout.LayoutParams();
            subGridParams.setMargins(8, 8, 8, 8);
            subGrid.setLayoutParams(subGridParams);

            for (int i = 0; i < max; i++) {
                EditText editText = new EditText(this);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                editText.setBackgroundResource(R.drawable.bg_btn_square);

                GridLayout.LayoutParams btnParams = new GridLayout.LayoutParams();
                btnParams.setMargins(2, 2, 2, 2);
                btnParams.width = 110;
                btnParams.height = 110;
                editText.setLayoutParams(btnParams);

                int r = cellCounter / max;
                int c = cellCounter % max;
                int poprawnePoprawne = rozwiazanie[r][c];
                cellCounter++;

                editText.setTag(poprawnePoprawne);

                if (random.nextInt(100) < 40) {
                    editText.setText(String.valueOf(poprawnePoprawne));
                    editText.setEnabled(false);
                } else {
                    editText.setText("");
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            String wpisane = s.toString();

                            if (wpisane.isEmpty()) {
                                editText.setBackgroundResource(R.drawable.bg_btn_square);
                            } else {
                                String poprawnyTekst = String.valueOf(editText.getTag());
                                if (wpisane.equals(poprawnyTekst)) {
                                    editText.setBackgroundResource(R.drawable.bg_btn_square);
                                } else {
                                    editText.setBackgroundResource(R.drawable.bg_btn_wrong);
                                }
                            }
                        }
                    });
                }
                subGrid.addView(editText);
            }
            grid.addView(subGrid);
        }
    }

    private void ini() {
        title = findViewById(R.id.tvTittle);
        spinner = findViewById(R.id.spinner);
    }
}
