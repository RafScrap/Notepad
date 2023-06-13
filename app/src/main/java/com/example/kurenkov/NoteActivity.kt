package com.example.kurenkov

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {

    private lateinit var edNoteTitle : EditText //поле для записи заголовка
    private lateinit var edNoteRecord : EditText //поле для записи основного текста
    private lateinit var tvNoteData : TextView //поле, где отображается дата

    //функция, активирующаяся при создании AddNoteActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        //связываем все три поля с полями на экране по id
        edNoteTitle = findViewById(R.id.edNoteTitle)
        edNoteRecord = findViewById(R.id.edNoteRecord)
        tvNoteData = findViewById(R.id.tvNoteData)
        //считываем данные из интента Activity, из которой мы пришли (т.е. MainActivity),
        //и записываем их в поля на экране
        edNoteTitle.setText(intent.extras!!.getString("title"))
        edNoteRecord.setText(intent.extras!!.getString("record"))
        tvNoteData.text = intent.extras!!.getString("dataNote")
    }

    //функция нажатия на кнопку сохранения изменений в записи блокнота
    fun onClickSave(view: View) {
        //интент для передачи данных в прошлую Асtivity (т.е. в MainАсtivity)
        val dataIntent = Intent()
        //записываем в интент, что нужно сохранить измененную запись, а не удалить
        dataIntent.putExtra("delete", false)
        //записываем в интент измененные данные записи
        dataIntent.putExtra("title", edNoteTitle.text.toString())
        dataIntent.putExtra("record", edNoteRecord.text.toString())
        setResult(RESULT_OK, dataIntent) //записываем в интент, что всё прошло успешно
        finish() //заканчиваем деятельность текущей Асtivity
    }

    //функция нажатия на кнопку удаления записи блокнота
    fun onClickDelete(view: View) {
        //интент для передачи данных в прошлую Асtivity (т.е. в MainАсtivity)
        val dataIntent = Intent()
        //записываем в интент, что нужно удалить текущую запись
        dataIntent.putExtra("delete", true)
        setResult(RESULT_OK, dataIntent) //записываем в интент, что всё прошло успешно
        finish() //заканчиваем деятельность текущей Асtivity
    }
}