package com.example.android.ui.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R

class CalculatorActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var firstOperand: Double? = null
    private var operation: Char? = null
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        tvResult = findViewById(R.id.tvResult)
        setupDigitButtons()
        setupOperationButtons()
        setupSpecialButtons()
    }

    private fun setupDigitButtons() {
        val digitIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                onDigitClick((it as Button).text.toString())
            }
        }
    }

    private fun setupOperationButtons() {
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperationClick('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperationClick('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperationClick('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperationClick('/') }
    }

    private fun setupSpecialButtons() {
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
    }

    private fun onDigitClick(digit: String) {
        if (isNewInput) {
            tvResult.text = digit
            isNewInput = false
        } else {
            tvResult.text = "${tvResult.text}$digit"
        }
    }

    private fun onOperationClick(op: Char) {
        firstOperand = tvResult.text.toString().toDoubleOrNull()
        operation = op
        isNewInput = true
    }

    private fun onEqualClick() {
        val secondOperand = tvResult.text.toString().toDoubleOrNull() ?: return
        val first = firstOperand ?: return
        val op = operation ?: return

        val result = when (op) {
            '+' -> first + secondOperand
            '-' -> first - secondOperand
            '*' -> first * secondOperand
            '/' -> if (secondOperand != 0.0) first / secondOperand else Double.NaN
            else -> return
        }

        tvResult.text = if (result.isNaN() || result.isInfinite()) {
            "Error"
        } else {
            result.toString()
        }

        firstOperand = null
        operation = null
        isNewInput = true
    }

    private fun onClearClick() {
        tvResult.text = "0"
        firstOperand = null
        operation = null
        isNewInput = true
    }
}