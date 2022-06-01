import exeption.GeneratorException;
import services.impl.MemeGeneratorAPI;
import java.io.IOException;



public class Main {
    public static void main(String[] args) throws IOException, GeneratorException {
        MemeGeneratorAPI generator = new MemeGeneratorAPI();
        generator.getRandomMeme(generator.generateMemes());

    }
}
