package com.heuss.benomari.go4lunch.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heuss.benomari.go4lunch.Fragment.MapViewFragment;
import com.heuss.benomari.go4lunch.R;

import java.util.Arrays;
import java.util.Objects;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar mTopToolbar;
    private static final int RC_SIGN_IN = 123;
    private NavigationView navigationView;
    private Fragment mainFragment;
    private DrawerLayout drawerLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFirstFragment();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!isCurrentUserLogged()){
            startSigningActivity();
        }
        mTopToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mTopToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        mTopToolbar.setTitle("I'm Hungry");
        configureNavigationView();
        configureDrawerLayout();
        configureBottomNav();
        showFirstFragment();


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
        if(id == R.id.activity_main_drawer_logout){
            Disconnect();

        }
        return false;
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            this.showFragment();
        }
    }
    private void showFragment(){
        if (this.mainFragment == null) this.mainFragment = MapViewFragment.newInstance();
        this.startTransactionFragment(this.mainFragment);

    }

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();

        }

    }

    // Disconnect the current User
    public void Disconnect(){
        AuthUI.getInstance().signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startSigningActivity();


                }
        });

    }
    public void startSigningActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.drawable.meal_v3_final)
                        .setAvailableProviders(
                                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), //EMAIL
                                        new AuthUI.IdpConfig.GoogleBuilder().build())) // SUPPORT GOOGLE
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);

    }

    public Boolean isCurrentUserLogged() {
        return (getCurrentUser() != null);
    }
    @Nullable
    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MapViewFragment.Request_User_Location_Code && mainFragment == MapViewFragment.newInstance()){
            mainFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}











