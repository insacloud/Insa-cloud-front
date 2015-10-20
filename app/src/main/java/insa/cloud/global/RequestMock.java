package insa.cloud.global;
import android.location.Location;
import java.util.Date;
/**
 * Created by Paul on 20/10/2015.
 */
public class RequestMock implements RequestInterface {

    public Event[] getEventList(){
        Event event1 = new Event("0","Concert Transbordeur");
        Event event2 = new Event("1","Fete du saucisson");
        Event[] res =  {event1,event2};
        return res;
    }

    public Event[] getEventList(Location location){
        Event event1 = new Event("0","Concert Transbordeur");
        Event event2 = new Event("1","Fete du saucisson");
        Event[] res =  {event2,event1};
        return res;
    }

    public Event getEvent(String id){
        Date startDate = new Date(2015,11,11,11,11);
        Date endDate = new Date(2015,12,12,12,12);
        switch (id){
            case "0":
                return new Event("0","Concert","Concert Transbordeur","Lyon",startDate,endDate,"Transbordeur",45.7838117,4.8584146,null);
            case "1":
                return new Event("1","Sauciflard","Fete du saucisson","Lyon",startDate,endDate,"Hotel de ville",45.7674727,4.8313947,null);
        }
        return null;
    }
}
