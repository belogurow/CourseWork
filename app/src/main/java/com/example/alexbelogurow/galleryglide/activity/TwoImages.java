package com.example.alexbelogurow.galleryglide.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

import java.util.ArrayList;

public class TwoImages extends AppCompatActivity {

    private ImageView mImageOne;
    private ImageView mImageTwo;
    private ArrayList<PersonImage> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_images);

        mImageOne = (ImageView) findViewById(R.id.imageViewOne);
        mImageTwo = (ImageView) findViewById(R.id.imageViewTwo);

        int position1 = getIntent().getIntExtra("position1", 1);
        int position2 = getIntent().getIntExtra("position2", 5);
        images = (ArrayList<PersonImage>) getIntent().getExtras().getSerializable("images");

        mImageOne.setImageResource(images.get(position1).getImageID());
        mImageTwo.setImageResource(images.get(position2).getImageID());

        Toast.makeText(getApplicationContext(), position1 + " " + position2, Toast.LENGTH_SHORT).show();


    }
}
