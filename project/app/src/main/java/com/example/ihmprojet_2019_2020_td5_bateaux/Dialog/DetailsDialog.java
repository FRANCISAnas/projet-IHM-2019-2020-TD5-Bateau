package com.example.ihmprojet_2019_2020_td5_bateaux.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.Metier.Incident;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.io.ByteArrayOutputStream;

public class DetailsDialog extends DialogFragment {
    Incident incident;

    public DetailsDialog(Incident incident) {
        this.incident = incident;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.details_layout, null);

        builder.setView(view).setTitle("Details").setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        TextView natureVal = view.findViewById(R.id.natureValue);
        natureVal.setText(incident.getNature());

        TextView longVal = view.findViewById(R.id.longValue);
        longVal.setText(incident.getLongitude());


        TextView latVal = view.findViewById(R.id.latValue);
        latVal.setText(incident.getLatitude());


        TextView dateVal = view.findViewById(R.id.dateValue);
        dateVal.setText(incident.getDate());
        TextView desc = view.findViewById(R.id.descValue);
        if (!incident.getNature().equals("")) {
            desc.setText(incident.getDescription());
            desc.setVisibility(View.VISIBLE);
        }


        if(!incident.getEncodedImage().equals("null")){
            ImageView imageView = view.findViewById(R.id.image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] imageBytes = baos.toByteArray();
            imageBytes = Base64.decode(incident.getEncodedImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imageView.setImageBitmap(decodedImage);
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
