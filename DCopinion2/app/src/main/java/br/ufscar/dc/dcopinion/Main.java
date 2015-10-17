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
    private ArrayList<String> id_noticia = new ArrayList<String>();


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Bundle aux = getIntent().getExtras();
        String id_cliente = aux.getString("id_cliente");
        String email = aux.getString("email_cliente");
        texto_email = (TextView)findViewById(R.id.email);
        texto_email.setText(email);


        Backgroundtask backgroundtask = new Backgroundtask(this);
        String method = "noticias";
        ArrayList<String> result = new ArrayList<String>();
        try {
            result = backgroundtask.execute(method).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(int i = 0 ; i < result.size(); i+=2)
            id_noticia.add(result.get(i));
        ArrayList<String> result2 = new ArrayList<String>();
        for(int i = 1 ; i < result.size(); i+=2)
            result2.add(result.get(i));

        String[] x = new String[result2.size()];
        x = result2.toArray(x);


        lista_noticias = (ListView)findViewById(R.id.conteudo_noticias);
        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,x);
        lista_noticias.setAdapter(adpater);

        lista_noticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), br.ufscar.dc.dcopinion.Noticias.class);
                intent.putExtra("position_noticia", position);

                intent.putExtra("id_noticia", id_noticia);
                startActivity(intent);
            }
        });

        String x1[] = {"DC em greve", "Lab fechados" , "Secomp", "Jovens Talentos Estao Aberta as Inscricoes", "PET"};

        lista_de_questoes_responder = (ListView)findViewById(R.id.conteudo_questionario);
        ArrayAdapter<String> adpater2 = new ArrayAdapter<String>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,x1);
        lista_de_questoes_responder.setAdapter(adpater2);

        lista_de_questoes_responder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Questionarios.class);
                intent.putExtra("posicao_conteudo", position);
                intent.putExtra("id_questionario", id);
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
