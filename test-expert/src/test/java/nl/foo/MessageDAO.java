package nl.foo;

import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;

public interface MessageDAO {

	/**
	 * @param betrokkene
	 * @param messageDigest
	 * @return Lijst met Meldingen voor AccidentalPerson met gegeven berichtKenmerk
	 */
	List<Announcement> findAllWhere(AccidentalPerson betrokkene, String messageDigest);

	@Expect(in = { "betrokkene", "berichtkenmerkAig", "voornaam" }, out = "melding")
	Announcement getMessage(AccidentalPerson accidentalPerson, String messageDigest,
			String firstName);

	List<Announcement> findAllWhereStateIsManual();

	void setMessage(String in);

	List<Announcement> getList();

	@CreateUnittest(out="melding")
	Announcement getMessage();

	@CreateUnittest(in = { "een", "twee" }, out = "drie")
	int add(int een, int twee);

}
