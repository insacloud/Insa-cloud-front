package insa.cloud.global;

/**
 * Created by Paul on 20/10/2015.
 */
import android.location.Location;

public interface RequestInterface {
    public Event[] getEventList();
    public Event[] getEventList(Location location);

    public Event getEvent(String id);
}
