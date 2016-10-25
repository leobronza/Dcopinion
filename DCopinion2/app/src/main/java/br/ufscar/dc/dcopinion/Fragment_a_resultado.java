package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
            String result = bundle.getString("key");

            try {
                JSONObject jsonObject = new JSONObject(result);
            int maior = -1;
                int x = 0;

            for(int i = 0; i < jsonObject.length()-4 ; i+=2) {

                if (!jsonObject.getString(String.valueOf(i)).equals("null")) {

                    x++;
                    if (Integer.parseInt(jsonObject.getString(String.valueOf(i))) > maior)
                        maior = Integer.parseInt(jsonObject.getString(String.valueOf(i)));
                }
            }

            // Cria textviews para as opcoes
            int n = 1;
            for(int i = 0 ; i < x*2 ; i+=2) {
                TextView textView = new TextView(view.getContext());
                textView.setText(jsonObject.getString(String.valueOf(i + 1)));
                textView.setTextSize(20);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textView.setPadding(5, 0, 0, 0);
                TextView textView2 = new TextView(view.getContext());
                textView2.setText(jsonObject.getString(String.valueOf(i)));
                textView2.setTextSize(20);
                textView2.setPadding(0, 0, 5, 0);

                Resources r = getResources();
                int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 230, r.getDisplayMetrics());


                textView.setMaxWidth(px);
                textView.setMinWidth(px);
                textView2.setTextColor(Color.parseColor("#FFFFFF"));
                // Diferencia opcao(es) mais votada(s)
             if(Integer.parseInt(jsonObject.getString(String.valueOf(i))) == maior){
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
                params2.setGravity(Gravity.CENTER);
                textView.setLayoutParams(params);
                textView2.setLayoutParams(params2);

                // Adiciona opcoes na Grid
                GridLayout gridLayout = (GridLayout)view.findViewById(R.id.grid_layout);
                gridLayout.addView(textView);
                gridLayout.addView(textView2);

                n++;
            }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(view.getContext(), "exception ", Toast.LENGTH_LONG).show();
            }
        }
        return view;
    }

}
