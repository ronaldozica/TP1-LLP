
package interpreter.expr;

import interpreter.value.Value;
import java.util.List;

public class ArrayExpr extends Expr{

    private List<Expr> exprs;

    public ArrayExpr(List<Expr> exprs, int line) {
        super(line);
        this.exprs = exprs;
    }
    
    @Override
    public Value<?> expr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
