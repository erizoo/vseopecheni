package ru.vseopecheni.app.ui.fragments.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class AboutLiverFragment extends BaseFragment {

    private static final String NUMBER = "NUMBER";
    private Unbinder unbinder;

    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.layout4)
    LinearLayout layout4;
    @BindView(R.id.layout5)
    LinearLayout layout5;
    @BindView(R.id.layout6)
    LinearLayout layout6;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_liver_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        AboutLiverFull aboutLiverFull = new AboutLiverFull();
        Bundle bundle = new Bundle();
        layout1.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "1");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        layout2.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "2");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        layout3.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "3");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        layout4.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "4");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        layout5.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "5");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        layout6.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "6");
            aboutLiverFull.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFull);
        });
        return v;
    }

}
