package cobus.project;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddIntelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    EditText edtInformation;
    Spinner spinnerThreatLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addintel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        edtInformation = (EditText) findViewById(R.id.edtInformation);
        spinnerThreatLevel = (Spinner) findViewById(R.id.spinnerThreatLevel);

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

        } else if (id == R.id.nav_operation) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onAddIntelClick(View view) {
        String information = edtInformation.getText().toString();
        String threat = spinnerThreatLevel.toString();

        if(information.isEmpty()){
            Toast.makeText(this, "Please fill in all text boxes", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                SQLiteDatabase DatabaseManipulator = this.openOrCreateDatabase("DailyAgentLife", MODE_PRIVATE, null);
                DatabaseManipulator.execSQL("CREATE TABLE IF NOT EXISTS tblIntel(ID integer primary key, Information VARCHAR, Threat VARCHAR);");
                DatabaseManipulator.execSQL("INSERT INTO tblIntel(Information, Threat) VALUES('"+information+"','"+threat+"')");
                Intent openViewIntel = new Intent(this, ViewIntelActivity.class);
                startActivity(openViewIntel);

            }catch (Exception e)
            {
                Toast.makeText(AddIntelActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
}
