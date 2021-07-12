
package interpreter.expr;

public class CompositeBoolExpr extends BoolExpr{
    
    private BoolExpr left;
    private BoolOp op;
    private BoolExpr right;

    public CompositeBoolExpr(BoolExpr left, BoolOp op, BoolExpr right, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }
    
    @Override
    public boolean expr() {
        if(op == BoolOp.And){
            return(left.expr() && right.expr());
        }
        else{
            return(left.expr() || right.expr());
        }
    }
    
}
