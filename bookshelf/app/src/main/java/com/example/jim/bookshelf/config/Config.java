package com.example.jim.bookshelf.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jim on 16-3-19.
 */
public class Config
{
    private static final String CONFIG_BOOKMARK_ARTICLE_NUM = "currentArticle";
    private static final String CONFIG_BOOKMARK_ARTICLE_PAGE_NUM = "currentArticlePage";
    private static final String CONFIG_BOOKMARK_MAGAZINE_ID = "bookmark";
    private static final String CONFIG_FILE_NAME = "magazineConfig";
    private static final String CONFIG_NICKNMAE = "nickName";
    private static Config config;
    private Context context;
    private SharedPreferences preferences;

    private Config(Context paramContext)
    {
        this.context = paramContext;
        this.preferences = paramContext.getSharedPreferences("magazineConfig", 1);
    }

    public static Config getInstence(Context paramContext)
    {
        if (config == null)
            config = new Config(paramContext);
        return config;
    }

    public void cleanBookmark()
    {
        setBookmarkByMagazineId("NULL");
        setBookmarkByArticleNum(0);
        setBookmarkByArticlePageNum(0);
    }

    public int getBookmarkByArticleNum()
    {
        return this.preferences.getInt("currentArticle", 0);
    }

    public int getBookmarkByArticlePageNum()
    {
        return this.preferences.getInt("currentArticlePage", 0);
    }

    public String getBookmarkByMagazineId()
    {
        return this.preferences.getString("bookmark", "0");
    }

    public String getNickName()
    {
        return this.preferences.getString("nickName", " ");
    }

    public void setBookmarkByArticleNum(int paramInt)
    {
        SharedPreferences.Editor localEditor = this.preferences.edit();
        localEditor.putInt("currentArticle", paramInt);
        localEditor.commit();
    }

    public void setBookmarkByArticlePageNum(int paramInt)
    {
        SharedPreferences.Editor localEditor = this.preferences.edit();
        localEditor.putInt("currentArticlePage", paramInt);
        localEditor.commit();
    }

    public void setBookmarkByMagazineId(String paramString)
    {
        SharedPreferences.Editor localEditor = this.preferences.edit();
        localEditor.putString("bookmark", paramString);
        localEditor.commit();
    }

    public void setNickName(String paramString)
    {
        SharedPreferences.Editor localEditor = this.preferences.edit();
        localEditor.putString("nickName", paramString);
        localEditor.commit();
    }
}
