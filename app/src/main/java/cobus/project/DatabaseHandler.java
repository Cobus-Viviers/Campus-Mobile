package cobus.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;

/**
 * Created by Cobus Viviers on 2016/03/25.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DailyAgentLife";
    private static final int DATABASE_VERSION = 1;

    private final String TNAME_CONTACT = "tblContact";
    private final String TNAME_OPERATION = "tblOperation";
    private final String TNAME_INTEL = "tblIntel";

    private final String[] COLS_CONTACT;
    private final String[] COLS_OPERATION;
    private final String[] COLS_INTEL;



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v("ProjectSqlDebug", "Constructor called");
        COLS_CONTACT = new String[]{"ID", "Contact", "Information", "Number"};
        COLS_OPERATION = new String[]{"ID", "Agent", "Information", "StartDate"};
        COLS_INTEL = new String[]{"ID", "Information", "Threat"};
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createContactCommand = "CREATE TABLE " + TNAME_CONTACT + "("
                + COLS_CONTACT[0] + " INTEGER PRIMARY KEY, "+""
                + COLS_CONTACT[1] + " VARCHAR, "
                + COLS_CONTACT[2] + " VARCHAR, "
                + COLS_CONTACT[3] + " VARCHAR);";
        Log.v("ProjectSqlDebug", createContactCommand);
        db.execSQL(createContactCommand);

        String createOperationCommand = "CREATE TABLE " + TNAME_OPERATION + "("
                + COLS_OPERATION[0] + " INTEGER PRIMARY KEY, "
                + COLS_OPERATION[1] + " INTEGER, "
                + COLS_OPERATION[2] + " VARCHAR, "
                + COLS_OPERATION[3] + " VARCHAR, "
                + "FOREIGN KEY(" + COLS_OPERATION[1] + ") REFERENCES " + TNAME_CONTACT +"("+ COLS_CONTACT[0] + "));";
        Log.v("ProjectSqlDebug", createOperationCommand);
        db.execSQL(createOperationCommand);

        String createIntelCommand = "CREATE TABLE " + TNAME_INTEL + "("
                + COLS_INTEL[0] + " INTEGER PRIMARY KEY, "+""
                + COLS_INTEL[1] + " VARCHAR, "
                + COLS_INTEL[2] + " VARCHAR) ";
        Log.v("ProjectSqlDebug", createIntelCommand);
        db.execSQL(createIntelCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TNAME_OPERATION);
        db.execSQL("DROP TABLE IF EXISTS "+ TNAME_INTEL);
        db.execSQL("DROP TABLE IF EXISTS " + TNAME_CONTACT);

        onCreate(db);

    }

    //gets a list of all contacts
    public Contact[] getContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TNAME_CONTACT + " ORDER BY  + " + COLS_CONTACT[1] + ";", null);

        Contact[] contacts = new Contact[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String contact = cursor.getString(1);
                String information = cursor.getString(2);
                String number = cursor.getString(3);
                contacts[index] = new Contact(Integer.parseInt(id), contact, information, number);
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }

    public Contact getContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TNAME_CONTACT + " WHERE ID =  '" + id + "';", null);

        Contact contact = null;
        if (cursor.moveToFirst()) {

            contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        return contact;

    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLS_CONTACT[1], contact.getContact());
        values.put(COLS_CONTACT[2], contact.getInformation());
        values.put(COLS_CONTACT[3], contact.getNumber());

        db.insert(TNAME_CONTACT, null, values);
        db.close();
    }

    public void updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLS_CONTACT[1], contact.getContact());
        values.put(COLS_CONTACT[2], contact.getInformation());
        values.put(COLS_CONTACT[3], contact.getNumber());

        db.update(TNAME_CONTACT, values, COLS_CONTACT[0] + " = ?", new String[]{Integer.toString(contact.getId())});
    }

    public Intel[] getIntels() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TNAME_INTEL + " ORDER BY  + " + COLS_INTEL[2] + ";", null);

        Intel[] intel = new Intel[cursor.getCount()];
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String information = cursor.getString(1);
                String threat = cursor.getString(2);
                intel[index] = new Intel(Integer.parseInt(id), threat, information);
                index++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return intel;
    }

    public void addIntel(Intel intel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLS_INTEL[1], intel.getInformation());
        values.put(COLS_INTEL[2], intel.getThreat());

        db.insert(TNAME_INTEL, null, values);
        db.close();
    }

    public void updateIntel(Intel intel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLS_INTEL[1], intel.getInformation());
        values.put(COLS_INTEL[2], intel.getThreat());

        db.update(TNAME_INTEL, values, COLS_INTEL[0] + " = ?", new String[]{Integer.toString(intel.getId())});
    }

    public Operation[] getOperations() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TNAME_OPERATION + " ORDER BY " + COLS_OPERATION[3] + ";", null);

        Operation[] operations = new Operation[cursor.getCount()];
        Log.v("DEBUG", Integer.toString(cursor.getCount()));
        int index = 0;
        try {


            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    Contact agent = getContact(Integer.parseInt(cursor.getString(1)));
                    String information = cursor.getString(2);
                    Date startDate = new Date(Long.parseLong(cursor.getString(3)));
                    operations[index] = new Operation(Integer.parseInt(id), information, startDate, agent);
                    index++;
                } while (cursor.moveToNext());
            }
        }catch (Exception e){Log.v("DEBUG", e.getMessage());}
        cursor.close();
        return operations;
    }

    public void addOperation(Operation operation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLS_OPERATION[1], operation.getAgent().getId());
        values.put(COLS_OPERATION[2], operation.getInformation());
        values.put(COLS_OPERATION[3], Long.toString(operation.getStartDate().getTime()));
        db.insert(TNAME_OPERATION, null, values);
        db.close();
    }

    public void updateOperation(Operation operation){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLS_OPERATION[1], operation.getAgent().getId());
        values.put(COLS_OPERATION[2], operation.getInformation());
        values.put(COLS_OPERATION[3], Long.toString(operation.getStartDate().getTime()));

        db.update(TNAME_OPERATION, values, "ID = ?", new String[]{Integer.toString(operation.getiD())});
    }

    public void delete(int id, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, "ID = ?", new String[]{Integer.toString(id)});
    }

}
