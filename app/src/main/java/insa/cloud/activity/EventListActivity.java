package insa.cloud.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Toast;

import insa.cloud.R;


public class EventListActivity extends Activity {
    ListView listView ;
    ImageButton addPhotoBtn;
    int selectedIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        listView = (ListView) findViewById(R.id.eventList);
        addPhotoBtn = (ImageButton) findViewById(R.id.addPhotoBtn);
        listView.setSelector(R.drawable.bg_key);
        Event[] values =getEvents();

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,android.R.layout.simple_list_item_1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

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
                    String itemValue = selectedEvent.name;
                    int idi = selectedEvent.id;

                    alertText = "Position :" + selectedIndex + "  ListItem : " + itemValue + "  Id : " + idi;
                }
                    // Show Alert
                    Toast.makeText(getApplicationContext(),alertText, Toast.LENGTH_LONG).show();

                }

        });
    }

    private Event[] getEvents() {
        //Actually use a mock, as web service isn't available
        return new Event[] {
                new Event("Event 1",1),
                new Event("Event 2",2),
                new Event("Event 3",4),
                new Event("Event 4",5),
                new Event("Event 5",3),
                new Event("Event 6",1),
                new Event("Event 7",1),
                new Event("Event 8",1),
                new Event("Event 9",1),
                new Event("Event 10",10),
                new Event("Event 10",10),
                new Event("Event 10",10),
                new Event("Event 10",10),
                new Event("Event 10",10)

        };
    }

    private class Event {
        String name;
        int id;
        Event(String name,int id){
            this.name=name;
            this.id=id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}