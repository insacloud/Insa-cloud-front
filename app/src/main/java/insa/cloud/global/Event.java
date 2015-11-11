package insa.cloud.global;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public Event(String id, String title) {
        this(id, null, title, null, null, null, null, 0, 0, null);
    }

    public Event(String id, String type, String title, String location, Date startDate, Date endDate, String venue, double longitude, double latitude, String idPoster) {
        this.id = id;
        this.type = type;
        this.title = title.substring(0, Math.min(20, title.length()));
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idPoster = idPoster;
    }

    public static Event fromJson(JSONObject jsonObject) {
        String startDateString = jsonObject.optString("date_start", "2015-11-24T00:00:00Z");
        String endDateString = jsonObject.optString("date_end", "2015-11-24T00:00:00Z");

        Date startDate, endDate;

        try {
            if ("null".equals(startDateString)) {
                startDate = formatter.parse("2015-11-24T00:00:00Z");
            } else {
                startDate = formatter.parse(startDateString);
            }

            if ("null".equals(endDateString)) {
                endDate = formatter.parse("2015-11-24T00:00:00Z");
            } else {
                endDate = formatter.parse(endDateString);
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            startDate = Calendar.getInstance().getTime();
            endDate = Calendar.getInstance().getTime();
        }

        return new Event(jsonObject.optString("id"),
                jsonObject.optString("category"),
                jsonObject.optString("title"),
                jsonObject.optString("location"),
                startDate,
                endDate,
                jsonObject.optString("venue"),
                jsonObject.optDouble("longitude", 0.),
                jsonObject.optDouble("latitude", 0.),
                jsonObject.optString("poster"));
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
