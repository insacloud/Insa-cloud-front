package insa.cloud.global;
import android.location.Location;

import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Paul on 20/10/2015.
 */
public class RequestMock implements RequestInterface {

    public void getEventList(RequestCallback<Event[]> callback){
        Event event1 = new Event("0","Concert Transbordeur");
        Event event2 = new Event("1","Fete du saucisson");
        Event[] res =  {event1,event2};
        callback.onResponse(res);
    }

    public void getEventList(Location location,RequestCallback<Event[]> callback){
        Event event1 = new Event("0","Concert Transbordeur");
        Event event2 = new Event("1","Fete du saucisson");
        Event[] res =  {event2,event1};
        callback.onResponse(res);
    }

    public void getEvent(String id,final RequestCallback<Event> callback){
        Date startDate = new GregorianCalendar(2015,10,11,11,11).getTime();
        Date endDate = new GregorianCalendar(2015,11,12,12,12).getTime();
        switch (id){
            case "0":
                callback.onResponse(new Event("0","Concert","Concert Transbordeur","Lyon",startDate,endDate,"Transbordeur",45.7838117,4.8584146,null));
            case "1":
                callback.onResponse(new Event("1","Sauciflard","Fete du saucisson","Lyon",startDate,endDate,"Hotel de ville",45.7674727,4.8313947,null));
        }
    }

    @Override
    public void login(String email, String password, RequestCallback<JSONObject> callback) {

    }
}
