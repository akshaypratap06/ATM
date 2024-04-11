package com.example.ATM;

import java.util.Map;

public abstract class IExtractor {

    IExtractor next;
    int noteValue;

    abstract protected Map<Integer,Integer> evaluate(long remainingMoney, Map<Integer,Integer> currExtract );

}
