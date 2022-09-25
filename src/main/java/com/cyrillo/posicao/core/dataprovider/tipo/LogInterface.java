package com.cyrillo.posicao.core.dataprovider.tipo;


import com.cyrillo.posicao.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;

public interface LogInterface {
    public void logError(String flowid, String sessionId, String mensagem);
    public void logInfo(String flowid, String sessionId, String mensagem);
    void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao;

}
