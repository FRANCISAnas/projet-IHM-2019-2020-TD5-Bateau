package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.Dialog.PhotoSourceDialogue;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPutService;


public class PostFragment extends Fragment {

    private final static String AUTRE = "Other";
    public static Bitmap photo;
    boolean fromAddButton = false;
    Bundle bundle;


    public PostFragment() {
        // Required empty public constructor
    }

    public PostFragment(boolean fromAddButton) {
        this.fromAddButton = fromAddButton;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_incident_submission, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.natures);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.natures));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);

                EditText editText = rootView.findViewById(R.id.naturetype);
                if (value.equals(AUTRE)) {
                    editText.setVisibility(View.VISIBLE);
                } else {
                    editText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button addPhoto = rootView.findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoSourceDialogue photoSourceDialogue = new PhotoSourceDialogue();
                photoSourceDialogue.show(getFragmentManager(), "ggg");
            }
        });


        Button post = rootView.findViewById(R.id.post);
        if (fromAddButton) {
            post.setText("Post");
            TextView tv = rootView.findViewById(R.id.textView5);
            tv.setText("Report");
            Button button = rootView.findViewById(R.id.addPhoto);
            button.setVisibility(View.VISIBLE);


        } else {

            bundle = getArguments();
            for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                if (bundle.get("nature").toString().equals(spinner.getItemAtPosition(i))) {
                    spinner.setSelection(i);
                }
            }


            EditText editText = rootView.findViewById(R.id.editTextDescription);
            editText.setText(bundle.get("description").toString());
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nature = spinner.getSelectedItem().toString();
                if (nature.equals(AUTRE)) {
                    EditText editText = rootView.findViewById(R.id.naturetype);
                    nature = editText.getText().toString();
                }
                EditText des = rootView.findViewById(R.id.editTextDescription);
                final String description = des.getText().toString();

                if (fromAddButton) {
                    IncidentPostService postService;
                    if (photo != null) {
                        postService = new IncidentPostService(container.getContext(), nature, description, photo);
                    } else {

                        postService = new IncidentPostService(container.getContext(), nature, description);
                    }
                    postService.execute();
                } else {
                    IncidentPutService incidentPutService = new IncidentPutService(container.getContext(), nature, description, (int) bundle.get("id"));
                    incidentPutService.execute();
                }

                FragmentTransaction frag = getFragmentManager().beginTransaction();
                frag.replace(R.id.fragment_container, new IncidentsFragment());
                frag.commit();


            }
        });


        return rootView;
    }


}
