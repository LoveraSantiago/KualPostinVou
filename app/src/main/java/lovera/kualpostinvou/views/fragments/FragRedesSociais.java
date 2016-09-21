package lovera.kualpostinvou.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.Arrays;

import lovera.kualpostinvou.Aplicacao;
import lovera.kualpostinvou.R;
import lovera.kualpostinvou.conexao.ConexaoPessoa;
import lovera.kualpostinvou.modelos.Link;
import lovera.kualpostinvou.modelos.Pessoa;

public class FragRedesSociais extends FragmentMenu {

    public static String TITULO_FRAGMENT = "Redes Sociais";
    public static int ID_FRAGMENT = 3;
    public static int ICONE = R.drawable.icn3;

    private LoginButton loginButton;

    private SignInButton signInButton;
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Aplicacao.getGoogleCoisas().onLoginFeito(result);
        }
        else{
            Aplicacao.getFaceCoisas().getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
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

    private void inicializarBtnGoogle(){
        this.signInButton = (SignInButton) getView().findViewById(R.id.f3_google_loginbutton);
        this.signInButton.setScopes(Aplicacao.getGoogleCoisas().getGso().getScopeArray());
        this.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Aplicacao.getGoogleCoisas().getmGoogleApiClient());
                getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
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

    public void cadastrarPerfilTeste(){

        //IGNORA COMPLETAMENTE ESSE CARA
        Link link = new Link();
        link.setHref("https://scontent.xx.fbcdn.net/v/t1.0-1/p160x160/14330154_1381541598540623_4482186030160339709_n.jpg?oh=78004da10af591005cc424de8a5ebf27&oe=5884C3B4");
        link.setRel("nome imagem");


        Pessoa pessoa = new Pessoa();
        pessoa.setCEP("05172-030");
        pessoa.setBiografia("biografiatemp");
        pessoa.setDataNascimento("25/06/1981");
        pessoa.setEmail("santuga@gmail");
        pessoa.setLatitude(20.20);
        pessoa.setLongitude(19.19);
        pessoa.setNomeCompleto("Santuga Lovera");
        pessoa.setNomeUsuario("Santuga");
        pessoa.setLinks(Arrays.asList(link));
        pessoa.setSexo("M");
        pessoa.setTokenFacebook("IDFACETESTE");
        pessoa.setTokenGoogle("IDGOOGLETESTE");

        ConexaoPessoa conexao = new ConexaoPessoa();
        conexao.cadastrarPessoa(pessoa);
    }
}
