
package interpreter.expr;

import static interpreter.util.Utils.abort;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import java.util.Vector;

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
        
        if(this.op == FunctionOp.ToIntOp){
            
            if(this.expr.expr() != null){
                
                if(this.expr.expr() instanceof StringValue){
                    
                    String aux = (String) this.expr.expr().value();
                    int auxInteiro = Integer.parseInt(aux);
                    
                    Value valor = new IntegerValue(auxInteiro);
                    return valor;
                }
                
                else{
                    return new IntegerValue(0);
                }
            }
            
            else{
                return new IntegerValue(0);
            }
        }
        
        else if(this.op == FunctionOp.ToStringOp){
            
            if(this.expr.expr() != null){
                
                if(this.expr.expr() instanceof IntegerValue){
                    
                    int auxInteiro = (int) this.expr.expr().value();
                    String auxString = String.valueOf(auxInteiro);
                    Value valor = new StringValue(auxString);
                    return valor;
                }
                
                else if(this.expr.expr() instanceof ArrayValue){
                    
                    Vector<Value<?>> vetor = (Vector) this.expr.expr().value();
                    String aux = "[";
                    
                    for(int i = 0; i < vetor.size(); i ++){
                        
                        if(vetor.get(i) instanceof IntegerValue){
                            aux = aux.concat(String.valueOf(vetor.get(i)));
                        }
                        
                        else if(vetor.get(i) instanceof StringValue){
                            //aux = aux.concat("'");        // Precisa de ' se for string?
                            aux = aux.concat(String.valueOf(vetor.get(i)));
                            //aux = aux.concat("'");
                        }
                        
                        else{
                            aux = aux.concat(" ");
                        }
                        
                        if(i + 1 != vetor.size())
                            aux = aux.concat(",");
                    }
                    
                    aux = aux.concat("]");
                    Value valor = new StringValue(aux);
                    return valor;
                }
                
                else if(this.expr.expr() instanceof StringValue){
                    return new StringValue((String) this.expr.expr().value());
                }
                
                else{
                    return new StringValue("");
                }
            }
            
            else{
                return new StringValue("");
            }
        }
        
        else if(this.op == FunctionOp.LenghtOp){
            
            if(this.expr.expr() != null){
                
                if(this.expr.expr() instanceof ArrayValue){
                    
                    Vector<Value<?>> vetor = (Vector) this.expr.expr().value();
                    Value valor = new IntegerValue(vetor.size());
                    return valor;
                }
                
                else{
                    return new IntegerValue(1);
                }
            }
            
            else{
                return new IntegerValue(0);
            }
        }
        
        return null;
    }

}
