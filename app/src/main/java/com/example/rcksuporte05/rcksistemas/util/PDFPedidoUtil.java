package com.example.rcksuporte05.rcksistemas.util;

import android.app.Activity;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Build;
import android.os.Environment;

import com.example.rcksuporte05.rcksistemas.DAO.CategoriaDAO;
import com.example.rcksuporte05.rcksistemas.DAO.DBHelper;
import com.example.rcksuporte05.rcksistemas.DAO.WebPedidoItensDAO;
import com.example.rcksuporte05.rcksistemas.Helper.UsuarioHelper;
import com.example.rcksuporte05.rcksistemas.R;
import com.example.rcksuporte05.rcksistemas.model.Categoria;
import com.example.rcksuporte05.rcksistemas.model.Cliente;
import com.example.rcksuporte05.rcksistemas.model.CondicoesPagamento;
import com.example.rcksuporte05.rcksistemas.model.Operacao;
import com.example.rcksuporte05.rcksistemas.model.WebPedido;
import com.example.rcksuporte05.rcksistemas.model.WebPedidoItens;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PDFPedidoUtil extends PdfPageEventHelper {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);
    private static Font normalBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static Font cabFont = new Font(Font.FontFamily.TIMES_ROMAN, 6,
            Font.BOLD);
    private WebPedido webPedido;
    private Activity activity;

    public PDFPedidoUtil(WebPedido webPedido, Activity activity) {
        this.webPedido = webPedido;
        this.activity = activity;
    }

    public File criandoPdf() {
        try {
            String filename;
            if (webPedido.getId_web_pedido_servidor() != null)
                filename = "pedido " + webPedido.getId_web_pedido_servidor() + ".pdf";
            else
                filename = "pedido " + webPedido.getId_web_pedido() + ".pdf";
            Document document = new Document(PageSize.A4);
            document.setMargins(15, 15, 15, 15);
            String path;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            else
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + activity.getString(R.string.app_name) + "/Pedidos";
            File dir = new File(path, filename);
            if (!dir.exists()) {
                dir.getParentFile().mkdirs();
            }
            FileOutputStream fOut = new FileOutputStream(dir);
            fOut.flush();
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            document.open();
            montandoRelatorio(document, writer);
            document.close();

            return dir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void montandoRelatorio(Document document, PdfWriter writer) throws DocumentException {
        Paragraph titulo;
        if (webPedido.getId_web_pedido_servidor() != null)
            titulo = new Paragraph("Espelho do Pedido " + webPedido.getId_web_pedido_servidor() + "\n\n", catFont);
        else
            titulo = new Paragraph("Espelho do Pedido " + webPedido.getId_web_pedido() + "\n\n", catFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph p = new Paragraph();
        p.setFont(normalBold);
        p.setAlignment(Element.ALIGN_LEFT);
        p.add("Cliente:       ");
        Chunk cliente = new Chunk(webPedido.getCadastro().getNome_cadastro() + "\n");
        cliente.setFont(normalFont);
        p.add(cliente);
        p.add("Endereço:   ");
        Chunk endereço = new Chunk(webPedido.getCadastro().getEndereco() + "\n");
        endereço.setFont(normalFont);
        p.add(endereço);
        p.add("Bairro:        ");
        Chunk bairro = new Chunk(webPedido.getCadastro().getEndereco_bairro() + "\n");
        bairro.setFont(normalFont);
        p.add(bairro);
        p.add("Munícipio: ");
        Chunk municipio = new Chunk(webPedido.getCadastro().getNome_municipio() + "\n");
        municipio.setFont(normalFont);
        p.add(municipio);
        p.add("Complemento: ");
        Chunk complemento = new Chunk("\n");
        if (webPedido.getCadastro().getEndereco_complemento() != null && !webPedido.getCadastro().getEndereco_complemento().trim().isEmpty())
            complemento = new Chunk(webPedido.getCadastro().getEndereco_complemento() + "\n");
        p.add(complemento);
        p.add("Condição de Pagamento: ");
        Chunk condPag = new Chunk(buscaCondPag().getNome_condicao());
        p.add(condPag);

        Paragraph p2 = new Paragraph();
        p2.setFont(normalBold);
        p2.setAlignment(Element.ALIGN_RIGHT);
        p2.add("CNPJ/CPF: ");
        Chunk cpf;
        switch (webPedido.getCadastro().getPessoa_f_j()) {
            case "J":
                cpf = new Chunk(MascaraUtil.mascaraCNPJ(webPedido.getCadastro().getCpf_cnpj()) + "\n");
                break;
            case "F":
                cpf = new Chunk(MascaraUtil.mascaraCPF(webPedido.getCadastro().getCpf_cnpj()) + "\n");
                break;
            default:
                cpf = new Chunk(webPedido.getCadastro().getCpf_cnpj() + "\n");
                break;
        }
        cpf.setFont(normalFont);
        p2.add(cpf);
        p2.add("IE: ");
        Chunk IE = new Chunk("\n");
        if (webPedido.getCadastro().getInscri_estadual() != null)
            IE = new Chunk(webPedido.getCadastro().getInscri_estadual() + "\n");
        IE.setFont(normalFont);
        p2.add(IE);
        p2.add("CEP: ");
        Chunk cep = new Chunk(MascaraUtil.mascaraCep(webPedido.getCadastro().getEndereco_cep()) + "\n");
        cep.setFont(normalFont);
        p2.add(cep);
        p2.add("Telefone: ");
        Chunk telefone = new Chunk(validaTelefone(webPedido.getCadastro()) + "\n");
        telefone.setFont(normalFont);
        p2.add(telefone);
        p2.add("Categoria: ");
        Chunk categoria;
        try {
            categoria = new Chunk(buscaCategoria(webPedido.getCadastro().getIdCategoria()) + "\n");
        } catch (CursorIndexOutOfBoundsException e) {
            categoria = new Chunk("");
        }
        p2.add(categoria);

        Rectangle rect = new Rectangle(23, 800, 400, 690);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, false, p);
        rect = new Rectangle(400, 800, 575, 690);
        addColumn(writer, rect, false, Element.ALIGN_RIGHT, false, p2);

        Paragraph operacao = new Paragraph("    Operação \n                ", cabFont);
        Chunk nomeOperacao = new Chunk(buscaOperacao().getNatureza_operacao(), normalFont);
        operacao.add(nomeOperacao);

        rect = new Rectangle(23, 685, 230, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, operacao);

        Paragraph dadosVendedor = new Paragraph("   Vendedor \n                ", cabFont);
        Chunk infoVendedo = new Chunk(UsuarioHelper.getUsuario().getNome_usuario(), normalFont);
        dadosVendedor.add(infoVendedo);

        rect = new Rectangle(230, 685, 385, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dadosVendedor);

        Paragraph dataEmissao = new Paragraph("  Data emissão \n                ", cabFont);
        Chunk infoDataEmissao = null;
        try {
            infoDataEmissao = new Chunk(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_emissao())), normalFont);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dataEmissao.add(infoDataEmissao);

        rect = new Rectangle(385, 685, 480, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dataEmissao);

        Paragraph dataEntrega = new Paragraph("   Data entrega \n                ", cabFont);
        Chunk infoDataEntrega = null;
        try {
            infoDataEntrega = new Chunk(new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(webPedido.getData_prev_entrega())), normalFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataEntrega.add(infoDataEntrega);

        rect = new Rectangle(480, 685, 575, 645);
        addColumn(writer, rect, false, Element.ALIGN_LEFT, true, dataEntrega);

        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));

        Paragraph produtos = new Paragraph("Dados dos produtos", smallBold);
        document.add(produtos);
        document.add(new Paragraph("           "));

        PdfPTable tabelaProdutos = new PdfPTable(new float[]{40f, 140f, 30f, 26f, 31f, 30f, 40f, 40f});
        tabelaProdutos.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.setTotalWidth(550);
        tabelaProdutos.setLockedWidth(true);

        PdfPCell c1 = new PdfPCell(new Phrase("Código Produto", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Descrição do Produto", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Unid.", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Qtd.", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor Unitário", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Preço pago", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Desconto total", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        c1 = new PdfPCell(new Phrase("Valor total", smallBold));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabelaProdutos.addCell(c1);

        if (webPedido.getWebPedidoItens() == null || webPedido.getWebPedidoItens().size() <= 0)
            webPedido.setWebPedidoItens(listaPedidoItens());

        for (WebPedidoItens webPedidoItens : webPedido.getWebPedidoItens()) {
            PdfPCell celulaProd = null;
            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getId_produto(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                tabelaProdutos.addCell(new Phrase(webPedidoItens.getNome_produto(), smallFont));
            } catch (Exception e) {
                tabelaProdutos.addCell(new Phrase(""));
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getUnidade(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(webPedidoItens.getQuantidade(), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                tabelaProdutos.addCell(new PdfPCell(new Phrase("")));
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_unitario()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_unitario() - (Float.parseFloat(webPedidoItens.getValor_desconto_real()) / Float.parseFloat(webPedidoItens.getQuantidade()))), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_desconto_real()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
            try {
                celulaProd = new PdfPCell(new Phrase(MascaraUtil.duasCasaDecimal(webPedidoItens.getValor_total()), smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
            } catch (Exception e) {
                celulaProd = new PdfPCell(new Phrase("0.00", smallFont));
                celulaProd.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaProdutos.addCell(celulaProd);
                e.printStackTrace();
            }
        }

        tabelaProdutos.setHeaderRows(1);
        document.add(tabelaProdutos);

        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));
        document.add(new Paragraph("           "));

        Paragraph totalProdutos = new Paragraph("    Valor total dos Produtos \n                                             ", cabFont);
        try {
            Chunk produto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_produtos()), normalFont);
            totalProdutos.add(produto);
            rect = new Rectangle(425, 145, 575, 110);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalProdutos);
        } catch (Exception e) {
            Chunk produto = new Chunk("R$0,00", normalFont);
            totalProdutos.add(produto);
            rect = new Rectangle(425, 145, 575, 110);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalProdutos);
            e.printStackTrace();
        }


        Paragraph totalDescontos = new Paragraph("    Valor total dos Descontos \n                                             ", cabFont);
        try {
            Chunk desconto = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_desconto()), normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
        } catch (Exception e) {
            Chunk desconto = new Chunk("R$0,00", normalFont);
            totalDescontos.add(desconto);
            rect = new Rectangle(425, 110, 575, 75);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalDescontos);
            e.printStackTrace();
        }


        Paragraph totalPedido = new Paragraph("    Valor total do Pedido \n                                             ", cabFont);
        try {
            Chunk pedido = new Chunk(MascaraUtil.mascaraReal(webPedido.getValor_total()), normalFont);
            totalPedido.add(pedido);
            rect = new Rectangle(425, 75, 575, 40);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalPedido);
        } catch (Exception e) {
            Chunk pedido = new Chunk("R$0,00", normalFont);
            totalPedido.add(pedido);
            rect = new Rectangle(425, 75, 575, 40);
            addColumn(writer, rect, false, Element.ALIGN_LEFT, true, totalPedido);
            e.printStackTrace();
        }


        rect = new Rectangle(23, 145, 425, 40);
        try {
            addColumn(writer, rect, false, Element.ALIGN_BOTTOM, true, new Paragraph(new Chunk(webPedido.getObservacoes(), normalFont)));
        } catch (NullPointerException e) {
            addColumn(writer, rect, false, Element.ALIGN_BOTTOM, true, new Paragraph(new Chunk("", normalFont)));
        }
    }

    private Operacao buscaOperacao() {
        DBHelper db = new DBHelper(activity);
        return db.listaOperacao("SELECT * FROM TBL_OPERACAO_ESTOQUE WHERE ID_OPERACAO = " + webPedido.getId_operacao() + ";").get(0);
    }

    private Categoria buscaCategoria(int idCategoria) {
        DBHelper db = new DBHelper(activity);
        CategoriaDAO categoriaDAO = new CategoriaDAO(db);
        return categoriaDAO.listaCategoriaPorId(idCategoria);
    }

    private CondicoesPagamento buscaCondPag() {
        DBHelper db = new DBHelper(activity);
        return db.listaCondicoesPagamento("SELECT * FROM TBL_CONDICOES_PAG_CAB WHERE ID_CONDICAO = " + webPedido.getId_condicao_pagamento() + ";").get(0);
    }

    private List<WebPedidoItens> listaPedidoItens() {
        DBHelper db = new DBHelper(activity);
        WebPedidoItensDAO itens = new WebPedidoItensDAO(db);
        return itens.listaWebPedidoItens("SELECT * FROM TBL_WEB_PEDIDO_ITENS WHERE ID_PEDIDO = " + webPedido.getId_web_pedido());
    }

    private String validaTelefone(Cliente cliente) {
        if (cliente.getTelefone_principal() != null && !cliente.getTelefone_principal().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_principal().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_principal()));
        } else if (cliente.getTelefone_dois() != null && !cliente.getTelefone_dois().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_dois().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_dois()));
        } else if (cliente.getTelefone_tres() != null && !cliente.getTelefone_tres().replaceAll("[^0-9]", "").trim().isEmpty() && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() >= 8 && cliente.getTelefone_tres().replaceAll("[^0-9]", "").length() <= 11) {
            return (formataTelefone(cliente.getTelefone_tres()));
        } else {
            return ("(  )     -    ");
        }
    }

    private String formataTelefone(String telefone) {
        telefone = telefone.trim().replaceAll("[^0-9]", "");
        switch (telefone.length()) {
            case 10:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
            case 11:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7, 11);
            case 9:
                return telefone.substring(0, 5) + "-" + telefone.substring(5, 9);
            case 8:
                return telefone.substring(0, 4) + "-" + telefone.substring(4, 8);
            default:
                return telefone;
        }
    }

    private void addColumn(PdfWriter writer, Rectangle rect, boolean useAscender, int align, Boolean borda, Paragraph p) throws DocumentException {
        rect.setBorder(Rectangle.BOX);
        if (borda) {
            rect.setBorderWidth(0.5f);
            rect.setBorderColor(BaseColor.BLACK);
        }
        PdfContentByte cb = writer.getDirectContent();
        cb.rectangle(rect);
        ColumnText ct = new ColumnText(cb);
        ct.setSimpleColumn(rect);
        ct.setUseAscender(useAscender);
        ct.addText(p);
        ct.setAlignment(align);
        ct.go();
    }
}
