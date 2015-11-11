package insa.cloud.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import insa.cloud.R;
import insa.cloud.activity.MainActivity;
import insa.cloud.global.Event;
import insa.cloud.global.RequestCallback;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestMock;
import insa.cloud.global.TestImageProvider;


public class EventInformationFragment extends Fragment {
    public EventInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_information, container, false);
        final DateFormat dateFormater = new SimpleDateFormat("E d MMM  yyy HH:mm");
        final TextView title = (TextView) rootView.findViewById(R.id.title);
        final TextView date = (TextView) rootView.findViewById(R.id.date);
        final TextView location = (TextView) rootView.findViewById(R.id.location);
        final TextView nbOfPhoto = (TextView) rootView.findViewById(R.id.nbOfPhoto);
        final ImageView poster = (ImageView) rootView.findViewById(R.id.event_poster_image_view);
        MainActivity.request.getEvent(getActivity().getIntent().getStringExtra("eventID"), new RequestCallback<Event>() {
            @Override
            public void onResponse(Event event) {
                title.setText(event.getTitle());
                date.setText("From " + dateFormater.format(event.getStartDate()) + "\n to " + dateFormater.format(event.getEndDate()));
                location.setText(event.getLocation());
                Picasso.with(getContext()).load(event.getIdPoster()).into(poster);

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });



        return rootView;
    }


}
