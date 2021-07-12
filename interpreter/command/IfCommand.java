
package interpreter.command;

import interpreter.expr.BoolExpr;

public class IfCommand extends Command{

    private BoolExpr cond;
    private Command thenCmds;
    private Command elseCmds;

    public IfCommand(BoolExpr cond, Command thenCmds, Command elseCmds, int line) {
        super(line);
        this.cond = cond;
        this.thenCmds = thenCmds;
        this.elseCmds = elseCmds;
    }
    
    public void setElseCommands(Command elseCmds){
        
    }
    
    @Override
    public void execute() {
        
    }

}
