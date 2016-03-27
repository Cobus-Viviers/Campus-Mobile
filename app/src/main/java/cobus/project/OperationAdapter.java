package cobus.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Tsuki on 2016/03/22.
 */
public class OperationAdapter extends ArrayAdapter<Operation> {
    public OperationAdapter(Context context, Operation[] values) {
        super(context,R.layout.list_operation, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View intelView = inflater.inflate(R.layout.list_operation, parent, false);

        Operation operation = getItem(position);
        TextView headView = (TextView) intelView.findViewById(R.id.intelLayour_item_headtext);
        TextView subView = (TextView) intelView.findViewById(R.id.intelLayout_item_subtext);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d");
        headView.setText(format.format(operation.getStartDate()));
        subView.setText(operation.getInformation());

        return intelView;

    }
}
