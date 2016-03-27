import java.io.*;
import java.util.*;


public class Tokenizer
{

	HashMap<String, Term> hmTokens = new HashMap<String, Term>();
	HashMap<String, Term> hmTempTokens = new HashMap<String, Term>();
	HashMap<String, Integer> hmMostFrequentTokens = new HashMap<String, Integer>();
	int iUniqueTokenCount = 0;
	int iTotalTokenCount = 0;
	String[] sStopWords = {"a", "all", "an", "and", "any", "are", "as", "be", "been", "but", "by ", "few", "for", "have", "he", "her", "here", "him", "his", "how", "i", "in", "is", "it", "its", "many", "me", "my", "none", "of", "on ", "or", "our", "she", "some", "the", "their", "them", "there", "they", "that ", "this", "us", "was", "what", "when", "where", "which", "who", "why", "will", "with", "you", "your"};
	
	//*************************************************************************************************************
	//This method reads a file from data dump one at a time and extracts all tokens - also removes all stop words
	//*************************************************************************************************************
	public HashMap<String, Term> Tokenize(String sTempDirectory)
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
		boolean bLower = false;
		boolean bStopWord = false;
		boolean bNewFile = true;
		boolean bNumber = false;
		int iNumberCount = 0;
		
		int iDocumentId = -1;
		
		for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		{
			
			bNewFile = true;
			bStopWord = false;
			sInputLine = null;
			sWord = null;
			bLower = false;
			iDocumentId = -1;
			bNumber = false;
			iNumberCount = 0;
			
			//***********************************************
			// Try opening a file to extract tokens form it
			//***********************************************
			try 
			{
				
				//******************************************
				// Start processing a file from collection
				//******************************************
				fFile = new File(sTempDirectory + fFileList[iFileIndex]);
				fisInputFile = new FileInputStream(fFile);
				disInput = new DataInputStream(fisInputFile);
				brInputReader = new BufferedReader(new InputStreamReader(disInput));
				
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
												
						//****************************************
						// Continue processing if not a stop word
						//****************************************
						if(bStopWord == false)
						{
							
							//*********************************
							// Removing any spaces from words
							//*********************************
							sWord = sWord.trim();

							//*********************************
							// Ignore any single letter words
							//*********************************
							if(sWord.length() > 1)
							{
								
								//*************************
								// Get DocID from the file
								//*************************
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
								
								//**********************
								// Ignore any tag words
								//**********************
								if(((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//Do nothing if the word has <> - it is tag and can be ignored
									
									
								}//End of if(((sWord.contains("<")) || (sWord.contains(">"))))
								else if(!((sWord.contains("<")) || (sWord.contains(">"))))
								{
									
									//*********************************************************************
									// If not a tag word - Checking for Possessives - remove 's from word
									//*********************************************************************
									if(sWord.endsWith("'s"))
									{

										sWord = sWord.substring(0, sWord.length()-2);
										
									
									}//End of if(sWord.endsWith("'s"))
											
									//*************************************************************
									// If the word is contains any special character - remove them
									//*************************************************************
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
									sWord = sWord.replace("-", "");
									sWord = sWord.replace("/", "");
									sWord = sWord.replace("\\", "");
									sWord = sWord.replace(".", "");
									sWord = sWord.replace(",", "");
									sWord = sWord.replace("~", "");
									sWord = sWord.replace("`", "");
									sWord = sWord.replace("_", "");
									sWord = sWord.replace("[", "");
									sWord = sWord.replace("]", "");
									sWord = sWord.replace("{", "");
									sWord = sWord.replace("}", "");

									//***********************************************
									// Does the word contain any lower case letters
									//***********************************************
									bLower = false;
									for(int i=0; i < (sWord.length()); i++)
									{
										
										if(Character.isLowerCase(sWord.charAt(i)))
										{
											
											bLower = true;
											
										}//End of if(Character.isLowerCase(sWord.charAt(i)))
										
									}//End of for(int i=0; i < (sWord.length()); i++)
									
									//*************************
									// Possibly not an acronym
									//*************************
									if(bLower == true)
									{
										
										sWord = sWord.toLowerCase();
										
										//***************************************************
										// If the word is contains any numbers - remove them
										//***************************************************
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
										
									}//End of if(bLower == true)
									else if(bLower == false)
									{
										
										iNumberCount = 0;
										
										for(int i=0; i < (sWord.length()); i++)
										{
											
											if(Character.isDigit((sWord.charAt(i))))
											{
												
												bNumber = true;
												iNumberCount = iNumberCount + 1;
												
											}//End of if(Character.isDigit((sWord.charAt(i))))
											
										}//End of for(int i=0; i < (sWord.length()); i++)
										
										//**********************************************
										// If the word is all numbers only - Ignore it
										//**********************************************
										if(iNumberCount == sWord.length())
										{

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
											
										}///End of if(iNumberCount == sWord.length())
										
									}//End of else if(bLower == false)
									
									//*********************************************************************************
									// If the word is "A" then it is most likely stop word "a" - convert to lower case
									//*********************************************************************************						
									if(sWord.contentEquals("A"))
									{
										
										sWord = sWord.toLowerCase();
										
									}
									
									bStopWord = false;
									//**********************************************************************************
									// Check once again if the word is not reduced to a stop word after all processing
									// If it is a stop word now then ignore else add it as a new token
									//**********************************************************************************
									for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
									{
										
										String sTempWord = sWord.toLowerCase();
										String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
										
										if(sTempWord.equals(sTempStopWord))
										{
											
											bStopWord = true;						
											
										}//End of if(sTempWord.equals(sTempStopWord))
										
									}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
									
									if(sWord.length() > 1 && bStopWord == false)
									{
										
										iTotalTokenCount = iTotalTokenCount + 1;
										
										//*******************************************************************
										// If the hashmap does not contain the word already - add new token
										//*******************************************************************
										if(!hmTokens.containsKey(sWord))
										{
											
											//*********************************
											// Add the word to tokens hashmap
											//*********************************
											Term TempToken = new Term();											
											TempToken.iFrequencyInCorpus = TempToken.iFrequencyInCorpus + 1;
											TempToken.iDocumentFrequency = TempToken.iDocumentFrequency + 1;
											TempToken.tmPosting.put(iDocumentId, iDocumentId);

											hmTokens.put(sWord, TempToken);
											
											iUniqueTokenCount = iUniqueTokenCount + 1;												
											
										}//End of if(!hmTokens.containsKey(sWord))
										else if (hmTokens.containsKey(sWord))
										{
											
											//********************************************************************************************
											// If the hashmap already contains that token then increment counts and add document to its
											// postings list if not already added
											//********************************************************************************************
											hmTokens.get(sWord).iFrequencyInCorpus = hmTokens.get(sWord).iFrequencyInCorpus + 1;
											
											if(!hmTokens.get(sWord).tmPosting.containsKey(iDocumentId))
											{
												
												hmTokens.get(sWord).iDocumentFrequency = hmTokens.get(sWord).iDocumentFrequency + 1;
												hmTokens.get(sWord).tmPosting.put(iDocumentId, iDocumentId);
												
											}//End of if(!hmTokens.get(sWord).hmPosting.containsKey(iDocumentId))

										}//End of else if (hmTokens.containsKey(sWord))										
										
									}//End of if(sWord.length() > 1 && bStopWord == false)
								
								}//End of else if(!((sWord.contains("<")) || (sWord.contains(">"))))
								
							}//End of if(sWord.length() > 1)							
							
						}//End of if(bStopWord == false)
											
					}//End of while(stStringTokenizer.hasMoreTokens())				
					
				}//End of while((sInputLine = brInputReader.readLine()) != null)				
				
			}//End of try block
			catch(Exception e)
			{
				
				System.err.println("File not found exception in extractTokens method of Tokenizer class...");
				
			}//End of catch(Exception e)
			
			
		}//End of for(int iFileIndex = 0; iFileIndex < iFileCount; iFileIndex++)
		
