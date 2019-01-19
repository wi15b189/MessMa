package technikum.at.messma.Legacy;


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
import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.MainActivity;

//THIS CLASS IS NEVER USED AT THE MOMENT SINCE WE DON'T KNOW WHAT HAPPENS WITH THE CONTEXT STUFF OF THE VIEW
public class APScanner {

    WifiManager wifi;
    List<ScanResult> results;
    int size = 0;
    List<AccessPoint> accessPoints = new LinkedList<>();




    /*

    '''''''''''''''''''''''''''''''''''''''''''''''''
    BLUETOOTH
    _________________________________________________
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
    */

}
