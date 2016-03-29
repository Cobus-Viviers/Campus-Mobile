package cobus.project;

import android.content.Context;

/**
 * Created by Cobus Viviers on 2016/03/27.
 */
public class User {
    private String useraname;
    private String password;


    public User(String useraname, String password) {
        this.useraname = useraname;
        this.password = password;
    }

    public String getUseraname() {
        return useraname;
    }

    public String getPassword() {
        return password;
    }
}
