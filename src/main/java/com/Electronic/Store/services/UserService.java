package com.Electronic.Store.services;
import java.util.List;

import com.Electronic.Store.dto.PageableResponse;
import com.Electronic.Store.dto.UserDto;

public interface UserService {
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId);
    //delete
    void deleteUser(String userId);
    //get All users
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get Single user by id

    UserDto getUserById(String userId);
    //get single user by email
    UserDto getUserByEmail(String email);
    //search user
    List<UserDto> searchUser(String keywords);

    //Other user specific feature

}
