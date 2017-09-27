package org.apimenov.domclick.interview.ft.rest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apimenov.domclick.interview.ft.rest.domain.dto.TransferDTO;
import org.apimenov.domclick.interview.ft.service.domain.OperationType;

public class MoneyTransferValidator implements
    ConstraintValidator<MoneyTransferConstraint, TransferDTO> {

  @Override
  public void initialize(MoneyTransferConstraint constraintAnnotation) {

  }


  @Override
  public boolean isValid(TransferDTO value, ConstraintValidatorContext context) {

    if (value.getType() == null) {
      context
          .buildConstraintViolationWithTemplate("Operation type cannot be null")
          .addPropertyNode("operationType");
      return false;
    }

    OperationType type = value.getType();

    if (type == OperationType.WITHDRAW && value.getToAccountId() == null) {
      context
          .buildConstraintViolationWithTemplate(
              "toAccountId type cannot be null when transferring money")
          .addPropertyNode("toAccountId");
      return false;
    }

    return true;
  }
}
