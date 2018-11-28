package idwall.desafio.string;

import idwall.desafio.Main;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class IdwallFormatterTest {

    @Test
    void format() throws IOException {
        StringFormatter formatter = new IdwallFormatter();
        String actual = formatter.format(Main.DEFAULT_INPUT_TEXT);
        String expected = new String(Files.readAllBytes(Paths.get("src","test", "resources", "output_parte1.txt")));
        // o texto gerado não vem com a última quebra de linha, então adicionamos ela aqui para a comparação funcionar.
        assertEquals(expected, actual + "\r\n");
    }
}