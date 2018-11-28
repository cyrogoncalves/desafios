package idwall.desafio.string;

import java.util.stream.Stream;

/**
 * Formata o texto, limitando e justificando o tamanho máximo de cada linha.
 *
 * Created by Cyro Gonçalves on 26/11/2018.
 */
public class IdwallJustifiedFormatter extends IdwallFormatter {

    public IdwallJustifiedFormatter() {
    }

    public IdwallJustifiedFormatter(Integer limit) {
        super(limit);
    }

    /**
     * Retorna a linha justificada, aumentando o tamanho dos delimitadores conforme a necessidade.
     * @param limitedLineWords Array com as palavras da linha.
     * @return Linha justificada.
     */
    @Override
    protected String getLimitedLine(String[] limitedLineWords) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int spareLength = getLimit() - Stream.of(limitedLineWords).mapToInt(String::length).sum();
        for (String word : limitedLineWords) {
            int delimiterLength = getDelimiterLength(i++, spareLength, limitedLineWords.length - 1);
            sb.append(new String(new char[delimiterLength]).replace('\0', ' ')).append(word);
        }
        return sb.toString();
    }

    /**
     * Define a quantidades de espaços serão usados em um determinado delimitador, para justificar o texto.
     * Considera-se que os espaços deverão ser distribuídos da forma mais uniforme possível, então os espaços
     * "irregulares", que deixam alguns delimitadores maiores que outros, serão preenchidos nos delimitadores ímpares
     * primeiro, da esquerda para a direita, e quando todos os ímpares estiverem preenchidos serão preenchidos nos
     * pares, da direita para a esquerda. Por exemplo, para 7 espaços adicionais e 5 delimitadores, a quantidade de
     * espaços deve ser [0, 3, 2, 3, 2, 2].
     *
     * @param position       Posição da palavra, à qual será adicionada o delimitador à esquerda.
     * @param spareLength    Número de espaços adicionais que precisam ser adicionados na linha para justificá-la.
     * @param delimiterCount Quantidade de delimitadores na linha (número de palavras menos um).
     * @return Número de espaços para o delimitador. Por exemplo, 2 se o limitador deverá ser "  ".
     */
    private int getDelimiterLength(int position, int spareLength, int delimiterCount) {
        // 7 -> 0, 2, 1, 2, 1, 1
        // 4 -> 0, 1, 0, 1, 0, 0
        if (position == 0) {
            // nenhum espaço antes da primeira palavra.
            return 0;
        }
        // faz a distribuição inicial dos espaços com uma divisão inteira
        int extraSpaceCount = spareLength / delimiterCount;
        // os espaços "irregulares" serão distribuidos nos delimitadores. A quantidade deles é o resto da divisão.
        int remainder = spareLength % delimiterCount;
        if (position % 2 == 1 && remainder * 2 > position) {
            // posição ímpar. Este limitador terá um espaço a mais.
            extraSpaceCount++;
        } else {
            // posição par. Define se este limitador terá um espaço a mais.
            // o número de delimitadores ímpares é a metade do número de delimitadores arredondada para cima.
            int oddDelimiterCount = (int) Math.ceil((double) delimiterCount / 2);
            // preenche da direita para a esquerda, se todas as posições ímpares estiverem preenchidas.
            if (remainder > oddDelimiterCount && spareLength - position <= remainder) {
                extraSpaceCount++;
            }
        }
        return extraSpaceCount;
    }
}
