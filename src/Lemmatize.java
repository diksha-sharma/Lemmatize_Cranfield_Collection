import java.util.*;
import java.io.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Lemmatize 
{

	String[] sStopWords = {"a", "all", "an", "and", "any", "are", "as", "be", "been", "but", "by ", "few", "for", "have", "he", "her", "here", "him", "his", "how", "i", "in", "is", "it", "its", "many", "me", "my", "none", "of", "on ", "or", "our", "she", "some", "the", "their", "them", "there", "they", "that ", "this", "us", "was", "what", "when", "where", "which", "who", "why", "will", "with", "you", "your"};
	Properties propProperties = new Properties();
	StanfordCoreNLP scnLemmatizer;
	Map<String, Term> tmTokenLemmaIndex = new TreeMap<String, Term>();
	Map<String, Term> tmStemLemmaIndex = new TreeMap<String, Term>();
	Compress cCompression = new Compress();
	
	public void TokenLemmatizer(String sTempDirectory, String sTempOutPutDirectory)
	{
		
		propProperties.put("annotators", "tokenize, ssplit, pos, lemma");
		scnLemmatizer = new StanfordCoreNLP(propProperties);
		
		findLemma(sTempDirectory);
		
		//Print Token Index UnCompressed
		TokenIndexUnCompressed(sTempOutPutDirectory);
		
				
	}//End of TokenLemmatizer method	
	
	public void StemLemmatizer(Map<String, Term> tmTempStemMap, String sTempOutPutDirectory)
	{
		
		propProperties.put("annotators", "tokenize, ssplit, pos, lemma");
		scnLemmatizer = new StanfordCoreNLP(propProperties);
		
		findStemLemma(tmTempStemMap);
		
		//Print Token Index UnCompressed
		StemIndexUnCompressed(sTempOutPutDirectory);
		
				
	}//End of StemLemmatizer method
	
	public void findLemma(String sTempDirectory)
	{
		
		int iFileCount = new File(sTempDirectory).listFiles().length;
		String fFileList[] = new File(sTempDirectory).list();
		File fFile;
		
		FileInputStream fisInputFile;
		DataInputStream disInput;
		BufferedReader brInputReader;
		
		StringTokenizer stStringTokenizer;
		String sInputLine = null;
		String sWord = null;
		boolean bStopWord = false;
		boolean bNewFile = true;
		boolean bTag = false;
		int iDocumentId = -1;
				
		//For each file fetch tokens and then search for lemma - store lemma in the HashMap
		for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		{
			
			bNewFile = true;
			sInputLine = null;
			iDocumentId = -1;
			
			try 
			{
				
				//******************************************
				// Start processing a file from collection
				//******************************************
				fFile = new File(sTempDirectory + fFileList[iFileIndex]);
				fisInputFile = new FileInputStream(fFile);
				disInput = new DataInputStream(fisInputFile);
				brInputReader = new BufferedReader(new InputStreamReader(disInput));
				
				//System.out.println("Reading file:   " + fFileList[iFileIndex]);
				
				//*************************************
				// Read till the last line of the file
				//*************************************
				while((sInputLine = brInputReader.readLine()) != null)
				{

					stStringTokenizer = new StringTokenizer(sInputLine);
					
					//****************************************************************************************
					//For each new word encountered check if the word is a number, a stop word, <tag>, </tag>
					//****************************************************************************************
					while(stStringTokenizer.hasMoreTokens())
					{
					
						sWord = stStringTokenizer.nextToken();
						bStopWord = false;
						bTag = false;
						
						if(bNewFile == true)
						{
							
							if(sInputLine.equals("<DOC>"))
							{
								
								sInputLine = brInputReader.readLine();
								if (sInputLine.equals("<DOCNO>"))
								{
									
									sInputLine = brInputReader.readLine();
									iDocumentId = Integer.parseInt(sInputLine);
									sInputLine = brInputReader.readLine();
									
									bNewFile = false;
									
								}//End of if (sInputLine.equals("<DOCNO>"))
								
							}//End of if(sInputLine.equals("<DOC>"))
							
						}//End of if(bNewFile == true)
						
						//**************************************
						//Remove any stop words from processing
						//**************************************
						for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
						{
							
							String sTempWord = sWord.toLowerCase();
							String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
							
							if(sTempWord.equals(sTempStopWord))
							{
								
								bStopWord = true;						
								
							}//End of if(sTempWord.equals(sTempStopWord))
							
						}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
						
						if(sWord.startsWith("<") || sWord.endsWith(">"))
						{
							
							bTag = true;
							
						}//End of if(sWord.startsWith("<") || sWord.endsWith(">"))
						
						//********************************************************
						// Continue processing if not a stop word or a tag word
						//********************************************************
						if(bStopWord == false && bTag == false)
						{
							
							if(sWord.endsWith("'s"))
							{
								
								sWord = sWord.substring(0, sWord.length()-2);								
							
							}//End of if(sWord.endsWith("'s"))
										
							//Remove all punctuations and numbers and change to lower case
							sWord = sWord.replace("!", "");
							sWord = sWord.replace("@", "");
							sWord = sWord.replace("#", "");
							sWord = sWord.replace("?", "");
							sWord = sWord.replace("'", "");
							sWord = sWord.replace(":", "");
							sWord = sWord.replace(";", "");
							sWord = sWord.replace("=", "");
							sWord = sWord.replace("+", "");
							sWord = sWord.replace("$", "");
							sWord = sWord.replace("%", "");
							sWord = sWord.replace("^", "");
							sWord = sWord.replace("&", "");
							sWord = sWord.replace("*", "");
							sWord = sWord.replace("(", "");
							sWord = sWord.replace(")", "");
							sWord = sWord.replace("|", "");
							sWord = sWord.replace("/", "");
							sWord = sWord.replace("\\", "");
							sWord = sWord.replace(".", "");
							sWord = sWord.replace(",", "");
							sWord = sWord.replace("~", "");
							sWord = sWord.replace("`", "");
							sWord = sWord.replace("[", "");
							sWord = sWord.replace("]", "");
							sWord = sWord.replace("{", "");
							sWord = sWord.replace("}", "");
							sWord = sWord.replace("1", "");
							sWord = sWord.replace("2", "");
							sWord = sWord.replace("3", "");
							sWord = sWord.replace("4", "");
							sWord = sWord.replace("5", "");
							sWord = sWord.replace("6", "");
							sWord = sWord.replace("7", "");
							sWord = sWord.replace("8", "");
							sWord = sWord.replace("9", "");
							sWord = sWord.replace("0", "");
							
							sWord = sWord.toLowerCase();
							sWord = sWord.trim();
												
							if(sWord.contains("-") || sWord.contains("_"))
							{
							
								//If the token consists of hyphens or underscores then split it into sub tokens 
								//and add them as separate tokens
								String[] sSubWords;
								
								if (sWord.contains("-")) 
								{
								
									sSubWords = sWord.split("-");
									for (int i=0; i < sSubWords.length; i++) 
									{
									
										//System.out.println("Looking for lemma for token:   " + sWord);
										addToIndex(sSubWords[i], iDocumentId);
										
									}//End of for (int i=0; i < sSubWords.length; i++)
									
								}//End of if (sWord.contains("-"))
								else if (sWord.contains("_")) 
								{
								
									sSubWords = sWord.split("_");
									for (int i=0; i < sSubWords.length; i++) 
									{
									
										//System.out.println("Looking for lemma for token:   " + sWord);
										addToIndex(sSubWords[i], iDocumentId);
										
									}//End of for (int i=0; i < sSubWords.length; i++)
									
								}//End of else if (sWord.contains("_"))
								
							}//End of if(sWord.contains("-") || sWord.contains("_"))
							else
							{

								//System.out.println("Looking for lemma for token:   " + sWord);
								addToIndex(sWord, iDocumentId);
								
							}//End of else							
							
						}//End of if(bStopWord == false)
						
					}//End of while(stStringTokenizer.hasMoreTokens())
					
				}//End of while((sInputLine = brInputReader.readLine()) != null)
				
			}//End of try block
			catch(Exception e)
			{
				
				System.err.println("Error during Lemmatizing ....");
				
			}//End of catch(Exception e)			
			
		}//End of for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		
	}//End of findLemma
	
	public void addToIndex(String sTempWord, int iTempDocId)
	{
		
		String sLemma = null;
				
		//Ignoring all tokens of length less or or equal to 1
		if(sTempWord.length() > 1)
		{
			
			Annotation aAnnotation = scnLemmatizer.process(sTempWord);
			
			for(CoreMap cmSentence: aAnnotation.get(SentencesAnnotation.class))
			{
				
				for (CoreLabel clToken: cmSentence.get(TokensAnnotation.class)) 
				{
					
					sLemma = clToken.get(LemmaAnnotation.class);
					sLemma = sLemma.toLowerCase();
					//System.out.println(sTempWord + "   " + sLemma);
					
				}//End of for (CoreLabel clToken: cmSentence.get(TokensAnnotation.class))				
				
			}//End of for(CoreMap cmSentence: aAnnotation.get(SentencesAnnotation.class))

			//Now that we have the lemma - add it to index		
			if(tmTokenLemmaIndex.containsKey(sLemma))
			{
				
				tmTokenLemmaIndex.get(sLemma).iFrequencyInCorpus = tmTokenLemmaIndex.get(sLemma).iFrequencyInCorpus + 1;
				
				if(!tmTokenLemmaIndex.get(sLemma).tmPosting.containsKey(iTempDocId))
				{
					
					tmTokenLemmaIndex.get(sLemma).tmPosting.put(iTempDocId, 1);
					tmTokenLemmaIndex.get(sLemma).iDocumentFrequency = tmTokenLemmaIndex.get(sLemma).iDocumentFrequency + 1;
					
				}//End of if(!hmLemmaIndex.get(sLemma).hmPosting.containsKey(iTempDocId))
				else
				{
					
					int iOldFrequency = tmTokenLemmaIndex.get(sLemma).tmPosting.get(iTempDocId).intValue();
					int iNewFrequency = tmTokenLemmaIndex.get(sLemma).tmPosting.get(iTempDocId).intValue() + 1;
					
					tmTokenLemmaIndex.get(sLemma).tmPosting.replace(iTempDocId, iOldFrequency, iNewFrequency);
					
				}//End of else
				
			}//End of if(hmLemmaIndex.containsKey(sLemma))
			else
			{
				
				Term tempTerm = new Term();
				tempTerm.iFrequencyInCorpus = tempTerm.iFrequencyInCorpus + 1;
				tempTerm.tmPosting.put(iTempDocId, 1);
				tempTerm.iDocumentFrequency = tempTerm.iDocumentFrequency + 1;
				
				tmTokenLemmaIndex.put(sLemma, tempTerm);			
				
			}//End of else
		
		}//End of if(sTempWord.length() > 1)
		
	}//End of addToIndex	

	public void TokenIndexUnCompressed(String sTempOutPutDirectory)
	{
		
		try
		{
			
			PrintWriter pwOutPutFile = new PrintWriter(new FileWriter(sTempOutPutDirectory + "Index_Version1.uncompress.txt"));
			//pwOutPutFile.println(tmTokenLemmaIndex);
			for(String sKey: tmTokenLemmaIndex.keySet())
			{
				
				pwOutPutFile.print(sKey);
				pwOutPutFile.print(tmTokenLemmaIndex.get(sKey).iDocumentFrequency);
				pwOutPutFile.print(tmTokenLemmaIndex.get(sKey).iFrequencyInCorpus);
				for(int i: tmTokenLemmaIndex.get(sKey).tmPosting.keySet())
				{
					pwOutPutFile.print(i);
					pwOutPutFile.print(tmTokenLemmaIndex.get(sKey).tmPosting.get(i).intValue());
					
				}
								
			}
			
			pwOutPutFile.close();
			
		}//End of try block
		catch(Exception e)
		{
			
			System.err.println("Error encountered while writing to output file...");
			
		}//End of catch block
				
		//Now we compress the index
		cCompression.compressTokenIndex(tmTokenLemmaIndex,sTempOutPutDirectory);
				
	}//End of TokenIndexUnCompressed()	
	
	public void findStemLemma(Map<String, Term> tmTempStemMap)
	{
		
		Map<String, Term> tmTempStemLemmaIndex = new TreeMap<String, Term>();
		tmTempStemLemmaIndex.putAll(tmTempStemMap);

		boolean bStopWord = false;
		
		//Ignoring all tokens of length less or or equal to 1
		for(String sTempWord : tmTempStemLemmaIndex.keySet())
		{
			
			bStopWord = false;
			//**************************************
			//Remove any stop words from processing
			//**************************************
			for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
			{
				
				String sTWord = sTempWord.toLowerCase();
				String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
				
				if(sTWord.equals(sTempStopWord))
				{
					
					bStopWord = true;						
					
				}//End of if(sTempWord.equals(sTempStopWord))
				
			}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
			
			if(bStopWord == false)
			{
				
				//Now that we have the lemma - add it to index			
				tmStemLemmaIndex.put(sTempWord, tmTempStemLemmaIndex.get(sTempWord));				
				
			}//End of if(bStopWord == false)
		
		}//End of if(sTempWord.length() > 1)		
	
	}//End of findStemLemma	
	
	public void StemIndexUnCompressed(String sTempOutPutDirectory)
	{
		
		try
		{
			
			PrintWriter pwOutPutFile = new PrintWriter(new FileWriter(sTempOutPutDirectory + "Index_Version2.uncompress.txt"));		
			//pwOutPutFile.println(tmStemLemmaIndex);	
			for(String sKey: tmStemLemmaIndex.keySet())
			{
				
				pwOutPutFile.print(sKey);
				pwOutPutFile.print(tmStemLemmaIndex.get(sKey).iDocumentFrequency);
				pwOutPutFile.print(tmStemLemmaIndex.get(sKey).iFrequencyInCorpus);
				for(int i: tmStemLemmaIndex.get(sKey).tmPosting.keySet())
				{
					pwOutPutFile.print(i);
					pwOutPutFile.print(tmStemLemmaIndex.get(sKey).tmPosting.get(i).intValue());
					
				}
								
			}
			pwOutPutFile.close();
			
		}//End of try block
		catch(Exception e)
		{
			
			System.err.println("Error encountered while writing to output file...");
			
		}//End of catch block			
		
		//Now we compress the index
		cCompression.compressStemIndex(tmStemLemmaIndex,sTempOutPutDirectory);
				
	}//End of StemIndexUnCompressed()	
	
}//End of Lemmatize
