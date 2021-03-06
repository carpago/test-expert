package nl.carpago.testexpert;

import java.util.List;

import nl.foo.AccidentalPerson;
import nl.foo.Announcement;


public interface ManageMessageService {

	List<Announcement> getMessagesWithStateManual() ;

	List<Announcement> findAllByExample(Announcement message);
	
	Announcement getMessage(AccidentalPerson accidentalPerson, String messageDigest);
	
	int add(int a, int b);
}
