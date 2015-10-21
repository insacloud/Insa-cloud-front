package insa.cloud.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;


public class ImageContainerLayout extends FrameLayout {

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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

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
