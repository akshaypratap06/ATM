package com.example.ATM;

import java.util.Map;

public class ThousandExtractor extends IExtractor {


    public ThousandExtractor(IExtractor fiveHundredExtractor, int noteValue) {
        this.next=fiveHundredExtractor;
        this.noteValue=noteValue;
    }

    @Override
    protected Map<Integer,Integer> evaluate(long remainingMoney, Map<Integer, Integer> currExtract) {
        if((remainingMoney/this.noteValue>=1)){
            int noteRequired= (int) (remainingMoney/this.noteValue);
            int noteCount;
            if(noteRequired<=AtmController.moneyByDenomination.getOrDefault(this.noteValue,0)) {
                currExtract.put(this.noteValue, (int) (remainingMoney / this.noteValue));
                noteCount=noteRequired;
            }else{
                currExtract.put(this.noteValue,AtmController.moneyByDenomination.getOrDefault(this.noteValue,0));
                noteCount=AtmController.moneyByDenomination.getOrDefault(this.noteValue,0);
            }
            remainingMoney-=((long) this.noteValue *noteCount);
        }
        if(remainingMoney==0)
            return currExtract;
        else
            return this.next.evaluate(remainingMoney,currExtract);
    }
}
