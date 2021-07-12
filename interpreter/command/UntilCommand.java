
package interpreter.command;

import interpreter.expr.BoolExpr;

public class UntilCommand extends Command{
    private BoolExpr cond;
    private Command cmds;

    public UntilCommand(BoolExpr cond, Command cmds, int line) {
        super(line);
        this.cond = cond;
        this.cmds = cmds;
    }
    
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
