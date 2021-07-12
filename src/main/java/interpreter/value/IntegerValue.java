package interpreter.value;

public class IntegerValue extends Value<Integer> {

    private Integer value;

    public IntegerValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return value;
    }

<<<<<<< Updated upstream:src/main/java/interpreter/value/IntegerValue.java
    @Override
    public String toString() {
        return value.toString();
=======
    public String toString(){
        return this.value.toString();
>>>>>>> Stashed changes:interpreter/value/IntegerValue.java
    }
}
