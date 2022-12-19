
package foundation;

import core.NGram;
import domain.Module;
import domain.Document;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Engine 
{
    public Engine(String filepath) 
        throws 
            IOException,
            NullPointerException, 
            FileNotFoundException
    {
        this(new Document(filepath));
    }

    public Engine(Document document)
        throws
            NullPointerException
    {
        if (document == null)
            throw new NullPointerException("Parameter 'document' is null.");

        this.document = document;
    }

    //#region Members

    private Document document = null;
    private Map<String, Set<String>> synonyma = new HashMap<>();

    //#endregion

    //#region Constants

    final int MinLength = 3;
    final int MaxLength = Integer.MAX_VALUE;

    //#endregion

    //#region Getters

    public Document getDocument()
    {
        return this.document;
    }

    //#endregion

    //#region Public Methods

    public String buildSynonyms(String filepath) 
        throws
            IOException, 
            FileNotFoundException
    {
        if (filepath == null)
            throw new NullPointerException("Parameter 'filepath' is null."); 

        if (filepath.isBlank())
            throw new FileNotFoundException("Parameter 'filepath' is blank.");
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath)))
        {
            String line = bufferedReader.readLine(); 

            while (line != null)
            {
                String phrase = line.trim().split(Pattern.quote("|"))[0];
                String count  = line.trim().split(Pattern.quote("|"))[1];

                Set<String> result = new HashSet<>();

                for (
                    int i = 0; i < Integer.parseInt(count); i++
                    ) 
                {
                    String[] synonyma = bufferedReader.readLine().split(Pattern.quote("|"));

                    for (int j = 1; j < synonyma.length; j++) 
                        result.add(synonyma[j].trim());   
                }
                
                StringBuilder keybuilder = new StringBuilder();

                Analyzer keyAnalyzer = new EnglishAnalyzer();
            
                TokenStream keyTokenStream = keyAnalyzer.tokenStream(
                    "key", phrase
                    );

                CharTermAttribute keyCharAttribute = keyTokenStream.addAttribute(
                    CharTermAttribute.class
                    );

                try 
                {
                    keyTokenStream.reset();
    
                    while (
                        keyTokenStream.incrementToken()
                        ) 
                    {
                        keybuilder
                            .append(keyCharAttribute.toString())
                            .append(" ");
                    }
                        
                    keyTokenStream.end();
                } 
                finally {
                    keyTokenStream.close();
                }
    
                keyAnalyzer.close();

                Set<String> set = new HashSet<>();

                for (String synonym : result) 
                {
                    StringBuilder setBuilder = new StringBuilder();

                    Analyzer setAnalyzer = new EnglishAnalyzer();

                    TokenStream setTokenStream = setAnalyzer.tokenStream(
                        "set", synonym.trim()
                        );

                    CharTermAttribute setCharAttribute = setTokenStream.addAttribute(
                        CharTermAttribute.class
                        );
        
                    try
                    {
                        setTokenStream.reset();

                        while (
                            setTokenStream.incrementToken()
                            ) 
                        {
                            setBuilder
                                .append(setCharAttribute.toString())
                                .append(" ");
                        }
                        
                        setTokenStream.end();
                    }
                    finally {
                        setTokenStream.close();
                    }
                    
                    set.add(
                        setBuilder.toString().trim()
                    );

                    setAnalyzer.close();
                }

                this.synonyma.put(
                    keybuilder.toString().trim(), set
                    );

                line = bufferedReader.readLine();
            }
        } 
        catch (FileNotFoundException fnfException) {
            throw fnfException;
        }
        catch (IOException ioException) {
            throw ioException;
        }

        return null;
    }

    public String buildIndex() 
        throws 
            IOException
    {
        if (document == null)
            return null;

        StringBuilder indexBuilder = new StringBuilder();
            
        for (Module module : document.getModules()) 
        {
            Analyzer analyzer = new EnglishAnalyzer();
            
            TokenStream tokenStream = new LengthFilter(
                analyzer.tokenStream("contents", module.getContent().toLowerCase()), MinLength, MaxLength
                );

            CharTermAttribute charAttribute = tokenStream.addAttribute(
                CharTermAttribute.class
                );

            try 
            {
                StringBuilder perModuleBuilder = new StringBuilder();
                
                tokenStream.reset();

                while (
                    tokenStream.incrementToken()
                    ) 
                {
                    String term = charAttribute.toString();

                    indexBuilder
                        .append(term)
                        .append(" ");

                    perModuleBuilder
                        .append(term)
                        .append(" ");

                    module.setTerm(term);
                    document.setTerm(term);                  
                }

                tokenStream.end();

                String indexPerModule = perModuleBuilder.toString();
                    module.setIndex(indexPerModule);

                for (String biGram : NGram.getBiGrams(indexPerModule) )
                    module.setBiGram(biGram);

                for (String triGram : NGram.getTriGrams(indexPerModule) )
                    module.setTriGram(triGram);

                for (String quadGram : NGram.getQuadGrams(indexPerModule) )
                    module.setQuadGram(quadGram);
            } 
            finally {
                tokenStream.close();
            }

            indexBuilder
                .append(System.getProperty("line.separator"))
                .append(System.getProperty("line.separator"));

            analyzer.close();
        }

        for (Module module : document.getModules()) 
        {
            for (String term : module.getTerms()) 
            {
                double docCnt = ( double )(document.getModules().size() );
                double docOcc = 1;

                for (Module x : document.getModules())
                    if (x.getTerms().contains(term))
                        docOcc++;

                double modCnt = module.getTermCount().doubleValue();
                double modFrq = module.getTermFrequency(term).doubleValue();

                double idf = Math.log(
                    docCnt / docOcc
                    );
                    
                double tf = modFrq / modCnt;
    
                double termTfIdf = (tf * idf);
    
                module.setTermTfIdf(
                    term, termTfIdf
                    );
            }
        }

        return indexBuilder.toString();
    }

    //#endregion
}
