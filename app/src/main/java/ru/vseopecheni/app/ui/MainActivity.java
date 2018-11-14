package ru.vseopecheni.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.fragments.MainFragment;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFragment;
import ru.vseopecheni.app.ui.fragments.alarm.AlarmFragment;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseFragment;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFragment;
import ru.vseopecheni.app.ui.model.SaveInfo;
import ru.vseopecheni.app.utils.Constant;
import ru.vseopecheni.app.utils.ImageSaver;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPagerMovement, MainActivityMvpView {

    @Inject
    MainActivityPresenter<MainActivityMvpView> presenter;

    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getScreenComponent().inject(this);
        presenter.onAttach(this);
        unbinder = ButterKnife.bind(this);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString("SAVE", "");
        if (!Objects.equals(savedText, "YES")) {
            if (Constant.isInternet(this)) {
//                progressDialog = Constant.showLoadingDialog(this);
//                presenter.saveAll();
            } else {
//                Snackbar.make(findViewById(R.id.recipes_image), "Подключите интернет для загрузки данных", Snackbar.LENGTH_LONG).show();
            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        moveToNewFragment(new MainFragment());

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.begin) {
            moveToNewFragment(new MainFragment());
        }
        if (id == R.id.table_five) {
            moveToNewFragment(new TableFiveFragment());
        }
        if (id == R.id.recipient) {
            moveToNewFragment(new RecipeFragment());
        }
        if (id == R.id.menu_week) {
            moveToNewFragment(new MenuWeekFragment());
        }
        if (id == R.id.hepatoprotectors) {
            moveToNewFragment(new HepatoprotectorsFragment());
        }
        if (id == R.id.about_liver) {
            moveToNewFragment(new AboutLiverFragment());
        }
        if (id == R.id.how_to_treat) {
            moveToNewFragment(new HowToTreatFragment());
        }
        if (id == R.id.liver_disease) {
            moveToNewFragment(new LiverDiseaseFragment());
        }
        if (id == R.id.alarm_clock) {
            moveToNewFragment(new AlarmFragment());
        }
        if (id == R.id.save_info) {
            DialogInterface.OnClickListener myClickListener = myClickListener = (dialog, which) -> {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
//                        presenter.getProducts();
//                        getFull();
                        presenter.saveMenuForWeek();
//                        hideLoading();
//                        presenter.saveRecipes();
//                        sharedPreferences = this.getPreferences(MODE_PRIVATE);
//                        SharedPreferences.Editor ed = sharedPreferences.edit();
//                        ed.putBoolean("SAVED", true);
//                        ed.apply();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                    case Dialog.BUTTON_NEUTRAL:
                        break;
                }
            };
            boolean isSaved = false;
            try {
                isSaved = sharedPreferences.getBoolean("SAVED",false);
            } catch (NullPointerException e){
            }
            if (!isSaved){
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle(R.string.title_dialog);
                adb.setMessage(R.string.content_dialog);
                adb.setIcon(android.R.drawable.ic_dialog_info);
                adb.setPositiveButton(R.string.yes_dialog, myClickListener);
                adb.setNegativeButton(R.string.no_dialog, myClickListener);
                adb.setCancelable(false);
                adb.create();
                adb.show();
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getFull() {
        Runnable runnable = () -> {
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
            presenter.getFull("231");
            presenter.getFull("233");
            presenter.getFull("235");
            presenter.getFull("236");
            presenter.getFull("237");
            presenter.getFull("238");
            presenter.getFull("239");
            presenter.getFull("240");
            presenter.getFull("241");
            presenter.getFull("242");
            presenter.getFull("243");
            presenter.getFull("244");
            presenter.getFull("245");
            presenter.getFull("248");
            presenter.getFull("249");
            presenter.getFull("250");
            presenter.getFull("251");
            presenter.getFull("252");
            presenter.getFull("253");
            presenter.getFull("256");
            presenter.getFull("257");
            presenter.getFull("258");
            presenter.getFull("265");
            presenter.getFull("266");
            presenter.getFull("273");
            presenter.getFull("278");
            presenter.getFull("280");
            presenter.getFull("281");
            presenter.getFull("282");
            presenter.getFull("283");
            presenter.getFull("284");
            presenter.getFull("286");
            presenter.getFull("295");
            presenter.getFull("296");
            presenter.getFull("297");
            presenter.getFull("298");
            presenter.getFull("299");
            presenter.getFull("300");
            presenter.getFull("301");
            presenter.getFull("302");
            presenter.getFull("303");
            presenter.getFull("304");
            presenter.getFull("305");
            presenter.getFull("306");
            presenter.getFull("307");
            presenter.getFull("308");
            presenter.getFull("95");
            presenter.getFull("310");
            presenter.getFull("311");
            presenter.getFull("312");
            presenter.getFull("313");
            presenter.getFull("314");
            presenter.getFull("315");
            presenter.getFull("316");
            presenter.getFull("317");
            presenter.getFull("318");
            presenter.getFull("319");
            presenter.getFull("320");
            presenter.getFull("321");
            presenter.getFull("322");
            presenter.getFull("323");
            presenter.getFull("324");
            presenter.getFull("325");
            presenter.getFull("326");
            presenter.getFull("327");
            presenter.getFull("267");
            presenter.getFull("268");
            presenter.getFull("269");
            presenter.getFull("270");
            presenter.getFull("271");
            presenter.getFull("272");
            presenter.getFull("294");
            presenter.getFull("246");
            presenter.getFull("247");
            presenter.getFull("256");
            presenter.getFull("6976");
            presenter.getFull("6912");
            presenter.getFull("34");
            presenter.getFull("287");
            presenter.getFull("6980");
            presenter.getFull("309");
            presenter.getFull("328");
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public boolean isInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void moveTo(Fragment fragment) {
        moveToNewFragment(fragment);
    }

    @Override
    public void onInfoSaved(SaveInfo saveInfo) {
        writeToFileFullRecipes(saveInfo.getResponseFullRecipes());
        writeToFileProducts(saveInfo.getResponseProducts());
        presenter.saveRecipesImages(saveInfo.getResponseFullRecipes(), getContext(), this);
    }

    @Override
    public void onImagesSaved() {
        progressDialog.hide();
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("SAVE", "YES");
        ed.apply();
    }

    @Override
    public void onSaveFullUpdated(List<ResponseAbout> responseAbouts) {
        FileOutputStream outputStream;
        try {
            outputStream = this.openFileOutput(responseAbouts.get(0).getId(), MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseAbouts.get(0)).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProductsUpdated(List<ResponseProducts> responseProducts) {
        FileOutputStream outputStream;
        try {
            outputStream = this.openFileOutput("jsonProducts", MODE_PRIVATE);
            String json = new Gson().toJson(responseProducts);
            outputStream.
                    write(json.getBytes());
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
                outputStream = this.openFileOutput("menuForWeek", MODE_PRIVATE);
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

                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("monday")
                        .save(theBitmap);
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

                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getMonday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("monday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("tuesday")
                        .save(theBitmap);
            } catch (Exception e) {
                Log.e("SAVE_IMAGE_FOR_WEEK", e.getMessage());
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("tuesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("tuesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("tuesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getTuesday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("tuesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("wednesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("wednesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("wednesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("wednesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getWednesday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("wednesday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("thursday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("thursday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("thursday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("thursday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getThursday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("thursday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("friday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("friday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("friday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("friday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getFriday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("friday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("saturday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("saturday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("saturday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("saturday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSaturday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("saturday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getBreakfast().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("breakfast.jpg")
                        .setDirectoryName("sunday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getTiffin().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("tiffin.jpg")
                        .setDirectoryName("sunday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getLunch().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("lunch.jpg")
                        .setDirectoryName("sunday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getAfternoon().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("afternoon.jpg")
                        .setDirectoryName("sunday")
                        .save(theBitmap);
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
                } else {
                    theBitmap = Glide.with(Objects.requireNonNull(getContext()))
                            .asBitmap()
                            .load("https://vseopecheni.ru/" + responseMenuForWeek.getSunday().getDinner().getImgUrl())
                            .into(500, 300)
                            .get();
                }
                new ImageSaver(getContext()).
                        setFileName("dinner.jpg")
                        .setDirectoryName("sunday")
                        .save(theBitmap);
            } catch (Exception ignored) {
            }
        }).start();
    }

    @Override
    public void onSavedRecipes(List<ResponseFullRecipes> responseFullRecipes) {
        FileOutputStream outputStream;
        try {
            outputStream = this.openFileOutput("jsonRecipes", MODE_PRIVATE);
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
            this.runOnUiThread(this::snackbar);
        }).start();
    }

    private void snackbar() {
        Toast.makeText(this, "Загрузка завершена",Toast.LENGTH_LONG).show();
    }

    public void writeToFileFullRecipes(List<ResponseFullRecipes> responseFullRecipesList) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getContext()).openFileOutput("jsonRecipes", Context.MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseFullRecipesList).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFileProducts(List<ResponseProducts> responseProducts) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getContext()).openFileOutput("jsonProducts", Context.MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseProducts).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
