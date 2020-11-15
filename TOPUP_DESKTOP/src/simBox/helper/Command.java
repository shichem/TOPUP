/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simBox.helper;

/**
 *
 * @author Soulsoft
 */
public class Command {

    private String Command;
    private String Type;

    public Command() {

    }

    public void setCommand(String Command) {
        this.Command = Command;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getCommand() {
        return Command;
    }

    public String getType() {
        return Type;
    }

}
