package interpreter.util;

public class Utils {

    private Utils(){

    }
    public static void abort(int line){
        System.out.printf("%02d: Operação inválida\n", line);
        try { throw new Exception(); } catch(Exception e) { e.printStackTrace(); }
        System.exit(1);
    }

}
