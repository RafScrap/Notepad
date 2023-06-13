package com.example.kurenkov

import java.text.SimpleDateFormat
import java.util.Date

class Note { //запись в блокноте
    var id : Int = -1 //id записи в БД
    var title : String = "title" //заголовок записи
    var record : String = "record" //основной текст записи
    var dataNote : String = "2023-01-01" //дата создания/изменения записи
    //конструктор для добавления новой/изменённой записи
    constructor(title: String, record : String) {
        val formatter = SimpleDateFormat("yyyy-MM-dd") //формат записи даты
        this.title = title
        this.record = record
        this.dataNote = formatter.format(Date())
    }
    //конструктор для считывания записи из БД
    constructor(title: String, record : String, dataNote: String, id: Int) {
        this.id = id
        this.title = title
        this.record = record
        this.dataNote = dataNote
    }
    //arrayAdapter реально работает с типом String, а потому конвертирует Note в String
    override fun toString(): String {
        return title  //возвращаем заговолок записи, так как в ListView
        // (с которым работает arrayAdapter) должен отображаться именно он
    }
}