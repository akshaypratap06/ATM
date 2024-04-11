package com.example.ATM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerResource {

    @Autowired
    private ExtractorWorkFlow extractorWorkFlow;

    @PostMapping("v1/user/{user}/{money}")
    public String userMoney(@PathVariable String user,@PathVariable Long money){
        AtmController.userMoney.put(user,AtmController.userMoney.getOrDefault(user,0L)+money);
        return "Done";
    }

    @GetMapping("v1/extractMoney/{user}/{money}")
    public ResponseEntity<Object> extractMoney(@PathVariable String user, @PathVariable Long money){
        try {
            if(AtmController.userMoney.get(user)==null)
                throw new Exception("User not yet registered");
            if(AtmController.userMoney.get(user)<money)
                throw new Exception("Not enough money in account");
            if(AtmController.totalMoney<money)
                throw new Exception("Not enough Money in ATM");
            if((money%100)!=0)
                throw new Exception("Money not in 100 denomination.");
            Map<Integer,Integer> response= extractorWorkFlow.evaluate(money,new HashMap<>());
            AtmController.userMoney.put(user,AtmController.userMoney.get(user)-money);
            AtmController.totalMoney-=money;
            for (Map.Entry<Integer,Integer>  note: response.entrySet()) {
                AtmController.moneyByDenomination.put(note.getKey(),AtmController.moneyByDenomination.get(note.getKey())- note.getValue());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("v1/users")
    public Map<String,Long> getAllUsers(){
        return AtmController.userMoney;
    }
}
