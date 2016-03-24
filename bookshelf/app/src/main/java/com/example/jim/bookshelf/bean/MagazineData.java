package com.example.jim.bookshelf.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by jim on 16-3-19.
 */
public class MagazineData
{

    @Expose
    private List<Magazine> magazines;

    public List<Magazine> getMagazines()
    {
        return this.magazines;
    }

    public void setMagazines(List<Magazine> paramList)
    {
        this.magazines = paramList;
    }
}
