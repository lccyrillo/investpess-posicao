package com.cyrillo.posicao;

import com.cyrillo.posicao.infra.config.Aplicacao;

public class Inicializacao {

    public static void main(String[] args)  {
        Aplicacao.getInstance().inicializaAplicacao();
    }
}
