package ru.vseopecheni.app.ui.fragments.table;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class TableFiveProductsFragment extends BaseFragment {

    @BindView(R.id.text_yes)
    TextView textYes;
    @BindView(R.id.text_no)
    TextView textNo;
    private String yes;
    private String no;
    private Unbinder unbinder;

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
        if (bundle != null) {
            yes = bundle.getString("YES");
            no = bundle.getString("NO");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textYes.setText(Html.fromHtml(yes, Html.FROM_HTML_MODE_COMPACT));
                String text = textYes.getText().toString();
                textYes.setText(text.replaceAll(";", "\n "));
                textNo.setText(Html.fromHtml(no, Html.FROM_HTML_MODE_COMPACT));
                String text2 = textNo.getText().toString();
                textNo.setText(text2.replaceAll(";", "\n "));
            } else {
                textYes.setText(Html.fromHtml(yes));
                String text = textYes.getText().toString();
                textYes.setText(text.replaceAll(";", "\n "));
                textNo.setText(Html.fromHtml(no));
                String text2 = textNo.getText().toString();
                textNo.setText(text2.replaceAll(";", "\n "));
            }
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
