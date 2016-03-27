import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.TreeMap;

public class Compress 
{

	Map<String, Term> tmUnCompressedStemIndex = new TreeMap<String, Term>();
	Map<String, Term> tmUnCompressedTokenIndex = new TreeMap<String, Term>();
	Map<String, CompressStem> tmCompressedStemIndex = new TreeMap<String, CompressStem>();
	Map<String, CompressToken> tmCompressedTokenIndex = new TreeMap<String, CompressToken>();
	String[] sStopWords = {"a", "all", "an", "and", "any", "are", "as", "be", "been", "but", "by ", "few", "for", "have", "he", "her", "here", "him", "his", "how", "i", "in", "is", "it", "its", "many", "me", "my", "none", "of", "on ", "or", "our", "she", "some", "the", "their", "them", "there", "they", "that ", "this", "us", "was", "what", "when", "where", "which", "who", "why", "will", "with", "you", "your"};
	
	//Compress Token Index
	public void compressTokenIndex(Map<String, Term> mTempMap, String sTempOutPutDirectory)
	{
		
		//Fetch the input uncompressed Index
		tmUnCompressedTokenIndex.putAll(mTempMap);
		boolean bStopWord = false;
		
		//for(String sKey : tmUnCompressedTokenIndex.keySet())
		{
			
			//bStopWord = false;
			//**************************************
			//Remove any stop words from processing
			//**************************************
			//for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
			//{
				
				//String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
				
				//if(sKey.equals(sTempStopWord))
				//{
					
					//bStopWord = true;						
					
				//}//End of if(sTempWord.equals(sTempStopWord))
				
			//}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
			
			//if(bStopWord == false)
			{
				
				String[] sKeyTokens = new String[8];
				int iIndex = 0;
				
				for(String sToken: tmUnCompressedTokenIndex.keySet())
				{
					
					bStopWord = false;
					//**************************************
					//Remove any stop words from processing
					//**************************************
					for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
					{
						
						String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
						
						if(sToken.equals(sTempStopWord))
						{
							
							bStopWord = true;						
							
						}//End of if(sTempWord.equals(sTempStopWord))
						
					}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
					
					if(bStopWord == false)
					{
						
						if(iIndex < 8)
						{
							sKeyTokens[iIndex] = sToken;
							//System.out.println("Token fetched: " + sKeyTokens[iIndex]);
							iIndex = iIndex + 1;
							
						}//End of if(iIndex < 8)
											
						if(iIndex >= 8)
						{

							String sPrefix = sKeyTokens[0].substring(0);
							String sMatch = findCommonPrefix(sKeyTokens, sPrefix);					
							iIndex = 0;//Reset the value of iIndex for next block of tokens
							int iPrefixLength = 0;
							
							//System.out.println("sMatch value: " + sMatch);
							if(sMatch != null)
							{
								
								iPrefixLength = sMatch.length();
								CompressToken ctNewToken = new CompressToken();
								String sDictionary = sMatch + "*";
								
								for(int x = 0; x < 8; x++)
								{
									
									sDictionary = sDictionary + sKeyTokens[x].substring(iPrefixLength).length() + "|" + sKeyTokens[x].substring(iPrefixLength);
									
									int iStartDocId = 0;
									int iGap = 0;
									int iFreq = 0;
									PostingFile psFile;
									//System.out.println("Adding posting files now...");
									
									switch(x)
									{
										
										case 0:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 1");
											break;
										}
										case 1:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm2.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 2");
											break;
										}
										case 2:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm3.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 3");
											break;
										}
										case 3:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm4.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 4");
											break;
										}
										case 4:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm5.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 5");
											break;
										}
										case 5:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm6.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 6");
											break;
										}
										case 6:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm7.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 7");
											break;
										}
										case 8:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewToken.alPostingFileTerm8.add(psFile);
												
												iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 8");
											break;
										}
										
									}//End of case
									
								}
								
								//System.out.println("New Dictionary Term: " + sDictionary);
								tmCompressedTokenIndex.put(sDictionary, ctNewToken);
								
							}//End of if(!sMatch.equals(null))
							else
							{
								
								String sPrefix2 = null;
								String sMatch2 = null;
								String[] sKeyTokens2 = new String[7];
								
								for(int p=0; p<7; p++)
								{
									
									sKeyTokens2[p] = sKeyTokens[p];
									
								}
								
								//If nothing matches in all 8 tokens together then split then in 7 and 1
								//then try 6 and 2 and so on
								for(int l = 0; l < 7; l++)
								{
									
									sPrefix2 = sKeyTokens[0].substring(0);
									sMatch2 = findCommonPrefix(sKeyTokens2, sPrefix);
									
								}								
								
								if(sMatch2 != null)//We have a grouping of 7 alike and 1 different 
								{

									int iPrefixLength2 = sMatch2.length();
									CompressToken ctNewToken = new CompressToken();
									String sDictionary2 = sMatch2 + "*";
									
									for(int x = 0; x < 7; x++)
									{
										
										sDictionary2 = sDictionary2 + sKeyTokens2[x].substring(iPrefixLength2).length() + "|" + sKeyTokens2[x].substring(iPrefixLength2);
										
										int iStartDocId = 0;
										int iGap = 0;
										int iFreq = 0;
										PostingFile psFile;
										//System.out.println("Adding posting files now...");
										
										switch(x)
										{
											
											case 0:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 1");
												break;
											}
											case 1:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm2.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 2");
												break;
											}
											case 2:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm3.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 3");
												break;
											}
											case 3:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm4.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 4");
												break;
											}
											case 4:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm5.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 5");
												break;
											}
											case 5:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm6.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 6");
												break;
											}
											case 6:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewToken.alPostingFileTerm7.add(psFile);
													
													iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 7");
												break;
											}
											
										}//End of case
										
									}
									
									//System.out.println("New Dictionary Term: " + sDictionary2);
									tmCompressedTokenIndex.put(sDictionary2, ctNewToken);
									
									sKeyTokens[0] = sKeyTokens[7];
									iIndex = 1;
									
								}
								else
								{
									
									String sPrefix3 = null;
									String sMatch3 = null;
									String[] sKeyTokens3 = new String[6];
									
									for(int p=0; p<6; p++)
									{
										
										sKeyTokens3[p] = sKeyTokens[p];
										
									}
									
									//If nothing matches in all 8 tokens together then split then in 7 and 1
									//then try 6 and 2 and so on
									for(int l = 0; l < 6; l++)
									{
										
										sPrefix3 = sKeyTokens[0].substring(0);
										sMatch3 = findCommonPrefix(sKeyTokens3, sPrefix);
										
									}
									
									if(sMatch3 != null)//We have a grouping of 6 alike and 2 different 
									{

										int iPrefixLength3 = sMatch3.length();
										CompressToken ctNewToken = new CompressToken();
										String sDictionary3 = sMatch3 + "*";
										
										for(int x = 0; x < 7; x++)
										{
											
											sDictionary3 = sDictionary3 + sKeyTokens3[x].substring(iPrefixLength3).length() + "|" + sKeyTokens3[x].substring(iPrefixLength3);
											
											int iStartDocId = 0;
											int iGap = 0;
											int iFreq = 0;
											PostingFile psFile;
											//System.out.println("Adding posting files now...");
											
											switch(x)
											{
												
												case 0:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 1");
													break;
												}
												case 1:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm2.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 2");
													break;
												}
												case 2:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm3.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 3");
													break;
												}
												case 3:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm4.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 4");
													break;
												}
												case 4:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm5.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 5");
													break;
												}
												case 5:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewToken.alPostingFileTerm6.add(psFile);
														
														iStartDocId = tmUnCompressedTokenIndex.get(sKeyTokens3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedTokenIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 6");
													break;
												}
												
											}//End of case
											
										}
										
										//System.out.println("New Dictionary Term: " + sDictionary3);
										tmCompressedTokenIndex.put(sDictionary3, ctNewToken);
										
										sKeyTokens[0] = sKeyTokens[6];
										sKeyTokens[1] = sKeyTokens[7];
										iIndex = 2;
									}									
									
								}
								
							}
							
						}
						
					}
										
					
				}//End of for(String sToken: tmUnCompressedTokenIndex.keySet())
					
			}//End of if(bStopWord == false)
			
		}//End of for(String sKey : tmUnCompressedTokenIndex.keySet())	
	
		try
		{
			
			PrintWriter pwOutPutFile = new PrintWriter(new FileWriter(sTempOutPutDirectory + "Index_Version1.compress.txt"));		
			//pwOutPutFile.println(tmCompressedTokenIndex);	
			for(String sKey: tmCompressedTokenIndex.keySet())
			{
				pwOutPutFile.print(sKey);
				for(int i=0; i<tmCompressedTokenIndex.get(sKey).alPostingFileTerm.size(); i++)
				{
					
					pwOutPutFile.print(tmCompressedTokenIndex.get(sKey).alPostingFileTerm.get(i).byDocumentId);
					pwOutPutFile.print(tmCompressedTokenIndex.get(sKey).alPostingFileTerm.get(i).byFrequency);
					
				}
				
			}
			pwOutPutFile.close();
			
		}//End of try block
		catch(Exception e)
		{
			
			System.err.println("Error encountered while writing to output file...");
			
		}//End of catch block
		
		System.out.println("UnCompressed: Inverted Token List Length in Bytes:  " + tmUnCompressedTokenIndex.size());
		System.out.println("Compressed: Inverted Token List Length in Bytes:  " + tmCompressedTokenIndex.size());
		
		String sInput = "Reynolds";
		System.out.println("Priting df, tf information for terms:  ");
		System.out.println();
		
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		}
        
		sInput = "NASA";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		}                      
         
        sInput = "Prandtl";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		}               
 
        sInput = "flow";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		}          
 
        sInput = "pressure";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		}                       
         
        sInput = "boundary";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		} 
        
        sInput = "shock";
		sInput = sInput.toLowerCase();
		
		if(tmUnCompressedTokenIndex.containsKey(sInput))
		{
			System.out.println("Token: " + sInput);
			System.out.println("df: " + tmUnCompressedTokenIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedTokenIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedTokenIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else if (tmUnCompressedStemIndex.containsKey(sInput))
		{
			System.out.println("Stem: " + sInput);
			System.out.println("df: " + tmUnCompressedStemIndex.get(sInput).iDocumentFrequency);
	        
	        for(Integer iKey: tmUnCompressedStemIndex.get(sInput).tmPosting.keySet())
	        {
	        	
	        	System.out.println("tf for document " + iKey + ":  " + tmUnCompressedStemIndex.get(sInput).tmPosting.get(iKey).intValue());
	        	
	        }
	        
		}
		else
		{
			
			System.out.println("Did not find Reynolds in tokens or stems index");
			
		} 
        
		
	}//End of compressTokenIndex
	
		
	
	public String findCommonPrefix(String sTempArray[], String sTempPrefix)
	{
		
		String sResult = null;
		boolean bFullMatch = true;
		String sPrefix = null;
		int iLength = sTempPrefix.length();
		int iMatch = 0;
		
		for(int j = 0; j < iLength; j++)
		{
			
			if(iMatch < 8)
			{

				if(j==0)					
				{
					sPrefix = sTempPrefix;
					
				}
				else
				{
					sPrefix = sTempPrefix.substring(0, iLength-j);
					
				}
				
				//System.out.println("Searching for match for:  " + sPrefix);
				iMatch = 0;
				
				for(int i= 0; i < sTempArray.length; i++)
				{
					
					if(sTempArray[i].startsWith(sPrefix))
					{
						
						iMatch = iMatch + 1;
						
					}
					
				}
				
			}
			
			if(iMatch == 8)
			{
				
				//System.out.println("Full match found:  " + sPrefix);
				return sPrefix;
				
			}
			
			
		}
				
		return null;
		
	}//End of public int findCommonPrefix()

	public byte[] getGammaCode(int iTempGap)
	{
		
		//Convert the gap value to its binary representation 
		String sBinaryRep = Integer.toBinaryString(iTempGap);
		int iBinaryCodeLen = sBinaryRep.length();
		
		//Remove the first bit from the binary representation and take the rest as offset
		String sOffset = sBinaryRep.substring(1);
		int iOffsetLength = sOffset.length();
		
		//Now get the unary code for the length of the offset
		String sUnaryCode = "";
		
		for(int i=0; i < iOffsetLength; i++)
		{
			
			sUnaryCode = sUnaryCode + "1";
			
		}//End of for(int i=0; i< iOffsetLength; i++	
		
		sUnaryCode = sUnaryCode + "0";
		
		//We now have the unary code of the offset length and also the offset - get the String value of the gamma code for the gap
		String sGammaCode = sUnaryCode + sOffset;
		int iGammaCodeLen = sGammaCode.length();
		
		BitSet bsGammaCode = new BitSet(iGammaCodeLen);
		
		//Now converting the String gamma code to bits - compression
		for(int j = 0; j < iGammaCodeLen; j++)
		{
			
			if(sGammaCode.charAt(j) == '1')
			{
				
				bsGammaCode.set(j, true);
				
			}//End of if(sGammaCode.charAt(j) == '1')
			else
			{
				
				bsGammaCode.set(j, false);
				
			}//End of else			
			
		}//End of for(int j = 0; j < iGammaCodeLen; j++)
		
		return bsGammaCode.toByteArray();
		
	}//End of getGammaCode
	
	public byte[] getDeltaCode(int iTempFreq)
	{
		
		//Convert the gap value to its binary representation 
		String sBinaryRep = Integer.toBinaryString(iTempFreq);
		int iBinaryCodeLen = sBinaryRep.length();
		
		//Get Gamma code for the length of the binary code
		byte[] byGammaCode = getGammaCode(iBinaryCodeLen);
		
		//Remove the first bit from the binary representation and take the rest as offset
		String sOffset = sBinaryRep.substring(1);
		
		String sGammaAndOffset = byGammaCode.toString().concat(sOffset);
		int iGammaAndOffsetLen = sGammaAndOffset.length();
		
		BitSet bsDeltaCode = new BitSet(iGammaAndOffsetLen);
		
		//Now converting the String gamma code to bits - compression
		for(int j = 0; j < iGammaAndOffsetLen; j++)
		{
			
			if(sGammaAndOffset.charAt(j) == '1')
			{
				
				bsDeltaCode.set(j, true);
				
			}//End of if(sGammaCode.charAt(j) == '1')
			else
			{
				
				bsDeltaCode.set(j, false);
				
			}//End of else			
			
		}//End of for(int j = 0; j < iGammaCodeLen; j++)
		
		return bsDeltaCode.toByteArray();
		
	}//End of getDeltaCode

	//Compress the dictionary term
	public String compressDictionary(String sTempWord, String sIndexType)
	{
		
		String sPrefix;
		int iArgLength = sTempWord.length();
		int iPrefixLength = 0;
		int iMaxPrefixLength = 0;
		String sPrefixKey = null;
		boolean bPrefixFound = false;
		String sWord = null;
		
		if(sIndexType =="token")
		{
			
			//If not the first word to be added to the compressed index
			if(tmCompressedTokenIndex.size() > 0)
			{
				
				//Iterate till you find the largest prefix for the current argument
				for(int i=0; i< iArgLength; i++)
				{
				
					sWord = sTempWord.substring(0, iArgLength-i);
					//System.out.println("Searching for word:  " + sWord);
					
					if(bPrefixFound == false)
					{
						
						for(String sKey : tmCompressedTokenIndex.keySet())
						{
							
							if(sWord.startsWith(sKey))
							{
								
								iPrefixLength = sKey.length();
								
								if(iPrefixLength > iMaxPrefixLength)
								{
									
									iMaxPrefixLength = iPrefixLength;
									sPrefixKey = sKey;
									bPrefixFound = true;
									
								}//End of if(iPrefixLength > iMaxPrefixLength)
								
							}//End of if(sTempWord.startsWith(sKey))
							
						}//End of for(String sKey : tmCompressedTokenIndex.keySet())
						
					}//End of if(bPrefixFound == false)
					
				}//End of for(int i=0; i< iArgLength; i++)
				
				if(bPrefixFound == false)
				{
					
					//If first word to be added to the compressed index - then return
					//System.out.println("Returning prefix5:  " + sTempWord);
					return sTempWord;
					
				}//End of if(bPrefixFound == false)
				else
				{
					
					//System.out.println("Returning prefix1:  " + sPrefixKey);
					return sPrefixKey;
					
				}//End of else
				
				
			}//End of if(tmUnCompressedTokenIndex.size() > 0)
			else
			{
				
				//If first word to be added to the compressed index - then return
				//System.out.println("Returning prefix2:  " + sTempWord);
				return sTempWord;
				
			}//End of else
			
		}//End of if(sIndexType =="token")
		else //If stems
		{
			
			//If not the first word to be added to the compressed index
			if(tmCompressedStemIndex.size() > 0)
			{
				
				//Iterate till you find the largest prefix for the current argument
				for(int i=0; i< iArgLength; i++)
				{
				
					sWord = sTempWord.substring(0, iArgLength-i);
					
					if(bPrefixFound == false)
					{
						
						for(String sKey : tmCompressedStemIndex.keySet())
						{
							
							if(sWord.startsWith(sKey))
							{
								
								iPrefixLength = sKey.length();
								
								if(iPrefixLength > iMaxPrefixLength)
								{
									
									iMaxPrefixLength = iPrefixLength;
									sPrefixKey = sKey;
									bPrefixFound = true;
									
								}//End of if(iPrefixLength > iMaxPrefixLength)
								
							}//End of if(sTempWord.startsWith(sKey))
							
						}//End of for(String sKey : tmCompressedStemIndex.keySet())
						
					}//End of if(bPrefixFound == false)
					
				}//End of for(int i=0; i< iArgLength; i++)
				
				//System.out.println("Returning prefix3:  " + sPrefixKey);
				return sPrefixKey;
				
			}//End of if(tmUnCompressedStemIndex.size() > 0)
			else
			{
				
				//If first word to be added to the compressed index - then return
				//System.out.println("Returning prefix4:  " + sTempWord);
				return sTempWord;
				
			}//End of else
			
		}//End of else
		
	}//End of getFrontCode
	
	
	//Compress Stem Index
	public void compressStemIndex(Map<String, Term> mTempMap, String sTempOutPutDirectory)
	{
		
		//Fetch the input uncompressed Index
		tmUnCompressedStemIndex.putAll(mTempMap);
		boolean bStopWord = false;
		
		//for(String sKey : tmUnCompressedStemIndex.keySet())
		{
			
			//bStopWord = false;
			//**************************************
			//Remove any stop words from processing
			//**************************************
			//for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
			//{
				
				//String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
				
				//if(sKey.equals(sTempStopWord))
				//{
					
					//bStopWord = true;						
					
				//}//End of if(sTempWord.equals(sTempStopWord))
				
			//}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
			
			//if(bStopWord == false)
			{
				
				String[] sKeyStems = new String[8];
				int iIndex = 0;
				
				for(String sStem: tmUnCompressedStemIndex.keySet())
				{
					
					bStopWord = false;
					//**************************************
					//Remove any stop words from processing
					//**************************************
					for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++)
					{
						
						String sTempStopWord = sStopWords[iStopIndex].toLowerCase();
						
						if(sStem.equals(sTempStopWord))
						{
							
							bStopWord = true;						
							
						}//End of if(sTempWord.equals(sTempStopWord))
						
					}//End of for(int iStopIndex = 0; iStopIndex < 54; iStopIndex++
					
					if(bStopWord == false)
					{
						
						if(iIndex < 8)
						{
							sKeyStems[iIndex] = sStem;
							//System.out.println("Stem fetched: " + sKeyStems[iIndex]);
							iIndex = iIndex + 1;
							
						}//End of if(iIndex < 8)
											
						if(iIndex >= 8)
						{

							String sPrefix = sKeyStems[0].substring(0);
							String sMatch = findCommonPrefix(sKeyStems, sPrefix);					
							iIndex = 0;//Reset the value of iIndex for next block of Stems
							int iPrefixLength = 0;
							
							//System.out.println("sMatch value: " + sMatch);
							if(sMatch != null)
							{
								
								iPrefixLength = sMatch.length();
								CompressStem ctNewStem = new CompressStem();
								String sDictionary = sMatch + "*";
								
								for(int x = 0; x < 8; x++)
								{
									
									sDictionary = sDictionary + sKeyStems[x].substring(iPrefixLength).length() + "|" + sKeyStems[x].substring(iPrefixLength);
									
									int iStartDocId = 0;
									int iGap = 0;
									int iFreq = 0;
									PostingFile psFile;
									//System.out.println("Adding posting files now...");
									
									switch(x)
									{
										
										case 0:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 1");
											break;
										}
										case 1:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm2.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 2");
											break;
										}
										case 2:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm3.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 3");
											break;
										}
										case 3:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm4.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 4");
											break;
										}
										case 4:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm5.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 5");
											break;
										}
										case 5:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm6.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 6");
											break;
										}
										case 6:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm7.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 7");
											break;
										}
										case 8:
										{
											
											//Compress the posting files and their frequency
											for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.keySet())
											{
												
												iGap = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId) - iStartDocId;
												iFreq = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId).intValue();
												
												psFile = new PostingFile();
												psFile.byDocumentId = getGammaCode(iGap);
												psFile.byFrequency = getDeltaCode(iFreq);
												
												ctNewStem.alPostingFileTerm8.add(psFile);
												
												iStartDocId = tmUnCompressedStemIndex.get(sKeyStems[x]).tmPosting.get(iDocId);
												
											}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
											
											//System.out.println("Added posting files for Term 8");
											break;
										}
										
									}//End of case
									
								}
								
								//System.out.println("New Dictionary Term: " + sDictionary);
								tmCompressedStemIndex.put(sDictionary, ctNewStem);
								
							}//End of if(!sMatch.equals(null))
							else
							{
								
								String sPrefix2 = null;
								String sMatch2 = null;
								String[] sKeyStems2 = new String[7];
								
								for(int p=0; p<7; p++)
								{
									
									sKeyStems2[p] = sKeyStems[p];
									
								}
								
								//If nothing matches in all 8 Stems together then split then in 7 and 1
								//then try 6 and 2 and so on
								for(int l = 0; l < 7; l++)
								{
									
									sPrefix2 = sKeyStems[0].substring(0);
									sMatch2 = findCommonPrefix(sKeyStems2, sPrefix);
									
								}								
								
								if(sMatch2 != null)//We have a grouping of 7 alike and 1 different 
								{

									int iPrefixLength2 = sMatch2.length();
									CompressStem ctNewStem = new CompressStem();
									String sDictionary2 = sMatch2 + "*";
									
									for(int x = 0; x < 7; x++)
									{
										
										sDictionary2 = sDictionary2 + sKeyStems2[x].substring(iPrefixLength2).length() + "|" + sKeyStems2[x].substring(iPrefixLength2);
										
										int iStartDocId = 0;
										int iGap = 0;
										int iFreq = 0;
										PostingFile psFile;
										//System.out.println("Adding posting files now...");
										
										switch(x)
										{
											
											case 0:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 1");
												break;
											}
											case 1:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm2.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 2");
												break;
											}
											case 2:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm3.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 3");
												break;
											}
											case 3:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm4.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 4");
												break;
											}
											case 4:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm5.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 5");
												break;
											}
											case 5:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm6.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 6");
												break;
											}
											case 6:
											{
												
												//Compress the posting files and their frequency
												for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.keySet())
												{
													
													iGap = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId) - iStartDocId;
													iFreq = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId).intValue();
													
													psFile = new PostingFile();
													psFile.byDocumentId = getGammaCode(iGap);
													psFile.byFrequency = getDeltaCode(iFreq);
													
													ctNewStem.alPostingFileTerm7.add(psFile);
													
													iStartDocId = tmUnCompressedStemIndex.get(sKeyStems2[x]).tmPosting.get(iDocId);
													
												}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
												
												//System.out.println("Added posting files for Term 7");
												break;
											}
											
										}//End of case
										
									}
									
									//System.out.println("New Dictionary Term: " + sDictionary2);
									tmCompressedStemIndex.put(sDictionary2, ctNewStem);
									
									sKeyStems[0] = sKeyStems[7];
									iIndex = 1;
									
								}
								else
								{
									
									String sPrefix3 = null;
									String sMatch3 = null;
									String[] sKeyStems3 = new String[6];
									
									for(int p=0; p<6; p++)
									{
										
										sKeyStems3[p] = sKeyStems[p];
										
									}
									
									//If nothing matches in all 8 Stems together then split then in 7 and 1
									//then try 6 and 2 and so on
									for(int l = 0; l < 6; l++)
									{
										
										sPrefix3 = sKeyStems[0].substring(0);
										sMatch3 = findCommonPrefix(sKeyStems3, sPrefix);
										
									}
									
									if(sMatch3 != null)//We have a grouping of 6 alike and 2 different 
									{

										int iPrefixLength3 = sMatch3.length();
										CompressStem ctNewStem = new CompressStem();
										String sDictionary3 = sMatch3 + "*";
										
										for(int x = 0; x < 7; x++)
										{
											
											sDictionary3 = sDictionary3 + sKeyStems3[x].substring(iPrefixLength3).length() + "|" + sKeyStems3[x].substring(iPrefixLength3);
											
											int iStartDocId = 0;
											int iGap = 0;
											int iFreq = 0;
											PostingFile psFile;
											//System.out.println("Adding posting files now...");
											
											switch(x)
											{
												
												case 0:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 1");
													break;
												}
												case 1:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm2.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 2");
													break;
												}
												case 2:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm3.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 3");
													break;
												}
												case 3:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm4.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 4");
													break;
												}
												case 4:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm5.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 5");
													break;
												}
												case 5:
												{
													
													//Compress the posting files and their frequency
													for(Integer iDocId : tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.keySet())
													{
														
														iGap = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId) - iStartDocId;
														iFreq = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId).intValue();
														
														psFile = new PostingFile();
														psFile.byDocumentId = getGammaCode(iGap);
														psFile.byFrequency = getDeltaCode(iFreq);
														
														ctNewStem.alPostingFileTerm6.add(psFile);
														
														iStartDocId = tmUnCompressedStemIndex.get(sKeyStems3[x]).tmPosting.get(iDocId);
														
													}//End of for(Integer iDocId : tmUnCompressedStemIndex.get(sKey).tmPosting.keySet())
													
													//System.out.println("Added posting files for Term 6");
													break;
												}
												
											}//End of case
											
										}
										
										//System.out.println("New Dictionary Term: " + sDictionary3);
										tmCompressedStemIndex.put(sDictionary3, ctNewStem);
										
										sKeyStems[0] = sKeyStems[6];
										sKeyStems[1] = sKeyStems[7];
										iIndex = 2;
									}									
									
								}
								
							}
							
						}
						
					}
										
					
				}//End of for(String sStem: tmUnCompressedStemIndex.keySet())
					
			}//End of if(bStopWord == false)
			
		}//End of for(String sKey : tmUnCompressedStemIndex.keySet())	
	
		try
		{
		
			PrintWriter pwOutPutFile = new PrintWriter(new FileWriter(sTempOutPutDirectory + "Index_Version2.compress.txt"));		

			for(String sKey: tmCompressedStemIndex.keySet())
			{
				pwOutPutFile.print(sKey);
				
				for(int i=0; i<tmCompressedStemIndex.get(sKey).alPostingFileTerm.size(); i++)
				{
					
					pwOutPutFile.print(tmCompressedStemIndex.get(sKey).alPostingFileTerm.get(i).byDocumentId);
					pwOutPutFile.print(tmCompressedStemIndex.get(sKey).alPostingFileTerm.get(i).byFrequency);
					
				}
								
			}
			pwOutPutFile.close();
			
		}//End of try block
		catch(Exception e)
		{
			
			System.err.println("Error encountered while writing to output file...");
			
		}//End of catch block
		
		System.out.println("UnCompressed: Inverted Stem List Length in Bytes:  " + tmUnCompressedStemIndex.size());
		System.out.println("Compressed: Inverted Stem List Length in Bytes:  " + tmCompressedStemIndex.size());
		
	}//End of compressStemIndex
	
}//End of Compress
