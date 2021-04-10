package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcodingbat.entity.Language;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.LanguageDto;
import uz.pdp.appcodingbat.service.LanguageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    /**
     * GET ALL LANGUAGES
     * @return LANGUAGE LIST
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Language> languageList = languageService.getAll();
        return ResponseEntity.ok(languageList);
    }



    /**
     * GET ONE LANGUAGE WITH ID
     * @param id
     * @return ApiResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }



    /**
     * ADD NEW LANGUAGE WITH LANGUAGE_DTO
     * @param languageDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = languageService.add(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }



    /**
     * DELETE LANGUAGE WITH ID
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



    /**
     * EDIT LANGUAGE WITH ID AND LANGUAGE_DTO
     * @param id
     * @param languageDto
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = languageService.edit(id, languageDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
