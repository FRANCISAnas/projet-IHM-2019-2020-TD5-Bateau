package com.example.ihmprojet_2019_2020_td5_bateaux.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.R;

public class PhotoSourceDialogue extends DialogFragment {
    private Bitmap photo;
    public static final int RESULT_GALLERY = 0;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.photo_source_layout, null);


        builder.setView(view).setTitle("Add Photo").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        Button takephoto = view.findViewById(R.id.TakePhoto);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });
        Button addphoto = view.findViewById(R.id.addPhotoo);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent , RESULT_GALLERY );
            }
        });




        return builder.create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            //PostFragment.photo = photo;
            UseImageDialog useImageDialog = new UseImageDialog(photo);
            useImageDialog.show(getFragmentManager(),"use image dialog");
            this.dismiss();
        }

        if (requestCode == RESULT_GALLERY && resultCode == Activity.RESULT_OK)
        {

        }


    }


    public Bitmap getPhoto(){
        return this.photo;
    }
}
