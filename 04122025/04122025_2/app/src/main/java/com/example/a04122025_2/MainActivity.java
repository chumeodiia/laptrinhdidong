package com.example.a04122025_2;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    // Khai báo biến
    Button btnPaired;
    ListView listDanhSach;
    public static int REQUEST_BLUETOOTH = 1;
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    // 1. Khai báo Launcher (thay thế startActivityForResult)
    private final ActivityResultLauncher<Intent> turnBTONLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Toast.makeText(getApplicationContext(), "Bluetooth đã được bật.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Bluetooth chưa được bật.", Toast.LENGTH_SHORT).show();
                        }
                    });

    // 2. Khai báo và Định nghĩa Listener (khắc phục lỗi myListClickListener)
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Lấy thông tin về thiết bị được chọn (tên và địa chỉ MAC)
            String info = ((TextView) view).getText().toString();
            // Địa chỉ MAC là 17 ký tự cuối (ví dụ: "AA:BB:CC:DD:EE:FF")
            String address = info.substring(info.length() - 17);

            // Tạo Intent để chuyển sang Activity tiếp theo (ví dụ: DeviceControlActivity)
            Intent i = new Intent(MainActivity.this, null); // THAY THẾ 'null' BẰNG TÊN ACTIVITY KẾT NỐI CỦA BẠN

            // Đặt địa chỉ MAC vào Intent
            i.putExtra(EXTRA_ADDRESS, address);
            // startActivity(i); // Bỏ comment khi bạn đã tạo Activity kết nối
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ánh xạ
        btnPaired = (Button) findViewById(R.id.btnTimthietbi);
        listDanhSach = (ListView) findViewById(R.id.ListTb);

        // Kiểm tra thiết bị có Bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null){
            // Show a message that the device has no Bluetooth adapter
            Toast.makeText(getApplicationContext(), "Thiết bị không hỗ trợ Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }else if(!myBluetooth.isEnabled()){
            // Ask to the user turn the bluetooth on
            Intent turnBton = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // Đây là nơi bạn cần yêu cầu quyền (request permission)
                Toast.makeText(getApplicationContext(), "Thiết bị bluetooth chưa bật", Toast.LENGTH_LONG).show();
                return; // Không chạy tiếp nếu chưa có quyền
            }

            Toast.makeText(getApplicationContext(), "Thiết bị bluetooth đã bật", Toast.LENGTH_LONG).show();
            // Sử dụng Launcher đã khai báo
            turnBTONLauncher.launch(turnBton);
        }

        // thực hiện tìm thiết bị
        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList(); // gọi hàm tìm thiết bị
            }
        });
    }

    // viet ham
    private void pairedDevicesList() {
        // 3. Khai báo list ngoài khối if (khắc phục lỗi 'list')
        ArrayList list = new ArrayList();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

            pairedDevices = myBluetooth.getBondedDevices();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice bt : pairedDevices) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Danh sách thiết bị Bluetooth đã bật", Toast.LENGTH_LONG).show();
                        list.add(bt.getName() + "\n" + bt.getAddress()); // Get the device's name and the address
                    }
                }
            }
        }
        else {
            // Trường hợp chưa có quyền
            Toast.makeText(getApplicationContext(), "Chưa có quyền BLUETOOTH_CONNECT.", Toast.LENGTH_LONG).show();
        }

        if (list.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Không tìm thấy thiết bị kết nối.", Toast.LENGTH_LONG).show();
        }

        // Adapter chỉ sử dụng được khi list đã được định nghĩa
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listDanhSach.setAdapter(adapter);
        listDanhSach.setOnItemClickListener(myListClickListener); // Sử dụng Listener đã định nghĩa
    }
}