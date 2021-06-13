package syntatic;
 
import java.io.IOException;
 
import javax.sound.sampled.Port;
 
import interpreter.command.Command;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.LexicalException;
import lexical.TokenType;
 
public class SyntaticAnalysis {
 
    private LexicalAnalysis lex;
    private Lexeme current;
 
    public SyntaticAnalysis(LexicalAnalysis lex) throws LexicalException {
        this.lex = lex;
        this.current = lex.nextToken();
    }
 
    public Command start() throws LexicalException {
        procCode();
        eat(TokenType.END_OF_FILE);
        return null;
    }
 
    private void advance() throws LexicalException {
        System.out.println("Advanced (\"" + current.token + "\", " +
            current.type + ")");
        current = lex.nextToken();
    }
 
    private void eat(TokenType type) throws LexicalException {
        System.out.println("Expected (..., " + type + "), found (\"" + 
            current.token + "\", " + current.type + ")");
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
    private void procCode() throws LexicalException {
        while (current.type == TokenType.IF ||
                current.type == TokenType.UNLESS ||
                current.type == TokenType.WHILE ||
                current.type == TokenType.UNTIL ||
                current.type == TokenType.FOR ||
                current.type == TokenType.PUTS ||
                current.type == TokenType.PRINT ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            procCmd();
        }
    }
 
    // <cmd>      ::= <if> | <unless> | <while> | <until> | <for> | <output> | <assign>
    private void procCmd() throws LexicalException {
        // ...
        if (current.type == TokenType.PUTS || current.type == TokenType.UNLESS) {
            procOutput();
        } else {
            procAssign();
        }
    }
 
    // <if>       ::= if <boolexpr> [ then ] <code> { elsif <boolexpr> [ then ] <code> } [ else <code> ] end
    private void procIf() throws LexicalException {
    }
 
    // <unless>   ::= unless <boolexpr> [ then ] <code> [ else <code> ] end
    private void procUnless() throws LexicalException {
    }
 
    // <while>    ::= while <boolexpr> [ do ] <code> end
    private void procWhile() throws LexicalException {
        eat(TokenType.WHILE);
        procBoolExpr();
 
        if (current.type == TokenType.DO)
            advance();
 
        procCode();
        eat(TokenType.END);
    }
 
    // <until>    ::= until <boolexpr> [ do ] <code> end
    private void procUntil() throws LexicalException {
    }
 
    // <for>      ::= for <id> in <expr> [ do ] <code> end
    private void procFor() throws LexicalException {
    }
 
    // <output>   ::= ( puts | print ) [ <expr> ] [ <post> ] ';'
    private void procOutput() throws LexicalException {
        if (current.type == TokenType.PUTS) {
            advance();
        } else if (current.type == TokenType.PRINT) {
            advance();
        } else {
            showError();
        }
 
        if (current.type == TokenType.ADD ||
                current.type == TokenType.SUB ||
                current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.GETS ||
                current.type == TokenType.RAND ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            procExpr();
        }
 
        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            procPost();
        }
 
        eat(TokenType.SEMI_COLON);
    }
 
    // <assign>   ::= <access> { ',' <access> } '=' <expr> { ',' <expr> } [ <post> ] ';'
    private void procAssign() throws LexicalException {
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
    private void procPost() throws LexicalException {
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
    private void procBoolExpr() throws LexicalException {
    }
 
    // <cmpexpr>  ::= <expr> ( '==' | '!=' | '<' | '<=' | '>' | '>=' | '===' ) <expr>
    private void procCmpExpr() throws LexicalException {
    }
 
    // <expr>     ::= <arith> [ ( '..' | '...' ) <arith> ]
    private void procExpr() throws LexicalException {
    }
 
    // <arith>    ::= <term> { ('+' | '-') <term> }
    private void procArith() throws LexicalException {
        procTerm();
 
        while (current.type == TokenType.ADD || current.type == TokenType.SUB) {
            advance();
            procTerm();
        }
    }
 
    // <term>     ::= <power> { ('*' | '/' | '%') <power> }
    private void procTerm() throws LexicalException {
    }
 
    // <power>    ::= <factor> { '**' <factor> }
    private void procPower() throws LexicalException {
        procFactor();
 
        while (current.type == TokenType.EXP) {
            advance();
            procFactor();
        }
    }
 
    // <factor>   ::= [ '+' | '-' ] ( <const> | <input> | <access> ) [ <function> ]
    private void procFactor() throws LexicalException {
    }
 
    // <const>    ::= <integer> | <string> | <array>
    private void procConst() throws LexicalException {
    }
 
    // <input>    ::= gets | rand
    private void procInput() throws LexicalException {
    }
 
    // <array>    ::= '[' [ <expr> { ',' <expr> } ] ']'
    private void procArray() throws LexicalException {
        eat(TokenType.OPEN_BRA);
 
        if (current.type == TokenType.ADD ||
                current.type == TokenType.SUB ||
                current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.GETS ||
                current.type == TokenType.RAND ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            procExpr();
 
            while (current.type == TokenType.COMMA) {
                advance();
                procExpr();
            }
        }
 
        eat(TokenType.CLOSE_BRA);
    }
 
    // <access>   ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
    private void procAccess() throws LexicalException {
    }
 
    // <function> ::= '.' ( length | to_i | to_s )
    private void procFunction() throws LexicalException {
    }
 
    private void procInteger() throws LexicalException {
        eat(TokenType.INTEGER);
    }
 
    private void procString() throws LexicalException {
        eat(TokenType.STRING);
    }
 
    private void procId() throws LexicalException {
        eat(TokenType.ID);
    }
 
}