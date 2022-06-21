package com.example.akcsit_protikdatta;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.akcsit_protikdatta.databinding.ActivityProfileBinding;
import com.example.akcsit_protikdatta.databinding.ProfileDpOptionsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    CircleImageView civ;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        civ = binding.dp; //dp was the id for circular image view
        binding.edit.setOnClickListener(v -> {

            dialog = new BottomSheetDialog(ProfileActivity.this);
            ProfileDpOptionsBinding dpBinding = ProfileDpOptionsBinding.inflate(getLayoutInflater());
            dialog.setContentView(dpBinding.getRoot());
            dpBinding.cameraOpt.setOnClickListener(v1 -> captureUsingCamera());
            dpBinding.galleryOpt.setOnClickListener(v1 -> getFromGallery());
            dialog.show();

//            captureUsingCamera();
        });        //edit was the id for floating action button
    }
    private void getFromGallery()
    {
        Intent in = new Intent(Intent.ACTION_GET_CONTENT);
        in.setType("*/*"); //any folder any file
        Intent chosen = Intent.createChooser(in, "Choose");
        library.launch(chosen);
        dialog.dismiss();
    }
    ActivityResultLauncher<Intent> library = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result != null || result.getData() != null)
                {
                    Uri uri = result.getData().getData();
                    civ.setImageURI(uri);
                }
            });
    private void captureUsingCamera()
    {
        Intent obj = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(obj);
        dialog.dismiss();

    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    civ.setImageBitmap(bitmap);
                }
            });

}