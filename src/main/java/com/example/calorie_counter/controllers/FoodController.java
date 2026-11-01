package com.example.calorie_counter.controllers;

import com.example.calorie_counter.DTOs.FoodDTO;
import com.example.calorie_counter.DTOs.FoodNameDTO;
import com.example.calorie_counter.services.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {
    private FoodService foodService;
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/search")
    public List<FoodDTO> search(@RequestBody FoodNameDTO data){
        return foodService.getFoods(data.name());
    }
}
