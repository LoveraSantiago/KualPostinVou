package lovera.kualpostinvou.views.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.conexao.ConexaoPessoa;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;

public class LoginService extends Service{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Worker worker = new Worker(startId);
        worker.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class Worker extends Thread{

        public int startId;

        public Worker(int startId) {
            this.startId = startId;
        }

        @Override
        public void run() {
            if(!Aplicacao.getPessoaLogada().isPessoaLogado()) stopSelf(this.startId);

            Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
            StringBuilder stringBuilder = new StringBuilder();
            ErrorObj errorObj = new ErrorObj();

            ConexaoPessoa conexao = new ConexaoPessoa();
            conexao.autenticar(pessoa, stringBuilder, errorObj);

            if(stringBuilder.length() > 0){
                Aplicacao.getPessoaLogada().setToken(stringBuilder.toString());
            }
            else{
                //TODO: procedimento de erros
            }
        }
    }
}
