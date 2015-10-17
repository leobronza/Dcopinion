package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Resultado extends Activity {
    ExpandableListView lista_de_questoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);
        lista_de_questoes = (ExpandableListView) findViewById(R.id.questionarios);
        List<String> titulos = new ArrayList<String>();
        List<String> resposta_1 = new ArrayList<String>();
        List<String> resposta_2 = new ArrayList<String>();
        HashMap<String, List<String>> hash_titulos = new HashMap<String, List<String>>();
        String titulos_itens[] = getResources().getStringArray(R.array.titulo_questionario);
        String resposta_1_itens[] = getResources().getStringArray(R.array.respostas_questionario_01);
        String resposta_2_itens[] = getResources().getStringArray(R.array.respostas_questionario_02);

        for (String x : titulos_itens)
            titulos.add(x);
        for (String x : resposta_1_itens)
            resposta_1.add(x);
        for (String x : resposta_2_itens)
            resposta_2.add(x);
        hash_titulos.put(titulos.get(0), resposta_1);
        hash_titulos.put(titulos.get(1), resposta_2);

        Myadapter myadapter = new Myadapter(this, titulos, hash_titulos);
        lista_de_questoes.setAdapter(myadapter);


        lista_de_questoes.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getApplicationContext(), Questionarios.class);
                intent.putExtra("posicao_grupo",groupPosition);
                intent.putExtra("posicao_conteudo",childPosition);
                intent.putExtra("id_questionario", id);
                startActivity(intent);
                return true;
            }
        });

    }
}
