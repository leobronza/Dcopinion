package br.ufscar.dc.dcopinion;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
/**
 * Created by leobronza@hotmail.com on 16/09/2015.
 */
public class Questionarios extends FragmentActivity {
    private TextView titulo_questao;
    private TextView corpo_questao;
    private TextView inicio;
    private TextView fim;
    private ImageView imagem_questao;
    private String id_questao;
    private String id_cliente;
    private Fragment_ma_responder fragment_ma_responder = new Fragment_ma_responder();
    private Fragment_a_responder fragment_a_responder = new Fragment_a_responder();
    private Fragment_a_resultado fragment_a_resultado = new Fragment_a_resultado();
    private Fragment_star_responder fragment_star_responder = new Fragment_star_responder();
    private Fragment_star_resultado fragment_star_resultado = new Fragment_star_resultado();
    private Fragment_img_responder fragment_img_responder = new Fragment_img_responder();
    private Fragment_img_resultado fragment_img_resultado = new Fragment_img_resultado();
    private String consulta_questao_completa_json = new String();
    private FragmentManager fragmentManager = getFragmentManager();
    private String tipo_questao;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
            setContentView(R.layout.questionarios);

            // Verifica se é responder ou resultado
            if(!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_star_responder, "fragment_star_responder");
                fragmentTransaction.commit();
            }else if(id_cliente.equals("resultado")){
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_star_resultado, "fragment_star_resultado");
                fragmentTransaction.commit();
            }

            // Funcao responsavel pelo Set de questao completa
            setConsulta_questao_completa();

            // Repassa questao completa
                Bundle bundle = new Bundle();
                bundle.putString("key", consulta_questao_completa_json);
                fragment_star_resultado.setArguments(bundle);

        }
        else if(tipo_questao.equals("1")) {
            setContentView(R.layout.questionarios);

            // Verifica se é responder ou resultado
            if (!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_a_responder, "fragment_a_responder");
                fragmentTransaction.commit();
            } else if (id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_a_resultado, "fragment_a_resultado");
                fragmentTransaction.commit();
            }

            // Funcao responsavel pelo Set de questao complete
            setConsulta_questao_completa();

            // Repassa Questao completa
                Bundle bundle = new Bundle();
                bundle.putString("key", consulta_questao_completa_json);
                fragment_a_responder.setArguments(bundle);
                fragment_a_resultado.setArguments(bundle);

        }else if(tipo_questao.equals("3")) {
            setContentView(R.layout.questionarios);

            // Verifica se é responder ou resultado
            if (!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_ma_responder, "fragment_ma_responder");
                fragmentTransaction.commit();
            } //else if (id_cliente.equals("resultado")) {
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.add(R.id.fragment, fragment_a_resultado, "fragment_a_resultado");
                //fragmentTransaction.commit();
            //}

            // Funcao responsavel pelo Set de questao complete
            setConsulta_questao_completa();

            // Repassa Questao completa
            Bundle bundle = new Bundle();
            bundle.putString("key", consulta_questao_completa_json);
            fragment_ma_responder.setArguments(bundle);
            //fragment_a_resultado.setArguments(bundle);

        }else if(tipo_questao.equals("4")){
            setContentView(R.layout.questionarios);

            // Verifica se é responder ou resultado
            if (!id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_img_responder, "fragment_img_responder");
                fragmentTransaction.commit();
            } else if (id_cliente.equals("resultado")) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment_img_resultado, "fragment_img_resultado");
                fragmentTransaction.commit();
                //Toast.makeText(getApplicationContext(), "resultado" , Toast.LENGTH_LONG).show();
            }
            // Funcao responsavel pelo Set de questao complete
            setConsulta_questao_completa();

            // Repassa Questao completa
            Bundle bundle = new Bundle();
            bundle.putString("key", consulta_questao_completa_json);
            fragment_img_responder.setArguments(bundle);
            fragment_img_resultado.setArguments(bundle);


        }
    }
    public void setConsulta_questao_completa(){

        // Consulta textos questao completa
        Backgroundtask_json thread_questao_completa_json = new Backgroundtask_json(this);
        try {
            consulta_questao_completa_json = thread_questao_completa_json.execute("questao_completa_json", id_questao).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(consulta_questao_completa_json);

            // Set titulo, corpo da questao
            titulo_questao = (TextView) findViewById(R.id.titulo_questao);
            titulo_questao.setText(jsonObject.getString("titulo"));
            corpo_questao = (TextView) findViewById(R.id.corpo_questao);
            corpo_questao.setText(jsonObject.getString("corpo"));
            inicio = (TextView) findViewById(R.id.iniciofim);
            inicio.setText(jsonObject.getString("inicio") + "  -  até  -  " + jsonObject.getString("fim"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
        //Toast.makeText(getApplicationContext(), String.valueOf(imagem) , Toast.LENGTH_LONG).show();
        if (imagem != null) {
            imagem_questao.setImageBitmap(imagem);
        }
        else{
           imagem_questao.setVisibility(View.GONE);
        }
    }
    public void onclick_mopcoes(View v){
        // Envia resposta ao servidor

        ArrayList<String> retorno = fragment_ma_responder.getmopcoes();
        if(!retorno.isEmpty()) {
            ArrayList<Backgroundtask> backgroundtask = new ArrayList<>();
            for (int i = 0; i < retorno.size(); i++)
                backgroundtask.add(new Backgroundtask(this));

            for (int i = 0; i < retorno.size(); i++)
                backgroundtask.get(i).execute("envia_resposta", id_questao, id_cliente, retorno.get(i));

            Toast.makeText(v.getContext(), "Resposta Enviada", Toast.LENGTH_LONG).show();

            finish();
        }
        else {
            Toast.makeText(v.getContext(), "Dê sua opinião, antes de confirmar", Toast.LENGTH_LONG).show();
        }
    }
    public void onclick_opcoes(View v){

        // Envia resposta ao servidor
        if(fragment_a_responder.getindex() != 0 ) {
            Backgroundtask thread_envia_resposta = new Backgroundtask(this);
            thread_envia_resposta.execute("envia_resposta", id_questao, id_cliente, String.valueOf(fragment_a_responder.getindex()));

            // Termina intent Questionarios()
            finish();
        }
        else{
            Toast.makeText(v.getContext(), "Dê sua opinião, antes de confirmar", Toast.LENGTH_LONG).show();
        }
    }
    public void onclick_opcaoimg(View v){

        // Envia resposta ao servidor
        if (fragment_img_responder.getopcaoimg() != -1){
            Backgroundtask thread_envia_resposta = new Backgroundtask(this);
            thread_envia_resposta.execute("envia_resposta", id_questao, id_cliente, String.valueOf((int) fragment_img_responder.getopcaoimg()));

            // Termina intent Questionarios()
            Toast.makeText(v.getContext(), "Resposta Enviada", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(v.getContext(), "Dê sua opinião, antes de confirmar", Toast.LENGTH_LONG).show();
        }

    }
    public void onclick_classifica(View v) {

        // Envia resposta ao servidor
        if (fragment_star_responder.getEstrela() != 0){
            Backgroundtask thread_envia_resposta = new Backgroundtask(this);
        thread_envia_resposta.execute("envia_resposta", id_questao, id_cliente, String.valueOf((int) fragment_star_responder.getEstrela()));

        // Termina intent Questionarios()
        finish();
        }
    else{
        Toast.makeText(v.getContext(), "Dê sua opinião, antes de confirmar", Toast.LENGTH_LONG).show();
        }
    }
    public void onclick_voltar(View view){
        finish();
    }

}

