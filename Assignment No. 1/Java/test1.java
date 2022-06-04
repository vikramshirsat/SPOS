import java.io.*;
import java.util.*;
class test1
	{
	public static void main (String[] args) throws Exception
	{
		FileReader f1=new FileReader("imperative_statements");
		BufferedReader br1=new BufferedReader(f1);
		FileReader f2=new FileReader("assembler_directives");
		BufferedReader br2=new BufferedReader(f2);
		FileReader f3=new FileReader("declarative_statements");
		BufferedReader br3=new BufferedReader(f3);
		FileReader f4=new FileReader("registers");
		BufferedReader br4=new BufferedReader(f4);
		FileReader f5=new FileReader("assembly_code");
		BufferedReader br5=new BufferedReader(f5);
///////////////////////////////////////////////////////////////////////////////////////
		String line1,intermediate_code="",symtab_write="NO.\tNAME\tADDR",littab_write="NO.\tVALUE\tADDR";
		int location_counter=300,starting_address,literal_count=1,symbol_count=1,symbol_count_file=1;
		Hashtable<String,String> IS = new Hashtable<String,String>();
		Hashtable<String,String> AD = new Hashtable<String,String>();
		Hashtable<String,String> DL = new Hashtable<String,String>();
		Hashtable<String,String> REG = new Hashtable<String,String>();
		Hashtable<String,String> symbols = new Hashtable<String,String>();
		Hashtable<String,String> literals = new Hashtable<String,String>();
///////////////////////////////////////////////////////////////////////////////////////		
		while ( (line1=br1.readLine()) != null )
		{
			String words[] = line1.split(",",3);
			IS.put(words[0],words[1]);
		}
		while ( (line1=br2.readLine()) != null )
		{
			String words[] = line1.split(",",2);
			AD.put(words[0],words[1]);
		}
		while ( (line1=br3.readLine()) != null )
		{
			String words[] = line1.split(",",2);
			DL.put(words[0],words[1]);
		}
		while ( (line1=br4.readLine()) != null )
		{
			String words[] = line1.split(",",2);
			REG.put(words[0],words[1]);
		}
///////////////////////////////////////////////////////////////////////////////////////
		//Abhi actual code starts
		while ( (line1=br5.readLine()) != null )
		{
			String words[] = line1.split(" ",2);
			if (words[0].compareTo("START")==0)						//To get starting address for LC
				location_counter=Integer.parseInt(words[1]);
			if (AD.containsKey(words[0]))							//Assembler Directive raha toh
			{
				intermediate_code += "\n---\t(AD,#"+AD.get(words[0])+")";
			}
			else if (IS.containsKey(words[0]))
			{
				String operands[]=words[1].split(",",2);
				if (operands[1].contains("="))						//Literal raha toh
				{
					String value=operands[1].substring(2,3);		//To extract 2 from '=2'
					if (!(literals.containsKey(value)))
					{
						literals.put(value,Integer.toString(literal_count));
						littab_write += "\n"+Integer.toString(literal_count)+"\t"+value+"\t"+Integer.toString(location_counter+1);
						literal_count++;
					}
					intermediate_code += "\n"+Integer.toString(location_counter)+"\t(IS,"+IS.get(words[0])+")\t"+REG.get(operands[0])+"\t(L,"+literals.get(value)+")";
					location_counter+=2;
				}
				else 									//If not literal, then its symbol
				{
					String symbol_name=operands[1];
					if (!(symbols.containsKey(symbol_name)))
					{
						symbols.put(symbol_name,Integer.toString(symbol_count));
						symbol_count++;
					}
					intermediate_code += "\n"+Integer.toString(location_counter)+"\t(IS,"+IS.get(words[0])+")\t"+REG.get(operands[0])+"\t(S,"+symbols.get(symbol_name)+")";;
					location_counter+=1;
				}
				
			}
			else 										//If not IS, AD then its symbol declaration
			{
				String declaration[]=words[1].split(" ",2);			//declaration now contains DS or DC
				intermediate_code+="\n"+Integer.toString(location_counter)+"\t(DL,"+DL.get(declaration[0])+")\t(C,"+declaration[1]+")";
				symtab_write+="\n"+Integer.toString(symbol_count_file)+"\t"+words[0]+"\t"+location_counter;
				location_counter++;
				symbol_count_file++;
			}
		}
/////////////////////////////////////////////////////////////////////////////////////
		FileWriter fw1 = new FileWriter("SYMTAB");
		FileWriter fw2 = new FileWriter("LITTAB");
		fw1.write(symtab_write);
		fw2.write(littab_write);
		fw1.close();
		fw2.close();

		System.out.println(intermediate_code);
		System.out.println("\nLiteral Table :\n"+littab_write);
		System.out.println("\nSymbol Table :\n"+symtab_write);
	}
}