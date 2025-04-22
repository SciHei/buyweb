package com.scihei.buyweb.user.service.DTO;

import com.scihei.buyweb.user.repository.DO.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private String username;
    private String password;
    private Users.AccountState accountState;

}
