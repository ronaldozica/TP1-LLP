package interpreter.value;

public class IntegerValue extends Value<Integer> {

    private Integer value;

    public IntegerValue(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public String toString(){
        return this.value.toString();
    }
}
