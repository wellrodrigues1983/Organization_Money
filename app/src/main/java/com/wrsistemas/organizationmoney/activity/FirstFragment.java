package com.wrsistemas.organizationmoney.activity;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.wrsistemas.organizationmoney.R;
import com.wrsistemas.organizationmoney.adapter.AdapterMovimentacao;
import com.wrsistemas.organizationmoney.config.ConfiguracaoFirebase;
import com.wrsistemas.organizationmoney.databinding.ActivityPrincipalBinding;
import com.wrsistemas.organizationmoney.databinding.ContentPrincipalBinding;
import com.wrsistemas.organizationmoney.databinding.FragmentFirstBinding;
import com.wrsistemas.organizationmoney.helper.Base64Custon;
import com.wrsistemas.organizationmoney.model.Movimentacao;
import com.wrsistemas.organizationmoney.model.Usuario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private MaterialCalendarView calendarView;
    private TextView textoSaudacao;
    private TextView textoSaldo;
    private RecyclerView recyclerView;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumoUsuario = 0.0;

    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();
    private String mesAnoSelecionado;
    private DatabaseReference movimetacaoRef;





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

        calendarView = getView().findViewById(R.id.calendarView);
        textoSaudacao = getView().findViewById(R.id.textSaudacao);
        textoSaldo = getView().findViewById(R.id.textSaldo);
        recyclerView = getView().findViewById(R.id.recyclerMovimentos);
        configuraCalendarView();

        //Configurar o adpter
        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);

    }

    public void configuraCalendarView(){

        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        //Recuperar o mes/ano para o metodo recuperarMovimentacoes()
        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth() + 1 ));
        mesAnoSelecionado = String.valueOf(mesSelecionado + "" + dataAtual.getYear());

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = String.format("%02d", (date.getMonth() + 1 ));
                mesAnoSelecionado = String.valueOf(mesSelecionado + "" + date.getYear());

                //limpa as movimentações ao mudar o mes e só aparece as do mes selecionado
                movimetacaoRef.removeEventListener(valueEventListenerMovimentacoes);
                recuperarMovimentacoes();
            }
        });
    }

    public void recuperarMovimentacoes(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custon.codificarBase64(emailUsuario);
        movimetacaoRef = firebaseRef.child("movimentacao")
                                    .child(idUsuario)
                                    .child(mesAnoSelecionado);

        /*Log.i("dadosRetorno", "dados" + mesAnoSelecionado);*/

        valueEventListenerMovimentacoes = movimetacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentacoes.clear();
                for (DataSnapshot dados: snapshot.getChildren()){

                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    movimentacoes.add(movimentacao);
                }

                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperarResumo(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custon.codificarBase64(emailUsuario);
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        Log.i("Evento", "Evento foi adcionado");
        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Usuario usuario = snapshot.getValue(Usuario.class);

                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumoUsuario = receitaTotal - despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(resumoUsuario);

                textoSaudacao.setText("Olá, " + usuario.getNome());
                textoSaldo.setText("R$ " + resultadoFormatado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarResumo();
        recuperarMovimentacoes();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Evento", "Evento foi removido");
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        firebaseRef.removeEventListener(valueEventListenerMovimentacoes);
    }
}