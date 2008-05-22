package xcon.word;

/**
 * Searches a dictionary for words of which the rotonym also occurs in the
 * dictionary
 * @author loudiyimo
 * 
 * <pre>
 * voorbeeld commandline om app te starten:
 * java xwords.palindrome.RotonymFinder dictionary.txt 180
 * 
 * waarbij:
 * 1e arg is filenaam van woordenboek
 * 2e arg is aanduiding van RotonymStrategy:
 *   180 = xwords.palindrome.Rotonym180
 *   90 = xwords.palindrome.Rotonym90
 * 
 * bepaal de rotonym strategie
 * bepaal het bestand die je wilt scannen 
 * scan het bestand
 * lees woord voor woord in
 * bepaal voor elk woord of het een rotonym is
 * ja:
 *   zoek in het woordenboek naar deze rotonym
 *   gevonden?
 *   ja: uitprinten
 *   nee: ga verder
 * nee:
 *   ga verder met volgende woorden  
 *   
 *   
 * extra 1:
 * doe dit altijd voor alle RotonymStrategien die er zijn   
 * 
 * extra 2:
 * Maak plugins van de RotonymStragy objecten (zie DataStore)
 * extra 3:
 * voeg een of meer nieuwe RotonymStrategy implementaties toe
 * </pre>
 */
public class RotonymFinder {

}
