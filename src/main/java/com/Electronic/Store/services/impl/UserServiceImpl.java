package com.Electronic.Store.services.impl;

import com.Electronic.Store.dto.PageableResponse;
import com.Electronic.Store.dto.UserDto;
import com.Electronic.Store.entities.User;
import com.Electronic.Store.exception.ResourceNotFoundException;
import com.Electronic.Store.helper.Helper;
import com.Electronic.Store.repositories.UserRepository;
import com.Electronic.Store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        //generate unique id in string format
        String userId= UUID.randomUUID().toString();;
        userDto.setUserId(userId);
        //dto=>entity
        User user=DtoEntity(userDto);
        User savedUser=userRepository.save(user);
        //entity-->dto
        UserDto newDto=entityToDto(savedUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not fount!!"));

//        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        //save data
        User updateUser=userRepository.save(user);
        UserDto updatedDto=entityToDto(updateUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User NOt fount!!"));
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

//        Sort sort= Sort.by(sortBy);
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);


        Page page=userRepository.findAll(pageable);

        // It is old code
//        List<User> users=page.getContent();
//        List<UserDto> dtoList=users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
//        return dtoList;
        PageableResponse<UserDto> response= Helper.getPageableResponse(page,UserDto.class);

        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not found!!"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User Not found By email/Email not found"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keywords)
    {
        List<User> users=userRepository.findByNameContaining(keywords);
        List<UserDto> dtoList=users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }



    private User DtoEntity(UserDto userDto) {
//          User user=User.builder()
//                  .userId(userDto.getUserId())
//                  .name(userDto.getName())
//                  .email(userDto.getEmail())
//                  .password(userDto.getPassword())
//                  .about(userDto.getAbout())
//                  .gender(userDto.getGender())
//                  .imageName(userDto.getImageName())
//                  .build();
//          return user;
        return mapper.map(userDto,User.class);
    }
    private UserDto entityToDto(User savedUser) {
//      UserDto userDto= UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();
//        return userDto;
          return mapper.map(savedUser,UserDto.class);
    }

}
