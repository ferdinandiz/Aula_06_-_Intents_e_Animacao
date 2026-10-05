package com.fer.aula06_intentseanimacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fer.aula06_intentseanimacao.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAcessar.setOnClickListener(
                view -> validarAcesso()
        );

    }

    public void validarAcesso(){
        String codinome = binding.editCodinome.getText().toString().trim();
        if(codinome.isEmpty()){
                binding.editCodinome.setError("Informe o Codime!");
                binding.editCodinome.requestFocus();
                return;
        }
        if(codinome.length() <3 ){
            binding.editCodinome.setError("Utilize 3 ou mais caracteres!");
            binding.editCodinome.requestFocus();
            return;
        }
        abrirMissao(codinome);
    }
    private void verificarSenha(String codigo) {
        if(codigo.isEmpty()){
            binding.editCodigo.setError("Informe o Codigo!");
            binding.editCodigo.requestFocus();
            return;
        }
        if(codigo.length() <4 ){
            binding.editCodigo.setError("Utilize 4 ou mais dígitos!");
            binding.editCodigo.requestFocus();
            return;
        }
        if(!codigo.matches("\\d+")){
            binding.editCodigo.setError("Utilize apenas dígitos!");
            binding.editCodigo.requestFocus();
            return;
        }

    }

    private void abrirMissao(String codinome) {
        String codigo = binding.editCodigo.getText().toString().trim();
        verificarSenha(codigo);
        boolean prioritaria = binding.switchPrioritario.isChecked();
        Intent intent = new Intent(this, MissaoActivity.class);
        intent.putExtra(MissaoActivity.EXTRA_CODINOME, codinome);
        intent.putExtra(MissaoActivity.EXTRA_PRIORITARIA, prioritaria);
        startActivity(intent);
    }


}