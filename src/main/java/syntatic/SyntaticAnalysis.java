package syntatic;

import java.util.ArrayList;
import java.util.List;

import interpreter.command.BlocksCommand;
import interpreter.command.Command;
import interpreter.command.OutputCommand;
import interpreter.command.OutputOp;
import interpreter.expr.AccessExpr;
import interpreter.expr.ConstExpr;
import interpreter.expr.ConvExpr;
import interpreter.expr.ConvOp;
import interpreter.expr.Expr;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
import java.io.IOException;
=======
import interpreter.value.Value;
import java.io.IOException;
import java.util.Vector;
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.LexicalException;
import lexical.TokenType;

public class SyntaticAnalysis {
    
    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws LexicalException, IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws LexicalException, IOException {
        BlocksCommand ccmd = procCode();
        eat(TokenType.END_OF_FILE);
        return ccmd;
    }

    private void advance() throws LexicalException, IOException {
        System.out.println("Advanced (\"" + current.token + "\", " + current.type + ")");
        current = lex.nextToken();
    }

    private void eat(TokenType type) throws LexicalException, IOException {
        System.out.println("Expected (..., " + type + "), found (\"" + current.token + "\", " + current.type + ")");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }

    // <code>     ::= { <cmd> }
    private BlocksCommand procCode() throws LexicalException, IOException {
        int line = lex.getLine();
        List<Command> cmds = new ArrayList<>();

        while ( current.type == TokenType.IF ||
                current.type == TokenType.UNLESS ||
                current.type == TokenType.WHILE ||
                current.type == TokenType.UNTIL ||
                current.type == TokenType.FOR ||
                current.type == TokenType.PUTS ||
                current.type == TokenType.PRINT ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            Command cmd = procCmd();
            cmds.add(cmd);
        }

        BlocksCommand cmd = new BlocksCommand(line, cmds);
        return cmd;
    }

    // <cmd>      ::= <if> | <unless> | <while> | <until> | <for> | <output> | <assign>
    private Command procCmd() throws LexicalException, IOException {
        Command cmd = null;
        if (current.type == TokenType.PUTS || current.type == TokenType.PRINT) {
            cmd = procOutput();
        } 
        else if(current.type == TokenType.IF){
            procIf();
        }
        else if(current.type == TokenType.UNLESS){
            procUnless();
        }
        else if(current.type == TokenType.WHILE){
            procWhile();
        }
        else if(current.type == TokenType.UNTIL){
            procUntil();
        }
        else if(current.type == TokenType.FOR){
            procFor();
        }
        else {
            procAssign();
        }
        return cmd;
    }

    // <if>       ::= if <boolexpr> [ then ] <code> { elsif <boolexpr> [ then ] <code> } [ else <code> ] end
    private void procIf() throws LexicalException, IOException {
        eat(TokenType.IF);
        
        procBoolExpr();
        
        if(current.type == TokenType.THEN){
            eat(TokenType.THEN);
        }
        
        procCode();
        
        while(current.type == TokenType.ELSIF){
            eat(TokenType.ELSIF);
            procBoolExpr();
            
            if(current.type == TokenType.THEN){
                eat(TokenType.THEN);
            }
            
            procCode();
        }
        
        if(current.type == TokenType.ELSE){
            eat(TokenType.ELSE);
            procCode();
        }
        
        eat(TokenType.END);
    }

    // <unless>   ::= unless <boolexpr> [ then ] <code> [ else <code> ] end
    private void procUnless() throws LexicalException, IOException {
        eat(TokenType.UNLESS);
        
        procBoolExpr();
        
        if(current.type == TokenType.THEN){
            eat(TokenType.THEN);
        }
        
        procCode();
        
        if(current.type == TokenType.ELSE){
            eat(TokenType.ELSE);
            procCode();
        }
        
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
        eat(TokenType.END);
=======
        if(current.type == TokenType.END){
            eat(TokenType.END);
        }
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
    }

    // <while>    ::= while <boolexpr> [ do ] <code> end
    private void procWhile() throws LexicalException, IOException {
        eat(TokenType.WHILE);

        procBoolExpr();

        if (current.type == TokenType.DO)
            eat(TokenType.DO);
    
        procCode();
        eat(TokenType.END);
    }

    // <until>    ::= until <boolexpr> [ do ] <code> end
    private void procUntil() throws LexicalException, IOException {
        eat(TokenType.UNTIL);

        procBoolExpr();
        
        if (current.type == TokenType.DO)
            eat(TokenType.DO);
        
        procCode();
        eat(TokenType.END);
    }

    // <for>      ::= for <id> in <expr> [ do ] <code> end
    private void procFor() throws LexicalException, IOException {
        eat(TokenType.FOR);
        eat(TokenType.ID);
        eat(TokenType.IN);
        
        procExpr();
        
        if (current.type == TokenType.DO)
            eat(TokenType.DO);
        
        procCode();
        eat(TokenType.END);
    }

