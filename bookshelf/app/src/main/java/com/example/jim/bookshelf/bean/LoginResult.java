package com.example.jim.bookshelf.bean;

/**
 * Created by jim on 16-3-21.
 */
public class LoginResult
{
    private String account;
    private String auth_token;
    private String cmu;
    private String session;
    private int status;
    private String user_id;

    public String getAccount()
    {
        return this.account;
    }

    public String getAuth_token()
    {
        return this.auth_token;
    }

    public String getCmu()
    {
        return this.cmu;
    }

    public String getSession()
    {
        return this.session;
    }

    public int getStatus()
    {
        return this.status;
    }

    public String getUser_id()
    {
        return this.user_id;
    }

    public void setAccount(String paramString)
    {
        this.account = paramString;
    }

    public void setAuth_token(String paramString)
    {
        this.auth_token = paramString;
    }

    public void setCmu(String paramString)
    {
        this.cmu = paramString;
    }

    public void setSession(String paramString)
    {
        this.session = paramString;
    }

    public void setStatus(int paramInt)
    {
        this.status = paramInt;
    }

    public void setUser_id(String paramString)
    {
        this.user_id = paramString;
    }
}
