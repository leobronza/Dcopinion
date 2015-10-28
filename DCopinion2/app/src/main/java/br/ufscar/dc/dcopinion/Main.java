package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main extends Activity {
    private ListView lista_noticias;
    private ListView lista_de_questoes_responder;
    private TextView texto_email;
    private ArrayList<String> id_noticias = new ArrayList<String>();
    private ArrayList<String> id_questoes = new ArrayList<String>();
    private ArrayList<String> tipo_questao = new ArrayList<String>();

    ArrayList<HashMap<String, String>> hashmap_noticias = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Bundle aux = getIntent().getExtras();
        final String id_cliente = aux.getString("id_cliente");
        String email = aux.getString("email_cliente");
        texto_email = (TextView)findViewById(R.id.email);
        texto_email.setText(email);

        Backgroundtask backgroundtask = new Backgroundtask(this);
        String method = "noticias";
        ArrayList<String> result_noticias = new ArrayList<String>();
        ArrayList<String> result2_noticias = new ArrayList<String>();

        try {
            result_noticias = backgroundtask.execute(method).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        for(int i = 0 ; i < result_noticias.size(); i+=2) {
            id_noticias.add(result_noticias.get(i));
            result2_noticias.add(result_noticias.get(i+1));
        }

        String[] populando_noticias = new String[result2_noticias.size()];
        populando_noticias = result2_noticias.toArray(populando_noticias);


        lista_noticias = (ListView)findViewById(R.id.conteudo_noticias);
        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,populando_noticias);
        lista_noticias.setAdapter(adpater);

        lista_noticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), br.ufscar.dc.dcopinion.Noticias.class);
                intent.putExtra("id_noticia", id_noticias.get(position));
                startActivity(intent);
            }
        });

        method = "questoes_responder";
        ArrayList<String> result_questoes = new ArrayList<String>();
        ArrayList<String> result2_questoes = new ArrayList<String>();
        Backgroundtask backgroundtask2 = new Backgroundtask(this);

        try {
            result_questoes = backgroundtask2.execute(method,id_cliente).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(result_questoes.size() > 0)
            for (int i = 1; i < result_questoes.size() - 1; i += 3) {
                id_questoes.add(result_questoes.get(i));
                result2_questoes.add(result_questoes.get(i + 1));
                tipo_questao.add(result_questoes.get(i + 2));
            }

            String[] populando_questoes_responder = new String[result2_questoes.size()];
            populando_questoes_responder = result2_questoes.toArray(populando_questoes_responder);


        lista_de_questoes_responder = (ListView)findViewById(R.id.conteudo_questionario);
        ArrayAdapter<String> adpater2 = new ArrayAdapter<String>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,populando_questoes_responder);
        lista_de_questoes_responder.setAdapter(adpater2);

        lista_de_questoes_responder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Questionarios.class);
                intent.putExtra("id_cliente", id_cliente);
                intent.putExtra("tipo_questao", tipo_questao.get(position));
                intent.putExtra("id_questao", id_questoes.get(position));
                startActivity(intent);
            }
        });

    }
    public void onclickresultados(View v){
    startActivity(new Intent(this, Resultado.class));
    }


    public void onclicksair(View v){
            startActivity(new Intent(this, Login.class));
            finish();
    }





}
