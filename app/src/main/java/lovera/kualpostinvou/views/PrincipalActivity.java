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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.adapters.MyAdapter;
import lovera.kualpostinvou.views.fragments.FragRedesSociais;
import lovera.kualpostinvou.views.redes_sociais.google.Google_Coisas;

public class PrincipalActivity extends AppCompatActivity{

    private String titulo;
    private String tituloOriginal;

    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private String TITLES[] = {"Home","Events","Mail","Shop","Travel"};
    int ICONS[] = {R.drawable.home2, R.drawable.icon_event_details,R.drawable.mail_2,R.drawable.shop,R.drawable.travel};
    private String nome = "Santuga Lovera";
    private String email = "santiago.lovera@gmail.com";
    private int profile = R.drawable.icon_people_128;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tituloOriginal = getTitle().toString();

        setContentView(R.layout.activity_principal);

        this.toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mAdapter = new MyAdapter(TITLES, ICONS, nome, email, profile);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        this.mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        this.mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.linha_lista_navigationdrawer,new String[]{"A", "B", "C"}));
//        this.mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        this.mDrawerToggle = new ActionBarDrawerToggleFilha(this, this.mDrawerLayout, this.toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);

        this.mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        selectItem(0);
    }

    @Override
    protected void onStart() {
        Google_Coisas.getGoogleCoisasUnicaInstancia().connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        Google_Coisas.getGoogleCoisasUnicaInstancia().disconnect();
        super.onStop();
    }

    private void selectItem(int position) {
        Fragment fragment = new FragRedesSociais();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .commit();

//        this.mDrawerList.setItemChecked(position, true);
        setTitle("Pos" + position);
//        this.mDrawerLayout.closeDrawer(this.mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.titulo = title.toString();
        getSupportActionBar().setTitle(this.titulo);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class ActionBarDrawerToggleFilha extends ActionBarDrawerToggle {

        public ActionBarDrawerToggleFilha(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            getSupportActionBar().setTitle(titulo);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            getSupportActionBar().setTitle(tituloOriginal);
            invalidateOptionsMenu();
        }
    }
}
