package com.devbramm.soga.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.devbramm.soga.models.ContactItemList;

import java.util.ArrayList;

public class ContactHelperUtils {

    public static ArrayList<ContactItemList> getContacts(Context context) {
        ArrayList<ContactItemList> contactsList = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            do {
                String contactName = cursor.getString(nameColumnIndex);
                String contactPhone = cursor.getString(numberColumnIndex);

                // Assuming ContactInSoga is always "yes" and ContactAbout is always "about"
                String contactInSoga = "yes";
                String contactAbout = "about";

                ContactItemList contactItem = new ContactItemList(contactName, contactPhone, contactInSoga, contactAbout);
                contactsList.add(contactItem);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return contactsList;
    }
}
