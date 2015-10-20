package insa.cloud.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Toast;
import android.location.Location;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationServices;
import insa.cloud.R;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestMock;
import insa.cloud.global.Event;


public class EventListActivity extends Activity implements ConnectionCallbacks,OnConnectionFailedListener{
    ListView listView ;
    ImageButton addPhotoBtn;
    int selectedIndex=-1;
    RequestInterface requests = new RequestMock();
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        listView = (ListView) findViewById(R.id.eventList);
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
                    Intent intent = new Intent(EventListActivity.this, EventInformationActivity.class);
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
        if (mLastLocation != null) {
           events=requests.getEventList(mLastLocation);
        }else{
            events=requests.getEventList();
        }
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,android.R.layout.simple_list_item_1, events);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}