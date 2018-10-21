package ru.vseopecheni.app.ui.fragments.hepatoprotectors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class HepatoprotectorsFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hepatoprotectors_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        hideLoading();
        return v;
    }

}
