package crawl.telegramBot;

import crawl.reddit.RedditCrawler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class IdwRedditBot extends TelegramLongPollingBot {

    private final RedditCrawler redditCrawler = new RedditCrawler();

    @Override
    public void onUpdateReceived(Update update) {
        // É preciso verificar também se a mensagem está vazia, o que é o caso quando o bot é registrado no TelegramBotMain.
        if (!update.hasMessage() || !update.getMessage().hasText() || update.getMessage().getText().isEmpty()) {
            return;
        }

        String[] tokens = update.getMessage().getText().split("\\s+");
        String responseMessageText = tokens.length != 2 || !tokens[0].equals("/NadaPraFazer")
                ? "não sei o que fazer com isso!"
                : redditCrawler.processCommand(tokens[1].split(";"));
        SendMessage message = new SendMessage() // cria uma mensagem
                .setChatId(update.getMessage().getChatId())
                .setText(responseMessageText);
        message.enableHtml(true);
        try {
            execute(message); // envia a mensagem para o usuário
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "IdwRedditBot";
    }

    @Override
    public String getBotToken() {
        // token do bot gerado pelo BotFather.
        return "679860817:AAFbt6eHWjKt9Tc9r-WXlZhNB73OF86BL60";
    }

}
