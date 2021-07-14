
package interpreter.expr;

import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import java.util.Random;
import java.util.Scanner;

public class InputExpr extends Expr{

    private InputOp op;

    public InputExpr(InputOp op, int line) {
        super(line);
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        if(op == InputOp.GetsOp){
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            Value valor = new StringValue(inputString);
            return valor;
        }
        else{
            Random rand = new Random();
            rand.setSeed (System.currentTimeMillis());
            int valorRandom = rand.nextInt();
            Value valor = new IntegerValue(valorRandom);
            return valor;
        }
    }

}
