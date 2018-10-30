package ru.vseopecheni.app.ui.fragments.treat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class HowToTreatFragment extends BaseFragment {

    private static final String NUMBER = "NUMBER";
    private static final String ID = "ID";
    @BindView(R.id.rl_1)
    RelativeLayout layout1;
    @BindView(R.id.rl_2)
    RelativeLayout layout2;
    @BindView(R.id.rl_3)
    RelativeLayout layout3;
    @BindView(R.id.rl_4)
    RelativeLayout layout4;
    @BindView(R.id.rl_5)
    RelativeLayout layout5;
    @BindView(R.id.rl_6)
    RelativeLayout layout6;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.how_to_trate_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        HowToTreatFullFragment howToTreatFullFragment = new HowToTreatFullFragment();
        Bundle bundle = new Bundle();
        layout1.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "1");
            bundle.putString(ID, "204");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        layout2.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "2");
            bundle.putString(ID, "204");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        layout3.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "3");
            bundle.putString(ID, "210");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        layout4.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "4");
            bundle.putString(ID, "212");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        layout5.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "5");
            bundle.putString(ID, "213");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        layout6.setOnClickListener(v1 -> {
            bundle.putString(NUMBER, "6");
            bundle.putString(ID, "16");
            howToTreatFullFragment.setArguments(bundle);
            ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFullFragment);
        });
        return v;
    }
}
