package markup;

import java.util.List;

public class Emphasis extends AbstractHighlight {
    public Emphasis(List<Highlight> markupList) {
        super(markupList);
        MarkdownOuter = "*";
        BBCodeOuterOpen = "[i]";
        BBCodeOuterClose = "[/i]";
    }
}
