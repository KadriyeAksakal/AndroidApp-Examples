package com.example.fragment_homework;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentSecond extends Fragment {
    TextView tvName;
    TextView tvVersion;
    TextView tvApiNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_second, container,false);

        tvName=viewGroup.findViewById(R.id.tv_Name);
        tvVersion=viewGroup.findViewById(R.id.tv_Version);
        tvApiNo=viewGroup.findViewById(R.id.tv_Api);

        Bundle bundle=this.getArguments();

        if(bundle != null){
            String name=bundle.getString("Name");
            String version=bundle.getString("Version");
            String api=bundle.getString("APINo");

            tvName.setText(name);
            tvVersion.setText(version);
            tvApiNo.setText(api);

        }

        return viewGroup;
    }
}
