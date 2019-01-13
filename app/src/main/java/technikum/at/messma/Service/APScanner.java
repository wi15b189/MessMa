package technikum.at.messma.Service;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;


public class APScanner {

    private ArrayList<AccessPoint> arrayList = new ArrayList<>();
    private List<ScanResult> temp;
    private ArrayAdapter wifi_adapter;
    private BluetoothAdapter mBluetoothAdapter;
    //set to identify the activity request
    public static int REQUEST_BLUETOOTH = 1;
    //accessing application context from a helper class by holding a reference to the activity's context and passing it on invocation to the helper
    private static Context mContext;


    public List<AccessPoint> showResults(){
        arrayList.clear();
        scanWifi();
        bluetoothScanning();
        return arrayList;
    }

    //Casting a Context object to an Activity object
    private WifiManager wifiManager = (WifiManager) ((Activity) mContext).getApplicationContext().getSystemService(Context.WIFI_SERVICE);

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            temp = wifiManager.getScanResults();
            //https://androidforums.com/threads/wifimanager-getscanresults-always-returns-empty-list.1266068/
            //need to enable permission to access localization service
            for (ScanResult scanResult : temp) {
                //if(filterAP(scanResult.BSSID)){
                arrayList.add(new AccessPoint(scanResult.BSSID, 0, true, scanResult.SSID, scanResult.level));
                //}
                wifi_adapter.notifyDataSetChanged();
            }
            ((Activity) mContext).unregisterReceiver(this);
        }
    };

    private void scanWifi(){
        //arrayList.clear();
        ((Activity) mContext).registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        //Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
    }

    private void bluetoothScanning(){

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        ((Activity) mContext).registerReceiver(mReceiver, filter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //Add the name and address to an array adapter to show in a ListView
                //arrayList.add(device.getClass() + "\n" + device.getAddress());
                arrayList.add(new AccessPoint(device.getAddress(), 1, true, device.getName(), device.getBondState()));
            }
        }

    };

}
