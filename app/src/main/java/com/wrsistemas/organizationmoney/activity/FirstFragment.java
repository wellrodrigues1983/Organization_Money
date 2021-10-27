package com.wrsistemas.organizationmoney.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.wrsistemas.organizationmoney.R;
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



      /*  toolbar = getView().findViewById(getActivity().);
        toolbar.setTitle("Organization & Money");*/

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