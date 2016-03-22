package cobus.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Tsuki on 2016/03/22.
 */
public class IntelAdapter extends ArrayAdapter<Intel> {
    public IntelAdapter(Context context, Intel[] values) {
        super(context,R.layout.list_intel, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View intelView = inflater.inflate(R.layout.list_intel, parent, false);

        Intel intel = getItem(position);

        TextView headView = (TextView) intelView.findViewById(R.id.intelLayour_item_headtext);
        TextView subView = (TextView) intelView.findViewById(R.id.intelLayout_item_subtext);

        headView.setText(intel.getThreat());
        subView.setText(intel.getInformation());

        return intelView;

    }
}
