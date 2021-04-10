package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcodingbat.entity.Example;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.ExampleDto;
import uz.pdp.appcodingbat.service.ExampleService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;


    /**
     * GET ALL EXAMPLE
     * @return EXAMPLE LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Example> exampleList = exampleService.getAll();
        return ResponseEntity.ok(exampleList);
    }



    /**
     * GET ONE EXAMPLE WITH ID
     * @param id
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = exampleService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }


    /**
     * ADD NEW EXAMPLE WITH EXAMPLE_DTO
     * @param exampleDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ExampleDto  exampleDto){
        ApiResponse apiResponse = exampleService.add(exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    /**
     * DELETE EXAMPLE WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = exampleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }


    /**
     * EDIT EXAMPLE WITH ID EXAMPLE_DTO
     * @param id
     * @param exampleDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody ExampleDto exampleDto){
        ApiResponse apiResponse = exampleService.edit(id, exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }





    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
