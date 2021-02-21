package test.intdmp.core.sortMessages;
import test.intdmp.core.helpClass.*;

import java.util.List;

public class PaginatorFilter {

    public String search;
    public Integer length;
    public Integer pageIndex;
    public Integer pageSize;
    public Integer previousPageIndex;

    public List<Category> categories;


    public PaginatorFilter(String search, Integer length, Integer pageIndex, Integer pageSize, Integer previousPageIndex, List<Category> categories) {
        this.search = search;
        this.length = length;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.previousPageIndex = previousPageIndex;

        this.categories = categories;

    }


}
