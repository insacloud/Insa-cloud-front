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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import insa.cloud.activity.MainActivity;

/**
 * Created by Paul on 21/10/2015.
 */
public class RequestVolley implements  RequestInterface{

    @Override
    public void getEventList(final RequestCallback<Event[]> callback) {
       getEventList(null,callback);
    }

    @Override
    public void getEventList(Location location,final RequestCallback<Event[]> callback) {
        Map<String,String> locations = new HashMap<>();
        if (location!=null) {
            locations.put("latitude", "" + location.getLatitude());
            locations.put("longitude", "" + location.getLongitude());
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url.Events, new JSONObject(locations), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("events");
                    int arrayLength = array.length();
                    Event[] events = new Event[arrayLength];
                    for (int i = 0; i < arrayLength; i++) {
                        events[i] = new Event(array.getJSONObject(i));
                    }
                    callback.onResponse(events);

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onErrorResponse(volleyError);
            }
        });
    }

    @Override
    public void getEvent(String id,final RequestCallback<Event> callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url.Events + id + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    callback.onResponse(new Event(jsonObject));
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
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
                callback.onErrorResponse(error);
            }
        }) {
        };

        // Adding request to request queue
        VolleyController.getInstance().addToRequestQueue(jsonObjReq);
    }
}