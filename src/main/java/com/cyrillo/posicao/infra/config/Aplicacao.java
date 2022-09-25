package com.cyrillo.posicao.infra.config;

import com.cyrillo.posicao.core.dataprovider.excecao.ComunicacaoRepoDataProvExcecao;
import com.cyrillo.posicao.core.dataprovider.tipo.*;
import com.cyrillo.posicao.core.tipobasico.UtilitarioInterface;
import com.cyrillo.posicao.infra.config.excecao.PropriedadeInvalidaConfigExcecao;
import com.cyrillo.posicao.infra.dataprovider.LogInterfaceImplConsole;
import com.cyrillo.posicao.infra.dataprovider.EventoRepositorioImplMemoria;
import com.cyrillo.posicao.infra.entrypoint.servicogrpc.ServidorGRPC;
import com.cyrillo.posicao.infra.util.Utilitario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Aplicacao implements DataProviderInterface {
    private static Aplicacao instance;
    private LogInterface logAplicacao;
    private String logImplementacao;
    private List<String> propriedadeLog; // lista de todos os domínios possíveis para a propriedade de log.
    private List<String> propriedadeRepo;// lista de todos os domínios possíveis para a propriedade de log.
    private String repoImplementacao;
    private UUID sessionId;
    private String flowId;
    private Utilitario utilitario;
    private EventoRepositorioInterface eventoEventoReposito;



    private Aplicacao(){
    }

    public static Aplicacao getInstance(){
        if(instance == null){
            synchronized (Aplicacao.class) {
                if(instance == null){
                    instance = new Aplicacao();
                }
            }
        }
        return instance;
    }

    public void inicializaAplicacao(){
        try {
            this.sessionId = UUID.randomUUID();
            carregarConfiguracoes();
            configurarLogAplicacao();
            configurarRepositorioAplicacao();
            this.logAplicacao.logInfo(null,null, "Inicializando aplicação...");
            this.logAplicacao.logInfo(null,null, "Propriedades de configuração da aplicação carregadas!");
            this.logAplicacao.logInfo(null,null, getConfiguracoesAplicacao());
            utilitario = new Utilitario();

            ServidorGRPC var = new ServidorGRPC(this);



        }
        catch (Exception e){
            System.out.println("Não foi possível inicializar a aplicação.");
            e.printStackTrace();
        }
    }

    private void carregarConfiguracoes() throws PropriedadeInvalidaConfigExcecao {
        PropriedadeInvalidaConfigExcecao propriedadeInvalidaConfigExcecao;
        montarListaConfiguracoes();
        try {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("config/config.properties");
            properties.load(fis);
            this.logImplementacao = properties.getProperty("log.implementacao");
            this.validarConfiguracoes();
        }
        catch (FileNotFoundException e) {
            propriedadeInvalidaConfigExcecao = new PropriedadeInvalidaConfigExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaConfigExcecao.addSuppressed(e);
            throw propriedadeInvalidaConfigExcecao;

        }
        catch (IOException e) {
            propriedadeInvalidaConfigExcecao = new PropriedadeInvalidaConfigExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaConfigExcecao.addSuppressed(e);
            throw propriedadeInvalidaConfigExcecao;
        }
        catch (Exception e) {
            propriedadeInvalidaConfigExcecao = new PropriedadeInvalidaConfigExcecao("Não foi possível carregar o arquivo de configuração: config.properties");
            propriedadeInvalidaConfigExcecao.addSuppressed(e);
            throw propriedadeInvalidaConfigExcecao;
        }
    }

    private void montarListaConfiguracoes() {
        this.propriedadeLog = new ArrayList<>();
        this.propriedadeLog.add("console");
        this.propriedadeRepo = new ArrayList<>();
        this.propriedadeRepo.add("postgres");
        this.propriedadeRepo.add("memoria");
    }

    private void validarConfiguracoes() throws PropriedadeInvalidaConfigExcecao {
        if ( ! propriedadeLog.contains(logImplementacao)){
            throw new PropriedadeInvalidaConfigExcecao("Propriedade: log.implementacao inválida.");
        }
    }

    private void configurarLogAplicacao(){
        this.logAplicacao = new LogInterfaceImplConsole();
    }

    public String getConfiguracoesAplicacao(){
        String descricaoConfiguracaoAplicacao;
        descricaoConfiguracaoAplicacao = "Log: " +logImplementacao;
        return descricaoConfiguracaoAplicacao;
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
        try {
            this.getLoggingInterface().healthCheck(data);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public LogInterface gerarNovoObjetoLog() {
        // deve ler parametros de configurar e instanciar a implementação correta de log
        // Por enquanto so tem uma implementacao
        // pode ser usado para o log de cada sessao / requisição
        return new LogInterfaceImplConsole();
    }

    private void configurarRepositorioAplicacao() {
      //  if (this.repoImplementacao.equals("postgres")){
            //this.ativoRepositorio = new AtivoRepositorioImplcomJDBC();
       // }
       // else {
        this.eventoEventoReposito = new EventoRepositorioImplMemoria();

        // }
    }



    public DataProviderInterface geraSessao(){
        return new Sessao();
    }

    public LogInterface getLoggingInterface() {
        return this.logAplicacao;
    }

    public UtilitarioInterface getUtilitario(){
        return utilitario;
    }

    public EventoRepositorioInterface getEventoRepositorioInterface() {return eventoEventoReposito; }





}
