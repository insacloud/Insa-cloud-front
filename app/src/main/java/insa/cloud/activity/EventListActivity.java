package insa.cloud.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import insa.cloud.R;


public class EventListActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        listView = (ListView) findViewById(R.id.list);

        Event[] values =getEvents();

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,android.R.layout.simple_list_item_1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Event  event    = ((Event)listView.getItemAtPosition(position));
                String itemValue = event.name;
                int idi= event.id;
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue+ "Id:"+ idi, Toast.LENGTH_LONG)
                        .show();

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