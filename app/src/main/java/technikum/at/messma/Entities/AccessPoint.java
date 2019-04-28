package technikum.at.messma.Entities;


import java.util.LinkedList;
import java.util.List;

public class AccessPoint {
    private String idMac;
    private int type;
    private boolean alive;
    private String description;
    private List<Integer> signal = new LinkedList<>();


    public AccessPoint(String idMac, int type, boolean alive, String description, int signal) {
        this.idMac = idMac;
        this.type = type;
        this.alive = alive;
        this.description = description;
        setSignal(signal);
    }

    public AccessPoint(String idMac, int signal) {
        this.idMac = idMac;
        setSignal(signal);
    }

    public AccessPoint(String BSSID, Integer type, boolean status){
        this.type = type;
        this.alive = status;
        this.idMac = BSSID;
    }

    public String getMAC() {
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
        if (description==null || description.isEmpty()) {
            switch (getMAC()){
                case "F2:2F:AB:30:E5:A7":
                    this.description = "mic_candy";
                    break;
                case "E6:F6:3A:CE:F2:B1":
                    this.description = "lemon";
                    break;
                case "FF:03:DC:4E:D1:72":
                    this.description = "mic_beetroot";
                    break;
                default:
                    this.description = "<<unknown>>";
            }
        } else
            this.description = description;
    }

    public void setSignal(int signal) {
        this.signal.add(signal);
    }

    public int getLastSignal() {
        return signal.get(signal.toArray().length - 1);
    }

    public int getStrongestSignal() {
        if (signal != null && signal.size() > 0) {
            if (signal.size() == 1) {
                return getLastSignal();
            }
            int strongest = -99;
            for (int i : signal) {
                if (i > strongest) {
                    strongest = i;
                }
            }
            return strongest;
        } else
            return 0;
    }

    public int getAverageSignal() {
        int sum = 0;
        for (int i : signal) {
            sum += i;
        }
        return sum / signal.size();
    }
}
