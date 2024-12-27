package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var tvHistory: TextView
    private val historyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInput = findViewById(R.id.etInput)
        tvHistory = findViewById(R.id.tvHistory)

        val buttons = mapOf(
            R.id.btn0 to "0",
            R.id.btn1 to "1",
            R.id.btn2 to "2",
            R.id.btn3 to "3",
            R.id.btn4 to "4",
            R.id.btn5 to "5",
            R.id.btn6 to "6",
            R.id.btn7 to "7",
            R.id.btn8 to "8",
            R.id.btn9 to "9",
            R.id.btnSum to "+",
            R.id.btnSub to "-",
            R.id.btnMul to "*",
            R.id.btnPower to "^",
            R.id.btnClear to "C"
        )

        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                onButtonClicked(value)
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            calculateResult()
        }
    }

    private fun onButtonClicked(value: String) {
        if (value == "C") {
            etInput.text.clear()
        } else {
            etInput.append(value)
        }
    }

    private fun calculateResult() {
        val input = etInput.text.toString()

        try {
            val result = evaluateExpression(input)
            if (historyList.size == 30) {
                historyList.removeAt(0)
            }
            historyList.add("$input = $result")
            updateHistory()
            etInput.setText(result.toString())
        } catch (e: Exception) {
            etInput.setText("Error")
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val operators = listOf("+", "-", "*", "/", "^")
        for (operator in operators) {
            if (expression.contains(operator)) {
                val parts = expression.split(operator)
                val left = parts[0].toDouble()
                val right = parts[1].toDouble()
                return when (operator) {
                    "+" -> left + right
                    "-" -> left - right
                    "*" -> left * right
                    "/" -> left / right
                    "^" -> left.pow(right)
                    else -> 0.0
                }
            }
        }
        return expression.toDouble()
    }

    private fun updateHistory() {
        tvHistory.text = historyList.joinToString("\n")
    }
}
