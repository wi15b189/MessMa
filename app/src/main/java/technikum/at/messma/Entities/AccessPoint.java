package technikum.at.messma.Entities;


public class AccessPoint {
    private String idMac;
    private int type;
    private boolean alive;
    private String description;
    private int signal;

    public AccessPoint(String idMac, int type, boolean alive, String description, int signal) {
        this.idMac = idMac;
        this.type = type;
        this.alive = alive;
        this.description = description;
        this.signal = signal;
    }

    public AccessPoint(String idMac, int signal) {
        this.idMac = idMac;
        this.signal = signal;
    }

    public String getIdMac() {
        return idMac;
    }

    public void setIdMac(String idMac) {
        this.idMac = idMac;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
}
