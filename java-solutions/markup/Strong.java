package markup;

import java.util.List;

public class Strong extends AbstractHighlight {
    public Strong(List<Highlight> markupList) {
        super(markupList);
        MarkdownOuter = "__";
        BBCodeOuterOpen = "[b]";
        BBCodeOuterClose = "[/b]";
    }
}