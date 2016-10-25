package br.ufscar.dc.dcopinion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

/**
 * Created by leobronza@hotmail.com on 02/11/15.
 */
public class Fragment_img_responder extends Fragment {
    private ViewPager viewPager;
    private Myadapter_img_swipe myadapter_img_swipe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_img_responder,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String result = bundle.getString("key");

            viewPager = (ViewPager) view.findViewById(R.id.view_page_fragment_img);
            myadapter_img_swipe = new Myadapter_img_swipe(getActivity(),result);
            viewPager.setAdapter(myadapter_img_swipe);
        }
        return view;
    }

    public int getopcaoimg(){
        // Retorna opcao de img selecionada pelo usuario
        return myadapter_img_swipe.getopcaoimg_myadapter();
    }

}
