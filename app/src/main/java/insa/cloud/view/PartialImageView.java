package insa.cloud.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by vcaen on 08/11/2015.
 */
public class PartialImageView extends ImageView {


    public PartialImageView(Context context) {
        super(context);
        init();
    }

    public PartialImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PartialImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
