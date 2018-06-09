package com.ngoe.ucc;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends DialogFragment {
    Switch dayNight,blue,green,teal,indigo,pink;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int DayBlue,NightBlue,DayGreen,NightGreen,DayTeal,NightTeal,DayIndigo,NightIndigo,DayPink,NightPink;

    int cblue,cgreen,cteal,cindigo,cpink;
    int themeColor = 0;
    boolean themeMode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_blank, container, false);

        DayBlue = R.style.DayBlue;
        NightBlue = R.style.NightBlue;
        DayGreen = R.style.DayGreen;
        NightGreen = R.style.NightGreen;
        DayTeal = R.style.DayTeal;
        NightTeal = R.style.NightTeal;
        DayIndigo = R.style.DayIndigo;
        NightIndigo = R.style.NightIndigo;
        DayPink = R.style.DayPink;
        NightPink = R.style.NightPink;

        sharedPreferences = this.getActivity().getSharedPreferences("myLOL", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        themeMode = sharedPreferences.getBoolean("theme",false);
        cblue = sharedPreferences.getInt("blue",0);
        cgreen = sharedPreferences.getInt("green",0);
        cteal = sharedPreferences.getInt("teal",0);
        cindigo = sharedPreferences.getInt("indigo",0);
        cpink = sharedPreferences.getInt("pink",0);

        dayNight = view.findViewById(R.id.dayNith);
        blue = view.findViewById(R.id.blue);
        green = view.findViewById(R.id.green);
        teal = view.findViewById(R.id.teal);
        indigo = view.findViewById(R.id.indigo);
        pink = view.findViewById(R.id.pink);

        dayNight.setChecked(themeMode);

        if (cblue!=0){
            blue.setChecked(true);
        }

        if (cgreen!=0){
            green.setChecked(true);
        }

        if (cteal!=0){
            teal.setChecked(true);
        }

        if (cindigo!=0){
            indigo.setChecked(true);
        }

        if (cpink!=0){
            pink.setChecked(true);
        }

        dayNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("theme",b);
                editor.commit();
                themeMode = sharedPreferences.getBoolean("theme",false);
                changeTheme(themeMode,themeColor);
            }
        });

        blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    blue.setChecked(b);
                    green.setChecked(false);
                    teal.setChecked(false);
                    indigo.setChecked(false);
                    pink.setChecked(false);

                    editor.putInt("color",0);
                    editor.putInt("blue",1);
                    editor.putInt("green",0);
                    editor.putInt("teal",0);
                    editor.putInt("indigo",0);
                    editor.putInt("pink",0);
                    editor.commit();

                    themeColor = sharedPreferences.getInt("color",0);
                    themeMode = sharedPreferences.getBoolean("theme",false);
                    changeTheme(themeMode,themeColor);
                }else{
                    defalutPFF();
                }
            }
        });

        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    blue.setChecked(false);
                    green.setChecked(true);
                    teal.setChecked(false);
                    indigo.setChecked(false);
                    pink.setChecked(false);

                    editor.putInt("color",1);
                    editor.putInt("blue",0);
                    editor.putInt("green",1);
                    editor.putInt("teal",0);
                    editor.putInt("indigo",0);
                    editor.putInt("pink",0);
                    editor.commit();

                    themeColor = sharedPreferences.getInt("color",0);
                    themeMode = sharedPreferences.getBoolean("theme",false);
                    changeTheme(themeMode,themeColor);
                }else{
                    defalutPFF();
                }
            }
        });

        teal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    blue.setChecked(false);
                    green.setChecked(false);
                    teal.setChecked(true);
                    indigo.setChecked(false);
                    pink.setChecked(false);

                    editor.putInt("color",2);
                    editor.putInt("blue",0);
                    editor.putInt("green",0);
                    editor.putInt("teal",1);
                    editor.putInt("indigo",0);
                    editor.putInt("pink",0);
                    editor.commit();

                    themeColor = sharedPreferences.getInt("color",0);
                    themeMode = sharedPreferences.getBoolean("theme",false);
                    changeTheme(themeMode,themeColor);
                }else{
                    defalutPFF();
                }
            }
        });

        indigo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    blue.setChecked(false);
                    green.setChecked(false);
                    teal.setChecked(false);
                    indigo.setChecked(true);
                    pink.setChecked(false);

                    editor.putInt("color",3);
                    editor.putInt("blue",0);
                    editor.putInt("green",0);
                    editor.putInt("teal",0);
                    editor.putInt("indigo",1);
                    editor.putInt("pink",0);
                    editor.commit();

                    themeColor = sharedPreferences.getInt("color",0);
                    themeMode = sharedPreferences.getBoolean("theme",false);
                    changeTheme(themeMode,themeColor);
                }else{
                    defalutPFF();
                }
            }
        });

        pink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b==true){
                    blue.setChecked(false);
                    green.setChecked(false);
                    teal.setChecked(false);
                    indigo.setChecked(false);
                    pink.setChecked(true);

                    editor.putInt("color",4);
                    editor.putInt("blue",0);
                    editor.putInt("green",0);
                    editor.putInt("teal",0);
                    editor.putInt("indigo",0);
                    editor.putInt("pink",1);
                    editor.commit();

                    themeColor = sharedPreferences.getInt("color",0);
                    themeMode = sharedPreferences.getBoolean("theme",false);
                    changeTheme(themeMode,themeColor);
                }else{
                    defalutPFF();
                }
            }
        });
        return view;
    }

    public void changeTheme(boolean themeMode,int themeColor){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        if (themeMode==false){
            switch (themeColor){
                case 0: this.getActivity().setTheme(DayBlue);break;
                case 1: this.getActivity().setTheme(DayGreen);break;
                case 2: this.getActivity().setTheme(DayTeal);break;
                case 3: this.getActivity().setTheme(DayIndigo);break;
                case 4: this.getActivity().setTheme(DayPink);break;
            }
        }else{
            switch (themeColor){
                case 0: this.getActivity().setTheme(NightBlue);break;
                case 1: this.getActivity().setTheme(NightGreen);break;
                case 2: this.getActivity().setTheme(NightTeal);break;
                case 3: this.getActivity().setTheme(NightIndigo);break;
                case 4: this.getActivity().setTheme(NightPink);break;
            }
        }
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    public void defalutPFF(){
        blue.setChecked(true);
        green.setChecked(false);
        teal.setChecked(false);
        indigo.setChecked(false);
        pink.setChecked(true);

        editor.putInt("color",0);
        editor.putInt("blue",1);
        editor.putInt("green",0);
        editor.putInt("teal",0);
        editor.putInt("indigo",0);
        editor.putInt("pink",0);
        editor.commit();

        themeColor = sharedPreferences.getInt("color",0);
        themeMode = sharedPreferences.getBoolean("theme",false);
        changeTheme(themeMode,themeColor);
    }
}
