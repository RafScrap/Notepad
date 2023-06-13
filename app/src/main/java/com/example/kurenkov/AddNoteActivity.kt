package com.example.kurenkov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {
    private lateinit var edAddNoteTitle : EditText //поле для записи заголовка
    private lateinit var edAddNoteRecord : EditText //поле для записи основного текста
    private lateinit var tvAddNoteData : TextView //поле, где отображается дата

    //функция, активирующаяся при создании AddNoteActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        //связываем все три поля с полями на экране
        edAddNoteTitle = findViewById(R.id.edAddNoteTitle)
        edAddNoteRecord = findViewById(R.id.edAddNoteRecord)
        tvAddNoteData = findViewById(R.id.tvAddNoteData)
        //в поле для даты записываем текущую дату, так как запись в блокноте — новая
        val formatter = SimpleDateFormat("yyyy-MM-dd") //формат записи даты
        tvAddNoteData.text = formatter.format(Date())
    }

    //функция нажатия на кнопку сохранения новой записи в блокноте
    fun onClickSaveNote(view: View) {
        // интент для передачи данных в прошлую Асtivity (т.е. в MainАсtivity)
        val dataIntent = Intent()
        //записываем в интент, что нужно сохранить новую запись
        dataIntent.putExtra("saveNote", true)
        //считываем данные новой записи в интент
        dataIntent.putExtra("title", edAddNoteTitle.text.toString())
        dataIntent.putExtra("record", edAddNoteRecord.text.toString())
        setResult(RESULT_OK, dataIntent) //записываем в интент, что всё прошло успешно
        finish() //заканчиваем деятельность текущей Асtivity
    }

    //функция нажатия на кнопку отмены создания новой записи в блокноте
    fun onClickCancel(view: View) {
        // интент для передачи данных в прошлую Асtivity (т.е. в MainАсtivity)
        val dataIntent = Intent()
        //записываем в интент, что не нужно сохранять новую запись
        dataIntent.putExtra("saveNote", false)
        setResult(RESULT_OK, dataIntent) //записываем в интент, что всё прошло успешно
        finish() //заканчиваем деятельность текущей Асtivity
    }
}