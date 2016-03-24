package com.example.jim.bookshelf.bean;

/**
 * Created by jim on 16-3-19.
 */
public class ArticlePage
{
    private PageLink linksHor;
    private PageLink linksVer;
    private String pageHor;
    private String pageVer;

    public PageLink getLinksHor()
    {
        return this.linksHor;
    }

    public PageLink getLinksVer()
    {
        return this.linksVer;
    }

    public String getPageHor()
    {
        return this.pageHor;
    }

    public String getPageVer()
    {
        return this.pageVer;
    }

    public void setLinksHor(PageLink paramPageLink)
    {
        this.linksHor = paramPageLink;
    }

    public void setLinksVer(PageLink paramPageLink)
    {
        this.linksVer = paramPageLink;
    }

    public void setPageHor(String paramString)
    {
        this.pageHor = paramString;
    }

    public void setPageVer(String paramString)
    {
        this.pageVer = paramString;
    }
}
