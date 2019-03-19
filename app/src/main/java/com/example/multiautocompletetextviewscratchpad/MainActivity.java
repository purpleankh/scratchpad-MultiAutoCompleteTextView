package com.example.multiautocompletetextviewscratchpad;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static {
        ArrayList<Locale> list = new ArrayList<>();
        for (String isoCountry : Locale.getISOCountries()) {
            list.add(new Locale.Builder().setRegion(isoCountry).build());
        }
        List<String> countriesList = new ArrayList<>();
        Locale myLocale = Locale.getDefault();
        for (Locale locale : list) {
            countriesList.add(locale.getDisplayCountry(myLocale));
        }
        String[] countries = countriesList.toArray(new String[0]);
        Arrays.sort(countries);
        COUNTRIES = countries;
    }

    private static final String[] COUNTRIES;

    private MultiAutoCompleteTextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Buggy layout
        setContentView(R.layout.activity_main);
        // Bodged layout
//        setContentView(R.layout.activity_main_bodge);

        editText = findViewById(R.id.mactv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        editText.setAdapter(adapter);
        editText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Set a simple 'everything-is-valid' validator
        editText.setValidator(new AutoCompleteTextView.Validator() {
            @Override
            public boolean isValid(CharSequence text) {
                Log.d(TAG, "isValid() called with: text = [" + text + "]");
                // Everything is valid
                return true;
            }

            @Override
            public CharSequence fixText(CharSequence invalidText) {
                Log.d(TAG, "fixText() called with: invalidText = [" + invalidText + "]");
                return invalidText;
            }
        });
    }

    public void showInput(View v) {
        String input = editText.getText().toString().trim();
        String[] singleInputs = input.split("\\s*,\\s*");

        StringBuilder toastText = new StringBuilder();

        for (int i = 0; i < singleInputs.length; i++) {
            toastText.append("Item ").append(i).append(": ").append(singleInputs[i]).append("\n");
        }

        Toast.makeText(this, toastText.toString(), Toast.LENGTH_SHORT).show();
    }
}