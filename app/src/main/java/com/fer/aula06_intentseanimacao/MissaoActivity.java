package com.fer.aula06_intentseanimacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fer.aula06_intentseanimacao.databinding.ActivityMissaoBinding;

public class MissaoActivity extends AppCompatActivity {
    public static final String EXTRA_CODINOME = "com.fer.aula06_intentseanimacao.CODINOME";
    public static final String EXTRA_PRIORITARIA = "com.fer.aula06_intentseanimacao.PRIORITARIA";

    private ActivityMissaoBinding binding;
    private String codinome;
    private boolean prioritaria;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMissaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!receberDados()){
            return;
        }
        configurarTela();
        configurarAcoes();
        animarEntradaDeMissao();
    }

    private boolean receberDados() {
        Intent i = getIntent();
        codinome = i.getStringExtra(EXTRA_CODINOME);
        prioritaria = i.getBooleanExtra(EXTRA_PRIORITARIA,false);
        if(codinome == null || codinome.trim().isEmpty()){
            Toast.makeText(this, "Dados do Agente Inválidos", Toast.LENGTH_SHORT).show();
            finish();
            return  false;
        }
        return true;
    }

    private void configurarTela() {
        binding.textAgente.setText("AGENTE: "+codinome.toUpperCase());
        if(prioritaria){
            binding.textPrioritaria.setText("ATENDIMENTO PRIORITÁRIO ATIVO");
            binding.textPrioritaria.setVisibility(View.VISIBLE);
        } else {
            binding.textPrioritaria.setVisibility(View.GONE);
        }
    }

    private void configurarAcoes() {
        binding.buttonMissao1.setOnClickListener(
                view -> abrirMissao(
                        binding.buttonMissao1.getText().toString(),
                        3,
                        "Entre num complexo industrial abandonado sob o manto da meia-noite, onde a única luz visível é o brilho intermitente das telas de segurança. Seu objetivo é o servidor central no subsolo."
                )
        );
        binding.buttonMissao2.setOnClickListener(
                view -> abrirMissao(
                        binding.buttonMissao2.getText().toString(),
                        2,
                        "Encontre o agente infiltrado conhecido pelo codinome Fênix. Nosso último contato com ele ocorreu há exatamente 12 horas, pouco antes de sua frequência de rádio ser abruptamente cortada no setor sul da cidade. Ele possui a chave de criptografia necessária para decodificar os arquivos do projeto \"Aurora\"."
                )
        );
        binding.buttonMissao3.setOnClickListener(
                view -> abrirMissao(
                        binding.buttonMissao3.getText().toString(),
                        1,
                        "Observe a área detalhadamente antes de iniciar qualquer progressão. Sua principal diretriz nesta missão de reconhecimento é mapear o perímetro sem comprometer sua posição ou revelar sua presença."
                )
        );
        binding.buttonVoltar.setOnClickListener(
                view -> finish()
        );
    }

    private void abrirMissao(String titulo, int nivel, String detalhe) {
        Intent intent = new Intent(this, MissaoDetalhesActivity.class);
        intent.putExtra(MissaoDetalhesActivity.EXTRA_CODINOME, codinome);
        intent.putExtra(MissaoDetalhesActivity.EXTRA_TITULO, titulo);
        intent.putExtra(MissaoDetalhesActivity.EXTRA_NIVEL, nivel);
        intent.putExtra(MissaoDetalhesActivity.EXTRA_DETALHE, detalhe);
        intent.putExtra(MissaoDetalhesActivity.EXTRA_PRIORITARIA, prioritaria);
        startActivity(intent);
    }

    private void animarEntradaDeMissao(){
        Animation slideR = AnimationUtils.loadAnimation(this, R.anim.slide_from_right);
        Animation slideL = AnimationUtils.loadAnimation(this, R.anim.slide_from_left);
        binding.containerMissoes.startAnimation(slideR);
        binding.buttonVoltar.startAnimation(slideL);
    }

}
