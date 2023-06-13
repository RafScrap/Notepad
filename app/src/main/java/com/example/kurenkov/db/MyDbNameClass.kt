package com.example.kurenkov.db

import android.provider.BaseColumns

object MyDbNameClass { //класс с информацией о БД и SQL-запросами для работы с ней
    const val TABLE_NAME = "note_table"
    const val COLUMN_NAME_ID = BaseColumns._ID //для ID-столбца используется специальный класс
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_RECORD = "record"
    const val COLUMN_NAME_DATA_NOTE = "dataNote"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MyNoteDb.db"
    //SQL-запросы для работы с БД
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_RECORD TEXT," + "$COLUMN_NAME_DATA_NOTE TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}