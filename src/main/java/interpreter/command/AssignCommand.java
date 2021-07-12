
package interpreter.command;

import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import static interpreter.util.Utils.abort;
import interpreter.value.Value;
import java.util.List;

public class AssignCommand extends Command{

    private List<Expr> left;
    private List<Expr> right;

    public AssignCommand(List<Expr> left, List<Expr> right, int line) {
        super(line);
        this.left = left;
        this.right = right;
    }
    
    @Override
    public void execute() {
        
        if(left.size() != right.size()){
            abort(this.getLine());
        }
        
        List<Value<?>> temp = null;
        
        for (Expr exprs : right){
            temp.add(exprs.expr());
        }
        
        for (Expr exprs : left){
            if( !(exprs instanceof SetExpr)){
                abort(this.getLine());
            }
        }
        
        for (int i = 0; i < left.size(); i ++){
            SetExpr setExprs = (SetExpr) temp.get(i).value();
            setExprs.SetValue(temp.get(i));
        }
    }
    
}


        // AssignCommand
        // =============

        // se o tamanho da lista left for diferente do tamanho da lista right
        // abortar com operacao invalida

        // List<Value<?>> temp = percorrer a lista right avaliando as expressoes
        // i = 0;
        // percorrer a lista left onde, para cada expr, fazer
        // se expr não for SetExpr
        //     abortar com operacao invalida

        // SetExpr setExpr = converter expr
        // sexpr->setValue(temp[i]);
        // i++;