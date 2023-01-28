
import core.KeyValuePair;

import domain.Module;
import domain.Document;
import domain.EscoModel;

import foundation.Engine;

import java.util.List;

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

        Engine engine = new Engine(document);
            engine.buildIndex();
            engine.buildSynonyms("./res/synonyma.txt");

        BufferedWriter bufferedWriter = new BufferedWriter(
            new FileWriter("./res/out/module_manual_INDEX.txt")
            );

        StringBuilder stringBuilder = new StringBuilder();

        for (Module module : document.getModules())
        {
            List<KeyValuePair> topTerms = module.getTopTerms();
            List<KeyValuePair> topBiGrams = module.getTopBiGrams();
            List<KeyValuePair> topTriGrams = module.getTopTriGrams();

            stringBuilder
                .append(module.getIndex())
                .append(System.getProperty("line.separator"))
                .append(System.getProperty("line.separator"));
            
            for (KeyValuePair pair : topTerms)
                stringBuilder
                    .append(pair.getKey())
                    .append(" : ")
                    .append(pair.getValue())
                    .append(System.getProperty("line.separator"));

                stringBuilder
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));

            for (KeyValuePair pair : topBiGrams)
                stringBuilder
                    .append(pair.getKey())
                    .append(" : ")
                    .append(pair.getValue())
                    .append(System.getProperty("line.separator"));

                stringBuilder
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));

            for (KeyValuePair pair : topTriGrams)
                stringBuilder
                    .append(pair.getKey())
                    .append(" : ")
                    .append(pair.getValue())
                    .append(System.getProperty("line.separator"));

                stringBuilder
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));
        }

        bufferedWriter.write(stringBuilder.toString());
        bufferedWriter.close();

        document.writeDocumentToFile(
            "./res/out/module_manual_IMACS.txt"
            );

        document.writeModulesToFile(
            "./res/out/module_manual_MODULES.txt"
            );


        List<EscoModel> escoModels = engine.performEscoMatching();

        StringBuilder modelBuilder = new StringBuilder();
        
        for (EscoModel model : escoModels)
            modelBuilder
                .append(model.toString())
                .append(System.getProperty("line.separator"))
                .append(System.getProperty("line.separator"));

        BufferedWriter modelWriter = new BufferedWriter(
            new FileWriter("./res/out/esco_matching_DEBUG.txt")
            );
        
        modelWriter.write(modelBuilder.toString());
        modelWriter.close();
    }
}
