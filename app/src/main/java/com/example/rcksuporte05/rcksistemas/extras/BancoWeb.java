package com.example.rcksuporte05.rcksistemas.extras;


import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroPendente;
import com.example.rcksuporte05.rcksistemas.classes.HistoricoFinanceiroQuitado;
import com.example.rcksuporte05.rcksistemas.classes.Municipios;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.Paises;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BancoWeb {

    String URL = "http://rcksistemassuporte.ddns.com.br:3383/Banco/Banco?wsdl"; //URL Maquina 05
//    String URL = "http://rcksistemassuporte.ddns.com.br:3384/Banco/Banco?wsdl";//URL Servidor RCK
//    String URL = "http://tksimports.ddns.com.br:3385/Banco/Banco?wsdl";//URL Servidor TKS

    String pacote = "http://DB/";
    SoapObject soap;
    HttpTransportSE httpTrans;
    SoapSerializationEnvelope envelope;

    public BancoWeb() {

    }

    public String ConsultaSQL(String SQL, String campo) throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "ConsultaSQL");
        soap.addProperty("SQL", SQL);
        soap.addProperty("campo", campo);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("ConsultaSQL", envelope);

        Object resultado = envelope.getResponse();

        String retorno = resultado.toString();

        if (retorno.equals("Nulo")) {
            return null;
        } else {
            return retorno;
        }
    }

    public String Atualiza(String SQL) throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "Atualiza");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);
        httpTrans.call("Atualiza", envelope);

        Object resultado = envelope.getResponse();

        return String.valueOf(resultado);
    }

    public ArrayList lista(String SQL, String campo) throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "lista");
        soap.addProperty("SQL", SQL);
        soap.addProperty("campo", campo);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("lista", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        ArrayList lista = new ArrayList();

        for (int c = 0; c < response.getPropertyCount(); c++) {
            lista.add(response.getProperty(c));
        }
        return lista;
    }

    public String AtualizaBanco() throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "atualizaBanco");

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("atualizaBanco", envelope);

        Object resultado = envelope.getResponse();

        return resultado.toString();
    }

    public Cliente[] sincronizaCliente(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaCliente");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaCliente", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Cliente[] listaCliente = new Cliente[response.getPropertyCount()];


        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);


            Cliente cliente = new Cliente();

            cliente.setAtivo(soapObject.getProperty("ativo").toString());
            cliente.setId_empresa(soapObject.getProperty("id_empresa").toString());
            cliente.setId_cadastro(soapObject.getProperty("id_cadastro").toString());
            cliente.setPessoa_f_j(soapObject.getProperty("pessoa_f_j").toString());
            cliente.setData_aniversario(soapObject.getProperty("data_aniversario").toString());
            cliente.setNome_cadastro(soapObject.getProperty("nome_cadastro").toString());
            cliente.setNome_fantasia(soapObject.getProperty("nome_fantasia").toString());
            cliente.setCpf_cnpj(soapObject.getProperty("cpf_cnpj").toString());
            cliente.setInscri_estadual(soapObject.getProperty("inscri_estadual").toString());
            cliente.setInscri_municipal(soapObject.getProperty("inscri_municipal").toString());
            cliente.setEndereco(soapObject.getProperty("endereco").toString());
            cliente.setEndereco_bairro(soapObject.getProperty("endereco_bairro").toString());
            cliente.setEndereco_numero(soapObject.getProperty("endereco_numero").toString());
            cliente.setEndereco_complemento(soapObject.getProperty("endereco_complemento").toString());
            cliente.setEndereco_uf(soapObject.getProperty("endereco_uf").toString());
            cliente.setEndereco_id_municipio(soapObject.getProperty("endereco_id_municipio").toString());
            cliente.setEndereco_cep(soapObject.getProperty("endereco_cep").toString());
            cliente.setUsuario_id(soapObject.getProperty("usuario_id").toString());
            cliente.setUsuario_nome(soapObject.getProperty("usuario_nome").toString());
            cliente.setUsuario_data(soapObject.getProperty("usuario_data").toString());
            cliente.setF_cliente(soapObject.getProperty("f_cliente").toString());
            cliente.setF_fornecedor(soapObject.getProperty("f_fornecedor").toString());
            cliente.setF_funcionario(soapObject.getProperty("f_funcionario").toString());
            cliente.setF_vendedor(soapObject.getProperty("f_vendedor").toString());
            cliente.setF_transportador(soapObject.getProperty("f_transportador").toString());
            cliente.setData_ultima_compra(soapObject.getProperty("data_ultima_compra").toString());
            cliente.setId_vendedor(soapObject.getProperty("id_vendedor").toString());
            cliente.setF_id_cliente(soapObject.getProperty("f_id_cliente").toString());
            cliente.setId_entidade(soapObject.getProperty("id_entidade").toString());
            cliente.setF_id_fornecedor(soapObject.getProperty("f_id_fornecedor").toString());
            cliente.setF_id_vendedor(soapObject.getProperty("f_id_vendedor").toString());
            cliente.setF_id_transportador(soapObject.getProperty("f_id_transportador").toString());
            cliente.setTelefone_principal(soapObject.getProperty("telefone_principal").toString());
            cliente.setEmail_principal(soapObject.getProperty("email_principal").toString());
            cliente.setId_pais(soapObject.getProperty("id_pais").toString());
            cliente.setF_id_funcionario(soapObject.getProperty("f_id_funcionario").toString());
            cliente.setAvisar_com_dias(soapObject.getProperty("avisar_com_dias").toString());
            cliente.setObservacoes(soapObject.getProperty("observacoes").toString());
            cliente.setPadrao_id_c_custo(soapObject.getProperty("padrao_id_c_custo").toString());
            cliente.setPadrao_id_c_gerenciadora(soapObject.getProperty("padrao_id_c_gerenciadora").toString());
            cliente.setPadrao_id_c_analitica(soapObject.getProperty("padrao_id_c_analitica").toString());
            cliente.setCob_endereco(soapObject.getProperty("cob_endereco").toString());
            cliente.setCob_endereco_bairro(soapObject.getProperty("cob_endereco_bairro").toString());
            cliente.setCob_endereco_numero(soapObject.getProperty("cob_endereco_numero").toString());
            cliente.setCob_endereco_complemento(soapObject.getProperty("cob_endereco_complemento").toString());
            cliente.setCob_endereco_uf(soapObject.getProperty("cob_endereco_uf").toString());
            cliente.setCob_endereco_id_municipio(soapObject.getProperty("cob_endereco_id_municipio").toString());
            cliente.setCob_endereco_cep(soapObject.getProperty("cob_endereco_cep").toString());
            cliente.setCob_endereco_id_pais(soapObject.getProperty("cob_endereco_id_pais").toString());
            cliente.setLimite_credito(soapObject.getProperty("limite_credito").toString());
            cliente.setLimite_disponivel(soapObject.getProperty("limite_disponivel").toString());
            cliente.setPessoa_contato_financeiro(soapObject.getProperty("pessoa_contato_financeiro").toString());
            cliente.setEmail_financeiro(soapObject.getProperty("email_financeiro").toString());
            cliente.setObservacoes_faturamento(soapObject.getProperty("observacoes_faturamento").toString());
            cliente.setObservacoes_financeiro(soapObject.getProperty("observacoes_financeiro").toString());
            cliente.setTelefone_dois(soapObject.getProperty("telefone_dois").toString());
            cliente.setTelefone_tres(soapObject.getProperty("telefone_tres").toString());
            cliente.setPessoa_contato_principal(soapObject.getProperty("pessoa_contato_principal").toString());
            cliente.setInd_da_ie_destinatario(soapObject.getProperty("ind_da_ie_destinatario").toString());
            cliente.setComissao_percentual(soapObject.getProperty("comissao_percentual").toString());
            cliente.setId_setor(soapObject.getProperty("id_setor").toString());
            cliente.setNfe_email_enviar(soapObject.getProperty("nfe_email_enviar").toString());
            cliente.setNfe_email_um(soapObject.getProperty("nfe_email_um").toString());
            cliente.setNfe_email_dois(soapObject.getProperty("nfe_email_dois").toString());
            cliente.setNfe_email_tres(soapObject.getProperty("nfe_email_tres").toString());
            cliente.setNfe_email_quatro(soapObject.getProperty("nfe_email_quatro").toString());
            cliente.setNfe_email_cinco(soapObject.getProperty("nfe_email_cinco").toString());
            cliente.setId_grupo_vendedor(soapObject.getProperty("id_grupo_vendedor").toString());
            cliente.setVendedor_usa_portal(soapObject.getProperty("vendedor_usa_portal").toString());
            cliente.setVendedor_id_user_portal(soapObject.getProperty("vendedor_id_user_portal").toString());
            cliente.setF_tarifa(soapObject.getProperty("f_tarifa").toString());
            cliente.setF_id_tarifa(soapObject.getProperty("f_id_tarifa").toString());
            cliente.setF_produtor(soapObject.getProperty("f_produtor").toString());
            cliente.setRg_numero(soapObject.getProperty("rg_numero").toString());
            cliente.setRg_ssp(soapObject.getProperty("rg_ssp").toString());
            cliente.setConta_contabil(soapObject.getProperty("conta_contabil").toString());
            cliente.setMotorista(soapObject.getProperty("motorista").toString());
            cliente.setF_id_motorista(soapObject.getProperty("f_id_motorista").toString());
            cliente.setHabilitacao_numero(soapObject.getProperty("habilitacao_numero").toString());
            cliente.setHabilitacao_categoria(soapObject.getProperty("habilitacao_categoria").toString());
            cliente.setHabilitacao_vencimento(soapObject.getProperty("habilitacao_vencimento").toString());
            cliente.setMot_id_transportadora(soapObject.getProperty("mot_id_transportadora").toString());
            cliente.setLocal_cadastro(soapObject.getProperty("local_cadastro").toString());

            listaCliente[i] = cliente;
        }

        return listaCliente;
    }

    public Usuario[] sincronizaUsuario(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaUsuario");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaUsuario", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Usuario[] listaUsuario = new Usuario[response.getPropertyCount()];


        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            Usuario usuario = new Usuario();


            usuario.setId_usuario(soapObject.getProperty("id_usuario").toString());
            usuario.setAtivo(soapObject.getProperty("ativo").toString());
            usuario.setNome_usuario(soapObject.getProperty("nome_usuario").toString());
            usuario.setLogin(soapObject.getProperty("login").toString());
            usuario.setSenha(soapObject.getProperty("senha").toString());
            usuario.setSenha_confirma(soapObject.getProperty("senha_confirma").toString());
            usuario.setData_cadastro(soapObject.getProperty("data_cadastro").toString());
            usuario.setUsuario_cadatro(soapObject.getProperty("usuario_cadatro").toString());
            usuario.setData_alterado(soapObject.getProperty("data_alterado").toString());
            usuario.setUsuario_alterou(soapObject.getProperty("usuario_alterou").toString());
            usuario.setAparece_cad_usuario(soapObject.getProperty("aparece_cad_usuario").toString());
            usuario.setCliente_lista_todos(soapObject.getProperty("cliente_lista_todos").toString());
            usuario.setCliente_lista_setor(soapObject.getProperty("cliente_lista_setor").toString());
            usuario.setCliente_lista_representante(soapObject.getProperty("cliente_lista_representante").toString());
            usuario.setPedido_lista_todos(soapObject.getProperty("pedido_lista_todos").toString());
            usuario.setPedido_lista_setor(soapObject.getProperty("pedido_lista_setor").toString());
            usuario.setPedido_lista_representante(soapObject.getProperty("pedido_lista_representante").toString());
            usuario.setMensagem_lista_financeiro(soapObject.getProperty("mensagem_lista_financeiro").toString());
            usuario.setMensagem_lista_todos(soapObject.getProperty("mensagem_lista_todos").toString());
            usuario.setMensagem_lista_setor(soapObject.getProperty("mensagem_lista_setor").toString());
            usuario.setMensagem_lista_representante(soapObject.getProperty("mensagem_lista_representante").toString());
            usuario.setOrcamento_lista_todos(soapObject.getProperty("orcamento_lista_todos").toString());
            usuario.setOrcamento_lista_setor(soapObject.getProperty("orcamento_lista_setor").toString());
            usuario.setOrcamento_lista_representante(soapObject.getProperty("orcamento_lista_representante").toString());
            usuario.setUsuario_lista_todos(soapObject.getProperty("usuario_lista_todos").toString());
            usuario.setUsuario_lista_setor(soapObject.getProperty("usuario_lista_setor").toString());
            usuario.setUsuario_lista_representante(soapObject.getProperty("usuario_lista_representante").toString());
            usuario.setExcluido(soapObject.getProperty("excluido").toString());
            usuario.setId_setor(soapObject.getProperty("id_setor").toString());
            usuario.setId_quando_vendedor(soapObject.getProperty("id_quando_vendedor").toString());
            usuario.setAparelho_id(soapObject.getProperty("aparelho_id").toString());

            listaUsuario[i] = usuario;
        }

        return listaUsuario;
    }

    public Produto[] sincronizaProduto(String SQL) throws IOException, XmlPullParserException {

        soap = new SoapObject(pacote, "sincronizaProduto");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaProduto", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Produto[] listaProduto = new Produto[response.getPropertyCount()];


        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            Produto produto = new Produto();

            produto.setAtivo(soapObject.getProperty("ativo").toString());
            produto.setId_produto(soapObject.getProperty("id_produto").toString());
            produto.setNome_produto(soapObject.getProperty("nome_produto").toString());
            produto.setUnidade(soapObject.getProperty("unidade").toString());
            produto.setTipo_cadastro(soapObject.getProperty("tipo_cadastro").toString());
            produto.setId_entidade(soapObject.getProperty("id_entidade").toString());
            produto.setNcm(soapObject.getProperty("ncm").toString());
            produto.setId_grupo(soapObject.getProperty("id_grupo").toString());
            produto.setId_sub_grupo(soapObject.getProperty("id_sub_grupo").toString());
            produto.setPeso_bruto(soapObject.getProperty("peso_bruto").toString());
            produto.setPeso_liquido(soapObject.getProperty("peso_liquido").toString());
            produto.setCodigo_em_barras(soapObject.getProperty("codigo_em_barras").toString());
            produto.setMovimenta_estoque(soapObject.getProperty("movimenta_estoque").toString());
            produto.setNome_da_marca(soapObject.getProperty("nome_da_marca").toString());
            produto.setId_empresa(soapObject.getProperty("id_empresa").toString());
            produto.setId_origem(soapObject.getProperty("id_origem").toString());
            produto.setCusto_produto(soapObject.getProperty("custo_produto").toString());
            produto.setCusto_per_ipi(soapObject.getProperty("custo_per_ipi").toString());
            produto.setCusto_ipi(soapObject.getProperty("custo_ipi").toString());
            produto.setCusto_per_frete(soapObject.getProperty("custo_per_frete").toString());
            produto.setCusto_frete(soapObject.getProperty("custo_frete").toString());
            produto.setCusto_per_icms(soapObject.getProperty("custo_per_icms").toString());
            produto.setCusto_icms(soapObject.getProperty("custo_icms").toString());
            produto.setCusto_per_fin(soapObject.getProperty("custo_per_fin").toString());
            produto.setCusto_fin(soapObject.getProperty("custo_fin").toString());
            produto.setCusto_per_subst(soapObject.getProperty("custo_per_subst").toString());
            produto.setCusto_subt(soapObject.getProperty("custo_subt").toString());
            produto.setCusto_per_outros(soapObject.getProperty("custo_per_outros").toString());
            produto.setCusto_outros(soapObject.getProperty("custo_outros").toString());
            produto.setValor_custo(soapObject.getProperty("valor_custo").toString());
            produto.setExcluido(soapObject.getProperty("excluido").toString());
            produto.setExcluido_por(soapObject.getProperty("excluido_por").toString());
            produto.setExcluido_por_data(soapObject.getProperty("excluido_por_data").toString());
            produto.setExcluido_codigo_novo(soapObject.getProperty("excluido_codigo_novo").toString());
            produto.setAjuste_preco_data(soapObject.getProperty("ajuste_preco_data").toString());
            produto.setAjuste_preco_nfe(soapObject.getProperty("ajuste_preco_nfe").toString());
            produto.setAjuste_preco_usuario(soapObject.getProperty("ajuste_preco_usuario").toString());
            produto.setTotal_custo(soapObject.getProperty("total_custo").toString());
            produto.setTotal_credito(soapObject.getProperty("total_credito").toString());
            produto.setValor_custo_estoque(soapObject.getProperty("valor_custo_estoque").toString());
            produto.setCusto_data_inicial(soapObject.getProperty("custo_data_inicial").toString());
            produto.setCusto_valor_inicial(soapObject.getProperty("custo_valor_inicial").toString());
            produto.setProduto_venda(soapObject.getProperty("produto_venda").toString());
            produto.setProduto_insumo(soapObject.getProperty("produto_insumo").toString());
            produto.setProduto_consumo(soapObject.getProperty("produto_consumo").toString());
            produto.setProduto_producao(soapObject.getProperty("produto_producao").toString());
            produto.setVenda_perc_comissao(soapObject.getProperty("venda_perc_comissao").toString());
            produto.setVenda_preco(soapObject.getProperty("venda_preco").toString());
            produto.setVenda_perc_comissao_dois(soapObject.getProperty("venda_perc_comissao_dois").toString());
            produto.setDescricao(soapObject.getProperty("descricao").toString());

            listaProduto[i] = produto;
        }
        return listaProduto;
    }

    public Paises[] sincronizaPaises(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaPaises");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaPaises", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Paises[] listaPaises = new Paises[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            Paises paises = new Paises();

            paises.setId_pais(soapObject.getProperty("id_pais").toString());
            paises.setNome_pais(soapObject.getProperty("nome_pais").toString());

            listaPaises[i] = paises;
        }

        return listaPaises;
    }

    public Municipios[] sincronizaMunicipios(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaMunicipios");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaMunicipios", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Municipios[] listaMunicipios = new Municipios[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            Municipios municipios = new Municipios();

            municipios.setId_municipio(soapObject.getProperty("id_municipio").toString());
            municipios.setCep(soapObject.getProperty("cep").toString());
            municipios.setNome_municipio(soapObject.getProperty("nome_municipio").toString());
            municipios.setUf(soapObject.getProperty("uf").toString());

            listaMunicipios[i] = municipios;
        }
        return listaMunicipios;
    }

    public Operacao[] sincronizaOperacao(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaOperacao");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaOperacao", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        Operacao[] listaOperacao = new Operacao[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            Operacao operacao = new Operacao();

            operacao.setAtivo(soapObject.getProperty("ativo").toString());
            operacao.setId_operacao(soapObject.getProperty("id_operacao").toString());
            operacao.setNome_operacao(soapObject.getProperty("nome_operacao").toString());

            listaOperacao[i] = operacao;
        }
        return listaOperacao;
    }

    public TabelaPreco[] sincronizaTabelaPreco(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaTabelaPreco");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaTabelaPreco", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        TabelaPreco[] listaTabelaPreco = new TabelaPreco[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            TabelaPreco tabelaPreco = new TabelaPreco();

            tabelaPreco.setId_tabela(soapObject.getProperty("id_tabela").toString());
            tabelaPreco.setId_empresa(soapObject.getProperty("id_empresa").toString());
            tabelaPreco.setAtivo(soapObject.getProperty("ativo").toString());
            tabelaPreco.setId_tipo_tabela(soapObject.getProperty("id_tipo_tabela").toString());
            tabelaPreco.setNome_tabela(soapObject.getProperty("nome_tabela").toString());
            tabelaPreco.setData_inicio(soapObject.getProperty("data_inicio").toString());
            tabelaPreco.setData_fim(soapObject.getProperty("data_fim").toString());
            tabelaPreco.setDesconto_de_perc(soapObject.getProperty("desconto_de_perc").toString());
            tabelaPreco.setDesconto_a_perc(soapObject.getProperty("desconto_a_perc").toString());
            tabelaPreco.setComissao_perc(soapObject.getProperty("comissao_perc").toString());
            tabelaPreco.setVerba_perc(soapObject.getProperty("verba_perc").toString());
            tabelaPreco.setFaixa_valor_de(soapObject.getProperty("faixa_valor_de").toString());
            tabelaPreco.setFaixa_valor_a(soapObject.getProperty("faixa_valor_a").toString());
            tabelaPreco.setUsuario_id(soapObject.getProperty("usuario_id").toString());
            tabelaPreco.setUsuario_nome(soapObject.getProperty("usuario_nome").toString());
            tabelaPreco.setUsuario_data(soapObject.getProperty("usuario_data").toString());
            tabelaPreco.setDesconto_verba_max(soapObject.getProperty("desconto_verba_max").toString());
            tabelaPreco.setId_grupo_vendedores(soapObject.getProperty("id_grupo_vendedores").toString());
            tabelaPreco.setUtiliza_verba(soapObject.getProperty("utiliza_verba").toString());
            tabelaPreco.setFaixa_valor_bruto_de(soapObject.getProperty("faixa_valor_bruto_de").toString());
            tabelaPreco.setFaixa_valor_bruto_a(soapObject.getProperty("faixa_valor_bruto_a").toString());

            listaTabelaPreco[i] = tabelaPreco;
        }
        return listaTabelaPreco;
    }

    public TabelaPrecoItem[] sincronizaTabelaPrecoItem(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaTabelaPrecoItem");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaTabelaPrecoItem", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        TabelaPrecoItem[] listaTabelaPrecoItem = new TabelaPrecoItem[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();

            tabelaPrecoItem.setId_item(soapObject.getProperty("id_item").toString());
            tabelaPrecoItem.setId_tabela(soapObject.getProperty("id_tabela").toString());
            tabelaPrecoItem.setPerc_desc_inicial(soapObject.getProperty("perc_desc_inicial").toString());
            tabelaPrecoItem.setPerc_desc_final(soapObject.getProperty("perc_desc_final").toString());
            tabelaPrecoItem.setPerc_com_interno(soapObject.getProperty("perc_com_interno").toString());
            tabelaPrecoItem.setPerc_com_externo(soapObject.getProperty("perc_com_externo").toString());
            tabelaPrecoItem.setPerc_com_exportacao(soapObject.getProperty("perc_com_exportacao").toString());
            tabelaPrecoItem.setPontos_premiacao(soapObject.getProperty("pontos_premiacao").toString());
            tabelaPrecoItem.setCor_painel(soapObject.getProperty("cor_painel").toString());
            tabelaPrecoItem.setCor_fonte(soapObject.getProperty("cor_fonte").toString());
            tabelaPrecoItem.setVerba_perc(soapObject.getProperty("verba_perc").toString());
            tabelaPrecoItem.setUtiliza_verba(soapObject.getProperty("utiliza_verba").toString());
            tabelaPrecoItem.setDesconto_verba_max(soapObject.getProperty("desconto_verba_max").toString());
            tabelaPrecoItem.setId_usuario(soapObject.getProperty("id_usuario").toString());
            tabelaPrecoItem.setUsuario(soapObject.getProperty("usuario").toString());
            tabelaPrecoItem.setUsuario_data(soapObject.getProperty("usuario_data").toString());
            tabelaPrecoItem.setCor_web(soapObject.getProperty("cor_web").toString());

            listaTabelaPrecoItem[i] = tabelaPrecoItem;
        }
        return listaTabelaPrecoItem;
    }

    public CondicoesPagamento[] sincronizaCondicoesPagamento(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaCondicoesPagamento");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaCondicoesPagamento", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        CondicoesPagamento[] listaCondicoesPagamento = new CondicoesPagamento[response.getPropertyCount()];

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            CondicoesPagamento condicoesPagamento = new CondicoesPagamento();

            condicoesPagamento.setAtivo(soapObject.getProperty("ativo").toString());
            condicoesPagamento.setId_condicao(soapObject.getProperty("id_condicao").toString());
            condicoesPagamento.setNome_condicao(soapObject.getProperty("nome_condicao").toString());
            condicoesPagamento.setNumero_parcelas(soapObject.getProperty("numero_parcelas").toString());
            condicoesPagamento.setIntervalo_dias(soapObject.getProperty("intervalo_dias").toString());
            condicoesPagamento.setTipo_condicao(soapObject.getProperty("tipo_condicao").toString());
            condicoesPagamento.setNfe_tipo_financeiro(soapObject.getProperty("nfe_tipo_financeiro").toString());
            condicoesPagamento.setNfe_mostrar_parcelas(soapObject.getProperty("nfe_mostrar_parcelas").toString());
            condicoesPagamento.setUsuario_id(soapObject.getProperty("usuario_id").toString());
            condicoesPagamento.setUsuario_nome(soapObject.getProperty("usuario_nome").toString());
            condicoesPagamento.setUsuario_data(soapObject.getProperty("usuario_data").toString());
            condicoesPagamento.setPublicar_na_web(soapObject.getProperty("publicar_na_web").toString());

            listaCondicoesPagamento[i] = condicoesPagamento;
        }
        return listaCondicoesPagamento;
    }

    public VendedorBonusResumo[] sincronizaVendedorBonusResumo(String SQL) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaVendedorBonusResumo");
        soap.addProperty("SQL", SQL);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaVendedorBonusResumo", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        VendedorBonusResumo[] listaVendedorBonusResumo = new VendedorBonusResumo[response.getPropertyCount()];


        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);


            VendedorBonusResumo vendedorBonusResumo = new VendedorBonusResumo();

            vendedorBonusResumo.setId_vendedor(soapObject.getProperty("id_vendedor").toString());
            vendedorBonusResumo.setId_empresa(soapObject.getProperty("id_empresa").toString());
            vendedorBonusResumo.setValor_credito(soapObject.getProperty("valor_credito").toString());
            vendedorBonusResumo.setValor_debito(soapObject.getProperty("valor_debito").toString());
            vendedorBonusResumo.setValor_bonus_cancelados(soapObject.getProperty("valor_bonus_cancelados").toString());
            vendedorBonusResumo.setValor_saldo(soapObject.getProperty("valor_saldo").toString());
            vendedorBonusResumo.setData_ultima_atualizacao(soapObject.getProperty("data_ultima_atualizacao").toString());

            listaVendedorBonusResumo[i] = vendedorBonusResumo;
        }

        return listaVendedorBonusResumo;
    }

    public String sincronizaWebPedido(WebPedido webPedido) throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "sincronizaWebPedido");

        soap.addProperty("id_web_pedido", webPedido.getId_web_pedido_servidor());
        soap.addProperty("id_empresa", webPedido.getId_empresa());
        soap.addProperty("id_cadastro", webPedido.getCadastro().getId_cadastro());
        soap.addProperty("id_vendedor", webPedido.getId_vendedor());
        soap.addProperty("id_condicao_pagamento", webPedido.getId_condicao_pagamento());
        soap.addProperty("id_operacao", webPedido.getId_operacao());
        soap.addProperty("id_tabela", webPedido.getId_tabela());
        soap.addProperty("nome_extenso", webPedido.getCadastro().getNome_cadastro());
        soap.addProperty("data_emissao", webPedido.getData_emissao());
        soap.addProperty("valor_produtos", webPedido.getValor_produtos());
        soap.addProperty("valor_desconto", webPedido.getValor_desconto());
        soap.addProperty("valor_desconto_add", webPedido.getValor_desconto_add());
        soap.addProperty("desconto_per", webPedido.getDesconto_per());
        soap.addProperty("desconto_per_add", webPedido.getDesconto_per_add());
        soap.addProperty("valor_total", webPedido.getValor_total());
        soap.addProperty("excluido", webPedido.getExcluido());
        soap.addProperty("excluido_usuario_id", webPedido.getExcluido_usuario_id());
        soap.addProperty("excluido_usuario_nome", webPedido.getExcluido_usuario_nome());
        soap.addProperty("excluido_usuario_data", webPedido.getExcluido_usuario_data());
        soap.addProperty("justificativa_exclusao", webPedido.getJustificativa_exclusao());
        soap.addProperty("usuario_lancamento_id", webPedido.getUsuario_lancamento_id());
        soap.addProperty("usuario_lancamento_nome", webPedido.getUsuario_lancamento_nome());
        soap.addProperty("usuario_lancamento_data", webPedido.getUsuario_lancamento_data());
        soap.addProperty("observacoes", webPedido.getObservacoes());
        soap.addProperty("status", webPedido.getStatus());
        soap.addProperty("id_pedido_venda", webPedido.getId_pedido_venda());
        soap.addProperty("id_nota_fiscal", webPedido.getId_nota_fiscal());
        soap.addProperty("id_tabela_preco_faixa", webPedido.getId_tabela_preco_faixa());
        soap.addProperty("pontos_total", webPedido.getPontos_total());
        soap.addProperty("pontos_coeficiente", webPedido.getPontos_coeficiente());
        soap.addProperty("pontos_cor", webPedido.getPontos_cor());
        soap.addProperty("comissao_percentual", webPedido.getComissao_percentual());
        soap.addProperty("comissao_valor", webPedido.getComissao_valor());
        soap.addProperty("id_faixa_final", webPedido.getId_faixa_final());
        soap.addProperty("valor_bonus_credor", webPedido.getValor_bonus_credor());
        soap.addProperty("perc_bonus_credor", webPedido.getPerc_bonus_credor());
        soap.addProperty("origem", webPedido.getOrigem());
        soap.addProperty("data_prev_entrega", webPedido.getData_prev_entrega());

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);
        httpTrans.call("sincronizaWebPedido", envelope);

        Object resultado = envelope.getResponse();

        return String.valueOf(resultado);
    }

    public String sincronizaWebPedidoItens(WebPedidoItens webPedidoItens) throws IOException, XmlPullParserException {
        soap = new SoapObject(pacote, "sincronizaWebPedidoItens");

        soap.addProperty("id_web_item", webPedidoItens.getId_web_item_servidor());
        soap.addProperty("id_pedido", webPedidoItens.getId_pedido());
        soap.addProperty("id_produto", webPedidoItens.getId_produto());
        soap.addProperty("id_empresa", webPedidoItens.getId_empresa());
        soap.addProperty("quantidade", webPedidoItens.getQuantidade());
        soap.addProperty("valor_unitario", webPedidoItens.getValor_unitario());
        soap.addProperty("valor_bruto", webPedidoItens.getValor_bruto());
        soap.addProperty("valor_desconto_per", webPedidoItens.getValor_desconto_per());
        soap.addProperty("valor_desconto_real", webPedidoItens.getValor_desconto_real());
        soap.addProperty("valor_desconto_per_add", webPedidoItens.getValor_desconto_per_add());
        soap.addProperty("valor_desconto_real_add", webPedidoItens.getValor_desconto_real_add());
        soap.addProperty("valor_total", webPedidoItens.getValor_total());
        soap.addProperty("data_movimentacao", webPedidoItens.getData_movimentacao());
        soap.addProperty("usuario_lancamento_id", webPedidoItens.getUsuario_lancamento_id());
        soap.addProperty("usuario_lancamento_data", webPedidoItens.getUsuario_lancamento_data());
//        soap.addProperty("id_tabela_preco_faixa", webPedidoItens.getTabela_preco_faixa().getId_item());
        soap.addProperty("id_item_desconto", webPedidoItens.getId_item_desconto());
        soap.addProperty("pontos_unitario", webPedidoItens.getPontos_unitario());
        soap.addProperty("pontos_total", webPedidoItens.getPontos_total());
        soap.addProperty("pontos_coeficiente", webPedidoItens.getPontos_coeficiente());
        soap.addProperty("pontos_cor", webPedidoItens.getPontos_cor());
        soap.addProperty("comissao_percentual", webPedidoItens.getComissao_percentual());
        soap.addProperty("comissao_valor", webPedidoItens.getComissao_valor());
        soap.addProperty("valor_bonus_credor", webPedidoItens.getValor_bonus_credor());
        soap.addProperty("perc_bonus_credor", webPedidoItens.getPerc_bonus_credor());
        soap.addProperty("id_tabela_preco", webPedidoItens.getId_tabela_preco());
        soap.addProperty("valor_desconto_per_orig", webPedidoItens.getValor_desconto_per_orig());
        soap.addProperty("valor_desconto_real_orig", webPedidoItens.getValor_desconto_real_orig());
        soap.addProperty("valor_desconto_per_add_orig", webPedidoItens.getValor_desconto_per_add_orig());
        soap.addProperty("valor_desconto_real_add_orig", webPedidoItens.getValor_desconto_real_add_orig());
        soap.addProperty("id_tabela_preco_faixa_orig", webPedidoItens.getId_tabela_preco_faixa_orig());
        soap.addProperty("valor_total_orig", webPedidoItens.getValor_total_orig());
        soap.addProperty("pontos_unitario_orig", webPedidoItens.getPontos_unitario_orig());
        soap.addProperty("pontos_coeficiente_orig", webPedidoItens.getPontos_coeficiente_orig());
        soap.addProperty("comissao_percentual_orig", webPedidoItens.getComissao_percentual_orig());
        soap.addProperty("valor_bonus_credor_orig", webPedidoItens.getValor_bonus_credor_orig());
        soap.addProperty("perc_bonus_credor_orig", webPedidoItens.getPerc_bonus_credor_orig());
        soap.addProperty("comissao_valor_orig", webPedidoItens.getComissao_valor_orig());
        soap.addProperty("pontos_total_orig", webPedidoItens.getPontos_total_orig());
        soap.addProperty("pontos_cor_orig", webPedidoItens.getPontos_cor_orig());

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);
        httpTrans.call("sincronizaWebPedidoItens", envelope);

        Object resultado = envelope.getResponse();

        return String.valueOf(resultado);
    }

    public List<HistoricoFinanceiroPendente> sincronizaHistoricoFinanceiroPendente(int idCliente) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaHistoricoFinanceiroPendente");
        soap.addProperty("idCliente", idCliente);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaHistoricoFinanceiroPendente", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        List<HistoricoFinanceiroPendente> listaPendente = new ArrayList<>();

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            HistoricoFinanceiroPendente pendente = new HistoricoFinanceiroPendente();

            pendente.setDocumento(soapObject.getProperty("documento").toString());
            pendente.setParcela(soapObject.getProperty("parcela").toString());
            pendente.setEspecie(soapObject.getProperty("especie").toString());
            pendente.setNome_conta(soapObject.getProperty("nome_conta").toString());
            pendente.setData_emissao(soapObject.getProperty("data_emissao").toString());
            pendente.setData_vencimento(soapObject.getProperty("data_vencimento").toString());
            pendente.setValor_total(soapObject.getProperty("valor_total").toString());
            pendente.setCobranca_descricao_status(soapObject.getProperty("cobranca_descricao_status").toString());
            pendente.setHistorico(soapObject.getProperty("historico").toString());
            pendente.setDias_atrazo(soapObject.getProperty("dias_atrazo").toString());

            listaPendente.add(pendente);
        }
        return listaPendente;
    }

    public List<HistoricoFinanceiroQuitado> sincronizaHistoricoFinanceiroQuitado(int idCliente) throws IOException, XmlPullParserException, RuntimeException {
        soap = new SoapObject(pacote, "sincronizaHistoricoFinanceiroQuitado");
        soap.addProperty("idCliente", idCliente);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soap);

        httpTrans = new HttpTransportSE(URL);

        httpTrans.call("sincronizaHistoricoFinanceiroQuitado", envelope);

        SoapObject response = (SoapObject) envelope.bodyIn;

        List<HistoricoFinanceiroQuitado> listaQuitado = new ArrayList<>();

        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);

            HistoricoFinanceiroQuitado quitado = new HistoricoFinanceiroQuitado();

            quitado.setDocumento(soapObject.getProperty("documento").toString());
            quitado.setParcela(soapObject.getProperty("parcela").toString());
            quitado.setEspecie(soapObject.getProperty("especie").toString());
            quitado.setNome_conta(soapObject.getProperty("nome_conta").toString());
            quitado.setData_emissao(soapObject.getProperty("data_emissao").toString());
            quitado.setData_vencimento(soapObject.getProperty("data_vencimento").toString());
            quitado.setData_baixa(soapObject.getProperty("data_baixa").toString());
            quitado.setValor_total(soapObject.getProperty("valor_total").toString());
            quitado.setCobranca_descricao_status(soapObject.getProperty("cobranca_descricao_status").toString());
            quitado.setHistorico(soapObject.getProperty("historico").toString());
            quitado.setPontualidade(soapObject.getProperty("pontualidade").toString());
            quitado.setPontualidade_status(soapObject.getProperty("pontualidade_status").toString());

            listaQuitado.add(quitado);
        }
        return listaQuitado;
    }
}
