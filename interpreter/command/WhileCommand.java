
package interpreter.command;

import interpreter.expr.BoolExpr;

public class WhileCommand extends Command{

    private BoolExpr cond;
    private Command cmds;
    
    @Override
    public void execute() {
        
    }

    public WhileCommand(BoolExpr cond, Command cmds, int line) {
        super(line);
        this.cond = cond;
        this.cmds = cmds;
    }
}
