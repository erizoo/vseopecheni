package ru.vseopecheni.app.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFragment;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseFragment;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFragment;
import ru.vseopecheni.app.ui.model.SaveInfo;
import ru.vseopecheni.app.utils.ImageSaver;

public class MainFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainPresenter<MainMvpView> presenter;

    private ImageView tableFive;
    private Unbinder unbinder;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        OnClickListener myClickListener = myClickListener = (dialog, which) -> {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    showLoading();
                    presenter.getFull("1");
                    presenter.getFull("2");
                    presenter.getFull("3");
                    presenter.getFull("4");
                    presenter.getFull("5");
                    presenter.getFull("6");
                    presenter.getFull("210");
                    presenter.getFull("204");
                    presenter.getFull("212");
                    presenter.getFull("213");
                    presenter.getFull("16");
                    presenter.saveMenuForWeek();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        };

        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setTitle(R.string.title_dialog);
        adb.setMessage(R.string.content_dialog);
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setPositiveButton(R.string.yes_dialog, myClickListener);
        adb.setNegativeButton(R.string.no_dialog, myClickListener);
        adb.setCancelable(false);
        adb.create();
        adb.show();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInfoSaved(SaveInfo saveInfo) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput(saveInfo.getResponseProducts().get(0).getId(), Context.MODE_PRIVATE);
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
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput(responseAbouts.get(0).getId(), Context.MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseAbouts.get(0)).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSavedMenuForWeek(ResponseMenuForWeek responseMenuForWeek) {
        new Thread(() -> {
            FileOutputStream outputStream;
            try {
                outputStream = Objects.requireNonNull(getActivity()).openFileOutput("menuForWeek", Context.MODE_PRIVATE);
                outputStream.
                        write(new Gson().toJson(responseMenuForWeek).getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getMonday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getMonday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getMonday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("monday")
                        .save(theBitmap);
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getMonday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getMonday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getMonday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }

                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("monday")
                        .save(theBitmap);
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getMonday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getMonday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getMonday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }

                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("monday")
                        .save(theBitmap);
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getMonday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getMonday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getMonday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }

                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("monday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getMonday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getMonday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getMonday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }

                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("monday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            //вторник
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getTuesday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getTuesday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getTuesday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("tuesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getTuesday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getTuesday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getTuesday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("tuesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getTuesday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getTuesday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getTuesday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("tuesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getTuesday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getTuesday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getTuesday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("tuesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getTuesday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getTuesday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getTuesday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("tuesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }

            //среда
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getWednesday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getWednesday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getWednesday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("wednesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getWednesday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getWednesday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getWednesday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("wednesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getWednesday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getWednesday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getWednesday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("wednesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getWednesday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getWednesday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getWednesday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("wednesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getWednesday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getWednesday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getWednesday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("wednesday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }

            //четверг
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getThursday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getThursday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getThursday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("thursday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getThursday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getThursday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getThursday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("thursday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getThursday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getThursday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getThursday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("thursday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getThursday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getThursday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getThursday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("thursday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getThursday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getThursday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getThursday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("thursday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }

            //пятница
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getFriday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getFriday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getFriday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("friday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getFriday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getFriday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getFriday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("friday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getFriday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getFriday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getFriday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("friday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getFriday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getFriday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getFriday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("friday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getFriday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getFriday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getFriday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("friday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }

            //суббота
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSaturday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSaturday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSaturday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("saturday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSaturday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSaturday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSaturday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("saturday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSaturday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSaturday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSaturday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("saturday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSaturday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSaturday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSaturday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("saturday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSaturday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSaturday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSaturday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("saturday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }

            //воскресенье
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSunday().getBreakfast().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSunday().getBreakfast().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSunday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getBreakfast().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("breakfast.jpg")
                            .setDirectoryName("sunday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSunday().getTiffin().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSunday().getTiffin().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSunday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getTiffin().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("tiffin.jpg")
                            .setDirectoryName("sunday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSunday().getLunch().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSunday().getLunch().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSunday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getLunch().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("lunch.jpg")
                            .setDirectoryName("sunday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSunday().getAfternoon().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSunday().getAfternoon().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSunday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getAfternoon().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("afternoon.jpg")
                            .setDirectoryName("sunday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
            try {
                Bitmap theBitmap = null;
                if (responseMenuForWeek.getSunday().getDinner().getImgUrl().equals("")) {
                    if (responseMenuForWeek.getSunday().getDinner().getContent().getImage().contains("/f/")) {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru" + responseMenuForWeek.getSunday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getDinner().getContent().getImage())
                                .into(500, 300)
                                .get();
                    }
                }
                else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                    new ImageSaver(getContext()).
                            setFileName("dinner.jpg")
                            .setDirectoryName("sunday")
                            .save(theBitmap);
                }
            } catch (Exception ignored) {
            }
        }).start();
    }
}