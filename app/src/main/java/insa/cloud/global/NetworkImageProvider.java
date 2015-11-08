package insa.cloud.global;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by vcaen on 09/11/2015.
 */
public class NetworkImageProvider implements ImageProvider {

    ArrayList<ArrayList<String>> imagesUrl = new ArrayList<>();

    public static final int TILL_SIZE = 512;
    public static final String SERVER = "http://insacloud.thoretton.com";




    @Override
    public int getMaxZoomLevel() {
        return 1;
    }

    @Override
    public int getRowCountForZoom(int zoom) {
        return zoom > 0 ? 4 : 1;
    }

    @Override
    public int getColCountForZoom(int zoom) {
        return zoom > 0 ? 4 : 1;
    }

    @Override
    public int getTillWidth() {
        return TILL_SIZE;
    }

    @Override
    public int getTillHeight() {
        return TILL_SIZE;
    }

    @Override
    public void fillImageViewForCoordinate(ImageView imageView, int zoom, int row, int col) {

    }

    @Override
    public void loadMetaData(LoadedCallBack callBack) {

    }
}
