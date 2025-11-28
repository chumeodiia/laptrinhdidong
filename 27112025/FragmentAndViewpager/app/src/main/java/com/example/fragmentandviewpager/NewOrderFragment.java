package com.example.fragmentandviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentandviewpager.databinding.FragmentNewOrderBinding;

//import com.example.fragmentandviewpager.databinding.FragmentNeworderBinding;

public class NewOrderFragment extends Fragment {

    FragmentNewOrderBinding binding;

    public NewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentNewOrderBinding.inflate(inflater, container, false);

        //RecyclerView

        return binding.getRoot();
    }
}
