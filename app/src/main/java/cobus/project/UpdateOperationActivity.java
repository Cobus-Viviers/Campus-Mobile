package cobus.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdateOperationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-d");
    Date date = calendar.getTime();
    Spinner agentSpinner;
    EditText edtInformation;
    Contact[] contacts;
    Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateoperation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = (TextView) findViewById(R.id.txtDatePicker);

        try {
            assert textView != null;
            textView.setText(format.format(date));
        }catch (Exception e){Toast.makeText(UpdateOperationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        try {
        DatabaseHandler dh = new DatabaseHandler(this);

        contacts = dh.getContacts();
        String[] contactStrings = new String[contacts.length];
        for (int i = 0; i<contacts.length; i++){
            contactStrings[i] = contacts[i].getContact();
        }
        ArrayAdapter adapter = new  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agentSpinner = (Spinner) findViewById(R.id.spinnerAgents);
        agentSpinner.setAdapter(adapter);

        edtInformation = (EditText) findViewById(R.id.edtInformation);

        Intent intent = getIntent();


            edtInformation.setText(intent.getExtras().getString("Information"));
            String info = intent.getExtras().getString("Information");
            date = new Date((Long)intent.getExtras().get("StartDate"));
            int id = (int) intent.getExtras().get("ID");
            Contact agent = dh.getContact((int) intent.getExtras().get("Agent"));
            operation = new Operation(id, info, date, agent);
        }catch(Exception e){ Log.v("DEBUG",e.getMessage());}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contacts) {
            Intent openAddContact = new Intent(this, ViewContactsActivity.class);
            startActivity(openAddContact);

        } else if (id == R.id.nav_intel) {
            Intent openViewIntel = new Intent(this, ViewIntelActivity.class);
            startActivity(openViewIntel);

        } else if (id == R.id.nav_locations) {
            Intent openLocationsActivity = new Intent(this, LocationsActivity.class);
            startActivity(openLocationsActivity);

        } else if (id == R.id.nav_operation) {
            Intent openViewOperation = new Intent(this, ViewOperationActivity.class);
            startActivity(openViewOperation);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     //   threat = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    public void onClickDatePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "DatePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        calendar.set(year, month, day);
        date = calendar.getTime();

       TextView textView =  (TextView) findViewById(R.id.txtDatePicker);
        if (textView != null) {
            textView.setText(format.format(date));
        }

    }


    public void onUpdateOperationClick(View view) {
        String information = edtInformation.getText().toString();
        try {
            Contact agent = contacts[agentSpinner.getSelectedItemPosition()];
            if (!information.isEmpty()) {
                 operation = new Operation(operation.getiD(), information, date, agent);
                DatabaseHandler dh = new DatabaseHandler(getBaseContext());
                dh.updateOperation(operation);
                Intent openViewOperations = new Intent(getBaseContext(), ViewOperationActivity.class);
                startActivity(openViewOperations);
            }
        }catch (Exception e){ Toast.makeText(UpdateOperationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();}
        finish();
    }
}
