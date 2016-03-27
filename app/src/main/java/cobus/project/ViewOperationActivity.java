package cobus.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ViewOperationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Operation[] operations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_operation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHandler dh = new DatabaseHandler(this);

        operations = dh.getOperations();

        Log.v("DEBUG", "---" + Integer.toString(operations.length));
        //Create list view
        ListAdapter adapter = new OperationAdapter(this, operations);

        ListView listView = (ListView) findViewById(R.id.listOperation);
        Log.v("DEBUG", "Adapeter Created");
        listView.setAdapter(adapter);
        Log.v("DEBUG", "Adapeter Set");

        registerForContextMenu(listView);
        final Intent openOperationDetailsActivity = new Intent(this, OperationDetailsActivity.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Operation operation = operations[position];
                openOperationDetailsActivity.putExtra("Information", operation.getInformation());
                openOperationDetailsActivity.putExtra("StartDate", operation.getStartDate());
                openOperationDetailsActivity.putExtra("Agent", operation.getAgent().getContact());
                startActivity(openOperationDetailsActivity);
            }
        });


        final Intent openAddOperationActivity = new Intent(this, AddOperationActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(openAddOperationActivity);
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
        if (v.getId() ==   R.id.listOperation){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(operations[info.position].getStartDate().toString());
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
        Operation operation = operations[info.position];
        if (menuItemIndex == 0){
            Intent openEditContactActivity = new Intent(this, UpdateOperationActivity.class);
            openEditContactActivity.putExtra("Information", operation.getInformation());
            openEditContactActivity.putExtra("StartDate", operation.getStartDate().getTime());
            openEditContactActivity.putExtra("Agent", operation.getAgent().getId());
            openEditContactActivity.putExtra("ID", operation.getiD());
            startActivity(openEditContactActivity);
        }
        if (menuItemIndex == 1){
            new DatabaseHandler(this).delete(operation.getiD(), "tblOperation");
            Toast.makeText(ViewOperationActivity.this, "Operation has been deleted", Toast.LENGTH_SHORT).show();
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
            Intent openViewContacts = new Intent(this, ViewContactsActivity.class);
            startActivity(openViewContacts);
        } else if (id == R.id.nav_intel) {
            Intent openViewIntel = new Intent(this, ViewOperationActivity.class);
            startActivity(openViewIntel);

        } else if (id == R.id.nav_locations) {

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
