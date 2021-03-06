package ru.vseopecheni.app.ui.fragments.about;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
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

public class AboutLiverFull extends BaseFragment implements AboutLiverFullMvpView {

    private static final String NUMBER = "NUMBER";

    @Inject
    AboutLiverFullPresenter<AboutLiverFullMvpView> presenter;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.scrollView_about)
    ScrollView scrollView;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private String number;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_liver_full_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        webView.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            number = bundle.getString(NUMBER);
            if (Constant.isInternet(getContext())){
                showLoading();
                v.findViewById(R.id.scrollView_about).setVisibility(View.GONE);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
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
                        progressBar.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl("javascript:(function() { " +
                                "document.getElementsByTagName('header')[0].style.display='none'; " + "document.getElementsById('nav_menu')[0].style.display='none'; " + "})()");
                    }
                });
                hideLoading();
                if (number.equals("1")){
                    webView.loadUrl("https://app.vseopecheni.ru/about-liver/slovo-o-pecheni/");
                }
                if (number.equals("2")){
                    webView.loadUrl("https://app.vseopecheni.ru/stroenie-pecheni/");
                }
                if (number.equals("3")){
                    webView.loadUrl("https://app.vseopecheni.ru/about-liver/bolit-li-pechen-/");
                }
                if (number.equals("4")){
                    webView.loadUrl("https://app.vseopecheni.ru/about-liver/5-mifov-o-rabote-pecheni/");
                }
                if (number.equals("5")){
                    webView.loadUrl("https://app.vseopecheni.ru/about-liver/pechen-i-holesterin/");
                }
                if (number.equals("6")){
                    webView.loadUrl("https://app.vseopecheni.ru/about-liver/est-li-svjaz-mezhdu-pokazatelem-indeksa-massi-tela-imt-i-pechenju-/");
                }
            } else {
                hideLoading();
                withoutInternet(number);
                v.findViewById(R.id.scrollView_about).setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
            }
        }
        return v;
    }

    private void withoutInternet(String number) {
        String id = null;
        if (number.equals("1")){
            id = "197";
        }
        if (number.equals("2")){
            id = "166";
        }
        if (number.equals("3")){
            id = "9";
        }
        if (number.equals("4")){
            id = "8";
        }
        if (number.equals("5")){
            id = "10";
        }
        if (number.equals("6")){
            id = "11";
        }
        FileInputStream stream = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            stream = Objects.requireNonNull(getActivity()).openFileInput(id);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                Gson gson = new Gson();
                ResponseAbout responseAbouts = gson.fromJson(sb.toString(), ResponseAbout.class);
                title.setText(responseAbouts.getTitle());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    content.setText(Html.fromHtml(responseAbouts.getContent().replaceAll("<img.+?>", ""), Html.FROM_HTML_MODE_COMPACT));
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                } else {
                    content.setText(Html.fromHtml(responseAbouts.getContent().replaceAll("<img.+?>", "")));
                    content.setMovementMethod(LinkMovementMethod.getInstance());
                }
                scrollView.setVisibility(View.VISIBLE);
                hideLoading();
                stream.close();
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
        }
    }

    @Override
    public void onFullInfoUpdated(List<ResponseAbout> responseAbouts) {
        title.setText(responseAbouts.get(0).getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(responseAbouts.get(0).getContent().replaceAll("<img.+?>", ""), Html.FROM_HTML_MODE_COMPACT));
            content.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            content.setText(Html.fromHtml(responseAbouts.get(0).getContent().replaceAll("<img.+?>", "")));
            content.setMovementMethod(LinkMovementMethod.getInstance());
        }
        hideLoading();
        scrollView.setVisibility(View.VISIBLE);
    }
}
