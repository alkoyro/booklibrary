package by;

/**
 * User: Alexey.Koyro
 */
public class Animal extends PreAnimal{
    private String name;
    private Integer number;
    
    private String value = getValue();

    public String getValue() {
        return "efwe";
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;

        if(this == obj)
        {
            result = true;
        }
        else if (obj != null && getClass().equals(obj.getClass()))
        {
            Animal that = (Animal) obj;

            Object[] fieldsThis = {name, number};
            Object[] fieldsThat = {that.name, that.number};
        }
        else
        {
            result = false;
        }


        return result;
    }
}
