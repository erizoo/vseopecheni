package ru.vseopecheni.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.ViewPagerMovement;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class MainFragment extends BaseFragment {

    private ImageView tableFive;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        hideLoading();
        return v;
    }


    @OnClick(R.id.table_five_image)
    public void getTable(){
        TableFiveFragment tableFiveFragment = new TableFiveFragment();
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(tableFiveFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}