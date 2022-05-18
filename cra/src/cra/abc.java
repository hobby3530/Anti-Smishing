package cra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

public class abc {
	//int count = 0;
	
	public void run() {
		// csv파일의 절대 경로 입력
		File csv = new File("C:\\Users\\LG-U\\OneDrive\\바탕 화면\\testcsv.csv");
		BufferedWriter bw = null;
		
		try {
			//new FileWriter(csv, true)에서 true를 입력하면 이어쓰기, false를 입력하면 새로 덮어쓰기임
			bw = new BufferedWriter(new FileWriter(csv, true));
			
			//i<x에서 x 값에 페이지 수 입력, 약 20000페이지임
			for(int i=0; i<10; i++) {
			URL url = new URL("https://urlhaus.abuse.ch/browse/page/"+i+"/");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "euc-kr"));
			String line;
			int check_line=0;
			
			while((line = reader.readLine()) != null) {
				if(line.contains("<a href=\"/url/"))
					check_line=1;
				else
					check_line=0;
				if(check_line == 1) {
						//count++;
						String temp = line.split(">")[5].split("<")[0];
						temp = temp.trim();
						bw.write(temp);
						bw.newLine();
						//System.out.println(temp + count);
					}	
				}
			}
		} catch(Exception e) {e.printStackTrace();}
		finally {
			try {
				if(bw != null) {
					bw.flush();
					bw.close();
				} 
			} catch(Exception e) {e.printStackTrace();}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		abc aa = new abc();
		aa.run();

	}

}
