package lovera.kualpostinvou.views.components;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class OnTabSelectedListenerImpl implements TabLayout.OnTabSelectedListener{

    private ViewPager viewpager;

    public OnTabSelectedListenerImpl(ViewPager viewpager) {
        this.viewpager = viewpager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.i("ciclo", "Tab onSelecetd");
        this.viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.i("ciclo", "Tab unSelected");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.i("ciclo", "Tab onReselecetd");
    }
}
