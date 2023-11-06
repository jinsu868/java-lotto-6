package lotto.validator;

import java.util.List;

public class InputValidator {
    public void validateNumber(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                throw new IllegalArgumentException();
            }
        }
    }
}