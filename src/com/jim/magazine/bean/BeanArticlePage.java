package com.jim.magazine.bean;

public class BeanArticlePage
{
  private BeanPageLink linksHor;
  private BeanPageLink linksVer;
  private String pageHor;
  private String pageVer;

  public BeanPageLink getLinksHor()
  {
    return this.linksHor;
  }

  public BeanPageLink getLinksVer()
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

  public void setLinksHor(BeanPageLink paramPageLink)
  {
    this.linksHor = paramPageLink;
  }

  public void setLinksVer(BeanPageLink paramPageLink)
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