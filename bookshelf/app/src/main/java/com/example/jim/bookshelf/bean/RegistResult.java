package com.example.jim.bookshelf.bean;

/**
 * Created by jim on 16-3-21.
 */
public class RegistResult
{
    private int account;
    private int accountId;
    private String cmu;
    private String desc;
    private String returnUrl;
    private String session;
    private int status;

    public int getAccount()
    {
        return this.account;
    }

    public int getAccountId()
    {
        return this.accountId;
    }

    public String getCmu()
    {
        return this.cmu;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public String getReturnUrl()
    {
        return this.returnUrl;
    }

    public String getSession()
    {
        return this.session;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setAccount(int paramInt)
    {
        this.account = paramInt;
    }

    public void setAccountId(int paramInt)
    {
        this.accountId = paramInt;
    }

    public void setCmu(String paramString)
    {
        this.cmu = paramString;
    }

    public void setDesc(String paramString)
    {
        this.desc = paramString;
    }

    public void setReturnUrl(String paramString)
    {
        this.returnUrl = paramString;
    }

    public void setSession(String paramString)
    {
        this.session = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }
}
