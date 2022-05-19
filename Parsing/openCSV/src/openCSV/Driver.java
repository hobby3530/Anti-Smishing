package openCSV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;
public class Driver {

  public static void main(String[] args) throws CsvValidationException {

    List<String[]> data = new ArrayList<String[]>();
    CSVRead read = new CSVRead();
    data = read.readCsv();
    Iterator<String[]> it = data.iterator();

    while (it.hasNext()) {
      String[] array = (String[]) it.next();
//      for (String s : array) {
//        System.out.print(s + " ");
//      }
//      System.out.print("\n");
    }

    // �̰��� �����͸� ���Ϸ� ���� ���� �ڵ�
    CSVWrite cw = new CSVWrite();
    cw.writeCsv(data);
  }
}