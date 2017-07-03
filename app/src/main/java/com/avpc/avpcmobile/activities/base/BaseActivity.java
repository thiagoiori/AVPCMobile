package com.avpc.avpcmobile.activities.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.member.MembersActivity;

public class BaseActivity extends FirebaseLoginActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SwitchCompat mSwitchTrackPosition;
    private static final String MENU_ITEM = "menu_item";
    private int mMenuItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };
        mToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        initNavigationViewComponents(navigationView);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MENU_ITEM)) {
            mMenuItemId = getIntent().getExtras().getInt(MENU_ITEM);
            navigationView.setCheckedItem(mMenuItemId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    private void initNavigationViewComponents(NavigationView navigationView) {
        mSwitchTrackPosition = (SwitchCompat) navigationView.getHeaderView(0).findViewById(R.id.menu_track_position_switch);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        if (mMenuItemId == menuItem.getItemId()) {
                            mDrawerLayout.closeDrawers();
                            return true;
                        }

                        mMenuItemId = menuItem.getItemId();

                        Bundle mBundle = new Bundle();
                        mBundle.putInt(MENU_ITEM, menuItem.getItemId());
                        switch (menuItem.getItemId()) {
                            case R.id.nav_mapa:

                                break;
                            case R.id.nav_voluntarios:
                                Intent intent =
                                        new Intent(BaseActivity.this, MembersActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra(MENU_ITEM, R.id.nav_voluntarios);
                                intent.putExtras(mBundle);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Do you really want to logout from the application?")
                .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                })
                .create()
                .show();
    }
}



