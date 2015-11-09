package insa.cloud.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;

import insa.cloud.global.ImageProvider;


public class ImageContainerLayout extends FrameLayout {


    public static final float MIN_SCALE_FACTOR = 1.0f;
    public static final float MAX_SCALE_FACTOR = 4.0f;
    public float mHigherScaleFactor = -1f;
    public float mLowerScaleFactor = -1f;
    static int mMaxZoomLevel = 1;

    ImageProvider provider;

    int mFullImageWidth = 0;


    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private int mCurrentZoomLevel = 0;

    PointF downPoint = new PointF(0, 0);
    ArrayList<PointF> childOrigins;


    public ImageContainerLayout(Context context) {
        super(context);

    }

    public ImageContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ImageContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private void init(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        displayInitImage();
    }

    public void setProvider(ImageProvider provider) {
        this.provider = provider;
        provider.loadMetaData(new ImageProvider.LoadedCallBack() {
            @Override
            public void onLoaded() {
                init(getContext());
            }
        });
    }

    private void displayInitImage() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    displayAllImageForZoomLevel(0);
                }
            });
        }
    }


    private float displayAllImageForZoomLevel(int zoomLevel) {

        if(provider == null) return -1;

        Log.d("LoadingImages", "Zoom Level : " + zoomLevel);
        if (zoomLevel >= 0 && zoomLevel <= provider.getMaxZoomLevel()) {

            int childIndex = 0;
            int colPerRow, numberOfRow;
            ImageView currentImageView;

            LayoutParams params = null;
            int imageWidth = provider.getTillWidth();
            int imageHeight = provider.getTillHeight();

            numberOfRow = provider.getRowCountForZoom(zoomLevel);
            if (numberOfRow < 1) {
                Log.w("ReplaceImage", "No images available for ZoomLevel " + zoomLevel);
                return 0;
            }

            colPerRow = provider.getColCountForZoom(zoomLevel);

            //Check if we have images and set the number of col and rows we have
            if (colPerRow < 1) {
                Log.w("ReplaceImage", "No images available for ZoomLevel " + zoomLevel + "at row 0");
                return 0;
            }
            if (numberOfRow < 1) {
                Log.w("ReplaceImage", "No images available for ZoomLevel " + zoomLevel);
                return 0;
            }


            // Display all imageView from the Bitmap Array
            for (int i = 0; i < numberOfRow; i++) {
                for (int j = 0; j < colPerRow; j++) {
                    while (childIndex < getChildCount() && !(getChildAt(childIndex) instanceof ImageView)) {
                        childIndex++;
                    }

                    if (childIndex < getChildCount()) {
                        currentImageView = (ImageView) getChildAt(childIndex);
                    } else {
                        currentImageView = new ImageView(getContext());
                        addView(currentImageView);
                    }
                    //Bitmap bitmap = images[i][j];
                    //currentImageView.setImageBitmap(bitmap);
                    provider.fillImageViewForCoordinate(currentImageView, zoomLevel, i, j);
                    currentImageView.setVisibility(VISIBLE);


                    if (params == null) {
                        params = new LayoutParams(imageWidth, imageHeight);
                    }
                    currentImageView.setLayoutParams(params);

                    // Set the coordinate such as the image are well placed
                    currentImageView.setX(j * imageWidth + getWidth() / 2 - (imageWidth * colPerRow) / 2);
                    currentImageView.setY(i * imageHeight + getHeight() / 2 - (imageHeight * numberOfRow) / 2);

                    childIndex++;
                }

            }

            while (childIndex < getChildCount()) {
                if (getChildAt(childIndex) instanceof ImageView) {
                    getChildAt(childIndex).setVisibility(GONE);
                }
                childIndex++;
            }

            mFullImageWidth = imageWidth * colPerRow;
            mLowerScaleFactor = MIN_SCALE_FACTOR;
            if (zoomLevel < provider.getMaxZoomLevel()) {
                mHigherScaleFactor = (provider.getColCountForZoom(zoomLevel +1) * imageWidth) / (float) mFullImageWidth;
            } else {
                mHigherScaleFactor = MAX_SCALE_FACTOR;
            }

            Log.d("Scale", "ScaleFActor : " + mLowerScaleFactor + " " + mHigherScaleFactor);
        }
        return mScaleFactor;
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        if (ev.getPointerCount() > 1) {
            mScaleDetector.onTouchEvent(ev);
            //Log.d("ImageAction", "Scale Event");
        } else {
             return handleMove(ev);
        }
        return true;
    }


    private boolean handleMove(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d("ImageAction", "Down Event");
                if (childOrigins == null) {
                    childOrigins = new ArrayList<>(getChildCount());
                } else {
                    childOrigins.clear();
                }

                // Save the orgin of te movement
                downPoint.set(ev.getX(), ev.getY());

                // Save the position of the child before the movement
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    PointF childOrigin;
                    if (i >= childOrigins.size()) {
                        childOrigin = new PointF();
                        childOrigins.add(childOrigin);
                    } else {
                        childOrigin = childOrigins.get(i);
                    }
                    childOrigin.set(child.getX(), child.getY());
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (getChildCount() == childOrigins.size()) {
                    for (int i = 0; i < getChildCount(); i++) {
                        View child = getChildAt(i);
                        PointF childOrigin = childOrigins.get(i);
                        child.setX(childOrigin.x + ev.getX() - downPoint.x);
                        child.setY(childOrigin.y + ev.getY() - downPoint.y);
                    }
                    return true;
                }
                return false;
        }
        return false;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            float pivotX, pivotY;
//            pivotX = detector.getFocusX();
//            pivotY = detector.getFocusY();
            pivotX = getWidth() / 2;
            pivotY = getHeight() / 2;
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(mLowerScaleFactor, Math.min(mScaleFactor, mHigherScaleFactor));

            if (mScaleFactor >= mHigherScaleFactor && mCurrentZoomLevel < mMaxZoomLevel) {
             /*   mScaleFactor = */
                displayAllImageForZoomLevel(++mCurrentZoomLevel);
                mScaleFactor = 1.f;

            } else if (mScaleFactor <= mLowerScaleFactor && mCurrentZoomLevel > 0) {
                /*mScaleFactor = */
                displayAllImageForZoomLevel(--mCurrentZoomLevel);
                mScaleFactor = mHigherScaleFactor;
            }

            scaleAllChildren(pivotX, pivotY);

            invalidate();
            return true;
        }

        private void scaleAllChildren(float pivotX, float pivotY) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
//                child.setPivotX(getWidth()/2f  - child.getX());
//                child.setPivotY(getHeight()/2f - child.getY() );
                child.setPivotX(pivotX - child.getX());
                child.setPivotY(pivotY - child.getY());
                child.setScaleX(mScaleFactor);
                child.setScaleY(mScaleFactor);
            }
        }

    }


}
