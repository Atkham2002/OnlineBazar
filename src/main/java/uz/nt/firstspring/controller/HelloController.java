package uz.nt.firstspring.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

@RestController
public class HelloController {

    public static ArrayList<Integer> primes = new ArrayList<>();

    @GetMapping(value = "/hello")
    public List<Integer> prime_collection(@RequestParam  Integer number){
        int i = 2;
        while (i <= number){
            if (is_prime(i)) primes.add(i);
            i++;
        }
        return primes;
    }

    public boolean is_prime(Integer num){
        int i = 2;
        int count = 0;
        while(sqrt(num) >= i){
            if(num % i == 0){
                count++;
            }
            i++;
        }

        return count == 0;
    }

}
