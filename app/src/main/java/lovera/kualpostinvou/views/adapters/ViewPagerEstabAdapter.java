package lovera.kualpostinvou.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import lovera.kualpostinvou.views.fragments.FragmentFilho;

public class ViewPagerEstabAdapter extends FragmentStatePagerAdapter{

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

    public void addFrags(Fragment... fragments){
        for(Fragment fragment : fragments){
            addFrag(fragment, "");
        }
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
