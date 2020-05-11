package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

public class AboutUsFragment extends Fragment {
    public AboutUsFragment() {
        // Empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        final View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);

        ImageView facebook = rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFaceBookPage();
            }

        });

        ImageView twiter = rootView.findViewById(R.id.twitter);
        twiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTwitterPage();
            }

        });

        ImageView gmail = rootView.findViewById(R.id.gmail);
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGmailPage(rootView);
            }

        });

        ImageView faq = rootView.findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFAQPage();
            }

        });



        return rootView;
    }

    private void goToTwitterPage() {

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/NIncidents?s=09")));

    }

    private void goToFAQPage() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.neptune.dinelhost.com/index.html")));
    }

    private void goToGmailPage(View rootView) {
        try {
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "anas?francis@etu.univ-cotedazur.fr"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(rootView.getContext(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    private void goToFaceBookPage() {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Neptune-Incidents-105901411124436"));
            startActivity(intent);

    }
}
