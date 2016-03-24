package com.example.jim.bookshelf.bean;

/**
 * Created by jim on 16-3-19.
 */
public class PageLink
{
    private String action;
    private float height;
    private String pageId;
    private float width;
    private float x;
    private float y;

    public String getAction()
    {
        return this.action;
    }

    public float getHeight()
    {
        return this.height;
    }

    public String getPageId()
    {
        return this.pageId;
    }

    public float getWidth()
    {
        return this.width;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public void setAction(String paramString)
    {
        this.action = paramString;
    }

    public void setHeight(float paramFloat)
    {
        this.height = paramFloat;
    }

    public void setPageId(String paramString)
    {
        this.pageId = paramString;
    }

    public void setWidth(float paramFloat)
    {
        this.width = paramFloat;
    }

    public void setX(float paramFloat)
    {
        this.x = paramFloat;
    }

    public void setY(float paramFloat)
    {
        this.y = paramFloat;
    }
}
