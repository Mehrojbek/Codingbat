package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.Language;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.LanguageDto;
import uz.pdp.appcodingbat.repository.LanguageRepository;

import java.util.*;

@Service
public class LanguageService {
    @Autowired
    LanguageRepository languageRepository;



    /**
     * GET ALL LANGUAGES
     * @return LANGUAGE LIST
     */
    public List<Language> getAll(){
        return languageRepository.findAll();
    }




    /**
     * GET ONE LANGUAGE WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found",false);
        return new ApiResponse("Succussfully",true,optionalLanguage.get());
    }



    /**
     * ADD NEW LANGUAGE WITH LANGUAGE_DTO
     * @param languageDto
     * @return ApiResponse
     */
    public ApiResponse add(LanguageDto languageDto){
        boolean byName = languageRepository.existsByName(languageDto.getName());
        if (byName){
            Language language = languageRepository.findByName(languageDto.getName());
            if (language.isActive())
                return new ApiResponse("this language already exist",false);
            language.setActive(true);
            languageRepository.save(language);
            return new ApiResponse("language added",true);
        }
        languageRepository.save(new Language(languageDto.getName()));
        return new ApiResponse("language added",true);
    }




    /**
     * DELETE LANGUAGE WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Language> optional = languageRepository.findById(id);
        if (!optional.isPresent())
            return new ApiResponse("language not found",false);
        Language language = optional.get();
        language.setActive(false);
        languageRepository.save(language);
        return new ApiResponse("language deleted",true);
    }




    /**
     * EDIT LANGUAGE WITH ID AND LANGUAGE_DTO
     * @param id
     * @param languageDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, LanguageDto languageDto){
        boolean exists = languageRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if (exists)
            return new ApiResponse("this language already exist",false);

        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (!optionalLanguage.isPresent())
            return new ApiResponse("language not found",false);
        Language language = optionalLanguage.get();
        language.setName(languageDto.getName());
        languageRepository.save(language);
        return new ApiResponse("language edited",true);
    }
}
