package uz.pdp.appcodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcodingbat.entity.User;
import uz.pdp.appcodingbat.payload.ApiResponse;
import uz.pdp.appcodingbat.payload.Deleted;
import uz.pdp.appcodingbat.payload.UserDto;
import uz.pdp.appcodingbat.repository.UserRepository;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    /**
     * GET ALL USER
     * @return USER LIST
     */
    public List<User> getAll(){
        return userRepository.findAll();
    }


    /**
     * GET ONE USER WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse getOne(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("user not found",false);
        return new ApiResponse("success",true,optionalUser.get());
    }


    /**
     * ADD NEW USER WITH USER_DTO
     * @param userDto
     * @return ApiResponse
     */
    public ApiResponse add(UserDto userDto){
        //CHECK UNIQUE EMAIL
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists)
            return new ApiResponse("this email exist",false);

        //CHECK EMAIL
        if (!userDto.getEmail().contains("@"))
            return new ApiResponse("email is wrong",false);

        User user=new User(userDto.getEmail(), userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("user added",true);
    }


    /**
     * DELETE USER WITH ID
     * @param id
     * @return ApiResponse
     */
    public ApiResponse delete(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("user not found",false);
        User user = optionalUser.get();

        //HOW MANY DELETED USER
        long numberOfDeletedUser = userRepository.countByEmailStartingWithAndEmailEndingWith(Deleted.DELETED, user.getEmail())+1;
        user.setActive(false);
        user.setEmail(Deleted.DELETED+numberOfDeletedUser+":"+user.getEmail());
        userRepository.save(user);
        return new ApiResponse("user deleted",true);
    }


    /**
     * EDIT USER WITH ID AND USER_DTO
     * @param id
     * @param userDto
     * @return ApiResponse
     */
    public ApiResponse edit(Integer id, UserDto userDto){
        //CHECK UNIQUE EMAIL
        boolean exists = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if (exists)
            return new ApiResponse("this email exist",false);

        //CHECK USER
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("user not found",false);
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("user edited",true);
    }

}
