package cobus.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Tsuki on 2016/03/22.
 */
public class ContactsAdapter extends ArrayAdapter<Contact> {
    public ContactsAdapter(Context context, Contact[] values) {
        super(context,R.layout.list_contacts, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        View contactView = inflater.inflate(R.layout.list_contacts, parent, false);

        Contact contact = getItem(position);

        TextView headView = (TextView) contactView.findViewById(R.id.contactLayout_item_headtext);
        TextView subView = (TextView) contactView.findViewById(R.id.contactLayout_item_subtext);

        headView.setText(contact.getContact());
        subView.setText(contact.getInformation());

        return contactView;

    }
}
