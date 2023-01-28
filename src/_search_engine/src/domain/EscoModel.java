
package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Collection<EscoSkill> getAllEscoSkills()
    {
        Map<String, EscoSkill> result = new HashMap<>();

        for (EscoSkill skill : skills)
        {
            if (result.containsKey(skill.getTitle()))
                continue;

            result.put(skill.getTitle(), skill);
        }

        return result.values();
    }

    public Collection<EscoOccu> getAllEssentialOccupations()
    {
        Collection<EscoSkill> escoSkills = getAllEscoSkills();

        Map<String, EscoOccu> result = new HashMap<>();

        for (EscoSkill skill: escoSkills)
        {
            for (EscoOccu occupation : skill.getEssentialFor())
            {
                if (result.containsKey(occupation.getTitle()))
                    continue;

                result.put(occupation.getTitle(), occupation);
            }
        }

        return result.values();
    }

    public Collection<EscoOccu> getAllOptionalOccupations()
    {
        Collection<EscoSkill> escoSkills = getAllEscoSkills();

        Map<String, EscoOccu> result = new HashMap<>();

        for (EscoSkill skill: escoSkills)
        {
            for (EscoOccu occupation : skill.getEssentialFor())
            {
                if (result.containsKey(occupation.getTitle()))
                    continue;

                result.put(occupation.getTitle(), occupation);
            }
        }

        return result.values();
    }
    
    //#endregion
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
            builder
                .append("Title: " + getModuleTitle())
                .append(System.getProperty("line.separator"))
                .append("Content:")
                .append(System.getProperty("line.separator"))
                .append(getModuleContent())
                .append(System.getProperty("line.separator"))
                .append(System.getProperty("line.separator"))
                .append("Skills:")
                .append(System.getProperty("line.separator"));
        
        for (EscoSkill skill : getAllEscoSkills())
            builder
                .append(skill.getTitle() + ", ");

        builder
            .append(System.getProperty("line.separator"))
            .append(System.getProperty("line.separator"))
            .append("Essential For:")
            .append(System.getProperty("line.separator"));

        for (EscoOccu occupation : getAllEssentialOccupations())
            builder
                .append(occupation.getTitle() + ", ");

        builder
            .append(System.getProperty("line.separator"))
            .append(System.getProperty("line.separator"))
            .append("Optional For:")
            .append(System.getProperty("line.separator"));

        for (EscoOccu occupation : getAllOptionalOccupations())
            builder
                .append(occupation.getTitle() + ", ");
    
        return builder.toString();
    }
}
