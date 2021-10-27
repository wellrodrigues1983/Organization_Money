package com.wrsistemas.organizationmoney.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.wrsistemas.organizationmoney.R;
import com.wrsistemas.organizationmoney.databinding.ActivityPrincipalBinding;
import com.wrsistemas.organizationmoney.databinding.ContentPrincipalBinding;
import com.wrsistemas.organizationmoney.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textoSaudacao;
    private TextView textoSaldo;
    private RecyclerView recyclerMovimentacao;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





//        ((MainActivity) getActivity()).getSupportActionBar().setTitle("ActionBarName");


        /*View actionBar;
        actionBar = getView().findViewById(R.id.toolbar);
        if (actionBar != null){
            ((PrincipalActivity) getActivity()).getSupportActionBar().setTitle("teste");

        }*/


        calendarView = getView().findViewById(R.id.calendarView);
        textoSaudacao = getView().findViewById(R.id.textSaudacao);
        textoSaldo = getView().findViewById(R.id.textSaldo);
        recyclerMovimentacao = getView().findViewById(R.id.recyclerMovimentos);

        configuraCalendarView();

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void configuraCalendarView(){

        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {



            }
        });
    }




}