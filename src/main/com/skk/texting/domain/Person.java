package com.skk.texting.domain;

import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.HashMap;

public class Person {
    private String name;
    private String address;
    private String contactType;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Person() { }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Person fromCursor(Cursor cursor){

        Person person = new Person();

        person.address = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        person.name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        person.contactType = getType(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)));

        return person;
    }

    private static String getType(String string) {

        int type = Integer.parseInt(string);

        HashMap<Integer, String> typeString = new HashMap<Integer, String>();
        typeString.put(ContactsContract.CommonDataKinds.Phone.TYPE_HOME, "HOME");
        typeString.put(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, "MOBILE");

        return typeString.containsKey(type)? typeString.get(type) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static Person fromAddress(String address) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }
}
