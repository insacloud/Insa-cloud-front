package insa.cloud.global;

import com.android.volley.VolleyError;

/**
 * Created by Paul on 21/10/2015.
 */
public interface RequestCallback<T> {
    public void onResponse(T object);
    public void onErrorResponse(VolleyError error);


}
