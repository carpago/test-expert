package nl.carpago.testexpert;

import java.util.ArrayList;
import java.util.List;

import nl.carpago.testexpert.annotation.CreateUnittest;
import nl.carpago.testexpert.annotation.Expect;
import nl.foo.AccidentalPerson;
import nl.foo.Announcement;
import nl.foo.MessageDAO;

public final class ManageMessageServiceImpl implements ManageMessageService {

	private MessageDAO messageDao;

	private PersonDAO personDao;

	private List<String> list = new ArrayList<String>();

	private List<MessageDAO> list2 = new ArrayList<MessageDAO>();

	public ManageMessageServiceImpl(String in) {

	}

	public ManageMessageServiceImpl() {

	}

	public List<Announcement> findAllByExample(Announcement announcement) {
		List<Announcement> result = this.getMessageDao().findAllWhere(announcement.getAccidentalPerson(), announcement.getMessageDigestOtherParty());

		return result;
	}

	public List<Announcement> getMessagesWithStateManual() {
		return this.getMessageDao().findAllWhereStateIsManual();
	}

	public void testMe(String in) throws InterruptedException {

		this.getMessageDao().setMessage("test");

	}

	public List<Announcement> testMetGenerics() {
		return this.getMessageDao().getList();
	}

	@CreateUnittest(in = { "otherAccidentalPerson", "anderMessageDigest" }, out = "message")
	public Announcement getMessage(AccidentalPerson accidentalPerson, String messageDigest) {

		String voornaam = "Raymond";
		Announcement resultMelding = this.messageDao.getMessage(accidentalPerson, messageDigest, voornaam);
		String test = this.messageDao.getMessage(accidentalPerson, messageDigest, voornaam).getMessageDigest();

		if (this.getMessageDao() != null && this.messageDao.getMessage(accidentalPerson, "riemar", voornaam).getMessageDigest().equals("mijn") && true) {
			System.out.println("equal");
		}

		return resultMelding;
	}

	@CreateUnittest(in = "number", out = "string")
	public String getNumber(int aNumber) {
		String result = this.personDao.getSofi(aNumber);

		return result;
	}

	public PersonDAO getPersoonDao() {
		return personDao;
	}

	public void setPersoonDao(PersonDAO persoonDao) {
		this.personDao = persoonDao;
	}

	@CreateUnittest(out = "message")
	public Announcement geefMelding() {

		list.add("aap");

		AccidentalPerson accidentalPerson = new AccidentalPerson(127797592, (short) 2012);
		accidentalPerson.setSocialSecurityNumber(127797592);
		accidentalPerson.setYear((short) 2012);

		Announcement message = new Announcement();
		message.setMessageDigest("aid02");
		message.setAccidentalPerson(accidentalPerson);

		return this.getMessageDao().getMessage();

	}

	// @CreateUnittest
	public void zonderReturn() {

	}

	// @CreateUnittest(in="messageDigest")
	public void zonderReturnMetParams(String eenString) {

		boolean resultjetemp = list.add("aap");

	}

	// @CreateUnittest(out="message")
	public Announcement geefTestMelding() {
		AccidentalPerson accidentalPerson = new AccidentalPerson(127797592, (short) 2012);
		String messageDigest = "teststring";
		String voornaam = "Raymond";

		Announcement resultMelding = this.getMessageDao().getMessage(accidentalPerson, messageDigest, voornaam);

		return resultMelding;

	}

	@Expect(out = "messageDao")
	public MessageDAO getMessageDao() {
		return this.messageDao;
	}

	@Expect(in = "messageDao")
	public void setMeldingDao(MessageDAO messageDao) {
		this.messageDao = messageDao;
	}

	// x verder met het maken van literals ipv vars voor EasyMock ...
	// @CreateUnittest(in={"1", "2"}, out="3")
	// straks ook testen zonder parameters
	 @CreateUnittest(in={"2", "3"}, out="drie")
	public int add(int monkey, int horses) {

		int resultFromDao = this.getMessageDao().add(monkey, horses);

		int result = monkey + horses;

		return result;
	}
}
