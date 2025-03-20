package inheritance.sender;

import java.time.LocalTime;

public class EmailLetterSender extends AbstractLetterSender {

    public EmailLetterSender(LocalTime currentTime) {
        super(currentTime);
    }

    @Override
    public void sendLetter() {
        String greeting = "Good " + getTimeOfDayString();
        String contents = "Dead customer, ...";
        System.out.println("Sending email ...");
        System.out.println("Title: " + greeting);
        System.out.println("Text: " + contents);
    }
}