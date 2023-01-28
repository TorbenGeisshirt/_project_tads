
package domain;

import java.util.List;

public class EscoModel 
{
    public EscoModel(
        Module module,
        List<EscoSkill> skills
        )
    {
        this.module = module;
        this.skills = skills;
    }

    //#region Members

    private Module module = null;
    private List<EscoSkill> skills = null;

    //#endregion

    //#region Getters

    public String getModuleTitle()
    {
        return module.getTitle();
    }

    public String getModuleContent()
    {
        return module.getContent();
    }

    public List<EscoSkill> getEscoSkills()
    {
        return this.skills;
    }

    //#endregion
}
