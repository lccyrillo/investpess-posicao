package com.cyrillo.posicao.infra.dataprovider;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.posicao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.posicao.core.dataprovider.excecao.ComunicacaoLogDataProvExcecao;


public class LogInterfaceImplConsole implements LogInterface {
    private static LogInterfaceImplConsole instance;

    public LogInterfaceImplConsole() {
    }

    public static LogInterfaceImplConsole getInstance(){
        if(instance == null){
            synchronized (LogInterfaceImplConsole.class) {
                if(instance == null){
                    instance = new LogInterfaceImplConsole();
                }
            }

        }
        return instance;
    }

    @Override
    public void logError(String flowid, String sessionId, String mensagem){
        System.out.println("Level: Error | Flow Id: " + flowid + " | Session Id: " + sessionId + " | Mensagem: " +mensagem);
    }

    @Override
    public void logInfo(String flowid, String sessionId, String mensagem) {
        System.out.println("Level: Info | Flow Id: " + flowid + " | Session Id: " + sessionId + " | Mensagem: " +mensagem);
    }


    @Override
    public void healthCheck(DataProviderInterface data) throws ComunicacaoLogDataProvExcecao {

    }
}
