package markup;

import java.util.List;

public class ListItem extends AbstractMarkup {
    public ListItem(List<ListMarkup> markupList) {
        super(markupList);
        BBCodeOuterOpen = "[*]";
        BBCodeOuterClose = "";
    }
}
