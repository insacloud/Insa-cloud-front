package insa.cloud.global;

/**
 * Created by Paul on 20/10/2015.
 */
import android.location.Location;

import org.json.JSONObject;

public interface RequestInterface {
    public void getEventList(final RequestCallback<Event[]> callback);
    public void getEventList(Location location,final RequestCallback<Event[]> callback);
    public void getEvent(String id,final RequestCallback<Event> callback);
    public void login(String email,String password,final RequestCallback<JSONObject> callback);
}
