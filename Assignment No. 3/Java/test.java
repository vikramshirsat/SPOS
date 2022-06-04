import java.io.*; 
import java.util.*; 
public class test 
{ 
  public static void main(String[] args)throws Exception 
  { 
	FileReader f1 = new FileReader("input");
	BufferedReader br1 = new BufferedReader(f1); 
	FileWriter myWriter4 = new FileWriter("IntermediateCode");
	String line1, macro="", MNT="MNTC\tMacro\tMDTC", MDT="MDTC\tMacro Instruction", ALA="Index\tDummy Argument", macro_name_line;
	int MNTC=1, MDTC=1, INDEX=1, i;
	Hashtable<String, Integer> hash_table = new Hashtable<String, Integer>();
	
	while ((line1 = br1.readLine()) != null) 
	{
		String words[] = line1.split(" ", 2);

		if (words[0].compareTo("MACRO")==0)								//Start of MACRO
		{
			macro_name_line = br1.readLine();							//To skip name of macro line from appending
			String macro_name[] = macro_name_line.split(" ", 2);
			MNT += "\n" + Integer.toString(MNTC) + "\t" + macro_name[0] + "\t" + Integer.toString(MDTC);		//To store name of macro to MNT
			MNTC++;														
			MDT += "\n" + Integer.toString(MDTC) + "\t" + macro_name_line;		//To store macro name line to MDT
			MDTC++;

			String arguments[] = macro_name[1].split(",", 2);
			for (i=0;i<arguments.length;i++)
			{
				ALA += "\n" + Integer.toString(INDEX) + "\t" + arguments[i];
				hash_table.put(arguments[i],INDEX);
				INDEX++;
			}

			while (line1.compareTo("MEND") != 0)
			{
				line1 = br1.readLine();
				try
				{
					String temp1[] = line1.split(",", 2);
					int index1 = hash_table.get(temp1[1]);
 					MDT += "\n" + Integer.toString(MDTC) + "\t" + temp1[0] + ", #" + hash_table.get(temp1[1]);
					MDTC++;
				}
				catch(Exception e)
				{
					MDT += "\n" + Integer.toString(MDTC) + "\t" + line1;
					MDTC++;
				}		
			}			
		}

		else
		{
			myWriter4.write(line1+"\n");
			System.out.println(line1);                                                           
		}
	}
	FileWriter myWriter1 = new FileWriter("MNT");
	FileWriter myWriter2 = new FileWriter("MDT");
	FileWriter myWriter3 = new FileWriter("ALA");
    myWriter1.write(MNT);
    myWriter2.write(MDT);
    myWriter3.write(ALA);
    myWriter1.close();
    myWriter2.close();
    myWriter3.close();
    myWriter4.close();
    System.out.println("\n"+MNT);
    System.out.println("\n"+MDT);
    System.out.println("\n"+ALA);
  } 
}
