package cra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class abc {
	int count = 0;
	public void run() {
		try {
			for(int i=0; i<10; i++) {
			URL url = new URL("https://urlhaus.abuse.ch/browse/page/"+i+"/");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "euc-kr"));
			String line;
			int check_line=0;
			
			while((line = reader.readLine()) != null) {
				if(line.contains("<a href=\"/url/"))
					check_line=1;
				else
				//if(line.contains("<a href=\"/marketindex/?tabSel=worldExchange#tab_section\""))
					check_line=0;
				if(check_line == 1) {
					count ++;
					//if(line.contains("<a href=\"/marketindex/exchangeDetail.naver?marketindexCd=")) {
						String temp = line.split(">")[5].split("<")[0];
						temp = temp.trim();
						System.out.println(temp + count);
					}
					//if(line.contains("<td>") && !line.contains("em")) {
					//	String temp = line.split(">")[1].split("<")[0];
					//	System.out.println(temp);
					//}
					//if(line.contains("<td>") && line.contains("em")) {
					//	String temp = line.split(">")[3].split("<")[0];
					//	System.out.print(temp);
					//	String temp2 = line.split(">")[5].split("<")[0];
					//	System.out.println(temp2);
					//	System.out.println();
					//}
				}
			//}
			}
		}
			catch(Exception e) {e.printStackTrace();}
		}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abc aa = new abc();
		aa.run();

	}

}
