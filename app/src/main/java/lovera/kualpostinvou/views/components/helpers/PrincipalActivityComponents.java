package lovera.kualpostinvou.views.components.helpers;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Map;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.components.navigationdrawer.ActionBarDrawerToggleImpl;
import lovera.kualpostinvou.views.components.navigationdrawer.RecyclerViewAdapterImpl;
import lovera.kualpostinvou.views.contratos.MsgFromNavigationDrawer;
import lovera.kualpostinvou.views.fragments.FragmentMenu;

public class PrincipalActivityComponents {

    private Toolbar toolbar;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    //Componentes progresso
    private TextView lblStatus;
    private View uiStatus;

    public PrincipalActivityComponents(PrincipalActivity activity, Map<Integer, FragmentMenu> mapFragments) {
        inicializarComponentes(activity, mapFragments);
    }

    private void inicializarComponentes(PrincipalActivity activity, Map<Integer, FragmentMenu> mapFragments){
        inicializarToolbar(activity);
        inicializarNavigationDrawer(activity, mapFragments);
        inicializarComponentesProgresso(activity);
    }

    private void inicializarToolbar(AppCompatActivity activity){
        this.toolbar = (Toolbar) activity.findViewById(R.id.app_bar);
        activity.setSupportActionBar(this.toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void inicializarNavigationDrawer(PrincipalActivity activity, Map<Integer, FragmentMenu> mapFragments){
        this.mRecyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        this.mRecyclerView.setHasFixedSize(true);
        this.mAdapter = new RecyclerViewAdapterImpl(mapFragments, activity);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mLayoutManager = new LinearLayoutManager(activity);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);

        this.mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggleImpl(activity, this.mDrawerLayout, this.toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);

        this.mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    private void inicializarComponentesProgresso(PrincipalActivity activity){
        this.uiStatus    = activity.findViewById(R.id.a1_ui_status);
        this.lblStatus   = (TextView) activity.findViewById(R.id.a1_lblstatus);
    }

    public void abrirDrawer(){
        this.mDrawerLayout.openDrawer(this.mRecyclerView);
    }

    public void fecharDrawer(){
        this.mDrawerLayout.closeDrawer(this.mRecyclerView);
    }

    public boolean isAbertoProgresso() {
        return this.uiStatus.getVisibility() == View.VISIBLE;
    }

    public void abrirProgresso(){
        this.uiStatus.setVisibility(View.VISIBLE);
    }

    public void fecharProgresso(){
        this.uiStatus.setVisibility(View.GONE);
    }

    public void setarTextoProgresso(Activity activity, final String texto){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lblStatus.setText(texto);
            }
        });
    }
}
