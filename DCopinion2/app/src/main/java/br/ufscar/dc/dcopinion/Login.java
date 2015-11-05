package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by leobronza@hotmail.com on 17/09/15.
 */
public class Login extends Activity {
    EditText email, senha;
    Button botao_entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Set nos EditText email e senha
        email = (EditText)findViewById(R.id.email);
        senha = (EditText)findViewById(R.id.senha);

        // Veritifica e executa se existe login armazenado
        SharedPreferences sharedPreferences = getSharedPreferences("manter_conectado", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString("email","").isEmpty()){

            // Set valores de email e senha armazenados
            email.setText(sharedPreferences.getString("email",""));
            senha.setText(sharedPreferences.getString("senha", ""));

            // Chama funcão onclicklogin
            botao_entrar = (Button)findViewById(R.id.botao_entrar);
            botao_entrar.callOnClick();
        }
    }

    public void onclickcadastro(View v){

        // Chama intent para cadastro
        startActivity(new Intent(this, Cadastro.class));
    }

    public void onclicklogin(View v) throws ExecutionException, InterruptedException {

        // Envia email e senha para validação no servidor
        Backgroundtask thread_login = new Backgroundtask(this);
        ArrayList<String> consulta_login = thread_login.execute("login", email.getText().toString(), senha.getText().toString()).get();

        // Verifica respota do servidor
        if(consulta_login.get(0).equals("ERRO")) {

            // Mostra mensagem de valores incorretos
            Toast.makeText(getBaseContext(), "Email e/ou Senha Incorretos.", Toast.LENGTH_LONG).show();

            // Limpa senha
            senha.setText(null);
        }else if (!consulta_login.get(0).equals("ERROR")) {

            // Cria e Inicia intent Main() com parametros
            Intent intent = new Intent(this, Main.class);
            intent.putExtra("id", consulta_login.get(0));
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);

            // Verifica se checkbox está selecionado
            CheckBox checkbox_manter_conectado = (CheckBox) findViewById(R.id.manter_conectado);
            if(checkbox_manter_conectado.isChecked()){

                // Armazena valores do TextView email e senha
                SharedPreferences sharedPreferences = getSharedPreferences("manter_conectado", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email.getText().toString());
                editor.putString("senha", senha.getText().toString());
                editor.apply();
            }

            // Termina intent login()
            finish();
        }
    }
}
