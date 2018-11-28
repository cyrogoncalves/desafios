package idwall.desafio.string;

/**
 * Formata o texto, limitando o tamanho máximo de cada linha.
 *
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    /**
     * Limite de caracteres por linha desejado para o texto formatado.
     */
    private Integer limit;

    /**
     * Cria um formatador que limita o tamanho de cada linha em 40 caracteres.
     */
    public StringFormatter() {
        this(40);
    }

    /**
     * Cria um formatador que limita o tamanho de cada linha em <code>limit</code> caracteres.
     * @param limit Limite de caracteres por linha desejado para o texto formatado.
     */
    public StringFormatter(int limit) {
        this.limit = limit;
    }

    /**
     * It receives a text and should return it formatted.
     *
     * @param text Texto original, com tamanho arbitrário para cada linha.
     * @return Texto formatado.
     */
    public abstract String format(String text);

    public Integer getLimit() {
        return limit;
    }
}
