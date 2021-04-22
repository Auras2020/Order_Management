package start;

import java.lang.reflect.Field;

public class ReflectionExample {

    public static void retrieveProperties(Object object) {

        if(object!=null){
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true); // set modifier to public
                Object value;
                try {
                    value = field.get(object);
                    System.out.println(field.getName() + "=" + value);

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

