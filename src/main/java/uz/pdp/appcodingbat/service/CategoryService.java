package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.Category;
import uz.pdp.appcodingbat.payload.Deleted;
import uz.pdp.appcodingbat.entity.Language;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.CategoryDto;
import uz.pdp.appcodingbat.repository.CategoryRepository;
import uz.pdp.appcodingbat.repository.LanguageRepository;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;


    /**
     * GET ALL CATEGORY
     * @return CATEGORY LIST
     */
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }


    /**
     * GET ONE CATEGORY WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found",false);
        return new ApiResponse("Success",true,optionalCategory.get());
    }



    /**
     * ADD CATEGORY WITH ID AND CATEGORY_DTO
     * @param categoryDto
     * @return ApiResponse
     */
    public ApiResponse add(CategoryDto categoryDto){
        boolean exists = categoryRepository.existsByNameAndLanguageId(categoryDto.getName(), categoryDto.getLanguageId());
        if (exists)
            return new ApiResponse("this category already exist",false);

        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("language not found",false);
        if (!optionalLanguage.get().isActive())
            return new ApiResponse("language deleted",false);

        Language language = optionalLanguage.get();
        Category category=new Category(categoryDto.getName(), categoryDto.getDicription(), language);
        categoryRepository.save(category);
        return new ApiResponse("category added",true);
    }


    /**
     * DELETE CATEGORY WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found",false);
        Category category = optionalCategory.get();

        long numberOfDeleted = categoryRepository.countAllByNameStartingWithAndNameEndingWith(Deleted.DELETED, category.getName())+1;
        category.setName(Deleted.DELETED+numberOfDeleted+":"+category.getName());
        category.setActive(false);
        categoryRepository.save(category);
        return new ApiResponse("category deleted",true);
    }


    /**
     * EDIT CATEGORY WITH ID AND CATEGORY_DTO
     * @param id
     * @param categoryDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, CategoryDto categoryDto){
        boolean exists = categoryRepository.existsByNameAndLanguageIdAndIdNot(categoryDto.getName(), categoryDto.getLanguageId(), id);
        if (exists)
            return new ApiResponse("this category already exist",false);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new ApiResponse("category not found",false);

        Optional<Language> optionalLanguage = languageRepository.findById(categoryDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("this language not found",false);
        if (!optionalLanguage.get().isActive())
            return new ApiResponse("this language deleted",false);

        Category category = optionalCategory.get();
        category.setDescription(categoryDto.getDicription());
        category.setLanguage(optionalLanguage.get());
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ApiResponse("category edited",true);
    }
}
