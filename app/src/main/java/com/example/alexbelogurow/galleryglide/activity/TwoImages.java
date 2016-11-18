package com.example.alexbelogurow.galleryglide.activity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

import java.util.ArrayList;

public class TwoImages extends AppCompatActivity {

    private ImageView mImageOne;
    private ImageView mImageTwo;
    private ArrayList<PersonImage> images;
    private Paint mPaint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_two_images);
        setContentView(new DrawView(this));
        //loadImages();


        mPaint = new Paint();
        mPaint.setAntiAlias(true);   //Paint flag that enables antialiasing when drawing.
        mPaint.setDither(true);  //Paint flag that enables dithering when blitting.
        mPaint.setColor(Color.GREEN);    //choose color
        mPaint.setStyle(Paint.Style.STROKE);     //The Style specifies if the primitive being drawn is filled, stroked, or both (in the same color).
        mPaint.setStrokeJoin(Paint.Join.ROUND);  //The Join specifies the treatment where lines and curve segments join on a stroked path.
        mPaint.setStrokeCap(Paint.Cap.ROUND);    //The Cap specifies the treatment for the beginning and ending of stroked lines and paths.
        mPaint.setStrokeWidth(12);
    }


    class DrawView extends View {


        Bitmap bitmapFirst;
        Bitmap bitmapSecond;

        Rect rectSrcFirst, rectSrcSecond;   //for initial position
        Rect rectDstFirst, rectDstSecond;   //position on the screen

        //------
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        private Paint circlePaint;
        private Path circlePath;

        public DrawView(Context context) {
            super(context);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            int position1 = getIntent().getIntExtra("position1", 1);
            images = (ArrayList<PersonImage>) getIntent().getExtras().getSerializable("images");
            bitmapFirst = BitmapFactory.decodeResource(getResources(), images.get(0).getImageID());
            bitmapSecond = BitmapFactory.decodeResource(getResources(), images.get(1).getImageID());

            int width = getWindowManager().getDefaultDisplay().getWidth();
            int height = getWindowManager().getDefaultDisplay().getHeight();

            rectSrcFirst = new Rect(0, 0, bitmapFirst.getWidth()-1, bitmapFirst.getHeight()-1);
            rectDstFirst = new Rect(0, 50, width, height/2 - 25);

            rectSrcSecond = new Rect(0, 0, bitmapFirst.getWidth()-1, bitmapFirst.getHeight()-1);
            rectDstSecond = new Rect(0, height/2 + 25, width, height-100);


            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);

            //draw first image
            canvas.drawBitmap(bitmapFirst, rectSrcFirst, rectDstFirst, mPaint);

            //draw second image
            canvas.drawBitmap(bitmapSecond, rectSrcSecond, rectDstSecond, mPaint);

            //draw bitmap for lines
            canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawPath(circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();   // It allows to redraw (onDraw)
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

    }



    private void loadImages() {
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
