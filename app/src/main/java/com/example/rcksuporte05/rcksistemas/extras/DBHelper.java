package com.example.rcksuporte05.rcksistemas.extras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rcksuporte05.rcksistemas.classes.Cliente;
import com.example.rcksuporte05.rcksistemas.classes.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.classes.Operacao;
import com.example.rcksuporte05.rcksistemas.classes.Produto;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPreco;
import com.example.rcksuporte05.rcksistemas.classes.TabelaPrecoItem;
import com.example.rcksuporte05.rcksistemas.classes.Usuario;
import com.example.rcksuporte05.rcksistemas.classes.VendedorBonusResumo;
import com.example.rcksuporte05.rcksistemas.classes.WebPedido;
import com.example.rcksuporte05.rcksistemas.classes.WebPedidoItens;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private final static String NomeBanco = "Banco.db";

    public DBHelper(Context context) {
        super(context, NomeBanco, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_USUARIO " +
                "(ID_USUARIO INTEGER PRIMARY KEY," +
                " ATIVO VARCHAR(1)," +
                " NOME_USUARIO VARCHAR(150)," +
                " LOGIN VARCHAR(100)," +
                " SENHA VARCHAR(100)," +
                " SENHA_CONFIRMA VARCHAR(100)," +
                " DATA_CADASTRO TIMESTAMP," +
                " USUARIO_CADATRO VARCHAR(20)," +
                " DATA_ALTERADO TIMESTAMP," +
                " USUARIO_ALTEROU VARCHAR(20)," +
                " APARECE_CAD_USUARIO VARCHAR(1)," +
                " CLIENTE_LISTA_TODOS VARCHAR(1)," +
                " CLIENTE_LISTA_SETOR VARCHAR(1)," +
                " CLIENTE_LISTA_REPRESENTANTE VARCHAR(1)," +
                " PEDIDO_LISTA_TODOS VARCHAR(1)," +
                " PEDIDO_LISTA_SETOR VARCHAR(1)," +
                " PEDIDO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " MENSAGEM_LISTA_FINANCEIRO VARCHAR(1)," +
                " MENSAGEM_LISTA_TODOS VARCHAR(1)," +
                " MENSAGEM_LISTA_SETOR VARCHAR(1)," +
                " MENSAGEM_LISTA_REPRESENTANTE VARCHAR(1)," +
                " ORCAMENTO_LISTA_TODOS VARCHAR(1)," +
                " ORCAMENTO_LISTA_SETOR VARCHAR(1)," +
                " ORCAMENTO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " USUARIO_LISTA_TODOS VARCHAR(1)," +
                " USUARIO_LISTA_SETOR VARCHAR(1)," +
                " USUARIO_LISTA_REPRESENTANTE VARCHAR(1)," +
                " EXCLUIDO VARCHAR(1)," +
                " ID_SETOR INTEGER," +
                " ID_QUANDO_VENDEDOR INTEGER," +
                " APARELHO_ID VARCHAR(20)," +
                " ID_EMPRESA_MULTI_DEVICE INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_LOGIN " +
                "(ID_LOGIN INTEGER PRIMARY KEY, " +
                "LOGIN VARCHAR(100), " +
                "SENHA VARCHAR(100), " +
                "LOGADO VARCHAR(1), " +
                "TOKEN VARCHAR(60), " +
                "APARELHO_ID VARCHAR(20));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO " +
                "(ATIVO VARCHAR(1) DEFAULT 'S'  NOT NULL ," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_CADASTRO INTEGER PRIMARY KEY," +
                " PESSOA_F_J VARCHAR(1)," +
                " DATA_ANIVERSARIO DATE," +
                " NOME_CADASTRO VARCHAR(60)," +
                " NOME_FANTASIA VARCHAR(60)," +
                " CPF_CNPJ VARCHAR(14)," +
                " INSCRI_ESTADUAL VARCHAR(20)," +
                " INSCRI_MUNICIPAL VARCHAR(20)," +
                " ENDERECO VARCHAR(60)," +
                " ENDERECO_BAIRRO VARCHAR(60)," +
                " ENDERECO_NUMERO VARCHAR(20)," +
                " ENDERECO_COMPLEMENTO VARCHAR(20)," +
                " ENDERECO_UF CHAR(2) NOT NULL," +
                " ENDERECO_ID_MUNICIPIO VARCHAR(50)," +
                " ENDERECO_CEP VARCHAR(8)," +
                " USUARIO_ID INTEGER," +
                " USUARIO_NOME VARCHAR(60)," +
                " USUARIO_DATA TIMESTAMP," +
                " F_CLIENTE VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_FORNECEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_FUNCIONARIO VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_VENDEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " F_TRANSPORTADOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                " DATA_ULTIMA_COMPRA DATE," +
                " NOME_VENDEDOR VARCHAR(60)," +
                " F_ID_CLIENTE INTEGER," +
                " ID_ENTIDADE INTEGER NOT NULL," +
                " F_ID_FORNECEDOR INTEGER," +
                " F_ID_VENDEDOR SMALLINT," +
                " F_ID_TRANSPORTADOR INTEGER," +
                " TELEFONE_PRINCIPAL VARCHAR(20)," +
                " EMAIL_PRINCIPAL VARCHAR(100)," +
                " NOME_PAIS VARCHAR(60) ," +
                " F_ID_FUNCIONARIO INTEGER," +
                " AVISAR_COM_DIAS INTEGER DEFAULT 0 ," +
                " OBSERVACOES BLOB," +
                " PADRAO_ID_C_CUSTO INTEGER," +
                " PADRAO_ID_C_GERENCIADORA INTEGER," +
                " PADRAO_ID_C_ANALITICA INTEGER," +
                " COB_ENDERECO VARCHAR(60)," +
                " COB_ENDERECO_BAIRRO VARCHAR(60)," +
                " COB_ENDERECO_NUMERO VARCHAR(20)," +
                " COB_ENDERECO_COMPLEMENTO VARCHAR(20)," +
                " COB_ENDERECO_UF VARCHAR(2)," +
                " COB_ENDERECO_ID_MUNICIPIO VARCHAR(60)," +
                " COB_ENDERECO_CEP VARCHAR(8)," +
                " NOME_PAIS_COB ," +
                " LIMITE_CREDITO DECIMAL(12, 2)," +
                " LIMITE_DISPONIVEL DECIMAL(12, 2)," +
                " PESSOA_CONTATO_FINANCEIRO VARCHAR(80)," +
                " EMAIL_FINANCEIRO VARCHAR(80)," +
                " OBSERVACOES_FATURAMENTO VARCHAR(300)," +
                " OBSERVACOES_FINANCEIRO VARCHAR(300)," +
                " TELEFONE_DOIS VARCHAR(20)," +
                " TELEFONE_TRES VARCHAR(20)," +
                " PESSOA_CONTATO_PRINCIPAL VARCHAR(80)," +
                " IND_DA_IE_DESTINATARIO INTEGER," +
                " COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                " ID_SETOR INTEGER," +
                " NFE_EMAIL_ENVIAR VARCHAR(1)," +
                " NFE_EMAIL_UM VARCHAR(60)," +
                " NFE_EMAIL_DOIS VARCHAR(60)," +
                " NFE_EMAIL_TRES VARCHAR(60)," +
                " NFE_EMAIL_QUATRO VARCHAR(60)," +
                " NFE_EMAIL_CINCO VARCHAR(60)," +
                " ID_GRUPO_VENDEDOR INTEGER," +
                " VENDEDOR_USA_PORTAL VARCHAR(1)," +
                " VENDEDOR_ID_USER_PORTAL INTEGER," +
                " F_TARIFA VARCHAR(1)," +
                " F_ID_TARIFA INTEGER," +
                " F_PRODUTOR VARCHAR(1)," +
                " RG_NUMERO VARCHAR(30)," +
                " RG_SSP VARCHAR(10)," +
                " CONTA_CONTABIL VARCHAR(15)," +
                " MOTORISTA VARCHAR(1)," +
                " F_ID_MOTORISTA INTEGER," +
                " HABILITACAO_NUMERO VARCHAR(20)," +
                " HABILITACAO_CATEGORIA VARCHAR(10)," +
                " HABILITACAO_VENCIMENTO DATE," +
                " MOT_ID_TRANSPORTADORA INTEGER," +
                " LOCAL_CADASTRO VARCHAR(20)," +
                " ID_EMPRESA_MULTIDEVICE INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_PRODUTO (ATIVO VARCHAR(1) DEFAULT 'S'  NOT NULL," +
                " ID_PRODUTO VARCHAR(20) PRIMARY KEY," +
                " NOME_PRODUTO VARCHAR(60) NOT NULL," +
                " UNIDADE VARCHAR(4) NOT NULL," +
                " TIPO_CADASTRO INTEGER NOT NULL," +
                " ID_ENTIDADE INTEGER DEFAULT 40  NOT NULL," +
                " NCM VARCHAR(8)," +
                " ID_GRUPO INTEGER," +
                " ID_SUB_GRUPO INTEGER," +
                " PESO_BRUTO DECIMAL(12," +
                " 4) DEFAULT 0 ," +
                " PESO_LIQUIDO DECIMAL(12," +
                " 4) DEFAULT 0 ," +
                " CODIGO_EM_BARRAS VARCHAR(30)," +
                " MOVIMENTA_ESTOQUE CHAR(1) NOT NULL," +
                " NOME_DA_MARCA VARCHAR(60)," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_ORIGEM INTEGER NOT NULL," +
                " CUSTO_PRODUTO DECIMAL(12, 4)," +
                " CUSTO_PER_IPI DECIMAL(12, 4)," +
                " CUSTO_IPI DECIMAL(12, 4)," +
                " CUSTO_PER_FRETE DECIMAL(12, 4)," +
                " CUSTO_FRETE DECIMAL(12, 4)," +
                " CUSTO_PER_ICMS DECIMAL(12, 4)," +
                " CUSTO_ICMS DECIMAL(12, 4)," +
                " CUSTO_PER_FIN DECIMAL(12, 4)," +
                " CUSTO_FIN DECIMAL(12, 4)," +
                " CUSTO_PER_SUBST DECIMAL(12, 4)," +
                " CUSTO_SUBT DECIMAL(12, 4)," +
                " CUSTO_PER_OUTROS DECIMAL(12, 4)," +
                " CUSTO_OUTROS DECIMAL(12, 4)," +
                " VALOR_CUSTO DECIMAL(12, 4)," +
                " EXCLUIDO VARCHAR(1)," +
                " EXCLUIDO_POR VARCHAR(50)," +
                " EXCLUIDO_POR_DATA TIMESTAMP," +
                " EXCLUIDO_CODIGO_NOVO VARCHAR(20)," +
                " AJUSTE_PRECO_DATA TIMESTAMP," +
                " AJUSTE_PRECO_NFE VARCHAR(10)," +
                " AJUSTE_PRECO_USUARIO VARCHAR(30)," +
                " TOTAL_CUSTO DECIMAL(12, 4)," +
                " TOTAL_CREDITO DECIMAL(12, 4)," +
                " VALOR_CUSTO_ESTOQUE DECIMAL(12, 4)," +
                " CUSTO_DATA_INICIAL DATE," +
                " CUSTO_VALOR_INICIAL DECIMAL(12, 4)," +
                " PRODUTO_VENDA VARCHAR(1)," +
                " PRODUTO_INSUMO VARCHAR(1)," +
                " PRODUTO_CONSUMO VARCHAR(1)," +
                " PRODUTO_PRODUCAO VARCHAR(1)," +
                " VENDA_PERC_COMISSAO DECIMAL(12, 6)," +
                " VENDA_PRECO DECIMAL(12, 6)," +
                " VENDA_PERC_COMISSAO_DOIS DECIMAL(12, 4)," +
                " DESCRICAO VARCHAR(20));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_TABELA_PRECO_CAB (ID_TABELA INTEGER PRIMARY KEY," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ATIVO VARCHAR(1) NOT NULL," +
                " ID_TIPO_TABELA INTEGER," +
                " NOME_TABELA VARCHAR(60)," +
                " DATA_INICIO DATE," +
                " DATA_FIM DATE," +
                " DESCONTO_DE_PERC DECIMAL(12, 4)," +
                " DESCONTO_A_PERC DECIMAL(12, 4)," +
                " COMISSAO_PERC DECIMAL(12, 4)," +
                " VERBA_PERC DECIMAL(12, 4)," +
                " FAIXA_VALOR_DE DECIMAL(12, 4)," +
                " FAIXA_VALOR_A DECIMAL(12, 4)," +
                " USUARIO_ID INTEGER NOT NULL," +
                " USUARIO_NOME VARCHAR(40)," +
                " USUARIO_DATA TIMESTAMP," +
                " DESCONTO_VERBA_MAX DECIMAL(12, 4)," +
                " ID_GRUPO_VENDEDORES INTEGER," +
                " UTILIZA_VERBA CHAR(1)," +
                " FAIXA_VALOR_BRUTO_DE DECIMAL(12, 2)," +
                " FAIXA_VALOR_BRUTO_A DECIMAL(12, 2));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_TABELA_PRECO_ITENS (ID_ITEM INTEGER PRIMARY KEY," +
                " ID_TABELA INTEGER NOT NULL," +
                " PERC_DESC_INICIAL DECIMAL(12, 4)," +
                " PERC_DESC_FINAL DECIMAL(12, 4)," +
                " PERC_COM_INTERNO DECIMAL(12, 4)," +
                " PERC_COM_EXTERNO DECIMAL(12, 4)," +
                " PERC_COM_EXPORTACAO DECIMAL(12, 4)," +
                " PONTOS_PREMIACAO DECIMAL(12, 4)," +
                " COR_PAINEL VARCHAR(15)," +
                " COR_FONTE VARCHAR(15)," +
                " VERBA_PERC DECIMAL(12, 4)," +
                " UTILIZA_VERBA CHAR(1)," +
                " DESCONTO_VERBA_MAX DECIMAL(12, 4)," +
                " ID_USUARIO INTEGER NOT NULL," +
                " USUARIO VARCHAR(30)," +
                " USUARIO_DATA TIMESTAMP," +
                " COR_WEB VARCHAR(20));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CONDICOES_PAG_CAB (ATIVO VARCHAR(1)," +
                "ID_CONDICAO INTEGER PRIMARY KEY," +
                "NOME_CONDICAO VARCHAR(70) NOT NULL," +
                "NUMERO_PARCELAS INTEGER NOT NULL," +
                "INTERVALO_DIAS INTEGER," +
                "TIPO_CONDICAO INTEGER NOT NULL," +
                "NFE_TIPO_FINANCEIRO VARCHAR(20) NOT NULL," +
                "NFE_MOSTRAR_PARCELAS VARCHAR(1) NOT NULL," +
                "USUARIO_ID INTEGER NOT NULL," +
                "USUARIO_NOME VARCHAR(40)," +
                "USUARIO_DATA TIMESTAMP," +
                "PUBLICAR_NA_WEB VARCHAR(1));");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_VENDEDOR_BONUS_RESUMO (ID_VENDEDOR INTEGER PRIMARY KEY," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " VALOR_CREDITO DECIMAL(12, 2)," +
                " VALOR_DEBITO DECIMAL(12, 2)," +
                " VALOR_BONUS_CANCELADOS DECIMAL(12, 2)," +
                " VALOR_SALDO DECIMAL(12, 2)," +
                " DATA_ULTIMA_ATUALIZACAO DATE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_PEDIDO (ID_WEB_PEDIDO INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ID_EMPRESA INTEGER NOT NULL," +
                " ID_CADASTRO INTEGER NOT NULL," +
                " ID_VENDEDOR INTEGER NOT NULL," +
                " ID_CONDICAO_PAGAMENTO INTEGER NOT NULL," +
                " ID_OPERACAO INTEGER NOT NULL," +
                " ID_TABELA INTEGER," +
                " NOME_EXTENSO VARCHAR(150)," +
                " DATA_EMISSAO DATE," +
                " VALOR_PRODUTOS DECIMAL(12, 2)," +
                " VALOR_DESCONTO DECIMAL(12, 2)," +
                " VALOR_DESCONTO_ADD DECIMAL(12, 2)," +
                " DESCONTO_PER DECIMAL(12, 2)," +
                " DESCONTO_PER_ADD DECIMAL(12, 2)," +
                " VALOR_TOTAL DECIMAL(12, 2)," +
                " EXCLUIDO VARCHAR(1) NOT NULL," +
                " EXCLUIDO_USUARIO_ID INTEGER," +
                " EXCLUIDO_USUARIO_NOME VARCHAR(40)," +
                " EXCLUIDO_USUARIO_DATA TIMESTAMP," +
                " JUSTIFICATIVA_EXCLUSAO VARCHAR(250)," +
                " USUARIO_LANCAMENTO_ID INTEGER NOT NULL," +
                " USUARIO_LANCAMENTO_NOME VARCHAR(40)," +
                " USUARIO_LANCAMENTO_DATA TIMESTAMP," +
                " OBSERVACOES VARCHAR(300)," +
                " STATUS VARCHAR(1) NOT NULL," +
                " ID_PEDIDO_VENDA INTEGER," +
                " ID_NOTA_FISCAL INTEGER," +
                " ID_TABELA_PRECO_FAIXA INTEGER," +
                " PONTOS_TOTAL DECIMAL(12, 4)," +
                " PONTOS_COEFICIENTE DECIMAL(12, 4)," +
                " PONTOS_COR VARCHAR(15)," +
                " COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                " COMISSAO_VALOR DECIMAL(12, 2)," +
                " ID_FAIXA_FINAL INTEGER," +
                " VALOR_BONUS_CREDOR DECIMAL(12, 2)," +
                " PERC_BONUS_CREDOR DECIMAL(12, 4), " +
                " FATURADO VARCHAR(1)," +
                " PEDIDO_ENVIADO VARCHAR(1) DEFAULT 'N', " +
                " ID_WEB_PEDIDO_SERVIDOR INTEGER," +
                " DATA_PREV_ENTREGA DATE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_OPERACAO_ESTOQUE (ATIVO CHAR(1) DEFAULT 'S'  NOT NULL, ID_OPERACAO INTEGER DEFAULT 0 PRIMARY KEY, NOME_OPERACAO VARCHAR(60) NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TBL_WEB_PEDIDO_ITENS (ID_WEB_ITEM INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "ID_PEDIDO INTEGER NOT NULL," +
                "ID_PRODUTO VARCHAR(20) NOT NULL," +
                "ID_EMPRESA INTEGER NOT NULL," +
                "QUANTIDADE DECIMAL(12, 8)," +
                "VALOR_UNITARIO DECIMAL(12, 8)," +
                "VALOR_BRUTO DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER_ADD DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ADD DECIMAL(12, 8)," +
                "VALOR_TOTAL DECIMAL(12, 8)," +
                "DATA_MOVIMENTACAO DATE," +
                "USUARIO_LANCAMENTO_ID INTEGER NOT NULL," +
                "USUARIO_LANCAMENTO_DATA TIMESTAMP," +
                "ID_TABELA_PRECO_FAIXA INTEGER," +
                "ID_ITEM_DESCONTO INTEGER," +
                "PONTOS_UNITARIO DECIMAL(12, 4)," +
                "PONTOS_TOTAL DECIMAL(12, 4)," +
                "PONTOS_COEFICIENTE DECIMAL(12, 6)," +
                "PONTOS_COR VARCHAR(15)," +
                "COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                "COMISSAO_VALOR DECIMAL(12, 2)," +
                "VALOR_BONUS_CREDOR DECIMAL(12, 2)," +
                "PERC_BONUS_CREDOR DECIMAL(12, 4)," +
                "ID_TABELA_PRECO INTEGER," +
                "VALOR_DESCONTO_PER_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_PER_ADD_ORIG DECIMAL(12, 8)," +
                "VALOR_DESCONTO_REAL_ADD_ORIG DECIMAL(12, 8)," +
                "ID_TABELA_PRECO_FAIXA_ORIG INTEGER," +
                "VALOR_TOTAL_ORIG DECIMAL(12, 8)," +
                "PONTOS_UNITARIO_ORIG DECIMAL(12, 4)," +
                "PONTOS_COEFICIENTE_ORIG DECIMAL(12, 6)," +
                "COMISSAO_PERCENTUAL_ORIG DECIMAL(12, 4)," +
                "VALOR_BONUS_CREDOR_ORIG DECIMAL(12, 2)," +
                "PERC_BONUS_CREDOR_ORIG DECIMAL(12, 4)," +
                "COMISSAO_VALOR_ORIG DECIMAL(12, 2)," +
                "PONTOS_TOTAL_ORIG DECIMAL(12, 4)," +
                "PONTOS_COR_ORIG VARCHAR(15)," +
                "VALOR_PRECO_PAGO DECIMAL(12, 8)," +
                "ITEM_ENVIADO VARCHAR(1) DEFAULT 'N'," +
                "ID_WEB_ITEM_SERVIDOR INTEGER);");

        System.gc();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            try {
                db.execSQL("DROP TABLE TBL_MUNICIPIOS;");
                db.execSQL("DROP TABLE TBL_PAISES;");
                db.execSQL("DROP TABLE TBL_CADASTRO;");
                db.execSQL("CREATE TABLE IF NOT EXISTS TBL_CADASTRO " +
                        "(ATIVO VARCHAR(1) DEFAULT 'S'  NOT NULL ," +
                        " ID_EMPRESA INTEGER NOT NULL," +
                        " ID_CADASTRO INTEGER PRIMARY KEY," +
                        " PESSOA_F_J VARCHAR(1)," +
                        " DATA_ANIVERSARIO DATE," +
                        " NOME_CADASTRO VARCHAR(60)," +
                        " NOME_FANTASIA VARCHAR(60)," +
                        " CPF_CNPJ VARCHAR(14)," +
                        " INSCRI_ESTADUAL VARCHAR(20)," +
                        " INSCRI_MUNICIPAL VARCHAR(20)," +
                        " ENDERECO VARCHAR(60)," +
                        " ENDERECO_BAIRRO VARCHAR(60)," +
                        " ENDERECO_NUMERO VARCHAR(20)," +
                        " ENDERECO_COMPLEMENTO VARCHAR(20)," +
                        " ENDERECO_UF CHAR(2) NOT NULL," +
                        " ENDERECO_ID_MUNICIPIO VARCHAR(50)," +
                        " ENDERECO_CEP VARCHAR(8)," +
                        " USUARIO_ID INTEGER," +
                        " USUARIO_NOME VARCHAR(60)," +
                        " USUARIO_DATA TIMESTAMP," +
                        " F_CLIENTE VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                        " F_FORNECEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                        " F_FUNCIONARIO VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                        " F_VENDEDOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                        " F_TRANSPORTADOR VARCHAR(1) DEFAULT 'N'  NOT NULL," +
                        " DATA_ULTIMA_COMPRA DATE," +
                        " NOME_VENDEDOR VARCHAR(60)," +
                        " F_ID_CLIENTE INTEGER," +
                        " ID_ENTIDADE INTEGER NOT NULL," +
                        " F_ID_FORNECEDOR INTEGER," +
                        " F_ID_VENDEDOR SMALLINT," +
                        " F_ID_TRANSPORTADOR INTEGER," +
                        " TELEFONE_PRINCIPAL VARCHAR(20)," +
                        " EMAIL_PRINCIPAL VARCHAR(100)," +
                        " NOME_PAIS VARCHAR(60) ," +
                        " F_ID_FUNCIONARIO INTEGER," +
                        " AVISAR_COM_DIAS INTEGER DEFAULT 0 ," +
                        " OBSERVACOES BLOB," +
                        " PADRAO_ID_C_CUSTO INTEGER," +
                        " PADRAO_ID_C_GERENCIADORA INTEGER," +
                        " PADRAO_ID_C_ANALITICA INTEGER," +
                        " COB_ENDERECO VARCHAR(60)," +
                        " COB_ENDERECO_BAIRRO VARCHAR(60)," +
                        " COB_ENDERECO_NUMERO VARCHAR(20)," +
                        " COB_ENDERECO_COMPLEMENTO VARCHAR(20)," +
                        " COB_ENDERECO_UF VARCHAR(2)," +
                        " COB_ENDERECO_ID_MUNICIPIO VARCHAR(60)," +
                        " COB_ENDERECO_CEP VARCHAR(8)," +
                        " NOME_PAIS_COB ," +
                        " LIMITE_CREDITO DECIMAL(12, 2)," +
                        " LIMITE_DISPONIVEL DECIMAL(12, 2)," +
                        " PESSOA_CONTATO_FINANCEIRO VARCHAR(80)," +
                        " EMAIL_FINANCEIRO VARCHAR(80)," +
                        " OBSERVACOES_FATURAMENTO VARCHAR(300)," +
                        " OBSERVACOES_FINANCEIRO VARCHAR(300)," +
                        " TELEFONE_DOIS VARCHAR(20)," +
                        " TELEFONE_TRES VARCHAR(20)," +
                        " PESSOA_CONTATO_PRINCIPAL VARCHAR(80)," +
                        " IND_DA_IE_DESTINATARIO INTEGER," +
                        " COMISSAO_PERCENTUAL DECIMAL(12, 4)," +
                        " ID_SETOR INTEGER," +
                        " NFE_EMAIL_ENVIAR VARCHAR(1)," +
                        " NFE_EMAIL_UM VARCHAR(60)," +
                        " NFE_EMAIL_DOIS VARCHAR(60)," +
                        " NFE_EMAIL_TRES VARCHAR(60)," +
                        " NFE_EMAIL_QUATRO VARCHAR(60)," +
                        " NFE_EMAIL_CINCO VARCHAR(60)," +
                        " ID_GRUPO_VENDEDOR INTEGER," +
                        " VENDEDOR_USA_PORTAL VARCHAR(1)," +
                        " VENDEDOR_ID_USER_PORTAL INTEGER," +
                        " F_TARIFA VARCHAR(1)," +
                        " F_ID_TARIFA INTEGER," +
                        " F_PRODUTOR VARCHAR(1)," +
                        " RG_NUMERO VARCHAR(30)," +
                        " RG_SSP VARCHAR(10)," +
                        " CONTA_CONTABIL VARCHAR(15)," +
                        " MOTORISTA VARCHAR(1)," +
                        " F_ID_MOTORISTA INTEGER," +
                        " HABILITACAO_NUMERO VARCHAR(20)," +
                        " HABILITACAO_CATEGORIA VARCHAR(10)," +
                        " HABILITACAO_VENCIMENTO DATE," +
                        " MOT_ID_TRANSPORTADORA INTEGER," +
                        " LOCAL_CADASTRO VARCHAR(20));");

                db.execSQL("ALTER TABLE TBL_LOGIN ADD COLUMN TOKEN VARCHAR(60)");
                db.execSQL("ALTER TABLE TBL_WEB_USUARIO ADD COLUMN ID_EMPRESA_MULTI_DEVICE INTEGER");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String pegaDataAtual() {
        String resultado = "";
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT date('now');", null);
        cursor.moveToFirst();
        resultado += cursor.getString(0);
        return resultado;
    }

    public String pegaHoraAtual() {
        String resultado = "";
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT time('localtime');", null);
        cursor.moveToFirst();
        resultado += cursor.getString(0);
        return resultado;
    }

    public String pegaDataHoraAtual() {
        String resultado = "";
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery("SELECT datetime('now', 'localtime');", null);
        cursor.moveToFirst();
        resultado += cursor.getString(0);
        return resultado;
    }

    public void inserirTBL_WEB_USUARIO(Usuario usuario) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_USUARIO", usuario.getId_usuario());
        content.put("ATIVO", usuario.getAtivo());
        content.put("NOME_USUARIO", usuario.getNome_usuario());
        content.put("LOGIN", usuario.getLogin());
        content.put("SENHA", usuario.getSenha());
        content.put("SENHA_CONFIRMA", usuario.getSenha_confirma());
        content.put("DATA_CADASTRO", usuario.getData_cadastro());
        content.put("USUARIO_CADATRO", usuario.getUsuario_cadatro());
        content.put("DATA_ALTERADO", usuario.getData_alterado());
        content.put("USUARIO_ALTEROU", usuario.getUsuario_alterou());
        content.put("APARECE_CAD_USUARIO", usuario.getAparece_cad_usuario());
        content.put("CLIENTE_LISTA_TODOS", usuario.getCliente_lista_todos());
        content.put("CLIENTE_LISTA_SETOR", usuario.getCliente_lista_setor());
        content.put("CLIENTE_LISTA_REPRESENTANTE", usuario.getCliente_lista_representante());
        content.put("PEDIDO_LISTA_TODOS", usuario.getPedido_lista_todos());
        content.put("PEDIDO_LISTA_SETOR", usuario.getPedido_lista_setor());
        content.put("PEDIDO_LISTA_REPRESENTANTE", usuario.getPedido_lista_representante());
        content.put("MENSAGEM_LISTA_FINANCEIRO", usuario.getMensagem_lista_financeiro());
        content.put("MENSAGEM_LISTA_TODOS", usuario.getMensagem_lista_todos());
        content.put("MENSAGEM_LISTA_SETOR", usuario.getMensagem_lista_setor());
        content.put("MENSAGEM_LISTA_REPRESENTANTE", usuario.getMensagem_lista_representante());
        content.put("ORCAMENTO_LISTA_TODOS", usuario.getOrcamento_lista_todos());
        content.put("ORCAMENTO_LISTA_SETOR", usuario.getOrcamento_lista_setor());
        content.put("ORCAMENTO_LISTA_REPRESENTANTE", usuario.getOrcamento_lista_representante());
        content.put("USUARIO_LISTA_TODOS", usuario.getUsuario_lista_todos());
        content.put("USUARIO_LISTA_SETOR", usuario.getUsuario_lista_setor());
        content.put("USUARIO_LISTA_REPRESENTANTE", usuario.getUsuario_lista_representante());
        content.put("EXCLUIDO", usuario.getExcluido());
        content.put("ID_SETOR", usuario.getId_setor());
        content.put("ID_QUANDO_VENDEDOR", usuario.getId_quando_vendedor());
        content.put("APARELHO_ID", usuario.getAparelho_id());
        content.put("ID_EMPRESA_MULTI_DEVICE", usuario.getIdEmpresaMultiDevice());

        db.insert("TBL_WEB_USUARIO", null, content);
        System.gc();
    }

    public void atualizarTBL_WEB_USUARIO(Usuario usuario) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_USUARIO", usuario.getId_usuario());
        content.put("ATIVO", usuario.getAtivo());
        content.put("NOME_USUARIO", usuario.getNome_usuario());
        content.put("LOGIN", usuario.getLogin());
        content.put("SENHA", usuario.getSenha());
        content.put("SENHA_CONFIRMA", usuario.getSenha_confirma());
        content.put("DATA_CADASTRO", usuario.getData_cadastro());
        content.put("USUARIO_CADATRO", usuario.getUsuario_cadatro());
        content.put("DATA_ALTERADO", usuario.getData_alterado());
        content.put("USUARIO_ALTEROU", usuario.getUsuario_alterou());
        content.put("APARECE_CAD_USUARIO", usuario.getAparece_cad_usuario());
        content.put("CLIENTE_LISTA_TODOS", usuario.getCliente_lista_todos());
        content.put("CLIENTE_LISTA_SETOR", usuario.getCliente_lista_setor());
        content.put("CLIENTE_LISTA_REPRESENTANTE", usuario.getCliente_lista_representante());
        content.put("PEDIDO_LISTA_TODOS", usuario.getPedido_lista_todos());
        content.put("PEDIDO_LISTA_SETOR", usuario.getPedido_lista_setor());
        content.put("PEDIDO_LISTA_REPRESENTANTE", usuario.getPedido_lista_representante());
        content.put("MENSAGEM_LISTA_FINANCEIRO", usuario.getMensagem_lista_financeiro());
        content.put("MENSAGEM_LISTA_TODOS", usuario.getMensagem_lista_todos());
        content.put("MENSAGEM_LISTA_SETOR", usuario.getMensagem_lista_setor());
        content.put("MENSAGEM_LISTA_REPRESENTANTE", usuario.getMensagem_lista_representante());
        content.put("ORCAMENTO_LISTA_TODOS", usuario.getOrcamento_lista_todos());
        content.put("ORCAMENTO_LISTA_SETOR", usuario.getOrcamento_lista_setor());
        content.put("ORCAMENTO_LISTA_REPRESENTANTE", usuario.getOrcamento_lista_representante());
        content.put("USUARIO_LISTA_TODOS", usuario.getUsuario_lista_todos());
        content.put("USUARIO_LISTA_SETOR", usuario.getUsuario_lista_setor());
        content.put("USUARIO_LISTA_REPRESENTANTE", usuario.getUsuario_lista_representante());
        content.put("EXCLUIDO", usuario.getExcluido());
        content.put("ID_SETOR", usuario.getId_setor());
        content.put("ID_QUANDO_VENDEDOR", usuario.getId_quando_vendedor());
        content.put("APARELHO_ID", usuario.getAparelho_id());
        content.put("ID_EMPRESA_MULTI_DEVICE", usuario.getIdEmpresaMultiDevice());
        db.update("TBL_WEB_USUARIO", content, "ID_USUARIO = " + usuario.getId_usuario(), null);
        System.gc();
    }

    public void inserirTBL_CADASTRO(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", cliente.getAtivo());
        content.put("ID_EMPRESA", cliente.getId_empresa());
        content.put("ID_CADASTRO", cliente.getId_cadastro());
        content.put("PESSOA_F_J", cliente.getPessoa_f_j());
        content.put("DATA_ANIVERSARIO", cliente.getData_aniversario());
        content.put("NOME_CADASTRO", cliente.getNome_cadastro());
        content.put("NOME_FANTASIA", cliente.getNome_fantasia());
        content.put("CPF_CNPJ", cliente.getCpf_cnpj());
        content.put("INSCRI_ESTADUAL", cliente.getInscri_estadual());
        content.put("INSCRI_MUNICIPAL", cliente.getInscri_municipal());
        content.put("ENDERECO", cliente.getEndereco());
        content.put("ENDERECO_BAIRRO", cliente.getEndereco_bairro());
        content.put("ENDERECO_NUMERO", cliente.getEndereco_numero());
        content.put("ENDERECO_COMPLEMENTO", cliente.getEndereco_complemento());
        content.put("ENDERECO_UF", cliente.getEndereco_uf());
        content.put("ENDERECO_ID_MUNICIPIO", cliente.getEndereco_id_municipio());
        content.put("ENDERECO_CEP", cliente.getEndereco_cep());
        content.put("USUARIO_ID", cliente.getUsuario_id());
        content.put("USUARIO_NOME", cliente.getUsuario_nome());
        content.put("USUARIO_DATA", cliente.getUsuario_data());
        content.put("F_CLIENTE", cliente.getF_cliente());
        content.put("F_FORNECEDOR", cliente.getF_fornecedor());
        content.put("F_FUNCIONARIO", cliente.getF_funcionario());
        content.put("F_VENDEDOR", cliente.getF_vendedor());
        content.put("F_TRANSPORTADOR", cliente.getF_transportador());
        content.put("DATA_ULTIMA_COMPRA", cliente.getData_ultima_compra());
        content.put("NOME_VENDEDOR", cliente.getNome_vendedor());
        content.put("F_ID_CLIENTE", cliente.getF_id_cliente());
        content.put("ID_ENTIDADE", cliente.getId_entidade());
        content.put("F_ID_FORNECEDOR", cliente.getF_id_fornecedor());
        content.put("F_ID_VENDEDOR", cliente.getF_id_vendedor());
        content.put("F_ID_TRANSPORTADOR", cliente.getF_id_transportador());
        content.put("TELEFONE_PRINCIPAL", cliente.getTelefone_principal());
        content.put("EMAIL_PRINCIPAL", cliente.getEmail_principal());
        content.put("NOME_PAIS", cliente.getNome_pais());
        content.put("F_ID_FUNCIONARIO", cliente.getF_id_funcionario());
        content.put("AVISAR_COM_DIAS", cliente.getAvisar_com_dias());
        content.put("OBSERVACOES", cliente.getObservacoes());
        content.put("PADRAO_ID_C_CUSTO", cliente.getPadrao_id_c_custo());
        content.put("PADRAO_ID_C_GERENCIADORA", cliente.getPadrao_id_c_gerenciadora());
        content.put("PADRAO_ID_C_ANALITICA", cliente.getPadrao_id_c_analitica());
        content.put("COB_ENDERECO", cliente.getCob_endereco());
        content.put("COB_ENDERECO_BAIRRO", cliente.getCob_endereco_bairro());
        content.put("COB_ENDERECO_NUMERO", cliente.getCob_endereco_numero());
        content.put("COB_ENDERECO_COMPLEMENTO", cliente.getCob_endereco_complemento());
        content.put("COB_ENDERECO_UF", cliente.getCob_endereco_uf());
        content.put("COB_ENDERECO_ID_MUNICIPIO", cliente.getCob_endereco_id_municipio());
        content.put("COB_ENDERECO_CEP", cliente.getCob_endereco_cep());
        content.put("NOME_PAIS_COB", cliente.getNome_pais_cob());
        content.put("LIMITE_CREDITO", cliente.getLimite_credito());
        content.put("LIMITE_DISPONIVEL", cliente.getLimite_disponivel());
        content.put("PESSOA_CONTATO_FINANCEIRO", cliente.getPessoa_contato_financeiro());
        content.put("EMAIL_FINANCEIRO", cliente.getEmail_financeiro());
        content.put("OBSERVACOES_FATURAMENTO", cliente.getObservacoes_faturamento());
        content.put("OBSERVACOES_FINANCEIRO", cliente.getObservacoes_financeiro());
        content.put("TELEFONE_DOIS", cliente.getTelefone_dois());
        content.put("TELEFONE_TRES", cliente.getTelefone_tres());
        content.put("PESSOA_CONTATO_PRINCIPAL", cliente.getPessoa_contato_principal());
        content.put("IND_DA_IE_DESTINATARIO", cliente.getInd_da_ie_destinatario());
        content.put("COMISSAO_PERCENTUAL", cliente.getComissao_percentual());
        content.put("ID_SETOR", cliente.getId_setor());
        content.put("NFE_EMAIL_ENVIAR", cliente.getNfe_email_enviar());
        content.put("NFE_EMAIL_UM", cliente.getNfe_email_um());
        content.put("NFE_EMAIL_DOIS", cliente.getNfe_email_dois());
        content.put("NFE_EMAIL_TRES", cliente.getNfe_email_tres());
        content.put("NFE_EMAIL_QUATRO", cliente.getNfe_email_quatro());
        content.put("NFE_EMAIL_CINCO", cliente.getNfe_email_cinco());
        content.put("ID_GRUPO_VENDEDOR", cliente.getId_grupo_vendedor());
        content.put("VENDEDOR_USA_PORTAL", cliente.getVendedor_usa_portal());
        content.put("VENDEDOR_ID_USER_PORTAL", cliente.getVendedor_id_user_portal());
        content.put("F_TARIFA", cliente.getF_tarifa());
        content.put("F_ID_TARIFA", cliente.getF_id_tarifa());
        content.put("F_PRODUTOR", cliente.getF_produtor());
        content.put("RG_NUMERO", cliente.getRg_numero());
        content.put("RG_SSP", cliente.getRg_ssp());
        content.put("CONTA_CONTABIL", cliente.getConta_contabil());
        content.put("MOTORISTA", cliente.getMotorista());
        content.put("F_ID_MOTORISTA", cliente.getF_id_motorista());
        content.put("HABILITACAO_NUMERO", cliente.getHabilitacao_numero());
        content.put("HABILITACAO_CATEGORIA", cliente.getHabilitacao_categoria());
        content.put("HABILITACAO_VENCIMENTO", cliente.getHabilitacao_vencimento());
        content.put("MOT_ID_TRANSPORTADORA", cliente.getMot_id_transportadora());
        content.put("LOCAL_CADASTRO", cliente.getLocal_cadastro());

        db.insert("TBL_CADASTRO", null, content);
        System.gc();
    }

    public void atualizarTBL_CADASTRO(Cliente cliente) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", cliente.getAtivo());
        content.put("ID_EMPRESA", cliente.getId_empresa());
        content.put("ID_CADASTRO", cliente.getId_cadastro());
        content.put("PESSOA_F_J", cliente.getPessoa_f_j());
        content.put("DATA_ANIVERSARIO", cliente.getData_aniversario());
        content.put("NOME_CADASTRO", cliente.getNome_cadastro());
        content.put("NOME_FANTASIA", cliente.getNome_fantasia());
        content.put("CPF_CNPJ", cliente.getCpf_cnpj());
        content.put("INSCRI_ESTADUAL", cliente.getInscri_estadual());
        content.put("INSCRI_MUNICIPAL", cliente.getInscri_municipal());
        content.put("ENDERECO", cliente.getEndereco());
        content.put("ENDERECO_BAIRRO", cliente.getEndereco_bairro());
        content.put("ENDERECO_NUMERO", cliente.getEndereco_numero());
        content.put("ENDERECO_COMPLEMENTO", cliente.getEndereco_complemento());
        content.put("ENDERECO_UF", cliente.getEndereco_uf());
        content.put("ENDERECO_ID_MUNICIPIO", cliente.getEndereco_id_municipio());
        content.put("ENDERECO_CEP", cliente.getEndereco_cep());
        content.put("USUARIO_ID", cliente.getUsuario_id());
        content.put("USUARIO_NOME", cliente.getUsuario_nome());
        content.put("USUARIO_DATA", cliente.getUsuario_data());
        content.put("F_CLIENTE", cliente.getF_cliente());
        content.put("F_FORNECEDOR", cliente.getF_fornecedor());
        content.put("F_FUNCIONARIO", cliente.getF_funcionario());
        content.put("F_VENDEDOR", cliente.getF_vendedor());
        content.put("F_TRANSPORTADOR", cliente.getF_transportador());
        content.put("DATA_ULTIMA_COMPRA", cliente.getData_ultima_compra());
        content.put("NOME_VENDEDOR", cliente.getNome_vendedor());
        content.put("F_ID_CLIENTE", cliente.getF_id_cliente());
        content.put("ID_ENTIDADE", cliente.getId_entidade());
        content.put("F_ID_FORNECEDOR", cliente.getF_id_fornecedor());
        content.put("F_ID_VENDEDOR", cliente.getF_id_vendedor());
        content.put("F_ID_TRANSPORTADOR", cliente.getF_id_transportador());
        content.put("TELEFONE_PRINCIPAL", cliente.getTelefone_principal());
        content.put("EMAIL_PRINCIPAL", cliente.getEmail_principal());
        content.put("NOME_PAIS", cliente.getNome_pais());
        content.put("F_ID_FUNCIONARIO", cliente.getF_id_funcionario());
        content.put("AVISAR_COM_DIAS", cliente.getAvisar_com_dias());
        content.put("OBSERVACOES", cliente.getObservacoes());
        content.put("PADRAO_ID_C_CUSTO", cliente.getPadrao_id_c_custo());
        content.put("PADRAO_ID_C_GERENCIADORA", cliente.getPadrao_id_c_gerenciadora());
        content.put("PADRAO_ID_C_ANALITICA", cliente.getPadrao_id_c_analitica());
        content.put("COB_ENDERECO", cliente.getCob_endereco());
        content.put("COB_ENDERECO_BAIRRO", cliente.getCob_endereco_bairro());
        content.put("COB_ENDERECO_NUMERO", cliente.getCob_endereco_numero());
        content.put("COB_ENDERECO_COMPLEMENTO", cliente.getCob_endereco_complemento());
        content.put("COB_ENDERECO_UF", cliente.getCob_endereco_uf());
        content.put("COB_ENDERECO_ID_MUNICIPIO", cliente.getCob_endereco_id_municipio());
        content.put("COB_ENDERECO_CEP", cliente.getCob_endereco_cep());
        content.put("NOME_PAIS_COB", cliente.getNome_pais_cob());
        content.put("LIMITE_CREDITO", cliente.getLimite_credito());
        content.put("LIMITE_DISPONIVEL", cliente.getLimite_disponivel());
        content.put("PESSOA_CONTATO_FINANCEIRO", cliente.getPessoa_contato_financeiro());
        content.put("EMAIL_FINANCEIRO", cliente.getEmail_financeiro());
        content.put("OBSERVACOES_FATURAMENTO", cliente.getObservacoes_faturamento());
        content.put("OBSERVACOES_FINANCEIRO", cliente.getObservacoes_financeiro());
        content.put("TELEFONE_DOIS", cliente.getTelefone_dois());
        content.put("TELEFONE_TRES", cliente.getTelefone_tres());
        content.put("PESSOA_CONTATO_PRINCIPAL", cliente.getPessoa_contato_principal());
        content.put("IND_DA_IE_DESTINATARIO", cliente.getInd_da_ie_destinatario());
        content.put("COMISSAO_PERCENTUAL", cliente.getComissao_percentual());
        content.put("ID_SETOR", cliente.getId_setor());
        content.put("NFE_EMAIL_ENVIAR", cliente.getNfe_email_enviar());
        content.put("NFE_EMAIL_UM", cliente.getNfe_email_um());
        content.put("NFE_EMAIL_DOIS", cliente.getNfe_email_dois());
        content.put("NFE_EMAIL_TRES", cliente.getNfe_email_tres());
        content.put("NFE_EMAIL_QUATRO", cliente.getNfe_email_quatro());
        content.put("NFE_EMAIL_CINCO", cliente.getNfe_email_cinco());
        content.put("ID_GRUPO_VENDEDOR", cliente.getId_grupo_vendedor());
        content.put("VENDEDOR_USA_PORTAL", cliente.getVendedor_usa_portal());
        content.put("VENDEDOR_ID_USER_PORTAL", cliente.getVendedor_id_user_portal());
        content.put("F_TARIFA", cliente.getF_tarifa());
        content.put("F_ID_TARIFA", cliente.getF_id_tarifa());
        content.put("F_PRODUTOR", cliente.getF_produtor());
        content.put("RG_NUMERO", cliente.getRg_numero());
        content.put("RG_SSP", cliente.getRg_ssp());
        content.put("CONTA_CONTABIL", cliente.getConta_contabil());
        content.put("MOTORISTA", cliente.getMotorista());
        content.put("F_ID_MOTORISTA", cliente.getF_id_motorista());
        content.put("HABILITACAO_NUMERO", cliente.getHabilitacao_numero());
        content.put("HABILITACAO_CATEGORIA", cliente.getHabilitacao_categoria());
        content.put("HABILITACAO_VENCIMENTO", cliente.getHabilitacao_vencimento());
        content.put("MOT_ID_TRANSPORTADORA", cliente.getMot_id_transportadora());
        db.update("TBL_CADASTRO", content, "ID_CADASTRO = " + cliente.getId_cadastro(), null);
        System.gc();
    }

    public void inserirTBL_PRODUTO(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", produto.getAtivo());
        content.put("ID_PRODUTO", produto.getId_produto());
        content.put("NOME_PRODUTO", produto.getNome_produto());
        content.put("UNIDADE", produto.getUnidade());
        content.put("TIPO_CADASTRO", produto.getTipo_cadastro());
        content.put("ID_ENTIDADE", produto.getId_entidade());
        content.put("NCM", produto.getNcm());
        content.put("ID_GRUPO", produto.getId_grupo());
        content.put("ID_SUB_GRUPO", produto.getId_sub_grupo());
        content.put("PESO_BRUTO", produto.getPeso_bruto());
        content.put("PESO_LIQUIDO", produto.getPeso_liquido());
        content.put("CODIGO_EM_BARRAS", produto.getCodigo_em_barras());
        content.put("MOVIMENTA_ESTOQUE", produto.getMovimenta_estoque());
        content.put("NOME_DA_MARCA", produto.getNome_da_marca());
        content.put("ID_EMPRESA", produto.getId_empresa());
        content.put("ID_ORIGEM", produto.getId_origem());
        content.put("CUSTO_PRODUTO", produto.getCusto_produto());
        content.put("CUSTO_PER_IPI", produto.getCusto_per_ipi());
        content.put("CUSTO_IPI", produto.getCusto_ipi());
        content.put("CUSTO_PER_FRETE", produto.getCusto_per_frete());
        content.put("CUSTO_FRETE", produto.getCusto_frete());
        content.put("CUSTO_PER_ICMS", produto.getCusto_per_icms());
        content.put("CUSTO_ICMS", produto.getCusto_icms());
        content.put("CUSTO_PER_FIN", produto.getCusto_per_fin());
        content.put("CUSTO_FIN", produto.getCusto_fin());
        content.put("CUSTO_PER_SUBST", produto.getCusto_per_subst());
        content.put("CUSTO_SUBT", produto.getCusto_subt());
        content.put("CUSTO_PER_OUTROS", produto.getCusto_per_outros());
        content.put("CUSTO_OUTROS", produto.getCusto_outros());
        content.put("VALOR_CUSTO", produto.getValor_custo());
        content.put("EXCLUIDO", produto.getExcluido());
        content.put("EXCLUIDO_POR", produto.getExcluido_por());
        content.put("EXCLUIDO_POR_DATA", produto.getExcluido_por_data());
        content.put("EXCLUIDO_CODIGO_NOVO", produto.getExcluido_codigo_novo());
        content.put("AJUSTE_PRECO_DATA", produto.getAjuste_preco_data());
        content.put("AJUSTE_PRECO_NFE", produto.getAjuste_preco_nfe());
        content.put("AJUSTE_PRECO_USUARIO", produto.getAjuste_preco_usuario());
        content.put("TOTAL_CUSTO", produto.getTotal_custo());
        content.put("TOTAL_CREDITO", produto.getTotal_credito());
        content.put("VALOR_CUSTO_ESTOQUE", produto.getValor_custo_estoque());
        content.put("CUSTO_DATA_INICIAL", produto.getCusto_data_inicial());
        content.put("CUSTO_VALOR_INICIAL", produto.getCusto_valor_inicial());
        content.put("PRODUTO_VENDA", produto.getProduto_venda());
        content.put("PRODUTO_INSUMO", produto.getProduto_insumo());
        content.put("PRODUTO_CONSUMO", produto.getProduto_consumo());
        content.put("PRODUTO_PRODUCAO", produto.getProduto_producao());
        content.put("VENDA_PERC_COMISSAO", produto.getVenda_perc_comissao());
        content.put("VENDA_PRECO", produto.getVenda_preco());
        content.put("VENDA_PERC_COMISSAO_DOIS", produto.getVenda_perc_comissao_dois());
        content.put("DESCRICAO", produto.getDescricao());
        db.insert("TBL_PRODUTO", null, content);
        System.gc();
    }

    public void atualizarTBL_PRODUTO(String ATIVO, String ID_PRODUTO, String NOME_PRODUTO, String UNIDADE, String TIPO_CADASTRO, String ID_ENTIDADE, String NCM, String ID_GRUPO, String ID_SUB_GRUPO, String PESO_BRUTO, String PESO_LIQUIDO, String CODIGO_EM_BARRAS, String MOVIMENTA_ESTOQUE, String NOME_DA_MARCA, String ID_EMPRESA, String ID_ORIGEM, String CUSTO_PRODUTO, String CUSTO_PER_IPI, String CUSTO_IPI, String CUSTO_PER_FRETE, String CUSTO_FRETE, String CUSTO_PER_ICMS, String CUSTO_ICMS, String CUSTO_PER_FIN, String CUSTO_FIN, String CUSTO_PER_SUBST, String CUSTO_SUBT, String CUSTO_PER_OUTROS, String CUSTO_OUTROS, String VALOR_CUSTO, String EXCLUIDO, String EXCLUIDO_POR, String EXCLUIDO_POR_DATA, String EXCLUIDO_CODIGO_NOVO, String AJUSTE_PRECO_DATA, String AJUSTE_PRECO_NFE, String AJUSTE_PRECO_USUARIO, String TOTAL_CUSTO, String TOTAL_CREDITO, String VALOR_CUSTO_ESTOQUE, String CUSTO_DATA_INICIAL, String CUSTO_VALOR_INICIAL, String PRODUTO_VENDA, String PRODUTO_INSUMO, String PRODUTO_CONSUMO, String PRODUTO_PRODUCAO, String VENDA_PERC_COMISSAO, String VENDA_PRECO, String VENDA_PERC_COMISSAO_DOIS, String DESCRICAO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", ATIVO);
        content.put("NOME_PRODUTO", NOME_PRODUTO);
        content.put("UNIDADE", UNIDADE);
        content.put("TIPO_CADASTRO", TIPO_CADASTRO);
        content.put("ID_ENTIDADE", ID_ENTIDADE);
        content.put("NCM", NCM);
        content.put("ID_GRUPO", ID_GRUPO);
        content.put("ID_SUB_GRUPO", ID_SUB_GRUPO);
        content.put("PESO_BRUTO", PESO_BRUTO);
        content.put("PESO_LIQUIDO", PESO_LIQUIDO);
        content.put("CODIGO_EM_BARRAS", CODIGO_EM_BARRAS);
        content.put("MOVIMENTA_ESTOQUE", MOVIMENTA_ESTOQUE);
        content.put("NOME_DA_MARCA", NOME_DA_MARCA);
        content.put("ID_EMPRESA", ID_EMPRESA);
        content.put("ID_ORIGEM", ID_ORIGEM);
        content.put("CUSTO_PRODUTO", CUSTO_PRODUTO);
        content.put("CUSTO_PER_IPI", CUSTO_PER_IPI);
        content.put("CUSTO_IPI", CUSTO_IPI);
        content.put("CUSTO_PER_FRETE", CUSTO_PER_FRETE);
        content.put("CUSTO_FRETE", CUSTO_FRETE);
        content.put("CUSTO_PER_ICMS", CUSTO_PER_ICMS);
        content.put("CUSTO_ICMS", CUSTO_ICMS);
        content.put("CUSTO_PER_FIN", CUSTO_PER_FIN);
        content.put("CUSTO_FIN", CUSTO_FIN);
        content.put("CUSTO_PER_SUBST", CUSTO_PER_SUBST);
        content.put("CUSTO_SUBT", CUSTO_SUBT);
        content.put("CUSTO_PER_OUTROS", CUSTO_PER_OUTROS);
        content.put("CUSTO_OUTROS", CUSTO_OUTROS);
        content.put("VALOR_CUSTO", VALOR_CUSTO);
        content.put("EXCLUIDO", EXCLUIDO);
        content.put("EXCLUIDO_POR", EXCLUIDO_POR);
        content.put("EXCLUIDO_POR_DATA", EXCLUIDO_POR_DATA);
        content.put("EXCLUIDO_CODIGO_NOVO", EXCLUIDO_CODIGO_NOVO);
        content.put("AJUSTE_PRECO_DATA", AJUSTE_PRECO_DATA);
        content.put("AJUSTE_PRECO_NFE", AJUSTE_PRECO_NFE);
        content.put("AJUSTE_PRECO_USUARIO", AJUSTE_PRECO_USUARIO);
        content.put("TOTAL_CUSTO", TOTAL_CUSTO);
        content.put("TOTAL_CREDITO", TOTAL_CREDITO);
        content.put("VALOR_CUSTO_ESTOQUE", VALOR_CUSTO_ESTOQUE);
        content.put("CUSTO_DATA_INICIAL", CUSTO_DATA_INICIAL);
        content.put("CUSTO_VALOR_INICIAL", CUSTO_VALOR_INICIAL);
        content.put("PRODUTO_VENDA", PRODUTO_VENDA);
        content.put("PRODUTO_INSUMO", PRODUTO_INSUMO);
        content.put("PRODUTO_CONSUMO", PRODUTO_CONSUMO);
        content.put("PRODUTO_PRODUCAO", PRODUTO_PRODUCAO);
        content.put("VENDA_PERC_COMISSAO", VENDA_PERC_COMISSAO);
        content.put("VENDA_PRECO", VENDA_PRECO);
        content.put("VENDA_PERC_COMISSAO_DOIS", VENDA_PERC_COMISSAO_DOIS);
        content.put("DESCRICAO", DESCRICAO);
        db.update("TBL_PRODUTO", content, "ID_PRODUTO = '" + ID_PRODUTO + "'", null);
        System.gc();
    }

    public void inserirTBL_TABELA_PRECO_CAB(TabelaPreco tabelaPreco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_TABELA", tabelaPreco.getId_tabela());
        content.put("ID_EMPRESA", tabelaPreco.getId_empresa());
        content.put("ATIVO", tabelaPreco.getAtivo());
        content.put("ID_TIPO_TABELA", tabelaPreco.getId_tipo_tabela());
        content.put("NOME_TABELA", tabelaPreco.getNome_tabela());
        content.put("DATA_INICIO", tabelaPreco.getData_inicio());
        content.put("DATA_FIM", tabelaPreco.getData_fim());
        content.put("DESCONTO_DE_PERC", tabelaPreco.getDesconto_de_perc());
        content.put("DESCONTO_A_PERC", tabelaPreco.getDesconto_a_perc());
        content.put("COMISSAO_PERC", tabelaPreco.getComissao_perc());
        content.put("VERBA_PERC", tabelaPreco.getVerba_perc());
        content.put("FAIXA_VALOR_DE", tabelaPreco.getFaixa_valor_de());
        content.put("FAIXA_VALOR_A", tabelaPreco.getFaixa_valor_a());
        content.put("USUARIO_ID", tabelaPreco.getUsuario_id());
        content.put("USUARIO_NOME", tabelaPreco.getUsuario_nome());
        content.put("USUARIO_DATA", tabelaPreco.getUsuario_data());
        content.put("DESCONTO_VERBA_MAX", tabelaPreco.getDesconto_verba_max());
        content.put("ID_GRUPO_VENDEDORES", tabelaPreco.getId_grupo_vendedores());
        content.put("UTILIZA_VERBA", tabelaPreco.getUtiliza_verba());
        content.put("FAIXA_VALOR_BRUTO_DE", tabelaPreco.getFaixa_valor_bruto_de());
        content.put("FAIXA_VALOR_BRUTO_A", tabelaPreco.getFaixa_valor_bruto_a());

        db.insert("TBL_TABELA_PRECO_CAB", null, content);
        System.gc();
    }

    public void atualizarTBL_TABELA_PRECO_CAB(String ID_TABELA, String ID_EMPRESA, String ATIVO, String ID_TIPO_TABELA, String NOME_TABELA, String DATA_INICIO, String DATA_FIM, String DESCONTO_DE_PERC, String DESCONTO_A_PERC, String COMISSAO_PERC, String VERBA_PERC, String FAIXA_VALOR_DE, String FAIXA_VALOR_A, String USUARIO_ID, String USUARIO_NOME, String USUARIO_DATA, String DESCONTO_VERBA_MAX, String ID_GRUPO_VENDEDORES, String UTILIZA_VERBA, String FAIXA_VALOR_BRUTO_DE, String FAIXA_VALOR_BRUTO_A) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_TABELA", ID_TABELA);
        content.put("ID_EMPRESA", ID_EMPRESA);
        content.put("ATIVO", ATIVO);
        content.put("ID_TIPO_TABELA", ID_TIPO_TABELA);
        content.put("NOME_TABELA", NOME_TABELA);
        content.put("DATA_INICIO", DATA_INICIO);
        content.put("DATA_FIM", DATA_FIM);
        content.put("DESCONTO_DE_PERC", DESCONTO_DE_PERC);
        content.put("DESCONTO_A_PERC", DESCONTO_A_PERC);
        content.put("COMISSAO_PERC", COMISSAO_PERC);
        content.put("VERBA_PERC", VERBA_PERC);
        content.put("FAIXA_VALOR_DE", FAIXA_VALOR_DE);
        content.put("FAIXA_VALOR_A", FAIXA_VALOR_A);
        content.put("USUARIO_ID", USUARIO_ID);
        content.put("USUARIO_NOME", USUARIO_NOME);
        content.put("USUARIO_DATA", USUARIO_DATA);
        content.put("DESCONTO_VERBA_MAX", DESCONTO_VERBA_MAX);
        content.put("ID_GRUPO_VENDEDORES", ID_GRUPO_VENDEDORES);
        content.put("UTILIZA_VERBA", UTILIZA_VERBA);
        content.put("FAIXA_VALOR_BRUTO_DE", FAIXA_VALOR_BRUTO_DE);
        content.put("FAIXA_VALOR_BRUTO_A", FAIXA_VALOR_BRUTO_A);

        db.update("TBL_TABELA_PRECO_CAB", content, "ID_TABELA = " + ID_TABELA, null);
        System.gc();
    }

    public void inserirTBL_TABELA_PRECO_ITENS(TabelaPrecoItem tabelaPrecoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_ITEM", tabelaPrecoItem.getId_item());
        content.put("ID_TABELA", tabelaPrecoItem.getId_tabela());
        content.put("PERC_DESC_INICIAL", tabelaPrecoItem.getPerc_desc_inicial());
        content.put("PERC_DESC_FINAL", tabelaPrecoItem.getPerc_desc_final());
        content.put("PERC_COM_INTERNO", tabelaPrecoItem.getPerc_com_interno());
        content.put("PERC_COM_EXTERNO", tabelaPrecoItem.getPerc_com_externo());
        content.put("PERC_COM_EXPORTACAO", tabelaPrecoItem.getPerc_com_exportacao());
        content.put("PONTOS_PREMIACAO", tabelaPrecoItem.getPontos_premiacao());
        content.put("COR_PAINEL", tabelaPrecoItem.getCor_painel());
        content.put("COR_FONTE", tabelaPrecoItem.getCor_fonte());
        content.put("VERBA_PERC", tabelaPrecoItem.getVerba_perc());
        content.put("UTILIZA_VERBA", tabelaPrecoItem.getUtiliza_verba());
        content.put("DESCONTO_VERBA_MAX", tabelaPrecoItem.getDesconto_verba_max());
        content.put("ID_USUARIO", tabelaPrecoItem.getId_usuario());
        content.put("USUARIO", tabelaPrecoItem.getUsuario());
        content.put("USUARIO_DATA", tabelaPrecoItem.getUsuario_data());
        content.put("COR_WEB", tabelaPrecoItem.getCor_web());

        db.insert("TBL_TABELA_PRECO_ITENS", null, content);
        System.gc();
    }

    public void atualizarTBL_TABELA_PRECO_ITENS(String ID_ITEM, String ID_TABELA, String PERC_DESC_INICIAL, String PERC_DESC_FINAL, String PERC_COM_INTERNO, String PERC_COM_EXTERNO, String PERC_COM_EXPORTACAO, String PONTOS_PREMIACAO, String COR_PAINEL, String COR_FONTE, String VERBA_PERC, String UTILIZA_VERBA, String DESCONTO_VERBA_MAX, String ID_USUARIO, String USUARIO, String USUARIO_DATA, String COR_WEB) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_ITEM", ID_ITEM);
        content.put("ID_TABELA", ID_TABELA);
        content.put("PERC_DESC_INICIAL", PERC_DESC_INICIAL);
        content.put("PERC_DESC_FINAL", PERC_DESC_FINAL);
        content.put("PERC_COM_INTERNO", PERC_COM_INTERNO);
        content.put("PERC_COM_EXTERNO", PERC_COM_EXTERNO);
        content.put("PERC_COM_EXPORTACAO", PERC_COM_EXPORTACAO);
        content.put("PONTOS_PREMIACAO", PONTOS_PREMIACAO);
        content.put("COR_PAINEL", COR_PAINEL);
        content.put("COR_FONTE", COR_FONTE);
        content.put("VERBA_PERC", VERBA_PERC);
        content.put("UTILIZA_VERBA", UTILIZA_VERBA);
        content.put("DESCONTO_VERBA_MAX", DESCONTO_VERBA_MAX);
        content.put("ID_USUARIO", ID_USUARIO);
        content.put("USUARIO", USUARIO);
        content.put("USUARIO_DATA", USUARIO_DATA);
        content.put("COR_WEB", COR_WEB);

        db.update("TBL_TABELA_PRECO_ITENS", content, "ID_ITEM = " + ID_ITEM, null);
        System.gc();
    }

    public void insertTBL_LOGIN(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ID_LOGIN", 1);
        content.put("LOGIN", usuario.getLogin());
        content.put("SENHA", usuario.getSenha());
        content.put("LOGADO", usuario.getLogado());
        content.put("APARELHO_ID", usuario.getAparelho_id());
        content.put("TOKEN", usuario.getToken());
        db.insert("TBL_LOGIN", null, content);
        System.gc();
    }

    public void atualizarTBL_LOGIN(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("LOGIN", usuario.getLogin());
        content.put("SENHA", usuario.getSenha());
        content.put("LOGADO", usuario.getLogado());
        content.put("APARELHO_ID", usuario.getAparelho_id());
        content.put("TOKEN", usuario.getToken());
        db.update("TBL_LOGIN", content, "ID_LOGIN = " + 1, null);
        System.gc();
    }

    public void inserirTBL_OPERACAO_ESTOQUE(Operacao operacao) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", operacao.getAtivo());
        content.put("ID_OPERACAO", operacao.getId_operacao());
        content.put("NOME_OPERACAO", operacao.getNome_operacao());
        db.insert("TBL_OPERACAO_ESTOQUE", null, content);
        System.gc();
    }

    public void atualizarTBL_OPERACAO_ESTOQUE(String ATIVO, String ID_OPERACAO, String NOME_OPERACAO) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", ATIVO);
        content.put("NOME_OPERACAO", NOME_OPERACAO);
        db.update("TBL_OPERACAO_ESTOQUE", content, "ID_OPERACAO = " + ID_OPERACAO, null);
        System.gc();
    }

    public void inserirTBL_CONDICOES_PAG_CAB(CondicoesPagamento condicoesPagamento) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", condicoesPagamento.getAtivo());
        content.put("ID_CONDICAO", condicoesPagamento.getId_condicao());
        content.put("NOME_CONDICAO", condicoesPagamento.getNome_condicao());
        content.put("NUMERO_PARCELAS", condicoesPagamento.getNumero_parcelas());
        content.put("INTERVALO_DIAS", condicoesPagamento.getIntervalo_dias());
        content.put("TIPO_CONDICAO", condicoesPagamento.getTipo_condicao());
        content.put("NFE_TIPO_FINANCEIRO", condicoesPagamento.getNfe_tipo_financeiro());
        content.put("NFE_MOSTRAR_PARCELAS", condicoesPagamento.getNfe_mostrar_parcelas());
        content.put("USUARIO_ID", condicoesPagamento.getUsuario_id());
        content.put("USUARIO_NOME", condicoesPagamento.getUsuario_nome());
        content.put("USUARIO_DATA", condicoesPagamento.getUsuario_data());
        content.put("PUBLICAR_NA_WEB", condicoesPagamento.getPublicar_na_web());

        db.insert("TBL_CONDICOES_PAG_CAB", null, content);
        System.gc();
    }

    public void atualizarTBL_CONDICOES_PAG_CAB(String ATIVO, String ID_CONDICAO, String NOME_CONDICAO, String NUMERO_PARCELAS, String INTERVALO_DIAS, String TIPO_CONDICAO, String NFE_TIPO_FINANCEIRO, String NFE_MOSTRAR_PARCELAS, String USUARIO_ID, String USUARIO_NOME, String USUARIO_DATA, String PUBLICAR_NA_WEB) throws android.database.sqlite.SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("ATIVO", ATIVO);
        content.put("ID_CONDICAO", ID_CONDICAO);
        content.put("NOME_CONDICAO", NOME_CONDICAO);
        content.put("NUMERO_PARCELAS", NUMERO_PARCELAS);
        content.put("INTERVALO_DIAS", INTERVALO_DIAS);
        content.put("TIPO_CONDICAO", TIPO_CONDICAO);
        content.put("NFE_TIPO_FINANCEIRO", NFE_TIPO_FINANCEIRO);
        content.put("NFE_MOSTRAR_PARCELAS", NFE_MOSTRAR_PARCELAS);
        content.put("USUARIO_ID", USUARIO_ID);
        content.put("USUARIO_NOME", USUARIO_NOME);
        content.put("USUARIO_DATA", USUARIO_DATA);
        content.put("PUBLICAR_NA_WEB", PUBLICAR_NA_WEB);

        db.update("TBL_CONDICOES_PAG_CAB", content, "ID_CONDICAO = " + ID_CONDICAO, null);
        System.gc();
    }

    public void inserirTBL_VENDEDOR_BONUS_RESUMO(VendedorBonusResumo vendedorBonusResumo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_VENDEDOR", vendedorBonusResumo.getId_vendedor());
        content.put("ID_EMPRESA", vendedorBonusResumo.getId_empresa());
        content.put("VALOR_CREDITO", vendedorBonusResumo.getValor_credito());
        content.put("VALOR_DEBITO", vendedorBonusResumo.getValor_debito());
        content.put("VALOR_BONUS_CANCELADOS", vendedorBonusResumo.getValor_bonus_cancelados());
        content.put("VALOR_SALDO", vendedorBonusResumo.getValor_saldo());
        content.put("DATA_ULTIMA_ATUALIZACAO", vendedorBonusResumo.getData_ultima_atualizacao());

        db.insert("TBL_VENDEDOR_BONUS_RESUMO", null, content);
        System.gc();
    }


    public void atualizarTBL_VENDEDOR_BONUS_RESUMO(String ID_VENDEDOR, String ID_EMPRESA, String VALOR_CREDITO, String VALOR_DEBITO, String VALOR_BONUS_CANCELADOS, String VALOR_SALDO, String DATA_ULTIMA_ATUALIZACAO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_VENDEDOR", ID_VENDEDOR);
        content.put("ID_EMPRESA", ID_EMPRESA);
        content.put("VALOR_CREDITO", VALOR_CREDITO);
        content.put("VALOR_DEBITO", VALOR_DEBITO);
        content.put("VALOR_BONUS_CANCELADOS", VALOR_BONUS_CANCELADOS);
        content.put("VALOR_SALDO", VALOR_SALDO);
        content.put("DATA_ULTIMA_ATUALIZACAO", DATA_ULTIMA_ATUALIZACAO);

        db.update("TBL_VENDEDOR_BONUS_RESUMO", content, "ID_VENDEDOR = " + ID_VENDEDOR, null);
        System.gc();
    }

    public void inserirTBL_WEB_PEDIDO(WebPedido webPedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_EMPRESA", webPedido.getId_empresa());
        content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro());
        content.put("ID_VENDEDOR", webPedido.getId_vendedor());
        content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
        content.put("ID_OPERACAO", webPedido.getId_operacao());
        content.put("ID_TABELA", webPedido.getId_tabela());
        content.put("NOME_EXTENSO", webPedido.getCadastro().getNome_cadastro());
        content.put("DATA_EMISSAO", this.pegaDataAtual());
        content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
        content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
        content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
        content.put("DESCONTO_PER", webPedido.getDesconto_per());
        content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
        content.put("VALOR_TOTAL", webPedido.getValor_total());
        content.put("EXCLUIDO", webPedido.getExcluido());
        content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
        content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
        content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
        content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
        content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
        content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
        content.put("OBSERVACOES", webPedido.getObservacoes());
        content.put("STATUS", webPedido.getStatus());
        content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
        content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
        content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
        content.put("PONTOS_TOTAL", webPedido.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedido.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
        content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
        content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
        content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
        content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());
        content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());

        db.insert("TBL_WEB_PEDIDO", null, content);
        System.gc();
    }

    public void atualizarTBL_WEB_PEDIDO(WebPedido webPedido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_WEB_PEDIDO", webPedido.getId_web_pedido());
        content.put("ID_EMPRESA", webPedido.getId_empresa());
        content.put("ID_CADASTRO", webPedido.getCadastro().getId_cadastro());
        content.put("ID_VENDEDOR", webPedido.getId_vendedor());
        content.put("ID_CONDICAO_PAGAMENTO", webPedido.getId_condicao_pagamento());
        content.put("ID_OPERACAO", webPedido.getId_operacao());
        content.put("ID_TABELA", webPedido.getId_tabela());
        content.put("NOME_EXTENSO", webPedido.getNome_extenso());
        content.put("DATA_EMISSAO", webPedido.getData_emissao());
        content.put("VALOR_PRODUTOS", webPedido.getValor_produtos());
        content.put("VALOR_DESCONTO", webPedido.getValor_desconto());
        content.put("VALOR_DESCONTO_ADD", webPedido.getValor_desconto_add());
        content.put("DESCONTO_PER", webPedido.getDesconto_per());
        content.put("DESCONTO_PER_ADD", webPedido.getDesconto_per_add());
        content.put("VALOR_TOTAL", webPedido.getValor_total());
        content.put("EXCLUIDO", webPedido.getExcluido());
        content.put("EXCLUIDO_USUARIO_ID", webPedido.getExcluido_usuario_id());
        content.put("EXCLUIDO_USUARIO_NOME", webPedido.getExcluido_usuario_nome());
        content.put("EXCLUIDO_USUARIO_DATA", webPedido.getExcluido_usuario_data());
        content.put("JUSTIFICATIVA_EXCLUSAO", webPedido.getJustificativa_exclusao());
        content.put("USUARIO_LANCAMENTO_ID", webPedido.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_NOME", webPedido.getUsuario_lancamento_nome());
        content.put("USUARIO_LANCAMENTO_DATA", webPedido.getUsuario_lancamento_data());
        content.put("OBSERVACOES", webPedido.getObservacoes());
        content.put("STATUS", webPedido.getStatus());
        content.put("ID_PEDIDO_VENDA", webPedido.getId_pedido_venda());
        content.put("ID_NOTA_FISCAL", webPedido.getId_nota_fiscal());
        content.put("ID_TABELA_PRECO_FAIXA", webPedido.getId_tabela_preco_faixa());
        content.put("PONTOS_TOTAL", webPedido.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedido.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedido.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedido.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedido.getComissao_valor());
        content.put("ID_FAIXA_FINAL", webPedido.getId_faixa_final());
        content.put("VALOR_BONUS_CREDOR", webPedido.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedido.getPerc_bonus_credor());
        content.put("DATA_PREV_ENTREGA", webPedido.getData_prev_entrega());
        content.put("PEDIDO_ENVIADO", webPedido.getPedido_enviado());
        content.put("ID_WEB_PEDIDO_SERVIDOR", webPedido.getId_web_pedido_servidor());

        db.update("TBL_WEB_PEDIDO", content, "ID_WEB_PEDIDO = " + webPedido.getId_web_pedido(), null);
        System.gc();
    }

    public void inserirTBL_WEB_PEDIDO_ITENS(WebPedidoItens webPedidoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
        content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
        content.put("ID_PRODUTO", webPedidoItem.getId_produto());
        content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
        content.put("QUANTIDADE", webPedidoItem.getQuantidade());
        content.put("VALOR_UNITARIO", webPedidoItem.getVenda_preco());
        content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
        content.put("VALOR_DESCONTO_PER", webPedidoItem.getTabela_preco_faixa().getPerc_desc_inicial());
        content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
        content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
        content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
        content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
        content.put("DATA_MOVIMENTACAO", this.pegaDataAtual());
        content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_DATA", this.pegaDataHoraAtual());
        content.put("ID_TABELA_PRECO_FAIXA", webPedidoItem.getTabela_preco_faixa().getId_item());
        content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
        content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
        content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedidoItem.getTabela_preco_faixa().getCor_painel());
        content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
        content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
        content.put("ID_TABELA_PRECO", webPedidoItem.getTabela_preco_faixa().getId_item());
        content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
        content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
        content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
        content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
        content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
        content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
        content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
        content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
        content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
        content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
        content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
        content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
        content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
        content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
        content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());

        db.insert("TBL_WEB_PEDIDO_ITENS", null, content);
        System.gc();
    }

    public void atualizarTBL_WEB_PEDIDO_ITENS(WebPedidoItens webPedidoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("ID_WEB_ITEM", webPedidoItem.getId_web_item());
        content.put("ID_WEB_ITEM_SERVIDOR", webPedidoItem.getId_web_item_servidor());
        content.put("ID_PEDIDO", webPedidoItem.getId_pedido());
        content.put("ID_PRODUTO", webPedidoItem.getId_produto());
        content.put("ID_EMPRESA", webPedidoItem.getId_empresa());
        content.put("QUANTIDADE", webPedidoItem.getQuantidade());
        content.put("VALOR_UNITARIO", webPedidoItem.getValor_unitario());
        content.put("VALOR_BRUTO", webPedidoItem.getValor_bruto());
        content.put("VALOR_DESCONTO_PER", webPedidoItem.getTabela_preco_faixa().getPerc_desc_inicial());
        content.put("VALOR_DESCONTO_REAL", webPedidoItem.getValor_desconto_real());
        content.put("VALOR_DESCONTO_PER_ADD", webPedidoItem.getValor_desconto_per_add());
        content.put("VALOR_DESCONTO_REAL_ADD", webPedidoItem.getValor_desconto_real_add());
        content.put("VALOR_TOTAL", webPedidoItem.getValor_total());
        content.put("DATA_MOVIMENTACAO", webPedidoItem.getData_movimentacao());
        content.put("USUARIO_LANCAMENTO_ID", webPedidoItem.getUsuario_lancamento_id());
        content.put("USUARIO_LANCAMENTO_DATA", webPedidoItem.getUsuario_lancamento_data());
        content.put("ID_TABELA_PRECO_FAIXA", webPedidoItem.getTabela_preco_faixa().getId_item());
        content.put("ID_ITEM_DESCONTO", webPedidoItem.getId_item_desconto());
        content.put("PONTOS_UNITARIO", webPedidoItem.getPontos_unitario());
        content.put("PONTOS_TOTAL", webPedidoItem.getPontos_total());
        content.put("PONTOS_COEFICIENTE", webPedidoItem.getPontos_coeficiente());
        content.put("PONTOS_COR", webPedidoItem.getPontos_cor());
        content.put("COMISSAO_PERCENTUAL", webPedidoItem.getComissao_percentual());
        content.put("COMISSAO_VALOR", webPedidoItem.getComissao_valor());
        content.put("VALOR_BONUS_CREDOR", webPedidoItem.getValor_bonus_credor());
        content.put("PERC_BONUS_CREDOR", webPedidoItem.getPerc_bonus_credor());
        content.put("ID_TABELA_PRECO", webPedidoItem.getId_tabela_preco());
        content.put("VALOR_DESCONTO_PER_ORIG", webPedidoItem.getValor_desconto_per_orig());
        content.put("VALOR_DESCONTO_REAL_ORIG", webPedidoItem.getValor_desconto_real_orig());
        content.put("VALOR_DESCONTO_PER_ADD_ORIG", webPedidoItem.getValor_desconto_per_add_orig());
        content.put("VALOR_DESCONTO_REAL_ADD_ORIG", webPedidoItem.getValor_desconto_real_add_orig());
        content.put("ID_TABELA_PRECO_FAIXA_ORIG", webPedidoItem.getId_tabela_preco_faixa_orig());
        content.put("VALOR_TOTAL_ORIG", webPedidoItem.getValor_total_orig());
        content.put("PONTOS_UNITARIO_ORIG", webPedidoItem.getPontos_unitario_orig());
        content.put("PONTOS_COEFICIENTE_ORIG", webPedidoItem.getPontos_coeficiente_orig());
        content.put("COMISSAO_PERCENTUAL_ORIG", webPedidoItem.getComissao_percentual_orig());
        content.put("VALOR_BONUS_CREDOR_ORIG", webPedidoItem.getValor_bonus_credor_orig());
        content.put("PERC_BONUS_CREDOR_ORIG", webPedidoItem.getPerc_bonus_credor_orig());
        content.put("COMISSAO_VALOR_ORIG", webPedidoItem.getComissao_valor_orig());
        content.put("PONTOS_TOTAL_ORIG", webPedidoItem.getPontos_total_orig());
        content.put("PONTOS_COR_ORIG", webPedidoItem.getPontos_cor_orig());
        content.put("VALOR_PRECO_PAGO", webPedidoItem.getValor_preco_pago());

        if (contagem("SELECT COUNT(*) FROM TBL_WEB_PEDIDO_ITENS WHERE ID_WEB_ITEM = " + webPedidoItem.getId_web_item()) <= 0) {
            db.insert("TBL_WEB_PEDIDO_ITENS", null, content);
        } else {
            db.update("TBL_WEB_PEDIDO_ITENS", content, "ID_WEB_ITEM = " + webPedidoItem.getId_web_item(), null);
        }

        System.gc();
    }


    public String consulta(String SQL, String campo) throws android.database.CursorIndexOutOfBoundsException {
        String resultado = "";
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        resultado += cursor.getString(cursor.getColumnIndex(campo));
        return resultado;
    }

    public int contagem(String SQL) {
        int resultado;
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        resultado = cursor.getInt(0);
        return resultado;
    }

    public void alterar(String SQL) {
        SQLiteDatabase banco = this.getWritableDatabase();
        banco.execSQL(SQL);
    }

    public String[] listaConsulta(String SQL, String campo) {
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        String[] retorno = new String[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            retorno[i] = cursor.getString(cursor.getColumnIndex(campo));
            i++;
        }
        return retorno;
    }

    public List<Cliente> listaCliente(String SQL) {
        List<Cliente> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor = banco.rawQuery(SQL, null);

        cursor.moveToFirst();
        do {
            Cliente cliente = new Cliente();

            cliente.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            cliente.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            cliente.setId_cadastro(cursor.getString(cursor.getColumnIndex("ID_CADASTRO")));
            cliente.setPessoa_f_j(cursor.getString(cursor.getColumnIndex("PESSOA_F_J")));
            cliente.setData_aniversario(cursor.getString(cursor.getColumnIndex("DATA_ANIVERSARIO")));
            cliente.setNome_cadastro(cursor.getString(cursor.getColumnIndex("NOME_CADASTRO")));
            cliente.setNome_fantasia(cursor.getString(cursor.getColumnIndex("NOME_FANTASIA")));
            cliente.setCpf_cnpj(cursor.getString(cursor.getColumnIndex("CPF_CNPJ")));
            cliente.setInscri_estadual(cursor.getString(cursor.getColumnIndex("INSCRI_ESTADUAL")));
            cliente.setInscri_municipal(cursor.getString(cursor.getColumnIndex("INSCRI_MUNICIPAL")));
            cliente.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
            cliente.setEndereco_bairro(cursor.getString(cursor.getColumnIndex("ENDERECO_BAIRRO")));
            cliente.setEndereco_numero(cursor.getString(cursor.getColumnIndex("ENDERECO_NUMERO")));
            cliente.setEndereco_complemento(cursor.getString(cursor.getColumnIndex("ENDERECO_COMPLEMENTO")));
            cliente.setEndereco_uf(cursor.getString(cursor.getColumnIndex("ENDERECO_UF")));
            cliente.setEndereco_id_municipio(cursor.getString(cursor.getColumnIndex("ENDERECO_ID_MUNICIPIO")));
            cliente.setEndereco_cep(cursor.getString(cursor.getColumnIndex("ENDERECO_CEP")));
            cliente.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            cliente.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            cliente.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            cliente.setF_cliente(cursor.getString(cursor.getColumnIndex("F_CLIENTE")));
            cliente.setF_fornecedor(cursor.getString(cursor.getColumnIndex("F_FORNECEDOR")));
            cliente.setF_funcionario(cursor.getString(cursor.getColumnIndex("F_FUNCIONARIO")));
            cliente.setF_vendedor(cursor.getString(cursor.getColumnIndex("F_VENDEDOR")));
            cliente.setF_transportador(cursor.getString(cursor.getColumnIndex("F_TRANSPORTADOR")));
            cliente.setData_ultima_compra(cursor.getString(cursor.getColumnIndex("DATA_ULTIMA_COMPRA")));
            cliente.setNome_vendedor(cursor.getString(cursor.getColumnIndex("NOME_VENDEDOR")));
            cliente.setF_id_cliente(cursor.getString(cursor.getColumnIndex("F_ID_CLIENTE")));
            cliente.setId_entidade(cursor.getString(cursor.getColumnIndex("ID_ENTIDADE")));
            cliente.setF_id_fornecedor(cursor.getString(cursor.getColumnIndex("F_ID_FORNECEDOR")));
            cliente.setF_id_vendedor(cursor.getString(cursor.getColumnIndex("F_ID_VENDEDOR")));
            cliente.setF_id_transportador(cursor.getString(cursor.getColumnIndex("F_ID_TRANSPORTADOR")));
            cliente.setTelefone_principal(cursor.getString(cursor.getColumnIndex("TELEFONE_PRINCIPAL")));
            cliente.setEmail_principal(cursor.getString(cursor.getColumnIndex("EMAIL_PRINCIPAL")));
            cliente.setNome_pais(cursor.getString(cursor.getColumnIndex("NOME_PAIS")));
            cliente.setF_id_funcionario(cursor.getString(cursor.getColumnIndex("F_ID_FUNCIONARIO")));
            cliente.setAvisar_com_dias(cursor.getString(cursor.getColumnIndex("AVISAR_COM_DIAS")));
            cliente.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
            cliente.setPadrao_id_c_custo(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_CUSTO")));
            cliente.setPadrao_id_c_gerenciadora(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_GERENCIADORA")));
            cliente.setPadrao_id_c_analitica(cursor.getString(cursor.getColumnIndex("PADRAO_ID_C_ANALITICA")));
            cliente.setCob_endereco(cursor.getString(cursor.getColumnIndex("COB_ENDERECO")));
            cliente.setCob_endereco_bairro(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_BAIRRO")));
            cliente.setCob_endereco_numero(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_NUMERO")));
            cliente.setCob_endereco_complemento(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_COMPLEMENTO")));
            cliente.setCob_endereco_uf(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_UF")));
            cliente.setCob_endereco_id_municipio(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_ID_MUNICIPIO")));
            cliente.setCob_endereco_cep(cursor.getString(cursor.getColumnIndex("COB_ENDERECO_CEP")));
            cliente.setNome_pais_cob(cursor.getString(cursor.getColumnIndex("NOME_PAIS_COB")));
            cliente.setLimite_credito(cursor.getString(cursor.getColumnIndex("LIMITE_CREDITO")));
            cliente.setLimite_disponivel(cursor.getString(cursor.getColumnIndex("LIMITE_DISPONIVEL")));
            cliente.setPessoa_contato_financeiro(cursor.getString(cursor.getColumnIndex("PESSOA_CONTATO_FINANCEIRO")));
            cliente.setEmail_financeiro(cursor.getString(cursor.getColumnIndex("EMAIL_FINANCEIRO")));
            cliente.setObservacoes_faturamento(cursor.getString(cursor.getColumnIndex("OBSERVACOES_FATURAMENTO")));
            cliente.setObservacoes_financeiro(cursor.getString(cursor.getColumnIndex("OBSERVACOES_FINANCEIRO")));
            cliente.setTelefone_dois(cursor.getString(cursor.getColumnIndex("TELEFONE_DOIS")));
            cliente.setTelefone_tres(cursor.getString(cursor.getColumnIndex("TELEFONE_TRES")));
            cliente.setPessoa_contato_principal(cursor.getString(cursor.getColumnIndex("PESSOA_CONTATO_PRINCIPAL")));
            cliente.setInd_da_ie_destinatario(cursor.getString(cursor.getColumnIndex("IND_DA_IE_DESTINATARIO")));
            cliente.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
            cliente.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
            cliente.setNfe_email_enviar(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_ENVIAR")));
            cliente.setNfe_email_um(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_UM")));
            cliente.setNfe_email_dois(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_DOIS")));
            cliente.setNfe_email_tres(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_TRES")));
            cliente.setNfe_email_quatro(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_QUATRO")));
            cliente.setNfe_email_cinco(cursor.getString(cursor.getColumnIndex("NFE_EMAIL_CINCO")));
            cliente.setId_grupo_vendedor(cursor.getString(cursor.getColumnIndex("ID_GRUPO_VENDEDOR")));
            cliente.setVendedor_usa_portal(cursor.getString(cursor.getColumnIndex("VENDEDOR_USA_PORTAL")));
            cliente.setVendedor_id_user_portal(cursor.getString(cursor.getColumnIndex("VENDEDOR_ID_USER_PORTAL")));
            cliente.setF_tarifa(cursor.getString(cursor.getColumnIndex("F_TARIFA")));
            cliente.setF_id_tarifa(cursor.getString(cursor.getColumnIndex("F_ID_TARIFA")));
            cliente.setF_produtor(cursor.getString(cursor.getColumnIndex("F_PRODUTOR")));
            cliente.setRg_numero(cursor.getString(cursor.getColumnIndex("RG_NUMERO")));
            cliente.setRg_ssp(cursor.getString(cursor.getColumnIndex("RG_SSP")));
            cliente.setConta_contabil(cursor.getString(cursor.getColumnIndex("CONTA_CONTABIL")));
            cliente.setMotorista(cursor.getString(cursor.getColumnIndex("MOTORISTA")));
            cliente.setF_id_motorista(cursor.getString(cursor.getColumnIndex("F_ID_MOTORISTA")));
            cliente.setHabilitacao_numero(cursor.getString(cursor.getColumnIndex("HABILITACAO_NUMERO")));
            cliente.setHabilitacao_categoria(cursor.getString(cursor.getColumnIndex("HABILITACAO_CATEGORIA")));
            cliente.setHabilitacao_vencimento(cursor.getString(cursor.getColumnIndex("HABILITACAO_VENCIMENTO")));
            cliente.setMot_id_transportadora(cursor.getString(cursor.getColumnIndex("MOT_ID_TRANSPORTADORA")));
            cliente.setLocal_cadastro(cursor.getString(cursor.getColumnIndex("LOCAL_CADASTRO")));

            lista.add(cliente);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<Produto> listaProduto(String SQL) {
        List<Produto> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            Produto produto = new Produto();

            produto.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
            produto.setNome_produto(cursor.getString(cursor.getColumnIndex("NOME_PRODUTO")));
            produto.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
            produto.setUnidade(cursor.getString(cursor.getColumnIndex("UNIDADE")));
            produto.setVenda_preco(cursor.getString(cursor.getColumnIndex("VENDA_PRECO")));
            produto.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));

            lista.add(produto);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<Operacao> listaOperacao(String SQL) {
        List<Operacao> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            Operacao operacao = new Operacao();

            operacao.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
            operacao.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            operacao.setNome_operacao(cursor.getString(cursor.getColumnIndex("NOME_OPERACAO")));

            lista.add(operacao);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<TabelaPreco> listaTabelaPreco(String SQL) {
        List<TabelaPreco> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            TabelaPreco tabelaPreco = new TabelaPreco();

            tabelaPreco.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
            tabelaPreco.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            tabelaPreco.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            tabelaPreco.setId_tipo_tabela(cursor.getString(cursor.getColumnIndex("ID_TIPO_TABELA")));
            tabelaPreco.setNome_tabela(cursor.getString(cursor.getColumnIndex("NOME_TABELA")));
            tabelaPreco.setData_inicio(cursor.getString(cursor.getColumnIndex("DATA_INICIO")));
            tabelaPreco.setData_fim(cursor.getString(cursor.getColumnIndex("DATA_FIM")));
            tabelaPreco.setDesconto_de_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_DE_PERC")));
            tabelaPreco.setDesconto_a_perc(cursor.getString(cursor.getColumnIndex("DESCONTO_A_PERC")));
            tabelaPreco.setComissao_perc(cursor.getString(cursor.getColumnIndex("COMISSAO_PERC")));
            tabelaPreco.setVerba_perc(cursor.getString(cursor.getColumnIndex("VERBA_PERC")));
            tabelaPreco.setFaixa_valor_de(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_DE")));
            tabelaPreco.setFaixa_valor_a(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_A")));
            tabelaPreco.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            tabelaPreco.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            tabelaPreco.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            tabelaPreco.setDesconto_verba_max(cursor.getString(cursor.getColumnIndex("DESCONTO_VERBA_MAX")));
            tabelaPreco.setId_grupo_vendedores(cursor.getString(cursor.getColumnIndex("ID_GRUPO_VENDEDORES")));
            tabelaPreco.setUtiliza_verba(cursor.getString(cursor.getColumnIndex("UTILIZA_VERBA")));
            tabelaPreco.setFaixa_valor_bruto_de(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_BRUTO_DE")));
            tabelaPreco.setFaixa_valor_bruto_a(cursor.getString(cursor.getColumnIndex("FAIXA_VALOR_BRUTO_A")));

            lista.add(tabelaPreco);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<TabelaPrecoItem> listaTabelaPrecoItem(String SQL) {
        List<TabelaPrecoItem> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            TabelaPrecoItem tabelaPrecoItem = new TabelaPrecoItem();

            tabelaPrecoItem.setId_item(cursor.getString(cursor.getColumnIndex("ID_ITEM")));
            tabelaPrecoItem.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
            tabelaPrecoItem.setPerc_desc_inicial(cursor.getString(cursor.getColumnIndex("PERC_DESC_INICIAL")));
            tabelaPrecoItem.setPerc_desc_final(cursor.getString(cursor.getColumnIndex("PERC_DESC_FINAL")));
            tabelaPrecoItem.setPerc_com_interno(cursor.getString(cursor.getColumnIndex("PERC_COM_INTERNO")));
            tabelaPrecoItem.setPerc_com_externo(cursor.getString(cursor.getColumnIndex("PERC_COM_EXTERNO")));
            tabelaPrecoItem.setPerc_com_exportacao(cursor.getString(cursor.getColumnIndex("PERC_COM_EXPORTACAO")));
            tabelaPrecoItem.setPontos_premiacao(cursor.getString(cursor.getColumnIndex("PONTOS_PREMIACAO")));
            tabelaPrecoItem.setCor_painel(cursor.getString(cursor.getColumnIndex("COR_PAINEL")));
            tabelaPrecoItem.setCor_fonte(cursor.getString(cursor.getColumnIndex("COR_FONTE")));
            tabelaPrecoItem.setVerba_perc(cursor.getString(cursor.getColumnIndex("VERBA_PERC")));
            tabelaPrecoItem.setUtiliza_verba(cursor.getString(cursor.getColumnIndex("UTILIZA_VERBA")));
            tabelaPrecoItem.setDesconto_verba_max(cursor.getString(cursor.getColumnIndex("DESCONTO_VERBA_MAX")));
            tabelaPrecoItem.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
            tabelaPrecoItem.setUsuario(cursor.getString(cursor.getColumnIndex("USUARIO")));
            tabelaPrecoItem.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            tabelaPrecoItem.setCor_web(cursor.getString(cursor.getColumnIndex("COR_WEB")));

            lista.add(tabelaPrecoItem);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<CondicoesPagamento> listaCondicoesPagamento(String SQL) {
        List<CondicoesPagamento> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            CondicoesPagamento condicoesPagamento = new CondicoesPagamento();

            condicoesPagamento.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            condicoesPagamento.setId_condicao(cursor.getString(cursor.getColumnIndex("ID_CONDICAO")));
            condicoesPagamento.setNome_condicao(cursor.getString(cursor.getColumnIndex("NOME_CONDICAO")));
            condicoesPagamento.setNumero_parcelas(cursor.getString(cursor.getColumnIndex("NUMERO_PARCELAS")));
            condicoesPagamento.setIntervalo_dias(cursor.getString(cursor.getColumnIndex("INTERVALO_DIAS")));
            condicoesPagamento.setTipo_condicao(cursor.getString(cursor.getColumnIndex("TIPO_CONDICAO")));
            condicoesPagamento.setNfe_tipo_financeiro(cursor.getString(cursor.getColumnIndex("NFE_TIPO_FINANCEIRO")));
            condicoesPagamento.setNfe_mostrar_parcelas(cursor.getString(cursor.getColumnIndex("NFE_MOSTRAR_PARCELAS")));
            condicoesPagamento.setUsuario_id(cursor.getString(cursor.getColumnIndex("USUARIO_ID")));
            condicoesPagamento.setUsuario_nome(cursor.getString(cursor.getColumnIndex("USUARIO_NOME")));
            condicoesPagamento.setUsuario_data(cursor.getString(cursor.getColumnIndex("USUARIO_DATA")));
            condicoesPagamento.setPublicar_na_web(cursor.getString(cursor.getColumnIndex("PUBLICAR_NA_WEB")));

            lista.add(condicoesPagamento);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<WebPedido> listaWebPedido(String SQL) {
        List<WebPedido> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            WebPedido webPedido = new WebPedido();
            try {

                webPedido.setId_web_pedido(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO")));
                webPedido.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
                webPedido.setCadastro(this.listaCliente("SELECT * FROM TBL_CADASTRO WHERE ID_CADASTRO = " + cursor.getString(cursor.getColumnIndex("ID_CADASTRO"))).get(0));
                webPedido.setId_vendedor(cursor.getString(cursor.getColumnIndex("ID_VENDEDOR")));
                webPedido.setId_condicao_pagamento(cursor.getString(cursor.getColumnIndex("ID_CONDICAO_PAGAMENTO")));
                webPedido.setId_operacao(cursor.getString(cursor.getColumnIndex("ID_OPERACAO")));
                webPedido.setId_tabela(cursor.getString(cursor.getColumnIndex("ID_TABELA")));
                webPedido.setNome_extenso(cursor.getString(cursor.getColumnIndex("NOME_EXTENSO")));
                webPedido.setData_emissao(cursor.getString(cursor.getColumnIndex("DATA_EMISSAO")));
                webPedido.setValor_produtos(cursor.getString(cursor.getColumnIndex("VALOR_PRODUTOS")));
                webPedido.setValor_desconto(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO")));
                webPedido.setValor_desconto_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_ADD")));
                webPedido.setDesconto_per(cursor.getString(cursor.getColumnIndex("DESCONTO_PER")));
                webPedido.setDesconto_per_add(cursor.getString(cursor.getColumnIndex("DESCONTO_PER_ADD")));
                webPedido.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
                webPedido.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
                webPedido.setExcluido_usuario_id(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_ID")));
                webPedido.setExcluido_usuario_nome(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_NOME")));
                webPedido.setExcluido_usuario_data(cursor.getString(cursor.getColumnIndex("EXCLUIDO_USUARIO_DATA")));
                webPedido.setJustificativa_exclusao(cursor.getString(cursor.getColumnIndex("JUSTIFICATIVA_EXCLUSAO")));
                webPedido.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
                webPedido.setUsuario_lancamento_nome(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_NOME")));
                webPedido.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
                webPedido.setObservacoes(cursor.getString(cursor.getColumnIndex("OBSERVACOES")));
                webPedido.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));
                webPedido.setId_pedido_venda(cursor.getString(cursor.getColumnIndex("ID_PEDIDO_VENDA")));
                webPedido.setId_nota_fiscal(cursor.getString(cursor.getColumnIndex("ID_NOTA_FISCAL")));
                webPedido.setId_tabela_preco_faixa(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA")));
                webPedido.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
                webPedido.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
                webPedido.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
                webPedido.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
                webPedido.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
                webPedido.setId_faixa_final(cursor.getString(cursor.getColumnIndex("ID_FAIXA_FINAL")));
                webPedido.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
                webPedido.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
                webPedido.setId_web_pedido_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_PEDIDO_SERVIDOR")));
                webPedido.setData_prev_entrega(cursor.getString(cursor.getColumnIndex("DATA_PREV_ENTREGA")));
                webPedido.setPedido_enviado(cursor.getString(cursor.getColumnIndex("PEDIDO_ENVIADO")));

                lista.add(webPedido);
            } catch (CursorIndexOutOfBoundsException e) {
                System.out.println("Cliente não encontrado na base de dados!");
            }

            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<WebPedidoItens> listaWebPedidoItens(String SQL) {
        List<WebPedidoItens> lista = new ArrayList<>();
        SQLiteDatabase banco = this.getReadableDatabase();
        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();
        do {
            WebPedidoItens webPedidoItens = new WebPedidoItens();

            webPedidoItens.setId_web_item(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM")));
            webPedidoItens.setId_web_item_servidor(cursor.getString(cursor.getColumnIndex("ID_WEB_ITEM_SERVIDOR")));
            webPedidoItens.setValor_preco_pago(cursor.getString(cursor.getColumnIndex("VALOR_PRECO_PAGO")));
            webPedidoItens.setId_pedido(cursor.getString(cursor.getColumnIndex("ID_PEDIDO")));
            webPedidoItens.setId_produto(cursor.getString(cursor.getColumnIndex("ID_PRODUTO")));
            webPedidoItens.setId_empresa(cursor.getString(cursor.getColumnIndex("ID_EMPRESA")));
            webPedidoItens.setQuantidade(cursor.getString(cursor.getColumnIndex("QUANTIDADE")));
            webPedidoItens.setValor_unitario(cursor.getString(cursor.getColumnIndex("VALOR_UNITARIO")));
            webPedidoItens.setValor_bruto(cursor.getString(cursor.getColumnIndex("VALOR_BRUTO")));
            webPedidoItens.setValor_desconto_per(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER")));
            webPedidoItens.setValor_desconto_real(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL")));
            webPedidoItens.setValor_desconto_per_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD")));
            webPedidoItens.setValor_desconto_real_add(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD")));
            webPedidoItens.setValor_total(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL")));
            webPedidoItens.setData_movimentacao(cursor.getString(cursor.getColumnIndex("DATA_MOVIMENTACAO")));
            webPedidoItens.setUsuario_lancamento_id(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_ID")));
            webPedidoItens.setUsuario_lancamento_data(cursor.getString(cursor.getColumnIndex("USUARIO_LANCAMENTO_DATA")));
            webPedidoItens.setTabela_preco_faixa(this.listaTabelaPrecoItem("SELECT * FROM TBL_TABELA_PRECO_ITENS WHERE ID_ITEM = " + cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA"))).get(0));
            webPedidoItens.setId_item_desconto(cursor.getString(cursor.getColumnIndex("ID_ITEM_DESCONTO")));
            webPedidoItens.setPontos_unitario(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO")));
            webPedidoItens.setPontos_total(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL")));
            webPedidoItens.setPontos_coeficiente(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE")));
            webPedidoItens.setPontos_cor(cursor.getString(cursor.getColumnIndex("PONTOS_COR")));
            webPedidoItens.setComissao_percentual(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL")));
            webPedidoItens.setComissao_valor(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR")));
            webPedidoItens.setValor_bonus_credor(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR")));
            webPedidoItens.setPerc_bonus_credor(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR")));
            webPedidoItens.setId_tabela_preco(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO")));
            webPedidoItens.setValor_desconto_per_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ORIG")));
            webPedidoItens.setValor_desconto_real_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ORIG")));
            webPedidoItens.setValor_desconto_per_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_PER_ADD_ORIG")));
            webPedidoItens.setValor_desconto_real_add_orig(cursor.getString(cursor.getColumnIndex("VALOR_DESCONTO_REAL_ADD_ORIG")));
            webPedidoItens.setId_tabela_preco_faixa_orig(cursor.getString(cursor.getColumnIndex("ID_TABELA_PRECO_FAIXA_ORIG")));
            webPedidoItens.setValor_total_orig(cursor.getString(cursor.getColumnIndex("VALOR_TOTAL_ORIG")));
            webPedidoItens.setPontos_unitario_orig(cursor.getString(cursor.getColumnIndex("PONTOS_UNITARIO_ORIG")));
            webPedidoItens.setPontos_coeficiente_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COEFICIENTE_ORIG")));
            webPedidoItens.setComissao_percentual_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_PERCENTUAL_ORIG")));
            webPedidoItens.setValor_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("VALOR_BONUS_CREDOR_ORIG")));
            webPedidoItens.setPerc_bonus_credor_orig(cursor.getString(cursor.getColumnIndex("PERC_BONUS_CREDOR_ORIG")));
            webPedidoItens.setComissao_valor_orig(cursor.getString(cursor.getColumnIndex("COMISSAO_VALOR_ORIG")));
            webPedidoItens.setPontos_total_orig(cursor.getString(cursor.getColumnIndex("PONTOS_TOTAL_ORIG")));
            webPedidoItens.setPontos_cor_orig(cursor.getString(cursor.getColumnIndex("PONTOS_COR_ORIG")));
            webPedidoItens.setProduto(this.listaProduto("SELECT * FROM TBL_PRODUTO WHERE ID_PRODUTO = '" + webPedidoItens.getId_produto() + "'").get(0));

            lista.add(webPedidoItens);
            System.gc();
        } while (cursor.moveToNext());
        cursor.close();
        System.gc();

        return lista;
    }

    public List<Usuario> listaUsuario(String SQL) {
        SQLiteDatabase banco = this.getReadableDatabase();
        List<Usuario> listaUsuario = new ArrayList<>();

        Cursor cursor;

        cursor = banco.rawQuery(SQL, null);
        cursor.moveToFirst();

        do {
            Usuario usuario = new Usuario();

            usuario.setId_usuario(cursor.getString(cursor.getColumnIndex("ID_USUARIO")));
            usuario.setAtivo(cursor.getString(cursor.getColumnIndex("ATIVO")));
            usuario.setNome_usuario(cursor.getString(cursor.getColumnIndex("NOME_USUARIO")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("LOGIN")));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
            usuario.setSenha_confirma(cursor.getString(cursor.getColumnIndex("SENHA_CONFIRMA")));
            usuario.setData_cadastro(cursor.getString(cursor.getColumnIndex("DATA_CADASTRO")));
            usuario.setUsuario_cadatro(cursor.getString(cursor.getColumnIndex("USUARIO_CADATRO")));
            usuario.setData_alterado(cursor.getString(cursor.getColumnIndex("DATA_ALTERADO")));
            usuario.setUsuario_alterou(cursor.getString(cursor.getColumnIndex("USUARIO_ALTEROU")));
            usuario.setAparece_cad_usuario(cursor.getString(cursor.getColumnIndex("APARECE_CAD_USUARIO")));
            usuario.setCliente_lista_todos(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_TODOS")));
            usuario.setCliente_lista_setor(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_SETOR")));
            usuario.setCliente_lista_representante(cursor.getString(cursor.getColumnIndex("CLIENTE_LISTA_REPRESENTANTE")));
            usuario.setPedido_lista_todos(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_TODOS")));
            usuario.setPedido_lista_setor(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_SETOR")));
            usuario.setPedido_lista_representante(cursor.getString(cursor.getColumnIndex("PEDIDO_LISTA_REPRESENTANTE")));
            usuario.setMensagem_lista_financeiro(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_FINANCEIRO")));
            usuario.setMensagem_lista_todos(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_TODOS")));
            usuario.setMensagem_lista_setor(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_SETOR")));
            usuario.setMensagem_lista_representante(cursor.getString(cursor.getColumnIndex("MENSAGEM_LISTA_REPRESENTANTE")));
            usuario.setOrcamento_lista_todos(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_TODOS")));
            usuario.setOrcamento_lista_setor(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_SETOR")));
            usuario.setOrcamento_lista_representante(cursor.getString(cursor.getColumnIndex("ORCAMENTO_LISTA_REPRESENTANTE")));
            usuario.setUsuario_lista_todos(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_TODOS")));
            usuario.setUsuario_lista_setor(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_SETOR")));
            usuario.setUsuario_lista_representante(cursor.getString(cursor.getColumnIndex("USUARIO_LISTA_REPRESENTANTE")));
            usuario.setExcluido(cursor.getString(cursor.getColumnIndex("EXCLUIDO")));
            usuario.setId_setor(cursor.getString(cursor.getColumnIndex("ID_SETOR")));
            usuario.setId_quando_vendedor(cursor.getString(cursor.getColumnIndex("ID_QUANDO_VENDEDOR")));
            usuario.setAparelho_id(cursor.getString(cursor.getColumnIndex("APARELHO_ID")));
            usuario.setIdEmpresaMultiDevice(cursor.getString(cursor.getColumnIndex("ID_EMPRESA_MULTI_DEVICE")));

            listaUsuario.add(usuario);
        } while (cursor.moveToNext());

        return listaUsuario;
    }
}
