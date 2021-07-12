///*
package interpreter.expr;

public class SingleBoolExpr extends BoolExpr{

    private Expr left;
    private RelOp op;
    private Expr right;

    public SingleBoolExpr(Expr left, RelOp op, Expr right, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }
    
    @Override
    public boolean expr() {
        return true;
    }
}
//*/

/*
package interpreter.expr;

public class SingleBoolExpr extends BoolExpr{

    private Expr left;
    private RelOp op;
    private Expr right;

    public SingleBoolExpr(Expr left, RelOp op, Expr right, int line) {
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }
    
    @Override
    public boolean expr() {
        switch(op){
            case EqualsOp:
                return (left.expr() == right.expr());
            case NotEqualsOp:
                return (left.expr() != right.expr());
            case LowerThanOp:
                return (left.expr() < right.expr());
            case LowerEqualOp:
                return (left.expr() <= right.expr());
            case GreaterThanOp:
                return (left.expr() > right.expr());
            case GreaterEqualOp:
                return (left.expr() >= right.expr());
            case ContainsOp:
                return (left.expr() === right.expr());
            default:
                return false;
        }
    }
}
//*/
