package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcodingbat.entity.User;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.UserDto;
import uz.pdp.appcodingbat.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
     * GET ALL USER
     * @return USER LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> userList = userService.getAll();
        return ResponseEntity.ok(userList);
    }


    /**
     * GET ONE USER WITH ID
     * @param id
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = userService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }


    /**
     * ADD NEW USER WITH USER_DTO
     * @param userDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    /**
     * DELETE USER WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }


    /**
     * EDIT USER WITH ID AND USER_DTO
     * @param id
     * @param userDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.edit(id, userDto);
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
