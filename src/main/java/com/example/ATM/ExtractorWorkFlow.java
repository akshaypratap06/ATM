package com.example.ATM;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExtractorWorkFlow {

    private IExtractor thousandExtractor;

    @PostConstruct
    void init(){
        HundredExtractor hundredExtractor=new HundredExtractor(100);
        FiveHundredExtractor fiveHundredExtractor= new FiveHundredExtractor(hundredExtractor,500);
        thousandExtractor = new ThousandExtractor(fiveHundredExtractor,1000);
    }

    protected Map<Integer,Integer> evaluate(long remainingMoney, Map<Integer,Integer> currExtract) {
        return thousandExtractor.evaluate(remainingMoney,currExtract);
    }
}
