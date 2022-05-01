package markup;

import java.util.List;

public abstract class AbstractMarkup implements Markup {

    protected List<? extends Markup> markupList;

    protected String BBCodeOuterOpen;
    protected String BBCodeOuterClose;

    public AbstractMarkup(List<? extends Markup> markupList) {
        this.markupList = markupList;
    }

    @Override
    public void toBBCode(StringBuilder stringBuilder) {
        stringBuilder.append(BBCodeOuterOpen);
        for (Markup markup : markupList) {
            markup.toBBCode(stringBuilder);
        }
        stringBuilder.append(BBCodeOuterClose);
    }
}
