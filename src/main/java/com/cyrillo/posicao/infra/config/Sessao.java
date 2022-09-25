package com.cyrillo.posicao.infra.config;

import com.cyrillo.posicao.core.dataprovider.tipo.*;
import com.cyrillo.posicao.core.tipobasico.UtilitarioInterface;

import java.util.UUID;

public class Sessao implements DataProviderInterface {

    private UUID sessionId;
    private LogInterface log;
    private String flowId;

    public Sessao(){
        this.sessionId = UUID.randomUUID();
        this.log = Aplicacao.getInstance().gerarNovoObjetoLog();
    }


    public String getSessionId() {
        return String.valueOf(sessionId);
    }
    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    @Override
    public boolean healthCheckOk(DataProviderInterface data) {
        return Aplicacao.getInstance().healthCheckOk(data);
    }

    @Override
    public DataProviderInterface geraSessao() {
        return this;
    }


    public LogInterface getLoggingInterface() {
        return this.log;
    }


    public UtilitarioInterface getUtilitario(){
        return Aplicacao.getInstance().getUtilitario();
    }


    @Override
    public EventoRepositorioInterface getEventoRepositorioInterface() {
        return Aplicacao.getInstance().getEventoRepositorioInterface();
    }

}
