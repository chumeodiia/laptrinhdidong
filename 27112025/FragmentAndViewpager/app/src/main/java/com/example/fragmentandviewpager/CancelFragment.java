package com.example.fragmentandviewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentandviewpager.databinding.FragmentCancelBinding;

public class CancelFragment extends Fragment {

    private FragmentCancelBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCancelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}