public class test2 {
    public static void main(String[] args) {
        TablaSimbolos TS_test=new TablaSimbolos("TS_Test");
        TS_test.showTable();

        TS_test.newInsert("Test");
        TS_test.newInsert("Test2");

        TS_test.showTable();
    }
}
