package com.help.rebate.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResultDTO implements Serializable {
    String status;
    String type;
    String currentAuthority;
}