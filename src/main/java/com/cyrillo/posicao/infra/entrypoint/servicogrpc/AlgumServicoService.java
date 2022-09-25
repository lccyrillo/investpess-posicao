package com.cyrillo.posicao.infra.entrypoint.servicogrpc;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
//import com.cyrillo.posicao.infra.entrypoint.servicogrpc.negociacaoproto.*;


public class AlgumServicoService {//extends NegociacaoServiceGrpc.NegociacaoServiceImplBase {
    private DataProviderInterface data;

    public AlgumServicoService(DataProviderInterface dataProviderInterface){
        this.data = dataProviderInterface;
    }

//    @Override
//    public void registraNegociacao(RegistraNegociacaoRequest request, StreamObserver<RegistraNegociacaoResponse> responseObserver) {
//        String msgResultado;
//        int codResultado;
//
//        try {
//
//            DataProviderInterface dataProvider = data.geraSessao();
//            String sessionId = String.valueOf(dataProvider.getSessionId());
//            LogInterface log = dataProvider.getLoggingInterface();
//
//            // Captura dados da requisição
//            String flowId = request.getFlowId();
//            dataProvider.setFlowId(flowId);
//            log.logInfo(flowId, sessionId,"Iniciando método GRPC para registro de negociação.");
//
//            // Montagem do Objeto DTO para iniciar o caso de uso
//            NegociacaoDto negociacaoDto = geraObjetoDtoComObjetoRequisicao(request);
//
//            // executa o caso de uso
//            new FacadeNegociacao().executarRegistrarNegocicacao(dataProvider,negociacaoDto);
//            codResultado = 200;
//            msgResultado = "Negociação registrada!";
//        }
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
//        //Formata objeto de saída
//        RegistraNegociacaoResponse response = RegistraNegociacaoResponse.newBuilder()
//                .setResponseCode(codResultado)
//                .setResponseMessage(msgResultado)
//                .setChaveIdentificadoraNegociacao(1)
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }



}