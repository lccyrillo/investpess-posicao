package com.cyrillo.posicao.infra.entrypoint.servicogrpc;

import com.cyrillo.posicao.core.dataprovider.tipo.DataProviderInterface;
import com.cyrillo.posicao.core.dataprovider.tipo.LogInterface;

// Avaliar melhor as importacoes. O Entry poiny não deveria referenciar frameworks externos.
import com.cyrillo.posicao.infra.config.Aplicacao;
import io.grpc.*;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.grpc.MetricCollectingServerInterceptor;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;


public class ServidorGRPC {
    private DataProviderInterface data;

    public ServidorGRPC(DataProviderInterface dataProviderInterface) {
        this.data = dataProviderInterface;
        this.inicializaServidor();

    }

    private void inicializaServidor() {
        
        LogInterface log = Aplicacao.getInstance().getLoggingInterface();
        log.logInfo(null,null,"Inicializando Servidor GRPC.");


        try {

            //CollectorRegistry collectorRegistry = new CollectorRegistry().register(new Collector() );
            //MonitoringServerInterceptor monitoringInterceptor = MonitoringServerInterceptor.create(Configuration.cheapMetricsOnly());

            //grpcServer = ServerBuilder.forPort(GRPC_PORT)
            //        .addService(ServerInterceptors.intercept(
            //                HelloServiceGrpc.bindService(new HelloServiceImpl()), monitoringInterceptor))
            //        .build();



            List<ServerServiceDefinition> lista = new ArrayList<>();
            ServicoAplicacaoService servicoAplicacaoService = new ServicoAplicacaoService(this.data);
            HealthCheckService healthCheckService = new HealthCheckService();

            //MeterRegistry meterRegistry = new MeterRegistry();

            //MetricCollectingServerInterceptor metricCollectingServerInterceptor = new MetricCollectingServerInterceptor(meterRegistry);

            // com prometheus
            //Server server = ServerBuilder.forPort(8080)
            //        .intercept(new MetricCollectingServerInterceptor(meterRegistry))
            //        .build();



            //lista.add(ServerInterceptors.intercept(servicoAplicacaoService.bindService(),monitoringInterceptor));
            //lista.add(ServerInterceptors.intercept(healthCheckService.bindService(),monitoringInterceptor));

            // sem prometheus
            lista.add(servicoAplicacaoService.bindService());
            lista.add(healthCheckService.bindService());

            Server server = ServerBuilder.forPort(50051)
                    .addServices(lista)
                    .build()
                    .start();

            Runtime.getRuntime().addShutdownHook(new Thread( () -> {
                log.logInfo(null,null,"Recebida solicitação para encerramento do servidor.");
                server.shutdown();
                log.logInfo(null,null,"Servidor GRPC encerrado com sucesso!");

            }  ));
            log.logInfo(null,null,"Servidor GRPC inicializado com sucesso!");
            log.logInfo(null,null,listaMetodosServico(lista));
            server.awaitTermination();
        }
        catch (Exception e){
            log.logError(null,null, "Falha na inicialização do servidor GRPC.");
            log.logError(null, null,e.getMessage());
        }

    }

    private String listaMetodosServico(List<ServerServiceDefinition> lista){
        String mensagem = "";
        for (int i =0; i <lista.size(); i++) {
            mensagem = mensagem + "Serviço: ";
                    mensagem = mensagem + lista.get(i).getServiceDescriptor().getName() + " Métodos: ";
            for (ServerMethodDefinition<?, ?> methodDesc : lista.get(i).getMethods()) {
                mensagem = mensagem + methodDesc.getMethodDescriptor().getBareMethodName() + " ";
            }
        }
        return mensagem;
    }
}
