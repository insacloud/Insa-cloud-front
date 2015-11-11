package insa.cloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import insa.cloud.R;
import insa.cloud.adapter.EventAdapter;
import insa.cloud.global.Event;
import insa.cloud.global.RequestCallback;


public class EventListActivity extends AppCompatActivity implements ConnectionCallbacks,OnConnectionFailedListener{
    ListView listView;
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        listView=(ListView) findViewById(R.id.eventList);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.select_event);
        }
        listView.setSelector(R.drawable.bg_key);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                view.setSelected(true);
                Event selectedEvent = (Event) listView.getItemAtPosition(position);
                String eventId = selectedEvent.getId();
                Intent intent = new Intent(EventListActivity.this, EventDetailsActivity.class);
                intent.putExtra("eventID", eventId);
                intent.putExtra("eventTitle", selectedEvent.getTitle());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Event[] events;
        EventListFiller filler=new EventListFiller();
        if (mLastLocation != null) {
           MainActivity.request.getEventList(mLastLocation, filler);
        }else{
            MainActivity.request.getEventList(filler);
        }

        // Assign adapter to ListView
    }
    public class EventListFiller implements RequestCallback<Event[]>{

        @Override
        public void onResponse(Event[] events) {
            ArrayAdapter<Event> adapter = new EventAdapter(EventListActivity.this,R.layout.event_list_elem, R.id.event_detail_title, events);
            listView.setAdapter(adapter);
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}