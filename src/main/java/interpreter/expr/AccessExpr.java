
package interpreter.expr;

import interpreter.util.Memory;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class AccessExpr extends SetExpr{

    private Expr base;
    private Expr index;

    public AccessExpr(Expr base, Expr index, int line) {
        super(line);
        this.base = base;
        this.index = index;
    }
    
    @Override
    public void SetValue(Value<?> value) {
        Memory.write(this.base.toString(), value);
        Memory.write(this.index.toString(), value);
    }
    
    @Override
    public Value<?> expr() {
        Value aux = new StringValue(this.base.toString());
        return aux;
    }
}