package com.example.quizy

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizy.databinding.ActivityQuizQuestionBinding
import com.example.quizy.dataclass.Questions

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionBinding

    private var mCurrentPosition: Int=1
    private var mQuestionsList: ArrayList<Questions>?=null

    private var mSelectedOptionPosition: Int=0

    private var userName:String?= null
    private var correctAnswer:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuizQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList=Constants.getQuestion()
        setQuestion()

        binding.tvOptionFour.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionOne.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        deafultOptionView()
        val question: Questions=mQuestionsList!![mCurrentPosition - 1]

        binding.progress.progress=mCurrentPosition
        binding.tvProgress.text="$mCurrentPosition /${binding.progress.max}"

        binding.tvQuestion.text=question.question
        binding.tvOptionOne.text=question.option1
        binding.tvOptionTwo.text=question.option2
        binding.tvOptionThree.text=question.option3
        binding.tvOptionFour.text=question.option4

        binding.ivImage.setImageResource(question.image)

        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit.text="Finish"
        } else {
            binding.btnSubmit.text="Submit"
        }
    }

    fun deafultOptionView() {
        val option=ArrayList<TextView>()
        binding.tvOptionOne.let {
            option.add(0, it)
        }
        binding.tvOptionTwo.let {
            option.add(1, it)
        }
        binding.tvOptionThree.let {
            option.add(2, it)
        }
        binding.tvOptionFour.let {
            option.add(3, it)
        }

        for (i in option) {
            i.setTextColor(Color.parseColor("#7A8089"))
            i.typeface=Typeface.DEFAULT
            i.background=ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

    }

    fun selectedOptionView(tv: TextView, sONum: Int) {
        deafultOptionView()
        mSelectedOptionPosition=sONum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tvOptionOne -> {
                binding.tvOptionOne.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tvOptionTwo -> {
                binding.tvOptionTwo.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tvOptionThree -> {
                binding.tvOptionThree.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tvOptionFour -> {
                binding.tvOptionFour.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }else -> {
                        var i=Intent(this, ResultActivity::class.java)
                        i.putExtra(Constants.USER_NAME,userName)
                        i.putExtra(Constants.CORRECT_ANSWERS,correctAnswer)
                        i.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                        startActivity(i)
                        finish()
                        }
                    }
                } else {
                    val question=mQuestionsList?.get(mCurrentPosition - 1)
                    if (question?.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        correctAnswer++
                    }
                        answerView(question!!.answer, R.drawable.correct_option_border_bg)

                        if (mCurrentPosition == mQuestionsList!!.size) {
                            binding.btnSubmit.text="Finish"
                        } else {
                            binding.btnSubmit.text="GO To Next Question"
                        }
                        mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun answerView(answer: Int, drawbleView: Int) {
        when (answer) {
            1 -> {
                binding.tvOptionOne.background=ContextCompat.getDrawable(
                    this, drawbleView
                )
            }
            2 -> {
                binding.tvOptionTwo.background=ContextCompat.getDrawable(
                    this, drawbleView
                )
            }
            3 -> {
                binding.tvOptionThree.background=ContextCompat.getDrawable(
                    this, drawbleView
                )
            }
            4 -> {
                binding.tvOptionFour.background=ContextCompat.getDrawable(
                    this, drawbleView
                )
            }
        }
    }
}