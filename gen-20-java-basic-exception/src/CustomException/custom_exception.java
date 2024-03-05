package CustomException;

public class custom_exception {

    public static void main(String[] args) throws age_custom_e {
        validateAge(-1);
    }

    private static void validateAge(int age) throws age_custom_e{
        if (age < 0){
            throw new age_custom_e("Age cannot be negative", new RuntimeException());
        }
    }

}
