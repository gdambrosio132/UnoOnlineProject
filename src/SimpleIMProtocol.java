/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * processInput:
 * @param theInput, a String, what the client last said
 * @return a String representing the server's intended response
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;



public class SimpleIMProtocol {
    private static final int WAITING = 0;
    private static final int IN_GAMEPLAY = 1;


    // private static final int NUMJOKES = 5;

    private int state = WAITING;
    // private int currentJoke = 0;
    private Object cardColor;
    private Object cardNumber;
    private Object cardColor2;
    private Object cardNumber2;
    private boolean validPlay=false;

    //private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    //private String[] answers = { "Turnip the heat, it's cold in here!",
    //       "I didn't know you could yodel!",
    //     "Bless you!",
    //     "Is there an owl in here?",
    //     "Is there an echo in here?" };

    public String processInput(String theInput) {
        String theOutput = null;
        //System.out.print("Server:");
        Scanner scan = new Scanner(System.in);
        if (state == WAITING) {
            theOutput = "Connection Established!";
            //start deck and give cards to players here

            state = IN_GAMEPLAY;
        } else if (state == IN_GAMEPLAY) {

            System.out.print("Server: ");
            theOutput =scan.nextLine();
        } else {
            theOutput = "Bye.";
            state = WAITING;
        }

        return theOutput;
    }

    /*
    public boolean processCard(Card card1, Card card2)
    {
        cardColor=card1.getCardColor();
        cardNumber = card1.getCardNumber();
        cardColor2=card2.getCardColor();
        cardNumber2 = card2.getCardNumber();
        if(cardColor == cardColor2 || cardNumber==cardNumber2)
        {
            validPlay= true;
            System.out.println("Valid Play");
        }

        return validPlay;
    }*/
}