package cobus.project;

/**
 * Created by Tsuki on 2016/03/22.
 */
public class Intel {
    private int id;
    private String Threat;
    private String Information;

    public Intel(int id, String threat, String information) {
        this.id = id;
        Threat = threat;
        Information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThreat() {
        return Threat;
    }

    public void setThreat(String threat) {
        Threat = threat;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }
}
