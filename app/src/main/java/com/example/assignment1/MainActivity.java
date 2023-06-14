package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText unitsEditText;
    private EditText rebateEditText;
    private Button calculateButton;
    private Button clearButton;
    private TextView resultTextView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            // Handle the About menu item click
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitsEditText = findViewById(R.id.unitsEditText);
        rebateEditText = findViewById(R.id.rebateEditText);
        calculateButton = findViewById(R.id.calculateButton);
        clearButton = findViewById(R.id.clearButton);
        resultTextView = findViewById(R.id.resultTextView);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInput();
            }
        });
    }

    private void calculateBill() {
        String unitsString = unitsEditText.getText().toString();
        String rebateString = rebateEditText.getText().toString();

        if (unitsString.isEmpty() || rebateString.isEmpty()) {
            resultTextView.setText("Please enter both units and rebate percentage.");
            return;
        }

        double units = Double.parseDouble(unitsString);
        double rebate = Double.parseDouble(rebateString);

        if (rebate >= 0.06){
            resultTextView.setText("Rebate is not more than 5%.");
            return;
        }

        double bill = 0;
        if (units <= 200) {
            bill = units * 0.218;
        } else if (units <= 300) {
            bill = (200 * 0.218) + ((units - 200) * 0.334);
        } else if (units <= 600) {
            bill = (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516);
        } else if (units > 600){
            bill = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546);
        }

        double finalCost = bill - (bill * rebate);
        resultTextView.setText("Final Bill: MYR " + String.format("%.2f", finalCost));
    }

    private void clearInput() {
        unitsEditText.setText("");
        rebateEditText.setText("");
        resultTextView.setText("");
    }
}
