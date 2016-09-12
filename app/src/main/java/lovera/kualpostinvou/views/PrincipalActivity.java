package lovera.kualpostinvou.views;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.fragments.PlanetFragment;

public class PrincipalActivity extends AppCompatActivity{

    private String titulo;
    private String tituloOriginal;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tituloOriginal = getTitle().toString();

        setContentView(R.layout.activity_principal);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerList = (ListView) findViewById(R.id.left_drawer);
        this.mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.linha_lista_navigationdrawer,new String[]{"A", "B", "C"}));
        this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        this.mDrawerToggle = new ActionBarDrawerToggleFilha(this, this.mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    private void selectItem(int position) {
        Fragment fragment = new PlanetFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .commit();

        this.mDrawerList.setItemChecked(position, true);
        setTitle("Pos" + position);
        this.mDrawerLayout.closeDrawer(this.mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.titulo = title.toString();
        getActionBar().setTitle(this.titulo);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class ActionBarDrawerToggleFilha extends ActionBarDrawerToggle {


        public ActionBarDrawerToggleFilha(Activity activity, DrawerLayout drawerLayout, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            getActionBar().setTitle(titulo);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            super.onDrawerOpened(drawerView);
            getActionBar().setTitle(tituloOriginal);
        }
    }
}
