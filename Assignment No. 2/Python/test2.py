import csv
#########################################################################################
code = open("pass2_input").readlines()[1:]
####	Now, this is a list. Each element in this list represents a line in assembly code.
machine_code = ''
for i in code:
	words_in_line = i.split('\t')
	instruction = words_in_line[1]
	if instruction[1:3]=="IS":
		machine_code += "\n"+words_in_line[0]+"\t(0"+instruction[4:5]+")\t"
		register = words_in_line[2]
		machine_code += "(0"+register[4:5]+")\t"
		sym_lit = words_in_line[3]
		if sym_lit[1:2]=='S':
			symbol_no = sym_lit[3:4]
			fo1 = open("symtab")
			fo1 = csv.reader(fo1,delimiter='\t')
			for row in fo1:
				if row[0] == symbol_no:
					machine_code += "("+row[2]+")"
		elif sym_lit[1:2]=='L':
			literal_no = sym_lit[3:4]
			fo1 = open("littab")
			fo1 = csv.reader(fo1,delimiter='\t')
			for row in fo1:
				if row[0] == literal_no:
					machine_code += "("+row[2]+")"
	
	if instruction[1:6]=="AD,#4":				#LTORG
		fo1 = open("littab")
		fo1 = csv.reader(fo1,delimiter='\t')
		for row in fo1:
			machine_code += "\n(00)\t(00)\t(00"+row[1]+")"
		
print(machine_code)
