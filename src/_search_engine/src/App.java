

import domain.Document;
import foundation.Engine;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class App 
{
    public static void main(String[] args) 
        throws
            Exception 
    {
        Document document = new Document(
            "./res/module_manual_IMACS.pdf"
            );

        document.writeDocumentToFile(
            "./res/out/module_manual_IMACS.txt"
            );

        document.writeModulesToFile(
            "./res/out/module_manual_MODULES.txt"
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
