package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var tvInput : TextView? = null
    private var hasPressedDot : Boolean = false
    private var lastNumeric : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvInput = findViewById(R.id.tvInput)

        val button0 : Button = findViewById(R.id.button0)
        button0.setOnClickListener(this)
        val button1 : Button = findViewById(R.id.button1)
        button1.setOnClickListener(this)
        val button2 : Button = findViewById(R.id.button2)
        button2.setOnClickListener(this)
        val button3 : Button = findViewById(R.id.button3)
        button3.setOnClickListener(this)
        val button4 : Button = findViewById(R.id.button4)
        button4.setOnClickListener(this)
        val button5 : Button = findViewById(R.id.button5)
        button5.setOnClickListener(this)
        val button6 : Button = findViewById(R.id.button6)
        button6.setOnClickListener(this)
        val button7 : Button = findViewById(R.id.button7)
        button7.setOnClickListener(this)
        val button8 : Button = findViewById(R.id.button8)
        button8.setOnClickListener(this)
        val button9 : Button = findViewById(R.id.button9)
        button9.setOnClickListener(this)
        val buttonDot : Button = findViewById(R.id.buttonDot)
        buttonDot.setOnClickListener(this)
        val buttonAdd : Button = findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener(this)
        val buttonMinus : Button = findViewById(R.id.buttonMinus)
        buttonMinus.setOnClickListener(this)
        val buttonMultiply : Button = findViewById(R.id.buttonMultiply)
        buttonMultiply.setOnClickListener(this)
        val buttonDivide : Button = findViewById(R.id.buttonDivide)
        buttonDivide.setOnClickListener(this)
        val buttonEqual : Button = findViewById(R.id.buttonEqual)
        buttonEqual.setOnClickListener(this)
        val buttonClr : Button = findViewById(R.id.buttonClr)
        buttonClr.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            //Clear number
            R.id.buttonClr -> {
                //Clear the screen if Clr button's clicked
                tvInput?.text = ""
                hasPressedDot = false
                lastNumeric = false
            }

            //Add dot
            R.id.buttonDot -> {
                //Check if there's already a dot
                val flag : Boolean = hasPressedDot
                hasPressedDot = checkHasPressedDot(view, hasPressedDot)
                //Check if hasPressedDot has changed
                if (flag != hasPressedDot) {
                    //A dot has been append on screen, lastNumeric set to False
                    lastNumeric = false
                }
            }

            //If an operator (+, -, *, /) is pressed, check whether to display it or not
            R.id.buttonAdd, R.id.buttonMinus, R.id.buttonDivide, R.id.buttonMultiply -> {
                tvInput?.text?.let {
                    //If there hasn't been any number, check if - or + operator is being pressed
                    if (tvInput?.text.toString() == "") {
                        val prefix = (view as Button).text
                        if (prefix == "+" || prefix == "-") {
                            tvInput?.append(prefix)
                            lastNumeric = false
                        }
                    }
                    println("!!!${tvInput?.text}???")
                    //If the last button pressed was a number and no operator has been pressed, display it
                    if (lastNumeric && !isOperatorAdded((it.toString()))) {
                        tvInput?.append((view as Button).text)
                        lastNumeric = false
                        hasPressedDot = false
                    }
                }
            }

            //
            R.id.buttonEqual -> {
                if (lastNumeric) {

                    var tvValue = tvInput?.text.toString()
                    var prefix = ""

                    if (tvValue.startsWith("+") || tvValue.startsWith("-")) {
                        prefix = (tvValue)[0].toString()
                        tvValue = tvValue.substring(1)
                    }

                    if (tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var firstNum = splitValue[0]
                        val secondNum = splitValue[1]
                        if (prefix != "") {
                            firstNum = prefix + firstNum
                        }
                        tvInput?.text = removeZeroAfterDot((firstNum.toDouble() + secondNum.toDouble()).toString())
                    }
                    else if (tvValue.contains("-")) {
                        val splitValue = tvValue.split("-")
                        var firstNum = splitValue[0]
                        val secondNum = splitValue[1]
                        if (prefix != "") {
                            firstNum = prefix + firstNum
                        }
                        tvInput?.text = removeZeroAfterDot((firstNum.toDouble() - secondNum.toDouble()).toString())
                    }
                    else if (tvValue.contains("*")) {
                        val splitValue = tvValue.split("*")
                        var firstNum = splitValue[0]
                        val secondNum = splitValue[1]
                        if (prefix != "") {
                            firstNum = prefix + firstNum
                        }
                        tvInput?.text = removeZeroAfterDot((firstNum.toDouble() * secondNum.toDouble()).toString())
                    }
                    else if (tvValue.contains("/")){
                        val splitValue = tvValue.split("/")
                        var firstNum = splitValue[0]
                        val secondNum = splitValue[1]
                        if (prefix != "") {
                            firstNum = prefix + firstNum
                        }
                        tvInput?.text = removeZeroAfterDot((firstNum.toDouble() / secondNum.toDouble()).toString())
                    }
                }
            }

            //Display the button clicked on screen
            else -> {
                tvInput?.append((view as Button).text)
                lastNumeric = true
            }
        }
    }

    private fun checkHasPressedDot(view: View?, hasPressedDot : Boolean) : Boolean {
        if (!hasPressedDot) {
            tvInput?.append((view as Button).text)
        }
        return true
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if (value.startsWith("+") || value.startsWith("-")) {
            false
        } else {
            value.contains("/" )
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.substring(result.length - 2, result.length) == ".0")  {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}