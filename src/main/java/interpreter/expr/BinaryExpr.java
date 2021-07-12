
package interpreter.expr;

import interpreter.value.IntegerValue;
import interpreter.value.Value;

public class BinaryExpr extends Expr{

    private Expr left;
    private BinaryOp op;
    private Expr right;

    public BinaryExpr(Expr left, BinaryOp op, Expr right, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }
    
    @Override
    public Value<?> expr() {
        int aux;
        
        aux = (int) this.left.expr().value() + (int) this.left.expr().value();     // add
        
        Value valor = new IntegerValue(aux);
        return valor;
    }

}
