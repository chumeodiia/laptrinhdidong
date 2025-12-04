package com.example.a04122025_1;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.a04122025_1.Const.Const;
import com.example.a04122025_1.api.ApiResponse;
import com.example.a04122025_1.api.RetrofitClient;
import com.example.a04122025_1.api.ServiceAPI;
import com.example.a04122025_1.model.RealPathUtil;
import com.example.a04122025_1.model.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAvatarActivity extends AppCompatActivity {

    // Khai báo biến toàn cục
    public static final String[] storage_permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final String[] storage_permissions_33 = {
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO
    };

    public static final int MY_REQUEST_CODE = 100;
    public static final String TAG = ChangeAvatarActivity.class.getName();

    Button btnChoose, btnUpload;
    ImageView imageViewChoose, imageViewUpload;
    EditText editTextUserName;
    TextView textViewUsername;
    private Uri mUri;
    private ProgressDialog mProgressDialog;

    // ActivityResultLauncher
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageViewChoose.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        // Ánh xạ view
        AnhXa();

        // Khởi tạo ProgressDialog
        mProgressDialog = new ProgressDialog(ChangeAvatarActivity.this);
        mProgressDialog.setMessage("Please wait upload...");

        // Xử lý nút Back
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Bắt sự kiện nút chọn ảnh
        btnChoose.setOnClickListener(v -> CheckPermission());

        // Bắt sự kiện upload ảnh
        btnUpload.setOnClickListener(v -> {
            if (mUri != null) {
                UploadImage1();
            } else {
                Toast.makeText(ChangeAvatarActivity.this, "Vui lòng chọn ảnh trước", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewUpload = findViewById(R.id.imgMultipart);
        editTextUserName = findViewById(R.id.editUserName);
        textViewUsername = findViewById(R.id.tvUsername);
        imageViewChoose = findViewById(R.id.imgChoose);
    }

    // Hàm kiểm tra quyền
    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    // Hàm xử lý mở ảnh trên thiết bị
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    // Hàm Upload file lên Server và nhận phản hồi
    public void UploadImage1() {
        mProgressDialog.show();

        String username = editTextUserName.getText().toString().trim();
        if (username.isEmpty()) {
            username = "user_" + System.currentTimeMillis();
        }
        RequestBody requestUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);

        String IMAGE_PATH = RealPathUtil.getRealPath(this, mUri);
        Log.e("FilePath", IMAGE_PATH);
        File file = new File(IMAGE_PATH);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part partbodyavatar =
                MultipartBody.Part.createFormData(Const.MY_IMAGES, file.getName(), requestFile);

        ServiceAPI serviceAPI = RetrofitClient.getService();
        serviceAPI.upload(requestUsername, partbodyavatar).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                mProgressDialog.dismiss();
                Log.e("RAW_RESPONSE", response.errorBody() != null
                        ? response.errorBody().toString()
                        : "NO ERROR BODY");

                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();

                    if (apiResponse.isSuccess() && apiResponse.getResult() != null && apiResponse.getResult().size() > 0) {
                        User user = apiResponse.getResult().get(0);

                        // Hiển thị thông tin
                        textViewUsername.setText(user.getUsername());

                        // Load avatar
                        if (user.getImages() != null && !user.getImages().isEmpty()) {
                            Glide.with(ChangeAvatarActivity.this)
                                    .load(user.getImages())
                                    .into(imageViewUpload);
                        }

                        Toast.makeText(ChangeAvatarActivity.this, apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChangeAvatarActivity.this, "Upload thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangeAvatarActivity.this, "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e(TAG, "Error: " + t.toString());
                Toast.makeText(ChangeAvatarActivity.this, "Gọi API thất bại: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}