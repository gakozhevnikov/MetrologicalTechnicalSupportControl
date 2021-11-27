package com.kga.metrologicaltechnicalsupportcontrol.facade;

import com.kga.metrologicaltechnicalsupportcontrol.dto.UserDTO;
import com.kga.metrologicaltechnicalsupportcontrol.model.User;

public class UserFacade {

    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDTO.getId());
        userDTO.setUserName(userDTO.getName());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastName());
        return userDTO;
    }
}
