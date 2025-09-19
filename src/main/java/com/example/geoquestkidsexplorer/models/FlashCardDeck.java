package com.example.geoquestkidsexplorer.models;

import java.util.List;
/**
 * Calls the main Logic of FlashCard
 * Refactored and moved from controller to model
 * **/

public class FlashCardDeck {
    private final List<FlashCard> cards;
    private int index = 0;

    //Front = image showing
    //Back = Text
    private boolean showingFront = true;
    public FlashCardDeck(List<FlashCard> cards){
        this.cards = cards;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
    public int size(){
        return cards.size();
    }
    public int index(){
        return index;
    }

    public boolean showingFront(){
        return showingFront;
    }

    // Return current FlashCard
    public FlashCard current(){
        if (isEmpty()) return null;
        return cards.get(index);
    }

    //Flip Logic
    public void flip(){
        if (!isEmpty()) showingFront = !showingFront;
    }

    public boolean next(){
        if(isEmpty()) return false;
        if (index < cards.size() - 1){
            index++;
            // New card starts on front
            showingFront = true;
            return true;
        }
        return false;
    }

    public boolean previous(){
        if(isEmpty()) return false;
        if(index > 0){
            index--;
            showingFront = true;
            return true;
        }
        return false;
    }

}
