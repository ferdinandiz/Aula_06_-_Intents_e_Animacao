package com.fer.aula06_intentseanimacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fer.aula06_intentseanimacao.databinding.ActivityMissaoDetalhesBinding;

public class MissaoDetalhesActivity extends AppCompatActivity {
    public static final String EXTRA_CODINOME = "com.fer.aula06_intentseanimacao.EXTRA_CODINOME";
    public static final String EXTRA_TITULO = "com.fer.aula06_intentseanimacao.EXTRA_TITULO";
    public static final String EXTRA_NIVEL = "com.fer.aula06_intentseanimacao.EXTRA_NIVEL";
    public static final String EXTRA_DETALHE = "com.fer.aula06_intentseanimacao.EXTRA_DETALHE";
    public static final String EXTRA_PRIORITARIA = "com.fer.aula06_intentseanimacao.EXTRA_PRIORITARIA";

    private ActivityMissaoDetalhesBinding binding;

    private String codinome;
    private String titulo;
    private String detalhes;
    private int nivel;
    private boolean prioritaria;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMissaoDetalhesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!receberEValidarDados()) {
            return;
        }

        preencherTela();
        configurarAcoes();
        executarAnimacaoDeAbertura();

    }

    private boolean receberEValidarDados() {
        Intent intent = getIntent();

        codinome = intent.getStringExtra(EXTRA_CODINOME);
        titulo = intent.getStringExtra(EXTRA_TITULO);
        nivel = intent.getIntExtra(EXTRA_NIVEL, -1);
        detalhes = intent.getStringExtra(EXTRA_DETALHE);
        prioritaria = intent.getBooleanExtra(EXTRA_PRIORITARIA, false);

        if (codinome == null || codinome.trim().isEmpty()
                || titulo == null || titulo.trim().isEmpty()
                || detalhes == null || detalhes.trim().isEmpty()
                || nivel < 0) {

            Toast.makeText(this,
                    "Dados da missão inválidos",
                    Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        return true;
    }

    private void preencherTela() {
        binding.textTituloMissao.setText(titulo);
        binding.textAgente.setText("Agente: " + codinome);
        binding.textBriefing.setText(detalhes);
        binding.textNivelRisco.setText("Nível de risco: " + nomeDoRisco(nivel));

        if (prioritaria) {
            binding.textPrioridade.setText("MISSÃO PRIORITÁRIA");
            binding.textPrioridade.setVisibility(View.VISIBLE);
        } else {
            binding.textPrioridade.setVisibility(View.GONE);
        }

        aplicarCorDoRisco(nivel);

        binding.buttonIniciar.setEnabled(false);
        binding.buttonIniciar.setVisibility(View.INVISIBLE);
    }

    private String nomeDoRisco(int nivel) {
        switch (nivel) {
            case 1:
                return "BAIXO";
            case 2:
                return "MÉDIO";
            case 3:
                return "ALTO";
            default:
                return "DESCONHECIDO";
        }
    }

    private void aplicarCorDoRisco(int nivel) {
        int cor;

        switch (nivel) {
            case 1:
                cor = R.color.risco_baixo;
                break;
            case 2:
                cor = R.color.risco_medio;
                break;
            case 3:
                cor = R.color.risco_alto;
                break;
            default:
                cor = R.color.risco_desconhecido;
                break;
        }

        binding.textNivelRisco.setTextColor(getColor(cor));
    }

    private void configurarAcoes() {
        binding.buttonVoltar.setOnClickListener(view -> finish());

        binding.buttonIniciar.setOnClickListener(view ->
                Toast.makeText(this,
                        "Missão iniciada. Boa sorte, " + codinome + "!",
                        Toast.LENGTH_SHORT).show()
        );

        binding.buttonEncerrar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void executarAnimacaoDeAbertura() {
        binding.painelEsquerdo.animate()
                .translationX(-1000f)
                .setDuration(1200)
                .start();

        binding.painelDireito.animate()
                .translationX(1000f)
                .setDuration(1200)
                .start();

        binding.textTituloMissao.postDelayed(() -> {
            Animation fadeIn = AnimationUtils.loadAnimation(
                    this, R.anim.fade_in);

            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.buttonIniciar.setVisibility(View.VISIBLE);
                    binding.buttonIniciar.setEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            binding.textTituloMissao.startAnimation(fadeIn);
        }, 400);
        binding.imageIconeMissao.postDelayed(() -> {
            Animation zoomIn = AnimationUtils.loadAnimation(
                    this, R.anim.zoom_in);
            binding.imageIconeMissao.startAnimation(zoomIn);
        }, 800);
    }

}
