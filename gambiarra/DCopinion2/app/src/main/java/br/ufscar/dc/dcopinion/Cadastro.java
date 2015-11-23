package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
/**
 * Created by leobronza@hotmail.com on 17/09/15.
 */
public class Cadastro extends Activity {

    EditText email,senha;
    String  string_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        email = (EditText)findViewById(R.id.novo_email);
        senha = (EditText)findViewById(R.id.novo_senha);
        string_categoria = "BCC";
    }
    public void onclick_categoria(View view){

        // Get valor do RadioButton
        switch (view.getId()){
            case R.id.bcc:
                string_categoria =  "BCC";
                break;
            case R.id.docente:
                string_categoria = "Docente";
                break;
            case  R.id.enc:
                string_categoria = "ENC";
                break;
            case R.id.tercerizado:
                string_categoria = "Tercerizado";
                break;
        }
    }

public void onclick_novo_cadastro (View view){

    // Envia valores para novo cadastro
    Backgroundtask thread_novo_cadastro = new Backgroundtask(this);
    thread_novo_cadastro.execute("cadastrar",email.getText().toString(),senha.getText().toString(),string_categoria);
    finish();
}


}
