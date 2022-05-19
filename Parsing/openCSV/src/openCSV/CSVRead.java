package openCSV;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class CSVRead {

  private String filename = "input/testcsv.csv";

  public CSVRead() {}

  public List<String[]> readCsv() throws CsvValidationException {

    List<String[]> data = new ArrayList<String[]>();

    try {
      // CSVReader reader = new CSVReader(new FileReader(filename), '\t');
      // UTF-8
      CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "EUC-KR"));
      String[] s;

      while ((s = reader.readNext()) != null) {
    	  //System.out.println(s[0]);
        data.add(s);
      }
      reader.close();
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return data;
  }
}