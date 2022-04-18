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
                                            "Выберите правильные варианты",
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "неправильно;" +
                                                    "правильно;" +
                                                    "не;" +
                                                    "тоже правильно",
                                            correctAnswer = "правильно;" +
                                                    "тоже правильно"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId1,
                                            title = "При взимании штрафа на месте совершения правонарушения нарушителю выдается",
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
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "14 лет;" +
                                                    "15 лет;" +
                                                    "16 лет;" +
                                                    "18 лет",
                                            correctAnswer = "16 лет"
                                        )
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