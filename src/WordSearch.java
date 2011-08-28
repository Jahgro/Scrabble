package src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

class WordSearch extends JPanel implements ActionListener {
  static final long serialVersionUID = 0L; 
  // Panels for the Frame
  private JPanel searchPanel,
                 inputPanel,
                 buttonPanel;
  private JScrollPane wordsPanel;
    
  // text field for user input
  private JTextField inputLetters,
                     containLetters;

  // Labels for input letters
  private JLabel lettersLabel,
                 containLabel,
                 fillLabel01,
                 fillLabel02;

  private JTextArea wordField;
   
  private JButton    calculate;
  private JButton    clear;

  // Holds dictionary
  private Vector<String> dict;

  // Holds words that will work
  Vector<String> good_words = new Vector<String>();

  // Holds letter values
  //private HashMap let_values = new HashMap()
    
  // Constructor
  public WordSearch() {

    /* Setup Dictionary */
    dict          = new Vector<String>(); //(2000);
    String record = new String();
    try {
      // Old way of opening file
      //FileReader fr           = new FileReader("dict.txt");
      //BufferedReader br       = new BufferedReader(fr);
      
      // Opens file in .jar
      // Open dict.txt
      // getResource gets the URL of dict.txt
      // openStream connects to URL and returns an inputStream
      BufferedReader br = new BufferedReader(new InputStreamReader(WordSearch.class.getResource("/dictionary/dict.txt").openStream()));


      /* Get all words */
      while((record = br.readLine()) != null) {
        dict.add(record);
      }
    }
    catch(FileNotFoundException e) {
      System.out.println("File Not Found");
      System.exit(1);
    }
    catch(Exception e) {
      System.out.println("Input Failed");
      System.exit(1);
    }

    /* Setup graphical objects */
    inputLetters   = new JTextField();
    containLetters = new JTextField();
    lettersLabel   = new JLabel("Input Letters");
    containLabel   = new JLabel("Must Contain");
    fillLabel01    = new JLabel("");
    fillLabel02    = new JLabel("");
    wordField      = new JTextArea();
    calculate      = new JButton("Search");
    clear          = new JButton("Clear");
    searchPanel    = new JPanel();
    wordsPanel     = new JScrollPane(wordField);
    inputPanel     = new JPanel();
    buttonPanel    = new JPanel();

    /* Setup button panel */
    buttonPanel.setLayout(new GridLayout(1, 4, 5, 0));
    buttonPanel.setPreferredSize(new Dimension(400,20));

    buttonPanel.add(fillLabel01);
    buttonPanel.add(calculate);
    buttonPanel.add(clear);
    buttonPanel.add(fillLabel02);

    /* Setup word output panel */
    wordsPanel.setLayout(new ScrollPaneLayout());
    wordsPanel.setPreferredSize(new Dimension(400,400));

    /* Setup letter input panel */
    inputPanel.setLayout(new GridLayout(1, 4, 5, 0));
    inputPanel.setPreferredSize(new Dimension(400,20));

    inputPanel.add(lettersLabel);
    inputPanel.add(inputLetters);
    inputPanel.add(containLabel);
    inputPanel.add(containLetters);
    

    /* Setup main search tab */
    searchPanel.setLayout(new BorderLayout());

    searchPanel.add(inputPanel, BorderLayout.NORTH);
    searchPanel.add(wordsPanel, BorderLayout.CENTER);
    searchPanel.add(buttonPanel, BorderLayout.SOUTH);

    // adds calc/clear buttons to action listener
    calculate.addActionListener(this);
    clear.addActionListener(this);
        
    this.add(searchPanel);
  }

  public void actionPerformed(ActionEvent ae) {
    if(ae.getSource() == clear) {
      inputLetters.setText("");
      wordField.setText("");
      containLetters.setText("");
    }
    if(ae.getSource() == calculate) {
      searchForWords();
      for(int i = 0; i != good_words.size(); ++i) {
        wordField.append(good_words.get(i) + "\n");
      }
    }
  }

  /* Search through dictionary and find all matching words */
  public void searchForWords() {

    /* Reset output Field */
    wordField.setText("");
    /* Clear words list */
    good_words.clear();

    //char[] letters = inputLetters.getText().toCharArray();
    //char[] contain = containLetters.getText().toCharArray();
    String contain = containLetters.getText();

    /* Get dictionary size */
    int dict_size = dict.size();

    /* Loop through dictionary */
    for(int i = 0; i != dict_size; ++i) {
    
      /* Get work variables to find valid words */
      StringBuilder tmp_letters = new StringBuilder(inputLetters.getText());
      StringBuilder dict_word   = new StringBuilder(dict.get(i));

      int dict_word_size = dict_word.length();
      int letter_size    = tmp_letters.length();

      /* Loop through letters in dictionary word */
      for(int j = 0; j != dict_word_size && j != letter_size; ++j) {
 
        /* Loop through input letters */
        for(int k = 0; k != letter_size; ++k) {

          /* Check if letters match */
          if(dict_word.charAt(j) == tmp_letters.charAt(k) || tmp_letters.charAt(k) == '*') {
            dict_word.setCharAt(j, ' ');       /* Set char to found */
            tmp_letters.setCharAt(k, ' ');     /* Set char to used  */
            break;
          }
        }
      }

      int l;
      /* Check if input characters can create word */
      for(l = 0; l != dict_word.length(); ++l) {
        if( dict_word.charAt(l) != ' ' ) {
          break;
        }
      }
      /* If true, word is valid */
      if(l == dict_word.length() ) {
        /* Check if word has contain */
        for(int ii = 0; ii != contain.length(); ++ii) {
          if( dict.get(i).contains(contain)) {
            good_words.add(dict.get(i));
            //wordField.append(dict.get(i) + "\n");
            break;
          }
        }
      }
    }

    return;
  }
}
