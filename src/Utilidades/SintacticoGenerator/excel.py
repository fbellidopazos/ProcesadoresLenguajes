from openpyxl import load_workbook,workbook,cell


workbook = load_workbook(filename="Sint_Ascendente.xlsx")
#print(workbook.sheetnames)

"""
Tabla Accion
"""

sheet = workbook["Accion"]

file = open("toJavaAccion.txt","w")
file2 = open("Errores.txt","w")

file.write("Pair<String, Integer>[][] tablaAccion=new Pair[105][29];\n")
for i in range(2,107):
    for j in range(2,31):

        content = sheet.cell(row=i, column=j).value
        if(content != None):
            if(content[0]=="s"):
                file.write(f"tablaAccion[{i-2}][{j-2}] = new Pair<>(\"S\", {(int)(content[1::])});\n")
            elif(content[0]=="r"):
                file.write(f"tablaAccion[{i-2}][{j-2}] = new Pair<>(\"R\", {(int)(content[1::])});\n")
            else:
                file.write(f"tablaAccion[{i-2}][{j-2}] = new Pair<>(\"ACC\", null);\n")
        else:
            k=i-2
            firsts={
                "Z":"if,let,identificador,alert,input,return,do,function or nothing",
                "P":"if,let,identificador,alert,input,return,do,function or nothing",
                "F":"function",
                "I":"function",
                "J":"(",
                "G":"{",
                "H":"number,boolean,string or nothing",
                "A":"number,boolean,string or nothing",
                "K":", or nothing",
                "C":"if,let,identificador,alert,input,return,do or nothing",
                "S":"identificador,alert,input,return",
                "B":"if,let,identificador,alert,input,return,do",
                "T":"number,boolean,string",
                "E":"identificador,( ,cteEntera,cadena",
                "R":"identificador,( ,cteEntera,cadena",
                "U":"identificador,( ,cteEntera,cadena",
                "V":"identificador,( ,cteEntera,cadena",
                "W":"identificador,( ,cteEntera,cadena",
                "L":"identificador,( ,cteEntera,cadena or nothing",
                "Q":", or nothing",
                "X":"identificador,( ,cteEntera,cadena or nothing"
            }

            switch = {
                0: "Expected: if,let,identificador,alert,input,return,do,function or nothing",
                1: "Expected: End of Program",
                2: "Expected: if,let,identificador,alert,input,return,do,function or nothing",
                3: "Expected: if,let,identificador,alert,input,return,do,function or nothing",
                4: "Expected: (",
                5: "Expected: number,boolean,string",
                6: "Nothing was expected",
                7: "Expected: {",
                8: "Expected: (",
                9: "Expected: =",
                10: "Expected: (",
                11: "Expected: (",
                12: "Expected: identificador,abrirParentesis,cteEntera,cadena or nothing",
                13: "Expected: number,boolean,string or nothing",
                14: "Nothing was expected",
                15: "Nothing was expected",
                16: "Expected: identificador,( ,cteEntera,cadena",
                17: "Expected: identificador",
                18: "Nothing was expected",
                19: "Nothing was expected",
                20: "Nothing was expected",
                21: "Expected: if,let,identificador,alert,input,return,do or nothing",
                22: "Expected: {",
                23: "Expected: number,boolean,string or nothing",
                24: "Expected: identificador,( ,cteEntera,cadena",
                25: "Expected: identificador,( ,cteEntera,cadena or nothing",
                26: "Expected: identificador,( ,cteEntera,cadena or nothing",
                27: "Expected: identificador,( ,cteEntera,cadena",
                28: "Expected: identificador",
                29: "Expected: ;",
                30: "Expected: operadorLogico2",
                31: "Expected: operadorLogico1",
                32: "Expected: operadorRelacional1 , operadorRelacional2",
                33: "Expected: operadorAritmetico1, operadorAritmetico2",
                34: "Nothing was expected",
                35: "Expected: (,||,&&,==,!=,+,- or nothing",
                36: "Expected: identificador,( ,cteEntera,cadena",
                37: "Nothing was expected",
                38: "Nothing was expected",
                39: "Expected: identificador",
                40: "Nothing was expected",
                41: "Expected: )",
                42: "Expected: ;",
                43: "Expected: }",
                44: "Expected: if,let,identificador,alert,input,return,do or nothing",
                45: "Nothing was expected",
                46: "Expected: if,let,identificador,alert,input,return,do or nothing",
                47: "Expected: )",
                48: "Expected: identificador",
                49: "Expected: ;",
                50: "Expected: )",
                51: "Expected: || // , or nothing",
                52: "Expected: ;",
                53: "Expected: ), ||",
                54: "Expected: )",
                55: "Nothing was expected",
                56: "Expected: identificador,( ,cteEntera,cadena",
                57: "Expected: identificador,( ,cteEntera,cadena",
                58: "Expected: identificador,( ,cteEntera,cadena",
                59: "Expected: identificador,( ,cteEntera,cadena",
                60: "Expected: identificador,( ,cteEntera,cadena",
                61: "Expected: identificador,( ,cteEntera,cadena",
                62: "Expected: identificador,( ,cteEntera,cadena or nothing",
                63: "Expected: )",
                64: "Nothing was expected",
                65: "Expected: identificador,alert,input,return",
                66: "Nothing was expected",
                67: "Expected: While",
                68: "Nothing was expected",
                69: "Expected: }",
                70: "Nothing was expected",
                71: ", or nothing",
                72: "Nothing was expected",
                73: "Expected: ;",
                74: "Nothing was expected",
                75: "Expected: identificador,( ,cteEntera,cadena",
                76: "Nothing was expected",
                77: "Expected: ;",
                78: "Expected: ;",
                79: "Expected: &&",
                80: "Expected: ==, !=",
                81: "Expected: +, -",
                82: "Expected: +, -",
                83: "Nothing was expected",
                84: "Nothing was expected",
                85: "Expected: )",
                86: "Nothing was expected",
                87: "Nothing was expected",
                88: "Expected: (",
                89: "Nothing was expected",
                90: "Nothing was expected",
                91: "Expected: number,boolean,string",
                92: "Nothing was expected",
                93: "|| //, or nothing",
                94: "Nothing was expected",
                95: "Nothing was expected",
                96: "Nothing was expected",
                97: "Expected: identificador,( ,cteEntera,cadena",
                98: "Expected: identificador",
                99: "Nothing was expected",
                100: "Expected: )",
                101: "Expected: , or nothing",
                102: "Expected: ;",
                103: "Nothing was expected",
                104: "Nothing was expected"
            }




            file2.write(f"tablaAccion[{i-2}][{j-2}] = new Pair<>(\"E\", \"{switch.get(k)}\");\n")
file.close()       
file2.close()
        

file = open("toJavaAccionHashMap.txt","w")
file.write("HashMap<String, Integer> aplicacionTerminal = new HashMap<>();\n")
for j in range(1,32):
    content = sheet.cell(row=1, column=j).value
    if(content != None):
            file.write(f"aplicacionTerminal.put(\"{content}\", {j-2});\n")



file.close()
"""
Tabla GoTo
"""

sheet = workbook["GoTo"]

file = open("toJavaGoTo.txt","w")


file.write("int[][] tablaGoTo = new int[105][21];\n")
for i in range(2,150):
    for j in range(1,40):

        content = sheet.cell(row=i, column=j).value
        if(content != None):
            file.write(f'tablaGoTo[{i-2}][{j-1}] = {(int)(content)};\n')
file.close()


file = open("toJavaGoToHashMap.txt","w")
file.write("HashMap<String, Integer> aplicacionNoTerminal = new HashMap<>();\n")
for j in range(1,32):
    content = sheet.cell(row=1, column=j).value
    if(content != None):
            file.write(f"aplicacionNoTerminal.put(\"{content}\", {j-1});\n")

file.close()



'''
https://realpython.com/openpyxl-excel-spreadsheets-python/
'''