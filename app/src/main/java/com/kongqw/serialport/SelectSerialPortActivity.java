package com.kongqw.serialport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kongqw.serialport.adapter.DeviceAdapter;
import com.kongqw.serialportlibrary.Device;
import com.kongqw.serialportlibrary.SerialPortFinder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class SelectSerialPortActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = SelectSerialPortActivity.class.getSimpleName();
    private DeviceAdapter mDeviceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_serial_port);
//        32 30 31 37 31 30 32 36 31 34 34 31 35 30
//        String sss = "313030303030303030313030303030303030303030303030303030303031";
//        List<Byte> list= new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.lv_devices);
//        byte[] srtbyte = {0x32,0x30,0x31,0x37,0x31,0x30,0x32,0x36,0x31,0x34,0x34,0x31,0x35,0x30};
//        String s= null;//第二个参数指定编码方式
//        try {
//            s = new String(srtbyte,"ascii");
//            String hexString = "0010121245";
//            Log.d("===================",convertHexToString(sss));
//
//
//
//            Log.d(">>>>>>>>>>>>>>>>>>>>>", convertStringToHex( hexString));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        Log.d(">>>>>>>>>>>>>>>>>>>>>", StringToHex.stringToHexString());
//        Log.d(">>>>>>>>>>>>>>>>>>>>>", StringToHex.hexStringToString());
//        Log.d(">>>>>>>>>>>>>>>>>>>>>", StringToHex.hexStringToByteArray());
//        Log.d(">>>>>>>>>>>>>>>>>>>>>", StringToHex.bytesToHexString());

         StringToHex.getXor(StringToHex.hexStringToByteArray("4F4F4F4E"));

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        ArrayList<Device> devices = serialPortFinder.getDevices();

        if (listView != null) {
            listView.setEmptyView(findViewById(R.id.tv_empty));
            mDeviceAdapter = new DeviceAdapter(getApplicationContext(), devices);
            listView.setAdapter(mDeviceAdapter);
            listView.setOnItemClickListener(this);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Device device = mDeviceAdapter.getItem(position);

        Intent intent = new Intent(this, SerialPortActivity.class);
        intent.putExtra(SerialPortActivity.DEVICE, device);
        startActivity(intent);
    }

    /**
     * 16进制 转 字符串
     * @param hex
     * @return
     */
    public static String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        // 564e3a322d302e312e34 split into two characters 56, 4e, 3a...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        // System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 字符串转16进制
     * @param str
     * @return
     */
    public String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
    }

}