    // <output>   ::= ( puts | print ) [ <expr> ] [ <post> ] ';'
    private OutputCommand procOutput() throws LexicalException, IOException {
        OutputOp op = null;
        if (current.type == TokenType.PUTS) {
            op = OutputOp.PutsOp;
            advance();
        } else if (current.type == TokenType.PRINT) {
            op = OutputOp.PrintOp;
            advance();
        } else {
            showError();
        }
        int line = lex.getLine();

        Expr expr = null;
        if (    current.type == TokenType.ADD ||
                current.type == TokenType.SUB ||
                current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.GETS ||
                current.type == TokenType.RAND ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            expr = procExpr();
        }

        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
             procPost();
        }

        eat(TokenType.SEMI_COLON);

        OutputCommand ocmd = new OutputCommand(line, op, expr);
        return ocmd;
    }

    // <assign>   ::= <access> { ',' <access> } '=' <expr> { ',' <expr> } [ <post> ] ';'
    private void procAssign() throws LexicalException, IOException {
        procAccess();
    
        while (current.type == TokenType.COMMA) {
            advance();
            procAccess();
        }

        eat(TokenType.ASSIGN);
        
        procExpr();

        while (current.type == TokenType.COMMA) {
            advance();
            procExpr();
        }

        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            procPost();
        }

        eat(TokenType.SEMI_COLON);
    }

    // <post>     ::= ( if | unless ) <boolexpr>
    private void procPost() throws LexicalException, IOException {
        if (current.type == TokenType.IF) {
            advance();
        } else if (current.type == TokenType.UNLESS) {
            advance();
        } else {
            showError();
        }

        procBoolExpr();        
    }

    // <boolexpr> ::= [ not ] <cmpexpr> [ (and | or) <boolexpr> ]
    private void procBoolExpr() throws LexicalException, IOException {
        if(current.type == TokenType.NOT){
            eat(TokenType.NOT);
        }
        
        procCmpExpr();
        
        if(current.type == TokenType.AND || current.type == TokenType.OR){
            advance();
            
            procBoolExpr();
        }
    }

    // <cmpexpr>  ::= <expr> ( '==' | '!=' | '<' | '<=' | '>' | '>=' | '===' ) <expr>
    private void procCmpExpr() throws LexicalException, IOException {
        procExpr();
        
        if(current.type == TokenType.EQUALS     ||
           current.type == TokenType.NOT_EQUALS ||
           current.type == TokenType.LOWER      ||
           current.type == TokenType.LOWER_EQ   ||
           current.type == TokenType.GREATER    ||
           current.type == TokenType.GREATER_EQ ||
           current.type == TokenType.CONTAINS){
            advance();
        }
        
        procExpr();
    }

    // <expr>     ::= <arith> [ ( '..' | '...' ) <arith> ]
    private Expr procExpr() throws LexicalException, IOException {
        Expr expr = procArith();

        if (    current.type == TokenType.RANGE_WITH ||
                current.type == TokenType.RANGE_WITHOUT) {
            advance();
            procArith();
        }

        return expr;
    }

    // <arith>    ::= <term> { ('+' | '-') <term> }
    private Expr procArith() throws LexicalException, IOException {
        Expr expr = procTerm();

        while (current.type == TokenType.ADD || current.type == TokenType.SUB) {
            
            if(current.type == TokenType.ADD){
                advance();
                procTerm();
            }
            else{       // SUB
                advance();
                //ConvExpr exprAux = new ConvExpr(lex.getLine(), ConvOp.MinusOp, expr);
                procTerm();
            }
        }

        return expr;
    }

    // <term>     ::= <power> { ('*' | '/' | '%') <power> }
    private Expr procTerm() throws LexicalException, IOException {
        Expr expr = procPower();

        while ( current.type == TokenType.MUL ||
                current.type == TokenType.DIV ||
                current.type == TokenType.MOD) {
            advance();
            procPower();
        }

        return expr;
    }

    // <power>    ::= <factor> { '**' <factor> }
    private Expr procPower() throws LexicalException, IOException {
        Expr expr = procFactor();

        while (current.type == TokenType.EXP) {
            advance();
            procFactor();
        }

        return expr;
    }

    // <factor>   ::= [ '+' | '-' ] ( <const> | <input> | <access> ) [ <function> ]
    private Expr procFactor() throws LexicalException, IOException {
        
        Expr expr = null;
        
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
        if(current.type == TokenType.INTEGER ||
           current.type == TokenType.STRING  ||
           current.type == TokenType.OPEN_BRA){
            advance();
            procConst();
        }
        else if(current.type == TokenType.GETS ||
                current.type == TokenType.RAND){
            advance();
            procInput();
        }
        else if(current.type == TokenType.ID ||
           current.type == TokenType.OPEN_PAR){
=======
        if(current.type == TokenType.SUB){
            
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
            advance();
            
            if(current.type == TokenType.INTEGER){
                expr = procConst();
            }
            else{
                showError();
            }
            
            ConvExpr exprAux = new ConvExpr(lex.getLine(), ConvOp.MinusOp, expr);
            
            if(current.type == TokenType.DOT){
                eat(TokenType.DOT);
                procFunction();
            }
            
            return exprAux;
        }
        
        else{
            if(current.type == TokenType.ADD){
                eat(TokenType.ADD);
            }
            
            if(current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING  ||
                current.type == TokenType.OPEN_BRA){
                 //advance();
                 expr = procConst();
            }
            else if(current.type == TokenType.GETS ||
                    current.type == TokenType.RAND){
                advance();
                procInput();
            }
            else if(current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR){
                //advance();
                expr = procAccess();
            }

            if(current.type == TokenType.DOT){
                eat(TokenType.DOT);
                procFunction();
            }
        }
        
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
        return procConst();
=======
        return expr;
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
    }

    // <const>    ::= <integer> | <string> | <array>
    private Expr procConst() throws LexicalException, IOException {
        Expr expr = null;
        if (current.type == TokenType.INTEGER) {
            expr = procInteger();
        } else if (current.type == TokenType.STRING) {
            expr = procString();
        } else {
            expr = procArray();
            //procArray();
        }
        return expr;
    }

    // <input>    ::= gets | rand
    private void procInput() throws LexicalException, IOException {
        if(current.type == TokenType.GETS){
            eat(TokenType.GETS);
        }
        
        else if(current.type == TokenType.RAND){
            eat(TokenType.RAND);
        }
    }

    // <array>    ::= '[' [ <expr> { ',' <expr> } ] ']'
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
    private void procArray() throws LexicalException, IOException {
        // VERIFICAR - CREIO QUE NÃO FUNCIONA PARA ARRAY
=======
    private ConstExpr procArray() throws LexicalException, IOException {

        Vector<Value<?>> value = new Vector<>();
        
        int line = lex.getLine();
        Expr exprAux = null;
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
        
        if(current.type == TokenType.OPEN_BRA){
            eat(TokenType.OPEN_BRA);
        }

        if (    current.type == TokenType.ADD ||
                current.type == TokenType.SUB ||
                current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.GETS ||
                current.type == TokenType.RAND ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            exprAux = procExpr();
            
            if(exprAux != null){
                value.add(exprAux.expr());
            }

            while (current.type == TokenType.COMMA) {
                advance();
                
                exprAux = procExpr();
                
                if(exprAux != null){
                    value.add(exprAux.expr());
                }
            }
        }

        if(current.type == TokenType.CLOSE_BRA){
            eat(TokenType.CLOSE_BRA);
        }
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
    }

    // <access>   ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
    private void procAccess() throws LexicalException, IOException {
=======
        
        line = lex.getLine();

        ArrayValue av = new ArrayValue(value);
        ConstExpr cexpr = new ConstExpr(line, av);
        
        return cexpr;
    }

    // <access>   ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
    private Expr procAccess() throws LexicalException, IOException {
        
        Expr expr = null;
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
        
        if(current.type == TokenType.ID){
            eat(TokenType.ID);
        }    
<<<<<<< Updated upstream:src/main/java/syntatic/SyntaticAnalysis.java
        else if(current.type == TokenType.OPEN_BRA){
            //eat(TokenType.OPEN_BRA);
            procExpr();
            //eat(TokenType.CLOSE_BRA);
        }
        
        if(current.type == TokenType.OPEN_PAR){
            eat(TokenType.OPEN_PAR);
            procExpr();
            eat(TokenType.CLOSE_PAR);
        }
=======
        
        else if(current.type == TokenType.OPEN_PAR){
            eat(TokenType.OPEN_PAR);
            expr = procExpr();
            eat(TokenType.CLOSE_PAR);
        }
        
        if(current.type == TokenType.OPEN_BRA){
            eat(TokenType.OPEN_BRA);
            expr = procExpr();
            // Ao invés de pegar a posição do array, está considerando a posição atual como um integer
            eat(TokenType.CLOSE_BRA);
        }
        
        return expr;
>>>>>>> Stashed changes:syntatic/SyntaticAnalysis.java
    }

    // <function> ::= '.' ( length | to_i | to_s )
    private void procFunction() throws LexicalException, IOException {
        if(current.type == TokenType.DOT){
            eat(TokenType.DOT);
        }
        
        if(current.type == TokenType.LENGTH){
            eat(TokenType.LENGTH);
        }
        else if(current.type == TokenType.TO_INT){
            eat(TokenType.TO_INT);
        }
        else if(current.type == TokenType.TO_STR){
            eat(TokenType.TO_STR);
        }
    }

    private ConstExpr procInteger() throws LexicalException, IOException {
        String str = current.token;
        eat(TokenType.INTEGER);
        int line = lex.getLine();

        int n;
        try {
            n = Integer.parseInt(str);
        } catch (Exception e) {
            n = 0;
        }

        IntegerValue iv = new IntegerValue(n);
        ConstExpr cexpr = new ConstExpr(line, iv);
        return cexpr;
    }

    private ConstExpr procString() throws LexicalException, IOException {
        String str = current.token;
        eat(TokenType.STRING);
        int line = lex.getLine();

        StringValue sv = new StringValue(str);
        ConstExpr cexpr = new ConstExpr(line, sv);
        return cexpr;
    }

    private void procId() throws LexicalException, IOException {
        eat(TokenType.ID);
    }

}