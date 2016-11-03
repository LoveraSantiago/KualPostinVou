package lovera.kualpostinvou.views.fragments.fraglistaestabelecimentos;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.adapters.ListEstabAdapter;

class Views implements AdapterView.OnItemClickListener{

    private ListView listView;
    private final FragListaEstabelecimentos fragment;

    public Views(FragListaEstabelecimentos fragment) {
        this.fragment = fragment;
        inicializarListView();
    }

    private void inicializarListView(){
        ListEstabAdapter listEstabAdapter = new ListEstabAdapter(this.fragment.getActivity(), this.fragment.getConsumer().getListaEstabelecimentos());

        this.listView = (ListView) this.fragment.getActivity().findViewById(R.id.listaEstabelecimentos);
        this.listView.setAdapter(listEstabAdapter);
        this.listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.fragment.getConsumer().inicioConsumirEstabelecimento(position);
    }
}
