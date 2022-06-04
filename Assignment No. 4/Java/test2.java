import java.io.*; 
import java.util.*; 
public class test2
{ 	
	public static void main(String[] args)throws Exception 
  	{ 
		FileReader f1 = new FileReader("IntermediateCode");
		BufferedReader br1 = new BufferedReader(f1);
		FileReader f2 = new FileReader("MDT");
		BufferedReader br2 = new BufferedReader(f2);
		FileReader f3 = new FileReader("MNT");
		BufferedReader br3 = new BufferedReader(f3);
		int INDEX=1, i;
		String line1, final_code="", new_ALA = "INDEX\tActual Args";
		Hashtable<Integer, String> ALA_pass2 = new Hashtable<Integer, String>();
		Hashtable<String, Integer> macro_names = new Hashtable<String, Integer>();
		Hashtable<Integer, String> definition = new Hashtable<Integer, String>();

		while ((line1 = br3.readLine()) != null)
		{
			String temp[] = line1.split("\t",3);
			macro_names.put(temp[1],Integer.parseInt(temp[2]));		//Copy Name and MDTC to MacroNames Dictionary
		}

		while ((line1 = br2.readLine()) != null)
		{
			String temp1[] = line1.split("\t",3);
			definition.put(Integer.parseInt(temp1[0]),temp1[1]);	//Copy MDTC and Definition
		}

		while ((line1 = br1.readLine()) != null)
		{
			String words[] = line1.split(" ", 2);
			if (macro_names.containsKey(words[0]))
			{
				String arguments[] = words[1].split(",",2);
				for (i=0;i<arguments.length;i++)
				{
					if (!(ALA_pass2.containsValue(arguments[i])))
					{
						ALA_pass2.put(INDEX,arguments[i]);
						new_ALA += "\n"+Integer.toString(INDEX)+"\t"+arguments[i];
						INDEX++;
					}
				}
				int macro_MDTC = macro_names.get(words[0])+1;
				for (i=macro_MDTC;;i++)
				{
					String temporary = definition.get(i);
					String to_split[] = temporary.split(" ",2);
					String to_replace[] = to_split[1].split(",",2);
					int jiska_argument_chahiye = Integer.parseInt(to_replace[1].substring(1));
					final_code += to_split[0]+" "+to_replace[0]+","+ALA_pass2.get(jiska_argument_chahiye)+"\n";
					if (definition.get(i+1).compareTo("MEND")==0)
						break;
				}
			}
			else
				final_code += line1+"\n";
		}
		System.out.println(final_code);System.out.println("\n"+new_ALA);
	}
}