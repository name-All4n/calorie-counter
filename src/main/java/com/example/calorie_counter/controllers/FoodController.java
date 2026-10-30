package com.example.calorie_counter.controllers;

import com.example.calorie_counter.DTOs.FoodDTO;
import com.example.calorie_counter.services.FoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {
    private FoodService foodService;
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/search")
    public List<FoodDTO> search(@RequestParam String name){
        return foodService.getFoods(name);
    }
}
