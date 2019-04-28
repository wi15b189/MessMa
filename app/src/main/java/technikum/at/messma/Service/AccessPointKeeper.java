package technikum.at.messma.Service;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.wifi.ScanResult;

import java.util.ArrayList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;


public class AccessPointKeeper {
    private List<ScanResult> currentlyReceivedAccessPoints;
    private boolean mode_filtered_on;
    private List<AccessPoint> ourAccessPoints;
    private List<AccessPoint> apPackage;
    private boolean mode_tolerance_on;

    public AccessPointKeeper() {
        apPackage = new ArrayList<>();
    }

    /**
     * used to add the bluetooth APs since they don't com in a List
     *
     * @param accessPoint
     * @param intent
     */
    public void addResult(BluetoothDevice accessPoint, Intent intent) {
        if (accessPoint != null) {
            int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            AccessPoint tempAP = new AccessPoint(accessPoint.getAddress(), 1, true, accessPoint.getName(), rssi);
            addAccessPoint(tempAP);
        }
    }

    public void addResult(BluetoothDevice device, int rssi, byte[] scanRecord, boolean mode_filtered_on) {
        this.mode_filtered_on = mode_filtered_on;
        if(device!=null){
            addAccessPoint(new AccessPoint(device.getAddress(),1,true,device.getName(),rssi));
        }
    }

    private void addAccessPoint(AccessPoint tempAP) {
        if (mode_filtered_on) {
            if (filterAP(tempAP.getMAC())) {
                if (itIsaNewOne(tempAP)) {
                    apPackage.add(tempAP);
                } else {
                    if (itIsaNewOne(tempAP)) {
                        apPackage.add(tempAP);
                    }
                }
            }
        } else {
            if (itIsaNewOne(tempAP)) {
                apPackage.add(tempAP);
            } else {
                if (itIsaNewOne(tempAP)) {
                    apPackage.add(tempAP);
                }
            }
        }
    }


    public void addResultList(List<ScanResult> currentlyReceivedAccessPoints, boolean mode_filtered_on) {
        this.currentlyReceivedAccessPoints = currentlyReceivedAccessPoints;
        this.mode_filtered_on = mode_filtered_on;

        //toDo: nice to have, implement sorting by signal strength
        if (mode_filtered_on) {
            for (ScanResult scanResult : currentlyReceivedAccessPoints) {
                if (filterAP(scanResult.BSSID)) {
                    AccessPoint tempAP = new AccessPoint(scanResult.BSSID, 0, true, scanResult.SSID, scanResult.level);
                    if (itIsaNewOne(tempAP))
                        apPackage.add(tempAP);
                }
            }
        } else {
            for (ScanResult scanResult : currentlyReceivedAccessPoints) {
                AccessPoint tempAP = new AccessPoint(scanResult.BSSID, 0, true, scanResult.SSID, scanResult.level);
                if (itIsaNewOne(tempAP))
                    apPackage.add(tempAP);
            }
        }
    }

    private boolean itIsaNewOne(AccessPoint tempAP) {
        if (apPackage.size() == 0)
            return true;
        boolean gotOne = false;
        for (AccessPoint ours : apPackage) {
            if (ours.getMAC().equals(tempAP.getMAC())) {
                //update signal
                /**
                 * Lösungsweg 1:
                 * Bei jedem scan wird die get von dem was wir haben mit der neuen ap.getSignal verglichen
                 * je nach tolerance mode wird entweder die höhere oder die niedere Signalstärke verspeichert.
                 */
                //updateSignalByMode(tempAP, ours);
                /**
                 * Läsungsweg 2: Die signale werden dirket verspeichert, die logik greift erst bei der Ausgabe der Signale
                 */
                ours.setSignal(tempAP.getLastSignal());
                gotOne = true;
            }
        }
        if (gotOne) {
            return false;
        }
        return true;
    }

    private boolean filterAP(String mac) {
        if (ourAccessPoints != null && ourAccessPoints.size() > 0) {
            for (AccessPoint oneOfOurs : ourAccessPoints) {
                if ((oneOfOurs.getMAC().equals(mac))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setToleranceMode(boolean mode_tolerance_on) {
        this.mode_tolerance_on = mode_tolerance_on;
    }

    public void reset() {
        apPackage.clear();
    }

    public void updateKnownAccessPoints(List<AccessPoint> accessPoints) {
        this.ourAccessPoints = accessPoints;
    }

    /**
     * delivers the package that will be saved in the database
     *
     * @return
     */
    public List<AccessPoint> getDelivery() {
        return apPackage;
    }

    public ArrayList<String> getApPackage() {
        ArrayList<String> delivery = new ArrayList<>();
        for (AccessPoint ap : apPackage) {
            delivery.add(ap.toString());
        }
        return delivery;
    }

    private void updateSignalByMode(AccessPoint tempAP, AccessPoint ours) {
        if (mode_tolerance_on)
            ours.setSignal(ours.getLastSignal() > tempAP.getLastSignal() ? ours.getLastSignal() : tempAP.getLastSignal());
        else
            ours.setSignal(ours.getLastSignal() < tempAP.getLastSignal() ? ours.getLastSignal() : tempAP.getLastSignal());
    }


}
