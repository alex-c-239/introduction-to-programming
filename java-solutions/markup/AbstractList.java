package markup;

import java.util.List;

public abstract class AbstractList extends AbstractMarkup implements ListMarkup {
    public AbstractList(List<ListItem> markupList) {
        super(markupList);
    }
}