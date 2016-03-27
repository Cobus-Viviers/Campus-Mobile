package cobus.project;

import android.os.Parcelable;

/**
 * Created by Tsuki on 2016/03/22.
 */
public class Contact{
    private  int id;
    private String contact;
    private String information;
    private String number;

    public Contact(int id, String contact, String information, String number) {
        this.id = id;
        this.contact = contact;
        this.information = information;
        this.number = number;
    }

    public Contact(String contact, String information, String number) {
        this.contact = contact;
        this.information = information;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
