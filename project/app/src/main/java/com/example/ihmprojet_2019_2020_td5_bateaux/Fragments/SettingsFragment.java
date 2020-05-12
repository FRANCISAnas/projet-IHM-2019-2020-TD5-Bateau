package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.HomeActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.util.Locale;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Locale myLocale;
    private String currentLanguage = "en", currentLang = "en";
    public static boolean URGENT_NOTIFICATIONS = false;
    private CheckBox checkBox;

    public SettingsFragment() {
        // Empty constructor required
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            currentLanguage = savedInstanceState.getString("fr","en");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        //currentLanguage = getIntent().getStringExtra(currentLang);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner spinner = rootView.findViewById(R.id.spinnerTemp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.tempSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 switch (position)
                 {
                     case 0:
                         if(parent.getSelectedItem().equals("Fehrnhite")) break;
                         else
                         {
                             parent.setSelection(position);
                         }

                     case 1:
                         if(parent.getSelectedItem().equals("Celecius") || parent.getSelectedItem().equals("Celesius")) break;
                         else
                         {
                             parent.setSelection(position);
                         }
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        Spinner spinner1 = rootView.findViewById(R.id.spinnerTempL);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.langue, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.getTextDirection();
        spinner1.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);
        spinner1.setOnItemSelectedListener(this);




        checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!URGENT_NOTIFICATIONS)URGENT_NOTIFICATIONS = true;
                else URGENT_NOTIFICATIONS = false;
            }
        });


        return rootView;
    }

    private void notificationsChoice(Spinner spinner3) {
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);
                if (value.equals("All notifications") || value.equals("Toutes les Notifications")){
                    URGENT_NOTIFICATIONS = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Intent getIntent() {
        Intent intent = new Intent();
        return intent;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // String text = parent.getItemAtPosition(position).toString();
        //  Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        Locale english = Locale.ENGLISH;
        Locale french = Locale.FRENCH;

       switch (pos){

           case 0:
               if(currentLanguage.equals("en")) break;
               else {
                   setLocale("en");
                   currentLanguage = "en";
                   parent.setSelection(pos);
               }
               break;

           case 1:
               if(currentLanguage.equals("fr")) break;
               else {
                   setLocale("fr");
                   currentLanguage = "fr";
                   parent.setSelection(pos);
               }
               break;
       }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fr", currentLanguage);
    }

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(getContext(), HomeActivity.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(getContext(), "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        System.exit(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
