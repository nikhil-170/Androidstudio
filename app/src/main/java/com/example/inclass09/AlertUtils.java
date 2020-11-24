package com.example.inclass09;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

/*
Assignment 9 InClass
InCLass09
Group1C ---Pramukh Nagendra
        ---Nikhil Surya Petiti
 */
public class AlertUtils
{
    public static void showOKDialog(Context context, String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }
}
