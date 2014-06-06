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

	@CreateUnittest(in = { "andereBetrokkene", "anderBerichtkenmerkAig" }, out = "melding")
	public Announcement getMessage(AccidentalPerson betrokkene, String berichtkenmerkAig) {

		String voornaam = "Raymond";
		Announcement resultMelding = this.messageDao.getMessage(betrokkene, berichtkenmerkAig, voornaam);
		String test = this.messageDao.getMessage(betrokkene, berichtkenmerkAig, voornaam).getMessageDigest();

		if (this.getMessageDao() != null && this.messageDao.getMessage(betrokkene, "riemar", voornaam).getMessageDigest().equals("mijn") && true) {
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

	@CreateUnittest(out = "melding")
	public Announcement geefMelding() {

		list.add("aap");

		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		betrokkene.setSocialSecurityNumber(127797592);
		betrokkene.setYear((short) 2012);

		Announcement melding = new Announcement();
		melding.setMessageDigest("aig02");
		melding.setAccidentalPerson(betrokkene);

		return this.getMessageDao().getMessage();

	}

	// @CreateUnittest
	public void zonderReturn() {

	}

	// @CreateUnittest(in="berichtkenmerkAig")
	public void zonderReturnMetParams(String eenString) {

		boolean resultjetemp = list.add("aap");

	}

	// @CreateUnittest(out="melding")
	public Announcement geefTestMelding() {
		AccidentalPerson betrokkene = new AccidentalPerson(127797592, (short) 2012);
		String berichtkenmerkAig = "teststring";
		String voornaam = "Raymond";

		Announcement resultMelding = this.getMessageDao().getMessage(betrokkene, berichtkenmerkAig, voornaam);

		return resultMelding;

	}

	@Expect(out = "messageDao")
	public MessageDAO getMessageDao() {
		return this.messageDao;
	}

	@Expect(in = "messageDao")
	public void setMeldingDao(MessageDAO meldingDao) {
		this.messageDao = meldingDao;
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
