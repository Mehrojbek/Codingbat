package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcodingbat.entity.Category;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.CategoryDto;
import uz.pdp.appcodingbat.payload.ValidationMessage;
import uz.pdp.appcodingbat.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends ValidationMessage {
    @Autowired
    CategoryService categoryService;


    /**
     * GET ALL CATEGORY
     * @return CATEGORY LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Category> categoryList = categoryService.getAll();
        return ResponseEntity.ok(categoryList);
    }



    /**
     * GET ONE CATEGORY WITH ID
     * @param id
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }



    /**
     * ADD CATEGORY WITH ID AND CATEGORY_DTO
     * @param categoryDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.add(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



    /**
     * DELETE CATEGORY WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }



    /**
     * EDIT CATEGORY WITH ID AND CATEGORY_DTO
     * @param id
     * @param categoryDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.edit(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }




}
