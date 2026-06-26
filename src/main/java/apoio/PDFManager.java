package apoio;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class PDFManager {

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
    public @interface ColunaPDF {
        String  label   () default "";
        int     largura () default 0;
        boolean ocultar () default false;
    }

    private static final class PageConfig {
        final PDRectangle pageSize   = PDRectangle.A4;
        final float       margin     = 50f;
        final float       leading    = 14.5f;
        final int         fontSize   = 10;
        final float       pageWidth  = pageSize.getWidth();
        final float       pageHeight = pageSize.getHeight();
        final int         maxLines   = (int) ((pageHeight - 2 * margin) / leading);
    }

    public static void gerar(List<?> objetos, String caminhoArquivo) throws IOException {
        if (objetos == null || objetos.isEmpty())
            throw new IllegalArgumentException("Lista de objetos está vazia.");

        PageConfig cfg = new PageConfig();

        try (PDDocument doc = new PDDocument()) {
            PDType1Font font   = new PDType1Font(FontName.COURIER);
            Field[]     fields = camposVisiveis(objetos.get(0).getClass());

            int[]    colWidths = calcularLarguras(objetos, fields, cfg);
            String[] headers   = cabecalhos(fields);
            String   header    = formatRow(headers, colWidths);
            String   divisor   = "-".repeat(header.length());

            RenderState rs = iniciarPagina(doc, cfg, font, header, divisor);

            for (Object obj : objetos) {
                rs = renderizarObjeto(obj, fields, colWidths, doc, cfg, font,
                                      header, divisor, rs);
            }

            rs.out.endText();
            rs.out.close();
            doc.save(new File(caminhoArquivo));

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erro ao acessar atributos dos objetos: "
                                       + e.getMessage(), e);
        }
    }

    private static Field[] camposVisiveis(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> {
                         ColunaPDF ann = f.getAnnotation(ColunaPDF.class);
                         return ann == null || !ann.ocultar();
                     })
                     .peek(f -> f.setAccessible(true))
                     .toArray(Field[]::new);
    }

    private static String[] cabecalhos(Field[] fields) {
        return Arrays.stream(fields)
                     .map(f -> {
                         ColunaPDF ann = f.getAnnotation(ColunaPDF.class);
                         return (ann != null && !ann.label().isEmpty())
                                ? ann.label() : f.getName();
                     })
                     .toArray(String[]::new);
    }

    private static int[] calcularLarguras(List<?> objetos, Field[] fields,
                                          PageConfig cfg) throws IllegalAccessException {
        String[] headers = cabecalhos(fields);
        int[]    widths  = new int[fields.length];

        for (int i = 0; i < fields.length; i++)
            widths[i] = headers[i].length();

        for (Object obj : objetos) {
            for (int i = 0; i < fields.length; i++) {
                ColunaPDF ann = fields[i].getAnnotation(ColunaPDF.class);
                if (ann != null && ann.largura() > 0) {
                    widths[i] = ann.largura();
                    continue;
                }
                Object v = fields[i].get(obj);
                String s = v == null ? "" : v.toString();
                int maxPalavra = Arrays.stream(s.split("\\s+"))
                                       .mapToInt(String::length)
                                       .max().orElse(0);
                widths[i] = Math.max(widths[i], Math.min(s.length(), maxPalavra));
            }
        }

        int disponivel = (int) ((cfg.pageWidth - 2 * cfg.margin) / 6) - fields.length * 3;
        int total = Arrays.stream(widths).sum();
        if (total > disponivel) {
            for (int i = 0; i < widths.length; i++)
                widths[i] = Math.max(4, (int) Math.floor((double) widths[i] / total * disponivel));
        }
        return widths;
    }

    private static final class RenderState {
        PDPageContentStream out;
        int                 line;

        RenderState(PDPageContentStream out, int line) {
            this.out  = out;
            this.line = line;
        }
    }

    private static RenderState iniciarPagina(PDDocument doc, PageConfig cfg,
                                             PDFont font, String header,
                                             String divisor) throws IOException {
        PDPage page = new PDPage(cfg.pageSize);
        doc.addPage(page);
        PDPageContentStream out = abrirStream(doc, page, font, cfg);
        writeLine(out, header);
        writeLine(out, divisor);
        return new RenderState(out, 2);
    }

    private static RenderState verificarQuebra(RenderState rs, PDDocument doc,
                                               PageConfig cfg, PDFont font,
                                               String header,
                                               String divisor) throws IOException {
        if (rs.line >= cfg.maxLines - 2) {
            rs.out.endText();
            rs.out.close();
            PDPage page = new PDPage(cfg.pageSize);
            doc.addPage(page);
            PDPageContentStream out = abrirStream(doc, page, font, cfg);
            writeLine(out, header);
            writeLine(out, divisor);
            return new RenderState(out, 2);
        }
        return rs;
    }

    private static RenderState renderizarObjeto(Object obj, Field[] fields,
                                                int[] colWidths, PDDocument doc,
                                                PageConfig cfg, PDFont font,
                                                String header, String divisor,
                                                RenderState rs)
            throws IOException, IllegalAccessException {

        List<String[]> colPartes = new ArrayList<>();
        List<Boolean>  ehNumero  = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            Object v = fields[i].get(obj);
            String s = v == null ? "" : v.toString();
            colPartes.add(wrap(s, colWidths[i]));
            ehNumero.add(v instanceof Number);
        }

        int linhasNecessarias = colPartes.stream()
                                         .mapToInt(a -> a.length)
                                         .max().orElse(1);

        for (int l = 0; l < linhasNecessarias; l++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < fields.length; c++) {
                String txt = l < colPartes.get(c).length ? colPartes.get(c)[l] : "";
                sb.append(ehNumero.get(c)
                          ? fixRight(txt, colWidths[c])
                          : fixLeft(txt, colWidths[c]));
                sb.append(" | ");
            }
            writeLine(rs.out, sb.toString());
            rs.line++;
            rs = verificarQuebra(rs, doc, cfg, font, header, divisor);
        }

        writeLine(rs.out, divisor);
        rs.line++;
        rs = verificarQuebra(rs, doc, cfg, font, header, divisor);

        return rs;
    }

    private static String formatRow(String[] vals, int[] colWidths) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vals.length; i++)
            sb.append(fixLeft(vals[i], colWidths[i])).append(" | ");
        return sb.toString();
    }

    private static String fixLeft(String txt, int w) {
        if (txt.length() > w) return txt.substring(0, w);
        return String.format("%-" + w + "s", txt);
    }

    private static String fixRight(String txt, int w) {
        if (txt.length() > w) return txt.substring(0, w);
        return String.format("%" + w + "s", txt);
    }

    private static String[] wrap(String txt, int w) {
        if (txt == null || txt.isEmpty()) return new String[]{""};

        List<String>  linhas = new ArrayList<>();
        StringBuilder atual  = new StringBuilder();

        for (String palavra : txt.split("\\s+")) {
            while (palavra.length() > w) {
                if (atual.length() > 0) { linhas.add(atual.toString()); atual.setLength(0); }
                linhas.add(palavra.substring(0, w));
                palavra = palavra.substring(w);
            }
            if (palavra.isEmpty()) continue;

            if (atual.length() == 0) {
                atual.append(palavra);
            } else if (atual.length() + 1 + palavra.length() <= w) {
                atual.append(' ').append(palavra);
            } else {
                linhas.add(atual.toString());
                atual.setLength(0);
                atual.append(palavra);
            }
        }
        if (!atual.isEmpty()) linhas.add(atual.toString());
        return linhas.isEmpty() ? new String[]{""} : linhas.toArray(String[]::new);
    }

    private static PDPageContentStream abrirStream(PDDocument doc, PDPage page,
                                                   PDFont font,
                                                   PageConfig cfg) throws IOException {
        PDPageContentStream s = new PDPageContentStream(doc, page);
        s.setFont(font, cfg.fontSize);
        s.beginText();
        s.setLeading(cfg.leading);
        s.newLineAtOffset(cfg.margin, cfg.pageHeight - cfg.margin);
        return s;
    }

    private static void writeLine(PDPageContentStream s, String txt) throws IOException {
        s.showText(txt);
        s.newLine();
    }
}
