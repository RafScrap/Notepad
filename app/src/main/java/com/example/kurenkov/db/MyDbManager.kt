package com.example.kurenkov.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.kurenkov.Note
import java.util.ArrayList

class MyDbManager(context: Context) {
    private val myDbHelper = MyDbHelper(context) //вспомогательный объект для работы с БД
    private var db:SQLiteDatabase? = null //БД

    fun openDb() { //открытие БД
        db = myDbHelper.writableDatabase //"инициализация" БД
    }

    fun insertToDb(note: Note) : Int { //добавление в БД записи в блокноте и возврат её id в БД
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, note.title)
            put(MyDbNameClass.COLUMN_NAME_RECORD, note.record)
            put(MyDbNameClass.COLUMN_NAME_DATA_NOTE, note.dataNote)
        }
        val id = db?.insert(MyDbNameClass.TABLE_NAME, null, values)
        return id?.toInt()!! //возврат id новой записи в БД
    }

    fun deleteToDb(id: Int) { //удаление записи в блокноте из БД
        db?.delete(MyDbNameClass.TABLE_NAME, MyDbNameClass.COLUMN_NAME_ID + "=" + id.toString(), null)
    }

    fun readDbData() : ArrayList<Note> { //чтение данных из БД
        val dataList = ArrayList<Note>() //список, куда будут записываться данные из БД
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null,
            null, null, null) //объект, которым мы построчно считываем данные из БД
        while (cursor?.moveToNext()!!) { //построчное чтение данных из БД
            //формирование записи для блокнота
            val note = Note(cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_RECORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_DATA_NOTE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_ID)),
            )
            dataList.add(note) //добавление записи в список
        }
        cursor.close() //"закрытие" объекта для считывания
        return dataList //возврат списка со считанными из БД записями
    }
    fun closeDb() { //закрытие БД
        myDbHelper.close()
    }
}