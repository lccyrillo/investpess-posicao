package com.cyrillo.posicao.infra.facade;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.posicao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.posicao.core.usecase.ExecutarAlgumaAcaoUseCase;
import com.cyrillo.posicao.core.usecase.excecao.*;

public class FacadeAplicacao {
    public FacadeAplicacao(){
    }
    public void executarAlgumaCoisa(DataProviderInterface data) throws ComunicacaoRepoUseCaseExcecao, ParametrosInvalidosUseCaseExcecao {

        LogInterface log = data.getLoggingInterface();
        String flowId = data.getFlowId();
        String sessionId = data.getSessionId();
        log.logInfo(flowId,sessionId,"Iniciando Facade Registrar Negociação");
        //log.logInfo(flowId,sessionId,negociacaoDto.toString());
        new ExecutarAlgumaAcaoUseCase().executar(data);
    }
}
