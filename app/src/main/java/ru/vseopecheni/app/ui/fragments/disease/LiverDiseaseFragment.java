package ru.vseopecheni.app.ui.fragments.disease;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class LiverDiseaseFragment extends BaseFragment implements LiverDiseaseMvpView, View.OnClickListener {

    private Unbinder unbinder;
    private LiverDiseaseFullFragment liverDiseaseFullFragment;
    private Bundle bundle;

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
    @BindView(R.id.layout)
    RelativeLayout relativeLayout;

    @Inject
    LiverDiseasePresenter<LiverDiseaseMvpView> presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.liver_disease_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        bundle = new Bundle();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (Constant.isInternet(getContext())) {
            relativeLayout.setVisibility(View.GONE);
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
            liverDiseaseFullFragment = new LiverDiseaseFullFragment();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        view.findViewById(R.id.c1).setOnClickListener(this);
        view.findViewById(R.id.c2).setOnClickListener(this);
        view.findViewById(R.id.c3).setOnClickListener(this);
        view.findViewById(R.id.c4).setOnClickListener(this);
        view.findViewById(R.id.c5).setOnClickListener(this);
        view.findViewById(R.id.c6).setOnClickListener(this);
        view.findViewById(R.id.c7).setOnClickListener(this);
        view.findViewById(R.id.c8).setOnClickListener(this);
        view.findViewById(R.id.c9).setOnClickListener(this);
        view.findViewById(R.id.c10).setOnClickListener(this);
        view.findViewById(R.id.c11).setOnClickListener(this);
        view.findViewById(R.id.c12).setOnClickListener(this);
        view.findViewById(R.id.c13).setOnClickListener(this);
        view.findViewById(R.id.c14).setOnClickListener(this);
        view.findViewById(R.id.c15).setOnClickListener(this);
        view.findViewById(R.id.c16).setOnClickListener(this);
        view.findViewById(R.id.c17).setOnClickListener(this);
        view.findViewById(R.id.c18).setOnClickListener(this);
        view.findViewById(R.id.c19).setOnClickListener(this);
        view.findViewById(R.id.c20).setOnClickListener(this);
        view.findViewById(R.id.c21).setOnClickListener(this);
        view.findViewById(R.id.c22).setOnClickListener(this);
        view.findViewById(R.id.c23).setOnClickListener(this);
        view.findViewById(R.id.c24).setOnClickListener(this);
        view.findViewById(R.id.c25).setOnClickListener(this);
        view.findViewById(R.id.c26).setOnClickListener(this);
        view.findViewById(R.id.c27).setOnClickListener(this);
        view.findViewById(R.id.c28).setOnClickListener(this);
        view.findViewById(R.id.c29).setOnClickListener(this);
        view.findViewById(R.id.c30).setOnClickListener(this);
        view.findViewById(R.id.c31).setOnClickListener(this);
        view.findViewById(R.id.c32).setOnClickListener(this);
        view.findViewById(R.id.c33).setOnClickListener(this);
        view.findViewById(R.id.c34).setOnClickListener(this);
        view.findViewById(R.id.c35).setOnClickListener(this);
        view.findViewById(R.id.c36).setOnClickListener(this);
        view.findViewById(R.id.c37).setOnClickListener(this);
        view.findViewById(R.id.c38).setOnClickListener(this);
        view.findViewById(R.id.c39).setOnClickListener(this);
        view.findViewById(R.id.c40).setOnClickListener(this);
        view.findViewById(R.id.c41).setOnClickListener(this);
        view.findViewById(R.id.c43).setOnClickListener(this);
        view.findViewById(R.id.c44).setOnClickListener(this);
        view.findViewById(R.id.c45).setOnClickListener(this);
        view.findViewById(R.id.c46).setOnClickListener(this);
        view.findViewById(R.id.c47).setOnClickListener(this);
        view.findViewById(R.id.c48).setOnClickListener(this);
        view.findViewById(R.id.c49).setOnClickListener(this);
        view.findViewById(R.id.c50).setOnClickListener(this);
        view.findViewById(R.id.c51).setOnClickListener(this);
        view.findViewById(R.id.c52).setOnClickListener(this);
        view.findViewById(R.id.c53).setOnClickListener(this);
        view.findViewById(R.id.c54).setOnClickListener(this);
        view.findViewById(R.id.c55).setOnClickListener(this);
        view.findViewById(R.id.c56).setOnClickListener(this);
        view.findViewById(R.id.c57).setOnClickListener(this);
        view.findViewById(R.id.c58).setOnClickListener(this);
        view.findViewById(R.id.c59).setOnClickListener(this);
        view.findViewById(R.id.c60).setOnClickListener(this);
        view.findViewById(R.id.c61).setOnClickListener(this);
        view.findViewById(R.id.c62).setOnClickListener(this);
        view.findViewById(R.id.c63).setOnClickListener(this);
        view.findViewById(R.id.c64).setOnClickListener(this);
        view.findViewById(R.id.c65).setOnClickListener(this);
        view.findViewById(R.id.c66).setOnClickListener(this);
        view.findViewById(R.id.c67).setOnClickListener(this);
        view.findViewById(R.id.c68).setOnClickListener(this);
        view.findViewById(R.id.c69).setOnClickListener(this);
        view.findViewById(R.id.c70).setOnClickListener(this);
        view.findViewById(R.id.c71).setOnClickListener(this);
        view.findViewById(R.id.c72).setOnClickListener(this);
        view.findViewById(R.id.c73).setOnClickListener(this);
        view.findViewById(R.id.c74).setOnClickListener(this);
        view.findViewById(R.id.p3).setOnClickListener(this);
        view.findViewById(R.id.p4).setOnClickListener(this);
        view.findViewById(R.id.p6).setOnClickListener(this);
        view.findViewById(R.id.p9).setOnClickListener(this);
        view.findViewById(R.id.p10).setOnClickListener(this);
        view.findViewById(R.id.p11).setOnClickListener(this);
        view.findViewById(R.id.p13).setOnClickListener(this);
        view.findViewById(R.id.p14).setOnClickListener(this);
        view.findViewById(R.id.p20).setOnClickListener(this);
        view.findViewById(R.id.p25).setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);

    }

    private void moveToFragment(String title, String id) {
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("ID", id);
        liverDiseaseFullFragment.setArguments(bundle);
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(liverDiseaseFullFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.c1:
                moveToFragment(((TextView) v.findViewById(R.id.c1)).getText().toString(), "231");
                break;
            case R.id.c2:
                moveToFragment(((TextView) v.findViewById(R.id.c2)).getText().toString(), "233");
                break;
            case R.id.c3:
                moveToFragment(((TextView) v.findViewById(R.id.c3)).getText().toString(), "235");
                break;
            case R.id.c4:
                moveToFragment(((TextView) v.findViewById(R.id.c4)).getText().toString(), "236");
                break;
            case R.id.c5:
                moveToFragment(((TextView) v.findViewById(R.id.c5)).getText().toString(), "237");
                break;
            case R.id.c6:
                moveToFragment(((TextView) v.findViewById(R.id.c6)).getText().toString(), "238");
                break;
            case R.id.c7:
                moveToFragment(((TextView) v.findViewById(R.id.c7)).getText().toString(), "239");
                break;
            case R.id.c8:
                moveToFragment(((TextView) v.findViewById(R.id.c8)).getText().toString(), "240");
                break;
            case R.id.c9:
                moveToFragment(((TextView) v.findViewById(R.id.c9)).getText().toString(), "241");
                break;
            case R.id.c10:
                moveToFragment(((TextView) v.findViewById(R.id.c10)).getText().toString(), "242");
                break;
            case R.id.c11:
                moveToFragment(((TextView) v.findViewById(R.id.c11)).getText().toString(), "243");
                break;
            case R.id.c12:
                moveToFragment(((TextView) v.findViewById(R.id.c12)).getText().toString(), "244");
                break;
            case R.id.c13:
                moveToFragment(((TextView) v.findViewById(R.id.c13)).getText().toString(), "245");
                break;
            case R.id.c14:
                moveToFragment(((TextView) v.findViewById(R.id.c14)).getText().toString(), "248");
                break;
            case R.id.c15:
                moveToFragment(((TextView) v.findViewById(R.id.c15)).getText().toString(), "249");
                break;
            case R.id.c16:
                moveToFragment(((TextView) v.findViewById(R.id.c16)).getText().toString(), "250");
                break;
            case R.id.c17:
                moveToFragment(((TextView) v.findViewById(R.id.c17)).getText().toString(), "251");
                break;
            case R.id.c18:
                moveToFragment(((TextView) v.findViewById(R.id.c18)).getText().toString(), "252");
                break;
            case R.id.c19:
                moveToFragment(((TextView) v.findViewById(R.id.c19)).getText().toString(), "253");
                break;
            case R.id.c20:
                moveToFragment(((TextView) v.findViewById(R.id.c20)).getText().toString(), "256");
                break;
            case R.id.c21:
                moveToFragment(((TextView) v.findViewById(R.id.c21)).getText().toString(), "257");
                break;
            case R.id.c22:
                moveToFragment(((TextView) v.findViewById(R.id.c22)).getText().toString(), "258");
                break;
            case R.id.c23:
                moveToFragment(((TextView) v.findViewById(R.id.c23)).getText().toString(), "265");
                break;
            case R.id.c24:
                moveToFragment(((TextView) v.findViewById(R.id.c24)).getText().toString(), "266");
                break;
            case R.id.c25:
                moveToFragment(((TextView) v.findViewById(R.id.c25)).getText().toString(), "273");
                break;
            case R.id.c26:
                moveToFragment(((TextView) v.findViewById(R.id.c26)).getText().toString(), "273");
                break;
            case R.id.c27:
                moveToFragment(((TextView) v.findViewById(R.id.c27)).getText().toString(), "278");
                break;
            case R.id.c28:
                moveToFragment(((TextView) v.findViewById(R.id.c28)).getText().toString(), "280");
                break;
            case R.id.c29:
                moveToFragment(((TextView) v.findViewById(R.id.c29)).getText().toString(), "281");
                break;
            case R.id.c30:
                moveToFragment(((TextView) v.findViewById(R.id.c30)).getText().toString(), "282");
                break;
            case R.id.c31:
                moveToFragment(((TextView) v.findViewById(R.id.c31)).getText().toString(), "283");
                break;
            case R.id.c32:
                moveToFragment(((TextView) v.findViewById(R.id.c32)).getText().toString(), "284");
                break;
            case R.id.c33:
                moveToFragment(((TextView) v.findViewById(R.id.c33)).getText().toString(), "286");
                break;
            case R.id.c34:
                moveToFragment(((TextView) v.findViewById(R.id.c34)).getText().toString(), "295");
                break;
            case R.id.c35:
                moveToFragment(((TextView) v.findViewById(R.id.c35)).getText().toString(), "296");
                break;
            case R.id.c36:
                moveToFragment(((TextView) v.findViewById(R.id.c36)).getText().toString(), "297");
                break;
            case R.id.c37:
                moveToFragment(((TextView) v.findViewById(R.id.c37)).getText().toString(), "298");
                break;
            case R.id.c38:
                moveToFragment(((TextView) v.findViewById(R.id.c38)).getText().toString(), "299");
                break;
            case R.id.c39:
                moveToFragment(((TextView) v.findViewById(R.id.c39)).getText().toString(), "300");
                break;
            case R.id.c40:
                moveToFragment(((TextView) v.findViewById(R.id.c40)).getText().toString(), "301");
                break;
            case R.id.c41:
                moveToFragment(((TextView) v.findViewById(R.id.c41)).getText().toString(), "302");
                break;
            case R.id.c43:
                moveToFragment(((TextView) v.findViewById(R.id.c43)).getText().toString(), "303");
                break;
            case R.id.c44:
                moveToFragment(((TextView) v.findViewById(R.id.c44)).getText().toString(), "304");
                break;
            case R.id.c45:
                moveToFragment(((TextView) v.findViewById(R.id.c45)).getText().toString(), "305");
                break;
            case R.id.c46:
                moveToFragment(((TextView) v.findViewById(R.id.c46)).getText().toString(), "306");
                break;
            case R.id.c47:
                moveToFragment(((TextView) v.findViewById(R.id.c47)).getText().toString(), "307");
                break;
            case R.id.c48:
                moveToFragment(((TextView) v.findViewById(R.id.c48)).getText().toString(), "308");
                break;
            case R.id.c49:
                moveToFragment(((TextView) v.findViewById(R.id.c49)).getText().toString(), "95");
                break;
            case R.id.c50:
                moveToFragment(((TextView) v.findViewById(R.id.c50)).getText().toString(), "310");
                break;
            case R.id.c51:
                moveToFragment(((TextView) v.findViewById(R.id.c51)).getText().toString(), "311");
                break;
            case R.id.c52:
                moveToFragment(((TextView) v.findViewById(R.id.c52)).getText().toString(), "312");
                break;
            case R.id.c53:
                moveToFragment(((TextView) v.findViewById(R.id.c53)).getText().toString(), "313");
                break;
            case R.id.c54:
                moveToFragment(((TextView) v.findViewById(R.id.c54)).getText().toString(), "314");
                break;
            case R.id.c55:
                moveToFragment(((TextView) v.findViewById(R.id.c55)).getText().toString(), "315");
                break;
            case R.id.c56:
                moveToFragment(((TextView) v.findViewById(R.id.c56)).getText().toString(), "316");
                break;
            case R.id.c57:
                moveToFragment(((TextView) v.findViewById(R.id.c57)).getText().toString(), "317");
                break;
            case R.id.c58:
                moveToFragment(((TextView) v.findViewById(R.id.c58)).getText().toString(), "318");
                break;
            case R.id.c59:
                moveToFragment(((TextView) v.findViewById(R.id.c59)).getText().toString(), "319");
                break;
            case R.id.c60:
                moveToFragment(((TextView) v.findViewById(R.id.c60)).getText().toString(), "320");
                break;
            case R.id.c61:
                moveToFragment(((TextView) v.findViewById(R.id.c61)).getText().toString(), "321");
                break;
            case R.id.c62:
                moveToFragment(((TextView) v.findViewById(R.id.c62)).getText().toString(), "322");
                break;
            case R.id.c63:
                moveToFragment(((TextView) v.findViewById(R.id.c63)).getText().toString(), "323");
                break;
            case R.id.c64:
                moveToFragment(((TextView) v.findViewById(R.id.c64)).getText().toString(), "324");
                break;
            case R.id.c65:
                moveToFragment(((TextView) v.findViewById(R.id.c65)).getText().toString(), "325");
                break;
            case R.id.c66:
                moveToFragment(((TextView) v.findViewById(R.id.c66)).getText().toString(), "326");
                break;
            case R.id.c67:
                moveToFragment(((TextView) v.findViewById(R.id.c67)).getText().toString(), "327");
                break;
            case R.id.c68:
                moveToFragment(((TextView) v.findViewById(R.id.c68)).getText().toString(), "267");
                break;
            case R.id.c69:
                moveToFragment(((TextView) v.findViewById(R.id.c69)).getText().toString(), "268");
                break;
            case R.id.c70:
                moveToFragment(((TextView) v.findViewById(R.id.c70)).getText().toString(), "269");
                break;
            case R.id.c71:
                moveToFragment(((TextView) v.findViewById(R.id.c71)).getText().toString(), "270");
                break;
            case R.id.c72:
                moveToFragment(((TextView) v.findViewById(R.id.c72)).getText().toString(), "271");
                break;
            case R.id.c73:
                moveToFragment(((TextView) v.findViewById(R.id.c73)).getText().toString(), "272");
                break;
            case R.id.c74:
                moveToFragment(((TextView) v.findViewById(R.id.c74)).getText().toString(), "294");
                break;
            case R.id.p3:
                moveToFragment(((TextView) v.findViewById(R.id.p3)).getText().toString(), "246");
                break;
            case R.id.p4:
                moveToFragment(((TextView) v.findViewById(R.id.p4)).getText().toString(), "247");
                break;
            case R.id.p6:
                moveToFragment(((TextView) v.findViewById(R.id.p6)).getText().toString(), "256");
                break;
            case R.id.p9:
                moveToFragment(((TextView) v.findViewById(R.id.p9)).getText().toString(), "6976");
                break;
            case R.id.p10:
                moveToFragment(((TextView) v.findViewById(R.id.p10)).getText().toString(), "6912");
                break;
            case R.id.p11:
                moveToFragment(((TextView) v.findViewById(R.id.p11)).getText().toString(), "34");
                break;
            case R.id.p13:
                moveToFragment(((TextView) v.findViewById(R.id.p13)).getText().toString(), "287");
                break;
            case R.id.p14:
                moveToFragment(((TextView) v.findViewById(R.id.p14)).getText().toString(), "6980");
                break;
            case R.id.p20:
                moveToFragment(((TextView) v.findViewById(R.id.p20)).getText().toString(), "309");
                break;
            case R.id.p25:
                moveToFragment(((TextView) v.findViewById(R.id.p25)).getText().toString(), "328");
                break;
        }
    }
}
