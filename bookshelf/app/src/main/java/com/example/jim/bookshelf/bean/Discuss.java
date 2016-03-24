package com.example.jim.bookshelf.bean;

import android.content.Context;
import com.example.jim.bookshelf.config.Config;
import java.io.Serializable;
/**
 * Created by jim on 16-3-21.
 */
public class Discuss
        implements Serializable
{
    private String title;
    private String topicId;
    private String total;
    private String url;

    public static String getNickName(Context paramContext)
    {
        return Config.getInstence(paramContext).getNickName();
    }

    public static void setNickName(Context paramContext, String paramString)
    {
        Config.getInstence(paramContext).setNickName(paramString);
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getTopicId()
    {
        return this.topicId;
    }

    public String getTotal()
    {
        return this.total;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setTitle(String paramString)
    {
        this.title = paramString;
    }

    public void setTopicId(String paramString)
    {
        this.topicId = paramString;
    }

    public void setTotal(String paramString)
    {
        this.total = paramString;
    }

    public void setUrl(String paramString)
    {
        this.url = paramString;
    }
}
