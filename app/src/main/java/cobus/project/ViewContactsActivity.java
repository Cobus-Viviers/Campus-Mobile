package cobus.project;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ViewContactsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Contact[] contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DatabaseHandler DH = new DatabaseHandler(this.getBaseContext());
        contacts = DH.getContacts();
        //Create list view
        ListAdapter adapter = new ContactsAdapter(this, contacts);

        ListView listView = (ListView) findViewById(R.id.listContacts);

        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        final Intent openContactDetialsActivity = new Intent(this, ContactDetailsActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts[position];
                openContactDetialsActivity.putExtra("Contact",contact.getContact() );
                openContactDetialsActivity.putExtra("Information", contact.getInformation());
                openContactDetialsActivity.putExtra("Number", contact.getNumber());
                startActivity(openContactDetialsActivity);
            }
        });


        final Intent openAddContactActivity = new Intent(this, AddContactActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(openAddContactActivity);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() ==   R.id.listContacts){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(contacts[info.position].getContact());
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++){
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        Contact contact = contacts[info.position];
        if (menuItemIndex == 0){
            Intent openEditContactActivity = new Intent(this, UpdateContactActivity.class);
            openEditContactActivity.putExtra("ID", contact.getId());
            openEditContactActivity.putExtra("Contact", contact.getContact());
            openEditContactActivity.putExtra("Information", contact.getInformation());
            openEditContactActivity.putExtra("Number", contact.getNumber());
            startActivity(openEditContactActivity);
        }
        if (menuItemIndex == 1){
            new DatabaseHandler(this).delete(contact.getId(), "tblContact");
            Toast.makeText(ViewContactsActivity.this, contacts[info.position].getContact()+" has been deleted", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());

        }
        return true;
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
}
