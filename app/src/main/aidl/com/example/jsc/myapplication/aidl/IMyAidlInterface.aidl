// IMyAidlInterface.aidl
package com.example.jsc.myapplication.aidl;
import com.example.jsc.myapplication.aidl.Book;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
void setBook(in Book book);
Book getBook();
}
