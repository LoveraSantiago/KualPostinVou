package lovera.kualpostinvou.views;

import android.app.Activity;
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

import java.util.HashMap;
import java.util.Map;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.adapters.MyAdapter;
import lovera.kualpostinvou.views.contratos.MsgFromNavigationDrawer;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabGeoLocalizacao;
import lovera.kualpostinvou.views.fragments.FragBuscaEstabelecimentos;
import lovera.kualpostinvou.views.fragments.FragRedesSociais;
import lovera.kualpostinvou.views.fragments.FragmentMenu;
import lovera.kualpostinvou.views.redes_sociais.google.Google_Coisas;

public class PrincipalActivity extends AppCompatActivity implements MsgFromNavigationDrawer{

    private String titulo;
    private String tituloOriginal;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private String nome = "Santuga Lovera";
    private String email = "santiago.lovera@gmail.com";
    private int profile = R.drawable.icon_people_128;

    private Map<Integer, FragmentMenu> mapFragments;

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

        inicializarFragmentMap();

        this.mAdapter = new MyAdapter(this.mapFragments, nome, email, profile, this, this);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggleFilha(this, this.mDrawerLayout, this.toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);

        this.mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        selectItem(1);
    }

    private void inicializarFragmentMap(){
        this.mapFragments = new HashMap<>();
        this.mapFragments.put(FragRedesSociais.ID_FRAGMENT, new FragRedesSociais());
        this.mapFragments.put(FragBuscaEstabelecimentos.ID_FRAGMENT, new FragBuscaEstabelecimentos());
        this.mapFragments.put(FragBuscaEstabGeoLocalizacao.ID_FRAGMENT, new FragBuscaEstabGeoLocalizacao());
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

    @Override
    public void selectItem(int position) {
        FragmentMenu fragment = this.mapFragments.get(position);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .addToBackStack("")
                       .commit();

        setTitle(fragment.getFragmentTitulo());
        this.mDrawerLayout.closeDrawer(this.mRecyclerView);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.titulo = title.toString();
        getSupportActionBar().setTitle(this.titulo);
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
