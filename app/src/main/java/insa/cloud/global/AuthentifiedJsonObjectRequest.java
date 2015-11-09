package insa.cloud.global;

import android.util.Base64;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul on 09/11/2015.
 */
public class AuthentifiedJsonObjectRequest extends JsonObjectRequest {
    public AuthentifiedJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(
                "Authorization",
                String.format("Basic %s", Base64.encodeToString(
                        String.format("%s:%s", "admin", "insacloud").getBytes(), Base64.DEFAULT)));
        return params;
    }
}
