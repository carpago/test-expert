package nl.foo;

import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;

public interface MessageDAO {

	/**
	 * @param accidentalPerson
	 * @param messageDigest
	 * @return Lijst met Meldingen voor AccidentalPerson met gegeven messageDigest
	 */
	List<Announcement> findAllWhere(AccidentalPerson accidentalPerson, String messageDigest);

	@Expect(in = { "accidentalPerson", "messageDigest", "voornaam" }, out = "message")
	Announcement getMessage(AccidentalPerson accidentalPerson, String messageDigest,
			String firstName);

	List<Announcement> findAllWhereStateIsManual();

	void setMessage(String in);

	List<Announcement> getList();

	@CreateUnittest(out="message")
	Announcement getMessage();

	@CreateUnittest(in = { "een", "twee" }, out = "drie")
	int add(int een, int twee);

}
