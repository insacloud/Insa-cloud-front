package insa.cloud.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import insa.cloud.R;
import insa.cloud.global.Event;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestMock;

public class EventInformationActivity extends ActionBarActivity {
    RequestInterface requests= new RequestMock();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_information);
        DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Event event = requests.getEvent(getIntent().getStringExtra("eventID"));
        TextView title = (TextView) findViewById(R.id.title);
        TextView date = (TextView) findViewById(R.id.date);
        TextView location = (TextView) findViewById(R.id.location);
        TextView nbOfPhoto = (TextView) findViewById(R.id.nbOfPhoto);

        title.setText(event.getTitle());
        date.setText("From " +dateFormater.format(event.getStartDate())+"\n to "+dateFormater.format(event.getEndDate()));
        location.setText(event.getLocation());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
