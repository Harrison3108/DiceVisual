package com.example.dicevisual.Dice

import kotlin.random.Random

/*
* DiceCal.kt - Contains all of the maths calculations after pressing the "=" Button
* Last Edit: Nov 8 2023
* */

//=========================================================
//              FORMATTING INPUT
//=========================================================
/*
* Table of Contents
* XXX
* */


//=========================================================
//              FORMATTING INPUT
//=========================================================
/**
* Contains:
 * parseInput: XXX
 * addCommas: XXX
 * limitNumbers: Limits the maximum amount of numbers to be used in this XXX
* */

/*
* parseInput: Gives the correct spacing and Formatting for Dice Calculation
* For an example, see the string after the ":" after a successful calculation
* Eg: 23:-> [6,5,6,3,2] + 1
*
* - input: string from BasicDiceCalculator (Enter Button Press)
*
* Output: Formatted String with correct spacing and dice rolled
* */

fun parseInput(input:String):String{

    var workingOut = ""
    val inputList = input.split(" ")

    //Roll Dice and Format correctly
    for(i in inputList){

        //All Checks
        if(i.toLongOrNull() != null){
            //If a normal number, add straight to output
            workingOut += i

        } else if(i.contains("d")){
            //If a Dice

            val split = i.split("d")

            var type : String?
            if(split.size > 2){
                type = split[2]
            }else{
                type = null
            }
            //Add each dice roll in proper formatting
            workingOut += "[" + rollDice(split[0].toLong(), split[1].toLong(),type) + "]" //TODO Show DL or DH

        } else if(i.contains("+")){
            //If a "+"

            workingOut += " $i "
        }else if(i.contains("-")){
            //If a "-"
            workingOut += " $i "
        }else if(i.contains("x")){
            //If a "x" (Multiply)
            workingOut += " $i "
        }else if(i.contains("/")){
            //If a "/"
            workingOut += " $i "
        }

        else{

            //Something went wrong. Inform the user without crashing
            //val mainText = findViewById<TextView>(R.id.id_Test)
            //mainText.text = "Something went wrong while calculating. Please try again later"

        }

    }

    return workingOut
}

//=========================================================================
/*
*   addCommas
*   - Input:
* */
fun addCommas(input:String):String{
    var output = ""
    var split = input.replace(",","").split(" ")

    for(i in split.indices){
        //Format specific cases
        if(split[i].contains("[0-9]".toRegex()) && !(split[i].contains('d') || split[i].contains('('))){
            //Format regular numbers
            output += split[i].reversed().chunked(3).joinToString(",").reversed()
            //Using https://stackoverflow.com/questions/53206936/add-commas-or-point-every-3-digits-using-kotlin - William Da Silva
        }else if(split[i].contains('d')){
            //Format dice, XXX
            output += addCommas(split[i].replace('d', ' ')).replace(' ', 'd')
        }else{
            output += " " + split[i] + " "
        }
    }


    return output
}

//==============================================
fun limitNumbers(input: String): String{
    val numLimit = 10
    var output = ""

    var numberString = input.replace(",","")


    if(numberString.length <= numLimit){
        output = numberString
    }else{
        output = numberString.substring(0, numLimit)
        //TODO play sound or some other feedback
    }

    return output
}

//=========================================================
//                  DICE FUNCTIONS
//=========================================================

/**
 * Contains:
 *  rollDice(): Does the whole rolling dice thing (Without the brackets)
 *  dropLowest(): Drop the LOWEST number
 * */

/*
* RollDice - Rolls dice given in tabletop format
*   (Dice Amount + 'd' + Faces of Dice Used; Eg: 1d6 is for rolling a normal dice)
* */
fun rollDice(amount:Long, face:Long, type:String?): String {

    var output = ""

    //-------------------------------------------
    //Roll Dice Normally

    //Loop for the amount of rolls necessary
    for (i in 1..amount){
        //Just adding the dice to the total string
        output += Random.nextLong(1, face + 1)

        //Format Correctly (Add + signs for all but last number)
        if(i < amount){
            output += " + "
        }

    }

    //-------------------------------------------------------
    //Drop either HIGHEST or LOWEST number from roll.
    if(type != null){
        var split2 = output.split(" + ")
        var dropHighNotLow = false //Default: Drop Lowest Number

        var dropPos = 0
        var drop = split2[0].toLong()


        // Determine which function to continue with
        if(type.contains("h")) {
            //Drop HIGHEST rolled number
            dropHighNotLow = true
        }else if(type.contains("l")){
            //Drop LOWEST rolled number
            dropHighNotLow = false
        }else{
            //Break the type forcefully, not an implemented option!
            return output
        }

        //XXX

        for(i in split2.indices){
            if(dropHighNotLow){
                //Drop Highest Code
                if(split2[i].toLong() > split2[dropPos].toLong()){
                    //If current number is HIGHER than the highest one available, switch it
                    dropPos = i
                }
            }else{
                //Drop Lowest Code
                if(split2[i].toLong() < split2[dropPos].toLong()){
                    //If current number is LOWER than the highest one available, switch it
                    dropPos = i
                }
            }

        }

        output = ""


        //Add all other numbers normally
        for (i in split2.indices){
            if(i != dropPos) {
                output += split2[i] + " + "
            }
        }
        //Drop last plus sign
        output = output.dropLast(3)

        //Add the dropped number (To show which number was dropped)
        output += " ("+split2[dropPos]+")"

    }

    //Return Dice Number
    return output
}

