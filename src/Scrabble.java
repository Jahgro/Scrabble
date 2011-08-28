package src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Scrabble extends JFrame implements ActionListener {
  static final long serialVersionUID = 0L; 
  private WordSearch findWord;         /* Class that finds words from a list of letters */

  private JMenuBar    mBar;
  private JMenu       file;
  private JMenuItem   exit;
  private JTabbedPane mainPane;
  
  public Scrabble() {
    findWord = new WordSearch();
    mBar     = new JMenuBar();
    file     = new JMenu("File");
    exit     = new JMenuItem("Exit");
    mainPane = new JTabbedPane();

    /* Setup Menu Bar */
    setJMenuBar(mBar);
    file.add(exit);

    mBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    mBar.add(file);

    //* Setup tabbed panes */
    mainPane.addTab("Find Word", null, findWord, "Input letters to find matching words.");

    setLayout(new BorderLayout());
    add(mainPane, BorderLayout.CENTER);
    setTitle("Scrabble Word Finder");

    exit.addActionListener(this);

    pack();
    setVisible(true);
  }

  public static void main(String [] args) {
    Scrabble scrabProg = new Scrabble();
    scrabProg.setResizable(false);
    scrabProg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void actionPerformed(java.awt.event.ActionEvent ae){
    if(ae.getSource() == exit) {
      System.exit(0);
    }
  }
}