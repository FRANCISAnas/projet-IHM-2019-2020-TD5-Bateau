package com.example.ihmprojet_2019_2020_td5_bateaux.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.PostFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

public class UseImageDialog extends DialogFragment {

    Bitmap photo;
    public UseImageDialog(Bitmap photo){
        this.photo = photo;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.use_image_layout, null);


        ImageView imageView = view.findViewById(R.id.use_image);
        imageView.setImageBitmap(photo);

        builder.setView(view).setTitle("Take Photo").setPositiveButton("Use Photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PostFragment.photo = photo;
                Toast.makeText(getContext(), "Image loaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PostFragment.photo=null;
            }
        });

        return builder.create();

    }
}
