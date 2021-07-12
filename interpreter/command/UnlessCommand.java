
package interpreter.command;

import interpreter.expr.BoolExpr;

public class UnlessCommand extends Command{

    private BoolExpr cond;
    private Command thenCmds;
    private Command elseCmds;

    public UnlessCommand(BoolExpr cond, Command thenCmds, Command elseCmds, int line) {
        super(line);
        this.cond = cond;
        this.thenCmds = thenCmds;
        this.elseCmds = elseCmds;
    }
    
    @Override
    public void execute() {
        
    }

}
