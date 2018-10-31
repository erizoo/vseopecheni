package ru.vseopecheni.app.ui.fragments.disease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class LiverDiseaseFragment extends BaseFragment implements LiverDiseaseMvpView {

    private Unbinder unbinder;

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

    @Inject
    LiverDiseasePresenter<LiverDiseaseMvpView> presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liver_disease_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Constant.isInternet(getContext())) {
            content.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            v.findViewById(R.id.rl).setVisibility(View.GONE);
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
            webView.loadUrl("https://app.vseopecheni.ru/zabolevaniya-pecheni/");
        } else {
            content.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        super.onViewCreated(view, savedInstanceState);
    }
}
