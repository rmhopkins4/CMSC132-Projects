import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/*
 * This class is used to determine the minimum/shortest snippet from a document which 
 * contains all of the requested terms in any order. 
 */
public class MinimumSnippet {

	ArrayList<String> orderTermsInSnippet;
	ArrayList<Integer> finalPositionsOfTermsInDoc;
	List<String> terms;
	
	/*
	 * Constructor for MinimumSnippet, determines what the minimum snippet is and provides
	 * the class with the order of the terms in the snippet as well as their positions in the
	 * original document.
	 */
	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		if(terms.size() == 0) {
			throw new IllegalArgumentException("terms param is empty");
		}
		
		this.terms = terms;
		
		Iterator<String> documentIterator = document.iterator();
		
		//keeps track of what terms have been found in the current snippet
		ArrayList<String> termsContained = new ArrayList<>(); 
		
		/*
		 * Maintains a list of the positions in the original document of the 
		 * terms that are in the current snippet
		 */
		ArrayList<Integer> termsPositions = new ArrayList<>();
		
		int iteratorPosition = -1;
		
		while(documentIterator.hasNext()) {
			iteratorPosition ++;
			String currentTerm = documentIterator.next();
			
			if(terms.contains(currentTerm)) {
				termsContained.add(currentTerm);
				termsPositions.add(iteratorPosition);
				
				//used to make sure the snippet is as short as viably possible
				while(termsContained.lastIndexOf(termsContained.get(0)) != 0) {
					termsContained.remove(0);
					termsPositions.remove(0);
				}
			}
			
			//makes sure snippet contains all terms and is viable
			boolean allTermsContained = true;
			for(String s : terms) {
				if(!termsContained.contains(s)) {
					allTermsContained = false;
				}
			}
			
			if(allTermsContained) {
				//determines whether the newest viable snippet is shorter than the shortest so-far
				if(finalPositionsOfTermsInDoc == null || 
						getLength() > termsPositions.get(termsPositions.size() - 1) 
						- termsPositions.get(0) + 1){
					orderTermsInSnippet = termsContained;
					finalPositionsOfTermsInDoc = termsPositions;
				}
			}
		}
	}

	/*
	 * Returns whether or not all terms were found in the document. 
	 * If all terms were not found, then none of the other methods should be called.

	 */
	public boolean foundAllTerms() {
		return finalPositionsOfTermsInDoc != null;
	}

	/*
	 * Return the starting position of the snippet in the original document.
	 */
	public int getStartingPos() {
		return finalPositionsOfTermsInDoc.get(0);
	}

	/*
	 * Returns the ending position of the snippet in the original document
	 */
	public int getEndingPos() {
		return finalPositionsOfTermsInDoc.get(finalPositionsOfTermsInDoc.size() - 1);
	}

	/*
	 * Return total number of elements contained in the snippet. 
	 */
	public int getLength() {
		return getEndingPos() - getStartingPos() + 1;
	}

	/*
	 * Returns the position of a search term as it appears in the original document. 
	 * index parameter is the index of the search term in the list of terms provided to the constructor.
	 * If the index is one this method will return the position of the second search term provided.
	 */
	public int getPos(int index) {
		return finalPositionsOfTermsInDoc.get(orderTermsInSnippet.indexOf(terms.get(index)));
	}

}
