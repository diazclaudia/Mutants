package com.meli.adn.app.validators;

import com.meli.adn.app.domain.Sequence;
import org.springframework.stereotype.Component;



@Component
public class SequenceValidator {

    public static final String ERROR_MESSAGE = "Invalid size. Min size: 4x4";

    public void validate(Object obj) {
        Sequence sample = (Sequence) obj;
        if(sample == null || sample.getDna() == null || sample.getDna().length < 4 || sample.getDna()[0].length() !=  sample.getDna().length) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
