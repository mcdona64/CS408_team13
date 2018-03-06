package HumorBot;

public class Exit_Automation_Exception extends Exception {
    public Exit_Automation_Exception(String message){
        super(message);
        System.out.println(message);
    }
    public Exit_Automation_Exception(){
        System.out.println("Automation exited for unkown reasons");
    }
    public void cheeseIt(boolean Spectator){
        if(Spectator)
            System.out.println("Automated Spectator Mode Exited");
        else
            System.out.println("Automated Play Mode Exited");
    }
}
