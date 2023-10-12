package com.example.dicevisual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

/*
*  FILE IS NOT USED. GOING TO ADD A MORE VISUAL CALCULATOR HERE
*  WITH ANIMATIONS AND WHATNOT.
*
*  CHECK
*       - BasicDiceCalculator.kt (MAIN): The button side, and preparing for calculations.
*       - DiceCal.kt: Basic Dice rolling and Basic Maths Calculation.
*       - History.kt: History Class meant to make storing items into "history_item_simple.xml easier.
*       - BasicHistoryAdapter.kt: Makes the History function possible, by translating history items into a visual format.
*
* VISUAL
*       - activity_main.xml: Ignore, more visual calculator would go here.
*       - basic_calculator.xml: Both Horizontal and Vertical versions of the calculator.
*       - history_item_simple.xml: Template for BasicHistoryAdapter.kt when creating calculator history.
*
* */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_calculator) //The actual XML file for the basic calculator

    }
}