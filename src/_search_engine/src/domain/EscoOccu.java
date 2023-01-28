
package domain;

public class EscoOccu 
{
    public EscoOccu(
        String title, 
        String uri, 
        String description
        )
    {
        this.title = title;
        this.uri = uri;
        this.description = description;
    }

    //#region Members

    private String title = null;
    private String uri = null;
    private String description = null;

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

    public String getDescription()
    {
        return this.description;
    }

    //#endregion
}
