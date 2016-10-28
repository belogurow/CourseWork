package com.example.alexbelogurow.galleryglide.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.activity.MainActivity;
import com.example.alexbelogurow.galleryglide.activity.SlideshowDialogFragment;
import com.example.alexbelogurow.galleryglide.activity.TwoImages;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexbelogurow on 28.10.16.
 */

public class RVGalleryAdapter extends RecyclerView.Adapter<RVGalleryAdapter.RViewHolder> {

    private MainActivity mainActivity;
    private List<PersonImage> images;
    private Context mContext;
    private int n;
    private boolean twoImages;

    private int positionone = 0;  // first image posititon
    private int positiontwo = 0;  // second image position


    public void setTwoImages(boolean twoImages) {
        this.twoImages = twoImages;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public class RViewHolder extends RecyclerView.ViewHolder {
        public ImageView mThumbnail;

        public RViewHolder(View itemView) {
            super(itemView);
            this.mThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }

    }


    public RVGalleryAdapter(Context mContext, List<PersonImage> images, MainActivity main) {
        this.images = images;
        this.mContext = mContext;
        mainActivity = main;

        this.n = 0;
        this.twoImages = false;
    }


    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RViewHolder holder, final int position) {
        PersonImage personImage = images.get(position);
        Glide.with(mContext).load(personImage.getImageID())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoImages) {
                    n++;
                    holder.mThumbnail.setAlpha(0.2f);
                    if (getN() == 1) {
                        positionone = position;
                    }
                    if (getN() == 2) {
                        positiontwo = position;

                        Intent intent = new Intent(v.getContext(), TwoImages.class);
                        intent.putExtra("position1", positionone);
                        intent.putExtra("position2", positiontwo);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("images", (Serializable) images);
                        intent.putExtras(bundle);

                        v.getContext().startActivity(intent);

                        setTwoImages(false);
                        setN(0);
                        }
                    }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("images", (Serializable) images);
                    bundle.putInt("position", position);
                    FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");
                }
                }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}