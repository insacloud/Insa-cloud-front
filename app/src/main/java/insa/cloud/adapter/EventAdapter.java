package insa.cloud.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import insa.cloud.R;
import insa.cloud.global.Event;
import insa.cloud.global.PicassoBlurTransformation;
import insa.cloud.global.Util;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;

/**
 * Created by vcaen on 11/11/2015.
 */
public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, int resource, int textViewResourceId, Event[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  super.getView(position, convertView, parent);
        ImageView poster = (ImageView) view.findViewById(R.id.event_detail_poster);
        View filter =  view.findViewById(R.id.event_detail_color);
        filter.setBackgroundColor(Util.getColorForPosition(position));
        Picasso.with(getContext()).load(getItem(position).getIdPoster())
                //.transform(new ColorFilterTransformation(Util.getColorForPosition(position)))
                .transform(new PicassoBlurTransformation(getContext()))
                .into(poster);

        return view;
    }
}
