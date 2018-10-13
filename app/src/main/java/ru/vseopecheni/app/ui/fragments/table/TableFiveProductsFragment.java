package ru.vseopecheni.app.ui.fragments.table;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class TableFiveProductsFragment extends BaseFragment {

    private String yes;
    private String no;
    private Unbinder unbinder;

    @BindView(R.id.text_yes)
    TextView textYes;
    @BindView(R.id.text_no)
    TextView textNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.table_five_products_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            yes = bundle.getString("YES");
            no = bundle.getString("NO");
            String resultYes = yes.replaceAll("\r\n\t", "\n- ");
            textYes.setText(resultYes);
            String resultNo = no.replaceAll("\r\n\t", "\n- ");
            textNo.setText(resultNo);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
