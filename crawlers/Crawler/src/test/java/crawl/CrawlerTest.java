package crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RedditCrawlerTest {

    @Test
    void crawl() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            System.out.println(String.format("%s\n\t%s",
                    headline.attr("title"), headline.absUrl("href")));
        }
    }

    @Test
    void processCommandTest() {
        System.out.println(new RedditCrawler().processCommand("space;cats".split(";")));
    }

    @Test
    void processInvalidSubredditCommandTest() {
        System.out.println(new RedditCrawler().processCommand("cats;gragragra".split(";")));
    }
}
