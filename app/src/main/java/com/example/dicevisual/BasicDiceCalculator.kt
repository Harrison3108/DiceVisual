package com.example.dicevisual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
        var inputUsed = findViewById<TextView>(R.id.txtid_prevInput)
        inputUsed.isVisible = false

        //Result Text
        var id_text = findViewById<TextView>(R.id.id_Test)
        id_text.text = "0"

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
        var text_edit = ""
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
            text_edit += buttonNum
            id_text.text = text_edit

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

            //If there is text to remove
            if(text_edit.length > 0){

                //Remove Operator if true
                if(operatorLast == true){
                    text_edit = text_edit.dropLast(2) //TODO This dropLast Works, why not the others
                    operatorLast = false
                }

                //Drop last number
                text_edit = text_edit.dropLast(1)

                //Check if previous is operator
                if (text_edit.last() == ' ') {
                    operatorLast = true
                }

                //Update Text
                id_text.text = text_edit

            }else{
                //If there is no text to remove
                text_edit = ""
                id_text.text = "0"

            }


        }


        //--------------------------------------
        /*
        * Dice: Adds the Dice indicator and formats beforehand if needed.
        * */
        button_dice.setOnClickListener {
            //Check if there is NOT a number before the dice button
            if(text_edit.length == 0 || operatorLast == true){
                //Adds one to indicate you want to roll the dice once
                text_edit += "1"
            }
            text_edit += "d"
            id_text.text = text_edit
        }

        //-----------------------------------------------------------
        /*
        * Add - Correctly 'add's this button, include proper formatting and remove dice indicator if nothing follows
        * */
        button_add.setOnClickListener {
            //If there is an operator there already, prepare to replace it!
            if(operatorLast == true){
                text_edit = text_edit.dropLast(3)
            }

            //Check if the sign is at the start
            if(text_edit.length > 0) {

                //Check if the previous character is the dice one, and removes it
                if (text_edit.last() == 'd') {
                    text_edit = text_edit.dropLast(1)
                    id_text.text = text_edit
                }
            }

            //Update Text Shown
            text_edit += " + "
            id_text.text = text_edit
            operatorLast = true
        }

        //------------------------------------------------------------------
        /*
        * Minus - Correctly adds the subtract operator, even formatting dice if needed.
        * */
        button_minus.setOnClickListener {
            //Check if the string has anything in it
            if(text_edit.length > 0) {

                //Check if the last character is 'd'
                if (text_edit.last() == 'd') {

                    //Remove the dice indicator (As a Xd1 is just X)
                    text_edit = text_edit.dropLast(1)
                    id_text.text = text_edit
                }
            }

            //Check if there was an operator used before it
            if(operatorLast == true){
                //Remove it
                text_edit = text_edit.dropLast(3)
            }

            //Add
            text_edit += " - "; id_text.text = text_edit
            operatorLast = true
        }

        //-----------------------------------------------------
        /*
        * Clear (Short Press) - Deletes all edited text and resets the calculator to the start. History Stays
        * */
        button_clear.setOnClickListener { text_edit = ""; inputUsed.isVisible = false; operatorLast = false; id_text.text = "0"}

        /*
        * Clear (Long Press) - Full Clear; Deletes all history, then resets back to the start
        * */
        button_clear.setOnLongClickListener {
            //
            history.clear()
            adapter.notifyDataSetChanged()
            text_edit = ""; inputUsed.isVisible = false; operatorLast = false; id_text.text = "0"
            true
        }

        //----------------------------------------------------
        /*
        * Enter: Start Calculating the value of the string, and adding a history item for next time
        * */
        button_enter.setOnClickListener {

            //Makes it so the te
            if(operatorLast == true){
                text_edit = text_edit.dropLast(3)
            }
            operatorLast = false

            //Calculate result from dice calculation (See DiceCal.kt)
            id_text.text = calculate(text_edit)

            //Create History

            inputUsed.text = text_edit
            inputUsed.isVisible = true



            //Add to History section
            val historyItem = History(inputUsed.text.toString(),id_text.text.toString(), history.size+1)
            history.add(historyItem)
            adapter.notifyItemInserted(history.lastIndex) //Notify that new history item was created, show visually

        }


    }


}