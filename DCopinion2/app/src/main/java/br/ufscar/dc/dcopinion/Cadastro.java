package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


/**
 * Created by leo on 17/09/15.
 */
public class Cadastro extends Activity {

    EditText email,senha;
    String string_email, string_senha, string_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        email = (EditText)findViewById(R.id.novo_email);
        senha = (EditText)findViewById(R.id.novo_senha);
        string_categoria = "BCC";
    }
    public void onclick_categoria(View view){
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

public void onclick_novo_cadastro (View view){
    string_email  = email.getText().toString();
    string_senha = senha.getText().toString();
    String method = "register";
    Backgroundtask backgroundtask = new Backgroundtask(this);
    backgroundtask.execute(method,string_email,string_senha,string_categoria);
    finish();
}


}