		//************************************
		//Printing the total number of tokens
		//************************************
		System.out.println("Total number of tokens in the collection:  " + iTotalTokenCount);
		
		//*************************************
		//Printing the number of unique tokens
		//*************************************
		System.out.println("Total number of unique tokens in the collection:  " + iUniqueTokenCount);
		
		//**************************************************
		//Printing the number of tokens with frequency = 1
		//**************************************************
		int iFreqEqualOne = 0;
		for (String sKey : hmTokens.keySet())
		{
			
			if(hmTokens.get(sKey).iFrequencyInCorpus == 1)
			{
				
				iFreqEqualOne = iFreqEqualOne + 1;
				
			}//End of if(hmTokens.get(sKey).iFrequencyInCorpus == 1)
			
		}//End of for (String sKey : hmTokens.keySet())
		
		System.out.println("Total number of tokens with frequency equal to 1 in the collection:  " + iFreqEqualOne);
		
		
		//****************************************************************************************
		//Fetching tokens based on frequency to retrieve the most frequent tokens and print them
		//****************************************************************************************
		hmTempTokens.putAll(hmTokens);
		int iMaxFrequency = 0;
		int iTempDocList = 0;
		String sTemp = null;
		
		System.out.println();
		System.out.println("Most frequent 30 tokens in the collection and their frequencies are: ");
		
		for(int iCount = 0; iCount < 30; iCount++)
		{
			
			iMaxFrequency = 0;
			for (String sTempKey : hmTempTokens.keySet())
			{
			
				if(hmTempTokens.get(sTempKey).iFrequencyInCorpus > iMaxFrequency)
				{
					
					sTemp = sTempKey;
					iMaxFrequency = hmTempTokens.get(sTempKey).iFrequencyInCorpus;
					iTempDocList = hmTempTokens.get(sTempKey).iDocumentFrequency;
					
				}//End of if(hmTempTokens.get(sTempKey).iFrequencyInCorpus > iMaxFrequency)
				
			}//End of for (String sTempKey : hmTempTokens.keySet())
			
			hmMostFrequentTokens.put(sTemp, iMaxFrequency);
			hmTempTokens.remove(sTemp);
			
			System.out.println(sTemp + " = " + iMaxFrequency +" Doc List Length:  " + iTempDocList);
			
		}//End of for(int iCount = 0; iCount < 30; iCount++)
		
		
		//**************************************************************************
		// Printing the average number of tokens in each document of the collection
		//**************************************************************************
		System.out.println();
		System.out.println("Average number of tokens in a document in the collection:  " + Math.round(iTotalTokenCount/iFileCount));
		
		return hmTokens;
		
	}//End of readFilesAndProcessTokens
	

}//End of class Tokenizer