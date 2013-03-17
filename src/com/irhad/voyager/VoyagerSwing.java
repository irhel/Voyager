/* 
 * 
 * Voyager - Bon Voyage through the World Wide Web
 * 
 * Version: 1.0
 * Build id: I21707987-2009
 *
 * (c) Copyright Voyager contributors and mentors, 2009.  All rights reserved.
 * 
 * Student: Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project mentors: Elchin Asgarov, Semir Elezovikj
 * 
 */
package com.irhad.voyager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.irhad.voyager.swing.AnchorConstraint;
import com.irhad.voyager.swing.AnchorLayout;


/**
*  @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class VoyagerSwing extends javax.swing.JFrame {
    
    private static final long serialVersionUID = 8441666998714308996L;
    
    {
        try {
            javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    JMenuBar jMenuBar1;
    JPanel jPanel1;
    JScrollPane jScrollPane1;
    JLabel downloadUrlLabel;
    JPanel downloadUrlPanel;
    JPanel downloadPanel;
    JTextArea badText;
    JScrollPane badLinksScrool;
    JLabel badLinksLabel;
    JLabel badLinks;
    JButton startDownloadButton;
    JLabel pagesLabel;
    JPanel pagesPanel;
    JButton pathButton;
    JTextField pathField;
    JLabel pathLabel;
    JPanel pathPanel;
    JTextField downloadUrlField;
    JLabel goodLink;
    JLabel currentlyProcessing;
    JPanel jPanel4;
    JTextArea logsTextArea;
    JPanel jPanel3;
    JTextField urlField;
    JTabbedPane jTabbedPane1;
    JPanel jPanel2;
    JButton startButton;
    JLabel urllabel;
    JMenuItem aboutItem;
    JMenuItem optionsItem;
    JMenu fileMenu;
    JButton saveToFileButton;
    JButton clearLogsButton;
    JCheckBox voyagerMode;
    JTextField threadNumberField;
    JButton saveOptionsButton;
    JButton loadOptionsButton;
    
    
    /**
     * Auto-generated main method to display this JFrame
     */
    public void showFrame() {
        initGUI();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    
    public VoyagerSwing() {
    }
    
    private void initGUI() {
        try {
            setTitle("Voyager - Bon voyage through the World Wide Web");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            {
                jTabbedPane1 = new JTabbedPane();
                getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
                jTabbedPane1.setPreferredSize(new java.awt.Dimension(392, 223));
                {
                    jPanel1 = new JPanel();
                    jTabbedPane1.addTab("Validate website", null, jPanel1, null);
                    AnchorLayout jPanel1Layout = new AnchorLayout();
                    jPanel1.setLayout(jPanel1Layout);
                    {
                        badLinksScrool = new JScrollPane();
                        jPanel1.add(badLinksScrool, new AnchorConstraint(476, 979, 941, 44, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                        badLinksScrool.setPreferredSize(new java.awt.Dimension(519, 130));
                        {
                            badText = new JTextArea();
                            badText.setEditable(false);
                            badLinksScrool.setViewportView(badText);
                        }
                    }
                    {
                        badLinksLabel = new JLabel();
                        jPanel1.add(badLinksLabel, new AnchorConstraint(416, 979, 469, 49, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                        badLinksLabel.setText("Erroneous links:");
                        badLinksLabel.setPreferredSize(new java.awt.Dimension(516, 15));
                    }
                    {
                        badLinks = new JLabel();
                        jPanel1.add(badLinks, new AnchorConstraint(323, 979, 373, 49, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                        badLinks.setText("Erroneous links detected: ");
                        badLinks.setPreferredSize(new java.awt.Dimension(516, 14));
                    }
                    {
                        goodLink = new JLabel();
                        jPanel1.add(goodLink, new AnchorConstraint(251, 979, 301, 49, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                        goodLink.setText("Successful hits: ");
                        goodLink.setPreferredSize(new java.awt.Dimension(516, 14));
                    }
                    {
                        currentlyProcessing = new JLabel();
                        jPanel1.add(currentlyProcessing, new AnchorConstraint(176, 979, 226, 49, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL, AnchorConstraint.ANCHOR_REL));
                        currentlyProcessing.setText("Currently processing: ");
                        currentlyProcessing.setPreferredSize(new java.awt.Dimension(516, 14));
                    }
                    {
                        jPanel2 = new JPanel();
                        FlowLayout jPanel2Layout = new FlowLayout();
                        jPanel1.add(jPanel2, new AnchorConstraint(7, 0, 230, 0, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS, AnchorConstraint.ANCHOR_ABS));
                        jPanel2.setPreferredSize(new java.awt.Dimension(555, 39));
                        jPanel2.setLayout(jPanel2Layout);
                        {
                            urllabel = new JLabel();
                            jPanel2.add(urllabel);
                            BoxLayout urllabelLayout = new BoxLayout(urllabel, javax.swing.BoxLayout.X_AXIS);
                            urllabel.setLayout(urllabelLayout);
                            urllabel.setText("Enter URL: ");
                            urllabel.setPreferredSize(new java.awt.Dimension(66, 13));
                            urllabel.setBounds(38, 21, 340, 0);
                        }
                        {
                            urlField = new JTextField();
                            jPanel2.add(urlField);
                            urlField.setText("http://www.semus-i.com");
                            urlField.setBounds(113, 36, 125, 21);
                            urlField.setPreferredSize(new java.awt.Dimension(339, 21));
                        }
                        {
                            startButton = new JButton();
                            jPanel2.add(startButton);
                            startButton.setText("Start");
                            startButton.setPreferredSize(new java.awt.Dimension(64, 19));
                            startButton.setBounds(340, 0, 38, 21);
                        }
                    }
                }
                {
                    downloadPanel = new JPanel();
                    BorderLayout downloadPanelLayout = new BorderLayout();
                    jTabbedPane1.addTab("Download website", null, downloadPanel, null);
                    downloadPanel.setLayout(downloadPanelLayout);
                    {
                        downloadUrlPanel = new JPanel();
                        downloadPanel.add(downloadUrlPanel, BorderLayout.NORTH);
                        downloadUrlPanel.setPreferredSize(new java.awt.Dimension(555, 55));
                        {
                            downloadUrlLabel = new JLabel();
                            downloadUrlPanel.add(downloadUrlLabel);
                            downloadUrlLabel.setText("Website to download: ");
                        }
                        {
                            downloadUrlField = new JTextField();
                            downloadUrlPanel.add(downloadUrlField);
                            downloadUrlField.setText("http://www.infomatrix.ro");
                            downloadUrlField.setPreferredSize(new java.awt.Dimension(411, 21));
                        }
                    }
                    {
                        pathPanel = new JPanel();
                        downloadPanel.add(pathPanel, BorderLayout.CENTER);
                        pathPanel.setPreferredSize(new java.awt.Dimension(555, 168));
                        {
                            pathLabel = new JLabel();
                            pathPanel.add(pathLabel);
                            pathLabel.setText("Path to download: ");
                        }
                        {
                            pathField = new JTextField();
                            pathPanel.add(pathField);
                            pathField.setPreferredSize(new java.awt.Dimension(374, 21));
                        }
                        {
                            pathButton = new JButton();
                            pathPanel.add(pathButton);
                            pathButton.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent arg0) {
									//TODO
									JFileChooser fc = new JFileChooser();
									fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                                if (fc.showSaveDialog(VoyagerSwing.this) 
	                                                                == JFileChooser.APPROVE_OPTION) {
	                                	pathField.setText(fc.getSelectedFile().getAbsolutePath());
	                                }
								}
                            });
                            pathButton.setText("Browse");
                        }
                        {
                            pagesLabel = new JLabel();
                            pathPanel.add(pagesLabel);
                            pagesLabel.setText("Pages downloaded: ");
                            pagesLabel.setPreferredSize(new java.awt.Dimension(137, 76));
                        }
                    }
                    {
                        pagesPanel = new JPanel();
                        downloadPanel.add(pagesPanel, BorderLayout.SOUTH);
                        pagesPanel.setPreferredSize(new java.awt.Dimension(555, 115));
                        {
                            startDownloadButton = new JButton();
                            pagesPanel.add(startDownloadButton);
                            startDownloadButton.setText("Start download");
                        }
                    }
                }
                {
                    jPanel3 = new JPanel();
                    BorderLayout jPanel3Layout = new BorderLayout();
                    jPanel3.setLayout(jPanel3Layout);
                    jTabbedPane1.addTab("Logs", null, jPanel3, null);
                    jPanel3.setPreferredSize(new java.awt.Dimension(538, 250));
                    {
                        jScrollPane1 = new JScrollPane();
                        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
                        {
                            logsTextArea = new JTextArea();
                            logsTextArea.setEditable(false);
                            jScrollPane1.setViewportView(logsTextArea);
                        }
                    }
                    {
                        jPanel4 = new JPanel();
                        FlowLayout jPanel4Layout = new FlowLayout();
                        jPanel3.add(jPanel4, BorderLayout.SOUTH);
                        jPanel4.setLayout(jPanel4Layout);
                        saveToFileButton = new JButton("Save to file");
                        saveToFileButton.addActionListener(new ActionListener(){
                        	@Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser fc = new JFileChooser();
                                if (fc.showSaveDialog(VoyagerSwing.this) 
                                                                == JFileChooser.APPROVE_OPTION) {
                                    try{
                                        File file = fc.getSelectedFile();
                                        BufferedWriter wr = new BufferedWriter(
                                                                        new FileWriter(file));
                                        wr.write(logsTextArea.getText());
                                        wr.close();
                                    } catch (IOException exx) {
                                        JOptionPane.showMessageDialog(null,
                                                                        "Could not write to file",
                                                                        "Error",
                                                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        });
                        jPanel4.add(saveToFileButton);
                        clearLogsButton = new JButton("Clear logs");
                        jPanel4.add(clearLogsButton);
                        clearLogsButton.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                logsTextArea.setText("");
                            }
                        });
                    }
                }
                {
                    //PREFERENCES
                    JPanel jPanel5 = new JPanel();
                    jPanel5.setLayout(new BorderLayout());
                    
                    jTabbedPane1.addTab("Preferences", null, jPanel5, null);
                    jPanel5.setPreferredSize(new java.awt.Dimension(538, 250));
                    
                    Box hbox1 = Box.createHorizontalBox();
                    hbox1.add(new JLabel("Voyager mode:"));
                    voyagerMode = new JCheckBox();
                    voyagerMode.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ValidateWebsite.voyageMode = voyagerMode.isSelected(); 
                        }
                    });
                    hbox1.add(Box.createHorizontalStrut(20));
                    hbox1.add(voyagerMode);
                    
                    Box hbox2 = Box.createHorizontalBox();
                    hbox2.add(Box.createHorizontalStrut(185));
                    hbox2.add(new JLabel("Number of Threads:"));
                    threadNumberField = new JTextField(ValidateWebsite.threadNumber + "");
                    threadNumberField.addFocusListener(new FocusListener(){
                        @Override
                        public void focusGained(FocusEvent e) {}
                        @Override
                        public void focusLost(FocusEvent e) {
                            try{
                                ValidateWebsite.threadNumber = 
                                    Integer.parseInt(threadNumberField.getText());
                            } catch (Exception emm) {}
                        }
                    });
                    hbox2.add(Box.createHorizontalStrut(20));
                    hbox2.add(threadNumberField);
                    hbox2.add(Box.createHorizontalStrut(190));
                    
                    Box hbox3 = Box.createHorizontalBox();
                    saveOptionsButton = new JButton("Save preferences");
                    loadOptionsButton = new JButton("Load preferences");
                    saveOptionsButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fc = new JFileChooser();
                            if (fc.showSaveDialog(VoyagerSwing.this) 
                                                            == JFileChooser.APPROVE_OPTION) {
                                try{
                                    File file = fc.getSelectedFile();
                                    BufferedWriter wr = new BufferedWriter(
                                                                    new FileWriter(file));
                                    wr.write("threads="+ValidateWebsite.threadNumber+"\r\n");
                                    wr.write("voyage="+ValidateWebsite.voyageMode+"\r\n");
                                    wr.close();
                                    JOptionPane.showMessageDialog(null,
                                            "Preferences successfully saved",
                                            "Save",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } catch (IOException exx) {
                                    JOptionPane.showMessageDialog(null,
                                                                    "Could not write to file",
                                                                    "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });
                    loadOptionsButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFileChooser fc = new JFileChooser();
                            if (fc.showOpenDialog(VoyagerSwing.this) 
                                                            == JFileChooser.APPROVE_OPTION) {
                                try{
                                    File file = fc.getSelectedFile();
                                    BufferedReader wr = new BufferedReader(
                                                                    new FileReader(file));
                                    
                                    String threads = wr.readLine();
                                    String voyage = wr.readLine();
                                    
                                    String temp[] = threads.split("=");
                                    String temp2[] = voyage.split("=");
                                    ValidateWebsite.threadNumber =
                                    	Integer.parseInt(temp[1]);
                                    ValidateWebsite.voyageMode =
                                    	Boolean.parseBoolean(temp2[1]);
                                    voyagerMode.setSelected(ValidateWebsite.voyageMode);
                                    threadNumberField.setText(ValidateWebsite.threadNumber+"");
                                    wr.close();
                                    JOptionPane.showMessageDialog(null,
                                            "Preferences successfully loaded",
                                            "Load",
                                            JOptionPane.INFORMATION_MESSAGE);
                                } catch (Exception exx) {
                                    JOptionPane.showMessageDialog(null,
                                                                    "Could load settings from file",
                                                                    "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    });
                    hbox3.add(Box.createHorizontalStrut(170));
                    hbox3.add(saveOptionsButton);
                    hbox3.add(Box.createHorizontalStrut(10));
                    hbox3.add(loadOptionsButton);
                    hbox3.add(Box.createHorizontalStrut(85));
                    
                    Box vbox = Box.createVerticalBox();
                    vbox.add(Box.createVerticalStrut(20));
                    vbox.add(hbox1);
                    vbox.add(Box.createVerticalStrut(20));
                    vbox.add(hbox2);
                    vbox.add(Box.createVerticalStrut(20));
                    
                    jPanel5.add(vbox,BorderLayout.NORTH);
                    jPanel5.add(hbox3,BorderLayout.CENTER);
                }
                {
                    //ABOUT TAB
                    JPanel jPanel6 = new JPanel();
                    jPanel6.setLayout(new BorderLayout());
                    
                    jTabbedPane1.addTab("About", null, jPanel6, null);
                    jPanel6.setPreferredSize(new java.awt.Dimension(538, 250));
                    Box vbox = Box.createVerticalBox();
                    Box hbox = Box.createHorizontalBox();
                    hbox.add(Box.createHorizontalStrut(50));
                    hbox.add(vbox);
                    jPanel6.add(hbox,BorderLayout.NORTH);
                    vbox.add(Box.createVerticalStrut(50));
                    vbox.add(new JLabel("Voyager - Bon Voyage through the World Wide Web"));
                    vbox.add(new JLabel("Version: 1.0"));
                    vbox.add(new JLabel("(c) Copyright Voyager contributors and mentors, 2009.  All rights reserved.\n"));
                    vbox.add(new JLabel("Student: Irhad Elezovikj"));
                    vbox.add(new JLabel("Teacher: Nahit Yilmaz"));
                    vbox.add(new JLabel("Project mentors: Elchin Asgarov, Semir Elezovikj"));     		               
                }
            }
            pack();
            this.setSize(568, 390);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
