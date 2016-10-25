package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 23/04/16.
 */
public class Geral extends Activity{
    private String id_cliente;
    private String email;
    private String consulta_id_questoes_respondidas_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.geral);

        // Recebe parametros
        id_cliente = getIntent().getStringExtra("id_cliente");
        email = getIntent().getStringExtra("email");

        // Define email
        TextView texto_email = (TextView)findViewById(R.id.email);
        texto_email.setText(email);

        questoes_respondidas();
    }
    public void questoes_respondidas(){

        // Consulta questoes respondidas
        Backgroundtask_json thread_id_questoes_respondidas_json = new Backgroundtask_json(this);
        try {
            consulta_id_questoes_respondidas_json = thread_id_questoes_respondidas_json.execute("id_questoes_respondidas_json",id_cliente).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }
        try {
            JSONArray jsonArray = new JSONArray(consulta_id_questoes_respondidas_json);
            TextView quant_questoesrespondidas = (TextView)findViewById(R.id.quant_questoesrespondidas);
            quant_questoesrespondidas.setText(String.valueOf(jsonArray.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onclick_voltar(View view){
        finish();
    }
    public void onclick_desativarconta(View view){
        //Todo
        Toast.makeText(view.getContext(), "Operação ainda não disponivel", Toast.LENGTH_LONG).show();
    }
}
