import java.util.*;

public class Term
{

	int iFrequencyInCorpus = 0; //How many times does the token occur in entire collection
	int iDocumentFrequency = 0; //In how many documents does the token occur
	Map<Integer, Integer> tmPosting = new TreeMap<Integer, Integer>(); //List of documents in which the token occurs in the collection and its frequency in that document
	
}//End of class Term
