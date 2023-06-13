package com.example.kurenkov

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.kurenkov.db.MyDbManager

class MainActivity : AppCompatActivity() {
    private val myDbManager = MyDbManager(this) //объект для работы с БД
    private var notes : ArrayList<Note> = ArrayList() //список записей в приложении

    private lateinit var lvNotes: ListView // ListView для отображения списка записей notes
    private lateinit var arrayAdapter: ArrayAdapter<Note>

    private var index : Int = -1 // индекс записи в ListView, на которую мы нажимали

    //функция, активирующаяся при создании MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvNotes = findViewById(R.id.lvNotes) //связываем ListView с тем, что на экране, по id
        // Здесь и в других подобных случаях:
        // Обратиться к ListView на экране напрямую в новых версиях Андроид Студио нельзя

        //адаптер, реагирующий на нажатие элемента в списке ListView
        val itemClickListener = AdapterView.OnItemClickListener { _, _, _, id ->
                index = id.toInt() //запоминаем индекс записи, на которую нажали
                //переходим в NoteActivity (экран отображения нажатой записи)
                goToNoteActivity()
            }
        //используем itemClickListener для ListView с именем lvNotes
        lvNotes.onItemClickListener = itemClickListener

        myDbManager.openDb() //открываем БД
        notes = myDbManager.readDbData() //считываем данные из БД в приложение
        //arrayAdapter связывает ListView (simple_list_item_1) и список записей (notes)
        //чтобы элементы notes отображались в ListView (см. также класс Notes)
        arrayAdapter = ArrayAdapter<Note>(
            this, android.R.layout.simple_list_item_1, notes
        )
        //используем arrayAdapter для ListView с именем lvNotes
        lvNotes.adapter = arrayAdapter
    }

    //функия при нажатии на кнопку «Аdd»
    fun onClickAdd(view: View) {
        goToAddNoteActivity() //переход в AddNoteActivity (экран создания новой записи)
    }

    //переход в NoteActivity (экран отображения записи)
    private fun goToNoteActivity() {
        //intent — объект для перехода между Activity («экранами»)
        // в данном случае — от MainActivity (this) к goToNoteAddActivity
        val intent = Intent(this, NoteActivity::class.java)
        //записываем в intent данные выбранной для отображения записи для их передачи в NoteActivity
        intent.putExtra("title", notes[index].title)
        intent.putExtra("record", notes[index].record)
        intent.putExtra("dataNote", notes[index].dataNote)
        startNoteForResult.launch(intent); //запускаем intent с помощью объекта startNoteForResult
    }
    //переход в AddNoteActivity (экран создания новой записи)
    private fun goToAddNoteActivity() {
        //intent — объект для перехода между Activity («экранами»)
        // в данном случае — от MainActivity (this) к goToNoteAddActivity
        val intent = Intent(this, AddNoteActivity::class.java)
        startAddNoteForResult.launch(intent); //запускаем intent с помощью объекта startAddNoteForResult
    }

    //обьект, запускающий интент на отображение выбранной записи в блокноте
    private var startNoteForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            //Callback-функция, вызывающаяся при завершении интента
            ActivityResultCallback { result ->
                if (result.resultCode === RESULT_OK) { //проверяем, что прошлая Activity завершилась успешно
                    val intent: Intent? = result.data //достаем данные из полученного от NoteActivity интента
                    //если выбранную запись в блокноте нужно удалить
                    if (intent?.getBooleanExtra("delete", false)!!) {
                        myDbManager.deleteToDb(notes[index].id) //удаление записи в БД по её id
                        notes.removeAt(index) //удаление записи в списке в приложении
                        arrayAdapter.notifyDataSetChanged() //обновляем ListView, отвечающий
                        // за отображение списка записей на экране

                    }
                    else { //если в выбранной записи в блокноте нужно сохранить изменения
                        //удаление элемента
                        myDbManager.deleteToDb(notes[index].id) //удаление записи в БД по её id
                        notes.removeAt(index) //удаление записи в списке в приложении
                        //добавление элемента
                        val note = Note( //создаем новую запись с измененными данными старой
                            intent.getStringExtra("title")!!,
                            intent.getStringExtra("record")!!
                        )
                        val id : Int = myDbManager.insertToDb(note) //записываем её в БД
                        note.id = id //записываем в новую запись её id в БД
                        notes.add(note) //добавляем запись в список в приложении
                        arrayAdapter.notifyDataSetChanged() //обновляем ListView, отвечающий
                        // за отображение списка записей на экране
                    }
                }
            }
        )

    //обьект, запускающий интент на добавление новой записи в блокнот
    private var startAddNoteForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            //Callback-функция, вызывающаяся при завершении интента
            ActivityResultCallback { result ->
                if (result.resultCode === RESULT_OK) { //проверяем, что прошлая Activity завершилась успешно
                    val intent: Intent? = result.data //достаем данные из полученного от AddNoteActivity интента
                    //если новую запись нужно сохранить
                    if (intent?.getBooleanExtra("saveNote", false)!!) {
                        val note = Note( //создаем новую запись с записанными в AddNoteActivity данными
                            intent.getStringExtra("title")!!,
                            intent.getStringExtra("record")!!
                        )
                        val id : Int = myDbManager.insertToDb(note) //записываем её в БД
                        note.id = id //записываем в новую запись её id в БД
                        notes.add(note) //добавляем запись в список в приложении
                        arrayAdapter.notifyDataSetChanged() //обновляем ListView, отвечающий
                        // за отображение списка записей на экране
                    }
                }
            }
        )
    //функция, активирующаяся при разрушении MainActivity
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb() //закрытие БД
    }
}