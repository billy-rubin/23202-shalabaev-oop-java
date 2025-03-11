package environment;

import java.util.*;

public class ExecutionContext {
    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> variables = new HashMap<>();

    public double popStack(){
        if (!stack.isEmpty()){
            return stack.pop();
        } else {
            throw new EmptyStackException();
        }
    }

    public int getStackSize(){
        return stack.size();
    }

    public boolean isStackEmpty(){
        return stack.isEmpty();
    }

    public void pushStack(double value){
        stack.push(value);
    }

    public double peekStack(){
        if (!stack.isEmpty()){
            return stack.peek();
        } else {
            throw new EmptyStackException();
        }
    }

    public Double getVarValue(String var){
        return variables.get(var);
    }

    public void putPair(String variable, Double value){
        variables.put(variable, value);
    }

}
