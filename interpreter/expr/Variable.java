
package interpreter.expr;

import interpreter.util.Memory;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class Variable extends SetExpr{

    private String name;

    public Variable(String name, int line) {
        super(line);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public void SetValue(Value<?> value) {
        Memory.write(this.name, value);
    }

    @Override
    public Value<?> expr() {
        if(Memory.read(this.name) == null){
            return new StringValue("");
        }
        else{
            return Memory.read(this.name);
        }
    }
}
