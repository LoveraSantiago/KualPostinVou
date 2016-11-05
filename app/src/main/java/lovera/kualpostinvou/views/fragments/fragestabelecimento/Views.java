package lovera.kualpostinvou.views.fragments.fragestabelecimento;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.components.OnTabSelectedListenerImpl;
import lovera.kualpostinvou.views.fragments.FragmentFilho;

class Views {

    private ViewPager viewPager;

    private StreetViewPanoramaView streetViewPanoramaView;
    private StreetViewPanorama panorama;

    public Views(Activity activity, Bundle savedInstanceState, FragmentFilho... fragFilhos) {
        inicializarComponentes(activity, savedInstanceState, fragFilhos);
    }

    private void inicializarComponentes(Activity activity, Bundle savedInstanceState, FragmentFilho... fragFilhos){
        inicializarViewPager(activity, fragFilhos);
        inicializarTabLayout(activity, fragFilhos);
        inicializarStreetView(activity, savedInstanceState);
    }

    private void inicializarViewPager(Activity activityParam, FragmentFilho... fragFilhos){
        AppCompatActivity activity = (AppCompatActivity) activityParam;
        this.viewPager = (ViewPager) activityParam.findViewById(R.id.f5_viewpager);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
        //                 fragFilhoDescricao, fragFilhoInfo, fragFilhoAvaliacao, fragFilhoEndereco
        vpAdapter.addFrags(fragFilhos[0]     , fragFilhos[1], fragFilhos[2]     , fragFilhos[3]);
        this.viewPager.setAdapter(vpAdapter);
    }

    private void inicializarTabLayout(Activity activityParam, FragmentFilho... fragFilhos){
        TabLayout tabLayout = (TabLayout) activityParam.findViewById(R.id.f5_tablayout);
        tabLayout.setupWithViewPager(this.viewPager);
        for(FragmentFilho fragment : fragFilhos){
            tabLayout.getTabAt(fragment.getFragmentId()).setIcon(fragment.getIcone());
        }
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListenerImpl(this.viewPager));
    }

    private void inicializarStreetView(Activity activity, Bundle savedInstanceState){
        try{
            this.streetViewPanoramaView = (StreetViewPanoramaView) activity.findViewById(R.id.f5_streetviewpanorama);
            this.streetViewPanoramaView.onCreate(savedInstanceState);
            this.streetViewPanoramaView.getStreetViewPanoramaAsync(
                    new OnStreetViewPanoramaReadyCallback() {
                        @Override
                        public void onStreetViewPanoramaReady(StreetViewPanorama panoramaCallBack) {
                            panorama = panoramaCallBack;
                        }
                    });
            this.streetViewPanoramaView.onResume();
        }catch (Exception e){}
    }

    public void setarPosicaoToPanorama(LatLng latLng){
        if(latLng != null){
            this.panorama.setPosition(latLng);
        }
    }

    public void onSaveInstanceState(Bundle outState){
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onSaveInstanceState(outState);
    }

    public void onPause(){
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onPause();
    }

    public void onDestroy() {
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onDestroy();
    }

    public void onResume(){
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onResume();
    }

    public void onLowMemory() {
        if(this.streetViewPanoramaView != null) this.streetViewPanoramaView.onLowMemory();
    }
}
