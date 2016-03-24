package com.example.jim.bookshelf.utils;

import android.content.Context;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jim.bookshelf.bean.Article;
import com.example.jim.bookshelf.bean.ArticlePage;
import com.example.jim.bookshelf.bean.Discuss;
import com.example.jim.bookshelf.bean.LoginResult;
import com.example.jim.bookshelf.bean.Magazine;
import com.example.jim.bookshelf.bean.MagazineData;
import com.example.jim.bookshelf.bean.PageLink;
import com.example.jim.bookshelf.bean.RegistResult;
import com.example.jim.bookshelf.bean.WeiboUser;

/**
 * Created by jim on 16-3-21.
 */
public class JsonUtil
{
    private static Gson g = new Gson();

    public static LoginResult getDiscussLoginResult(String paramString)
    {
        return (LoginResult)new Gson().fromJson(paramString, LoginResult.class);
    }

    public static Discuss getDiscussNum(String paramString)
            throws JSONException
    {
        JSONObject localJSONObject = new JSONObject(paramString);
        Discuss localDiscuss = new Discuss();
        localDiscuss.setTotal(localJSONObject.optString("total"));
        localDiscuss.setTopicId(localJSONObject.optString("topicId"));
        return localDiscuss;
    }

    public static RegistResult getDiscussRegistResult(String paramString)
    {
        return (RegistResult)new Gson().fromJson(paramString, RegistResult.class);
    }

    public static String getJson(Map paramMap)
    {
        return null;
    }

    public static Magazine getMagazine(Context paramContext, String paramString)
            throws JSONException
    {
        JSONObject localJSONObject1 = new JSONObject(paramString);
        ArrayList localArrayList1 = new ArrayList();
        Magazine localMagazine = new Magazine();
        localMagazine.setId(localJSONObject1.optString("id"));
        localMagazine.setMagazine(localJSONObject1.optString("magazine"));
        localMagazine.setPublisher(localJSONObject1.optString("magazine"));
        localMagazine.setVolume(localJSONObject1.optString("volume"));
        localMagazine.setIssue(localJSONObject1.optString("issue"));
        localMagazine.setTime(localJSONObject1.optString("time"));
        localMagazine.setArticleList(localArrayList1);
        JSONArray localJSONArray1 = localJSONObject1.optJSONArray("sections");
        int i = 0;
        for (int j = 0; j < localJSONArray1.length(); j++)
        {
            JSONArray localJSONArray2 = localJSONArray1.optJSONObject(j).optJSONArray("articles");
            for (int k = 0; k < localJSONArray2.length(); k++)
            {
                JSONObject localJSONObject2 = localJSONArray2.optJSONObject(k);
                Article localArticle = new Article();
                ArrayList localArrayList2 = new ArrayList();
                localArticle.setId(localJSONObject2.optString("id"));
                localArticle.setTitle(localJSONObject2.optString("title"));
                localArticle.setAuthor(localJSONObject2.optString("author"));
                localArticle.setIntro(localJSONObject2.optString("intro"));
                localArticle.setNavHor(localJSONObject2.optString("nav-hor"));
                localArticle.setNavVer(localJSONObject2.optString("nav-ver"));
                localArticle.setThumbVer(localJSONObject2.optString("thumb-ver"));
                localArticle.setCommentUrl(localJSONObject2.optString("comment-url"));
                localArticle.setPageList(localArrayList2);
                JSONArray localJSONArray3 = localJSONObject2.optJSONArray("pdf-ver");
                for (int m = 0; m < localJSONArray3.length(); m++)
                {
                    ArticlePage localArticlePage = new ArticlePage();
                    localArticlePage.setPageVer(localJSONArray3.optString(m));
                    localArrayList2.add(localArticlePage);
                }
                JSONArray localJSONArray4 = localJSONObject2.optJSONArray("links-ver");
                if (localJSONArray4 != null)
                    for (int n = 0; n < localJSONArray4.length(); n++)
                    {
                        JSONObject localJSONObject3 = localJSONArray4.optJSONObject(n);
                        PageLink localPageLink = new PageLink();
                        String str = localJSONObject3.optString("action");
                        localPageLink.setAction(str.substring(1 + str.lastIndexOf(":")));
                        localPageLink.setPageId(localJSONObject3.optString("pageId"));
                        localPageLink.setX((float)localJSONObject3.optDouble("x"));
                        localPageLink.setY((float)localJSONObject3.optDouble("y"));
                        localPageLink.setWidth((float)localJSONObject3.optDouble("width"));
                        localPageLink.setHeight((float)localJSONObject3.optDouble("height"));
                        setLinkToPage(localArrayList2, localPageLink);
                    }
                localArrayList1.add(localArticle);
                i++;
                localMagazine.orderNum.put(localArticle.getId(), i - 1 + "");
            }
        }
        return localMagazine;
    }

    public static MagazineData getMagazineData(String paramString)
    {
        return (MagazineData)g.fromJson(paramString, MagazineData.class);
    }

    public static int getSendDiscussResult(String paramString)
            throws JSONException
    {
        return new JSONObject(paramString).optInt("resultCode");
    }

    public static List<WeiboUser> getWatchSina(String paramString)
            throws JSONException
    {
        JSONArray localJSONArray = new JSONObject(paramString).optJSONArray("users");
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < localJSONArray.length(); i++)
        {
            JSONObject localJSONObject = localJSONArray.optJSONObject(i);
            WeiboUser localWeiboUser = new WeiboUser();
            localWeiboUser.setImgUrl(localJSONObject.optString("avatar_large"));
            localWeiboUser.setNickName(localJSONObject.optString("screen_name"));
            localArrayList.add(localWeiboUser);
        }
        return localArrayList;
    }

    public static List<WeiboUser> getWatchTencent(String paramString)
            throws JSONException
    {
        JSONArray localJSONArray = new JSONObject(paramString).optJSONObject("data").optJSONArray("info");
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < localJSONArray.length(); i++)
        {
            JSONObject localJSONObject = localJSONArray.optJSONObject(i);
            WeiboUser localWeiboUser = new WeiboUser();
            localWeiboUser.setImgUrl(localJSONObject.optString("head") + "/100");
            localWeiboUser.setNickName(localJSONObject.optString("nick"));
            localArrayList.add(localWeiboUser);
        }
        return localArrayList;
    }

    private static void setLinkToPage(List<ArticlePage> paramList, PageLink paramPageLink)
    {
        for (int i = 0; i < paramList.size(); i++)
            if (paramPageLink.getPageId().equals(i + ""))
                ((ArticlePage)paramList.get(i)).setLinksVer(paramPageLink);
    }
}
