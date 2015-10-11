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


public class EventActivity extends Activity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        Event[] values = new Event[] {
               new Event("Event 1",1),
                new Event("Event 2",2),
                new Event("Event 3",4),
                new Event("Event 4",5),
                new Event("Event 5",3),
                new Event("Event 6",1),
                new Event("Event 7",1),
                new Event("Event 8",1),
                new Event("Event 9",1),
                new Event("Event 10",10)

        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

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
    private class EventListAdapter extends ArrayAdapter<String>{
        private String[] values;
        private int[] ids;
        EventListAdapter(Context context,Event[] list){
            super(context,-1,new String[0]);
            setData(list);
            this.addAll();
        }

        private void setData(Event[] list) {
            int nbItems=list.length;
            values = new String[nbItems];
            ids= new int[nbItems];
            for (int i=0;i<nbItems;i++){
                values[i]=list[i].name;
                ids[i]=list[i].id;
            }
        }

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