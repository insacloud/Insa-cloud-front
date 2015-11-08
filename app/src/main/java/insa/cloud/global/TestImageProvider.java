package insa.cloud.global;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import insa.cloud.R;

/**
 * Created by vcaen on 08/11/2015.
 */
public class TestImageProvider implements ImageProvider {

    int[] drawableZoom0 = { R.drawable.timetravel_small_effect};

    int[] drawablesZoom1 = {
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

    private final Context mContext;
    ArrayList<Bitmap[][]> imagesByZoom = new ArrayList<>();

    int[][] zoomImageCount = {
            {1,1},
            {3,4}
    };

    public TestImageProvider(Context context) {
        this.mContext = context;
        populateWithDummy();
    }
    @Override
    public int getMaxZoomLevel() {
        return zoomImageCount.length - 1;
    }

    @Override
    public int getRowCountForZoom(int zoom) {
        if(zoom < zoomImageCount.length) {
            return zoomImageCount[zoom][0];
        }
        return 0;
    }

    @Override
    public int getColCountForZoom(int zoom) {
        if(zoom < zoomImageCount.length) {
            return zoomImageCount[zoom][1];
        }
        return 0;
    }

    @Override
    public int getTillWidth() {
        return 300;
    }

    @Override
    public int getTillHeight() {
        return 300;
    }

    @Override
    public void fillImageViewForCoordinate(ImageView imageView, int zoom, int row, int col) {
        switch (zoom) {
            case 0:
                Picasso.with(mContext).load(drawableZoom0[0]).into(imageView);
                break;
            case 1:
                Picasso.with(mContext).load(drawablesZoom1[col + row * getColCountForZoom(zoom)]).into(imageView);
        }
    }

    @Override
    public void loadMetaData(ImageProvider.LoadedCallBack callBack) {
        callBack.onLoaded();
    }

    public void populateWithDummy() {

        Bitmap[][] imagesZoom0 = new Bitmap[1][1];
        Bitmap initBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.timetravel_small_effect);
        imagesZoom0[0][0] = initBitmap;
        imagesByZoom.add(0, imagesZoom0);

        int colPerRow = getColCountForZoom(1);
        int numberOfRow = getRowCountForZoom(1);
        Bitmap[][] imagesZoom1 = new Bitmap[numberOfRow][colPerRow];
        int j = 0;
        for (int i = 0; i < drawablesZoom1.length; i++) {
            if (i % colPerRow == 0 && i != 0) {
                j++;
            }
            // Add eachBitmap to the
            imagesZoom1[j][i % colPerRow] = BitmapFactory.decodeResource(mContext.getResources(), drawablesZoom1[i]);
        }

        imagesByZoom.add(1, imagesZoom1);
    }
}
