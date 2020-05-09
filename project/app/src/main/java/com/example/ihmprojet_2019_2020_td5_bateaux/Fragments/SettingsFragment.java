package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.MainActivity;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.util.Locale;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Locale myLocale;
    String currentLanguage = "en", currentLang;
    public static boolean URGENT_NOTIFICATIONS = true;
    public SettingsFragment() {
        // Empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        currentLanguage = getIntent().getStringExtra(currentLang);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner spinner = rootView.findViewById(R.id.spinnerTemp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.tempSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);
        // spinner.setOnItemSelectedListener(this);

        Spinner spinner1 = rootView.findViewById(R.id.spinnerTempL);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.langue, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = rootView.findViewById(R.id.spinnerTempF);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.format, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);

        Spinner spinner3 = rootView.findViewById(R.id.spinnerTempN); // Notifications
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.notification, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);
        notificationsChoice(spinner3);


        Spinner spinner4 = rootView.findViewById(R.id.spinnerTempP);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getActivity(), R.array.precipitation, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);
        spinner4.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);

        Spinner spinner5 = rootView.findViewById(R.id.spinnerTempWSP);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getActivity(), R.array.windspeed, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter5);
        spinner5.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);

        Spinner spinner6 = rootView.findViewById(R.id.spinnerTempPR);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(getActivity(), R.array.pressure, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter6);
        spinner6.getBackground().setColorFilter(getResources().getColor(R.color.design_default_color_primary_dark), PorterDuff.Mode.SRC_ATOP);

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

        Configuration config;
        switch (pos) {
            case 0:
                break;

            case 1:
                setLocale("en");
                break;

            case 2:
                setLocale("fr");
                break;
        }

    }

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(getContext(), MainActivity.class);
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
