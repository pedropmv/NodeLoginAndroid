package pedropablomoral.com.nodeloginandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import pedropablomoral.com.nodeloginandroid.utilidades.constantes;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences mSharedPreferences;
    private BaseActivity thisActivity;
    public String token;
    public String email;

    protected void onCreateDrawer() {
        initSharedPreferences();
        /** Comprueba si hay token y si no ha expirado */
        if (!token.equals("") && ComprobarToken()) {
            setContentView(R.layout.activity_base);
            thisActivity = this;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            initSharedPreferences();

        } else {
            Logout();

        }


    }

    private void Logout() {
 /**       Vacia las variables token y email, limpia el historial de la aplicacion
                y devuelve al usuario a la pantalla de login */
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(constantes.TOKEN, "");
        editor.putString(constantes.EMAIL, "");
        editor.apply();
        finish();
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, PerfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Logout();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initSharedPreferences() {
        /** Asignamos los valores correspondientes a las avriables token y email */
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = mSharedPreferences.getString(constantes.TOKEN, "");
        email = mSharedPreferences.getString(constantes.EMAIL, "");
    }

    private boolean ComprobarToken() {
      /** Comprueba si el token no ha expirado */

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(constantes.SECRET.getBytes())
                    .parseClaimsJws(token).getBody();
            System.out.println("body: " + claims.toString());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("exception : " + ex.getMessage());
            return false;
        }


    }


}
