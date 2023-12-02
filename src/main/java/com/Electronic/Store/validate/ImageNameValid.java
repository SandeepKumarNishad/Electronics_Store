package com.Electronic.Store.validate;

import jakarta.validation.Payload;

public @interface ImageNameValid {
    //error message
    String message() default "Invalid Image Name !!";

    //represent group of constraints
    Class<?>[] groups() default {};

    //additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
