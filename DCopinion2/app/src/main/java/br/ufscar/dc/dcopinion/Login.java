package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 17/09/15.
 */
public class Login extends Activity {
    EditText email, senha;
    String string_email, string_senha, string_categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = (EditText)findViewById(R.id.email);
        senha = (EditText)findViewById(R.id.senha);
        string_categoria = "BCC";
    }
    public void onclickcadastro(View v){
        startActivity(new Intent(this, Cadastro.class));
    }

    public void onclick_categoria_login(View view){
        switch (view.getId()){
            case R.id.bcc:
                string_categoria =  "BCC";
                break;
            case R.id.prof:
                string_categoria = "Prof";
                break;
            case  R.id.enc:
                string_categoria = "ENC";
                break;
            case R.id.func:
                string_categoria = "Func";
                break;
        }
    }

    public void onclicklogin(View v) throws ExecutionException, InterruptedException {
        string_email = email.getText().toString();
        string_senha = senha.getText().toString();
        String method = "login";
        Backgroundtask backgroundtask2 = new Backgroundtask(this);
        ArrayList<String> result = new ArrayList<String>();
        result = backgroundtask2.execute(method, string_email, string_senha, string_categoria).get();
        if (!result.get(1).equals("Ops, email, senha e/ou categoria incorretas.")) {
            Intent intent = new Intent(this, Main.class);
            Bundle aux = new Bundle();
            aux.putString("id_cliente", result.get(0));
            aux.putString("email_cliente", string_email);
            intent.putExtras(aux);
            startActivity(intent);
            finish();
        }
    }
}
