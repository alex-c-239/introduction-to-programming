package md2html;

import myscanner.MyScanner;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Md2HtmlParser implements AutoCloseable {
    private static final String MD_TAG_PRE = "```";

    private static final String HTML_OPEN_TAG_PRE = "<pre>";
    private static final String HTML_CLOSE_TAG_PRE = "</pre>";

    private final MyScanner scanner;
    private final Deque<String> stack;
    private StringBuilder ans;

    public Md2HtmlParser(File source) throws FileNotFoundException {
        scanner = new MyScanner(source);
        stack = new ArrayDeque<>();
        ans = new StringBuilder();
    }

    private String toHtmlTag(String mdTag, boolean isOpenTag) throws IllegalStateException {
        return switch (mdTag) {
            case "`" -> isOpenTag ? "<code>" : "</code>";
            case "*", "_" -> isOpenTag ? "<em>" : "</em>";
            case "--" -> isOpenTag ? "<s>" : "</s>";
            case "**", "__" -> isOpenTag ? "<strong>" : "</strong>";
            default -> throw new IllegalStateException("Unexpected value: " + mdTag);
        };
    }

    private String toHtmlChar(char symbol) throws IllegalStateException {
        return switch (symbol) {
            case '<' -> "&lt;";
            case '>' -> "&gt;";
            case '&' -> "&amp;";
            default -> throw new IllegalStateException("Unexpected value: " + symbol);
        };
    }

    private String checkTag(String tag) {
        if (stack.getLast().equals(toHtmlTag(tag, false))) {
            return stack.removeLast();
        } else if (scanner.checkNextChar(c -> !Character.isWhitespace(c))) {
            stack.add(toHtmlTag(tag, false));
            return toHtmlTag(tag, true);
        }
        return tag;
    }

    private String checkDoubleTag(char tag) {
        String stringTag = String.valueOf(tag);
        if (scanner.checkNextChar(tag)) {
            scanner.nextChar();
            return checkTag(stringTag.repeat(2));
        }
        return stringTag;
    }

    private String checkStrongTag(char tag) {
        String result = checkDoubleTag(tag);
        if (result.equals(String.valueOf(tag))) {
            return checkTag(String.valueOf(tag));
        }
        return result;
    }

    private String parsePreTag() {
        StringBuilder ans = new StringBuilder();
        ans.append(HTML_OPEN_TAG_PRE);

        while (scanner.hasNext()) {
            int tagCounter = 1;
            char symbol = scanner.nextChar();
            while (symbol == '`' && scanner.checkNextChar('`') && tagCounter < 3) {
                tagCounter++;
                scanner.nextChar();
            }
            if (tagCounter < 3) {
                ans.append(String.valueOf(symbol).repeat(tagCounter));
            } else {
                break;
            }
        }
        ans.append(HTML_CLOSE_TAG_PRE);

        return ans.toString();
    }

    private String checkPreTag() {
        int tagCounter = 1;
        while (scanner.checkNextChar('`') && tagCounter < 3) {
            scanner.nextChar();
            tagCounter++;
        }

        if (tagCounter == 1) {
            return checkTag("`");
        } else if (tagCounter == 2) {
            return toHtmlTag("`", true) + toHtmlTag("`", false);
        } else if (scanner.checkNextChar(c -> !Character.isWhitespace(c))) {
            return parsePreTag();
        }
        return MD_TAG_PRE;
    }

    private void clearStack() {
        while (stack.size() > 0) {
            ans.append(stack.removeLast());
        }
    }

    private boolean checkForLineBreaks() {
        if (scanner.checkNextChar(scanner::isLineBreak)) {
            char breakSymbol = scanner.nextChar();
            if (!scanner.hasNext() || scanner.checkNextChar(scanner::isLineBreak)) {
                if (breakSymbol == '\r' && scanner.checkNextChar('\n')) {    //  Check for "\r\n" line break
                    scanner.nextChar();
                    if (!scanner.hasNext() || scanner.checkNextChar(scanner::isLineBreak)) {
                        clearStack();
                        return true;
                    } else {
                        ans.append("\r\n");
                        return false;
                    }
                }
                clearStack();
                return true;
            }
            ans.append(breakSymbol);
        }
        return false;
    }

    private void parseText() {
        while (scanner.hasNext()) {
            if (checkForLineBreaks()) {     //End of paragraph
                return;
            }

            char symbol = scanner.nextChar();
            switch (symbol) {
                case '*', '_' -> ans.append(checkStrongTag(symbol));
                case '-' -> ans.append(checkDoubleTag(symbol));
                case '`' -> ans.append(checkPreTag());
                case '>', '<', '&' -> ans.append(toHtmlChar(symbol));
                case '\\' -> ans.append(((scanner.hasNext()) ? (scanner.nextChar()) : ("")));
                default -> ans.append(symbol);
            }
        }
        clearStack();
    }

    private void parseParagraph() {
        int headerLevel = 0;
        while (scanner.checkNextChar('#')) {
            ++headerLevel;
            scanner.nextChar();
        }

        if (headerLevel > 0 && scanner.checkNextChar(Character::isWhitespace)) {
            scanner.skipSpaces();
            ans.append(String.format("<h%d>", headerLevel));
            stack.add(String.format("</h%d>\n", headerLevel));
        } else {
            ans.append("<p>");
            ans.append("#".repeat(headerLevel));
            stack.add("</p>\n");
        }

        parseText();
    }

    public String parse() {
        ans.setLength(0);
        scanner.skipLineBreaks();
        while (scanner.hasNext()) {
            parseParagraph();
            scanner.skipLineBreaks();
        }
        return ans.toString();
    }

    @Override
    public void close() {
        scanner.close();
    }
}