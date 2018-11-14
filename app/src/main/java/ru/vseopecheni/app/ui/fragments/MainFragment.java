package ru.vseopecheni.app.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFragment;
import ru.vseopecheni.app.ui.fragments.alarm.AlarmFragment;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseFragment;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFragment;
import ru.vseopecheni.app.ui.model.SaveInfo;
import ru.vseopecheni.app.utils.ImageSaver;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainPresenter<MainMvpView> presenter;

    private ImageView tableFive;
    private Unbinder unbinder;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.table_five_image)
    public void getTable() {
        TableFiveFragment tableFiveFragment = new TableFiveFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(tableFiveFragment);
    }

    @OnClick(R.id.recipes_image)
    public void getRecipes() {
        RecipeFragment recipeFragment = new RecipeFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(recipeFragment);
    }

    @OnClick(R.id.hepatoprotectors)
    public void getHepatoprotectors() {
        HepatoprotectorsFragment hepatoprotectorsFragment = new HepatoprotectorsFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(hepatoprotectorsFragment);
    }

    @OnClick(R.id.menu_week)
    public void getMenuForWeek() {
        MenuWeekFragment menuWeekFragment = new MenuWeekFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(menuWeekFragment);
    }

    @OnClick(R.id.bad_liver)
    public void getLiverDisease() {
        LiverDiseaseFragment liverDiseaseFragment = new LiverDiseaseFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(liverDiseaseFragment);
    }

    @OnClick(R.id.about_liver)
    public void getAboutLiver() {
        AboutLiverFragment aboutLiverFragment = new AboutLiverFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(aboutLiverFragment);
    }

    @OnClick(R.id.how)
    public void getHowToTrate() {
        HowToTreatFragment howToTreatFragment = new HowToTreatFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(howToTreatFragment);
    }

    @OnClick(R.id.alarm)
    public void getAlarm() {
        AlarmFragment alarmFragment = new AlarmFragment();
        ((MainActivity) Objects.requireNonNull(getActivity())).moveToNewFragment(alarmFragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInfoSaved(SaveInfo saveInfo) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput(saveInfo.getResponseProducts().get(0).getId(), MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(saveInfo.getResponseProducts().get(0)).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveProductUpdated(Object o) {

    }

    @Override
    public void onSaveFullUpdated(List<ResponseAbout> responseAbouts) {

    }

    @Override
    public void onSavedMenuForWeek(ResponseMenuForWeek responseMenuForWeek) {

    }

    @Override
    public void onProductsUpdated(List<ResponseProducts> responseProducts) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput("jsonProducts", MODE_PRIVATE);
            String json = new Gson().toJson(responseProducts);
            outputStream.
                    write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSavedRecipes(List<ResponseFullRecipes> responseFullRecipes) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput("jsonRecipes", MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseFullRecipes).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            for (ResponseFullRecipes items : responseFullRecipes) {
                try {
                    Bitmap theBitmap = null;
                    if (items.getContent().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + items.getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + items.getImage())
                                .into(500, 300)
                                .get();
                    }
                    new ImageSaver(getContext()).
                            setFileName(items.getId() + ".jpg")
                            .setDirectoryName("recipes")
                            .save(theBitmap);
                } catch (Exception ignored) {
                }
            }
            getActivity().runOnUiThread(this::hideLoading);
        }).start();
    }
}