import csv
fo_code      =  open('assembly_code')
fo_tables    =  open('tables')
tables       =  csv.reader(fo_tables)
#########################################################
code = fo_code.read()
code = code.replace(":"," ")
code = code.replace(","," ")
code = code.replace("'","")
####	Aata code variable madhe clean assembly code aahe
fo_code.seek(0)
first_line = fo_code.readline()
first_line_words = first_line.split()

starting_address = int(first_line_words[1])
location_counter = starting_address-1

list_of_lines = code.split('\n')
list_of_lines.pop()
####	Here i represents line number. Eg. 1st line, 2nd line, etc. Hence, i is string.
####	Now the matching starts
str1=''
SYMTAB_str=""
LITTAB_str=""
literal_count=0
symbol_count=0
sym_count=0
values=[]
for i in list_of_lines:
	words_in_line = i.split()
	first_word = words_in_line[0]

	flag=0
	fo_tables.seek(0)
	for row in tables:
		if first_word == row[0]:
			flag=1
			if row[1]=="AD":
				str1 += "\n---\t(AD,#"+row[2]+")"
			
			elif row[1] == "IS":
				location_counter += 1
				str1 += "\n"+str(location_counter) + "\t(IS,"+row[2]+")\t"
				
				if first_word=="READ":
					symbol_count += 1
					str1 += "(S,"+str(symbol_count)+")"
					break
				second_word = words_in_line[1]
				third_word  = words_in_line[2]
				
				for row in tables:
					if second_word == row[0]:
						str1 += "(RG,"+row[2]+")"+"\t"
						
						flag1 = 0
						for row in tables:
							if third_word == row[0]:			#Matlab agar second operand register hua toh
								str1 += "(RG,"+row[2]+")"
								flag1=1							
						if flag1==0:		
							if "=" in third_word:				#Matlab agar second operand literal raha toh
								if str(third_word[1:]) not in values:
									literal_count += 1
									str1 += "(L,"+str(literal_count)+")"
									LITTAB_str += "\n"+str(literal_count)+"\t"+str(third_word[1:])+"\t"+str(location_counter+1)
									values.append(third_word[1:])
									location_counter += 1
								else:
									str1 += "(L,"+str(literal_count)+")"		# Ismein thoda locha hai...Search from stored LITTAB						
							else:								#Matlab agar second operand symbol raha toh
								symbol_count += 1
								str1 += "(S,"+str(symbol_count)+")"
																
	if flag==0:													#It means if first word is symbol
		sym_count += 1
		second_word = words_in_line[1]
		
		if second_word == "DC":
			third_word = words_in_line[2]
			location_counter += 1
			str1 += "\n"+str(location_counter)+"\t(DL,1)\t(C,"+third_word+")"
			SYMTAB_str += "\n"+str(sym_count)+"\t"+first_word+"\t"+str(location_counter)
		elif second_word == "DS":
			third_word = words_in_line[2]
			location_counter += 1
			str1 += "\n"+str(location_counter)+"\t(DL,2)\t(C,"+third_word+")"
			SYMTAB_str += "\n"+str(sym_count)+"\t"+first_word+"\t"+str(location_counter)
			location_counter += int(third_word) - 1
		
print("Intermediate Code :\n"+'--------------------------------------------------\nADDR\t(C,MC)\t(REG/C)\t(S/L,NO)\n--------------------------------------------------'+str1+"\n\n")
print("Symbol Table :\n"+'------------------------------\nNO.\tNAME\tADDRESS\n------------------------------'+SYMTAB_str+"\n\n")
print("Literal Table :\n"+'-----------------------------\nNO.\tVALUE\tADDRESS\n-----------------------------'+LITTAB_str+"\n\n")

open("pass2_input",'w').write(str1)
open("symtab",'w').write(SYMTAB_str)
open("littab",'w').write(LITTAB_str)
