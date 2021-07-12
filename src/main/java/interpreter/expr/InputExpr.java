
package interpreter.expr;

import interpreter.value.Value;

public class InputExpr extends Expr{

    private InputOp op;

    public InputExpr(InputOp op, int line) {
        super(line);
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        if(op == InputOp.GetsOp){
            return null;
        }
        else{
            return null;
        }
    }

}
