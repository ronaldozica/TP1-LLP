
package interpreter.expr;

import interpreter.value.Value;

public class FunctionExpr extends Expr{

    private Expr expr;
    private FunctionOp op;

    public FunctionExpr(Expr expr, FunctionOp op, int line) {
        super(line);
        this.expr = expr;
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
