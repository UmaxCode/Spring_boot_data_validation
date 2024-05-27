package com.umaxcode.spring.boot.data.validation.exceptions.custom;


import lombok.*;

import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
public class WrongInputException extends RuntimeException{

    private final Set<String> errorMessages;

}
