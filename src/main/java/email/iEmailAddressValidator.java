package email;

import org.springframework.stereotype.Component;

@Component
public interface iEmailAddressValidator {
    public boolean isValidEmailAddress(String emailAddress);
}
