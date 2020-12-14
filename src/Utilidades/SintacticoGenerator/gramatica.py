
txt = open("toJavaGramaticaDepurada.txt","w")


txt.write("Pair<String, Integer>[] gramaticaDepurada=new Pair<>[50];\n")


with open('gramatica.txt') as file:
    line = file.readline()
    i=0
    while line:
        
        if(line != "\n"):
            line=line.replace('\n','')
            if("lambda" in line):
                txt.write(f'gramaticaDepurada[{i}] = new Pair<String, Integer>("{data[0]}", {0});\n')
            else:   
                data = line.split(' ')

                txt.write(f'gramaticaDepurada[{i}] = new Pair<String, Integer>("{data[0]}", {len(data)-2});\n')
            i+=1
            
        line = file.readline()
        
        #print(line.split(' '))