package idwall.desafio.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Formata o texto, limitando o tamanho máximo de cada linha.
 *
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter() {
    }

    public IdwallFormatter(Integer limit) {
        super(limit);
    }

    /**
     * Formata o texto, limitando o tamanho máximo das linhas conforme o parâmetro <code>limit</code>.
     *
     * @param text Texto a formatar.
     * @return Texto formatado.
     */
    @Override
    public String format(String text) {
        return Stream.of(text.split("\\R"))
                .map(line -> getLimitedLineWordList(line).stream()
                    .map(this::getLimitedLine)
                    .collect(Collectors.joining("\r\n")))
                .collect(Collectors.joining("\r\n"));
    }

    /**
     * Retorna a nova linha referente às palavras, separandoas com um espaço.
     *
     * @param limitedLineWords Array com as palavras da linha.
     * @return Linha com o tamanho limitado.
     */
    protected String getLimitedLine(String[] limitedLineWords) {
        return String.join(" ", limitedLineWords);
    }

    /**
     * Separa as palavras de uma linha original em segmentos que cabem numa linha com tamanho limitado.
     *
     * @param line Linha original, com tamanho arbitrário.
     * @return Lista de arrays de palavras, cada array com as palavras referentes à nova linha que será formada.
     */
    private List<String[]> getLimitedLineWordList(String line) {
        // o processo assume que:
        // 1. As palavras são sempre separadas por apenas um espaço originalmente.
        // 2. Não haverá palavras maiores que o limite do tamanho da linha.
        String[] words = line.split("\\s+");
        int wordOffset = 0;
        List<String[]> limitedLineWordList = new ArrayList<>();
        while (wordOffset < words.length) {
            int nextWordOffset = getWordOffsetForNextLine(words, wordOffset);
            limitedLineWordList.add(Arrays.copyOfRange(words, wordOffset, nextWordOffset));
            wordOffset = nextWordOffset;
        }
        return limitedLineWordList;
    }

    /**
     * Calcula o índice da próxima palavra a partir da qual a linha deverá ser quebrada.
     *
     * @param words      Array com todas as palavras da linha original
     * @param wordOffset Offset da palavra a partir da qual deve-se calcular a linha limitada.
     * @return Offset da palavra a partir da qual a linha deve ser quebrada.
     */
    private int getWordOffsetForNextLine(String[] words, int wordOffset) {
        int textOffset = 0;
        int delimiterSize = 0;
        // itera até as palavras acabarem, ou enquanto a linha não ultrapassar o tamanho limite.
        while (wordOffset < words.length && textOffset + words[wordOffset].length() + delimiterSize <= getLimit()) {
            // incrementa o tamanho da linha, e um caractere adicional para o espaço (exceto na primeira palavra).
            textOffset += words[wordOffset++].length() + delimiterSize;
            // depois da primeira palavra, já considera um espaço delimitador adicional no tamanho da linha.
            delimiterSize = 1;
        }
        return wordOffset;
    }
}
