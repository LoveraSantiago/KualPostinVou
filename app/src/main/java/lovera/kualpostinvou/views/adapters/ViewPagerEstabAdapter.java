package lovera.kualpostinvou.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerEstabAdapter extends FragmentPagerAdapter{

    private final List<Fragment> listaFragments;
    private final List<String> listaTitulos;

    public ViewPagerEstabAdapter(FragmentManager fm) {
        super(fm);
        this.listaFragments = new ArrayList<>();
        this.listaTitulos = new ArrayList<>();
    }

    public void addFrag(Fragment fragment, String titulo){
        this.listaFragments.add(fragment);
        this.listaTitulos.add(titulo);
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.listaFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.listaTitulos.get(position);
    }
}
