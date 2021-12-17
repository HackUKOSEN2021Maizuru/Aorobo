package colob.example.aorobo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;


import colob.example.aorobo.databinding.ActivityMainBinding;
import colob.example.aorobo.db.FavorabilityDataBase;
import colob.example.aorobo.db.FavorabilityDataBaseSingleton;
import colob.example.aorobo.db.ScheduleDataBase;
import colob.example.aorobo.db.ScheduleDataBaseSingleton;
import colob.example.aorobo.db.StudyTimeDataBase;
import colob.example.aorobo.db.StudyTimeDataBaseSingleton;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.id.ccccccc);
        StudyTimeDataBase tdb = StudyTimeDataBaseSingleton.getInstance(getApplicationContext());
        ScheduleDataBase sdb= ScheduleDataBaseSingleton.getInstance(getApplicationContext());
        FavorabilityDataBase fdb= FavorabilityDataBaseSingleton.getInstance(getApplicationContext());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        NavigationView navigationView = binding.navView;
        //setContentView(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_first_title,R.id.nav_home, R.id.nav_gallery,R.id.nav_schedule, R.id.nav_startpage, R.id.nav_talk,R.id.nav_bluetooth, R.id.fragment_container,R.id.nav_chart,R.id.nav_today)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}