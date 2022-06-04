import java.io.*;
import java.util.*;
class pass2
{
	public static void main(String[] args) throws Exception
	{
		FileReader f1 = new FileReader("pass2_input");
		BufferedReader br1=new BufferedReader(f1);
		FileReader f2 = new FileReader("littab");
		BufferedReader br2=new BufferedReader(f2);
		FileReader f3 = new FileReader("symtab");
		BufferedReader br3=new BufferedReader(f3);
		String line1,machine_code="",int_code="";
		int location_counter=300;
		Hashtable<String,String> code=new Hashtable<String,String>();
		Hashtable<String,String> symbols=new Hashtable<String,String>();
		Hashtable<String,String> literals=new Hashtable<String,String>();
///////////////////////////////////////////////////////////////////////////////////
		while ((line1=br2.readLine())!=null)
		{
			String words[]=line1.split(" ",3);
			literals.put(words[0],words[2]);
		}
		while ((line1=br3.readLine())!=null)
		{
			String words[]=line1.split(" ",3);
			symbols.put(words[0],words[2]);
		}
//////////////////////////////////////////////////////////////////////////////////
		while ((line1=br1.readLine())!=null)
		{
			String words[]=line1.split(" ",2);
			int_code+="\n"+line1;
			if((words[1].substring(1,3)).compareTo("IS")==0)
			{
				String temp[]=words[1].split(" ",3);
				if(temp[2].substring(1,2).compareTo("S")==0)					//Symbol raha toh
				{
					String symbol_no=temp[2].substring(3,4);					//(S,2) To get this '2'
					machine_code+="\n"+words[0]+"\t("+temp[0].substring(4,5)+")\t("+temp[1].substring(4,5)+")\t("+symbols.get(symbol_no)+")";
				}
				else if(temp[2].substring(1,2).compareTo("L")==0)				//Litral raha toh
				{
					String literal_no=temp[2].substring(3,4);					//(L,2) To get this '2'
					machine_code+="\n"+words[0]+"\t("+temp[0].substring(4,5)+")\t("+temp[1].substring(4,5)+")\t("+literals.get(literal_no)+")";
				}
			}
		}
		System.out.println("\n### Intermediate Code :"+int_code);
		System.out.println("\n### Required Machine Code :"+machine_code);
	}
}