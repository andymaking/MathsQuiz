package io.king.mathsquiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var random = Random()
    var a = 0
    var b = 0
    var indexOfCorrectAnswer = 0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lastUi.visibility = View.INVISIBLE
        quizUi.visibility = View.INVISIBLE

    }

    @SuppressLint("SetTextI18n")
    fun nextQuestion() {
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionTextView!!.text = "$a+$b"
        indexOfCorrectAnswer = random.nextInt(4)
        answers.clear()
        for (i in 0..3) {
            if (indexOfCorrectAnswer == i) {
                answers.add(a + b)
            } else {
                var wrongAnswer = random.nextInt(20)
                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(20)
                }
                answers.add(wrongAnswer)
            }
        }
        button0!!.text = answers[0].toString()
        button1!!.text = answers[1].toString()
        button2!!.text = answers[2].toString()
        button3!!.text = answers[3].toString()
    }

    fun optionSelect(view: View) {
        totalQuestions++
        if (indexOfCorrectAnswer.toString() == view.tag.toString()) {
            points++
            AlertTextView!!.text = "Correct"
        } else {
            AlertTextView!!.text = "Wrong"
        }
        ScoreTextView!!.text = "$points/$totalQuestions"
        nextQuestion()
    }

    private fun count(){
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TimeTextView!!.text = "${(millisUntilFinished / 1000)}s"
            }

            override fun onFinish() {
                TimeTextView!!.text = "Time Up!"
                FinalscoreTextView!!.text =
                    "$points/$totalQuestions"
                quizUi!!.visibility = View.INVISIBLE
                lastUi!!.visibility = View.VISIBLE
            }
        }.start()
    }

    fun playAgain(view: View?) {
        points = 0
        totalQuestions = 0
        ScoreTextView!!.text = "$points/$totalQuestions"
        count()
        lastUi!!.visibility = View.INVISIBLE
        quizUi!!.visibility = View.VISIBLE
    }

    fun start(view: View?) {
        btnStart!!.visibility = View.INVISIBLE
        quizUi!!.visibility = View.VISIBLE
        nextQuestion()
        count()
    }
}
