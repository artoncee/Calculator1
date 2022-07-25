package com.example.calculator1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var res : TextView? = null
    var num : EditText? = null
    var op : TextView? = null
    var operand: Double? = null
    var lastOperation: String? = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        res = findViewById(R.id.result)
        num = findViewById(R.id.number)
        op = findViewById(R.id.operation)
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putString("operation", lastOperation)
        if (operand != null) outState.putDouble("operand", operand!!)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle)
    {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("operation")
        operand = savedInstanceState.getDouble("operand")
        res!!.text = operand.toString()
        op!!.text = lastOperation
    }
    
    fun onNumberClick(view: View)
    {
        val button = view as Button
        num!!.append(button.text)
        if (lastOperation == "Execute" && operand != null)
        {
            operand = null
        }
    }

    fun onOperationClick(view: View)
    {
        val button = view as Button
        val operation = button.text.toString()
        var number = num!!.text.toString()
        if (number.length > 0)
        {
            number = number.replace(',', '.')
            try
            {
                performOperation(java.lang.Double.valueOf(number), operation)
            }
            catch (ex: NumberFormatException)
            {
                num!!.setText("")
            }
        }
        if (operation== "Execute")
        {
            lastOperation= "="
        }
        else
            lastOperation = operation
        op!!.text = lastOperation
    }

    private fun performOperation(number: Double, operation: String)
    {
        if (operand == null)
        {
            operand = number
        }
        else
        {
            if (lastOperation == "Execute")
            {
                lastOperation = operation
            }
//            val operand1 = operand?: return
            when (lastOperation)
            {
                "=" -> operand = number
                "/" -> if (number == 0.0)
                {
                    operand = 0.0
                }
                else
                {
                    operand=operand!!/number
                }
                "*" -> operand=operand!!*number
                "+" -> operand=operand!!+number
                "-" -> operand=operand!!-number
            }
        }
        res!!.text = operand.toString().replace('.', ',')
        num!!.setText("")
    }
}
