package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
/**
 * Created by leobronza@hotmail.com on 02/11/15.
 */
public class Fragment_star_responder extends Fragment {
    private RatingBar estrela;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star_responder,container,false);
        estrela = (RatingBar) view.findViewById(R.id.estrela);
        return view;
    }
    public float getEstrela(){

        // Retorna quantidade de estrelas selecionadas pelo usuario
        return estrela.getRating();
    }
}
