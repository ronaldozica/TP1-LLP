
package interpreter.expr;

import static interpreter.util.Utils.abort;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
import java.util.Vector;

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
        
        if(this.op == BinaryOp.AddOp){
            if( ( this.left.expr() != null ) && ( this.right.expr() != null ) ){
                if(this.left.expr() instanceof IntegerValue && this.right.expr() instanceof IntegerValue){
                    int aux;

                    aux = (int) this.left.expr().value() + (int) this.right.expr().value();     // add

                    Value valor = new IntegerValue(aux);
                    return valor;
                }

                else if(this.left.expr() instanceof StringValue && this.right.expr() instanceof StringValue){
                    String aux;

                    aux = this.left.expr().value().toString();
                    aux = aux.concat(this.right.expr().value().toString());

                    Value valor = new StringValue(aux);
                    return valor;
                }

                else if(this.left.expr() instanceof ArrayValue && this.right.expr() instanceof ArrayValue){

                    Vector<Value<?>> vetorResultado = (Vector) this.left.expr().value();
                    Vector<Value<?>> vetorRight = (Vector) this.right.expr().value();

                    for(int i = 0; i < vetorRight.size(); i ++){ 
                        vetorResultado.add(vetorRight.get(i));
                    }

                    Value valor = new ArrayValue(vetorResultado);
                    return valor;
                }
            }
        }
        
        else if(this.op == BinaryOp.SubOp){
            if( ( this.left.expr() != null ) && ( this.right.expr() != null ) ){
                
                if(this.left.expr() instanceof IntegerValue && this.right.expr() instanceof IntegerValue){
                    int aux;

                    aux = (int) this.left.expr().value() - (int) this.right.expr().value();     // add

                    Value valor = new IntegerValue(aux);
                    return valor;
                }

                else if(this.left.expr() instanceof StringValue && this.right.expr() instanceof StringValue){
                    abort(this.getLine());
                    return null;
                }

                else if(this.left.expr() instanceof ArrayValue && this.right.expr() instanceof ArrayValue){
                    abort(this.getLine());
                    return null;
                }
            }
        }
        
        else if(this.op == BinaryOp.RangeWithOp || this.op == BinaryOp.RangeWithoutOp){
            if( ( this.left.expr() != null ) && ( this.right.expr() != null ) ){
                
                if(this.left.expr() instanceof IntegerValue && this.right.expr() instanceof IntegerValue){
                    Vector<Value<?>> vetorResultado = new Vector<>();

                    int limiteInferior = (int) this.left.expr().value();
                    int limiteSuperior = (int) this.right.expr().value();

                    if(limiteInferior >= limiteSuperior){
                        for(int i = limiteInferior; i > limiteSuperior; i --){
                            vetorResultado.add(new IntegerValue(i));
                        }
                        
                        if(this.op == BinaryOp.RangeWithOp){
                            vetorResultado.add(new IntegerValue(limiteSuperior));
                        }
                    }
                    
                    else{
                        for(int i = limiteInferior; i < limiteSuperior; i ++){
                            vetorResultado.add(new IntegerValue(i));
                        }

                        if(this.op == BinaryOp.RangeWithOp){
                            vetorResultado.add(new IntegerValue(limiteSuperior));
                        }
                    }
                    
                    Value valor = new ArrayValue(vetorResultado);
                    return valor;
                }

                else if(this.left.expr() instanceof StringValue && this.right.expr() instanceof StringValue){
                    abort(this.getLine());
                    return null;
                }

                else if(this.left.expr() instanceof ArrayValue && this.right.expr() instanceof ArrayValue){
                    abort(this.getLine());
                    return null;
                }
            }
        }
        
        else if(this.op == BinaryOp.MulOp || this.op == BinaryOp.DivOp || this.op == BinaryOp.ModOp){
            
            if( ( this.left.expr() != null ) && ( this.right.expr() != null ) ){
                
                if(this.left.expr() instanceof IntegerValue && this.right.expr() instanceof IntegerValue){
                    int aux;

                    if(this.op == BinaryOp.MulOp){
                        aux = (int) this.left.expr().value() * (int) this.right.expr().value();
                    }
                    else if(this.op == BinaryOp.DivOp){
                        aux = (int) this.left.expr().value() / (int) this.right.expr().value();
                    }
                    else{
                        aux = (int) this.left.expr().value() % (int) this.right.expr().value();
                    }

                    Value valor = new IntegerValue(aux);
                    return valor;
                }

                else if(this.left.expr() instanceof StringValue && this.right.expr() instanceof StringValue){
                    abort(this.getLine());
                    return null;
                }

                else if(this.left.expr() instanceof ArrayValue && this.right.expr() instanceof ArrayValue){
                    abort(this.getLine());
                    return null;
                }
            }
        }
        
        else if(this.op == BinaryOp.ExpOp){
            
            if( ( this.left.expr() != null ) && ( this.right.expr() != null ) ){
                
                if(this.left.expr() instanceof IntegerValue && this.right.expr() instanceof IntegerValue){
                    
                    int aux = (int) this.left.expr().value();
                    int armazenaLeft = aux;
                    
                    for(int i = 2; i <= (int) this.right.expr().value(); i ++){
                        aux *= armazenaLeft;
                    }

                    Value valor = new IntegerValue(aux);
                    return valor;
                }

                else if(this.left.expr() instanceof StringValue && this.right.expr() instanceof StringValue){
                    abort(this.getLine());
                    return null;
                }

                else if(this.left.expr() instanceof ArrayValue && this.right.expr() instanceof ArrayValue){
                    abort(this.getLine());
                    return null;
                }
            }
        }
        
        return null;
        
        //Value valor = new IntegerValue((int) this.left.expr().value());
        //return valor;
    }
}