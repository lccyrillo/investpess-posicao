package com.cyrillo.posicao.core.usecase;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.posicao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.posicao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;

public class ExecutarAlgumaAcaoUseCase {

    public ExecutarAlgumaAcaoUseCase() {
    }

    public void executar(DataProviderInterface data) throws ComunicacaoRepoUseCaseExcecao {
        DataProviderInterface dataProvider = data.geraSessao();
        String sessionId = String.valueOf(dataProvider.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = dataProvider.getLoggingInterface();
        log.logInfo(flowId, sessionId, "Iniciando método ExecutarAlgumaAcaoUseCase.executar ...");

    }

}
