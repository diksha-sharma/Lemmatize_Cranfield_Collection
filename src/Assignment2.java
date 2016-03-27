import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Assignment2 
{
	
	long lStartTime = 0;
	long lEndTime = 0;
	
	long lIntermediateStartTime = 0;
	long IntermediateEndTime = 0;

	Tokenizer Tokenizer;
	Porter Stemmer;
	Lemmatize lLemma;
		
	static Map<String, Term> mStemIndexUnCompressed = new HashMap<String, Term>();
	
	//This is the main class that calls other classes for performing all information retrieval sub steps
	
	public static void main(String args[])
	{
		
		Assignment2 searchEngine = new Assignment2();		
		
		// Start search engine
		searchEngine.lStartTime = System.currentTimeMillis();
		
		String sDirectory = args[0];
		
		// Start Tokenizing
		searchEngine.lIntermediateStartTime = System.currentTimeMillis();

		searchEngine.Tokenizer = new Tokenizer();
		searchEngine.IntermediateEndTime = System.currentTimeMillis();
		
		System.out.println("Time taken to tokenize:  " + ((searchEngine.IntermediateEndTime - searchEngine.lIntermediateStartTime)/1000) + " seconds");
		System.out.println();
		
		// Start Porter Stemmer
		searchEngine.lIntermediateStartTime = System.currentTimeMillis();
		
		searchEngine.Stemmer = new Porter();
		searchEngine.Stemmer.startExecution(sDirectory);
		
		searchEngine.IntermediateEndTime = System.currentTimeMillis();
		
		System.out.println("Time taken by Porter Stemmer:  " + ((searchEngine.IntermediateEndTime - searchEngine.lIntermediateStartTime)/1000) + " seconds");
		System.out.println();
		
		// Start Lemmatizing Tokens
		searchEngine.lIntermediateStartTime = System.currentTimeMillis();
		
		searchEngine.lLemma = new Lemmatize();
		searchEngine.lLemma.TokenLemmatizer(args[0], args[1]);
		searchEngine.lLemma.StemLemmatizer(Porter.mStemIndex, args[1]);
		
		searchEngine.IntermediateEndTime = System.currentTimeMillis();
		
		System.out.println("Time taken to lemmatize tokens:  " + ((searchEngine.IntermediateEndTime - searchEngine.lIntermediateStartTime)/1000) + " seconds");
		System.out.println();		
		
		// End search engine
		
		searchEngine.lEndTime = System.currentTimeMillis();

		System.out.println("Total Execution Time:  " + ((searchEngine.lEndTime - searchEngine.lStartTime)/1000) + " seconds");
		System.out.println();
		
		//Now display the Index sizes:
		File fFile1 = new File(args[1] + "Index_Version1.uncompress.txt");
		System.out.println("Token Index Uncompressed:  " + fFile1.length());
		File fFile2 = new File(args[1] + "Index_Version1.compress.txt");
		System.out.println("Token Index Compressed:  " + fFile2.length());
		System.out.println();
		
		File fFile3 = new File(args[1] + "Index_Version2.uncompress.txt");
		System.out.println("Stem Index Uncompressed:  " + fFile3.length());
		File fFile4 = new File(args[1] + "Index_Version2.compress.txt");
		System.out.println("Stem Index Compressed:  " + fFile4.length());	
		
	}//End of main method	
	
	
}//End of Search class
