
package domain;

import java.util.List;
import java.util.ArrayList;

public class EscoSkill 
{
    public EscoSkill(
        String title, String uri
        )
    {
        this.title = title;
        this.uri = uri;
    }

    //#region Members

    private String title = null;
    private String uri = null;

    private List<EscoOccu> essentialOccupations = new ArrayList<>();
    private List<EscoOccu> optionalOccupations = new ArrayList<>();

    //#endregion

    //#region Getters

    public String getTitle()
    {
        return this.title;
    }

    public String getUri()
    {
        return this.uri;
    }

    public List<EscoOccu> getEssentialFor()
    {
        return essentialOccupations;
    }

    public List<EscoOccu> getOptionalFor()
    {
        return optionalOccupations;
    }

    //#endregion

    public void addEssentialFor(EscoOccu occupation)
    {
        essentialOccupations.add(occupation);
    }

    public void addOptionalFor(EscoOccu occupation)
    {
        optionalOccupations.add(occupation);
    }
}
