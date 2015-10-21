package insa.cloud.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;

import java.util.ArrayList;
import java.util.HashMap;

import insa.cloud.R;


public class ImageContainerLayout extends FrameLayout {


    HashMap<Integer, Bitmap[][]> imagesByZoom = new HashMap<>();


    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;


    public ImageContainerLayout(Context context) {
        super(context);
        init(context);
    }

    public ImageContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        populateWithDummy();

        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    for (int i = 0; i < getChildCount(); i++) {
                        View child = getChildAt(i);
                        child.setX(child.getX() + getWidth()/2f);
                        child.setY(child.getY() + getWidth() / 2f);
                    }
                }
            });
        }
    }


    public void populateWithDummy() {
        int[] drawables = {
                R.drawable.slice_0_0,
                R.drawable.slice_0_1,
                R.drawable.slice_0_2,
                R.drawable.slice_0_3,
                R.drawable.slice_1_0,
                R.drawable.slice_1_1,
                R.drawable.slice_1_2,
                R.drawable.slice_1_3,
                R.drawable.slice_2_0,
                R.drawable.slice_2_1,
                R.drawable.slice_2_2,
                R.drawable.slice_2_3,
        };

        int colPerRow = 4;
        int numberOfRow = 3;
        Bitmap[][] rows = new Bitmap[numberOfRow][colPerRow];
        LayoutParams params = null;
        int imageWidth = 0;
        int imageHeight = 0;
        int xCoor = 0;
        int yCoor = 0;
        int j = 0;
        for (int i = 0; i < drawables.length; i++) {
            if(i % colPerRow == 0 && i != 0) {
                j++;
            }


            rows[j][i % colPerRow] = BitmapFactory.decodeResource(getResources(), drawables[i]);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(rows[j][i % colPerRow]);

            if(params == null) {
                imageWidth = rows[j][i % colPerRow].getWidth();
                imageHeight = rows[j][i % colPerRow].getHeight();
                params = new LayoutParams(imageWidth, imageHeight);
            }
            imageView.setLayoutParams(params);
            addView(imageView);

            imageView.setX(-(imageWidth * colPerRow)/ 2 + (i % colPerRow) * imageWidth + getWidth()/2);
            imageView.setY(-(imageHeight * numberOfRow)/2 + (j * imageHeight) + getHeight()/2);
        }
        imagesByZoom.put(0, rows);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public void addImage(Bitmap bitmap) {

    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        //...
        //Your onDraw() code
        //...
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();


            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.setScaleX(mScaleFactor);
                child.setScaleY(mScaleFactor);
            }

            invalidate();
            return true;
        }
    }
}
