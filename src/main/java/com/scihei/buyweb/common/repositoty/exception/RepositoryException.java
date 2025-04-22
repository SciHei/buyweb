package com.scihei.buyweb.common.repositoty.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryException extends RuntimeException{
    String result;
}
