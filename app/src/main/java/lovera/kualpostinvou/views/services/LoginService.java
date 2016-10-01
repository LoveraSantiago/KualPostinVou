package lovera.kualpostinvou.views.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Instalacao;
import lovera.kualpostinvou.modelos.Pessoa;
import lovera.kualpostinvou.views.utils.Utils_View;

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
            if(!Aplicacao.getPessoaLogada().hasToken()) stopSelf(this.startId);

            Pessoa pessoa = Aplicacao.getPessoaLogada().getPessoa();
            StringBuilder stringBuilder = new StringBuilder();
            ErrorObj errorObj = new ErrorObj();

            ConexaoMetaModelo conexao = new ConexaoMetaModelo();

            conexao.autenticar(pessoa, stringBuilder, errorObj);//Tenta pegar o token
            if(stringBuilder.length() > 0){//resultado token
                Aplicacao.getPessoaLogada().setToken(stringBuilder.toString());//Conseguiu pegar o token
            }
            else if(errorObj.getReasonPhrase().equals("Unauthorized")){//Não conseguiu pegar o token e aponta que o erro foi de unauthorized
                conexao.cadastrarPessoa(pessoa, stringBuilder, errorObj); //Tenta o cadastro
                if(stringBuilder.length() > 0){//resultado cadastro

                    conexao.autenticar(pessoa, stringBuilder, errorObj);//Tenta pegar o Token novamente
                    if(stringBuilder.length() > 0){//resultado token
                        Aplicacao.getPessoaLogada().setToken(stringBuilder.toString());//Conseguiu pegar o token
                    }
                    else{
                        throw new RuntimeException("Problema pegar token");
                    }

                    Instalacao instalacao = new Instalacao();
                    instalacao.setCodUsuario(pessoa.getCod());
                    instalacao.setDataHora(Utils_View.dateToString(Calendar.getInstance().getTime(), "yyyy-MM-dd"));
                    instalacao.setDeviceToken(Aplicacao.getAplicacaoInstancia().getAndroidId());
                    conexao.cadastrarInstalacao(Aplicacao.getPessoaLogada().getToken(), instalacao, stringBuilder, errorObj);
                    if(stringBuilder.length() > 0){

                    }
                    else{
                        throw new RuntimeException("Problema ao cadastrar instalacao");
                    }
                }
                else{
                    throw new RuntimeException("Problema ao cadastrar");
                }

            }
            stopSelf(this.startId);
        }
    }
}
