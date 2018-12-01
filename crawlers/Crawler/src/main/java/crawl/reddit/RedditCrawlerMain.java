package crawl.reddit;

import java.util.Scanner;

public class RedditCrawlerMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System. in);
        RedditCrawler redditCrawler = new RedditCrawler();
        while (true) {
            System.out.print("\ndigite nomes de subreddits separados por ';': ");
            String input = scanner.nextLine();
            System.out.println(redditCrawler.processCommand(input.split(";")));
        }
    }
}
