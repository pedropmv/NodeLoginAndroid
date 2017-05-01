package pedropablomoral.com.nodeloginandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import pedropablomoral.com.nodeloginandroid.clases.Usuario;
import pedropablomoral.com.nodeloginandroid.utilidades.constantes;

import static pedropablomoral.com.nodeloginandroid.utilidades.constantes.LOG_TAG;

public class PerfilActivity extends BaseActivity {
    private Usuario usuario;
    @BindView(R.id.email)
    TextView mEmailView;
    @BindView(R.id.username)
    TextView mUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            super.onCreateDrawer();

        RelativeLayout placeHolder = (RelativeLayout) findViewById(R.id.contenedor);
        getLayoutInflater().inflate(R.layout.activity_perfil, placeHolder);
        ButterKnife.bind(this);
        GetPerfil();
        //SetPerfil();
    }

    private void SetPerfil() {
        mEmailView.setText(usuario.getEmail());
        mUserView.setText(usuario.getUsername());
    }

    private void GetPerfil() {
        Ion.with(getApplicationContext())
                .load("GET", constantes.BASE_URL+"/usuarios/"+email)
                .setHeader("x-access-token", token)
                .setLogging("ION_VERBOSE_LOGGING", Log.VERBOSE)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        // do stuff with the result or error
                        // print the response code, ie, 200
                        int status;
                        status=result.getHeaders().code();
                        System.out.println(result.getHeaders().code());
                        // print the String that was downloaded
                        System.out.println(result.getResult());

                        if (e != null) {

                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), "Error loading user data", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.d(LOG_TAG, result.toString() );
                        final Gson gson = new Gson();
                        usuario = gson.fromJson(result.getResult(),Usuario.class);
                        mEmailView.setText(usuario.getEmail());
                        mUserView.setText(usuario.getUsername());

                    }
                });
    }
}
