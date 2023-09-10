package email;

import org.springframework.stereotype.Component;
import lombok.*;
import java.util.regex.*;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class EmailAddressValidator implements iEmailAddressValidator{

    private String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"; // Regex expression used to split our string representing an email address into parts
    private int maximumEmailLength = 64;

    public boolean isValidEmailAddress(String emailAddress){

        if (emailAddress == null || emailAddress.length() == 0 || emailAddress.length() > maximumEmailLength){
            return false;
        }

        Pattern p = java.util.regex.Pattern.compile(emailPattern);
        Matcher matcher = p.matcher(emailAddress);

        return matcher.matches(); // Return true if @ is in our string only once, . is in our string at least once, contains no other symbols, and does not end with a . or @ symbol

    }

}
