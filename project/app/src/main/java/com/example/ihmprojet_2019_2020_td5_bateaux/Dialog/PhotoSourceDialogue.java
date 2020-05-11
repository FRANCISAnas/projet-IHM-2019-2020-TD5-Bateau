package com.example.ihmprojet_2019_2020_td5_bateaux.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import com.example.ihmprojet_2019_2020_td5_bateaux.Fragments.PostFragment;
import com.example.ihmprojet_2019_2020_td5_bateaux.R;

import java.io.IOException;

public class PhotoSourceDialogue extends DialogFragment {
    private Bitmap photo;
    public static final int RESULT_GALLERY = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View  view = layoutInflater.inflate(R.layout.photo_source_layout, null);


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

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_GALLERY);
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
           Uri imageUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                PostFragment.photo =bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getContext(), "Image loaded successfully", Toast.LENGTH_SHORT).show();
            this.dismiss();


        }


    }


    public Bitmap getPhoto(){
        return this.photo;
    }
}
