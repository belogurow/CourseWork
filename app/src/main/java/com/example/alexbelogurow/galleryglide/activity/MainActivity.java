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
        PersonImage image1 = new PersonImage("Alex", "7350", "23/01/1951", "25/11/2010 12:34:32", R.drawable.person31);
        PersonImage image2 = new PersonImage("Tom", "9765", "13/02/1956", "18/08/2016 14:18:19", R.drawable.person310);
        PersonImage image3 = new PersonImage("Kate", "5317", "06/10/1971", "29/04/2014 08:55:57", R.drawable.person32);
        PersonImage image4 = new PersonImage("John", "4889", "01/03/1977", "11/01/2012 10:29:48", R.drawable.person34);
        PersonImage image5 = new PersonImage("Maks", "8678", "29/02/1976", "26/12/2012 15:36:22", R.drawable.person35);
        PersonImage image6 = new PersonImage("Elena", "9088", "01/10/1979", "03/02/2011 11:07:41", R.drawable.person36);
        PersonImage image7 = new PersonImage("Smith", "8209", "02/04/1986", "03/05/2011 09:10:46", R.drawable.person38);
        PersonImage image8 = new PersonImage("Anna", "9270", "09/05/1980", "18/11/2015 15:09:04", R.drawable.person39);
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

    // TODO записка
    @Override
    protected void onDestroy() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}