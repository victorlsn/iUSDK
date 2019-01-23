package br.com.iuapp.android.sdk;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by victorlsn on 05/11/18.
 */

public class ContactsUtil {
    private static final String[] PROJECTION = new String[]{
            ContactsContract.RawContacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.CONTACT_ID,
            ContactsContract.RawContacts.VERSION};

    /**
     * Metodo responsavel por buscar contatos na agenda do dispositivo.
     *
     * @param context Contexto utilizado na chamada (ex. Activity, etc)
     * @param userDDD String contendo o DDD do usuario do aplicativo, utilizado para determinar como DDD padrao em contatos sem DDD.
     * @return ArrayList com todos os contatos validos.
     */
    public ArrayList<PhoneContact> getValidContacts(Context context, String userDDD) {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String order = ContactsContract.Contacts.DISPLAY_NAME + " ASC";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactsCursor = contentResolver.query(uri, PROJECTION,
                ContactsContract.Contacts.HAS_PHONE_NUMBER, null, order);
        ArrayList<PhoneContact> contacts = null;
        if (null != contactsCursor) {
            contacts = getContacts(contactsCursor, userDDD);
            contactsCursor.close();
        }
        return contacts;
    }

    private ArrayList<PhoneContact> getContacts(Cursor cursor, String userDDD) {
        Set<PhoneContact> appContacts = new HashSet<>();

        while (cursor.moveToNext()) {
            String sUserContactPhone = FormatUtil.setToPhonePattern(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER)), userDDD);

            String sUserContactName = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            PhoneContact contact = new PhoneContact(sUserContactName, sUserContactPhone);
            if (!sUserContactPhone.equals("#")) {
                appContacts.add(contact);
            }
        }


        return convertSetIntoList(appContacts);
    }

    private ArrayList<PhoneContact> convertSetIntoList(Set<PhoneContact> contactsSet) {

        ArrayList<PhoneContact> contacts = new ArrayList<>();
        for (PhoneContact contact : contactsSet) {
            contacts.add(contact);
        }

        return contacts;
    }
}