/*
* addDice: Takes the formatted string from start, and adds all rolled dice together here.
* Allows Multiplication to work by multiplying result. Same with Division
* - input: Formatted String from parseInput()
*
* Output: The Formatted String from parseInput(), but with the total dice rolled
*         rather than the each individual dice rolled
* */
fun addDice(input:String):String{

    var output = ""
    var diceTotal:Long = 0

    var isDice = false

    val inputArray = input.split(" ")

    for(i in inputArray.indices){

        //Check if the Character has the Starting Bracket Sign
        if(inputArray[i].contains('[')){
            isDice = true
        }

        //Check if you are within the square brackets []
        if(isDice){
            //Add all the rolls together
            if(!inputArray[i].contains('+')) {
                //Add Number without Dice Symbols
                val current = inputArray[i].replace("[", "").replace("]", "")
                if (current.toLongOrNull() != null) {
                    diceTotal += current.toLong()
                }
            }
        }else{
            //Leave the input the same (No Dice Here)

            //Check if Int or Operator
            if(inputArray[i].toLongOrNull() != null){
                //Add without Spaces
                output += inputArray[i]
            }else{
                //Add WITH Spaces
                output += " $inputArray[i] "
            }
        }

        //Checks if you hit the last dice digit
        if(inputArray[i].contains(']')){
            isDice = false

            //Add Final Result and reset
            output += diceTotal
            diceTotal = 0
        }

    }

    return output
}

/*
* multiplyAndDivide - Does DM in BODMAS; Division and Multiplication
*  Assumes
*   - TODO Proper Formatting (No Multiplication or Divide without numbers before and after operator)
*   - TODO Brackets are dealt with
*
* TODO Add Support for more than one operation (eg: 4 x 3 / 2 BREAKS EVERYTHING)
* */
fun multiplyAndDivide(input:String): String{

    val inputSplit = input.split(' ')

    var output = ""

    var doOperation = false
    var isMultiply = true //True = Multiply; False = Divide
    var afterOperation = false

    var prevNum : Double

    for(i in inputSplit.indices){
        if(inputSplit[i].contains('x')){
            //Prepare for Multiplication
            doOperation = true
            isMultiply = true
        }else if(inputSplit[i].contains('/')){
            //Prepare for Multiplication
            doOperation = true
            isMultiply = false
        }else if(inputSplit[i].toLongOrNull() != null && doOperation){
            //Do Calculation

            //Get Previous Number
            if((i - 2) >= 0){
                //
                val temp = output.split(" ")

                //Checks if you have done a Multiply or Divide Before
                if(afterOperation){
                    //
                    prevNum = temp.last().toDouble()
                }else{

                    //Works Fine
                    prevNum = temp[i-2].toDouble()
                    afterOperation = true

                }

                if(output.contains(' ')) {
                    output = output.dropLast(temp.last().length)
                }else{
                    output = ""
                }

            }else{
                prevNum = 0.0
            }

            //Check which operator to use (NOTE: Tabletop games ALWAYS Round DOWN, hence why it is done here)
            if(isMultiply){
                val calculation = prevNum * inputSplit[i].toDouble()
                output += calculation.toString().split(".").first() //Always Round Down in DND
            }else{
                val calculation = prevNum / inputSplit[i].toDouble()
                output += calculation.toString().split(".").first() //Always Round Down in DND
            }
            //
            doOperation = false
        }else{
            //
            output += inputSplit[i]
            afterOperation = false
        }
    }

    /*

    for(i in inputSplit.indices){
        //Skip first Num

        if(isMultiply && inputSplit.get(i).toIntOrNull() != null){
            firstNum = inputSplit.get(i-2)




        }

        if(inputSplit.get(i).contains('x') || inputSplit.get(i).contains('/') ){
            if(inputSplit.get(i).contains('x') ){
                isMultiply = true
                isDivide = true
            }else {
                isDivide = true
            }
        }else{
            //Is Number
            if(inputSplit.get(i).toIntOrNull() != null){

                //
                output += inputSplit.get(i)
                firstNum = inputSplit.get(i)
            }else{
                //Normal Operators
                output += " " + inputSplit.get(i) + " "
            }
        }





    }

    */
    //
    return output
}

/*
* addAndSubtract: The final stage of adding and subtracting what is left
* - input: String from multiplyAndDivide (Currently from addDice)
*
* Output: Final Result from the calculator
* */
fun addAndSubtract(input:String):Long{
    var addNext = true
    var result:Long = 0
    for(i in input.split(" ")){
        //Check if it is an Operator
        if(i.contains("+")){
            //Add Next Number from result
            addNext = true
        }else if(i.contains("-")){
            //Subtract Next Number from result
            addNext = false
        }
        //Check if Number
        else if(i.toLongOrNull() != null){

            //Add or Subtract when ready
            if(addNext){
                // Add to Result
                result += i.toLong()
            }else{
                // Minus from Result
                result -= i.toLong()
            }
        }
    }

    return result

}

/*
* calculate: Calls functions to format and calculate the equation, then pass result back to
*            main code to be put into a history object.
*
* - input: Normal text from the Enter button on click function
* Output: Formatted Result with "result: Formatted Input" Structure
* */
fun calculate(input:String):String{

    //First Half (Write Equation Out)
    val formattedInput = parseInput(input)


    //Second Half (Actually Calculate using that Equation)
    val result = addAndSubtract(addDice(formattedInput)) //TODO Add multiplyAndDivide when working

    //Return Formatted Output
    //return "$result: $formattedInput"
    return addCommas(result.toString()) +": " + addCommas(formattedInput)

}