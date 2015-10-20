package insa.cloud.global;
import java.util.Date;
/**
 * Created by Paul on 20/10/2015.
 */
public class Event {

    private String id;
    private String type;
    private String title;
    private String location;
    private Date startDate;
    private Date endDate;
    private String venue;
    private double longitude;
    private double latitude;

    public Event(String id,String title){
        this(id,null,title,null,null,null,null,0,0,null);
    }
    public Event(String id, String type, String title, String location, Date startDate, Date endDate, String venue, double longitude, double latitude, byte[] poster) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
        this.longitude = longitude;
        this.latitude = latitude;
        this.poster = poster;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    private byte[] poster;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getPoster() {
        return poster;
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
