package com.spandigital;
import java.util.*;
import java.util.stream.Stream;
import java.io.*;

public class LeagueRanking {

public static HashMap<String,Integer>  Mapper(HashMap<String,Integer> m1, String Team, int score) {
	/* In this Method, we check if the Team details are already present, 
	 * if the details are present, we get the old value and add the score value passed from earlier Method
	 * if not preset then the Team details and score are put in to the HashMap 
	 */
	if(m1.containsKey(Team)) {
		m1.put(Team,(int)m1.get(Team)+score);
	}
	else {
		m1.put(Team, score);
	}
	return m1;
}
	
public static void  ProcessLine(HashMap<String,Integer> map, String s) {
	/* Read each line and split it by Comma Delimeter, 
	 * Further Split the File with space. as score is space delimeted so as the name
	 * So a name can have space in between as well. 
	 * concatenate the names which was space separated, separate out the score for both teams. 
	 * If Team1 wins then call the Mapper with Team1 score as 3 and Team2 as 0
	 * If Team2 wins then call the Mapper with Team1 score as 0 and Team2 score as 3
	 * If the Match is a tie, call Mapper with Team1 and Team2 score as 1 each. 	 * 
	 */
	String[] s1=s.split(",");
	String res="";
	String[] s3=new String[2];	
	String Team1="",Team2="";
	int score1=0,score2=0; 
	for(int i=0;i<s1.length;i++) {
		res="";
		String[] s2=s1[i].split(" ");
		for(int j=0;j<s2.length;j++) {
			if(j==s2.length-1) {
				if (s2[j]!= null) {
					if(i==0) {
						Team1=res; score1=Integer.parseInt(s2[j]); 
					}
					if(i==1) {
						Team2=res;score2=Integer.parseInt(s2[j]);
					}			
				}
			}
			else {
				if (s2[j]!= null) {
					res+=s2[j]+" ";
					}
			}			 			
		}
	}
	if(score1 > score2)  {
		map=Mapper(map,Team1,3); map=Mapper(map,Team2,0);
	}
	if ( score2 > score1 ) {
		map=Mapper(map,Team1,0);map=Mapper(map,Team2,3);	
	}
	if (score1 == score2 )  {
		map=Mapper(map,Team1,1);
		map=Mapper(map,Team2,1);
	}
}
public static void FileProcessing(HashMap<String,Integer> map ,String fname) {
	// The Method is to process the File that is entered. 
	try  
	{  
			//the file to be opened for reading  
		FileInputStream fis=new FileInputStream(fname);       
		Scanner sc=new Scanner(fis);    //file to be scanned  
			//returns true if there is another line to read  
		while(sc.hasNextLine())  
		{  
			ProcessLine(map,sc.nextLine()) ;   //returns the line 
		}  
			sc.close();     //closes the scanner  
	} 
	
	catch(IOException e)  
	{  
		e.printStackTrace();   // Throws Exception if the file is not present. 
	}
}

public static void sortandDisplay(HashMap<String,Integer> map) {
	// This method is the final method to sort the HashMap in descending order or Score and Ascenging order of Team name.
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
	System.out.println("Points  Table ");
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
	map.entrySet()
	  .stream()
	  .sorted(Map.Entry.comparingByKey())
	  .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()));
	  //.forEach(System.out::println); // Sort in descending order of Value / Ascending order of Key
	  for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
		    System.out.println(entry.getKey()+" , "+entry.getValue()+"pts");
		}
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
	System.out.println("End of Points  Table ");
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
}

public static void main(String[] args) throws IOException {
	HashMap<String,Integer> map = new HashMap<String,Integer>();
	Scanner in = new Scanner(System.in);	
	System.out.print("Enter the FileName with complete path: ");
	String fname = in.nextLine();
	System.out.println("File name to be processed: " + fname); 
	in.close();
	FileProcessing(map,fname); // Call File Processing Method 
	sortandDisplay(map); // Call the sort and Display Method 

}
}