package lovera.kualpostinvou.views.fragments.fragestabelecimento.frag_filhos.avaliacao;

import android.os.Bundle;

import lovera.kualpostinvou.views.PrincipalActivity;
import lovera.kualpostinvou.views.receivers.ReceiverPai;
import lovera.kualpostinvou.views.services.ServicesNames;

class Receiver extends ReceiverPai{

    private final FragFilho_Avaliacao fragment;

    public Receiver(FragFilho_Avaliacao fragment) {
        super();
        this.fragment = fragment;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(resultCode == ServicesNames.GPS_SERVICE && resultData != null){
            ((PrincipalActivity) this.fragment.getActivity()).fecharProgresso();
        }
        else{
            this.fragment.getViews().getDialogs().showDialogGpsCancelado();
        }
    }
}
