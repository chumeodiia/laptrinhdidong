package com.example.fragmentandviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentandviewpager.databinding.FragmentDeliveryBinding;

public class DeliveryFragment extends Fragment {

    private FragmentDeliveryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}