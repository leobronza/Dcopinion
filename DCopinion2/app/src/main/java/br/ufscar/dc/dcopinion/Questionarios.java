package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Leonardo on 16/09/2015.
 */
public class Questionarios extends Activity {
    private TextView titulo_questao;
    private TextView corpo_questao;
    private RatingBar estrela;
    private ImageView imagem_questao;
    private RadioGroup rg;
    private String method;
    private String id_questao;
    private String id_cliente;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tipo_questao = getIntent().getStringExtra("tipo_questao");
        id_questao = getIntent().getStringExtra("id_questao");
        id_cliente = getIntent().getStringExtra("id_cliente");
        if(tipo_questao.equals("E")) {
            setContentView(R.layout.questionarios_e);
            estrela = (RatingBar)findViewById(R.id.estrela);

            method = "questao_completa";
            Backgroundtask backgroundtask = new Backgroundtask(this);
            ArrayList<String> result = new ArrayList<String>();
            try {
                result = backgroundtask.execute(method, id_questao, tipo_questao).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            titulo_questao = (TextView)findViewById(R.id.titulo_questao);
            titulo_questao.setText(result.get(1));
            corpo_questao = (TextView)findViewById(R.id.corpo_questao);
            corpo_questao.setText(result.get(2));

            method = "questao_completa_imagem";
            Bitmap imagem = null;
            BackGroundtaskImage backgroundtask2 = new BackGroundtaskImage(this);
            try {
                imagem = backgroundtask2.execute(method, id_questao).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            imagem_questao = (ImageView)findViewById(R.id.imagem_questao);
            imagem_questao.setImageBitmap(imagem);

        }
        else if(tipo_questao.equals("A")) {
            setContentView(R.layout.questionarios_a);
            method = "questao_completa";
            Backgroundtask backgroundtask = new Backgroundtask(this);
            ArrayList<String> result = new ArrayList<String>();
            try {
                result = backgroundtask.execute(method, id_questao, tipo_questao).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            titulo_questao = (TextView) findViewById(R.id.titulo_questao);
            titulo_questao.setText(result.get(1));
            corpo_questao = (TextView) findViewById(R.id.corpo_questao);
            corpo_questao.setText(result.get(2));

            int x = 0;
            for(int i = 0 ; i < result.size() ; i++)
                if(result.get(i).isEmpty())
                    x++;

            rg = (RadioGroup) findViewById(R.id.opcoes);
            for (int i = rg.getChildCount() - 1; i >= ((result.size() - 3-x)/2); i--)
                rg.removeViewAt(i);

            ArrayList<RadioButton> lista_de_opcoes = new ArrayList<RadioButton>();
            for (int i = 0; i < rg.getChildCount(); i++) {
                View o = rg.getChildAt(i);
                if (o instanceof RadioButton) {
                    lista_de_opcoes.add((RadioButton) o);
                }
            }
            int n = 0;
            for (int i = 0; i < ((result.size() - 3-x)/2); i++){
                lista_de_opcoes.get(i).setText(result.get(4 + n));
                n += 2;
            }

            method = "questao_completa_imagem";
            Bitmap imagem = null;

            BackGroundtaskImage backgroundtask2 = new BackGroundtaskImage(this);
            try {
                imagem = backgroundtask2.execute(method, id_questao).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            imagem_questao = (ImageView)findViewById(R.id.imagem_questao);
            imagem_questao.setImageBitmap(imagem);

            }
    }
    public void onclick_opcoes(View v){
        Backgroundtask backgroundtask3 = new Backgroundtask(this);
        String method = "envia_resposta";
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);

        backgroundtask3.execute(method, id_questao, id_cliente, String.valueOf(rg.indexOfChild(radioButton)+1));
        finish();
        //Toast.makeText(getBaseContext(), "."+String.valueOf(rg.indexOfChild(radioButton)+1), Toast.LENGTH_LONG).show();

    }
    public void onclick_classifica(View v){
        Backgroundtask backgroundtask3 = new Backgroundtask(this);
        method = "envia_resposta";
        backgroundtask3.execute(method, id_questao, id_cliente, String.valueOf((int)estrela.getRating()));
        finish();
    }

}

