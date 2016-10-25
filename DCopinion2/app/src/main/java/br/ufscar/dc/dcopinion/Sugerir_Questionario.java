package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nicolas on 20/11/2015.
 */
public class Sugerir_Questionario extends Activity {
    private String id_cliente;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sugerir_questionario);
        id_cliente = getIntent().getStringExtra("id_cliente");
    }
    public void onclick_voltar(View view){
        finish();
    }

    public void onclick_enviarsugestaoquestionario(View view){
        EditText titulo_questionariosugerir = (EditText)findViewById(R.id.titulo_questionariosugerir);
        EditText descricao_questionariosugerir = (EditText)findViewById(R.id.descricao_questionariosugerir);

        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", id_cliente);
            jsonObject.put("titulo", titulo_questionariosugerir.getText());
            jsonObject.put("descricao", descricao_questionariosugerir.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Backgroundtask_json thread_sugerir_questionario_json = new Backgroundtask_json(this);
        thread_sugerir_questionario_json.execute("sugerir_questionario_json", jsonObject.toString());
        finish();
    }
}
