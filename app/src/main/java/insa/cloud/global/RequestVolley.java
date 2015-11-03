package insa.cloud.global;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import insa.cloud.activity.MainActivity;

/**
 * Created by Paul on 21/10/2015.
 */
public class RequestVolley implements  RequestInterface{
    @Override
    public void getEventList(final RequestCallback<Event[]> callback) {
    }

    @Override
    public void getEventList(Location location,final RequestCallback<Event[]> callback) {
    }

    @Override
    public void getEvent(String id,final RequestCallback<Event> callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url.Events + id + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("allo", "allo");
                callback.onResponse(new Event("a","b"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        VolleyController.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void login(String email, String password,final RequestCallback<JSONObject> callback ) {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("email", email);
            jsonObj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST ,
                Url.Login, jsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                callback.onResponse(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onErrorResponse(null);
            }
        }) {
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
