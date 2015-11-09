package insa.cloud.global;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import insa.cloud.network.AuthJsonRequest;
import insa.cloud.network.AuthStringRequest;

/**
 * Created by vcaen on 09/11/2015.
 */
public class NetworkImageProvider implements ImageProvider {

    private static final String TAG = NetworkImageProvider.class.getName();


    private final String mEventId;
    private final Context mContext;
    HashMap<Long, String> imagesUrl = new HashMap<>();

    public static final int TILL_SIZE = 512;
    public static final String SERVER = "http://insacloud.thoretton.com";
    private String baseUrl;


    public NetworkImageProvider(Context context, String eventId) {
        mEventId = eventId;
        mContext = context;
        baseUrl = (SERVER + "/api/mosaics/get_image?event="+ mEventId);
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
        final long key = hashCoordinate(zoom, col, row);
        if(imagesUrl.containsKey(key)) {
            Picasso.with(mContext).load(imagesUrl.get(key)).into(imageView);
        } else {
            loadUrlForZoom(zoom, new LoadedCallBack() {
                @Override
                public void onLoaded() {
                    Picasso.with(mContext).load(imagesUrl.get(key)).into(imageView);
                }
            });
        }

    }

    @Override
    public void loadMetaData(LoadedCallBack callBack) {
            loadUrlForZoom(0, callBack);
    }

    private void loadUrlForZoom(final int zoomlevel, final LoadedCallBack callBack) {
        if(zoomlevel < 0 || zoomlevel > getMaxZoomLevel()) return;
        String url = String.format(baseUrl + "&level=%d", zoomlevel);
        VolleyController.getInstance().addToRequestQueue(new AuthJsonRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject image = results.getJSONObject(i);
                                int row, col;

                                row = image.isNull("row") ? 0 : image.getInt("row");
                                col = image.isNull("column") ? 0 : image.getInt("column");
                                imagesUrl.put(hashCoordinate(
                                                zoomlevel,
                                                row,
                                                col),
                                        image.getString("image")
                                );
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(callBack != null) callBack.onLoaded();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        ));
    }


    private long hashCoordinate(int zoom, int col, int row) {
        return (col << 16) | row << 8 | zoom;
    }
}
