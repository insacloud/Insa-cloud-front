package insa.cloud.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import insa.cloud.R;
import insa.cloud.global.Event;
import insa.cloud.global.RequestInterface;
import insa.cloud.global.RequestMock;


public class EventInformationFragment extends Fragment {
    RequestInterface requests= new RequestMock();
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
        DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Event event = requests.getEvent(getActivity().getIntent().getStringExtra("eventID"));
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView date = (TextView) rootView.findViewById(R.id.date);
        TextView location = (TextView) rootView.findViewById(R.id.location);
        TextView nbOfPhoto = (TextView) rootView.findViewById(R.id.nbOfPhoto);

        title.setText(event.getTitle());
        date.setText("From " +dateFormater.format(event.getStartDate())+"\n to "+dateFormater.format(event.getEndDate()));
        location.setText(event.getLocation());
        return rootView;
    }


}
