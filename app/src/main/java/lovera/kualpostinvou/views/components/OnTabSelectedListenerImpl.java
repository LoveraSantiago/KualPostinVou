package lovera.kualpostinvou.views.components;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

public class OnTabSelectedListenerImpl implements TabLayout.OnTabSelectedListener{

    private ViewPager viewpager;

    public OnTabSelectedListenerImpl(ViewPager viewpager) {
        this.viewpager = viewpager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
