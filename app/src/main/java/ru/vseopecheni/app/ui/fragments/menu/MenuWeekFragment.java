package ru.vseopecheni.app.ui.fragments.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class MenuWeekFragment extends BaseFragment implements MenuForWeekMvpView {

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
    private ResponseMenu responseMenu;

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
            menuForWeekAdapter.setItems(responseMenu.getArray().get(0).getMonday());
        }
    }

    @OnClick(R.id.tuesday)
    public void showTuesday() {
        if (tuesdayLayout.getVisibility() == View.VISIBLE) {
            tuesdayLayout.setVisibility(View.GONE);
        } else {
            tuesdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(1).getTuesday());
        }
    }

    @OnClick(R.id.wednesday)
    public void showWednesday() {
        if (wednesdayLayout.getVisibility() == View.VISIBLE) {
            wednesdayLayout.setVisibility(View.GONE);
        } else {
            wednesdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(2).getWednesday());
        }
    }

    @OnClick(R.id.thursday)
    public void showThursday() {
        if (thursdayLayout.getVisibility() == View.VISIBLE) {
            thursdayLayout.setVisibility(View.GONE);
        } else {
            thursdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(3).getThursday());
        }
    }

    @OnClick(R.id.friday)
    public void showFriday() {
        if (fridayLayout.getVisibility() == View.VISIBLE) {
            fridayLayout.setVisibility(View.GONE);
        } else {
            fridayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(4).getFriday());
        }
    }

    @OnClick(R.id.saturday)
    public void showSaturday() {
        if (saturdayLayout.getVisibility() == View.VISIBLE) {
            saturdayLayout.setVisibility(View.GONE);
        } else {
            saturdayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(5).getSaturday());
        }
    }

    @OnClick(R.id.sunday)
    public void showSunday() {
        if (sundayLayout.getVisibility() == View.VISIBLE) {
            sundayLayout.setVisibility(View.GONE);
        } else {
            sundayLayout.setVisibility(View.VISIBLE);
            menuForWeekAdapter.setItems(responseMenu.getArray().get(6).getSunday());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        presenter.getMenuForWeek();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMenuForWeekUpdated(ResponseMenu responseMenu) {
        this.responseMenu = new ResponseMenu();
        this.responseMenu.setArray(responseMenu.getArray());
    }
}
