package markup;

import java.util.List;

public abstract class AbstractHighlight extends AbstractMarkup implements Highlight {

    protected String MarkdownOuter;

    public AbstractHighlight(List<Highlight> markupList) {
        super(markupList);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(MarkdownOuter);
        for (Markup markup : markupList) {
            ((Highlight) markup).toMarkdown(stringBuilder);
        }
        stringBuilder.append(MarkdownOuter);
    }
}
