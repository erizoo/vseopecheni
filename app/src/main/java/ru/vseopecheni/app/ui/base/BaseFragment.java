package ru.vseopecheni.app.ui.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import java.util.Objects;

import butterknife.Unbinder;
import ru.vseopecheni.app.utils.Constant;

public class BaseFragment extends Fragment {

    public void showLoading() {
        ((BaseActivity)Objects.requireNonNull(getActivity())).showLoading();
    }

    public void showLoadingWithMessage(String title) {
        ((BaseActivity)Objects.requireNonNull(getActivity())).showLoadingWithMessage(title);
    }

    public void hideLoading() {
        ((BaseActivity)Objects.requireNonNull(getActivity())).hideLoading();
    }

}
