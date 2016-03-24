package com.example.jim.bookshelf.ui;

public class NavigationDrawerItem
{
    private int itemIcon;
    private String itemName;
    private String itemSubTitle;
    private boolean mainItem;
    private boolean selected;

    public NavigationDrawerItem(String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    {
        this.itemName = paramString1;
        this.itemIcon = paramInt;
        this.mainItem = paramBoolean;
        this.itemSubTitle = paramString2;
    }

    public NavigationDrawerItem(String paramString1, String paramString2, boolean paramBoolean)
    {
        this(paramString1, paramString2, 0, paramBoolean);
    }

    public NavigationDrawerItem(String paramString, boolean paramBoolean)
    {
        this(paramString, "", 0, paramBoolean);
    }

    public int getItemIcon()
    {
        return this.itemIcon;
    }

    public String getItemName()
    {
        return this.itemName;
    }

    public String getItemSubTitle()
    {
        return this.itemSubTitle;
    }

    public boolean isMainItem()
    {
        return this.mainItem;
    }

    public boolean isSelected()
    {
        return this.selected;
    }

    public void setItemIcon(int paramInt)
    {
        this.itemIcon = paramInt;
    }

    public void setItemName(String paramString)
    {
        this.itemName = paramString;
    }

    public void setItemSubTitle(String paramString)
    {
        this.itemSubTitle = paramString;
    }

    public void setMainItem(boolean paramBoolean)
    {
        this.mainItem = paramBoolean;
    }

    public void setSelected(boolean paramBoolean)
    {
        this.selected = paramBoolean;
    }
}