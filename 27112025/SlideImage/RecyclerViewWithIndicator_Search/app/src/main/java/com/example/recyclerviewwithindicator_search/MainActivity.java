package com.example.recyclerviewwithindicator_search;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.recyclerviewwithindicator_search.adapter.IconAdapter;
import com.example.recyclerviewwithindicator_search.model.IconModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcIcon;
    private IconAdapter iconAdapter;
    private List<IconModel> iconList;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcIcon = findViewById(R.id.rcIcon);
        searchView = findViewById(R.id.searchView);
        // RecyclerView nằm ngang
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcIcon.setLayoutManager(layoutManager);

        // dữ liệu mẫu (thay icon/desc theo ý bạn)
        iconList = new ArrayList<>();
        iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));iconList.add(new IconModel(R.drawable.shoppe, "Shopee Food"));
        iconList.add(new IconModel(R.drawable.napthe, "Nạp thẻ"));
        iconList.add(new IconModel(R.drawable.flashsale, "Flash Sale"));
        iconList.add(new IconModel(R.drawable.voucherr, "Voucher"));

        // gắn adp vào recyclerview]
        iconAdapter = new IconAdapter(this, iconList);
        rcIcon.setAdapter(iconAdapter);

        // Snap từng item + indicator
        SnapHelper snapHelper = new LinearSnapHelper();
        // gắn 1 itemdecoration và recyclerview
        snapHelper.attachToRecyclerView(rcIcon);

        rcIcon.addItemDecoration(new LinePagerIndicatorDecoration(this));

        // search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                filterList(query);
                return true;

            }

        });
    }
    private void filterList(String text) {
        List<IconModel> filteredList = new ArrayList<>();
        for (IconModel item : iconList) {
            if (item.getDesc().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu phù hợp", Toast.LENGTH_SHORT).show();
        } else {
            iconAdapter.setFilterList(filteredList);
        }
    }
}
