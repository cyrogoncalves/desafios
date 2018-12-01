package crawl.telegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Classe que sobe a API do bot.
 * O Bot só responderá o comando enquanto o serviço disponibilizado por este main estiver de pé.
 */
public class TelegramBotMain {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            // Registrar o bot
            botsApi.registerBot(new IdwRedditBot());
            System.out.println("IdwRedditBot de pé! Pergunte algo a ele com '/NadaPraFazer'.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
