package com.cyrillo.posicao.core.dataprovider.tipo;

import com.cyrillo.posicao.core.tipobasico.UtilitarioInterface;

// Acerto para apontar para conexao interface
public interface DataProviderInterface {
    public LogInterface getLoggingInterface();
    public boolean healthCheckOk(DataProviderInterface data);
    public DataProviderInterface geraSessao();
    public String getSessionId();
    public void setFlowId(String flowId);
    public String getFlowId();
    public UtilitarioInterface getUtilitario();
    public EventoRepositorioInterface getEventoRepositorioInterface();
}
