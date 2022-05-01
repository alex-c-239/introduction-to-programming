package markup;

import java.util.List;

public class Paragraph extends AbstractMarkup implements ListMarkup {
    public Paragraph(List<Highlight> markupList) {
        super(markupList);
        BBCodeOuterOpen = "";
        BBCodeOuterClose = "";
    }

    public void toMarkdown(StringBuilder stringBuilder) {
        for (Markup markup : markupList) {
            ((Highlight) markup).toMarkdown(stringBuilder);
        }
    }
}
