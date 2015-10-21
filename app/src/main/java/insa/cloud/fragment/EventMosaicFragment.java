package insa.cloud.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Random;

import insa.cloud.R;
import insa.cloud.global.VolleyController;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventMosaicFragment extends Fragment {
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();
    private String testUrl = "http://lorempixel.com/100/90/?random=";
    public EventMosaicFragment() {
        // Required empty public constructor
    }

    public String getTestUrl(){
        Random generator = new Random();
        int number = generator.nextInt(10000);
        String myUrl = testUrl + Integer.toString(number);
        Log.d("URL",myUrl);
        return myUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_mosaic, container, false);

        if (imageLoader == null)
            imageLoader = VolleyController.getInstance().getImageLoader();
        testData(rootView);
        return rootView;
    }


    public void testData(View rootView) {
        NetworkImageView image = (NetworkImageView) rootView.findViewById(R.id.image1_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image1_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image1_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image1_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image1_5);
        image.setImageUrl(getTestUrl(), imageLoader);

        image = (NetworkImageView) rootView.findViewById(R.id.image2_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image2_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image2_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image2_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image2_5);
        image.setImageUrl(getTestUrl(), imageLoader);

        image = (NetworkImageView) rootView.findViewById(R.id.image3_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image3_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image3_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image3_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image3_5);
        image.setImageUrl(getTestUrl(), imageLoader);

        image = (NetworkImageView) rootView.findViewById(R.id.image4_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image4_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image4_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image4_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image4_5);
        image.setImageUrl(getTestUrl(), imageLoader);

        image = (NetworkImageView) rootView.findViewById(R.id.image5_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image5_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image5_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image5_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image5_5);
        image.setImageUrl(getTestUrl(), imageLoader);

        image = (NetworkImageView) rootView.findViewById(R.id.image6_1);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image6_2);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image6_3);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image6_4);
        image.setImageUrl(getTestUrl(), imageLoader);
        image = (NetworkImageView) rootView.findViewById(R.id.image6_5);
        image.setImageUrl(getTestUrl(), imageLoader);
    }
}
