package org.apimenov.domclick.interview.ft.rest.validator;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = MoneyTransferValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MoneyTransferConstraint {

  String message() default "no valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};


}
