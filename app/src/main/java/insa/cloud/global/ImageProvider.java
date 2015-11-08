package insa.cloud.global;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by vcaen on 08/11/2015.
 */
public interface ImageProvider {

    int getMaxZoomLevel();
    int getRowCountForZoom(int zoom);
    int getColCountForZoom(int zoom);
    int getTillWidth();
    int getTillHeight();
    void fillImageViewForCoordinate(ImageView imageView,int zoom, int row, int col);


    void loadMetaData(ImageProvider.LoadedCallBack callBack);


    interface LoadedCallBack {
        void onLoaded();
    }



}
