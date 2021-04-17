package com.Maruszak.MantisKeeper.DTO;

import com.Maruszak.MantisKeeper.model.Invertebrate;

import java.util.List;

public class TableDTO {
    private List<Invertebrate> inverts;
    private int PageNo;
    private int totalPages;
    private String sortBY;
    private String direction;

    public TableDTO() {
    }

    public List<Invertebrate> getInverts() {
        return inverts;
    }

    public void setInverts(List<Invertebrate> inverts) {
        this.inverts = inverts;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSortBY() {
        return sortBY;
    }

    public void setSortBY(String sortBY) {
        this.sortBY = sortBY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
