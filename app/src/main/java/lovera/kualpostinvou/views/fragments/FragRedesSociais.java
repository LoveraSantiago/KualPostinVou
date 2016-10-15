package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.Arrays;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoMetaModelo;
import lovera.kualpostinvou.modelos.ErrorObj;
import lovera.kualpostinvou.modelos.Pessoa;

public class FragRedesSociais extends FragmentMenu {

    public static String TITULO_FRAGMENT = "Logar";
    public static int ID_FRAGMENT = 2;
    public static int ICONE = R.drawable.icn_pessoa_linha;

    private LoginButton faceLoginButton;

    private SignInButton googleSignInButton;

    private static final int RC_SIGN_IN = 9001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_redessociais, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inicializarBtnFacebook();
        inicializarBtnGoogle();

        inicializarBtnLogouts();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Aplicacao.getGoogleCoisas().onLoginFeito(getActivity(), result);
        }
        else{
            Aplicacao.getFaceCoisas().getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void inicializarBtnFacebook(){
        this.faceLoginButton = (LoginButton) getView().findViewById(R.id.f3_face_loginbutton);
        this.faceLoginButton.setReadPermissions(Arrays.asList("email"));
        this.faceLoginButton.registerCallback(Aplicacao.getFaceCoisas().getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        new ProfileTracker(){
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                Aplicacao.getFaceCoisas().onLoginFeito(getActivity());
                                this.stopTracking();
                            }
                        };
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

    private void inicializarBtnGoogle(){
        this.googleSignInButton = (SignInButton) getView().findViewById(R.id.f3_google_loginbutton);
        this.googleSignInButton.setScopes(Aplicacao.getGoogleCoisas().getGso().getScopeArray());
        this.googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Aplicacao.getGoogleCoisas().isEstouLogado()){
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Aplicacao.getGoogleCoisas().getmGoogleApiClient());
                    getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        });
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

    public void cadastrarPerfilTeste(){
        Pessoa pessoa = new Pessoa();
        pessoa.setCEP("05172-030");
        pessoa.setBiografia("biografiatemp");
        pessoa.setDataNascimento("25/06/1981");
        pessoa.setEmail("santuga@gmail");
        pessoa.setLatitude(20.20);
        pessoa.setLongitude(19.19);
        pessoa.setNomeCompleto("Santuga Lovera");
        pessoa.setNomeUsuario("Santuga");
        pessoa.setSexo("M");
        pessoa.setTokenFacebook("IDFACETESTE");
        pessoa.setTokenGoogle("IDGOOGLETESTE");

        ConexaoMetaModelo conexao = new ConexaoMetaModelo(null);
        conexao.cadastrarPessoa(pessoa, new StringBuilder(), new ErrorObj());
    }

    private void inicializarBtnLogouts(){
        Button btn = (Button) getActivity().findViewById(R.id.tempForceLogouts);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Aplicacao.getPessoaLogada().setToken(null);
                    Aplicacao.getGoogleCoisas().realizarLogout();
                }
                catch (Exception e){ e.printStackTrace();}

                try{
                    Aplicacao.getFaceCoisas().realizarLogout();
                }
                catch (Exception e){ e.printStackTrace();}
            }
        });
    }
}
