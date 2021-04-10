package uz.pdp.appcodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcodingbat.entity.Answer;
import uz.pdp.appcodingbat.payload.AnswerDto;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.Sinov;
import uz.pdp.appcodingbat.service.AnswerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController extends Sinov {
    @Autowired
    AnswerService answerService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Answer> answerList = answerService.getAll();
        return ResponseEntity.ok(answerList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        ApiResponse apiResponse = answerService.getOne(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse.isSuccess()?apiResponse.getObject():apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.add(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = answerService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.edit(id, answerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }



}
