package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.views.redes_sociais.facebook.Facebook_Coisas;

public class FragRedesSociais extends FragmentMenu {

    public static String TITULO_FRAGMENT = "Redes Sociais";
    public static int ID_FRAGMENT = 3;
    public static int ICONE = R.drawable.icn3;

    private LoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_redessociais, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarBtnFacebook();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Aplicacao.getFaceCoisas().getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    private void inicializarBtnFacebook(){
        if(AccessToken.getCurrentAccessToken() == null){
            setTextToLabel("Não", R.id.f3_lblstatus_facelogado);
        }
        else{
            setTextToLabel("Sim", R.id.f3_lblstatus_facelogado);
        }

        this.loginButton = (LoginButton) getView().findViewById(R.id.f3_face_loginbutton);
        this.loginButton.setReadPermissions(Arrays.asList("email"));
        this.loginButton.registerCallback(Aplicacao.getFaceCoisas().getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        new ProfileTracker(){
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                Aplicacao.getFaceCoisas().onLoginFeito();
                                this.stopTracking();
                            }
                        };
                        setTextToLabel("Acabou de logar", R.id.f3_lblstatus_facelogado);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "Facebook cancelado", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), "Facebook erro", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setTextToLabel(String texto, int id){
        TextView lblCodigo = (TextView) getView().findViewById(id);
        lblCodigo.setText(texto);
    }

    @Override
    public int getFragmentId() {
        return ID_FRAGMENT;
    }

    @Override
    public String getFragmentTitulo() {
        return TITULO_FRAGMENT;
    }

    @Override
    public int getIcone() {
        return ICONE;
    }
}
