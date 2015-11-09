package insa.cloud.global;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import insa.cloud.network.AuthStringRequest;

/**
 * Created by vcaen on 09/11/2015.
 */
public class NetworkImageProvider implements ImageProvider {

    private static final String TAG = NetworkImageProvider.class.getName();


    private final int mEventId;
    private final Context mContext;
    ArrayList<ArrayList<String>> imagesUrl = new ArrayList<>();

    public static final int TILL_SIZE = 512;
    public static final String SERVER = "http://insacloud.thoretton.com";


    public NetworkImageProvider(Context context, int eventId) {
        mEventId = eventId;
        mContext = context;
    }

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
    public void fillImageViewForCoordinate(final ImageView imageView, int zoom, int row, int col) {

        String URL = String.format(SERVER + "/api/mosaics/get_image?level=%d&event=%d", zoom, mEventId);
        if(zoom > 0 ) URL = String.format(URL+"&row=%d&column=%d",row, col);

        StringRequest urlRequest = new AuthStringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String imageUrl) {
                        Picasso.with(mContext).load(imageUrl).into(imageView);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "onErrorResponse() returned: " + volleyError.getMessage());
                    }
                }
        );
        VolleyController.getInstance().addToRequestQueue(urlRequest);

    }

    @Override
    public void loadMetaData(LoadedCallBack callBack) {
        callBack.onLoaded();
    }
}
