package apoio;

import javax.swing.JOptionPane;

/**
 *
 * @author mateusrovedaa
 */
public class Mensagem {

    private static void mensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, tipo);
    }

    private static int mensagemConfirmacao(String titulo, String mensagem, int tipo, Object botoes[]) {
        int escolha = JOptionPane.showOptionDialog(null, mensagem, titulo,
                JOptionPane.DEFAULT_OPTION, tipo,
                null, botoes, botoes[0]);
        return escolha;
    }

    public static void aviso(String mensagem) {
        mensagem("Aviso", mensagem, JOptionPane.WARNING_MESSAGE);
    }

    public static void informacao(String mensagem) {
        mensagem("Informação", mensagem, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void erro(String mensagem) {
        mensagem("Erro", mensagem, JOptionPane.ERROR_MESSAGE);
    }

    public static int confirmacao(String mensagem) {
        Object botoes[] = {"Sim", "Não", "Cancelar"};
        return mensagemConfirmacao("Confirmação", mensagem, JOptionPane.QUESTION_MESSAGE, botoes);
    }
}
