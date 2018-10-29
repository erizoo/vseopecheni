package ru.vseopecheni.app.ui.fragments.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;

public class MenuWeekFragment extends BaseFragment implements MenuForWeekMvpView, MenuForWeekAdapter.Callback {

    @BindView(R.id.monday_layout)
    LinearLayout mondayLayout;
    @BindView(R.id.tuesday_layout)
    LinearLayout tuesdayLayout;
    @BindView(R.id.wednesday_layout)
    LinearLayout wednesdayLayout;
    @BindView(R.id.thursday_layout)
    LinearLayout thursdayLayout;
    @BindView(R.id.friday_layout)
    LinearLayout fridayLayout;
    @BindView(R.id.saturday_layout)
    LinearLayout saturdayLayout;
    @BindView(R.id.sunday_layout)
    LinearLayout sundayLayout;

    @BindView(R.id.rv_monday)
    RecyclerView recyclerViewMonday;
    @BindView(R.id.rv_tuesday)
    RecyclerView recyclerViewTuesday;
    @BindView(R.id.rv_wednesday)
    RecyclerView recyclerViewWednesday;
    @BindView(R.id.rv_thursday)
    RecyclerView recyclerViewThursday;
    @BindView(R.id.rv_friday)
    RecyclerView recyclerViewFriday;
    @BindView(R.id.rv_saturday)
    RecyclerView recyclerViewSaturday;
    @BindView(R.id.rv_sunday)
    RecyclerView recyclerViewSunday;

    @Inject
    MenuForWeekPresenter<MenuForWeekMvpView> presenter;
    private Unbinder unbinder;
    private MenuForWeekAdapter menuForWeekAdapter;
    private ResponseMenuForWeek responseMenuForWeek;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_for_week_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        menuForWeekAdapter = new MenuForWeekAdapter();
        responseMenuForWeek = new ResponseMenuForWeek();

        menuForWeekAdapter.setCallback(this);

        recyclerViewMonday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMonday.setAdapter(menuForWeekAdapter);
        recyclerViewTuesday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTuesday.setAdapter(menuForWeekAdapter);
        recyclerViewWednesday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWednesday.setAdapter(menuForWeekAdapter);
        recyclerViewThursday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewThursday.setAdapter(menuForWeekAdapter);
        recyclerViewFriday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFriday.setAdapter(menuForWeekAdapter);
        recyclerViewSaturday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSaturday.setAdapter(menuForWeekAdapter);
        recyclerViewSunday.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSunday.setAdapter(menuForWeekAdapter);

        return v;
    }

    @OnClick(R.id.monday)
    public void showMonday() {
        if (mondayLayout.getVisibility() == View.VISIBLE) {
            mondayLayout.setVisibility(View.GONE);
        } else {
            mondayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getMonday(), "monday");
        }
    }

    @OnClick(R.id.tuesday)
    public void showTuesday() {
        if (tuesdayLayout.getVisibility() == View.VISIBLE) {
            tuesdayLayout.setVisibility(View.GONE);
        } else {
            tuesdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getTuesday(), "tuesday");
        }
    }

    @OnClick(R.id.wednesday)
    public void showWednesday() {
        if (wednesdayLayout.getVisibility() == View.VISIBLE) {
            wednesdayLayout.setVisibility(View.GONE);
        } else {
            wednesdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getWednesday(), "wednesday");
        }
    }

    @OnClick(R.id.thursday)
    public void showThursday() {
        if (thursdayLayout.getVisibility() == View.VISIBLE) {
            thursdayLayout.setVisibility(View.GONE);
        } else {
            thursdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getThursday(), "thursday");
        }
    }

    @OnClick(R.id.friday)
    public void showFriday() {
        if (fridayLayout.getVisibility() == View.VISIBLE) {
            fridayLayout.setVisibility(View.GONE);
        } else {
            fridayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getFriday(), "friday");
        }
    }

    @OnClick(R.id.saturday)
    public void showSaturday() {
        if (saturdayLayout.getVisibility() == View.VISIBLE) {
            saturdayLayout.setVisibility(View.GONE);
        } else {
            saturdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getSaturday(), "saturday");
        }
    }

    @OnClick(R.id.sunday)
    public void showSunday() {
        if (sundayLayout.getVisibility() == View.VISIBLE) {
            sundayLayout.setVisibility(View.GONE);
        } else {
            sundayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenuForWeek.getSunday(), "sunday");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        if (Constant.isInternet(getContext())) {
            showLoading();
            presenter.getMenuForWeek();
        } else {
            FileInputStream stream = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                stream = Objects.requireNonNull(getActivity()).openFileInput("menuForWeek");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } finally {
                    Gson gson = new Gson();
                    ResponseMenuForWeek responseMenuForWeek = gson.fromJson(sb.toString(), ResponseMenuForWeek.class);
                    stream.close();
                    this.responseMenuForWeek.setMonday(responseMenuForWeek.getMonday());
                    this.responseMenuForWeek.setTuesday(responseMenuForWeek.getTuesday());
                    this.responseMenuForWeek.setWednesday(responseMenuForWeek.getWednesday());
                    this.responseMenuForWeek.setThursday(responseMenuForWeek.getThursday());
                    this.responseMenuForWeek.setFriday(responseMenuForWeek.getFriday());
                    this.responseMenuForWeek.setSaturday(responseMenuForWeek.getSaturday());
                    this.responseMenuForWeek.setSunday(responseMenuForWeek.getSunday());
                    hideLoading();
                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
            }
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMenuForWeekUpdated(ResponseMenuForWeek responseMenuForWeek) {
        this.responseMenuForWeek.setMonday(responseMenuForWeek.getMonday());
        this.responseMenuForWeek.setTuesday(responseMenuForWeek.getTuesday());
        this.responseMenuForWeek.setWednesday(responseMenuForWeek.getWednesday());
        this.responseMenuForWeek.setThursday(responseMenuForWeek.getThursday());
        this.responseMenuForWeek.setFriday(responseMenuForWeek.getFriday());
        this.responseMenuForWeek.setSaturday(responseMenuForWeek.getSaturday());
        this.responseMenuForWeek.setSunday(responseMenuForWeek.getSunday());
        hideLoading();
    }

    @Override
    public void showRecipe(String json) {
        Bundle bundle = new Bundle();
        FullRecipeForWeekFragment fullRecipeForWeekFragment = new FullRecipeForWeekFragment();
        bundle.putString("json", json);
        fullRecipeForWeekFragment.setArguments(bundle);
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(fullRecipeForWeekFragment);
    }
}
