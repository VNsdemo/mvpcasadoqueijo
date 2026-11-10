package br.com.casadoqueijo.pdf;

import br.com.casadoqueijo.model.Produto;
import br.com.casadoqueijo.pdf.PdfDocumento.Fonte;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RelatorioCriticosPdf {

    private static final double MARGEM = 40;
    private static final double ALTURA_LINHA = 22;
    private static final double MARGEM_INFERIOR = 50;
    private static final double LARGURA_UTIL = PdfDocumento.LARGURA_PAGINA - 2 * MARGEM; // 515

    private static final String[] TITULOS = {"Produto", "Categoria", "Fornecedor", "Atual", "Mínimo", "Falta", "Un."};
    private static final double[] LARGURAS = {140, 75, 115, 45, 45, 45, 50}; // soma = 515
    private static final boolean[] ALINHA_DIREITA = {false, false, false, true, true, true, false};
    private static final double PADDING = 5;

    public static byte[] gerar(List<Produto> criticos, LocalDateTime geradoEm) {
        List<Produto> lista = new ArrayList<>(criticos);
        lista.sort(Comparator.comparing(Produto::fornecedorNome, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Produto::nome, String.CASE_INSENSITIVE_ORDER));

        PdfDocumento pdf = new PdfDocumento();
        pdf.novaPagina();
        double y = desenharCabecalho(pdf, geradoEm);

        if (lista.isEmpty()) {
            pdf.texto(MARGEM, y, Fonte.NORMAL, 11, 40, 40, 40, "Nenhum produto abaixo do estoque mínimo. Tudo em ordem!");
        } else {
            pdf.texto(MARGEM, y, Fonte.NEGRITO, 11, 40, 40, 40,
                    lista.size() + (lista.size() == 1 ? " produto abaixo" : " produtos abaixo") + " do estoque mínimo");
            pdf.texto(MARGEM, y - 15, Fonte.NORMAL, 8.5, 110, 110, 110,
                    "Falta = estoque mínimo - quantidade atual. Lista ordenada por fornecedor.");
            y -= 35;

            y = desenharTitulosDaTabela(pdf, y);
            int indice = 0;
            for (Produto p : lista) {
                if (y - ALTURA_LINHA < MARGEM_INFERIOR) {
                    pdf.novaPagina();
                    y = desenharTitulosDaTabela(pdf, PdfDocumento.ALTURA_PAGINA - MARGEM);
                }
                desenharLinha(pdf, y, p, indice++);
                y -= ALTURA_LINHA;
            }
        }

        int total = pdf.numeroPaginas();
        for (int i = 0; i < total; i++) {
            pdf.irParaPagina(i);
            String rodape = "Página " + (i + 1) + " de " + total;
            double x = (PdfDocumento.LARGURA_PAGINA - pdf.largura(rodape, Fonte.NORMAL, 8)) / 2;
            pdf.texto(x, 28, Fonte.NORMAL, 8, 130, 130, 130, rodape);
        }
        return pdf.gerarBytes();
    }

    private static double desenharCabecalho(PdfDocumento pdf, LocalDateTime geradoEm) {
        double altura = 70;
        pdf.retangulo(0, PdfDocumento.ALTURA_PAGINA - altura, PdfDocumento.LARGURA_PAGINA, altura, 74, 44, 42);
        pdf.texto(MARGEM, PdfDocumento.ALTURA_PAGINA - 33, Fonte.NEGRITO, 17, 255, 255, 255, "Casa do Queijo de Coqueiral");
        pdf.texto(MARGEM, PdfDocumento.ALTURA_PAGINA - 53, Fonte.NORMAL, 11, 255, 255, 255, "Relatório de Estoque Crítico");

        String data = "Gerado em " + geradoEm.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        double x = PdfDocumento.LARGURA_PAGINA - MARGEM - pdf.largura(data, Fonte.NORMAL, 9);
        pdf.texto(x, PdfDocumento.ALTURA_PAGINA - 53, Fonte.NORMAL, 9, 255, 255, 255, data);
        return PdfDocumento.ALTURA_PAGINA - altura - 30;
    }

    private static double desenharTitulosDaTabela(PdfDocumento pdf, double yTopo) {
        pdf.retangulo(MARGEM, yTopo - ALTURA_LINHA, LARGURA_UTIL, ALTURA_LINHA, 74, 44, 42);
        double x = MARGEM;
        for (int c = 0; c < TITULOS.length; c++) {
            escreverCelula(pdf, x, yTopo - 15, c, Fonte.NEGRITO, 8.5, 255, 255, 255, TITULOS[c]);
            x += LARGURAS[c];
        }
        return yTopo - ALTURA_LINHA;
    }

    private static void desenharLinha(PdfDocumento pdf, double yTopo, Produto p, int indice) {
        if (indice % 2 == 1) pdf.retangulo(MARGEM, yTopo - ALTURA_LINHA, LARGURA_UTIL, ALTURA_LINHA, 245, 245, 245); // zebra
        pdf.linha(MARGEM, yTopo - ALTURA_LINHA, MARGEM + LARGURA_UTIL, yTopo - ALTURA_LINHA, 0.5, 225, 225, 225);

        double falta = Math.max(0, p.estoqueMinimo() - p.quantidade());
        String[] valores = {p.nome(), p.categoria(), p.fornecedorNome(), formatar(p.quantidade()),
                formatar(p.estoqueMinimo()), formatar(falta), p.unidade()};

        double x = MARGEM;
        double base = yTopo - 15;
        for (int c = 0; c < valores.length; c++) {
            boolean destaque = (c == 5);
            escreverCelula(pdf, x, base, c, destaque ? Fonte.NEGRITO : Fonte.NORMAL, 9,
                    destaque ? 176 : 40, destaque ? 42 : 40, destaque ? 55 : 40, valores[c]);
            x += LARGURAS[c];
        }
    }

    private static void escreverCelula(PdfDocumento pdf, double xColuna, double base, int c, Fonte fonte, double tamanho,
                                       int r, int g, int b, String texto) {
        double espaco = LARGURAS[c] - 2 * PADDING;
        String t = texto == null ? "" : texto;
        if (pdf.largura(t, fonte, tamanho) > espaco) {
            while (t.length() > 1 && pdf.largura(t + "...", fonte, tamanho) > espaco) t = t.substring(0, t.length() - 1);
            t = t.trim() + "...";
        }
        double x = ALINHA_DIREITA[c] ? xColuna + LARGURAS[c] - PADDING - pdf.largura(t, fonte, tamanho) : xColuna + PADDING;
        pdf.texto(x, base, fonte, tamanho, r, g, b, t);
    }

    static String formatar(double v) {
        BigDecimal d = BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
        if (d.scale() < 0) d = d.setScale(0);
        return d.toPlainString().replace('.', ',');
    }
}
