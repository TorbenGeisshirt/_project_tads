
import java.io.BufferedWriter;
import java.io.FileWriter;

import domain.Document;
import foundation.Engine;

public class App 
{
    public static void main(String[] args) 
        throws
            Exception 
    {
        Document document = new Document(
            "./res/module_manual_IMACS.pdf"
            );

        document.writeContentToFile(
            "./res/out/module_manual_IMACS.txt"
            );

        Engine engine = new Engine(document);
        String index = engine.buildIndex();

        BufferedWriter bufferedWriter = new BufferedWriter(
            new FileWriter("./res/out/module_manual_INDEX.txt")
            );

        bufferedWriter.write(index);
        bufferedWriter.close();

        engine.buildSynonyms("./res/synonyma.txt");
    }
}
