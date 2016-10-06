package com.bol.beatoflights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jackb on 06/10/2016.
 */
public class ScreenSlidePageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate( R.layout.fragment_screen_slide_page, container, false);
        ((TextView) rootView.findViewById(R.id.text3)).setText(
                getString(R.string.app_name));
        return rootView;
        }
}
