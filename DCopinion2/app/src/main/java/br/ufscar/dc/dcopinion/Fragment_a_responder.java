package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
/**
 * Created by leobronza@hotmail.com on 03/11/15.
 */
public class Fragment_a_responder extends Fragment {
    private  RadioGroup rg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_responder,container,false);

        // Recebe parametros
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<String> result = bundle.getStringArrayList("key");

            // Remove alternativas vazias
            int x = 0;
            for (int i = 0; i < result.size(); i++)
                if (result.get(i).isEmpty())
                    x++;

            // Retira radiobuton extras
            rg = (RadioGroup) view.findViewById(R.id.opcoes);
            for (int i = rg.getChildCount() - 1; i >= ((result.size() - 3 - x) / 2); i--)
                rg.removeViewAt(i);

            // Cria arraylist com os radiobutton restantes
            ArrayList<RadioButton> lista_de_opcoes = new ArrayList<>();
            for (int i = 0; i < rg.getChildCount(); i++) {
                View o = rg.getChildAt(i);
                if (o instanceof RadioButton) {
                    lista_de_opcoes.add((RadioButton) o);
                }
            }

            // Set texto dos radiobuttons
            int n = 0;
            for (int i = 0; i < ((result.size() - 3 - x) / 2); i++) {
                lista_de_opcoes.get(i).setText(result.get(4 + n));
                n += 2;
            }
        }
        return view;
    }
    public int getindex(){

        // Retorna opcao selecionadas pelo usuario
        int radioButtonID = rg.getCheckedRadioButtonId();
        View radioButton = rg.findViewById(radioButtonID);
        return rg.indexOfChild(radioButton)+1;
    }
}
