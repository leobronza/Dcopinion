package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
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
    @Override
    protected void onResume() {

        // Função responsavel pelas Questoes Respondidas
        Noticias();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);

        // Recebe Parametros
        id_cliente = getIntent().getStringExtra("id_cliente");
    }
    public void Noticias(){

        // Limpa arrays para atualizar
        titulos.clear();
        respostas.clear();
        questao_completa.clear();


        // Consulta questoes respondidas
        Backgroundtask thread_id_questoes_respondidas = new Backgroundtask(this);
        try {
            consulta_id_questoes_respondidas = thread_id_questoes_respondidas.execute("id_questoes_respondidas",id_cliente).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Cria um array de threads
        ArrayList<Backgroundtask> backgroundtask = new ArrayList<>();
        for(int i = 0 ; i < consulta_id_questoes_respondidas.size(); i++)
            backgroundtask.add(new Backgroundtask(this));

        //Consulta questoes completas respondidas
        for(int i = 0; i < consulta_id_questoes_respondidas.size(); i++) {
            try {
                questao_completa.add(backgroundtask.get(i).execute("questao_completa", consulta_id_questoes_respondidas.get(i)).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // Exige Atencao !!
            titulos.add(questao_completa.get(i).get(1));
            for(int k = 0 ; k < 3 ; k++)
                questao_completa.get(i).remove(0);
            int x = 0;
            for(int z = 0 ; z < questao_completa.get(i).size(); z++)
                if (questao_completa.get(i).get(z).isEmpty())
                    x++;
            for(int f = 0 ; f < x ; f++)
                questao_completa.get(i).remove(questao_completa.get(i).size()-1);

            respostas.add(questao_completa.get(i));
            hash_titulos.put(titulos.get(i), respostas.get(i));
        }

        // Criando e Set Adpater para questoes
        Myadapter myadapter = new Myadapter(this, titulos, hash_titulos);
        ExpandableListView lista_de_questoes = (ExpandableListView) findViewById(R.id.questionarios);
        lista_de_questoes.setAdapter(myadapter);

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
                if(respostas.get(groupPosition).get(1).contains("<E>"))
                    intent.putExtra("tipo_questao", "2");
                else
                    intent.putExtra("tipo_questao", "1");
                startActivity(intent);
                return true;
            }
        });
    }
}

