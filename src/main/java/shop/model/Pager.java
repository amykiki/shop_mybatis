package shop.model;

import java.util.List;

/**
 * Created by Amysue on 2016/3/18.
 */
public class Pager<T> {
    private int     currentPage;
    private int     pageLimit;
    private int     allPageNums;
    private int     allItems;
    private int     begin;
    private int     end;
    private List<T> tLists;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
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

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
