package com.example.alexbelogurow.galleryglide.activity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.alexbelogurow.galleryglide.R;
import com.example.alexbelogurow.galleryglide.model.PersonImage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TwoImages extends Activity implements ColorPickerDialog.OnColorChangedListener{

    private ImageView mImageOne;
    private ImageView mImageTwo;
    private ArrayList<PersonImage> images;
    private Paint mPaint;


    private MaskFilter  mEmboss;
    private MaskFilter mBlur;
    DrawView drawView;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawView = new DrawView(this);
        drawView.setDrawingCacheEnabled(true);
        setContentView(drawView);



        mPaint = new Paint();
        mPaint.setAntiAlias(true);   //Paint flag that enables antialiasing when drawing.
        mPaint.setDither(true);  //Paint flag that enables dithering when blitting.
        mPaint.setColor(Color.GREEN);    //choose color
        mPaint.setStyle(Paint.Style.STROKE);     //The Style specifies if the primitive being drawn is filled, stroked, or both (in the same color).
        mPaint.setStrokeJoin(Paint.Join.ROUND);  //The Join specifies the treatment where lines and curve segments join on a stroked path.
        mPaint.setStrokeCap(Paint.Cap.ROUND);    //The Cap specifies the treatment for the beginning and ending of stroked lines and paths.
        mPaint.setStrokeWidth(12);
        mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
                0.4f, 6, 3.5f);
        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    public void colorChanged(int color) {
        mPaint.setColor(color);
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
            //canvas.drawARGB(80, 102, 204, 255);

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
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
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



    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int CLEAR_MENU_ID = Menu.FIRST + 1;
    private static final int SAVE_MENU_ID = Menu.FIRST + 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        menu.add(0, COLOR_MENU_ID, 0, "Color");
        menu.add(0, CLEAR_MENU_ID, 0, "Clear");
        menu.add(0, SAVE_MENU_ID, 0, "Save");

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case COLOR_MENU_ID:
                new ColorPickerDialog(this, this, mPaint.getColor()).show();
                return true;
            case CLEAR_MENU_ID:
                drawView.mBitmap.eraseColor(Color.TRANSPARENT);
                drawView.invalidate();
                return true;
            case SAVE_MENU_ID:
                AlertDialog.Builder editalert = new AlertDialog.Builder(TwoImages.this);
                editalert.setTitle("Please Enter the name with which you want to Save");
                final EditText input = new EditText(TwoImages.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT);
                input.setLayoutParams(lp);
                editalert.setView(input);
                editalert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String name= input.getText().toString();
                        Bitmap bitmap = drawView.getDrawingCache();

                        File path = TwoImages.this.getExternalFilesDir(null);
                        //String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        Toast.makeText(TwoImages.this, path+name+".png", Toast.LENGTH_SHORT).show();
                        //File file = new File(path+name+".png");
                        File file = new File(path, name + ".png");

                        try
                        {
                            if(!file.exists())
                            {
                                file.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                            ostream.close();
                            drawView.invalidate();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }finally
                        {

                            drawView.setDrawingCacheEnabled(false);
                        }
                    }
                });
                editalert.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
