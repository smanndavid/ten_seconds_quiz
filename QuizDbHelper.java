package com.david.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.david.quiz.QuizContract.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Who invented Compact Disc?", "A. James Daniel ", "B. James T Russel", "C. William Thomson", 2);
        addQuestion(q1);
        Question q2 = new Question("What is SQL?", "A. Structured Query Language", "B. System Query Language", "C. Standard Query Language", 1);
        addQuestion(q2);
        Question q3 = new Question("I pad is manufactured by ", "A. Samsung", "B. Huawei", "C. Apple", 3);
        addQuestion(q3);
        Question q4 = new Question("\"Connecting people\" is the tagline of ....", "A. Samsung", "B. Nokia", "C. LG", 2);
        addQuestion(q4);
        Question q5 = new Question("In which year Microsoft Office was launched?", "A. 1989", "B. 1998", "C. 1988", 1);
        addQuestion(q5);
        Question q6 = new Question("When was the first smart phone launched?", "A. 1992", "B. 1993", "C. 1994", 1);
        addQuestion(q6);
        Question q7 = new Question("Number of bit used by the IPv6 address", "A. 32 bit", "B. 64 bit", "C. 128 bit", 3);
        addQuestion(q7);
        Question q8 = new Question("Number of layers in the OSI Model ", "A. 9", "B. 3", "C. 7", 3);
        addQuestion(q8);
        Question q9 = new Question("Which is an input device?", "A. Monitor", "B. Mouse", "C. Printer", 2);
        addQuestion(q9);
        Question q10 = new Question("Mark Zuckerberg is the owner of", "A. Facebook", "B. Google", "C. Linux", 1);
        addQuestion(q10);

    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}