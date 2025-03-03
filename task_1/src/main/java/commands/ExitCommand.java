package commands;

import environment.ExecutionContext;

public class ExitCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args){
        System.out.println("bipka");
        System.exit(0);
    }
}
