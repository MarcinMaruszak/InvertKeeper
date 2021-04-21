package com.Maruszak.MantisKeeper.DTO;

import com.Maruszak.MantisKeeper.model.Invertebrate;
import com.Maruszak.MantisKeeper.model.L;
import com.Maruszak.MantisKeeper.model.Sex;
import com.Maruszak.MantisKeeper.model.Type;

import java.util.List;

public class TableDTO {
    private List<Invertebrate> inverts;
    private int PageNo;
    private int totalPages;
    private String sortBY;
    private String direction;
    private Type insectType;
    private Sex sex;
    private L lastInstar;

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

    public Type getInsectType() {
        return insectType;
    }

    public void setInsectType(Type insectType) {
        this.insectType = insectType;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public L getLastInstar() {
        return lastInstar;
    }

    public void setLastInstar(L lastInstar) {
        this.lastInstar = lastInstar;
    }
}
