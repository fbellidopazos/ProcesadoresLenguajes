semanticoTxt = open("semanticoTxt.txt","w")
switchFunciones = open("switchFunciones.txt","w")

switchFunciones.write("switch (regla) {")

for i in range(0,50):
    switchFunciones.write(f'case {i}:\nresHashMap=function{i}();\nbreak;')
    semanticoTxt.write("public HashMap<String,Object> function"+str(i)+"(){\n HashMap<String,Object> resHashMap = new HashMap<>();resHashMap.put(\"tipo\", null);resHashMap.put(\"numeros\", null);resHashMap.put(\"tipos\",null);  resHashMap.put(\"returnType\", null); resHashMap.put(\"hasReturn\",null);resHashMap.put(\"valor\",null);\nresHashMap.put(\"id\",null);\n\nreturn resHashMap;\n}\n")
switchFunciones.write("default:\nbreak;\n}")
