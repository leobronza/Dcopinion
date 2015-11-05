package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
/**
 * Created by leobronza@hotmail.com on 17/09/15.
 */
public class Main extends Activity {
    private String id_cliente;
    private ListView lista_noticias;
    private ListView lista_de_questoes_responder;
    private ArrayList<String> id_noticias = new ArrayList<>();
    private ArrayList<String> titulos_noticias = new ArrayList<>();
    private ArrayList<String> id_questoes = new ArrayList<>();
    private ArrayList<String> titulos_questoes = new ArrayList<>();
    private ArrayList<String> tipo_questao = new ArrayList<>();
    /*
    @Override // ????
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();

            }
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Recebe parametros
        id_cliente = getIntent().getStringExtra("id");
        String email = getIntent().getStringExtra("email");

        // Define email do cabeçalho
        TextView texto_email = (TextView)findViewById(R.id.email);
        texto_email.setText(email);
    }
    @Override
    protected void onResume() {
        // Função responsavel pelas Noticias
        Noticias();

        // Função responsavel pelas Questoes
        Questoes();
        super.onResume();
    }
    public void onclickresultados(View v){

        // Cria e Inicia intent Resultado() com parametros
        Intent intent = new Intent(getApplicationContext(), Resultado.class);
        intent.putExtra("id_cliente", id_cliente);
        startActivity(intent);
    }
    public void onclicksair(View v){

        // Cria e Inicia intent Login()
        startActivity(new Intent(this, Login.class));

        // Apaga valores armazenados
        SharedPreferences sharedPreferences = getSharedPreferences("manter_conectado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", "");
        editor.putString("senha", "");
        editor.apply();

        // Termina intent Main()
        finish();
    }
    public void Questoes(){

        // Limpa arrays para atualizar
        id_questoes.clear();
        titulos_questoes.clear();
        tipo_questao.clear();

        // Consulta questoes disponiveis
        Backgroundtask thread_questoes = new Backgroundtask(this);
        ArrayList<String> consulta_questoes = new ArrayList<>();
        try {
            consulta_questoes = thread_questoes.execute("questoes_responder",id_cliente).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Separa ids e titulos e tipos
        for (int i = 0; i < consulta_questoes.size(); i += 3) {
            id_questoes.add(consulta_questoes.get(i));
            titulos_questoes.add(consulta_questoes.get(i + 1));
            tipo_questao.add(consulta_questoes.get(i + 2));
        }

        // Set String com os titulos
        String[] populando_questoes_responder = new String[titulos_questoes.size()];
        populando_questoes_responder = titulos_questoes.toArray(populando_questoes_responder);

        // Criando e Set Adpater para questoes
        lista_de_questoes_responder = (ListView)findViewById(R.id.conteudo_questionario);
        ArrayAdapter<String> adpater2 = new ArrayAdapter<>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,populando_questoes_responder);
        lista_de_questoes_responder.setAdapter(adpater2);

        // Onclick em questoes
        lista_de_questoes_responder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Cria e Inicia intent Questionarios() com parametros
                Intent intent = new Intent(getApplicationContext(), Questionarios.class);
                intent.putExtra("id_cliente", id_cliente);
                intent.putExtra("tipo_questao", tipo_questao.get(position));
                intent.putExtra("id_questao", id_questoes.get(position));
                startActivity(intent);
            }
        });
    }
    public void Noticias(){

        // Limpa arrays para atualizar
        titulos_noticias.clear();

        // Consulta noticias disponiveis
        Backgroundtask thread_noticias = new Backgroundtask(this);
        ArrayList<String> consulta_noticias = new ArrayList<>();
        try {
            consulta_noticias = thread_noticias.execute("noticias").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Separa ids e titulos das noticias
        for(int i = 0 ; i < consulta_noticias.size(); i+=2) {
            id_noticias.add(consulta_noticias.get(i));
            titulos_noticias.add(consulta_noticias.get(i+1));
        }

        // Set String com os titulos
        String[] populando_noticias = new String[titulos_noticias.size()];
        populando_noticias = titulos_noticias.toArray(populando_noticias);

        // Criando e Set Adpater para noticias
        lista_noticias = (ListView)findViewById(R.id.conteudo_noticias);
        ArrayAdapter<String> adapater = new ArrayAdapter<>(this, R.layout.conteudo_listview,R.id.conteudo_listview ,populando_noticias);
        lista_noticias.setAdapter(adapater);

        // Onclick em noticias
        lista_noticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Cria e Inicia intent Noticias() com parametros
                Intent intent = new Intent(getApplicationContext(), Noticias.class);
                intent.putExtra("id_noticia", id_noticias.get(position));
                startActivity(intent);
            }
        });
    }
}
