package markup;

import java.util.List;

public class UnorderedList extends AbstractList {
    public UnorderedList(List<ListItem> markupList) {
        super(markupList);
        BBCodeOuterOpen = "[list]";
        BBCodeOuterClose = "[/list]";
    }
}