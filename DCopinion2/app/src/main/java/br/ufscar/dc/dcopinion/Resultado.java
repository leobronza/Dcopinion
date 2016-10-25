package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
/**
 * Created by leobronza@hotmail.com on 17/09/15.
 */
public class Resultado extends Activity {
    private String id_cliente;
    private List<List<String>> respostas = new ArrayList<>();
    private List<String> titulos = new ArrayList<>();
    private HashMap<String, List<String>> hash_titulos = new HashMap<>();
    private List<List<String>> questao_completa = new ArrayList<>();
    private ArrayList<String> consulta_id_questoes_respondidas = new ArrayList<>();
    ArrayList<String> consulta_questao_completa_json = new ArrayList<>();
    private String consulta_id_questoes_respondidas_json;
    @Override
    protected void onResume() {

        // Função responsavel pelas Questoes Respondidas
        Questoes();
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.resultados);

        // Recebe Parametros
        id_cliente = getIntent().getStringExtra("id_cliente");
    }
    public void Questoes(){
        // Limpa arrays para atualizar
        titulos.clear();
        respostas.clear();
        questao_completa.clear();
        consulta_questao_completa_json.clear();
        consulta_id_questoes_respondidas.clear();


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
            for(int i = 0 ; i < jsonArray.length() ; i++)
                consulta_id_questoes_respondidas.add(jsonArray.getJSONObject(i).getString("questoes_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Cria um array de threads
        ArrayList<Backgroundtask_json> backgroundtask_json = new ArrayList<>();
        for(int i = 0 ; i < consulta_id_questoes_respondidas.size(); i++)
            backgroundtask_json.add(new Backgroundtask_json(this));

        //Consulta questoes completas respondida

        ArrayList<String> perguntasrespostas = new ArrayList<>();

        for(int i = 0; i < consulta_id_questoes_respondidas.size(); i++) {
            try {
                consulta_questao_completa_json.add(backgroundtask_json.get(i).execute("questao_completa_json", consulta_id_questoes_respondidas.get(i)).get());
                //Toast.makeText(getApplicationContext(), consulta_questao_completa_json + String.valueOf(i) + " " + consulta_id_questoes_respondidas.get(i) , Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        // Criando e Set Adpater para questoes
        MyadapterJson myadapterjson = new MyadapterJson(this, consulta_questao_completa_json);
        ExpandableListView lista_de_questoes = (ExpandableListView) findViewById(R.id.questionarios);
        lista_de_questoes.setAdapter(myadapterjson);

        // Onclick em questoes
        lista_de_questoes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                // Cria e Inicia intent Questionarios() com parametros
                Intent intent = new Intent(getApplicationContext(), Questionarios.class);

                // Set id_cliente para abrir como resultado
                intent.putExtra("id_cliente", "resultado");
                intent.putExtra("id_questao", consulta_id_questoes_respondidas.get(groupPosition));

                // Set tipo_questao de acordo com o tipo
                try {
                    JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
                    jsonObject.getString("E1");
                    intent.putExtra("tipo_questao", "2");
                } catch (JSONException e) {
                    try {
                    JSONObject jsonObject = new JSONObject(consulta_questao_completa_json.get(groupPosition));
                    jsonObject.getString("tipo");
                    intent.putExtra("tipo_questao", "4");
                    } catch (JSONException e1) {
                        intent.putExtra("tipo_questao", "1");
                    }
                }
                startActivity(intent);
                return true;
            }
        });
    }
    public void onclick_voltar(View view){
        finish();
    }
}

