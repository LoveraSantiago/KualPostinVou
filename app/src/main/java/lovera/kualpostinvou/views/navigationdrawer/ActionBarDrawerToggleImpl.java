package lovera.kualpostinvou.views.navigationdrawer;

import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import lovera.kualpostinvou.views.PrincipalActivity;

public class ActionBarDrawerToggleImpl extends ActionBarDrawerToggle {

    private final PrincipalActivity activity;

    public ActionBarDrawerToggleImpl(PrincipalActivity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        this.activity.getSupportActionBar().setTitle(this.activity.getTitulo());
        this.activity.invalidateOptionsMenu();
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        this.activity.getSupportActionBar().setTitle(this.activity.getTituloOriginal());
        this.activity.invalidateOptionsMenu();
    }
}