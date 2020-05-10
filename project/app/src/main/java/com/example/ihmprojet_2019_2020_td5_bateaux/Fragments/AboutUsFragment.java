package com.example.ihmprojet_2019_2020_td5_bateaux.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

public class AboutUsFragment extends Fragment {
    private static final String FACEBOOK_PAGE_ID = "151932215253161";
    public AboutUsFragment() {
        // Empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);

        ImageView facebook = rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFaceBookPage(FACEBOOK_PAGE_ID);
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
                goToGmailPage();
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
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=[user_name]"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/[user_name]")));
        }

    }

    private void goToFAQPage() {
    }

    private void goToGmailPage() {

    }


    private void goToFaceBookPage(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("facebook://page/" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + id));
            startActivity(intent);
        }
    }
}
