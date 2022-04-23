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
                                val quizId3 = UUIDS.createShort()
                                val quizId4 = UUIDS.createShort()
                                val quizId5 = UUIDS.createShort()
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
                                                id = quizId3,
                                                name = "Права ребенка",
                                                imageUrl = "https://www.ya-roditel.ru/upload/resizeman/1__upload_iblock_709_709fa4f487439ca4bb56b822f909779c.jpg?cache=Y",
                                                lectureUrl = ""
                                            ),
                                            QuizEntity(
                                                id = quizId4,
                                                name = "Потребитель и его права",
                                                imageUrl = "https://avatars.mds.yandex.net/get-zen_doc/1866022/pub_5d189b92913f0600acb02862_5d189d9457394600adca7815/scale_1200",
                                                lectureUrl = "http://promtorg.volgograd.ru/current-activity/trade/protection/info/47482/"
                                            ),
                                            QuizEntity(
                                                id = quizId5,
                                                name = "Право на информацию",
                                                imageUrl = "https://4brain.ru/blog/wp-content/uploads/2019/08/critical-information-analysis.jpg",
                                                lectureUrl = "https://intuit.ru/studies/professional_skill_improvements/20696/courses/843/lecture/31511"
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
                                    ) + listOf(
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "С какого возраста несовершеннолетний может обратиться в суд за защитой своих прав?",
                                            imageUrl = "data:image/jpeg;base64,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",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "16 лет;" +
                                                    "12 лет;" +
                                                    "14 лет;" +
                                                    "10 лет",
                                            correctAnswer = "14 лет"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Согласно международному законодательству ребенок обладает правами:",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "с момента рождения;" +
                                                    "по достижению 14 лет;" +
                                                    "по достижению 16 лет;" +
                                                    "по достижению 18 лет",
                                            correctAnswer = "с момента рождения"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Семья, материнство, отцовство и детство в РФ находится под защитой:",
                                            imageUrl = "https://дом-родословия.рф/media/k2/items/cache/39eee751af30032eeece2f48de2de4ba_XL.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "полиции;" +
                                                    "государства;" +
                                                    "органов загса;" +
                                                    "суда",
                                            correctAnswer = "государства"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "По определению ООН, подросток – это лицо в возрасте от 10 лет до...",
                                            imageUrl = "https://53news.ru/wp-content/uploads/2018/04/starorusskij-podrostok-s-gollivudskoj-ulybkoj-postradal-iz-za-khalatnosti-vzroslykh-2.jpg",
                                            choiceType = QuestionEntity.ChoiceType.ENTER_CHOICE,
                                            answers = "",
                                            correctAnswer = "19"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Документ, защищающий права ребенка и имеющий обязательную силу для подписавших его стран, - это …",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Декларация;" +
                                                    "Программа;" +
                                                    "Конвенция",
                                            correctAnswer = "Конвенция"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Согласно «Всеобщей Декларации прав человека» к элементарным правам личности не относится право на …",
                                            imageUrl = "https://ktonanovenkogo.ru/image/prava-cheloveka-chto-takoe.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "свободу;" +
                                                    "жизнь;" +
                                                    "личную неприкосновенность;" +
                                                    "труд",
                                            correctAnswer = "труд"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Ребенок имеет право на свободное выражение собственного мнения",
                                            imageUrl = "https://deti48.ru/images/shutterstock_79442740.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "если это не наносит вреда другим;" +
                                                    "безоговорочно;" +
                                                    "по разрешению старших;" +
                                                    "не имеет",
                                            correctAnswer = "если это не наносит вреда другим"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId3,
                                            title = "Какой международный документ был принят в 1959г. Генеральной Ассамблеей ООН?",
                                            imageUrl = "https://tyumedia.ru/i/n/206/253206/253206_b7baa6e3a712.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Конвенция о правах ребенка;" +
                                                    "Конституция о правах ребенка;" +
                                                    "Международный пакт о правах ребёнка;" +
                                                    "Декларация прав ребёнка",
                                            correctAnswer = "Декларация прав ребёнка"
                                        )
                                    ) + listOf(
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Какой характер несут отношения с участием потребителей по законодательству о защите прав потребителей?",
                                            imageUrl = "https://elm52.ru/common/images/user1/osobennosti-rassmotreniya-del-o-zashite-prav-potrebitelya.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "публичный характер;" +
                                                    "частно-публичный характер;" +
                                                    "частный характер",
                                            correctAnswer = "публичный характер"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Вправе ли банк в одностороннем порядке менять условия кредитования (например, увеличивать процентную ставку)?",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "Да;" +
                                                    "Да, но банк должен уведомить меня об этом;" +
                                                    "Нет, без моего согласия не вправе",
                                            correctAnswer = "Нет, без моего согласия не вправе"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "При расторжении договора купли-продажи товара с недостатками потребитель вправе",
                                            imageUrl = "https://pravo-sfera.ru/image/rastorj-dogovora-2jpg.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "взыскать моральный ущерб с изготовителя;" +
                                                    "оставить товар с недостатками у себя;" +
                                                    "требовать возмещения убытков у продавца;" +
                                                    "требовать уменьшения стоимости товара с недостатками",
                                            correctAnswer = "требовать возмещения убытков у продавца"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Договор розничной купли-продажи с использованием автоматов считается заключенным с момента",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "совершения потребителем действий, необходимых для получения товара;" +
                                                    "приобретения потребителем знака оплаты товара;" +
                                                    "опускания знака оплаты товара или денежной суммы в прорезь автомата;" +
                                                    "получения товара потребителем",
                                            correctAnswer = "приобретения потребителем знака оплаты товара"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Требование потребителя о соразмерном уменьшении покупной цены должно быть удовлетворено в течение\n",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "3 месяцев со дня получения продавцом требования;" +
                                                    "10 дней со дня его предъявления;" +
                                                    "14 дней со дня его предъявления",
                                            correctAnswer = "10 дней со дня его предъявления"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Из перечисленных товаров, срок годности устанавливается на",
                                            imageUrl = "https://pravobez.ru/media/uploads//posmotri-srok-godnosti-vozmi-tolko-esli-svezhee_45fd5d088bee4d8.jpg",
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "медикаменты;" +
                                                    "косметика;" +
                                                    "электроника;" +
                                                    "мототехника;" +
                                                    "продукты питания",
                                            correctAnswer = "медикаменты;косметика;продукты питания"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Продавец может установить гарантийный срок на товар, если",
                                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTRvPBA6Dx7_5qLso40Nen5NR_OC6BbJ5JGQ&usqp=CAU",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "продавец имеет на это согласие изготовителя;" +
                                                    "он не был установлен изготовителем и равняется 6 месяцам;" +
                                                    "данная обязанность установлена соглашением сторон",
                                            correctAnswer = "он не был установлен изготовителем и равняется 6 месяцам"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Риск случайной гибели товара переходит на покупателя с момента",
                                            imageUrl = "https://365calend.ru/wp-content/uploads/2020/08/Razbitaya-vaza-tolkovanie-primety.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "оплаты стоимости товара покупателем;" +
                                                    "когда оплаченный товар оставлен у продавца для маркировки;" +
                                                    "заключения договора купли-продажи;" +
                                                    "когда продавец исполнил свою обязанность по передачи товара покупателю",
                                            correctAnswer = "заключения договора купли-продажи"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Кто обеспечивает безопасность товара в течение срока службы?",
                                            imageUrl = "https://www.garant.ru/files/9/3/1451039/prava_potrebiteley_pravovie_azi_v_voprosah_i_otvetah_300.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "изготовитель;" +
                                                    "продавец;" +
                                                    "покупатель;" +
                                                    "иные ответственные лица",
                                            correctAnswer = "изготовитель"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId4,
                                            title = "Кто определяет время наступления сезона для сезонных товаров:",
                                            imageUrl = "https://dolauto.ru/upload/iblock/342/342bf094f83a6209a6c2d993b7eaecf3.png",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "правительство Российской Федерации;" +
                                                    "продавец этих товаров самостоятельно;" +
                                                    "субъекты Российской Федерации",
                                            correctAnswer = "субъекты Российской Федерации"
                                        ),
                                    ) + listOf(
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Режим защиты информации не устанавливается в отношении сведений, относящихся к...",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "государственной тайне;" +
                                                    "конфиденциальной информации;" +
                                                    "деятельности государственных деятелей;" +
                                                    "персональным данным",
                                            correctAnswer = "деятельности государственных деятелей"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "В регистрации средства массовой информации не может быть отказано...",
                                            imageUrl = "https://tvorcheskie-proekty.ru/files/images/smi.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "по мотивам нецелесообразности;" +
                                                    "когда заявление подано не соответствующим лицом;" +
                                                    "даже если сведения в заявлении не соответствуют действительности",
                                            correctAnswer = "по мотивам нецелесообразности"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Засекречиванию подлежат сведения о...",
                                            imageUrl = "https://cdn3.static1-sima-land.com/items/306945/0/1600.jpg?v=0",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "состоянии преступности;" +
                                                    "силах и средствах гражданской обороны;" +
                                                    "фактах нарушения прав и свобод человека и гражданина",
                                            correctAnswer = "силах и средствах гражданской обороны"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Режим документированной информации – это...",
                                            imageUrl = "https://autogear.ru/misc/i/gallery/20380/2026498.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "выделенная информация по определенной цели;" +
                                                    "электронный документ с электронно-цифровой подписью;" +
                                                    "выделенная информация в любой знаковой форме",
                                            correctAnswer = "электронный документ с электронно-цифровой подписью"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Режим общественного достояния устанавливается для",
                                            imageUrl = "https://i-fakt.ru/wp-content/uploads/2019/10/prazdik-den-obschesctvennogo-dostoyaniya.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "для государственных органов и муниципальных образований;" +
                                                    "любой общедоступной информации;" +
                                                    "сведений, которые являются уникальными, незаменимыми по своей природе;" +
                                                    "любой общественной организации",
                                            correctAnswer = "сведений, которые являются уникальными, незаменимыми по своей природе"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "С точки зрения информационного права информация – это...",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "форма выражения объективных знаний;" +
                                                    "сведения о законодательстве, правовых явлениях, правоприменительной деятельности;" +
                                                    "сведения независимо от формы их представления;" +
                                                    "данные о развитии конкретной правовой науки и ее практическом применении",
                                            correctAnswer = "сведения независимо от формы их представления"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "К служебной тайне не относится...",
                                            imageUrl = "https://autogear.ru/misc/i/gallery/26556/1305518.jpg",
                                            choiceType = QuestionEntity.ChoiceType.MULTI_CHOICE,
                                            answers = "тайна деятельности соответствующего органа;" +
                                                    "профессиональная тайна;" +
                                                    "вред, причиненный здоровью работника в связи с производственной травмой",
                                            correctAnswer = "вред, причиненный здоровью работника в связи с производственной травмой"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "В правовой режим документированной информации входит...",
                                            imageUrl = null,
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "тайна частной жизни;" +
                                                    "банковская тайна;" +
                                                    "электронная цифровая подпись;" +
                                                    "государственная тайна",
                                            correctAnswer = "электронная цифровая подпись"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Основное средство антивирусной защиты данных",
                                            imageUrl = "https://media.kingston.com/kingston/hero/ktc-hero-solutions-data-security-who-is-responsible-for-cyber-security-lg.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "резервное копирование ценных данных;" +
                                                    "подготовка квалифицированных кадров в сфере информационной безопасности;" +
                                                    "регулярное сканирование жестких дисков",
                                            correctAnswer = "резервное копирование ценных данных"
                                        ),
                                        QuestionEntity(
                                            id = UUIDS.createShort(),
                                            quizId = quizId5,
                                            title = "Дети до 6 лет не вправе",
                                            imageUrl = "https://img.freepik.com/free-photo/school-boy-with-laptop-at-table-at-home-child-using-digital-technology-and-internet-communication_494619-402.jpg",
                                            choiceType = QuestionEntity.ChoiceType.SINGLE_CHOICE,
                                            answers = "с разрешения законных представителей выходить в Интернет;" +
                                                    "с согласия законных представителей пользоваться телефонными услугами;" +
                                                    "с согласия законных представителей совершать сделки с компьютерной техникой",
                                            correctAnswer = "с согласия законных представителей совершать сделки с компьютерной техникой"
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