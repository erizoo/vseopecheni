package ru.vseopecheni.app.ui.fragments.table;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.utils.Constant;

public class TableFiveFragment extends Fragment implements TableFiveMvpView {

    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String DRINKS_ID = "8945";
    private static final String SOUP_ID = "8946";
    private static final String PORRIDGE_ID = "8947";
    private static final String PASTA_ID = "8948";
    private static final String MEAT_ID = "8949";
    private static final String BREAD_ID = "8950";
    private static final String MILK_ID = "8951";
    private static final String VEG_ID = "8952";
    private static final String FRUIT_ID = "8953";
    private static final String EGGS_ID = "8954";
    private static final String BUTTER_ID = "8955";
    private static final String SNACKS_ID = "8956";
    private static final String SAUSE_ID = "8957";
    private static final String SWEET_ID = "8958";

    @Inject
    TableFivePresenter<TableFiveMvpView> presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.table_five_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.drinks)
    public void getDrinks() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(DRINKS_ID);
        } else {
            moveNewFragmentWithoutInternet(DRINKS_ID);
        }
    }

    @OnClick(R.id.soup)
    public void getSoup() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(SOUP_ID);
        } else {
            moveNewFragmentWithoutInternet(SOUP_ID);
        }
    }

    @OnClick(R.id.porrige)
    public void getPorrige() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(PORRIDGE_ID);
        } else {
            moveNewFragmentWithoutInternet(PORRIDGE_ID);
        }
    }

    @OnClick(R.id.pasta)
    public void getPasta() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(PASTA_ID);
        } else {
            moveNewFragmentWithoutInternet(PASTA_ID);
        }
    }

    @OnClick(R.id.meat)
    public void getMeat() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(MEAT_ID);
        } else {
            moveNewFragmentWithoutInternet(MEAT_ID);
        }
    }

    @OnClick(R.id.bread)
    public void getBread() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(BREAD_ID);
        } else {
            moveNewFragmentWithoutInternet(BREAD_ID);
        }
    }

    @OnClick(R.id.milk)
    public void getMilk() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(MILK_ID);
        } else {
            moveNewFragmentWithoutInternet(MILK_ID);
        }
    }

    @OnClick(R.id.vegetables)
    public void getVegetables() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(VEG_ID);
        } else {
            moveNewFragmentWithoutInternet(VEG_ID);
        }
    }

    @OnClick(R.id.fruits)
    public void getFruits() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(FRUIT_ID);
        } else {
            moveNewFragmentWithoutInternet(FRUIT_ID);
        }
    }

    @OnClick(R.id.eggs)
    public void getEggs() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(EGGS_ID);
        } else {
            moveNewFragmentWithoutInternet(EGGS_ID);
        }
    }

    @OnClick(R.id.butter)
    public void getButter() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(BUTTER_ID);
        } else {
            moveNewFragmentWithoutInternet(BUTTER_ID);
        }
    }

    @OnClick(R.id.snacks)
    public void getSnacks() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(SNACKS_ID);
        } else {
            moveNewFragmentWithoutInternet(SNACKS_ID);
        }
    }

    @OnClick(R.id.sauce)
    public void getSauce() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(SAUSE_ID);
        } else {
            moveNewFragmentWithoutInternet(SAUSE_ID);
        }
    }

    @OnClick(R.id.sweet)
    public void getSweet() {
        if (((MainActivity) Objects.requireNonNull(getActivity())).isInternet()) {
            presenter.getProducts(SWEET_ID);
        } else {
            moveNewFragmentWithoutInternet(SWEET_ID);
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onProductsUpdate(List<ResponseProducts> responseProducts) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput(responseProducts.get(0).getId(), Context.MODE_PRIVATE);
            outputStream.write(new Gson().toJson(responseProducts.get(0)).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString(YES, responseProducts.get(0).getYes()); // Put anything what you want
        bundle.putString(NO, responseProducts.get(0).getNo()); // Put anything what you want
        TableFiveProductsFragment tableFiveProductsFragment = new TableFiveProductsFragment();
        tableFiveProductsFragment.setArguments(bundle);
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(tableFiveProductsFragment);
    }

    public void moveNewFragmentWithoutInternet(String id) {
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
                ResponseProducts responseProducts = gson.fromJson(sb.toString(), ResponseProducts.class);
                Bundle bundle = new Bundle();
                bundle.putString(YES, responseProducts.getYes()); // Put anything what you want
                bundle.putString(NO, responseProducts.getNo()); // Put anything what you want
                TableFiveProductsFragment tableFiveProductsFragment = new TableFiveProductsFragment();
                tableFiveProductsFragment.setArguments(bundle);
                ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(tableFiveProductsFragment);
                stream.close();
            }
        } catch (Exception e) {
            Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
        }
    }
}

