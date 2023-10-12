package com.example.dicevisual

import kotlin.random.Random

/*
* DiceCal.kt - Contains all of the maths calculations after pressing the "=" Button
* */


/*
* calculate() - Formats and Calculates the end string shown when the enter button is pressed
* //TODO Split into two or more parts (First Half, Second Half [Add Dice Together, Multiply, Add/Subtract])
* */
fun calculate(input:String):String{

    //First Half (Write Equation Out)
    var workingOut = ""
    var inputList = input.split(" ")

    //Second Half (Actually Calculate using that Equation)
    var result = 0
    var addNext = true

    //---------------------------------------------------------
    //First Half - Build String Equation properly formatted for rest of XXX

    //Roll Dice and Format correctly
    for(i in inputList){

        //All Checks
        if(i.toIntOrNull() != null){
            //If a normal number, add straight to output
            workingOut += i

        } else if(i.contains("d")){
            //If a Dice

            //TODO: Get "Xd" dice rolls working (Probably just the number "X" by dropping)
            var split = i.splitToSequence("d")

            //Add each dice roll in proper formatting
            workingOut += "[" + rollDice(split.first().toInt(), split.last().toInt()) + "]"

        } else if(i.contains("+")){
            //If a "+"

            workingOut += " " + i + " "
        }else if(i.contains("-")){
            //If a "-"
            workingOut += " " + i + " "
        }else{
            //TODO: Add Multiplication support (Use AFTER Dice Roll, but BEFORE Addition or Subtraction. [Parser Function? Eg: Input -> parseDice() -> parseMultiply() -> parseBasic() -> Output?]

            //Something went wrong. Inform the user without crashing
            //val mainText = findViewById<TextView>(R.id.id_Test)
            //mainText.text = "Something went wrong while calculating. Please try again later"

        }

    }

    //--------------------------------------------------------------
    // Second Half: Calculate


    //Complete the equation (Remove Dice Format)
    for(i in workingOut.replace("[","").replace("]","").split(" ")){

        //TODO: Add Multiplication support (Add Dice Results together, then Multiply, then add/subtract. Find better way to reduce for loops first)

        //Check if it is an Operator
        if(i.contains("+")){
            //Add Next Number from result
            addNext = true
        }else if(i.contains("-")){
            //Subtract Next Number from result
            addNext = false
        }
        //Check if Number
        else if(i.toIntOrNull() != null){

            //Add or Subtract when ready
            if(addNext){
                // Add to Result
                result += i.toInt()
            }else{
                // Minus from Result
                result -= i.toInt()
            }
        }

    }

    //Return Formatted Output
    return result.toString() + ": " + workingOut

}


/*
* RollDice - Rolls dice given in tabletop format
*   (Dice Amount + 'd' + Faces of Dice Used; Eg: 1d6 is for rolling a normal dice)
* */
fun rollDice(amount:Int, face:Int): String {

    var output = ""

    //Loop for the amount of rolls necessary
    for (i in 1..amount){
        //Just adding the dice to the total string
        output += Random.nextInt(1, face+1)

        //Format Correctly (Add + signs for all but last number)
        if(i < amount){
            output += " + "
        }

    }

    //Return Dice Number
    return output
}