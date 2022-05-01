package markup;

import java.util.List;

public class Strikeout extends AbstractHighlight {
    public Strikeout(List<Highlight> markupList) {
        super(markupList);
        MarkdownOuter = "~";
        BBCodeOuterOpen = "[s]";
        BBCodeOuterClose = "[/s]";
    }
}
