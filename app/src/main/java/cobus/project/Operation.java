package cobus.project;

import java.util.Date;

/**
 * Created by Cobus Viviers on 2016/03/25.
 */
public class Operation {
    private int iD;
    private String information;
    private Date startDate;
    private Contact agent;

    public Operation(int iD, String information, Date startDate, Contact agent) {
        this.iD = iD;
        this.information = information;
        this.startDate = startDate;
        this.agent = agent;
    }

    public Operation(String information, Date startDate, Contact agent) {
        this.information = information;
        this.startDate = startDate;
        this.agent = agent;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Contact getAgent() {
        return agent;
    }

    public void setAgent(Contact agent) {
        this.agent = agent;
    }
}
