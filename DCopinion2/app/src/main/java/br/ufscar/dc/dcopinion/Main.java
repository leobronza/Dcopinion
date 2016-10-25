package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leobronza@hotmail.com on 17/09/15.
 */
public class Main extends Activity implements PopupMenu.OnMenuItemClickListener {
    private String id_cliente;
    private ListView lista_noticias;
    private ListView lista_de_questoes_responder;
    private ArrayList<String> id_noticias = new ArrayList<>();
    private ArrayList<String> titulos_noticias = new ArrayList<>();
    private ArrayList<String> id_questoes = new ArrayList<>();
    private ArrayList<String> titulos_questoes = new ArrayList<>();
    private ArrayList<String> tipo_questao = new ArrayList<>();
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        // Recebe parametros
        id_cliente = getIntent().getStringExtra("id");
        email = getIntent().getStringExtra("email");

        // Define email do cabeçalho
        TextView texto_email = (TextView)findViewById(R.id.email);
        texto_email.setText(email);

    }
    public void abrirmenu(View view){
        PopupMenu menu = new PopupMenu(getApplicationContext(), view);
        menu.setOnMenuItemClickListener(Main.this);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu.getMenu());
        menu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_geral:
                Intent intent = new Intent(getApplicationContext(), Geral.class);
                intent.putExtra("id_cliente", id_cliente);
                intent.putExtra("email", email);
                startActivity(intent);
                return true;
            case R.id.action_notificacoes:
                Intent intent2 = new Intent(getApplicationContext(), Notificacoes.class);
                intent2.putExtra("id_cliente", id_cliente);
                startActivity(intent2);
                return true;
            case R.id.action_sugerirquestionario:
                Intent intent3 = new Intent(getApplicationContext(), Sugerir_Questionario.class);
                intent3.putExtra("id_cliente", id_cliente);
                startActivity(intent3);
                return true;
            case R.id.action_sair:
                onclicksair();
                return true;
        }
        return false;
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
    public void onclicksair(){

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
        id_noticias.clear();

        // Consulta noticias disponiveis
        Backgroundtask_json thread_noticias_json = new Backgroundtask_json(this);
        String consulta_noticias_json = new String();

        try {
            consulta_noticias_json = thread_noticias_json.execute("noticias_json", id_cliente).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Separa ids e titulos das noticias
        String[] populando_noticias = null;

        try {
            JSONArray jsonArray = new JSONArray(consulta_noticias_json);
            populando_noticias = new String[jsonArray.length()];
            for(int i = 0 ; i < jsonArray.length() ; i++) {
                id_noticias.add(jsonArray.getJSONObject(i).getString("id"));
                populando_noticias[i] = jsonArray.getJSONObject(i).getString("titulo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
