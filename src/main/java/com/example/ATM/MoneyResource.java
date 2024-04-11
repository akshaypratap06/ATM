package com.example.ATM;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MoneyResource {

    @PostMapping("v1/addMoney")
    public String addMoney(@RequestBody Map<Integer,Integer> money){
        long count=0;
        for (Map.Entry<Integer,Integer> obj: money.entrySet()) {
            count=count +((long) obj.getValue() *obj.getKey());
            AtmController.moneyByDenomination.put(obj.getKey(),AtmController.moneyByDenomination.getOrDefault(obj.getKey(),0)+ obj.getValue());
        }
        AtmController.totalMoney+=count;
        return "Done";
    }

    @GetMapping("v1/totalMoney")
    public Long totalMoney(){
        return AtmController.totalMoney;
    }

    @GetMapping("v1/moneyDenomination")
    public Map<Integer,Integer> moneyDenomination(){
        return AtmController.moneyByDenomination;
    }
}
