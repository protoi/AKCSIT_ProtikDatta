package com.example.akcsit_protikdatta;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalSession {
    SharedPreferences sPref;
    SharedPreferences.Editor pEditor;

    public LocalSession(Context context)
    {
        sPref = PreferenceManager.getDefaultSharedPreferences(context);
        pEditor = sPref.edit();
    }
    public void storeLogin()
    {
        pEditor.putBoolean("log-in", true);
        pEditor.commit();
    }
    public boolean checkLogin()
    {
        return sPref.getBoolean("log-in", false);
    }
    public void storeCredential(String uid, String email)
    {
        pEditor.putString("user-id", uid);
        pEditor.putString("user-mail", email);
        pEditor.commit();
    }
    public String getCredential(String k)
    {
        return sPref.getString(k, "");
    }
    public void delIndividual(String k)
    {
        pEditor.remove(k);
        pEditor.commit();
    }
    public void delete()
    {
        pEditor.clear();
        pEditor.commit();
    }

}
