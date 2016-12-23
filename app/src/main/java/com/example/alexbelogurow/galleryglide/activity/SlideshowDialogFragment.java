package com.example.alexbelogurow.galleryglide.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

import java.util.ArrayList;


public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<PersonImage> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    //private TextView lblCount, lblTitle, lblDate;
    private TextView lblCount, personName, personID, birthDate, imageDate, spin, tilt, w, c, sl;
    private int selectedPosition = 0;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        personName = (TextView) v.findViewById(R.id.slider_name);
        personID = (TextView) v.findViewById(R.id.slider_id);
        birthDate = (TextView) v.findViewById(R.id.slider_birthDate);
        imageDate = (TextView) v.findViewById(R.id.slider_imageDate);
        spin = (TextView) v.findViewById(R.id.slider_spin);
        tilt = (TextView) v.findViewById(R.id.slider_tilt);
        sl = (TextView) v.findViewById(R.id.slider_sl);
        w = (TextView) v.findViewById(R.id.slider_w);
        c = (TextView) v.findViewById(R.id.slider_c);

        images = (ArrayList<PersonImage>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        //Toast.makeText(v.getContext(), images.toString(), Toast.LENGTH_SHORT).show();
        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {

        lblCount.setText((position + 1) + " of " + images.size());

        PersonImage image = images.get(position);
        personName.setText(image.getPersonName());
        personID.setText(getString(R.string.id, image.getPersonID()));
        imageDate.setText(image.getImageDate());
        birthDate.setText(image.getBirthDate());
        spin.setText(getString(R.string.spin, image.getSpin()));
        tilt.setText(getString(R.string.tilt, image.getTilt()));
        sl.setText(getString(R.string.sl, image.getSl()));
        w.setText(getString(R.string.w, image.getW()));
        c.setText(getString(R.string.c, image.getC()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

            PersonImage image = images.get(position);

            if (image.getImageID() == null) {
                Glide.with(getActivity())
                        .load("")
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(image.getDrawable())
                        .into(imageViewPreview);

            }
            else {
            Glide.with(getActivity()).load(image.getImageID())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);
            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
