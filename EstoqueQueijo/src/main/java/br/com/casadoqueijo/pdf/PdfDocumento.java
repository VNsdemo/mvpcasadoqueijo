package br.com.casadoqueijo.pdf;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

    public class PdfDocumento {

    public static final double LARGURA_PAGINA = 595;
    public static final double ALTURA_PAGINA = 842;

    public enum Fonte { NORMAL, NEGRITO }

    private static final int[] LARGURA_NORMAL = {278, 278, 355, 556, 556, 889, 667, 191, 333, 333, 389, 584, 278, 333, 278, 278,
            556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 278, 278, 584, 584, 584, 556, 1015, 667, 667, 722, 722, 667, 611,
            778, 722, 278, 500, 667, 556, 833, 722, 778, 667, 778, 722, 667, 611, 722, 667, 944, 667, 667, 611, 278, 278, 278,
            469, 556, 333, 556, 556, 500, 556, 556, 278, 556, 556, 222, 222, 500, 222, 833, 556, 556, 556, 556, 333, 500, 278,
            556, 500, 722, 500, 500, 500, 334, 260, 334, 584};
    private static final int[] LARGURA_NEGRITO = {278, 333, 474, 556, 556, 889, 722, 238, 333, 333, 389, 584, 278, 333, 278, 278,
            556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 333, 333, 584, 584, 584, 611, 975, 722, 722, 722, 722, 667, 611,
            778, 722, 278, 556, 722, 611, 833, 722, 778, 667, 778, 722, 667, 611, 722, 667, 944, 667, 667, 611, 333, 278, 333,
            584, 556, 333, 556, 611, 556, 611, 556, 333, 611, 611, 278, 278, 556, 278, 889, 611, 611, 611, 611, 389, 556, 333,
            611, 556, 778, 556, 556, 500, 389, 280, 389, 584};


    private static final Charset WIN_ANSI = Charset.forName("windows-1252");

    private final List<StringBuilder> paginas = new ArrayList<>();
    private StringBuilder atual;


    public void novaPagina() {
        atual = new StringBuilder();
        paginas.add(atual);
    }

    public int numeroPaginas() { return paginas.size(); }


    public void irParaPagina(int indice) { atual = paginas.get(indice); }


    public void retangulo(double x, double y, double largura, double altura, int r, int g, int b) {
        atual.append(cor(r, g, b, "rg")).append(' ')
                .append(num(x)).append(' ').append(num(y)).append(' ')
                .append(num(largura)).append(' ').append(num(altura)).append(" re f\n");
    }

    public void linha(double x1, double y1, double x2, double y2, double espessura, int r, int g, int b) {
        atual.append(cor(r, g, b, "RG")).append(' ').append(num(espessura)).append(" w ")
                .append(num(x1)).append(' ').append(num(y1)).append(" m ")
                .append(num(x2)).append(' ').append(num(y2)).append(" l S\n");
    }

    public void texto(double x, double y, Fonte fonte, double tamanho, int r, int g, int b, String texto) {
        atual.append("BT ").append(fonte == Fonte.NEGRITO ? "/F2 " : "/F1 ").append(num(tamanho)).append(" Tf ")
                .append(cor(r, g, b, "rg")).append(' ')
                .append(num(x)).append(' ').append(num(y)).append(" Td (")
                .append(escapar(texto)).append(") Tj ET\n");
    }

    public double largura(String texto, Fonte fonte, double tamanho) {
        int[] tabela = fonte == Fonte.NEGRITO ? LARGURA_NEGRITO : LARGURA_NORMAL;
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        double total = 0;
        for (char c : semAcento.toCharArray()) {
            if (c >= 32 && c <= 126) total += tabela[c - 32];
            else if (c == '\u2014' || c == '\u2026') total += 1000; // — e …
            else total += 556;                                       // qualquer outro: largura média
        }
        return total * tamanho / 1000.0;
    }


    public byte[] gerarBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        List<Integer> posicoes = new ArrayList<>(); // posicoes.get(i) = byte onde começa o objeto i+1
        escrever(out, "%PDF-1.4\n%\u00e2\u00e3\u00cf\u00d3\n");

        int n = paginas.size();
        StringBuilder filhos = new StringBuilder();
        for (int i = 0; i < n; i++) filhos.append(6 + 2 * i).append(" 0 R ");

        objeto(out, posicoes, "<< /Type /Catalog /Pages 2 0 R >>");                                   // 1
        objeto(out, posicoes, "<< /Type /Pages /Kids [" + filhos + "] /Count " + n + " >>");          // 2
        objeto(out, posicoes, "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>");      // 3
        objeto(out, posicoes, "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>"); // 4
        objeto(out, posicoes, "<< /Title (Relatorio de Estoque Critico) /Producer (Casa do Queijo) >>");                // 5

        for (int i = 0; i < n; i++) {
            int numeroConteudo = 7 + 2 * i;
            objeto(out, posicoes, "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " + (int) LARGURA_PAGINA + " " + (int) ALTURA_PAGINA
                    + "] /Resources << /Font << /F1 3 0 R /F2 4 0 R >> >> /Contents " + numeroConteudo + " 0 R >>");
            byte[] conteudo = paginas.get(i).toString().getBytes(StandardCharsets.ISO_8859_1);
            posicoes.add(out.size());
            escrever(out, numeroConteudo + " 0 obj\n<< /Length " + conteudo.length + " >>\nstream\n");
            out.write(conteudo, 0, conteudo.length);
            escrever(out, "\nendstream\nendobj\n");
        }

        int inicioXref = out.size();
        escrever(out, "xref\n0 " + (posicoes.size() + 1) + "\n0000000000 65535 f \n");
        for (int pos : posicoes) escrever(out, String.format(Locale.ROOT, "%010d 00000 n \n", pos));
        escrever(out, "trailer\n<< /Size " + (posicoes.size() + 1) + " /Root 1 0 R /Info 5 0 R >>\nstartxref\n"
                + inicioXref + "\n%%EOF\n");
        return out.toByteArray();
    }

    private void objeto(ByteArrayOutputStream out, List<Integer> posicoes, String corpo) {
        posicoes.add(out.size());
        escrever(out, posicoes.size() + " 0 obj\n" + corpo + "\nendobj\n");
    }

    private void escrever(ByteArrayOutputStream out, String texto) {
        byte[] bytes = texto.getBytes(StandardCharsets.ISO_8859_1);
        out.write(bytes, 0, bytes.length);
    }

        private String num(double v) { return String.format(Locale.ROOT, "%.2f", v); }

    private String cor(int r, int g, int b, String operador) {
        return num(r / 255.0) + " " + num(g / 255.0) + " " + num(b / 255.0) + " " + operador;
    }

    private String escapar(String texto) {
        StringBuilder sb = new StringBuilder();
        for (byte b : texto.getBytes(WIN_ANSI)) {
            int c = b & 0xFF;
            if (c == '(' || c == ')' || c == '\\') sb.append('\\').append((char) c);
            else if (c < 32 || c > 126) sb.append(String.format(Locale.ROOT, "\\%03o", c));
            else sb.append((char) c);
        }
        return sb.toString();
    }
}
