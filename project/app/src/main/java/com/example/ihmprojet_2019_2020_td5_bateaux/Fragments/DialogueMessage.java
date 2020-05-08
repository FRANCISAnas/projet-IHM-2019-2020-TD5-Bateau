package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;
import com.example.ihmprojet_2019_2020_td5_bateaux.Service.IncidentPostService;

public class DialogueMessage extends AppCompatDialogFragment {
    private String nature;
    private String description;
    private static final String FACEBOOK_PAGE_ID = "151932215253161";
    final ViewGroup container;
    DialogueMessage(String nature, String desc, ViewGroup container){
        this.nature = nature;
        this.description = desc;
        this.container = container;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.option_dialog_layout, null);
        builder.setView(view).setTitle("Yes").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                portAnIncident();
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // si l'utilsiateur click oui donc on fait appelle à la service de FaceBook
                goToFaceBookPage(FACEBOOK_PAGE_ID);
            }
        });
        return builder.create();
    }

    private void goToFaceBookPage(String id){
        portAnIncident();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + id));
            startActivity(intent);
        }
    }

    private void portAnIncident(){
        IncidentPostService postService = new IncidentPostService(container.getContext(), nature, description);
        postService.execute();
        FragmentTransaction frag = getFragmentManager().beginTransaction();
        frag.replace(R.id.fragment_container, new IncidentsFragment());
        frag.commit();
    }
}
