javac -d bin src/*.java src/DataStructures/*.java
cd bin
jar cfe Analizador-JSPDL.jar main *.class DataStructures/*.class
jar cfe Analizador-JSPDL-allTests.jar todasPruebas *.class DataStructures/*.class
move Analizador-JSPDL.jar ../JAR/
move Analizador-JSPDL-allTests.jar ../JAR/