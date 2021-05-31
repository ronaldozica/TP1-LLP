import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.TokenType;
/*
import lexical.SymbolTable;
import lexical.LexicalException;
import syntatic.SyntaticAnalysis;
import interpreter.command.Command;
import interpreter.util.Memory;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;
*/

public class mrbi {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java mrbi [miniRuby file]");
            return;
        }

        try (LexicalAnalysis l = new LexicalAnalysis(args[0])) {
            /*
            SyntaticAnalysis s = new SyntaticAnalysis(l);
            Command c = s.start();
            c.execute();
            */
            
            Lexeme lex = l.nextToken();
            while (checkType(lex.type)) {
                System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
                lex = l.nextToken();
            }

            switch (lex.type) {
                case INVALID_TOKEN:
                    System.out.printf("%02d: Lexema inválido [%s]\n", l.getLine(), lex.token);
                    break;
                case UNEXPECTED_EOF:
                    System.out.printf("%02d: Fim de arquivo inesperado\n", l.getLine());
                    break;
                default:
                    System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Internal error: " + e.getMessage());
        }
    }

    private static boolean checkType(TokenType type) {
        return !(type == TokenType.END_OF_FILE ||
                 type == TokenType.INVALID_TOKEN ||
                 type == TokenType.UNEXPECTED_EOF);
    }
}
