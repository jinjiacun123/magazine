package com.example.jim.bookshelf.bean;

import java.util.List;

/**
 * Created by jim on 16-3-19.
 */
public class Article
{
    private String author;
    private String commentUrl;
    private String id;
    private String intro;
    private String navHor;
    private String navVer;
    private List<ArticlePage> pageList;
    private int sections;
    private String thumbHor;
    private String thumbVer;
    private String title;
    private String url;

    public boolean equals(Object paramObject)
    {
        if (this == paramObject);
            Article localArticle;
        do
        {
            if ((paramObject == null) || (getClass() != paramObject.getClass()))
                return false;
            localArticle = (Article)paramObject;
            return true;
        }
        while (this.id.equals(localArticle.id));
        //return false;
    }

    public ArticlePage getArticlePage(int paramInt)
    {
        if ((paramInt < 0) || (paramInt > -1 + this.pageList.size()))
            return null;
        return this.pageList.get(paramInt);
    }

    public String getAuthor()
    {
        return this.author;
    }

    public String getCommentUrl()
    {
        return this.commentUrl;
    }

    public String getId()
    {
        return this.id;
    }

    public String getIntro()
    {
        return this.intro;
    }

    public String getNavHor()
    {
        return this.navHor;
    }

    public String getNavVer()
    {
        return this.navVer;
    }

    public List<ArticlePage> getPageList()
    {
        return this.pageList;
    }

    public int getSections()
    {
        return this.sections;
    }

    public String getThumbHor()
    {
        return this.thumbHor;
    }

    public String getThumbVer()
    {
        return this.thumbVer;
    }

    public String getTitle()
    {
        return this.title;
    }

    public int hashCode()
    {
        return this.id.hashCode();
    }

    public void setAuthor(String paramString)
    {
        this.author = paramString;
    }

    public void setCommentUrl(String paramString)
    {
        this.commentUrl = paramString;
    }

    public void setId(String paramString)
    {
        this.id = paramString;
    }

    public void setIntro(String paramString)
    {
        this.intro = paramString;
    }

    public void setNavHor(String paramString)
    {
        this.navHor = paramString;
    }

    public void setNavVer(String paramString)
    {
        this.navVer = paramString;
    }

    public void setPageList(List<ArticlePage> paramList)
    {
        this.pageList = paramList;
    }

    public void setSections(int paramInt)
    {
        this.sections = paramInt;
    }

    public void setThumbHor(String paramString)
    {
        this.thumbHor = paramString;
    }

    public void setThumbVer(String paramString)
    {
        this.thumbVer = paramString;
    }

    public void setTitle(String paramString)
    {
        this.title = paramString;
    }
}
