package com.heuss.benomari.go4lunch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    private Toolbar mTopToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(getBaseContext(), "HOME", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(getBaseContext(), "DASHBOARD", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
                    Toast.makeText(getBaseContext(), "NOTIFICATION", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
    /*@Override
    public void onBackPressed() {

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mTopToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mTopToolbar.setTitle("I'm Hungry");

        configureNavigationView();
        configureDrawerLayout();
        configureBottomNav();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);

    }

    // Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Configure DrawerLayout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mTopToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void configureBottomNav() {
        BottomNavigationView navigation = findViewById(R.id.bottom_toolbar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Configure action on click of the Drawer item
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        return false;
    }

}




