package br.ufscar.dc.dcopinion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
/**
 * Created by leobronza@hotmail.com on 02/11/15.
 */
public class Fragment_star_resultado extends android.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star_resultado, null);

        // Recebe parametros
        Bundle bundle = this.getArguments();
        if (bundle != null) {

            // Set quantidade de votos
            ArrayList<String> result = bundle.getStringArrayList("key");
            TextView RE1 = (TextView) view.findViewById(R.id.RE1);
            RE1.setText(String.valueOf(result.get(3)));
            TextView RE2 = (TextView) view.findViewById(R.id.RE2);
            RE2.setText(String.valueOf(result.get(5)));
            TextView RE3 = (TextView) view.findViewById(R.id.RE3);
            RE3.setText(String.valueOf(result.get(7)));
            TextView RE4 = (TextView) view.findViewById(R.id.RE4);
            RE4.setText(String.valueOf(result.get(9)));
            TextView RE5 = (TextView) view.findViewById(R.id.RE5);
            RE5.setText(String.valueOf(result.get(11)));

            // calculo de votos
            float estrelas = (Integer.parseInt(result.get(3).toString()) +
                    Integer.parseInt(result.get(5).toString())*2 +
                    Integer.parseInt(result.get(7).toString())*3 +
                    Integer.parseInt(result.get(9).toString())*4 +
                    Integer.parseInt(result.get(11).toString())*5);
            float respostas = (Integer.parseInt(result.get(3).toString()) +
                    Integer.parseInt(result.get(5).toString()) +
                    Integer.parseInt(result.get(7).toString()) +
                    Integer.parseInt(result.get(9).toString()) +
                    Integer.parseInt(result.get(11).toString()));

            // Set meida de votos
            RatingBar REM = (RatingBar) view.findViewById(R.id.REM);
            REM.setRating(estrelas / respostas);
        }
        return view;
    }
}
