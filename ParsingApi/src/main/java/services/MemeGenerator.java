package services;

import exeption.GeneratorException;

import java.io.IOException;
import java.util.LinkedList;

public interface MemeGenerator {
    LinkedList<String> generateMemes() throws GeneratorException, IOException;
    void getRandomMeme(LinkedList<String> memesUrl) throws IOException;
}
