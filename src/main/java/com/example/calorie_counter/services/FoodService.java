package com.example.calorie_counter.services;

import com.example.calorie_counter.DTOs.FoodDTO;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {
    private final List<FoodDTO> foods = new ArrayList<>();

    @PostConstruct
    public void loadFoods() throws IOException {
        ClassPathResource resource = new ClassPathResource("Taco-4a-Edicao.xlsx");
        try (InputStream inputStream = resource.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if(row == null) {
                    continue;
                }

                Cell numberCell = row.getCell(0);
                Cell nameCell = row.getCell(1);

                if(numberCell == null || numberCell.getCellType() != CellType.NUMERIC) {
                    continue;
                }
                if(nameCell == null || nameCell.getCellType() != CellType.STRING) {
                    continue;
                }

                String name = nameCell.getStringCellValue().trim();
                Double calories = readNumericCell(row.getCell(3));
                Double protein = readNumericCell(row.getCell(5));
                foods.add(new FoodDTO(name, calories, protein));
            }
        }
    }

    private Double readNumericCell(Cell cell) {
        if(cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> "Tr".equalsIgnoreCase(cell.getStringCellValue().trim()) ? 0.0:null;
            default -> null;
        };
    }

    public List<FoodDTO> getFoods(String name) {
        return foods.stream()
                .filter(food -> food.name()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }
}
