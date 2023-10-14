package com.example.dicevisual.Dice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicevisual.R


class BasicDiceCalculator : AppCompatActivity() {

    //Used to stop bugs when adding view elements when loading Android Studio for the first time
    //@SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {

        ///Create Application
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_calculator) //The actual XML file for the basic calculator

        //---------------------------------------------------------------
        ///Setting Defaults

        //Text that shows what you typed to get the result
        val inputUsed = findViewById<TextView>(R.id.txtid_prevInput)
        inputUsed.isVisible = false

        //Result Text
        val idText = findViewById<TextView>(R.id.id_Test)
        idText.text = "0"

        //History
        val recycle = findViewById<RecyclerView>(R.id.rvid_history)
        var history = ArrayList<History>()
        val adapter = BasicHistoryAdapter(history)

        recycle.adapter = adapter
        recycle.layoutManager = LinearLayoutManager(this)

        /* //Later Addition to make History better
        var lastResult = ""
        var lastInput = ""
        */

        //XXX
        var textEdit = ""
        var operatorLast = false




        //---------------------------------------------
        //Create Buttons

        //Numbered Buttons
        val button_0 = findViewById<Button>(R.id.btnid_0)
        val button_1 = findViewById<Button>(R.id.btnid_1)
        val button_2 = findViewById<Button>(R.id.btnid_2)
        val button_3 = findViewById<Button>(R.id.btnid_3)
        val button_4 = findViewById<Button>(R.id.btnid_4)
        val button_5 = findViewById<Button>(R.id.btnid_5)
        val button_6 = findViewById<Button>(R.id.btnid_6)
        val button_7 = findViewById<Button>(R.id.btnid_7)
        val button_8 = findViewById<Button>(R.id.btnid_8)
        val button_9 = findViewById<Button>(R.id.btnid_9)

        //Operation Buttons
        val button_dice = findViewById<Button>(R.id.btnid_dice)
        val button_back = findViewById<Button>(R.id.btnid_back)
        val button_add = findViewById<Button>(R.id.btnid_add)
        val button_minus = findViewById<Button>(R.id.btnid_minus)
        val button_clear = findViewById<Button>(R.id.btnid_clear)
        val button_enter = findViewById<Button>(R.id.btnid_enter)

        //-----------------------------------------------------------------
        //Numerical Buttons

        //Simplify Button Clicks involving numbers
        fun buttonClick (buttonNum: String){
            //Add Text to calculation and update shown text
            textEdit += buttonNum
            idText.text = textEdit

            //Tell that it is not an operator (For Back and Math Operator purposes)
            operatorLast = false
        }

        //Create On-Click Events (Numbers)
        button_0.setOnClickListener { buttonClick("0") }
        button_1.setOnClickListener { buttonClick("1") }
        button_2.setOnClickListener { buttonClick("2") }
        button_3.setOnClickListener { buttonClick("3") }
        button_4.setOnClickListener { buttonClick("4") }
        button_5.setOnClickListener { buttonClick("5") }
        button_6.setOnClickListener { buttonClick("6") }
        button_7.setOnClickListener { buttonClick("7") }
        button_8.setOnClickListener { buttonClick("8") }
        button_9.setOnClickListener { buttonClick("9") }

        //-----------------------------------------------------------------------------
        /*
        * Back Button - Remove the last number or operator
        * */
        button_back.setOnClickListener {

            //Remove Operator if true
            if(operatorLast){
                //Check if your not at the start of the calculation
                if(textEdit.length > 2) {
                    textEdit = textEdit.dropLast(2)
                }else{
                    textEdit = ""
                    idText.text = "0"
                }
                operatorLast = false
            }

            //If there is text to remove
            if(textEdit.length > 1){

                //Drop last number
                textEdit = textEdit.dropLast(1)

                //Check if previous is operator
                if (textEdit.last() == ' ') {
                    operatorLast = true
                }

                //Update Text
                idText.text = textEdit

            }else{
                //If there is no text to remove
                textEdit = ""
                idText.text = "0"

            }


        }


        //--------------------------------------
        /*
        * Dice: Adds the Dice indicator and formats beforehand if needed.
        * */
        button_dice.setOnClickListener {
            //Check if there is NOT a number before the dice button
            if(textEdit.isEmpty() || operatorLast){
                //Adds one to indicate you want to roll the dice once
                textEdit += "1"
            }
            textEdit += "d"
            idText.text = textEdit
        }

        //-----------------------------------------------------------
        /*
        * Add - Correctly 'add's this button, include proper formatting and remove dice indicator if nothing follows
        * */
        button_add.setOnClickListener {
            //If there is an operator there already, prepare to replace it!
            if(operatorLast){
                textEdit = textEdit.dropLast(3)
            }

            //Check if the sign is at the start
            if(textEdit.isNotEmpty()) {

                //Check if the previous character is the dice one, and removes it
                if (textEdit.last() == 'd') {
                    textEdit = textEdit.dropLast(1)
                    idText.text = textEdit
                }
            }

            //Update Text Shown
            textEdit += " + "
            idText.text = textEdit
            operatorLast = true
        }

        //------------------------------------------------------------------
        /*
        * Minus - Correctly adds the subtract operator, even formatting dice if needed.
        * */
        button_minus.setOnClickListener {
            //Check if the string has anything in it
            if(textEdit.isNotEmpty()) {

                //Check if the last character is 'd'
                if (textEdit.last() == 'd') {

                    //Remove the dice indicator (As a Xd1 is just X)
                    textEdit = textEdit.dropLast(1)
                    idText.text = textEdit
                }
            }

            //Check if there was an operator used before it
            if(operatorLast){
                //Remove it
                textEdit = textEdit.dropLast(3)
            }

            //Add
            textEdit += " - "; idText.text = textEdit
            operatorLast = true
        }

        //-----------------------------------------------------
        /*
        * Clear (Short Press) - Deletes all edited text and resets the calculator to the start. History Stays
        * */
        button_clear.setOnClickListener { textEdit = ""; inputUsed.isVisible = false; operatorLast = false; idText.text = "0"}

        /*
        * Clear (Long Press) - Full Clear; Deletes all history, then resets back to the start
        * */
        button_clear.setOnLongClickListener {
            //
            history.clear()
            adapter.notifyDataSetChanged()
            textEdit = ""; inputUsed.isVisible = false; operatorLast = false; idText.text = "0"
            true
        }

        //----------------------------------------------------
        /*
        * Enter: Start Calculating the value of the string, and adding a history item for next time
        * */
        button_enter.setOnClickListener {

            //Makes it so the te
            if(operatorLast){
                textEdit = textEdit.dropLast(3)
            }
            operatorLast = false

            //Calculate result from dice calculation (See DiceCal.kt)
            idText.text = calculate(textEdit)// ("2d6 / 3 + 1 x 2") //Testing Multiplication / Division. Only one works at a time TODO Fix That

            //Show Input Used
            inputUsed.text = textEdit
            inputUsed.isVisible = true

            //Add to History section
            val historyItem = History(inputUsed.text.toString(),idText.text.toString(), history.size+1)
            history.add(historyItem)
            adapter.notifyItemInserted(history.lastIndex) //Notify that new history item was created, show visually

        }


    }


}