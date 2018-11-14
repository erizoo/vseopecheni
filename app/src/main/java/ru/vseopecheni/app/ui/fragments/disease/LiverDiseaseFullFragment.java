package ru.vseopecheni.app.ui.fragments.disease;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class LiverDiseaseFullFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private Unbinder unbinder;
    private String number;
    private String titleText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liver_disease_full_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            number = bundle.getString("ID");
            titleText = bundle.getString("TITLE");
            withoutInternet(titleText, number);
        }
        return v;
    }

    private void withoutInternet(String titleText, String number) {
        FileInputStream stream = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            stream = Objects.requireNonNull(getActivity()).openFileInput(number);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                Gson gson = new Gson();
                ResponseAbout responseAbouts = gson.fromJson(sb.toString(), ResponseAbout.class);
                title.setText(titleText);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    content.setText(Html.fromHtml(responseAbouts.getContent().replaceAll("<img.+?>", ""), Html.FROM_HTML_MODE_COMPACT));
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    content.setText(Html.fromHtml(responseAbouts.getContent().replaceAll("<img.+?>", "")));
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                }
                scrollView.setVisibility(View.VISIBLE);
                stream.close();
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
        }
    }
}
