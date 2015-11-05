package br.ufscar.dc.dcopinion;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
/**
 * Created by leobronza@hotmail.com on 16/09/2015.
 */
public class Questionarios extends FragmentActivity {
    private TextView titulo_questao;
    private TextView corpo_questao;
    private ImageView imagem_questao;
    private String id_questao;
    private String id_cliente;
    private Fragment_a_responder fragment_a_responder = new Fragment_a_responder();
    private Fragment_a_resultado fragment_a_resultado = new Fragment_a_resultado();
    private Fragment_star_responder fragment_star_responder = new Fragment_star_responder();
    private Fragment_star_resultado fragment_star_resultado = new Fragment_star_resultado();
    private ArrayList<String> consulta_questao_completa = new ArrayList<>();
    private FragmentManager fragmentManager = getFragmentManager();
    private String tipo_questao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recebe parametros
        tipo_questao = getIntent().getStringExtra("tipo_questao");
        id_questao = getIntent().getStringExtra("id_questao");
        id_cliente = getIntent().getStringExtra("id_cliente");

        // Funcao responsavel pelo Set de questoes
        setquestoes();
    }
    public void setquestoes(){
        // Verifica o tipo da questao
        if(tipo_questao.equals("2")) {
            setContentView(R.layout.questionarios_e);

            // Verifica se é responder ou resultado
            if(!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_star, fragment_star_responder, "fragment_star_responder");
                fragmentTransaction.commit();
            }else if(id_cliente.equals("resultado")){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_star, fragment_star_resultado, "fragment_star_resultado");
                fragmentTransaction.commit();
            }

            // Funcao responsavel pelo Set de questao complete
            setConsulta_questao_completa();

            // Envia parametros caso seja do tipo resultado
            if(id_cliente.equals("resultado")) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("key", consulta_questao_completa);
                fragment_star_resultado.setArguments(bundle);
            }
        }
        else if(tipo_questao.equals("1")) {
            setContentView(R.layout.questionarios_a);

            // Verifica se é responder ou resultado
            if (!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_a, fragment_a_responder, "fragment_a_responder");
                fragmentTransaction.commit();
            } else if (id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_a, fragment_a_resultado, "fragment_a_resultado");
                fragmentTransaction.commit();
            }

            // Funcao responsavel pelo Set de questao complete
            setConsulta_questao_completa();

            // Verifica se é responder ou resultado
            if (id_cliente.equals("resultado")) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("key", consulta_questao_completa);
                fragment_a_resultado.setArguments(bundle);
            } else {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("key", consulta_questao_completa);
                fragment_a_responder.setArguments(bundle);
            }
        }
    }
    public void setConsulta_questao_completa(){

        // Consulta textos questao completa
        Backgroundtask thread_questao_completa = new Backgroundtask(this);
        try {
            consulta_questao_completa = thread_questao_completa.execute("questao_completa", id_questao).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Set titulo, corpo da questao
        titulo_questao = (TextView) findViewById(R.id.titulo_questao);
        titulo_questao.setText(consulta_questao_completa.get(1));
        corpo_questao = (TextView) findViewById(R.id.corpo_questao);
        corpo_questao.setText(consulta_questao_completa.get(2));

        // Consulta imagem questao completa
        Bitmap imagem = null;
        BackGroundtaskImage thread_questao_completa_imagem = new BackGroundtaskImage(this);
        try {
            imagem = thread_questao_completa_imagem.execute("questao_completa_imagem", id_questao).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Set imagem questao completa
        imagem_questao = (ImageView) findViewById(R.id.imagem_questao);
        imagem_questao.setImageBitmap(imagem);
    }
    public void onclick_opcoes(View v){

        // Envia resposta ao servidor
        Backgroundtask thread_envia_resposta = new Backgroundtask(this);
        thread_envia_resposta.execute("envia_resposta", id_questao, id_cliente, String.valueOf(fragment_a_responder.getindex()));
        finish();
    }
    public void onclick_classifica(View v){

        // Envia resposta ao servidor
        Backgroundtask thread_envia_resposta = new Backgroundtask(this);
        thread_envia_resposta.execute("envia_resposta", id_questao, id_cliente, String.valueOf((int) fragment_star_responder.getEstrela()));

        // Termina intent Questionarios()
        finish();
    }
}

