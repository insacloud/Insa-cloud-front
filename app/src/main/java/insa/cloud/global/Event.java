package insa.cloud.global;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Paul on 20/10/2015.
 */
public class Event {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private String id;
    private String type;
    private String title;
    private String location;
    private Date startDate;
    private Date endDate;
    private String venue;
    private double longitude;
    private double latitude;
    private String idPoster;
    public Event(String id,String title){
        this(id,null,title,null,null,null,null,0,0,null);
    }
    public Event(String id, String type, String title, String location, Date startDate, Date endDate, String venue, double longitude, double latitude, String idPoster) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idPoster = idPoster;
    }
    public Event(JSONObject jsonObject) throws JSONException, ParseException {
        this(jsonObject.get("id").toString(),
                jsonObject.get("category").toString(),
                jsonObject.get("title").toString(),
                jsonObject.get("location").toString(),
                formatter.parse(jsonObject.get("date_start").toString()),
                formatter.parse((String)jsonObject.get("date_end").toString()),
                jsonObject.get("venue").toString(),
                (double) jsonObject.get("longitude"),
                (double) jsonObject.get("latitude"),
                jsonObject.get("poster").toString());
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getIdPoster() {
        return idPoster;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getVenue() {
        return venue;
    }

    public String getLocation() {
        return location;
    }
    @Override
    public String toString() {
        return title;
    }

}
