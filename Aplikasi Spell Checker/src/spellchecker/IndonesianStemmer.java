package spellchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndonesianStemmer
{
  private String current_word;
  public List<String> list_dictionaries = new ArrayList();
  
  public IndonesianStemmer()
  {
    try {
      InputStream fileInput = getClass().getResourceAsStream("rootword.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
      StringBuilder sb = new StringBuilder();
      String line = br.readLine();
      
      while (line != null) {
        sb.append(line);
        sb.append('\n');
        line = br.readLine();
      }
      String[] dictionary = sb.toString().split(" ");
      Collections.addAll(list_dictionaries, dictionary);
      
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("file not found");
    } catch (IOException e) {
      System.out.println("file not found");
    }
  }
  
  private boolean checkDictionary(String key)
  {
    if (list_dictionaries.contains(key)) {
      return true;
    }
    return false;
  }
  
  private void delInflectionSuffixes()
  {
    if (current_word.endsWith("lah")) {
      current_word = current_word.substring(0, current_word.length() - 3);
    }
    else if (current_word.endsWith("kah")) {
      current_word = current_word.substring(0, current_word.length() - 3);
    }
    else if (current_word.endsWith("ku")) {
      current_word = current_word.substring(0, current_word.length() - 2);
    }
    else if (current_word.endsWith("mu")) {
      current_word = current_word.substring(0, current_word.length() - 2);
    }
    else if (current_word.endsWith("nya")) {
      current_word = current_word.substring(0, current_word.length() - 3);
    }
  }
  
  private void delDerivationSuffixes()
  {
    if (current_word.endsWith("i")) {
      current_word = current_word.substring(0, current_word.length() - 1);
    }
    else if (current_word.endsWith("kan")) {
      current_word = current_word.substring(0, current_word.length() - 3);
    }
    else if (current_word.endsWith("an")) {
      current_word = current_word.substring(0, current_word.length() - 2);
    }
  }
  
  private void delDerivationPrefix()
  {
    if ((current_word.startsWith("di")) || (current_word.startsWith("ke")) || (current_word.startsWith("se"))) {
      current_word = current_word.substring(2);
    }
    if (!list_dictionaries.contains(current_word)) {
      if (current_word.startsWith("me")) {
        if (current_word.startsWith("meng")) {
          if (list_dictionaries.contains("k" + current_word.substring(4))) {
            current_word = ("k" + current_word.substring(4));
          } else if (current_word.substring(4, 5).matches("[gh]")) {
            current_word = current_word.substring(4);
          }
        } else if (current_word.startsWith("meny")) {
          if (list_dictionaries.contains("s" + current_word.substring(4))) {
            current_word = ("s" + current_word.substring(4));
          }
        } else if (current_word.startsWith("mem")) {
          if (current_word.substring(3, 4).matches("[bpf]")) {
            current_word = current_word.substring(3);
          }
        } else if (current_word.startsWith("men")) {
          if (current_word.substring(3, 4).matches("[cdj]")) {
            current_word = current_word.substring(3);
          } else if (list_dictionaries.contains("t" + current_word.substring(3))) {
            current_word = ("k" + current_word.substring(3));
          }
        } else {
          current_word = current_word.substring(2);
        }
      }
      else if (current_word.startsWith("te")) {
        if (current_word.startsWith("ter")) {
          if (list_dictionaries.contains("r" + current_word.substring(3))) {
            current_word = ("r" + current_word.substring(3));
          } else {
            current_word = current_word.substring(3);
          }
        }
      }
      else if (current_word.startsWith("be")) {
        if (current_word.substring(3, 5).matches("er")) {
          current_word = current_word.substring(2);
        } else if (list_dictionaries.contains(current_word.substring(2))) {
          current_word = current_word.substring(2);
        } else {
          current_word = current_word.substring(3);
        }
      }
      
      if ((!list_dictionaries.contains(current_word)) && 
        (current_word.startsWith("pe"))) {
        if (list_dictionaries.contains(current_word.substring(2))) {
          current_word = current_word.substring(2);
        } else if (current_word.startsWith("per")) {
          current_word = current_word.substring(3);
        } else if (current_word.startsWith("pem")) {
          if (current_word.substring(3, 4).matches("[bfv]")) {
            current_word = current_word.substring(3);
          } else if (list_dictionaries.contains("p" + current_word.substring(3))) {
            current_word = ("p" + current_word.substring(3));
          }
        } else if (current_word.startsWith("peny")) {
          if (list_dictionaries.contains("s" + current_word.substring(4))) {
            current_word = ("s" + current_word.substring(4));
          }
        } else if (current_word.startsWith("pen")) {
          if (list_dictionaries.contains("t" + current_word.substring(3))) {
            current_word = ("t" + current_word.substring(3));
          } else if (current_word.substring(3, 4).matches("[jdcz]")) {
            current_word = current_word.substring(3);
          }
        }
      }
    }
  }
  
  public void delReduplikasi()
  {
    String firstWord = null;
    String secondWord = null;
    
    if (current_word.contains("-")) {
      firstWord = current_word.substring(0, current_word.indexOf("-"));
      secondWord = current_word.substring(current_word.indexOf("-") + 1);
      
      if (list_dictionaries.contains(secondWord)) {
        current_word = secondWord;
      } else if (list_dictionaries.contains(firstWord)) {
        current_word = firstWord;
      } else {
        current_word = secondWord;
      }
    }
  }
  
  public String findRootWord(String key) {
    current_word = key;
    
    if (checkDictionary(current_word) == true) {
      return key;
    }
    
    delInflectionSuffixes();
    delDerivationSuffixes();
    delDerivationPrefix();
    delReduplikasi();
    if (checkDictionary(current_word)) {
      key = current_word;
      return key;
    }
    return null;
  }
}

