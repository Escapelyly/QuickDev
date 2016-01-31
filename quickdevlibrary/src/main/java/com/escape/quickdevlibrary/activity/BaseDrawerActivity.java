package com.escape.quickdevlibrary.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.escape.quickdevlibrary.R;


public class BaseDrawerActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stubs
        setTheme(getThemeId());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_drawerlayout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_open) {

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public int getThemeId() {
        return R.style.Theme_AppCompat_Light_NoActionBar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        return super.onCreateOptionsMenu(menu);
    }

    public void onDrawerClosed(View drawerView) {
    }

    public void onDrawerOpened(View drawerView) {
    }

    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    public void onDrawerStateChanged(int newState) {
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (mDrawerLayout.isDrawerVisible(findViewById(R.id.left_drawer))) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }

    }
}
