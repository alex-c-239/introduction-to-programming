package markup;

import java.util.List;

public class OrderedList extends AbstractList {
    public OrderedList(List<ListItem> markupList) {
        super(markupList);
        BBCodeOuterOpen = "[list=1]";
        BBCodeOuterClose = "[/list]";
    }
}
