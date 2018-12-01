package crawl.reddit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedditCrawler {

    /**
     * Varre cada subreddit especificado em busca de posts com mais de 5000 pontos.
     *
     * @param subredditNames Nomes dos subreddits, separados por ";".
     * @return Lista de referências das threads com mais de 5000 pontos.
     */
    public String processCommand(String[] subredditNames) {
        return Stream.of(subredditNames).map(this::crawlReddit).collect(Collectors.joining("\n\n"));
    }

    /**
     * Varre o subreddtit especificado em busca de posts com mais de 5000 pontos.
     * @param subreddit Nome do subreddit.
     * @return Texto formatado para lista de posts, com nome, pontuação, link e link dos comentários
     */
    private String crawlReddit(String subreddit) {
        try {
            Document doc = Jsoup.connect(String.format("https://old.reddit.com/r/%s/", subreddit)).get();
            if ("search results".equals(doc.title())) {
                return String.format("<b>Subreddit '%s' não encontrado.</b>", subreddit);
            }
            Elements strangerThings = doc.select(".thing");
            return String.format("<b>%s</b>\n%s", doc.title(), strangerThings.stream()
                    .filter(this::moreThan5000Points).map(thing ->
                            String.format("\t%s (%s pontos)\n\t\tURL: %s\n\t\tcomentários: %s",
                                    thing.selectFirst("p.title a").text(),
                                    thing.attr("data-score"),
                                    thing.absUrl("data-url"),
                                    thing.absUrl("data-permalink")))
                    .collect(Collectors.joining("\n\n")));
        } catch (IOException e) {
            e.printStackTrace();
            return "Me atrapalhei pra acessar a internet, nem sei como estou falando com você!";
        }
    }

    private boolean moreThan5000Points(Element thing) {
        try {
            return Integer.parseInt(thing.attr("data-score")) >= 5000;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
