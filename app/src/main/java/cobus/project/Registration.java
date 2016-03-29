package cobus.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Registration extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       edtPassword = (EditText) findViewById(R.id.edtPassword);
       edtUsername = (EditText) findViewById(R.id.edtUsername);
    }

    public void onUserRegisterClick(View view) {
        DatabaseHandler handler = new DatabaseHandler(this);
        handler.registerUser(edtUsername.getText().toString(), edtPassword.getText().toString());
        Intent intent  = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
