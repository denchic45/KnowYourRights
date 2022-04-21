package com.denchic45.knowyourrights.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.denchic45.knowyourrights.data.dao.AnswerDao
import com.denchic45.knowyourrights.data.dao.QuestionDao
import com.denchic45.knowyourrights.data.dao.QuizDao
import com.denchic45.knowyourrights.data.dao.QuizResultDao
import com.denchic45.knowyourrights.data.model.AnswerEntity
import com.denchic45.knowyourrights.data.model.QuestionEntity
import com.denchic45.knowyourrights.data.model.QuizEntity
import com.denchic45.knowyourrights.data.model.QuizResultEntity
import com.denchic45.knowyourrights.utils.UUIDS
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        QuizEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
        QuizResultEntity::class
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class DataBase : RoomDatabase() {

    abstract fun quizDao(): QuizDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao

    companion object {
        private var instance: DataBase? = null

        @Synchronized
        fun getInstance(context: Context): DataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "database.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            suspend fun addQuizzes() {
                                val quizId1 = UUIDS.createShort()
                                val quizId2 = UUIDS.createShort()
                                instance!!.quizDao().apply {
                                    upsert(
                                        listOf(
                                            QuizEntity(
                                                id = quizId1,
                                                name = "Административная ответственность",
                                                imageUrl = "https://kadry.mcfr.kz/images/articles/141/Administrativnaya-otvetstvennosty.jpg",
                                                lectureUrl = "https://edu.kpfu.ru/mod/book/view.php?id=248405&chapterid=7043"
                                            ),
                                            QuizEntity(
                                                id = quizId2,
                                                name = "Трудоустройство",
                                                imageUrl = "https://estart.com.ua/images/services/photo9.jpg",
                                                lectureUrl = "https://moodle.kstu.ru/mod/book/view.php?id=16370"
                                            ),
                                            QuizEntity(
                                                id = UUIDS.createShort(),
                                                name = "Права ребенка",
                                                imageUrl = "https://www.ya-roditel.ru/upload/resizeman/1__upload_iblock_709_709fa4f487439ca4bb56b822f909779c.jpg?cache=Y",
                                                lectureUrl = ""
                                            ),
                                            QuizEntity(
                                                id = UUIDS.createShort(),
                                                name = "Потребитель и его права",
                                                imageUrl = "https://avatars.mds.yandex.net/get-zen_doc/1866022/pub_5d189b92913f0600acb02862_5d189d9457394600adca7815/scale_1200",
                                                lectureUrl = ""
                                            ),
                                            QuizEntity(
                                                id = UUIDS.createShort(),
                                                name = "Право на информацию",
                                                imageUrl = "https://4brain.ru/blog/wp-content/uploads/2019/08/critical-information-analysis.jpg",
                                                lectureUrl = ""
                                            ),
                                        )
                                    )
                                }

                                instance!!.questionDao().upsert(
                                    listOf(
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "При взимании штрафа на месте совершения правонарушения нарушителю выдается",
                                            imageUrl = "https://life-pay.ru/wp-content/uploads/2019/09/296.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "повестка в орган внутренних дел;" +
                                                    "квитанция установленного образца;" +
                                                    "расписка должностного лица;" +
                                                    "копия нарушенного правового акта",
                                            correctAnswer = "квитанция установленного образца"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "С какого возраста лицо подлежит административной ответственности",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "14 лет;" +
                                                    "15 лет;" +
                                                    "16 лет;" +
                                                    "18 лет",
                                            correctAnswer = "16 лет"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Дела о мелком хулиганстве рассматриваются в течение",
                                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXhmd8hWFBzriCTNzfqUaGyT3kr3zjgYWfpA&usqp=CAU",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "3 суток;15 дней;суток;10 дней",
                                            correctAnswer = "15 дней"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Основанием для административной ответственности является:",
                                            imageUrl = "https://st1.prosto.im/cache/st1/8/3/6/2/83628/83628.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "правонарушение;" +
                                                    "административное правонарушение;" +
                                                    "административное правонарушение или преступление небольшой тяжести;" +
                                                    "преступление",
                                            correctAnswer = "административное правонарушение"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Какой срок дается на погашение административного наказания?",
                                            imageUrl = "https://www.garant.ru/files/2/6/1268962/200srok-davnosti-privlecheniya-kollektorov-k-administrativnoj-otvetstvennosti-mozhet-vozrasti-do-dvuh-let.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "6 месяцев;" +
                                                    "1 год;" +
                                                    "3 года;" +
                                                    "6 лет",
                                            correctAnswer = "1 год"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Что может устанавливаться законами субъектов РФ в качестве меры административного наказания?",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "Административный штраф;" +
                                                    "Предупреждение;" +
                                                    "Конфискация предмета административного правонарушения",
                                            correctAnswer = "Административный штраф;Предупреждение"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Кто рассматривает дела об административных правонарушениях, совершенных военнослужащими",
                                            imageUrl = "data:image/jpeg;base64,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",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Судьи арбитражных судов;" +
                                                    "Мировые судьи;" +
                                                    "Судьи районных судов;" +
                                                    "Судьи гарнизонных военных судов",
                                            correctAnswer = "Судьи гарнизонных военных судов"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "В чем разница между административным и прокурорским надзором",
                                            imageUrl = "data:image/jpeg;base64,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",
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "Полномочиями надзорных органов;" +
                                                    "Субъектами надзора",
                                            correctAnswer = "Полномочиями надзорных органов;Субъектами надзора"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Что из перечисленного не является административным наказанием",
                                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzRnRzQ302-DCId3MkAqdiN8SY-RQeWFNZfB3nk0EuH4Rv9uRiMXeoLq4WOTcth_Puxug&usqp=CAU",
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "Административный арест;" +
                                                    "Административный штраф;" +
                                                    "Предупреждение;" +
                                                    "Обязательные работы",
                                            correctAnswer = "Обязательные работы"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Согласно общему правилу срок административного задержания должен быть не более",
                                            imageUrl = "https://www.advo24.ru/upload/iblock/1f6/zaderzhanie_srok_sud_sizo.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = " 1 суток;" +
                                                    "2 суток;" +
                                                    "3 часов;" +
                                                    "6 часов",
                                            correctAnswer = "3 часов"
                                        ),
                                    ) + listOf(
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Нормальная продолжительность рабочего времени относительно законодательства не может превышать …",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "8 часов в смену;" +
                                                    "300 календарных дней в год;" +
                                                    "40 часов в неделю;" +
                                                    "8 часов в день;" +
                                                    "28 календарных дней в месяц",
                                            correctAnswer = "40 часов в неделю"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Ночное время продолжается с … часов",
                                            imageUrl = "https://pravodeneg.net/wp-content/uploads/2018/04/1-33-1024x576-300x300.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "22.00 до 6;" +
                                                    "23.00 до 6;" +
                                                    "22.00 до 5",
                                            correctAnswer = "22.00 до 6"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Продолжительность еженедельного непрерывного отдыха не может быть менее … часов",
                                            imageUrl = "https://lh3.googleusercontent.com/WftSr0LKPAelPBidpR0IJyHfBQzPRT9dbXCGGIr0RcXiZCkSiDxhrHdLOik-d4KhAR5XOgxE0PkrjWwpCJ1lU14SclrDI7AhTRFz6Dq6i6AWwaP_wDL4cALbIXEYsevr1_0VcBUq",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "24;" +
                                                    "36;" +
                                                    "42;" +
                                                    "48;" +
                                                    "12",
                                            correctAnswer = "42"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Испытательный срок для рабочих составляет:",
                                            imageUrl = "https://avatars.mds.yandex.net/get-zen_doc/225901/pub_5bfd575497773400aae77a01_5bfd57a79d365500aa5f822a/scale_1200",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "3 месяца;" +
                                                    "10 дней;" +
                                                    "1 месяц;" +
                                                    "6 месяцев",
                                            correctAnswer = "1 месяц"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Трудовой договор может прекратится по инициативе:",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "Собственника;" +
                                                    "Работника;" +
                                                    "Cотрудников милиции;" +
                                                    "Начальника отдела кадров;" +
                                                    "Профсоюза",
                                            correctAnswer = "Собственника;Работника;Профсоюза"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = " Сфера применения контрактов определяется:",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Сторонами трудового договора;Законодательством РФ;Конституцией РФ;Профсоюзами",
                                            correctAnswer = "Законодательством РФ"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Прогулом считается:",
                                            imageUrl = "https://viplawyer.ru/wp-content/uploads/2016/05/%D0%A3%D0%B2%D0%BE%D0%BB%D1%8C%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5-%D0%B7%D0%B0-%D0%BF%D1%80%D0%BE%D0%B3%D1%83%D0%BB.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Отсутствие на работе свыше 3х часов;" +
                                                    "Отсутствие на рабочем месте в течении дня;" +
                                                    "Неявка на работу более 2х дней",
                                            correctAnswer = "Отсутствие на работе свыше 3х часов"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Какой документ является единственным свидетельством о трудовой деятельности работника.",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Трудовой договор;Трудовая книжка;Приказ о приеме на работу",
                                            correctAnswer = "Трудовая книжка"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Минимальная продолжительность обеденного перерыва согласно Трудовому кодексу РФ?",
                                            imageUrl = "https://img.freepik.com/free-photo/lunch-break-from-work-at-home_23-2148515915.jpg?size=626&ext=jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "30 минут;" +
                                                    "60 минут;" +
                                                    "20 минут",
                                            correctAnswer = "30 минут"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId2,
                                            title = "Имеет ли право дистанционный работник предприятия на оплачиваемый отпуск?",
                                            imageUrl = "https://dropi.ru/img/uploads/test/2021-04-02/9c7e39c4d5d9332e713a2977eb5b68d2.jpeg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Только на сокращенный отпуск;" +
                                                    "Да, как и другие работники, на 28-дневный отпуск, согласно статье 19 ТК;" +
                                                    "Нет",
                                            correctAnswer = "Да, как и другие работники, на 28-дневный отпуск, согласно статье 19 ТК"
                                        ),
                                    )
                                )
                            }

                            GlobalScope.launch {
                                instance!!.withTransaction {
                                    addQuizzes()
                                }
                            }
                        }
                    }).build()
            }
            return instance!!
        }
    }
}