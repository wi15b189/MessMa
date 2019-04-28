package technikum.at.messma.Service;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RemoteItHandler {

    private static final String TAG = APIService.class.getSimpleName();

    public String getRemoteUrl() {
        String jsonString = "";
        String urlString = "https://api.remot3.it/apv/v27/device/connect";
        String token = getRemoteToken();

        try {
            //setup connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("developerkey", "NURBRUEyODUtRTg0NS00QTMxLTk0N0MtMjUyNEFCMDZDMzgw");
            conn.setRequestProperty("token", token);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(getDeviceData());
            wr.flush();
            wr.close();

            conn.connect();

            //read data
            InputStream in = new BufferedInputStream(conn.getInputStream());
            jsonString = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return getServerUrlFromJsonString(jsonString);
    }

    private String getDeviceData() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("deviceaddress", "80:00:00:00:01:00:1A:77");
            return postData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getRemoteToken() {
        String jsonString = "";
        String urlString = "https://api.remot3.it/apv/v27/user/login";

        try {
            //setup connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("developerkey", "NURBRUEyODUtRTg0NS00QTMxLTk0N0MtMjUyNEFCMDZDMzgw");
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(getLoginData());
            wr.flush();
            wr.close();

            conn.connect();

            //read data
            InputStream in = new BufferedInputStream(conn.getInputStream());
            jsonString = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return getTokenFromJsonString(jsonString);
    }

    private String getLoginData() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("password", "LN86nfNj56Fg6D");
            postData.put("username", "khaleds@outlook.com");
            return postData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getTokenFromJsonString(String json) {
        if (json == null) {
            return null;
        }
        String tempToken;
        try {
            JSONObject jsonObj = new JSONObject(json);
            // Getting JSON Array node
            tempToken = jsonObj.getString("token");
        } catch (Exception e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            return "";
        }

        return tempToken;
    }

    public String getServerUrlFromJsonString(String json) {
        if (json == null) {
            return null;
        }
        String tempDeviceUrl;
        try {
            JSONObject jsonObj = new JSONObject(json);
            // Getting JSON Array node
            tempDeviceUrl = jsonObj.getJSONObject("connection").getString("proxy");
        } catch (Exception e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            return "";
        }

        return tempDeviceUrl;
    }
}
