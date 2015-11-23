package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by leobronza@hotmail.com on 03/11/15.
 */
public class Fragment_a_resultado extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_resultado,container,false);

        // Recebe Parametros
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<String> result = bundle.getStringArrayList("key");

            // Remove titulos e corpo
            for(int i = 0 ; i < 3 ; i++)
                result.remove(0);

            // Remove alternativas vazias
            int x = 0;
            for (int i = 0; i < result.size(); i++)
                if (result.get(i).isEmpty())
                    x++;
            for (int i = 0; i < x ;i++ )
                result.remove(result.size()-1);

            // Seleciona a(s) opcoe(s) com maior(es) voto(s)
            int maior = -1;
            for(int i = 0 ; i < result.size() ; i+=2){
                if(Integer.parseInt(result.get(i)) > maior)
                    maior = Integer.parseInt(result.get(i));
            }

            // Cria textviews para as opcoes
            int n = 0;
            for(int i = 0 ; i < result.size() ; i+=2) {
                TextView textView = new TextView(view.getContext());
                textView.setText(result.get(i+ 1));
                textView.setTextSize(20);
                TextView textView2 = new TextView(view.getContext());
                textView2.setText(result.get(i));
                textView2.setTextSize(20);
                textView2.setPadding(5,0,0,0);

                // Diferencia opcao(es) mais votada(s)
             if(Integer.parseInt(result.get(i)) == maior){
               textView.setTextSize(23);
                 textView.setTypeface(null, Typeface.BOLD);
                 textView2.setTextSize(23);
                 textView2.setTypeface(null, Typeface.BOLD);
             }
                // Set posicao das opcoes na grid
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(n);
                params.columnSpec = GridLayout.spec(0);
                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                params2.rowSpec = GridLayout.spec(n);
                params2.columnSpec = GridLayout.spec(1);
                textView.setLayoutParams(params);
                textView2.setLayoutParams(params2);

                // Adiciona opcoes na Grid
                GridLayout gridLayout = (GridLayout)view.findViewById(R.id.grid_layout);
                gridLayout.addView(textView);
                gridLayout.addView(textView2);

                n++;
            }
        }
        return view;
    }
}
