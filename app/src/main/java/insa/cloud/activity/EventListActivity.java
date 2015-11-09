package insa.cloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Toast;
import android.location.Location;

import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationServices;
import insa.cloud.R;
import insa.cloud.global.RequestCallback;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestMock;
import insa.cloud.global.Event;


public class EventListActivity extends Activity implements ConnectionCallbacks,OnConnectionFailedListener{
    ListView listView;
    ImageButton addPhotoBtn;
    int selectedIndex=-1;
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

        addPhotoBtn = (ImageButton) findViewById(R.id.addPhotoBtn);
        listView.setSelector(R.drawable.bg_key);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                view.setSelected(true);
                selectedIndex=position;
            }
        });

        addPhotoBtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alertText="Please, select an event";
                if (selectedIndex>=0 && selectedIndex<listView.getCount()) {
                    Event selectedEvent = (Event) listView.getItemAtPosition(selectedIndex);
                    String id = selectedEvent.getId();
                    Intent intent = new Intent(EventListActivity.this, EventDetailsActivity.class);
                    intent.putExtra("eventID",id);
                    startActivity(intent);
                    }
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
            ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(EventListActivity.this,android.R.layout.simple_list_item_1, events);
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