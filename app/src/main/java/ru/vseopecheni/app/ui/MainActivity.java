package ru.vseopecheni.app.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
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
        if (!Objects.equals(savedText, "YES")){
            if (Constant.isInternet(this)){
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void writeToFileFullRecipes(List<ResponseFullRecipes> responseFullRecipesList){
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

    public void writeToFileProducts(List<ResponseProducts> responseProducts){
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
