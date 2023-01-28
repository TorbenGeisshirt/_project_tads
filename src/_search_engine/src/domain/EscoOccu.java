
package domain;

public class EscoOccu 
{
    public EscoOccu(
        String title, 
        String uri
        )
    {
        this.title = title;
        this.uri = uri;
    }

    //#region Members

    private String title = null;
    private String uri = null;

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

    //#endregion
}
