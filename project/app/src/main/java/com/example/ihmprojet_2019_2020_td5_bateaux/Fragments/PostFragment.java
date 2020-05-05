package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_URGENTE;


public class PostFragment extends Fragment {


    private NotificationManagerCompat notificationManager;

    public static int nbOfNotification = 0;

    private static final int MAX_NUMBER_OF_NOTIFICATIONS = 3;
    public final static String TAG = "FRANCIS";


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_incident_submission, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.natures);
        notificationManager = NotificationManagerCompat.from(getContext());
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.natures));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = (String) parent.getItemAtPosition(position);

                if (value.equals("Autre")) {
                    EditText editText = rootView.findViewById(R.id.naturetype);
                    editText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button post = rootView.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nature;
                nature = spinner.getSelectedItem().toString();
                if (nature.equals("Autre")) {
                    EditText editText = rootView.findViewById(R.id.naturetype);
                    nature = editText.getText().toString();
                }
                EditText editText = rootView.findViewById(R.id.editTextDescription);
                final String description = editText.getText().toString();
                openDialog(nature, description, container);
                sendOnUrgent(v);
            }


        });


        return rootView;
    }
    private void openDialog(String nature, String description, ViewGroup container) {

        DialogueMessage dialogueMessage = new DialogueMessage(nature, description, container);
        dialogueMessage.show(getFragmentManager(), TAG);

    }




    public void sendOnUrgent(View v) {
        if (nbOfNotification == MAX_NUMBER_OF_NOTIFICATIONS) nbOfNotification = 0;
        /*Intent intent = new Intent(getApplicationContext(), IncidentsFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/
        final String desccription = ((EditText) getView().findViewById(R.id.editTextDescription)).getText().toString();

        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_URGENTE)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentText(desccription)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManager.notify(nbOfNotification++, notification);
    }
}
