package br.ufscar.dc.dcopinion;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Leonardo on 16/09/2015.
 */
public class Questionarios extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionarios);
        int posicao_grupo = getIntent().getIntExtra("posicao_grupo", -1);
        int posicao_conteudo = getIntent().getIntExtra("posicao_conteudo", -1);
        long id_questionario = getIntent().getIntExtra("id_questionario", -1);
        Toast.makeText(getBaseContext(), "Click" + " " + posicao_grupo + " " + posicao_conteudo + " " + id_questionario, Toast.LENGTH_LONG).show();

    }
}
