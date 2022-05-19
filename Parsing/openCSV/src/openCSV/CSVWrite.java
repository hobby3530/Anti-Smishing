package openCSV;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVWriter;

public class CSVWrite {

  private String filename = "output/parsing_testcsv.csv";

  public CSVWrite() {}

  public void writeCsv(List<String[]> data) {
    try {
      CSVWriter cw = new CSVWriter(new FileWriter(filename, false));
      Iterator it = data.iterator();
      try {
        while (it.hasNext()) {
          String[] s = (String[])it.next();
          
//          s[0] = s[0].substring(9);
//          cw.writeNext(s);
          
          //openphish.csv url ÆÄ½Ì
          if(s[0].contains("http")) {
        	  if(s[0].contains("Mozi") || s[0].contains("mozi"))
        		  continue;
        	  String[] result1 = s[0].split("://");
        	  String[] result2 = result1[1].split("/");
        	  String[] result = new String[7];
        	  if(result2[0].contains("www")) {
        		  result2[0] = result2[0].substring(4);
        	  }
        	  //testcsv.csv url ÆÄ½ÌÇÒ ¶§ Ãß°¡
        	  if(result2[0].contains(":")) {
                  if(result2.length != 1)
                      result2[0] = result2[1];
              }
        	  //
        	  result[0] = result2[0];
        	  cw.writeNext(result);
        	  //System.out.println(result[0]);
          }
      }
      } finally {
        cw.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}