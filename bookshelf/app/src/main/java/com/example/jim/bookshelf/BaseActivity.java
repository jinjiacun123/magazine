package com.example.jim.bookshelf;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity
{
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        /*
        if (!MagazineApplication.sBackProcess);
        for (boolean bool = true; ; bool = false)
        {
            MagazineApplication.sBackProcess = bool;
            return;
        }*/
    }

    protected void onRestart()
    {
        super.onRestart();

        /*if (!MagazineApplication.sBackProcess);
        for (boolean bool = true; ; bool = false)
        {
            MagazineApplication.sBackProcess = bool;
            return;
        }*/
    }

    protected void onStop()
    {
        super.onStop();

        /*if (!MagazineApplication.sBackProcess);
        for (boolean bool = true; ; bool = false)
        {
            MagazineApplication.sBackProcess = bool;
            return;
        }*/
    }
}