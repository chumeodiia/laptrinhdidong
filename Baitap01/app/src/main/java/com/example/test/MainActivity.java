package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "KIEM_TRA_SO"; // Biến TAG cho Logcat
    private EditText editTextNumbers;
    private TextView textViewResults;
    private Button buttonSeparate;
    private EditText editText_input;
    private TextView textView_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View cho Bài tập 4
        editTextNumbers = findViewById(R.id.editText_numbers);
        textViewResults = findViewById(R.id.textView_results);
        buttonSeparate = findViewById(R.id.button_separate);

        // Thiết lập sự kiện click
        buttonSeparate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInputNumbers();
            }
        });

        // ------------------------------------------------------------------
        // Bài tập 5 (Đảo chuỗi)
        // ------------------------------------------------------------------
        editText_input = findViewById(R.id.editText_input);
        textView_output = findViewById(R.id.textView_output);
    }
    private void processInputNumbers() {
        String inputString = editTextNumbers.getText().toString().trim();

        if (inputString.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập dãy số!", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Integer> numberList = new ArrayList<>();

        // Tách chuỗi theo dấu phẩy và loại bỏ khoảng trắng
        String[] numberStrs = inputString.split(",");

        for (String s : numberStrs) {
            try {
                // Chuyển chuỗi thành số nguyên
                int number = Integer.parseInt(s.trim());
                numberList.add(number);
            } catch (NumberFormatException e) {
                // Xử lý nếu người dùng nhập ký tự không phải số
                Toast.makeText(this, "Lỗi: Dữ liệu nhập vào phải là số nguyên.", Toast.LENGTH_LONG).show();
                textViewResults.setText("Lỗi: Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
                return;
            }
        }

        // Gọi hàm xử lý chính
        separateEvenOddNumbers(numberList);
    }

    /**
     * Tách số chẵn và số lẻ và in ra Logcat và TextView.
     */
    private void separateEvenOddNumbers(ArrayList<Integer> list) {
        // Chỉ dùng ArrayList để lưu trữ các số duy nhất
        ArrayList<Integer> uniqueEvenNumbers = new ArrayList<>();
        ArrayList<Integer> uniqueOddNumbers = new ArrayList<>();

        for (int number : list) {
            if (number % 2 == 0) {
                // Kiểm tra: nếu số chẵn này CHƯA có trong danh sách chẵn duy nhất, thì thêm vào
                if (!uniqueEvenNumbers.contains(number)) {
                    uniqueEvenNumbers.add(number);
                }
            } else {
                // Kiểm tra: nếu số lẻ này CHƯA có trong danh sách lẻ duy nhất, thì thêm vào
                if (!uniqueOddNumbers.contains(number)) {
                    uniqueOddNumbers.add(number);
                }
            }
        }

        // Sắp xếp (tùy chọn)
        Collections.sort(uniqueEvenNumbers);
        Collections.sort(uniqueOddNumbers);

        // Tạo chuỗi kết quả (Sử dụng TextUtils.join)
        String evenStr = TextUtils.join(", ", uniqueEvenNumbers);
        String oddStr = TextUtils.join(", ", uniqueOddNumbers);

        // In kết quả ra Logcat (Log.d)
        Log.d(TAG, "Dãy số gốc: " + TextUtils.join(", ", list));
        Log.d(TAG, "Các số CHẴN: " + evenStr);
        Log.d(TAG, "Các số LẺ: " + oddStr);

        // In kết quả ra TextView
        String results = "Số chẵn duy nhất: " + evenStr + "\n"
                + "Số lẻ duy nhất: " + oddStr;

        textViewResults.setText(results);
        Toast.makeText(this, "Đã xử lý và in kết quả ra màn hình/Logcat.", Toast.LENGTH_SHORT).show();
    }
    public void processString(View view) {
        // 1. Lấy chuỗi từ EditText
        String inputString = editText_input.getText().toString().trim();

        if (inputString.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập chuỗi ký tự.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. Xử lý chuỗi: Đảo ngược vị trí các từ và In Hoa
        // Tách chuỗi thành các từ dựa trên khoảng trắng
        String[] words = inputString.split("\\s+");

        // Chuyển mảng từ thành List để dùng Collections.reverse()
        List<String> wordList = Arrays.asList(words);
        Collections.reverse(wordList); // Đảo ngược thứ tự các từ

        // Ghép các từ lại thành chuỗi, cách nhau bằng khoảng trắng
        String reversedString = String.join(" ", wordList);

        // In hoa toàn bộ chuỗi kết quả
        String resultString = reversedString.toUpperCase();

        // 3. Hiển thị kết quả ra TextView
        textView_output.setText("Kết quả: " + resultString);

        // 4. In ra Toast chuỗi đảo ngược và in hoa
        Toast.makeText(this, "Chuỗi đảo ngược: " + resultString, Toast.LENGTH_LONG).show();
    }
}