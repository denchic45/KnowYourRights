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
                                                id = UUIDS.createShort(),
                                                name = "Трудоустройство",
                                                imageUrl = "https://estart.com.ua/images/services/photo9.jpg",
                                                lectureUrl = ""
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
                                            imageUrl = "https://www.google.com/url?sa=i&url=http%3A%2F%2Fzorkanews.by%2F2020%2F05%2F22%2F%25D1%2587%25D1%2582%25D0%25BE-%25D0%25B3%25D1%2580%25D0%25BE%25D0%25B7%25D0%25B8%25D1%2582-%25D0%25B7%25D0%25B0-%25D0%25BD%25D0%25B5%25D1%2583%25D0%25BF%25D0%25BB%25D0%25B0%25D1%2582%25D1%2583-%25D1%2588%25D1%2582%25D1%2580%25D0%25B0%25D1%2584%25D0%25B0-%25D0%25B3%25D0%25B0%25D0%25B8%2F&psig=AOvVaw0nzhd86hFnZNfh3s5n_zvn&ust=1650547758759000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCNjpxNnfovcCFQAAAAAdAAAAABAD",
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
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "3 суток;15 дней;суток;10 дней",
                                            correctAnswer = "15 дней"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Основанием для административной ответственности является:",
                                            imageUrl = null,
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
                                            imageUrl = null,
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
                                            imageUrl = null,
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
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "Полномочиями надзорных органов;" +
                                                    "Субъектами надзора",
                                            correctAnswer = "Полномочиями надзорных органов;Субъектами надзора"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "Что из перечисленного не является административным наказанием",
                                            imageUrl = null,
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
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = " 1 суток;" +
                                                    "2 суток;" +
                                                    "3 часов;" +
                                                    "6 часов",
                                            correctAnswer = "3 часа"
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