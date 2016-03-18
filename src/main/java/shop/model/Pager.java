package shop.model;

import java.util.List;

/**
 * Created by Amysue on 2016/3/18.
 */
public class Pager<T> {
    private int currentPage;
    private int pageItems;
    private int allPageNums;
    private int allItems;
    private List<T> tLists;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageItems() {
        return pageItems;
    }

    public void setPageItems(int pageItems) {
        this.pageItems = pageItems;
    }

    public int getAllPageNums() {
        return allPageNums;
    }

    public void setAllPageNums(int allPageNums) {
        this.allPageNums = allPageNums;
    }

    public int getAllItems() {
        return allItems;
    }

    public void setAllItems(int allItems) {
        this.allItems = allItems;
    }

    public List<T> gettLists() {
        return tLists;
    }

    public void settLists(List<T> tLists) {
        this.tLists = tLists;
    }
}
