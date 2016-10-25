package br.ufscar.dc.dcopinion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
            String result = bundle.getString("key");
            try {
                JSONObject jsonObject = new JSONObject(result);
                RatingBar estrela;

                float estrelas = (Integer.parseInt(jsonObject.getString("E1")) +
                        Integer.parseInt(jsonObject.getString("E2"))*2 +
                        Integer.parseInt(jsonObject.getString("E3"))*3 +
                        Integer.parseInt(jsonObject.getString("E4"))*4 +
                        Integer.parseInt(jsonObject.getString("E5"))*5);
                float respostas = (Integer.parseInt(jsonObject.getString("E1")) +
                        Integer.parseInt(jsonObject.getString("E2")) +
                        Integer.parseInt(jsonObject.getString("E3")) +
                        Integer.parseInt(jsonObject.getString("E4")) +
                        Integer.parseInt(jsonObject.getString("E5")));

                estrela = (RatingBar) view.findViewById(R.id.REM);
                estrela.setRating(estrelas / respostas);

                TextView E1 = (TextView) view.findViewById(R.id.RE1);
                E1.setText(jsonObject.getString("E1"));
                TextView E2 = (TextView) view.findViewById(R.id.RE2);
                E2.setText(jsonObject.getString("E2"));
                TextView E3 = (TextView) view.findViewById(R.id.RE3);
                E3.setText(jsonObject.getString("E3"));
                TextView E4 = (TextView) view.findViewById(R.id.RE4);
                E4.setText(jsonObject.getString("E4"));
                TextView E5 = (TextView) view.findViewById(R.id.RE5);
                E5.setText(jsonObject.getString("E5"));


            } catch (JSONException e) {
            }

        }
        return view;
    }

}
