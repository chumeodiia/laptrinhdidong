package com.example.a21112025_retrofit2;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a21112025_retrofit2.api.APIService;
import com.example.a21112025_retrofit2.api.RetrofitClient;
import com.example.a21112025_retrofit2.model.Category;

import java.util.List;

import adapter.CategoryAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcCate;

    //Khai báo Adapter
    CategoryAdapter categoryAdapter;
    APIService apiService;
    List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCategory();//Load dữ liệu cho category
    }
    private void AnhXa() {
        //ánh xạ
        rcCate = (RecyclerView) findViewById(R.id.rc_category);
    }
    private void GetCategory() {
        //Gọi Interface trong APIService
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {
                    categoryList = response.body(); //nhận mảng
                    //khởi tạo Adapter
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categoryList);
                    rcCate.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()
                            , LinearLayoutManager.HORIZONTAL, false);
                    rcCate.setLayoutManager(layoutManager);
                    rcCate.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }
}