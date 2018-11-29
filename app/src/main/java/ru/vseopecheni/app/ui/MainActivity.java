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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseId;
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

    @BindView(R.id.default_rv)
    TextView textView;

    @Inject
    MainActivityPresenter<MainActivityMvpView> presenter;

    private Unbinder unbinder;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private List<ResponseId> responseIds = new ArrayList<>();
    private List<String> noList = new ArrayList<>();
    private List<String> yesList = new ArrayList<>();
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getScreenComponent().inject(this);
        presenter.onAttach(this);
        unbinder = ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        presenter.getProducts();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        moveToNewFragment(new MainFragment());

        searchView = findViewById(R.id.search_view);
        searchView.onActionViewExpanded();

        searchView.setOnCloseListener(() -> {
            textView.setVisibility(View.GONE);
            textView.setText("");
            return false;
        });

        ImageView closeButton = this.searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(v -> {
            searchView.setQuery("", false);
            searchView.clearFocus();
            textView.setVisibility(View.GONE);
            textView.setText("");
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StringBuilder sb = new StringBuilder();
                for (String item : yesList) {
                    if (item.toLowerCase().contains(query.toLowerCase()) || item.toLowerCase().equals(query.toLowerCase())) {
                        sb.append(item).append(" ").append("МОЖНО").append("\n");
                    }
                }
                for (String items : noList) {
                    if (items.toLowerCase().contains(query.toLowerCase()) || items.toLowerCase().equals(query.toLowerCase())) {
                        sb.append(items).append(" ").append("НЕЛЬЗЯ").append("\n");
                    }
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(sb);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StringBuilder sb = new StringBuilder();
                for (String item : yesList) {
                    if (item.toLowerCase().contains(newText.toLowerCase()) || item.toLowerCase().equals(newText.toLowerCase())) {
                        sb.append(item).append(" ").append("МОЖНО").append("\n");
                    }
                }
                for (String items : noList) {
                    if (items.toLowerCase().contains(newText.toLowerCase()) || items.toLowerCase().equals(newText.toLowerCase())) {
                        sb.append(items).append(" ").append("НЕЛЬЗЯ").append("\n");
                    }
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(sb);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            textView.setVisibility(View.GONE);
            textView.setText("");
            return false;
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constant.saveToSharedPreferenceBoolean("JSON_RECIPES_FULL_IS_CHECK", false, this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.begin) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new MainFragment());
        }
        if (id == R.id.table_five) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new TableFiveFragment());
        }
        if (id == R.id.recipient) {
            searchView.setVisibility(View.GONE);
            moveToNewFragment(new RecipeFragment());
        }
        if (id == R.id.menu_week) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new MenuWeekFragment());
        }
        if (id == R.id.hepatoprotectors) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new HepatoprotectorsFragment());
        }
        if (id == R.id.about_liver) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new AboutLiverFragment());
        }
        if (id == R.id.how_to_treat) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new HowToTreatFragment());
        }
        if (id == R.id.liver_disease) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new LiverDiseaseFragment());
        }
        if (id == R.id.alarm_clock) {
            searchView.setVisibility(View.VISIBLE);
            moveToNewFragment(new AlarmFragment());
        }
        if (id == R.id.save_info) {
            DialogInterface.OnClickListener myClickListener = myClickListener = (dialog, which) -> {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        saveAllId();
                        presenter.getProducts();
                        getFull();
                        presenter.saveMenuForWeek();
                        presenter.saveRecipes();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        break;
                    case Dialog.BUTTON_NEUTRAL:
                        break;
                }
            };
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void saveAllId() {
        this.responseIds.add(new ResponseId("197"));
        this.responseIds.add(new ResponseId("166"));
        this.responseIds.add(new ResponseId("9"));
        this.responseIds.add(new ResponseId("8"));
        this.responseIds.add(new ResponseId("10"));
        this.responseIds.add(new ResponseId("11"));
        this.responseIds.add(new ResponseId("2"));
        this.responseIds.add(new ResponseId("3"));
        this.responseIds.add(new ResponseId("4"));
        this.responseIds.add(new ResponseId("5"));
        this.responseIds.add(new ResponseId("6"));
        this.responseIds.add(new ResponseId("210"));
        this.responseIds.add(new ResponseId("204"));
        this.responseIds.add(new ResponseId("212"));
        this.responseIds.add(new ResponseId("213"));
        this.responseIds.add(new ResponseId("16"));
        this.responseIds.add(new ResponseId("231"));
        this.responseIds.add(new ResponseId("233"));
        this.responseIds.add(new ResponseId("235"));
        this.responseIds.add(new ResponseId("236"));
        this.responseIds.add(new ResponseId("237"));
        this.responseIds.add(new ResponseId("239"));
        this.responseIds.add(new ResponseId("240"));
        this.responseIds.add(new ResponseId("241"));
        this.responseIds.add(new ResponseId("242"));
        this.responseIds.add(new ResponseId("243"));
        this.responseIds.add(new ResponseId("244"));
        this.responseIds.add(new ResponseId("245"));
        this.responseIds.add(new ResponseId("248"));
        this.responseIds.add(new ResponseId("249"));
        this.responseIds.add(new ResponseId("250"));
        this.responseIds.add(new ResponseId("251"));
        this.responseIds.add(new ResponseId("252"));
        this.responseIds.add(new ResponseId("253"));
        this.responseIds.add(new ResponseId("256"));
        this.responseIds.add(new ResponseId("257"));
        this.responseIds.add(new ResponseId("258"));
        this.responseIds.add(new ResponseId("265"));
        this.responseIds.add(new ResponseId("266"));
        this.responseIds.add(new ResponseId("273"));
        this.responseIds.add(new ResponseId("278"));
        this.responseIds.add(new ResponseId("280"));
        this.responseIds.add(new ResponseId("281"));
        this.responseIds.add(new ResponseId("282"));
        this.responseIds.add(new ResponseId("283"));
        this.responseIds.add(new ResponseId("284"));
        this.responseIds.add(new ResponseId("286"));
        this.responseIds.add(new ResponseId("295"));
        this.responseIds.add(new ResponseId("296"));
        this.responseIds.add(new ResponseId("297"));
        this.responseIds.add(new ResponseId("298"));
        this.responseIds.add(new ResponseId("299"));
        this.responseIds.add(new ResponseId("300"));
        this.responseIds.add(new ResponseId("301"));
        this.responseIds.add(new ResponseId("302"));
        this.responseIds.add(new ResponseId("303"));
        this.responseIds.add(new ResponseId("304"));
        this.responseIds.add(new ResponseId("305"));
        this.responseIds.add(new ResponseId("306"));
        this.responseIds.add(new ResponseId("307"));
        this.responseIds.add(new ResponseId("308"));
        this.responseIds.add(new ResponseId("95"));
        this.responseIds.add(new ResponseId("310"));
        this.responseIds.add(new ResponseId("311"));
        this.responseIds.add(new ResponseId("312"));
        this.responseIds.add(new ResponseId("313"));
        this.responseIds.add(new ResponseId("314"));
        this.responseIds.add(new ResponseId("315"));
        this.responseIds.add(new ResponseId("316"));
        this.responseIds.add(new ResponseId("317"));
        this.responseIds.add(new ResponseId("318"));
        this.responseIds.add(new ResponseId("319"));
        this.responseIds.add(new ResponseId("320"));
        this.responseIds.add(new ResponseId("321"));
        this.responseIds.add(new ResponseId("322"));
        this.responseIds.add(new ResponseId("323"));
        this.responseIds.add(new ResponseId("324"));
        this.responseIds.add(new ResponseId("325"));
        this.responseIds.add(new ResponseId("326"));
        this.responseIds.add(new ResponseId("327"));
        this.responseIds.add(new ResponseId("267"));
        this.responseIds.add(new ResponseId("268"));
        this.responseIds.add(new ResponseId("269"));
        this.responseIds.add(new ResponseId("270"));
        this.responseIds.add(new ResponseId("271"));
        this.responseIds.add(new ResponseId("272"));
        this.responseIds.add(new ResponseId("294"));
        this.responseIds.add(new ResponseId("246"));
        this.responseIds.add(new ResponseId("247"));
        this.responseIds.add(new ResponseId("256"));
        this.responseIds.add(new ResponseId("6976"));
        this.responseIds.add(new ResponseId("6912"));
        this.responseIds.add(new ResponseId("34"));
        this.responseIds.add(new ResponseId("287"));
        this.responseIds.add(new ResponseId("6980"));
        this.responseIds.add(new ResponseId("309"));
        this.responseIds.add(new ResponseId("328"));
    }

    private void getFull() {
        Runnable runnable = () -> {
            try {
                for (ResponseId items : responseIds) {
                    Thread.sleep(2000);
                    presenter.getFull(items.getId());
                }
            } catch (Exception e) {

            }
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
        for (ResponseProducts items : responseProducts) {
            String[] strNo = Html.fromHtml(items.getNo()).toString().split("\n\n");
            for (int i = 0; i < strNo.length - 1; i++) {
                noList.add(strNo[i]);
            }
            String[] strYes = Html.fromHtml(items.getYes()).toString().split("\n\n");
            for (int i = 0; i < strYes.length - 1; i++) {
                yesList.add(strYes[i]);
            }
        }
        System.out.println();
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

    @Override
    public void onGetedAll(List<ResponseId> responseIds) {
        this.responseIds.addAll(responseIds);
    }

    private void snackbar() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle(R.string.title_dialog);
        adb.setMessage("Загрузка заввершена");
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setCancelable(true);
        adb.create();
        adb.show();
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
