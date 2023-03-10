
package domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Document 
{
    public Document(String filepath) 
        throws 
            IOException,
            NullPointerException,
            FileNotFoundException
    {
        if (filepath == null)
            throw new NullPointerException("Parameter 'filepath' is null."); 

        if (filepath.isBlank())
            throw new FileNotFoundException("Parameter 'filepath' is blank.");
    
        this.filepath = filepath;

        try 
        {
            this.document = PDDocument.load(
                new File(this.filepath)
                );

            this.content = new PDFTextStripper().getText(
                this.document
                );
        } 
        catch (FileNotFoundException fnfException) {
            throw fnfException;
        }
        catch (IOException ioException) {
            throw ioException;
        }

        this.modules = getModuleCollection();
    }
    
    //#region Members

    private Integer termCount = 0;

    private String content = null;
    private String filepath = null;
    private PDDocument document = null;
    private List<Module> modules = null;
    
    private final Map<String, Integer> termFrequency = new HashMap<>();

    //#endregion

    //#region Constants

    private final String ModuleIdentifier = "[Mm]odul[e]? [Nn]ame";
    private final String QualificationIdentifier = "[Qq]ualification? [Tt]arget[s]";

    //#endregion

    //#region Getters

    public String getContent()
    {
        return content;
    }

    public String getFilepath()
    {
        return filepath;
    }

    public Integer getTermCount()
    {
        return termCount;
    }
    
    public PDDocument getDocument()
    {
        return document;
    }

    public List<Module> getModules()
    {
        return modules;
    }

    //#endregion

    //#region Public Methods

    public void setTerm(String term)
    {
        this.termCount++;

        Integer frequency = null;

        if (termFrequency.containsKey(term))
        {
            frequency = termFrequency.get(term);
            frequency++;
        }

        termFrequency.put(term, frequency == null ? 1 : frequency);
    }

    public Integer getTermFrequency(String term)
    {
        return termFrequency.containsKey(term) 
                ? termFrequency.get(term) 
                : null;
    }

    public void writeDocumentToFile(String filepath) 
        throws 
            IOException,
            NullPointerException,
            FileNotFoundException
    {
        if (filepath == null)
            throw new NullPointerException("Parameter 'filepath' is null."); 

        if (filepath.trim().length() == 0)
            throw new FileNotFoundException("Parameter 'filepath' is blank.");

        try 
        {
            BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(filepath)
                );

            bufferedWriter.write(getContent());
            bufferedWriter.close();
        }
        catch (IOException ioException) {
            throw ioException;
        }
    }

    public void writeModulesToFile(String filepath)
        throws 
            IOException,
            NullPointerException,
            FileNotFoundException
    {
        if (filepath == null)
            throw new NullPointerException("Parameter 'filepath' is null."); 

        if (filepath.trim().length() == 0)
            throw new FileNotFoundException("Parameter 'filepath' is blank.");

        try 
        {
            BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(filepath)
                );

            StringBuilder stringBuilder = new StringBuilder();

            for (Module module : getModules()) 
            {
                stringBuilder
                    .append(module.getTitle())
                    .append(System.getProperty("line.separator"));

                stringBuilder
                    .append(module.getContent())
                    .append(System.getProperty("line.separator"))
                    .append(System.getProperty("line.separator"));
            }

            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
        }
        catch (IOException ioException) {
            throw ioException;
        }
    }

    //#endregion

    //#region Private Methods

    private List<Module> getModuleCollection()
    {
        String[] moduleCollection = getContent().split(ModuleIdentifier);
        List<Module> modules = new ArrayList<>();

        for (int i = 1; i < moduleCollection.length; i++) 
        {
            String moduleText = moduleCollection[i].trim();

            String moduleTitle = moduleText.split(
                System.getProperty("line.separator"), 2
                )[0].trim();

            String moduleContent = moduleText.split(
                System.getProperty("line.separator"), 2
                )[1].trim();

            String qualifications = moduleContent.split(QualificationIdentifier)[1];

            String content = qualifications.split("[Mm]odul[e]? [Cc]ontent[s]?")[0];

            modules.add(new Module(moduleTitle, content));
        }

        return modules;
    }

    //#endregion
}
