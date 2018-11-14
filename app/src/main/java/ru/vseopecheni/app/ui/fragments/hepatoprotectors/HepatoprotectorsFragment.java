package ru.vseopecheni.app.ui.fragments.hepatoprotectors;

import  android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class HepatoprotectorsFragment extends BaseFragment implements HepatoprotectorsMvpView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    HepatoprotectorsPresenter<HepatoprotectorsMvpView> presenter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hepatoprotectors_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        webView.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Constant.isInternet(getContext())) {
            presenter.getFull("210");
            content.setVisibility(View.GONE);
            v.findViewById(R.id.rl).setVisibility(View.GONE);
        } else {
            withoutInternet("210");
            content.setVisibility(View.VISIBLE);
        }
        return v;
    }

    private void withoutInternet(String number) {
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
                title.setText("Гепатопротекторы");
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

    @Override
    public void onFullInfoUpdated(List<ResponseAbout> responseAbouts) {
        title.setText("Гепатопротекторы");
        webView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('header')[0].style.display='none'; " + "document.getElementsById('nav_menu')[0].style.display='none'; " + "})()");
            }
        });
        webView.loadUrl("https://app.vseopecheni.ru/kak-lechit-pechen/gepatoprotektori/");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(responseAbouts.get(0).getContent(), Html.FROM_HTML_MODE_COMPACT));
            content.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            content.setText(Html.fromHtml(responseAbouts.get(0).getContent()));
            content.setMovementMethod(LinkMovementMethod.getInstance());
        }
        scrollView.setVisibility(View.VISIBLE);
    }
}
