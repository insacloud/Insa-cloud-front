package insa.cloud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;


import insa.cloud.R;
import insa.cloud.global.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private Button btnEvents;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnEvents = (Button) findViewById(R.id.btnEvent);
        //Skip to main
        if (!session.isLoggedIn()) {
            btnLogout.setText("Back");
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (session.isLoggedIn()) {
                    session.setLogin(false);
                    try {
                        LoginManager.getInstance().logOut();
                    } catch (Exception e) {

                    }
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

        });
        btnEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (session.isLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
