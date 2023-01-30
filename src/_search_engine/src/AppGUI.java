
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

import domain.Document;
import domain.EscoOccu;
import domain.EscoSkill;
import domain.EscoModel;
import foundation.Engine;

import javax.swing.SwingUtilities;

public class AppGUI 
    extends 
        JPanel
{
    public AppGUI()
    {
        super(new GridLayout(1,0));

        escoTable = new JTable(
            new EscoTable(getEscoData("./res/module_manual_IMACS.pdf"))
            );
        escoTable.setPreferredScrollableViewportSize(
            new Dimension(640, 256)
            );
        escoTable.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(escoTable);    

        add(scrollPane, BorderLayout.PAGE_START);
    }

    private Engine engine;
    private Document document;

    private JTable escoTable;
    private JScrollPane scrollPane;

    private java.util.List<EscoModel> getEscoData(String filepath)
    {
        try
        {
            this.document = new Document(filepath);
            this.engine = new Engine(document);

            this.engine.buildIndex();

            return this.engine.performEscoMatching();
        }
        catch(Exception e)
        {}

        return null;
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(
            new Runnable() 
            {
                public void run()
                {
                    try 
                    {
                        UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName()
                            );
                    } 
                    catch (Exception e) 
                    {}
                    
                    JFrame frame = new JFrame("Does it match your job? - Demo");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.add(new AppGUI());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                }
            });
    }
}

class EscoTable 
    extends 
        AbstractTableModel
{
    private String[] columnNames = 
    {
        "Module Title", "Skills", "Essential Occupations", "Optional Occupations"
    };

    public EscoTable(java.util.List<EscoModel> esco)
    {
        data = new Object[esco.size()][];

        for (int i = 0; i < esco.size(); i++)
        {
            data[i] = new String[] 
            { 
                esco.get(i).getModuleTitle(),
                getAllSkillsAsString(esco.get(i).getAllEscoSkills()),
                getAllEssentialOccusAsString(esco.get(i).getAllEssentialOccupations()),
                getAllOptionalOccusAsString(esco.get(i).getAllOptionalOccupations())
            };
        }
    }

    private String getAllSkillsAsString(java.util.Collection<EscoSkill> skills)
    {
        java.util.List<EscoSkill> skillList = new java.util.ArrayList<EscoSkill>(skills);

        StringBuilder builder = new StringBuilder();
            builder.append(skillList.get(0).getTitle());

        for (int i = 1; i < skillList.size(); i++)
        {
            builder
                .append(", ")
                .append(skillList.get(i).getTitle());
        }

        return builder.toString();
    }

    private String getAllEssentialOccusAsString(java.util.Collection<EscoOccu> essential)
    {
        java.util.List<EscoOccu> occuList = new java.util.ArrayList<EscoOccu>(essential);

        StringBuilder builder = new StringBuilder();
            builder.append(occuList.get(0).getTitle());

        for (int i = 1; i < occuList.size(); i++)
        {
            builder
                .append(", ")
                .append(occuList.get(i).getTitle());
        }

        return builder.toString();
    }

    private String getAllOptionalOccusAsString(java.util.Collection<EscoOccu> optional)
    {
        java.util.List<EscoOccu> occuList = new java.util.ArrayList<EscoOccu>(optional);

        StringBuilder builder = new StringBuilder();
            builder.append(occuList.get(0).getTitle());

        for (int i = 1; i < occuList.size(); i++)
        {
            builder
                .append(", ")
                .append(occuList.get(i).getTitle());
        }

        return builder.toString();
    }

    private Object[][] data;

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
