package com.example.ATM;

import java.util.Map;

public class HundredExtractor extends IExtractor{
    public HundredExtractor(int noteValue) {
        this.noteValue=noteValue;
    }

    @Override
    protected Map<Integer, Integer> evaluate(long remainingMoney, Map<Integer, Integer> currExtract) {
        currExtract.put(this.noteValue, (int) (remainingMoney/this.noteValue));
        return currExtract;
    }
}
