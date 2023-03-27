package domain.Validator;

import domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        Boolean passwordHasNumbers = false;
        String password = entity.getPassword();
        String errors = new String();
        if (password.length() < 8)
            errors += "The password must be minimum 8 characters long";
        for (int i = 0; ((i < 10) && (!passwordHasNumbers)); i++){
            if (password.contains(Integer.toString(i))){
                passwordHasNumbers = true;
            }
        }
        if (!passwordHasNumbers)
            errors += "The password must contain a number";
        if (!errors.equals("")){
            throw new ValidationException(errors);
        }
    }
}
