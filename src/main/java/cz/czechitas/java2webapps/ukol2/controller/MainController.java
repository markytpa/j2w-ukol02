package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final Random random = new Random();

    private List<String> seznamObrazku = Arrays.asList("0d-z8cJGIR4", "SdU2RpuynFU", "NEsz4hYgAPo", "opXKweGtDM");

    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }

    private List<String> seznamCitatu;

    {
        try {
            seznamCitatu = readAllLines("citaty.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public ModelAndView index() {

        ModelAndView result = new ModelAndView("index");

        int indexVybranehoObrazku = random.nextInt(seznamObrazku.size());
        String vybranyObrazek = seznamObrazku.get(indexVybranehoObrazku);
        result.addObject("obrazek","https://source.unsplash.com/" + vybranyObrazek + "/1600x900");

        int indexVybranehoCitatu = random.nextInt(seznamCitatu.size());
        String vybranyCitat = seznamCitatu.get(indexVybranehoCitatu);
        result.addObject("citat",vybranyCitat);

        return result;

    }
}
