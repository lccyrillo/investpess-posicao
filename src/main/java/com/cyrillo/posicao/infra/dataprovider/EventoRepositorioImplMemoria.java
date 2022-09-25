package com.cyrillo.posicao.infra.dataprovider;

import com.cyrillo.posicao.core.dataprovider.tipo.*;

public class EventoRepositorioImplMemoria implements EventoRepositorioInterface {
    @Override
    public void notificarEvento(DataProviderInterface data, EventoInterface eventoInterface) {
        String sessionId = String.valueOf(data.getSessionId());
        String flowId = data.getFlowId();
        LogInterface log = data.getLoggingInterface();
        log.logInfo(flowId, sessionId,eventoInterface.getMensagemEvento());
    }

}
