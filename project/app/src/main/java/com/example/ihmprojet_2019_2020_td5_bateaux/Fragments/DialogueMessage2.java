package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

import static com.example.ihmprojet_2019_2020_td5_bateaux.NeptuneNotification.CHANNEL_CLASSIQUE;

public class DialogueMessage2 extends AppCompatDialogFragment {
    private static final String FACEBOOK_PAGE_ID = "151932215253161";
    private static final int MAX_NUMBER_OF_NOTIFICATIONS = 3;
    final ViewGroup container;
    private String nature;
    private String description;
    private NotificationManagerCompat notificationManager;

    DialogueMessage2(String nature, String desc, ViewGroup container) {
        this.nature = nature;
        this.description = desc;
        this.container = container;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.option_dialog_layout, null);
        notificationManager = NotificationManagerCompat.from(container.getContext());
        builder.setView(view).setTitle("Yes").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                portAnIncident();
                sendOnclassic();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // si l'utilsiateur click oui donc on fait appelle à la service de FaceBook
                goToFaceBookPage(FACEBOOK_PAGE_ID);
                sendOnclassic();
            }
        });
        return builder.create();
    }

    private void goToFaceBookPage(String id) {
        portAnIncident();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + id));
            startActivity(intent);
        }
    }

    private void portAnIncident() {
        IncidentPostService postService = new IncidentPostService(container.getContext(), nature, description);
        postService.execute();
        FragmentTransaction frag = getFragmentManager().beginTransaction();
        frag.replace(R.id.fragment_container, new IncidentsFragment());
        frag.commit();
    }

    public void sendOnclassic() {
        if (DialogueMessage.nbOfNotification == MAX_NUMBER_OF_NOTIFICATIONS)
            DialogueMessage.nbOfNotification = 0;
        /*Intent intent = new Intent(getApplicationContext(), IncidentsFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/
        final String desccription = ((EditText) container.findViewById(R.id.editTextDescription)).getText().toString();

        Notification notification = new NotificationCompat.Builder(container.getContext(), CHANNEL_CLASSIQUE)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentText(desccription)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        notificationManager.notify(DialogueMessage.nbOfNotification++, notification);
    }

}
