package org.example.lab1.Controller;

import org.example.lab1.model.CarModels;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController  //@Controller + @ResponseBody (convert model to data (JSON or XML) with  HttpMessageConverter)
@RequestMapping("cars")
public class ResponseController {
    private final RestTemplate restTemplate=new RestTemplate();
    String URL="https://cars-base.ru/api/cars";
    @GetMapping("all")
    public CarModels getResponseAll() {
        String response=restTemplate.getForObject(URL,String.class);
        return new CarModels(response);
    }
    @GetMapping("mark")
    public CarModels getResponseId(@RequestParam(value="id", required = false, defaultValue = "AC")String id) {
       String response= restTemplate.getForObject(URL+"/"+id,String.class);
       return new CarModels(response);
    }
}