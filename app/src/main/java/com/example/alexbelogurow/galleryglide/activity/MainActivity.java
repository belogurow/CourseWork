package com.example.alexbelogurow.galleryglide.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;

import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.adapter.RVGalleryAdapter;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

public class MainActivity extends AppCompatActivity {

    private ArrayList<PersonImage> images;  // ArrayList для изображений
    private ProgressDialog pDialog; // диалог с пользователем (напр. полоса загрузки изображения)
    private RVGalleryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RVGalleryAdapter(getApplicationContext(), images, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        mRecyclerView.setAdapter(mAdapter);




        /*mAdapter = new GalleryAdapter(getApplicationContext(), images);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), mRecyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));   */

        //fetchImages();
        loadDICOM();
    }

    private void loadDICOM() {
        pDialog.setMessage("Load DICOM images");
        pDialog.show();

        images.clear();
        PersonImage image1 = new PersonImage("Alex", "", R.drawable.person31);
        PersonImage image2 = new PersonImage("Tom", "", R.drawable.person310);
        PersonImage image3 = new PersonImage("Kate", "", R.drawable.person32);
        PersonImage image4 = new PersonImage("John", "", R.drawable.person34);
        PersonImage image5 = new PersonImage("Maks", "", R.drawable.person35);
        PersonImage image6 = new PersonImage("Elena", "", R.drawable.person36);
        PersonImage image7 = new PersonImage("Smith", "", R.drawable.person38);
        PersonImage image8 = new PersonImage("Anna", "", R.drawable.person39);
        pDialog.hide();

        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        images.add(image6);
        images.add(image7);
        images.add(image8);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resID = item.getItemId();
        if (resID == R.id.action_twoimages) {
            Toast.makeText(getApplicationContext(), "Choose two images", Toast.LENGTH_SHORT).show();
            mAdapter.setTwoImages(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        mRecyclerView.setAdapter(mAdapter);
        super.onResume();
    }
}