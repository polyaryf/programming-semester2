package services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exeption.GeneratorException;
import services.MemeGenerator;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class MemeGeneratorAPI implements MemeGenerator {

    @Override
    public LinkedList<String> generateMemes() throws GeneratorException, IOException {
        URL url = new URL("https://api.imgflip.com/get_memes");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responsecode = connection.getResponseCode();
        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " + responsecode);

        Scanner scanner = new Scanner(url.openStream());
        String input = null;
        while (scanner.hasNext()){
            input += scanner.nextLine();
        }
        input = input.replaceAll("null","");
        scanner.close();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(input);
        JsonObject data = (JsonObject) jsonObject.get("data");
        JsonArray memes = (JsonArray) data.getAsJsonArray("memes");
        LinkedList<String> memesUrl = new LinkedList<>();
        for (int i = 0; i < memes.size(); i++) {
            JsonObject object = (JsonObject) memes.get(i);
            String memeUrl = object.get("url").getAsString();
            memesUrl.add(memeUrl);
        }
        return memesUrl;
    }

    public void getRandomMeme(LinkedList<String> memesUrl) throws IOException{
        Desktop desktop = java.awt.Desktop.getDesktop();
        try {
            Random random = new Random();
            int randNum = random.nextInt(memesUrl.size());
            URI oURL = new URI(memesUrl.get(randNum));
            desktop.browse(oURL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
