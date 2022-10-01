package com.cyrillo.posicao.infra.entrypoint.servicogrpc;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.posicao.core.dataprovider.tipo.LogInterface;
import com.cyrillo.posicao.core.usecase.excecao.ComunicacaoRepoUseCaseExcecao;
import com.cyrillo.posicao.core.usecase.excecao.ParametrosInvalidosUseCaseExcecao;
import com.cyrillo.posicao.infra.entrypoint.servicogrpc.posicaoproto.MovimentoServiceGrpc;
import com.cyrillo.posicao.infra.entrypoint.servicogrpc.posicaoproto.RegistraMovimentoRequest;
import com.cyrillo.posicao.infra.entrypoint.servicogrpc.posicaoproto.RegistraMovimentoResponse;
import com.cyrillo.posicao.infra.facade.FacadeAplicacao;
import io.grpc.stub.StreamObserver;
//import com.cyrillo.posicao.infra.entrypoint.servicogrpc.negociacaoproto.*;


public class ServicoAplicacaoService extends MovimentoServiceGrpc.MovimentoServiceImplBase {
    private DataProviderInterface data;

    public ServicoAplicacaoService(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
    }

    @Override
    public void registraMovimento(RegistraMovimentoRequest request, StreamObserver<RegistraMovimentoResponse> responseObserver) {
        String msgResultado;
        int codResultado;

        try {

            DataProviderInterface dataProvider = data.geraSessao();
            String sessionId = String.valueOf(dataProvider.getSessionId());
            LogInterface log = dataProvider.getLoggingInterface();

            // Captura dados da requisição
            String flowId = request.getFlowId();
            dataProvider.setFlowId(flowId);
            log.logInfo(flowId, sessionId, "Iniciando método GRPC para ....");

            // Montagem do Objeto DTO para iniciar o caso de uso
            //NegociacaoDto negociacaoDto = geraObjetoDtoComObjetoRequisicao(request);
            // executa o caso de uso
            new FacadeAplicacao().executarAlgumaCoisa(dataProvider);
            codResultado = 200;
            msgResultado = "Negociação registrada!";
        } catch (ComunicacaoRepoUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro na comunicação com repositório de dados!";
        } catch (ParametrosInvalidosUseCaseExcecao e) {
            codResultado = 401;
            msgResultado = "Erro nos parâmetros informados na requisição:" + e.getMessage();
        }
        //Formata objeto de saída
        RegistraMovimentoResponse response = RegistraMovimentoResponse.newBuilder()
                .setResponseCode(codResultado)
                .setResponseMessage(msgResultado)
                .setChaveIdentificadora(1)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}



//        catch (ValoresFinanceirosNaoConferemUseCaseExcecao e) {
//            codResultado = 102;
//            msgResultado = e.getMessage();
//        }
//        catch (AtivoNaoIdentificadoUseCaseExcecao e) {
//            codResultado = 102;
//            msgResultado = e.getMessage();
//        }
//        catch (AtivoNaoEAcaoUseCaseExcecao e) {
//            codResultado = 102;
//            msgResultado = e.getMessage();
//        }
//        catch (ComunicacaoRepoUseCaseExcecao e) {
//            codResultado = 401;
//            msgResultado = "Erro na comunicação com repositório de dados!";
//        }
//        catch (ParametrosInvalidosUseCaseExcecao e) {
//            codResultado = 401;
//            msgResultado = "Erro na persistência do Ativo no banco de dados!";
//        }
//        catch (NotaNegociacaoExistenteUseCaseExcecao e) {
//            codResultado = 101;
//            msgResultado = e.getMessage();
//        }
//